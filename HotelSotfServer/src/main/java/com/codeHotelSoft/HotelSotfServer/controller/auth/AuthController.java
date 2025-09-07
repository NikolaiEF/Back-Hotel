package com.codeHotelSoft.HotelSotfServer.controller.auth;


import com.codeHotelSoft.HotelSotfServer.dto.AuthenticationRequest;
import com.codeHotelSoft.HotelSotfServer.dto.AuthenticationResponse;
import com.codeHotelSoft.HotelSotfServer.dto.SignupRequest;
import com.codeHotelSoft.HotelSotfServer.dto.UserDto;
import com.codeHotelSoft.HotelSotfServer.entity.User;
import com.codeHotelSoft.HotelSotfServer.repository.UserRepository;
import com.codeHotelSoft.HotelSotfServer.services.auth.AuthService;
import com.codeHotelSoft.HotelSotfServer.services.jwt.UserService;
import com.codeHotelSoft.HotelSotfServer.utill.JwtUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
@Repository
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

private final AuthService authService;

private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

private final JwtUtil jwtUtil;

private final UserService userService;

@PostMapping("/signup")
public ResponseEntity<?>  signupUser(@RequestBody SignupRequest signupRequest){
    try {
        UserDto createUser = authService.createUser(signupRequest);
        return new ResponseEntity<>(createUser, HttpStatus.OK);
    }catch (EntityExistsException entityExistsException){
        return new ResponseEntity<>("El usuario ya existe", HttpStatus.NOT_ACCEPTABLE);
    }catch (Exception e){
        return new ResponseEntity<>("El usuario no se creo, vuleve a intentarlo", HttpStatus.BAD_REQUEST);
    }
}
@PostMapping("/login")
   public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
   try{
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
   } catch (BadCredentialsException e) {
       throw new BadCredentialsException("Correo o contraseña incorrecta");
   }

   final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
       Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
       final String jwt = jwtUtil.generateToken(userDetails);

       AuthenticationResponse authenticationResponse =new AuthenticationResponse();
       if(optionalUser.isPresent()){
           authenticationResponse.setJwt(jwt);
           authenticationResponse.setUserRole(optionalUser.get().getUserRole());
           authenticationResponse.setUserId(optionalUser.get().getId());
       }
       return authenticationResponse;

}
}
