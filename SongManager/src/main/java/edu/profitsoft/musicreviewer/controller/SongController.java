package edu.profitsoft.musicreviewer.controller;

import edu.profitsoft.musicreviewer.dao.SongDAO;
import edu.profitsoft.musicreviewer.dto.SongDetailsDTO;
import edu.profitsoft.musicreviewer.dto.SongInfoDTO;
import edu.profitsoft.musicreviewer.exception.EmptyFileException;
import edu.profitsoft.musicreviewer.exception.WrongFileExtension;
import edu.profitsoft.musicreviewer.mapper.SongMapper;
import edu.profitsoft.musicreviewer.model.Song;
import edu.profitsoft.musicreviewer.payload.request.CreateSongRequest;
import edu.profitsoft.musicreviewer.payload.request.SongListRequest;
import edu.profitsoft.musicreviewer.payload.request.UpdateSongRequest;
import edu.profitsoft.musicreviewer.payload.response.FileProcessResponse;
import edu.profitsoft.musicreviewer.payload.response.MessageResponse;
import edu.profitsoft.musicreviewer.payload.response.SongListResponse;
import edu.profitsoft.musicreviewer.publishers.EmailPublisher;
import edu.profitsoft.musicreviewer.service.SongService;
import edu.profitsoft.musicreviewer.validator.ResponseErrorValidator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
@RequiredArgsConstructor
@CrossOrigin
public class SongController {

    private final SongService songService;
    private final SongDAO songDAO;
    private final ResponseErrorValidator responseErrorValidator;
    private final EmailPublisher emailPublisher;

    @PostMapping
    public ResponseEntity<Object> createSong(@Valid @RequestBody CreateSongRequest request,
                                             BindingResult bindingResult) {
        ResponseEntity<Object> errorMap = responseErrorValidator.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errorMap)) return errorMap;

        Song song = songService.createSong(request);
        emailPublisher.publish(song);
        return new ResponseEntity<>(new MessageResponse("Song has been created successfully!"), HttpStatus.OK);
    }

    @GetMapping("/{songId}")
    public ResponseEntity<Object> getAllSongs(@PathVariable Long songId) {
        Song song = songDAO.getSongById(songId);
        SongDetailsDTO dto = SongMapper.songToSongDetailsDTO(song);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity<Object> deleteSong(@PathVariable Long songId) {
        Integer result = songDAO.deleteSongById(songId);
        String resultMessage = result > 0 ? "Song has been deleted successfully!" : "Song has not been deleted!";

        return new ResponseEntity<>(new MessageResponse(resultMessage), HttpStatus.OK);
    }

    @PutMapping("/{songId}")
    public ResponseEntity<Object> updateSong(@PathVariable Long songId,
                                             @Valid @RequestBody UpdateSongRequest request,
                                             BindingResult bindingResult) {
        ResponseEntity<Object> errorMap = responseErrorValidator.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errorMap)) return errorMap;

        songService.updateSong(songId, request);
        return new ResponseEntity<>(new MessageResponse("Song has been updated successfully!"), HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadSong(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new EmptyFileException("The file is empty!");
        }

        if (!file.getOriginalFilename().endsWith(".json")) {
            throw new WrongFileExtension("This file is not json!");
        }


        FileProcessResponse response = songService.processFile(file);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/_list")
    public ResponseEntity<Object> listSongs(@Valid @RequestBody SongListRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        Page<Song> songPage = songService.getSongsPage(request, pageable);

        List<SongInfoDTO> songInfoDTOList = songPage.stream()
                .map(SongMapper::songToSongInfoDTO)
                .toList();

        SongListResponse response = SongListResponse.builder()
                .list(songInfoDTOList)
                .totalPages(songPage.getTotalPages())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/_report")
    public void getExcelReport(@Valid @RequestBody SongListRequest request,
                                                 HttpServletResponse response) {
        Pageable pageable = Pageable.unpaged();
        Page<Song> songPage = songService.getSongsPage(request, pageable);

        songService.exportAsExcelSheet(songPage, response);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(Exception ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
