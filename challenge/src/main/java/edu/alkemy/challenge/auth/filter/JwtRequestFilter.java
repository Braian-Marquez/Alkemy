package edu.alkemy.challenge.auth.filter;


import edu.alkemy.challenge.auth.repository.UserRepository;
import edu.alkemy.challenge.auth.service.JwtUtil;
import edu.alkemy.challenge.auth.service.UserDetailsCustomService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {


    private UserDetailsCustomService userDetailsCustomService;
    private UserRepository userRepository;
    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    @Autowired
    public JwtRequestFilter(UserDetailsCustomService userDetailsCustomService, UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userDetailsCustomService = userDetailsCustomService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        final String authotizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if (authotizationHeader != null && authotizationHeader.startsWith("Bearer ")) {
            jwt = authotizationHeader.substring(7);
            username = this.jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsCustomService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authReq =
                        new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword());
                Authentication auth = this.authenticationManager.authenticate(authReq);


            }
        }
        filterChain.doFilter(request, response);

    }
}
