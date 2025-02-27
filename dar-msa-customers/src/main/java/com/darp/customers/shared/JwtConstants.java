package com.darp.customers.shared;

import lombok.experimental.UtilityClass;

@UtilityClass
public class JwtConstants {
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_NAME = "Authorization";
  public static final String GENERIC_USER_ROLE = "ROLE_USER";
}
