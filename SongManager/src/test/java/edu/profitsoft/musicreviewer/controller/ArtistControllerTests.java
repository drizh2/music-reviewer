package edu.profitsoft.musicreviewer.controller;

import edu.profitsoft.musicreviewer.dao.ArtistDAO;
import edu.profitsoft.musicreviewer.model.Artist;
import edu.profitsoft.musicreviewer.service.ArtistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ArtistControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistService artistService;

    @MockBean
    private ArtistDAO artistDAO;

    @BeforeEach
    public void setup() {
        Mockito.reset(artistService, artistDAO);
    }

    @Test
    void testGetAllArtists() throws Exception {
        // Arrange
        Artist artist1 = new Artist(1L, "Artist 1", "Bio 1");
        Artist artist2 = new Artist(2L, "Artist 2", "Bio 2");
        List<Artist> artistList = Arrays.asList(artist1, artist2);

        Mockito.when(artistDAO.getAllArtists()).thenReturn(artistList);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/artists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fullName").value("Artist 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].fullName").value("Artist 2"));
    }

    @Test
    void testCreateArtist() throws Exception {
        // Arrange
        String requestJson = "{ \"name\": \"New Artist\" }";

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/artists")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Artist has been created"));
    }

    @Test
    void testDeleteArtist() throws Exception {
        // Arrange
        Mockito.when(artistDAO.deleteArtistById(1L)).thenReturn(1); // 1 means success

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/artists/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Artist has been deleted"));
    }

    @Test
    void testDeleteArtistNotFound() throws Exception {
        // Arrange
        Mockito.when(artistDAO.deleteArtistById(ArgumentMatchers.anyLong())).thenReturn(0); // 0 means artist not found

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/artists/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Artist has not been deleted"));
    }

    @Test
    void testUpdateArtistWithException() throws Exception {
        // Arrange
        String updateRequestJson = "{ \"name\": \"Invalid Artist\" }";

        Mockito.doThrow(new RuntimeException("Artist not found")).when(artistService).updateArtist(ArgumentMatchers.anyLong(), ArgumentMatchers.any());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/artists/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateRequestJson))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Artist not found"));
    }
}