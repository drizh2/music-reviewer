package edu.profitsoft.musicreviewer.payload.response;

import lombok.Data;

@Data
public class FileProcessResponse {
    private int successCount;
    private int invalidCount;
}
