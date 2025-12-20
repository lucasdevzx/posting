package com.posting.post.resources;

import com.posting.post.config.SecurityConfig;
import com.posting.post.config.TokenConfig;
import com.posting.post.config.UserDetailsImpl;
import com.posting.post.dto.request.LoginUserRequestDTO;
import com.posting.post.dto.request.RegisterUserRequestDTO;
import com.posting.post.dto.response.LoginUserResponseDTO;
import com.posting.post.dto.response.RegisterUserResponseDTO;
import com.posting.post.entities.User;
import com.posting.post.mapper.LoginUserMapper;
import com.posting.post.mapper.RegisterUserMapper;
import com.posting.post.repositories.UserRepository;
import com.posting.post.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    private final UserRepository userRepository;
    private final RegisterUserMapper registerUserMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    public AuthResource(UserRepository userRepository,
                        RegisterUserMapper registerUserMapper,
                        AuthenticationManager authenticationManager,
                        TokenConfig tokenConfig) {

        this.userRepository = userRepository;
        this.registerUserMapper = registerUserMapper;
        this.authenticationManager = authenticationManager;
        this.tokenConfig = tokenConfig;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginUserResponseDTO> login(@RequestBody LoginUserRequestDTO body) {

        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                body.email(),
                body.password());

        Authentication authentication = authenticationManager.authenticate(credentials);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userDetails.getUser();
        String token = tokenConfig.generateToken(user);
        return ResponseEntity.ok().body(new LoginUserResponseDTO(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RegisterUserResponseDTO> register(@RequestBody RegisterUserRequestDTO body) {
        User user =  registerUserMapper.toEntity(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(registerUserMapper.toRegisterUserResponseDTO(userRepository.save(user)));
    }


}
