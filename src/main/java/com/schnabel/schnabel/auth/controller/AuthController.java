package com.schnabel.schnabel.auth.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.schnabel.schnabel.auth.dto.LoginRequest;
import com.schnabel.schnabel.auth.dto.SignupRequest;
import com.schnabel.schnabel.auth.model.RefreshToken;
import com.schnabel.schnabel.auth.service.IRefreshTokenService;
import com.schnabel.schnabel.penalty.service.IPenaltyService;
import com.schnabel.schnabel.security.util.JwtUtils;

import com.schnabel.schnabel.users.model.ERole;
import com.schnabel.schnabel.users.model.Role;
import com.schnabel.schnabel.users.model.UserS;
import com.schnabel.schnabel.users.repository.IRoleRepository;
import com.schnabel.schnabel.users.repository.IUserssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication REST controller
 */
@RestController
@RequestMapping("api/auth")
public class AuthController {
    
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final IRefreshTokenService refreshTokenService;
    private final IPenaltyService penaltyService;
    private final IUserssRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, IRefreshTokenService refreshTokenService, IPenaltyService penaltyService, IUserssRepository userssRepository, IRoleRepository roleRepository, PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.penaltyService = penaltyService;
        this.userRepository = userssRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @GetMapping("refresh")
    public ResponseEntity<String> refresh(@CookieValue(value = "email", required = true, defaultValue = "") String token, @RequestHeader("Authorization") String oldJws) {
        if (token.isBlank() || !refreshTokenService.validate(token)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(jwtUtils.regenerateJws(jwtUtils.parseJwtFromAuthorizationHeader(oldJws)));
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginRequest dto, HttpServletResponse response) {
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jws = jwtUtils.generateJws(auth);

        /*Optional<RefreshToken> refreshToken = refreshTokenService.findByEmail(dto.getEmail());
        if (!refreshToken.isPresent() || !refreshTokenService.validate(refreshToken.get().getToken())) {
            return ResponseEntity.badRequest().build();
        }

        penaltyService.generatePenalties(dto.getEmail());

        Cookie cookie = new Cookie("email", refreshToken.get().getToken());
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);*/

        return ResponseEntity.ok(jws);
    }

    @GetMapping("role")
    public ResponseEntity<String> getRole(@RequestHeader("Authorization") String auth) {
        return ResponseEntity.ok(jwtUtils.getRoleFromJws(jwtUtils.parseJwtFromAuthorizationHeader(auth)));
    }

    /*@PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJws(authentication);
        SchnabelUserDetails userDetails = (SchnabelUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new Jwt)
    }*/

    @PostMapping("singup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }
        UserS user = new UserS(signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_PATIENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "pharmacist":
                        Role pharmacistRole = roleRepository.findByName(ERole.ROLE_PHARMACIST)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(pharmacistRole);

                        break;
                    case "dermatologist":
                        Role dermatologistRole = roleRepository.findByName(ERole.ROLE_DERMATOLOGIST)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(dermatologistRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_PATIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }
}
