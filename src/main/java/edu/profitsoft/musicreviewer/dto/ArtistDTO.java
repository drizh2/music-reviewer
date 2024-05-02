package edu.profitsoft.musicreviewer.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArtistDTO {
    private Long id;
    private String fullName;
    private String bio;
}
