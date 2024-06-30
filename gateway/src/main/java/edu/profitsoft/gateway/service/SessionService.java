package edu.profitsoft.gateway.service;

import edu.profitsoft.gateway.auth.dto.UserInfo;
import edu.profitsoft.gateway.data.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import edu.profitsoft.gateway.repository.UserSessionRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import static edu.profitsoft.gateway.filter.AuthenticationFilter.COOKIE_SESSION_ID;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final UserSessionRepository userSessionRepository;
    public static final Duration SESSION_DURATION = Duration.ofHours(1);

    public Mono<UserSession> checkSession(ServerWebExchange exchange) {
        HttpCookie sessionCookie = exchange.getRequest().getCookies().getFirst(COOKIE_SESSION_ID);
        if (sessionCookie == null) {
            return Mono.error(new UnauthorizedException("Session Cookie not found"));
        }
        return userSessionRepository.findUserSessionById(sessionCookie.getValue())
                .flatMap(session ->
                        session.isExpired()
                                ? Mono.error(new UnauthorizedException("Session expired"))
                                : Mono.just(session)
                ).switchIfEmpty(Mono.error(new UnauthorizedException("Session not found")));
    }

    public Mono<UserSession> saveSession(UserInfo userInfo) {
        return createSession(userInfo, Instant.now().plus(SESSION_DURATION));
    }

    private Mono<UserSession> createSession(UserInfo userInfo, Instant expiresAt) {
        UserSession userSession = new UserSession();
        userSession.setId(UUID.randomUUID().toString());
        userSession.setEmail(userInfo.getEmail());
        userSession.setName(userInfo.getName());
        userSession.setExpiresAt(expiresAt);

        return userSessionRepository.save(userSession);
    }

    public Mono<Void> addSessionCookie(ServerWebExchange exchange, UserSession session) {
        return Mono.fromRunnable(() -> exchange.getResponse().addCookie(ResponseCookie.from(COOKIE_SESSION_ID)
                .value(session.getId())
                .path("/")
                .maxAge(SESSION_DURATION)
                .secure(true)
                .httpOnly(true) // Prevents JavaScript from accessing the cookie
                .build()));
    }


}