package edu.profitsoft.musicreviewer.repository;

import edu.profitsoft.musicreviewer.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findArtistByFullName(String fullName);
    Integer deleteArtistById(Long id);
}
