package edu.profitsoft.musicreviewer.exception;

public class SongExistsException extends RuntimeException {
    public SongExistsException(String message) {
        super(message);
    }
}
