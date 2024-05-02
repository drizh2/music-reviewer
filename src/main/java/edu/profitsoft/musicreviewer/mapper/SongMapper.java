package edu.profitsoft.musicreviewer.mapper;

import edu.profitsoft.musicreviewer.dto.ArtistDTO;
import edu.profitsoft.musicreviewer.dto.SongDetailsDTO;
import edu.profitsoft.musicreviewer.dto.SongInfoDTO;
import edu.profitsoft.musicreviewer.model.Song;

public class SongMapper {

    private SongMapper() {
    }

    public static SongInfoDTO songToSongInfoDTO(Song song) {
        return SongInfoDTO.builder()
                .title(song.getTitle())
                .album(song.getAlbum())
                .year(song.getYear())
                .build();
    }

    public static SongDetailsDTO songToSongDetailsDTO(Song song) {
        ArtistDTO artistDTO = ArtistMapper.artistToArtistDTO(song.getArtist());

        return SongDetailsDTO.builder()
                .id(song.getId())
                .title(song.getTitle())
                .album(song.getAlbum())
                .year(song.getYear())
                .genres(song.getGenres())
                .artist(artistDTO)
                .build();
    }
}
