package com.wrc.QueryUs.controller;

import com.wrc.QueryUs.dto.ApiResponse;
import com.wrc.QueryUs.dto.PasswordResetDto;
import com.wrc.QueryUs.service.TokenService;
import com.wrc.QueryUs.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset-password")
@AllArgsConstructor
public class PasswordResetController {
    private final UserService userService;
    private final TokenService tokenService;
    @PostMapping("/token")
    public ResponseEntity<ApiResponse> sendToken(@RequestParam String email){
        userService.resetPasswordToken(email);
        return new ResponseEntity<>(new ApiResponse("Password Reset Token has been sent.",true), HttpStatus.OK);
    }
    @PostMapping("/change")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody PasswordResetDto dto, Errors errors) {
        if (errors.getAllErrors().size() > 0) {
            return new ResponseEntity<>(new ApiResponse(errors.getAllErrors().get(0).getDefaultMessage(), false), HttpStatus.BAD_REQUEST);
        }
        tokenService.confirmPassword(dto);
        return new ResponseEntity<>(new ApiResponse("Password Changed Successfully.",true), HttpStatus.OK);

    }


    }
