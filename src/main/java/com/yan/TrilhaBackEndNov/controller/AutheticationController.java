package com.yan.TrilhaBackEndNov.controller;


import com.yan.TrilhaBackEndNov.infra.security.TokenService;
import com.yan.TrilhaBackEndNov.model.user.AuthenticationDTO;
import com.yan.TrilhaBackEndNov.model.user.LoginResponseDTO;
import com.yan.TrilhaBackEndNov.model.user.RegisterDTO;
import com.yan.TrilhaBackEndNov.model.user.User;
import com.yan.TrilhaBackEndNov.repository.userRepository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AutheticationController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AutheticationController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User)auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.userRepository.findByEmail(data.email()) != null) return  ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User user = new User(data.email(), encryptedPassword, data.role());

        this.userRepository.save(user);
        return ResponseEntity.ok().build();
    }

}
