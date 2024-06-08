package edu.profitsoft.musicreviewer.exception;

public class WrongFileExtension extends RuntimeException {
    public WrongFileExtension(String message) {
        super(message);
    }
}
