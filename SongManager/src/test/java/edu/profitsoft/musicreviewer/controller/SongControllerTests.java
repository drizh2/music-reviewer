package edu.profitsoft.musicreviewer.controller;

import edu.profitsoft.musicreviewer.dao.SongDAO;
import edu.profitsoft.musicreviewer.model.Artist;
import edu.profitsoft.musicreviewer.model.Song;
import edu.profitsoft.musicreviewer.payload.request.SongListRequest;
import edu.profitsoft.musicreviewer.service.SongService;
import edu.profitsoft.musicreviewer.validator.ResponseErrorValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SongControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SongService songService;

    @MockBean
    private SongDAO songDAO;

    @MockBean
    private ResponseErrorValidator responseErrorValidator;

    @BeforeEach
    public void setup() {
        Mockito.reset(songService, songDAO, responseErrorValidator);
    }

    @Test
    void testCreateSong() throws Exception {
        // Arrange
        String createSongJson = "{ \"title\": \"New Song\", \"artistId\": 1, \"duration\": 240 }";

        when(responseErrorValidator.mapValidationService(any())).thenReturn(null);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/songs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createSongJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Song has been created successfully!"));
    }

    @Test
    void testGetSongById() throws Exception {

        Artist artist = Artist.builder()
                .id(1L)
                .build();
        // Arrange
        Song song = Song.builder()
                .id(1L)
                .title("Existing Song")
                .album("Existing Album")
                .year(2000)
                .genres("Existing Genres")
                .artist(artist)
                .build();
        when(songDAO.getSongById(1L)).thenReturn(song);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/songs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Existing Song"));
    }

    @Test
    void testDeleteSong() throws Exception {
        // Arrange
        when(songDAO.deleteSongById(1L)).thenReturn(1);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/songs/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Song has been deleted successfully!"));
    }

    @Test
    void testUpdateSong() throws Exception {
        // Arrange
        String updateSongJson = "{ \"title\": \"Updated Song\", \"duration\": 300 }";

        when(responseErrorValidator.mapValidationService(any())).thenReturn(null);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/songs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateSongJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Song has been updated successfully!"));
    }

    @Test
    void testUploadSong_EmptyFile() throws Exception {
        // Arrange
        MockMultipartFile emptyFile = new MockMultipartFile("file", "empty.mp3", MediaType.MULTIPART_FORM_DATA_VALUE, new byte[0]);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/songs/upload").file(emptyFile))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The file is empty!"));
    }

    @Test
    void testUploadSong_WrongFileExtension() throws Exception {
        // Arrange
        MockMultipartFile jsonFile = new MockMultipartFile("file", "wrong.xml", MediaType.MULTIPART_FORM_DATA_VALUE, "{ }".getBytes());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/songs/upload").file(jsonFile))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("This file is not json!"));
    }

    @Test
    void testGetExcelReport() throws Exception {
        // Arrange
        SongListRequest listRequest = new SongListRequest();
        listRequest.setPage(1);
        listRequest.setSize(10);

        when(songService.getSongsPage(any(), any())).thenReturn(Page.empty());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/songs/_report")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"page\": 1, \"size\": 10 }"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}