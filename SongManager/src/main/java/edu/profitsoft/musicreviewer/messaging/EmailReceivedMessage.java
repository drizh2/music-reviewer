package edu.profitsoft.musicreviewer.messaging;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.io.Serializable;

@Getter
@Builder
@Jacksonized
public class EmailReceivedMessage implements Serializable {
    private String subject;
    private String content;
}