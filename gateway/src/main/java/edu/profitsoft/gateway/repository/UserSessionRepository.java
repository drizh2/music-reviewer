package edu.profitsoft.gateway.repository;

import edu.profitsoft.gateway.data.UserSession;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserSessionRepository extends R2dbcRepository<UserSession, String> {
    Mono<UserSession> findUserSessionById(String id);
}
