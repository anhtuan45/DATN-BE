package com.ws.masterserver.config.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.masterserver.utils.common.JsonUtils;
import com.ws.masterserver.utils.constants.WsCode;
import com.ws.masterserver.utils.constants.WsConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("doFilterInternal() servlet path: {}", request.getServletPath());
        if (checkNoAuth4EndPoints(request)) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith(WsConst.Values.BEARER_SPACE)) {
                try {
                    final String token = authorizationHeader.substring(WsConst.Values.BEARER_SPACE.length());
                    final Algorithm algorithm = Algorithm.HMAC256(WsConst.Values.JWT_SECRET.getBytes());
                    final JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                    final DecodedJWT decodedJWT = jwtVerifier.verify(token);

                    final Claim id = decodedJWT.getClaim("id");
                    final Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(decodedJWT.getClaim("role").asString()));

                    final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            id, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e) {
                    log.error("doFilterInternal() error: {}", e.getMessage());
                    response.setHeader("error", e.getMessage());
                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    HashMap<String, Object> errors = new HashMap<>();
                    errors.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
                    errors.put("message", WsCode.INTERNAL_SERVER.getMessage());
                    errors.put("timeStamp", new Date());
                    errors.put("path", request.getServletPath());
                    new ObjectMapper().writeValue(response.getOutputStream(), errors);
//                    throw new WsException(WsCode.SESSION_EXPIRED_DATE);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }

    private boolean checkNoAuth4EndPoints(HttpServletRequest request) {
        //log.info("checkNoAuth4EndPoints() req: {}", JsonUtils.toJson(request));
        System.out.println();
//        List<String> noAuthEndPoints = List.of(
//                "/api/v1/login",
//                "/api/v1/token/refresh",
//                "/api/v1/auth/forgot-password/send-mail",
//                "/api/v1/auth/reset-password",
//                "/api/v1/auth/check-reset-token",
//                "/api/v1/user/customer/register"
//        );
        return request.getServletPath().contains("/api/v1/no-auth") || request.getServletPath().equals("/api/v1/login") || "/ping".equalsIgnoreCase(request.getServletPath());
    }
}
