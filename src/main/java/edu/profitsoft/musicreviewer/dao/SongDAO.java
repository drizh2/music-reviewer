package edu.profitsoft.musicreviewer.dao;

import edu.profitsoft.musicreviewer.exception.SongNotFoundException;
import edu.profitsoft.musicreviewer.model.Song;
import edu.profitsoft.musicreviewer.repository.SongRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SongDAO {
    private final SongRepository songRepository;

    public Song getSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("Song not found with id: " + id));
    }

    @Transactional
    public Integer deleteSongById(Long id) {
        return songRepository.deleteSongById(id);
    }

    public Page<Song> getSongsPageById(Long id, Pageable pageable) {
        return songRepository.findById(id, pageable);
    }
}
