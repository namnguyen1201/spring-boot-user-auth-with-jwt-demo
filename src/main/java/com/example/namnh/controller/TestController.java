package com.example.namnh.controller;

import com.example.namnh.dto.response.SimpleResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/v1")
public class TestController {
    @Autowired
    private SimpleResponseDTO simpleResponseDTO;

    @GetMapping("/test")
    public ResponseEntity<SimpleResponseDTO> test() {
        simpleResponseDTO = new SimpleResponseDTO(HttpStatus.OK.value(), "test");
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/admin-only")
    public ResponseEntity<SimpleResponseDTO> test2() {
        simpleResponseDTO = new SimpleResponseDTO(HttpStatus.OK.value(), "this is admin-only page");
        return new ResponseEntity<>(simpleResponseDTO, HttpStatus.OK);
    }
}
