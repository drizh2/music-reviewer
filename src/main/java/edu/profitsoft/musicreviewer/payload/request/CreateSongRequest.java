package edu.profitsoft.musicreviewer.payload.request;

import edu.profitsoft.musicreviewer.annotation.ValidYear;
import lombok.Data;

@Data
public class CreateSongRequest {
    private String title;
    private String album;
    @ValidYear
    private Integer year;
    private String genres;
    private Long artistId;
}
