package edu.profitsoft.musicreviewer.mapper;

import edu.profitsoft.musicreviewer.dto.ArtistDTO;
import edu.profitsoft.musicreviewer.model.Artist;

public class ArtistMapper {

    private ArtistMapper() {
    }

    public static ArtistDTO artistToArtistDTO(Artist artist) {
        return ArtistDTO.builder()
                .id(artist.getId())
                .fullName(artist.getFullName())
                .bio(artist.getBio())
                .build();
    }
}
