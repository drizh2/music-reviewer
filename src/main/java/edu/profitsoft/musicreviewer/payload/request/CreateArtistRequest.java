package edu.profitsoft.musicreviewer.payload.request;

import lombok.Data;

@Data
public class CreateArtistRequest {
    private String fullName;
    private String bio;
}
