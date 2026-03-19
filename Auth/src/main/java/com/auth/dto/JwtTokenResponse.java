package com.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data

public class JwtTokenResponse {

    private String token;
    private String type;
    private String validUntil;

}
