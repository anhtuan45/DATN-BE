package com.ws.masterserver.config.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.masterserver.entity.UserEntity;
import com.ws.masterserver.utils.base.WsRepository;
import com.ws.masterserver.utils.common.BeanUtils;
import com.ws.masterserver.utils.constants.WsConst;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String email = request.getParameter(WsConst.UserFields.EMAIL_VAR);
        String password = request.getParameter(WsConst.UserFields.PASSWORD);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        final User user = (User) authResult.getPrincipal();
        final WsRepository repository = BeanUtils.getBean(WsRepository.class);
        final UserEntity userEntity = repository.userRepository.findByEmailAndActive(user.getUsername(), Boolean.TRUE);
        final Algorithm algorithm = Algorithm.HMAC256(WsConst.Values.JWT_SECRET.getBytes());
        final String accessToken = JWT.create()
                .withSubject(userEntity.getEmail())
                .withExpiresAt(new Date(System.currentTimeMillis() + WsConst.Values.ACCESS_TOKEN_EXPIRED))
                .withIssuer(request.getRequestURL().toString())
                .withClaim(WsConst.UserFields.ROLE_VAR, user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()).get(0))
                .withClaim(WsConst.UserFields.NAME_VAR, (Optional.of(userEntity.getFirstName()).orElse("") + " " + Optional.of(userEntity.getLastName()).orElse("")).trim())
                .withClaim(WsConst.UserFields.EMAIL_VAR, userEntity.getEmail())
                .withClaim(WsConst.UserFields.ID_VAR, userEntity.getId())
                .sign(algorithm);

        final String refreshToken = JWT.create()
                .withSubject(userEntity.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + WsConst.Values.REFRESH_TOKEN_EXPIRED))
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map responses = new HashMap<String, Object>();
        responses.put(WsConst.Nouns.ACCESS_TOKEN_FIELD, accessToken);
        responses.put(WsConst.Nouns.REFRESH_TOKEN_FIELD, refreshToken);

        new ObjectMapper().writeValue(response.getOutputStream(), responses);
    }
}
