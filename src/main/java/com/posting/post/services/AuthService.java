package com.posting.post.services;

import com.posting.post.config.TokenConfig;
import com.posting.post.config.UserDetailsImpl;
import com.posting.post.dto.request.LoginUserRequestDTO;
import com.posting.post.dto.request.RegisterUserRequestDTO;
import com.posting.post.dto.response.LoginUserResponseDTO;
import com.posting.post.dto.response.RegisterUserResponseDTO;
import com.posting.post.dto.response.TokenRefreshResponseDTO;
import com.posting.post.entities.TokenRefresh;
import com.posting.post.entities.User;
import com.posting.post.mapper.RegisterUserMapper;
import com.posting.post.repositories.UserRepository;
import com.posting.post.services.exceptions.ConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {


    private final UserRepository userRepository;
    private final RegisterUserMapper registerUserMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;
    private final TokenRefreshService  tokenRefreshService;

    public AuthService(UserRepository userRepository,
                        RegisterUserMapper registerUserMapper,
                        AuthenticationManager authenticationManager,
                        TokenConfig tokenConfig,
                        TokenRefreshService tokenRefreshService) {

        this.userRepository = userRepository;
        this.registerUserMapper = registerUserMapper;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
        this.tokenRefreshService = tokenRefreshService;
    }

    public List<String>  loginService(LoginUserRequestDTO body) throws NoSuchAlgorithmException {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(body.email(), body.password());
        List<String> tokens = new ArrayList<>();

        // Regra de negócio
        boolean exists  = userRepository.existsByEmail(body.email());

        // Autenticação do usuário
        Authentication authentication = authenticationManager.authenticate(credentials);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();

        if (body.rememberMe()) {
            TokenRefresh tokenRefresh = tokenRefreshService.createTokenRefresh(user);
            String token = tokenConfig.generateToken(user);
            tokens.add(token);
            tokens.add(tokenRefresh.getToken());
            return tokens;
        }

        String token = tokenConfig.generateToken(user);
        tokens.add(token);
        return tokens;
    }

    public User registerService(RegisterUserRequestDTO body) throws NoSuchAlgorithmException {
        User user =  registerUserMapper.toEntity(body);

        // Regra de negócio
        boolean exists  = userRepository.existsByEmail(user.getEmail());

        if (exists) throw new ConflictException(user.getEmail(), "Email já cadastrado!");

        return userRepository.save(user);
    }
}
