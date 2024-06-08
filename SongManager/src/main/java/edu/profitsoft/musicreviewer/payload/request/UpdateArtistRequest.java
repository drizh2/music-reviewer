package edu.profitsoft.musicreviewer.payload.request;

import lombok.Data;

@Data
public class UpdateArtistRequest {
    private String fullName;
    private String bio;
}
