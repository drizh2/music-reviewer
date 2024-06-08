package edu.profitsoft.musicreviewer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SongInfoDTO {
    private String title;
    private String album;
    private Integer year;
}
