package com.example.namnh.controller;

import com.example.namnh.constant.UserConstants;
import com.example.namnh.dto.request.UserLoginRequestDTO;
import com.example.namnh.dto.request.UserRegisterRequestDTO;
import com.example.namnh.dto.response.SimpleResponseDTO;
import com.example.namnh.dto.response.UserResponseDTO;
import com.example.namnh.entity.User;
import com.example.namnh.repository.UserRepository;
import com.example.namnh.utils.DateTimeUtils;
import com.example.namnh.utils.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/v1/user")//without JWT
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpleResponseDTO simpleResponseDTO;

    @PostMapping("/register")
    public ResponseEntity<SimpleResponseDTO> register(@RequestBody UserRegisterRequestDTO userRegisterRequestDTO) {
        try {
            System.out.println(userRegisterRequestDTO);
            User user = ModelMapperUtils.map(userRegisterRequestDTO, User.class);
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

    @GetMapping("/get")
    public ResponseEntity<SimpleResponseDTO> getUserInfoToDisplay(@RequestParam int id) {
        try {
            System.out.println("id: "+id);
            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent()) {
                System.out.println("user not found!");
                simpleResponseDTO = new SimpleResponseDTO(HttpStatus.NOT_FOUND.value(), null);
                return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
            }
            UserResponseDTO userResponseDTO = ModelMapperUtils.map(user.get(), UserResponseDTO.class);
            simpleResponseDTO = new SimpleResponseDTO(HttpStatus.OK.value(), userResponseDTO);
        } catch (Exception e) {
            System.out.println(e);
            simpleResponseDTO = new SimpleResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<SimpleResponseDTO> login(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        try {
            System.out.println(userLoginRequestDTO);
            Optional<User> user = userRepository.findByUserNameAndPassword(userLoginRequestDTO.getUserName(), userLoginRequestDTO.getPassword());
            if (!user.isPresent()) {
                System.out.println("wrong username or password!");
                simpleResponseDTO = new SimpleResponseDTO(HttpStatus.UNAUTHORIZED.value(), null);
                return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
            }
            user.get().setLastLogin(DateTimeUtils.toSimpleDateTime(new Date()));
            userRepository.save(user.get());
            System.out.println("user logged in!");
            simpleResponseDTO = new SimpleResponseDTO(HttpStatus.OK.value(), null);
        } catch (Exception e) {
            System.out.println(e);
            simpleResponseDTO = new SimpleResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/get-all")
    public ResponseEntity<SimpleResponseDTO> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            List<UserResponseDTO> userResponseDTOs = ModelMapperUtils.mapAll(users, UserResponseDTO.class);
            simpleResponseDTO = new SimpleResponseDTO(HttpStatus.OK.value(), userResponseDTOs);
        } catch (Exception e) {
            System.out.println(e);
            simpleResponseDTO = new SimpleResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), null);
        }
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }
}
