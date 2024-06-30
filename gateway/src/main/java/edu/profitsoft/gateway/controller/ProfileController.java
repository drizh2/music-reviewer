package edu.profitsoft.gateway.controller;

import edu.profitsoft.gateway.auth.dto.UserInfo;
import edu.profitsoft.gateway.data.UserSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import edu.profitsoft.gateway.service.SessionService;
import edu.profitsoft.gateway.service.UnauthorizedException;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final SessionService sessionService;

    @GetMapping("/profile")
    public Mono<UserInfo> profile(ServerWebExchange exchange) {
        return sessionService.checkSession(exchange)
                .flatMap(this::toUserInfo)
                .onErrorResume(UnauthorizedException.class, e -> {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
                });
    }

    private Mono<UserInfo> toUserInfo(UserSession session) {
        return Mono.just(UserInfo.builder()
                .email(session.getEmail())
                .name(session.getName())
                .build());
    }

}
