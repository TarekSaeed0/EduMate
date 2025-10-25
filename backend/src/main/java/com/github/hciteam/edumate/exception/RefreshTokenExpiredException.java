package com.github.hciteam.edumate.exception;

import org.springframework.http.HttpStatus;

public class RefreshTokenExpiredException extends ApiException {

  public RefreshTokenExpiredException() {
    super("REFRESH_TOKEN_EXPIRED", "Refresh token is expired",
        HttpStatus.UNAUTHORIZED);
  }
}
