package com.modakdev.modakflix_auth.api.controllers;

import com.modakdev.modakflix_auth.api.service.impl.ModakFlixAuthServiceImpl;

import com.modakdev.models.entities.ModakFlixUser;

import com.modakdev.models.nonentities.request.auth.ModakFlixAuthenticateRequest;
import com.modakdev.models.nonentities.request.auth.ModakFlixLoginRequest;
import com.modakdev.models.nonentities.request.auth.ModakFlixSignupRequest;
import com.modakdev.models.nonentities.response.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private ModakFlixAuthServiceImpl modakFlixAuthService;


    @PostMapping("authenticate")
    public ModakFlixAuthenticateResponse authenticate(@RequestBody ModakFlixAuthenticateRequest request) {
        ModakFlixAuthenticateResponse response = new ModakFlixAuthenticateResponse();
        ModakFlixUser user = modakFlixAuthService.validateToken(request.getToken());
        if (user != null) {
            response.setMessage("Authentication successful");
            response.setToken(request.getToken());
            response.setStatus(HttpStatus.OK);
            response.setUser(user);
        } else {
            response.setMessage("Invalid token");
            response.setStatus(HttpStatus.FORBIDDEN);
        }
        return response;
    }

    @PostMapping("login")
    public ModakFlixLoginResponse login(@RequestBody ModakFlixLoginRequest modakFlixLoginRequest) {
        String token = modakFlixAuthService.login(modakFlixLoginRequest.getUsername(), modakFlixLoginRequest.getPassword());
        ModakFlixLoginResponse response = new ModakFlixLoginResponse();

        if (token != null) {
            response.setMessage("Login successful");
            response.setStatus(HttpStatus.OK);
            response.setToken(token);
        } else {
            response.setStatus(HttpStatus.FORBIDDEN);
            response.setMessage("Login unsuccessful, username or password didn't match");
        }

        return response;
    }

    @PostMapping("signup")
    public ModakFlixSignupResponse signup(@RequestBody ModakFlixSignupRequest modakFlixSignupRequest) {
        boolean success = modakFlixAuthService.signUp(modakFlixSignupRequest.getUser());
        ModakFlixSignupResponse response = new ModakFlixSignupResponse(modakFlixSignupRequest.getUser());

        if (success) {
            response.setMessage("Signup successful.");
            response.setStatus(HttpStatus.ACCEPTED);
            response.setUser(modakFlixSignupRequest.getUser());
        } else {
            response.setMessage("Signup failed, user already exist.");
            response.setStatus(HttpStatus.NOT_ACCEPTABLE);
            response.setUser(modakFlixSignupRequest.getUser());
        }
        return response;
    }

    @PostMapping("signout")
    public ModakFlixBaseResponse signOut(@RequestBody ModakFlixAuthenticateRequest request) {
        ModakFlixBaseResponse response = new ModakFlixAuthenticateResponse();
        ModakFlixUser user = modakFlixAuthService.validateToken(request.getToken());

        if (user != null) {
            modakFlixAuthService.invalidateToken(request.getToken());
            response.setMessage("Sign out successful");
            response.setStatus(HttpStatus.OK);
        } else {
            response.setMessage("Invalid token");
            response.setStatus(HttpStatus.FORBIDDEN);
        }
        return response;
    }
}
