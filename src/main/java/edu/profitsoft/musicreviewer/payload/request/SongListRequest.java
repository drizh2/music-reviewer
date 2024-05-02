package edu.profitsoft.musicreviewer.payload.request;

import lombok.Data;

@Data
public class SongListRequest {
    private Long songId;
    private String title;
    private String album;
    private Integer year;
    private int page;
    private int size;
}
