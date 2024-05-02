package edu.profitsoft.musicreviewer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SongDetailsDTO {
    private Long id;
    private String title;
    private String album;
    private int year;
    private String genres;
    private ArtistDTO artist;
}
