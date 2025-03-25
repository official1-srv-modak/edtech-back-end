package com.modakdev.modakflix_auth.api.controllers;

import com.modakdev.modakflix_auth.api.service.impl.ModakFlixServerAuthServiceImpl;
import com.modakdev.models.entities.ModakFlixServerUser;
import com.modakdev.models.entities.ModakFlixUser;
import com.modakdev.models.nonentities.request.auth.ModakFlixAuthenticateRequest;
import com.modakdev.models.nonentities.request.auth.ModakFlixLoginRequest;
import com.modakdev.models.nonentities.request.auth.ModakFlixServerSignupRequest;
import com.modakdev.models.nonentities.response.auth.*;
import com.modakdev.models.repo.ModakFlixServerUserRepo;
import com.modakdev.models.repo.ModakFlixUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private ModakFlixUserRepo repo;

    @Autowired
    private ModakFlixServerUserRepo adminRepo;

    @Autowired
    private ModakFlixServerAuthServiceImpl modakFlixServerAuthService;


    @GetMapping("get-all-users")
    public ModakFlixListUsersResponse getAllUsers()
    {
        ModakFlixListUsersResponse response = new ModakFlixListUsersResponse();
        List<ModakFlixUser> users = repo.findAll();
        if(!users.isEmpty())
        {
            response.setStatus(HttpStatus.OK);
            response.setMessage("Successful");
            response.setUsers(users);
        }
        else {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("No users found");
        }

        return response;
    }

    @GetMapping("get-all-admin-users")
    public ModakFlixListServerUsersResponse getAllAdminUsers()
    {
        ModakFlixListServerUsersResponse response = new ModakFlixListServerUsersResponse();
        List<ModakFlixServerUser> users = adminRepo.findAll();
        if(!users.isEmpty())
        {
            response.setStatus(HttpStatus.OK);
            response.setMessage("Successful");
            response.setUsers(users);
        }
        else {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage("No users found");
        }

        return response;
    }

    @PostMapping("signup-admin")
    public ModakFlixServerSignupResponse signupAdmin(@RequestBody ModakFlixServerSignupRequest modakFlixSignupRequest) {
        boolean success = modakFlixServerAuthService.signUpAdmin(modakFlixSignupRequest.getUser());
        ModakFlixServerSignupResponse response = new ModakFlixServerSignupResponse(modakFlixSignupRequest.getUser());

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

    @PostMapping("signup-api-key")
    public ModakFlixServerSignupResponse signupApiKey(@RequestBody ModakFlixServerSignupRequest modakFlixSignupRequest) {
        boolean success = modakFlixServerAuthService.generateApiKey(modakFlixSignupRequest.getUser());
        ModakFlixServerSignupResponse response = new ModakFlixServerSignupResponse(modakFlixSignupRequest.getUser());

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


    @PostMapping("authenticate")
    public ModakFlixServerAuthenticateResponse authenticate(@RequestBody ModakFlixAuthenticateRequest request) {
        ModakFlixServerAuthenticateResponse response = new ModakFlixServerAuthenticateResponse();
        ModakFlixServerUser user = modakFlixServerAuthService.validateToken(request.getToken());
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
        String token = modakFlixServerAuthService.login(modakFlixLoginRequest.getUsername(), modakFlixLoginRequest.getPassword());
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

    @PostMapping("signout")
    public ModakFlixBaseResponse signOut(@RequestBody ModakFlixAuthenticateRequest request) {
        ModakFlixBaseResponse response = new ModakFlixAuthenticateResponse();
        ModakFlixServerUser user = modakFlixServerAuthService.validateToken(request.getToken());

        if (user != null) {
            modakFlixServerAuthService.invalidateToken(request.getToken());
            response.setMessage("Sign out successful");
            response.setStatus(HttpStatus.OK);
        } else {
            response.setMessage("Invalid token");
            response.setStatus(HttpStatus.FORBIDDEN);
        }
        return response;
    }
}
