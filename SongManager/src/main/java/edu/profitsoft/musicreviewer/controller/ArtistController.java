package edu.profitsoft.musicreviewer.controller;

import edu.profitsoft.musicreviewer.dao.ArtistDAO;
import edu.profitsoft.musicreviewer.dto.ArtistDTO;
import edu.profitsoft.musicreviewer.mapper.ArtistMapper;
import edu.profitsoft.musicreviewer.model.Artist;
import edu.profitsoft.musicreviewer.payload.request.CreateArtistRequest;
import edu.profitsoft.musicreviewer.payload.request.UpdateArtistRequest;
import edu.profitsoft.musicreviewer.payload.response.MessageResponse;
import edu.profitsoft.musicreviewer.service.ArtistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@RequiredArgsConstructor
@CrossOrigin
public class ArtistController {

    private final ArtistService artistService;
    private final ArtistDAO artistDAO;

    @GetMapping
    public ResponseEntity<Object> getAllArtists() {
        List<Artist> artistList = artistDAO.getAllArtists();
        List<ArtistDTO> artistDTOList = artistList.stream()
                .map(ArtistMapper::artistToArtistDTO)
                .toList();
        return new ResponseEntity<>(artistDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> createArtist(@Valid @RequestBody CreateArtistRequest request) {
        artistService.createArtist(request);
        return new ResponseEntity<>(new MessageResponse("Artist has been created"), HttpStatus.OK);
    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<Object> deleteArtist(@PathVariable Long artistId) {
        Integer result = artistDAO.deleteArtistById(artistId);
        String resultMessage = result > 0 ? "Artist has been deleted" : "Artist has not been deleted";
        return new ResponseEntity<>(new MessageResponse(resultMessage), HttpStatus.OK);
    }

    @PutMapping("/{artistId}")
    public ResponseEntity<Object> updateArtist(@PathVariable Long artistId,
                                               @Valid @RequestBody UpdateArtistRequest request) {
        artistService.updateArtist(artistId, request);

        return new ResponseEntity<>(new MessageResponse("Artist has been updated"), HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(Exception ex) {
        return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
