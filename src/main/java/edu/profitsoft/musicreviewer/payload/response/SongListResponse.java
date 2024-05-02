package edu.profitsoft.musicreviewer.payload.response;

import edu.profitsoft.musicreviewer.dto.SongInfoDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SongListResponse {
    private List<SongInfoDTO> list;
    private int totalPages;
}
