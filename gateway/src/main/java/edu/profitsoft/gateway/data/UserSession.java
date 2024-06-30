package edu.profitsoft.gateway.data;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;

@Getter
@Setter
@Entity
public class UserSession {

//    @org.springframework.data.annotation.Id
    @Id
    private String id;
    private String email;
    private String name;
    private Instant expiresAt;

    public boolean isExpired() {
        return expiresAt.isBefore(Instant.now());
    }

}
