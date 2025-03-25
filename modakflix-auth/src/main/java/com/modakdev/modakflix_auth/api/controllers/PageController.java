package com.modakdev.modakflix_auth.api.controllers;

import com.modakdev.modakflix_auth.api.service.impl.ModakFlixServerAuthServiceImpl;
import com.modakdev.models.entities.ModakFlixServerUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("secure")
public class PageController {


    @Autowired
    private ModakFlixServerAuthServiceImpl modakFlixServerAuthService;

    @GetMapping("signup-api-key-page")
    public ResponseEntity<?> signupApiKeyPageGet(HttpServletRequest request) {
        // Extract username and password from the "Authorization" header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base64Credentials = authHeader.substring(6);
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(decodedBytes);
            String[] values = credentials.split(":", 2);
            String username = values[0];
            String password = values[1];

            // Now call the login method with username and password
            String token = modakFlixServerAuthService.login(username, password);

            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid username or password");
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body("JWT issued. Use this token for future requests.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Authorization header missing or incorrect");
        }
    }



    @PostMapping("signup-api-key-page")
    public String signupApiKeyPagePost(@ModelAttribute ModakFlixServerUser user, Model model) {
        // You can save the user to the database or handle any logic
        // For example:
        // userService.save(user);
        boolean success = modakFlixServerAuthService.generateApiKey(user);

        if(success)
        {
            model.addAttribute("message", "User created successfully!");
        }
        else{
            model.addAttribute("message", "Some error");

        }
        return "api-key-page";  // Return the same page with success message
    }
}
