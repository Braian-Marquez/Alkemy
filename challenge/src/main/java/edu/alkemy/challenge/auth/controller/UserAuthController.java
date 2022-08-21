package edu.alkemy.challenge.auth.controller;



import edu.alkemy.challenge.auth.dto.AuthenticationRequest;
import edu.alkemy.challenge.auth.dto.AuthenticationResponse;
import edu.alkemy.challenge.auth.dto.UserDto;
import edu.alkemy.challenge.auth.service.JwtUtil;
import edu.alkemy.challenge.auth.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;



@RestController
@RequestMapping("/auth")
public class UserAuthController {
    @Autowired
    private UserDetailsCustomService userDetailsCustomService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/singUp")
    public ResponseEntity<AuthenticationResponse> singUp(@Valid @RequestBody UserDto user) throws Exception{
        userDetailsCustomService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/singIn")
    public ResponseEntity<AuthenticationResponse> singIn(@RequestBody AuthenticationRequest authRequest)throws Exception{
        UserDetails userDetails;

        try{
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword()));

            userDetails=(UserDetails) auth.getPrincipal();
        }catch (BadCredentialsException e){
            throw new Exception("password o username failed", e);
        }

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }




}
