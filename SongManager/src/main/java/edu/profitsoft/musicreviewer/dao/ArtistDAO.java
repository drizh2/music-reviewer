package edu.profitsoft.musicreviewer.dao;

import edu.profitsoft.musicreviewer.model.Artist;
import edu.profitsoft.musicreviewer.repository.ArtistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ArtistDAO {
    private final ArtistRepository artistRepository;

    public Artist getArtistById(Long id) {
        return artistRepository.findById(id)
                .orElse(null);
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    @Transactional
    public Integer deleteArtistById(Long id) {
        return artistRepository.deleteArtistById(id);
    }

    public Artist getArtistByName(String name) {
        return artistRepository.findArtistByFullName(name)
                .orElse(null);
    }
}
