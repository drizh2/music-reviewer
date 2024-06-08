package edu.profitsoft.musicreviewer.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.profitsoft.musicreviewer.dao.ArtistDAO;
import edu.profitsoft.musicreviewer.dao.SongDAO;
import edu.profitsoft.musicreviewer.exception.ArtistNotFoundException;
import edu.profitsoft.musicreviewer.exception.FileProcessException;
import edu.profitsoft.musicreviewer.exception.SongExistsException;
import edu.profitsoft.musicreviewer.exception.SongNotFoundException;
import edu.profitsoft.musicreviewer.model.Artist;
import edu.profitsoft.musicreviewer.model.Song;
import edu.profitsoft.musicreviewer.payload.request.CreateSongRequest;
import edu.profitsoft.musicreviewer.payload.request.SongListRequest;
import edu.profitsoft.musicreviewer.payload.request.UpdateSongRequest;
import edu.profitsoft.musicreviewer.payload.response.FileProcessResponse;
import edu.profitsoft.musicreviewer.repository.ArtistRepository;
import edu.profitsoft.musicreviewer.repository.SongRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class SongService {
    private final SongRepository songRepository;
    private final ArtistDAO artistDAO;
    private final SongDAO songDAO;
    private final ObjectMapper objectMapper;
    private final ArtistRepository artistRepository;

    public Song createSong(CreateSongRequest request) {

        Artist artist = artistDAO.getArtistById(request.getArtistId());

        if (artist == null) {
            throw new ArtistNotFoundException("Artist not found");
        } else {
            try {
                Song song = Song.builder()
                        .title(request.getTitle())
                        .album(request.getAlbum())
                        .year(request.getYear())
                        .genres(request.getGenres())
                        .artist(artist)
                        .build();

                return songRepository.save(song);
            } catch (Exception e) {
                throw new SongExistsException("Song is already exists");
            }
        }
    }

    public Song updateSong(Long songId, UpdateSongRequest request) {
        Artist artist = artistDAO.getArtistById(request.getArtistId());

        if (artist == null) {
            throw new ArtistNotFoundException("Artist not found");
        }

        Song song = songDAO.getSongById(songId);

        if (song == null) {
            throw new SongNotFoundException("Song not found");
        }

        try {
            song.setTitle(request.getTitle());
            song.setAlbum(request.getAlbum());
            song.setYear(request.getYear());
            song.setGenres(request.getGenres());
            song.setArtist(artist);
            return songRepository.save(song);
        } catch (Exception e) {
            throw new SongExistsException("Song is already exists");
        }
    }

    public FileProcessResponse processFile(MultipartFile in) {

        int success = 0;
        int failure = 0;

        File file;
        try {
            file = File.createTempFile("file", ".json");
            in.transferTo(file);
        } catch (IOException e) {
            throw new FileProcessException("Error while converting input file");
        }

        try {
            JsonNode jsonTree = objectMapper.readTree(file);
            for (JsonNode node : jsonTree) {

                CreateSongRequest song = objectMapper.treeToValue(node, CreateSongRequest.class);
                if (!validateSongFields(song)) {
                    log.info("One or more fields were incorrect! Failure!");
                    failure++;
                    continue;
                }

                try {
                    this.createSong(song);
                } catch (ArtistNotFoundException e) {
                    log.info("Artist not found! Failure!");
                    failure++;
                    continue;
                }

                log.info("Successfully processed song!");
                success++;
            }
        } catch (IOException e) {
            throw new FileProcessException("Error while processing file");
        }

        FileProcessResponse response = new FileProcessResponse();
        response.setInvalidCount(failure);
        response.setSuccessCount(success);

        return response;
    }

    public Page<Song> getSongsPage(SongListRequest request, Pageable pageable) {
        if (request.getSongId() != null) {
            return songDAO.getSongsPageById(request.getSongId(), pageable);
        }

        return songRepository.findByFilters(request.getTitle(), request.getAlbum(), request.getYear(), pageable);
    }

    public void exportAsExcelSheet(Page<Song> songPage, HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=songs.xlsx");

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Songs");

            Row headers = sheet.createRow(0);
            headers.createCell(0).setCellValue("Id");
            headers.createCell(1).setCellValue("Title");
            headers.createCell(2).setCellValue("Album");
            headers.createCell(3).setCellValue("Year");
            headers.createCell(4).setCellValue("Genres");
            headers.createCell(5).setCellValue("Artist Name");
            headers.createCell(6).setCellValue("Artist Bio");

            for (int i = 0; i < songPage.getNumberOfElements(); i++) {
                Song song = songPage.getContent().get(i);
                Row row = sheet.createRow(i + 1);
                row.createCell(0).setCellValue(i);
                row.createCell(1).setCellValue(song.getTitle());
                row.createCell(2).setCellValue(song.getAlbum());
                row.createCell(3).setCellValue(song.getYear());
                row.createCell(4).setCellValue(song.getGenres());
                row.createCell(5).setCellValue(song.getArtist().getFullName());
                row.createCell(6).setCellValue(song.getArtist().getBio());
            }

            workbook.write(response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new FileProcessException("Something went wrong when writing to the output stream!");
        }
    }

    private boolean validateSongFields(CreateSongRequest request) {
        return request.getTitle() != null && !request.getTitle().isBlank()
                && request.getAlbum() != null && !request.getAlbum().isBlank()
                && request.getGenres() != null && !request.getGenres().isBlank()
                && request.getYear() != null && request.getYear() > 1900 && request.getYear() <= 2024
                && request.getArtistId() != null;
    }
}
