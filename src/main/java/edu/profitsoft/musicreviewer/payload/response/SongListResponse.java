package edu.profitsoft.musicreviewer.payload.response;

import edu.profitsoft.musicreviewer.model.Song;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SongListResponse {
    private List<Song> list;
    private int totalPages;
}
