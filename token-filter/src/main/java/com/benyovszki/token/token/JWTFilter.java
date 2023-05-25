package com.benyovszki.token.token;

import java.io.IOException;
import java.util.List;

import com.benyovszki.token.repository.UserToken;
import com.benyovszki.token.repository.UserTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

@AllArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private static final Object CREDENTIALS = null;

    private final JWTUtils jwtUtils;
    private final UserTokenRepository userTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        username = jwtUtils.extractUsername(jwt);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserToken token = userTokenRepository.findById(jwt).orElseThrow();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    token,
                    CREDENTIALS,
                    List.of(new SimpleGrantedAuthority(token.getRole()))
            );
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request,response);
    }
}
