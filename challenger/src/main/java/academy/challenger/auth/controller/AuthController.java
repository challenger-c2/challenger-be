package academy.challenger.auth.controller;

import academy.challenger.auth.dto.LoginRequest;
import academy.challenger.auth.dto.RegisterRequest;
import academy.challenger.auth.dto.TokenRefreshRequest;
import academy.challenger.auth.dto.TokenResponse;
import academy.challenger.auth.security.CustomUserDetails;
import academy.challenger.auth.security.LoginUser;
import academy.challenger.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        TokenResponse tokenResponse = authService.login(loginRequest);
        return ResponseEntity.ok(tokenResponse);
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody @Valid TokenRefreshRequest request) {
        TokenResponse tokenResponse = authService.refreshToken(request.refreshToken());
        return ResponseEntity.ok(tokenResponse);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@LoginUser CustomUserDetails userDetails) {
        authService.logout(userDetails.getId());
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/register")
    public ResponseEntity<Long> register(@RequestBody @Valid RegisterRequest registerRequest) {
        Long userId = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userId);
    }
}
