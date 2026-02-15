package com.posting.post.resources;

import com.posting.post.config.SecurityConfig;
import com.posting.post.config.TokenConfig;
import com.posting.post.config.UserDetailsImpl;
import com.posting.post.dto.request.LoginUserRequestDTO;
import com.posting.post.dto.request.RegisterUserRequestDTO;
import com.posting.post.dto.request.TokenRefreshRequestDTO;
import com.posting.post.dto.response.LoginUserResponseDTO;
import com.posting.post.dto.response.RegisterUserResponseDTO;
import com.posting.post.dto.response.TokenRefreshResponseDTO;
import com.posting.post.entities.TokenRefresh;
import com.posting.post.entities.User;
import com.posting.post.mapper.LoginUserMapper;
import com.posting.post.mapper.RegisterUserMapper;
import com.posting.post.repositories.UserRepository;
import com.posting.post.services.AuthService;
import com.posting.post.services.TokenRefreshService;
import com.posting.post.services.UserService;
import com.posting.post.services.exceptions.ConflictException;
import com.posting.post.services.exceptions.UnauthorizedActionException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;


@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    private final AuthService authService;
    private final RegisterUserMapper registerUserMapper;
    private final TokenRefreshService  tokenRefreshService;

    public AuthResource(AuthService authService,
                        RegisterUserMapper registerUserMapper,
                        TokenRefreshService tokenRefreshService) {

        this.authService = authService;
        this.registerUserMapper = registerUserMapper;
        this.tokenRefreshService = tokenRefreshService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginUserRequestDTO body) throws NoSuchAlgorithmException {
        List<String> tokens = authService.loginService(body);

        if (tokens.size() == 2) {
            return ResponseEntity.ok().body(new TokenRefreshResponseDTO(tokens.get(0), tokens.get(1)));
        }

        return ResponseEntity.ok().body(new LoginUserResponseDTO(tokens.get(0)));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RegisterUserResponseDTO> register(@RequestBody @Valid RegisterUserRequestDTO body) throws NoSuchAlgorithmException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                registerUserMapper.toRegisterUserResponseDTO(authService.registerService(body)));
    }

    @PostMapping(value = "/refresh")
    public ResponseEntity<TokenRefreshResponseDTO> refresh(@RequestBody TokenRefreshRequestDTO dto) throws NoSuchAlgorithmException {
        return ResponseEntity.ok().body(tokenRefreshService.updateTokensPair(dto.tokenRefresh()));

    }
}