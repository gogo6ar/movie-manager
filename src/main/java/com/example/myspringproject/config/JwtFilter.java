package com.example.myspringproject.config;

import com.example.myspringproject.service.TokenService;
import com.example.myspringproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserService userService;

    @Override
    protected void doFilterInternal( HttpServletRequest request,
                                     HttpServletResponse response,
                                     FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromRequest(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        Map<String, String> userData = tokenService.getUserDataFromToken(token);
        if (userData == null || userData.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String userEmail = userData.get("email");
        String userId = userData.get("id");

        if (userEmail == null || userId == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            request.setAttribute("userId", userId);
            request.setAttribute("username", userEmail);
            UserDetails userDetails = userService.loadUserByUsername(userData.get("email"));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                    "", userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        }
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
