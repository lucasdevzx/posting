package com.posting.post.services;

import com.posting.post.config.AuthenticatedUserService;
import com.posting.post.config.TokenConfig;
import com.posting.post.dto.response.TokenRefreshResponseDTO;
import com.posting.post.entities.TokenRefresh;
import com.posting.post.entities.User;
import com.posting.post.enums.TokenRefreshEnums;
import com.posting.post.mapper.TokenRefreshMapper;
import com.posting.post.repositories.TokenRefreshRepository;
import com.posting.post.repositories.UserRepository;
import com.posting.post.services.exceptions.ResourceNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class TokenRefreshService {

    private final TokenRefreshRepository tokenRefreshRepository;
    private final TokenRefreshMapper tokenRefreshMapper;

    private final TokenConfig  tokenConfig;
    private final HashService hashService;

    public TokenRefreshService(TokenRefreshRepository tokenRefreshRepository,
                               TokenRefreshMapper tokenRefreshMapper,
                               TokenConfig tokenConfig,
                               HashService hashService) {

        this.tokenRefreshRepository = tokenRefreshRepository;
        this.tokenRefreshMapper = tokenRefreshMapper;
        this.tokenConfig = tokenConfig;
        this.hashService = hashService;
    }

    public TokenRefreshResponseDTO updateTokensPair(String tokenRefreshHash) throws NoSuchAlgorithmException {
        TokenRefresh tokenRefresh = validateTokenRefresh(tokenRefreshHash);
        updateTokenRefresh(tokenRefresh);
        String newTokenRefreshHash = createTokenRefresh(tokenRefresh.getUser()).getToken();
        String newTokenAcessCripto = tokenConfig.generateToken(tokenRefresh.getUser());
        return tokenRefreshMapper.toDTO(newTokenAcessCripto, newTokenRefreshHash);
    }

    public String generateToken() throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] tokenRefreshRandom = new byte[32];
        random.nextBytes(tokenRefreshRandom);
        byte[] tokenRefreshHash = hashService.hashBytesGenerate(tokenRefreshRandom);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenRefreshHash);
    }

    public TokenRefresh validateTokenRefresh(String tokenRefreshHash) throws NoSuchAlgorithmException {
        TokenRefresh tokenRefreshEntity = findByTokenRefresh(tokenRefreshHash);

        if (!tokenRefreshHash.equals(tokenRefreshEntity.getToken())) throw new IllegalArgumentException("Invalid token refresh");

        if (tokenRefreshEntity.getStatus().equals(TokenRefreshEnums.USED)) {
            throw new  IllegalArgumentException("Token has been used");
            // Revogar tokens futuramente
        }

        LocalDateTime dateTimeNow = LocalDateTime.now();
        if (dateTimeNow.isAfter(tokenRefreshEntity.getExpireAt())) throw new IllegalArgumentException("Invalid token refresh expired");

        return tokenRefreshEntity;
    }

    @Transactional(readOnly = true)
    public TokenRefresh findByTokenRefresh(String tokenRefresh) {
        return tokenRefreshRepository.findByToken(tokenRefresh).orElseThrow(() ->
                new ResourceNotFoundException("Token refresh not found"));
    }

    @Transactional
    public TokenRefresh createTokenRefresh(User user) throws NoSuchAlgorithmException {
        String tokenRefreshHash = generateToken();
        long expireDayDefault = 7;

        TokenRefresh tokenRefresh = new TokenRefresh();
        tokenRefresh.setUser(user);
        tokenRefresh.setToken(tokenRefreshHash);
        tokenRefresh.setExpireAt(LocalDateTime.now().plusDays(expireDayDefault));
        tokenRefresh.setStatus(TokenRefreshEnums.ACTIVE);
        return tokenRefreshRepository.save(tokenRefresh);
    }

    @Transactional
    public void updateTokenRefresh(TokenRefresh tokenRefresh) {
        tokenRefresh.setStatus(TokenRefreshEnums.USED);
        tokenRefreshRepository.save(tokenRefresh);
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *", zone = "America/Sao_Paulo")
    public void deleteTokenRefreshPeriodic() {
        Long limit = 7L;
        LocalDateTime createdAt = LocalDateTime.now().minusDays(limit);
        tokenRefreshRepository.deleteByExpireAtBefore(createdAt);
    }
}