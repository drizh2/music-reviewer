package edu.profitsoft.musicreviewer.repository;

import edu.profitsoft.musicreviewer.model.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Optional<Song> findById(Long id);
    Integer deleteSongById(Long id);

    @Query("SELECT s FROM Song s WHERE " +
            "(:title IS NULL OR s.title = :title) AND " +
            "(:album IS NULL OR s.album = :album) AND " +
            "(:year IS NULL OR s.year = :year)")
    Page<Song> findByFilters(
            @Param("title") String title,
            @Param("album") String album,
            @Param("year") Integer year,
            Pageable pageable);

    Page<Song> findById(@Param("songId") Long songId, Pageable pageable);
}
