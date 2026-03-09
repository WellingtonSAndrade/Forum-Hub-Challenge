package com.well.forumhubchallenge.infra.security.dto;

import java.time.Instant;

public record DadosTokenJWT(String tokenJWT, Instant expiration) {
}
