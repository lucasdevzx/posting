package com.posting.post.resources;

import com.posting.post.dto.request.LoginRequest;
import com.posting.post.security.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/login")
public class LoginResource {

    private TokenService tokenService;

    private final LoginRequest dto;
    private final AuthenticationManager authenticationManager;

    public LoginResource(LoginRequest body, AuthenticationManager authenticationManager) {
        dto = body;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<?> login(@RequestBody LoginRequest body) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(body.email(), body.password());
        var auth = authenticationManager.authenticate(token);
        String jwt = tokenService.generateTokens((UserDetails) auth.getPrincipal());
        return ResponseEntity.ok(jwt);
    }
}
