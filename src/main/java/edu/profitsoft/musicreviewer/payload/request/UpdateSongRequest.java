package edu.profitsoft.musicreviewer.payload.request;

import edu.profitsoft.musicreviewer.annotation.ValidYear;
import lombok.Data;

@Data
public class UpdateSongRequest {
    private String title;
    private String album;
    @ValidYear
    private int year;
    private String genres;
    private Long artistId;
}
