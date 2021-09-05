package com.example.namnh.controller;

import com.example.namnh.constant.UserConstants;
import com.example.namnh.dto.request.UserLoginRequestDTO;
import com.example.namnh.dto.request.UserRegisterRequestDTO;
import com.example.namnh.dto.response.SimpleResponseDTO;
import com.example.namnh.entity.CustomUserDetails;
import com.example.namnh.entity.User;
import com.example.namnh.jwt.JwtTokenProvider;
import com.example.namnh.repository.UserRepository;
import com.example.namnh.utils.DateTimeUtils;
import com.example.namnh.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping(path = "/v2/user")//with JWT
public class UserControllerV2 {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private SimpleResponseDTO simpleResponseDTO;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<SimpleResponseDTO> register(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO) {
        try {
            System.out.println("register V2: "+userRegisterRequestDTO);
            User user = ModelMapperUtils.map(userRegisterRequestDTO, User.class);
            user.setPassword(passwordEncoder.encode(userRegisterRequestDTO.getPassword()));
            user.setCreatedDate(DateTimeUtils.toSimpleDateTime(new Date()));
            user.setRole(UserConstants.ROLE_USER);
            userRepository.save(user);
            System.out.println("user registered!");
            simpleResponseDTO = new SimpleResponseDTO(HttpStatus.CREATED.value(), null);
        } catch (Exception e) {
            System.out.println(e);
            simpleResponseDTO = new SimpleResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<SimpleResponseDTO> login(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        try {
            System.out.println("login V2: "+userLoginRequestDTO);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLoginRequestDTO.getUserName(),
                            userLoginRequestDTO.getPassword()
                    )
            );
            User user = userRepository.findByUsername(userLoginRequestDTO.getUserName());
            user.setLastLogin(DateTimeUtils.toSimpleDateTime(new Date()));
            userRepository.save(user);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
            simpleResponseDTO = new SimpleResponseDTO(HttpStatus.OK.value(), jwt);
        } catch (Exception e) {
            System.out.println(e);
            simpleResponseDTO = new SimpleResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }

        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }
}
