package capstone.EpicDelivery.security;

import capstone.EpicDelivery.Users.User;
import capstone.EpicDelivery.Users.UsersService;
import capstone.EpicDelivery.exceptions.NotFoundException;
import capstone.EpicDelivery.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

    @Autowired
    JWTTools jwttools;
    @Autowired
    UsersService usersService;

    private static final String[] PUBLIC_ROUTES = { "/product/**", "/cart/**", "/cart" };
    private static final String[] ADMIN_ROUTES = {
            "/products/*",
            "/user",
            "/user/*"
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (isPublicRoute(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Per favore passa il token nell'authorization header");
        }
        String token = authHeader.substring(7);

        System.out.println("TOKEN -------> " + token);

        try {
            jwttools.verifyToken(token);
            String id = jwttools.extractSubject(token);
            User currentUser = usersService.findById(UUID.fromString(id));

            if (isAdminRoute(request.getServletPath()) && !"ADMIN".equalsIgnoreCase(currentUser.getRole().toString())) {
                throw new UnauthorizedException("Accesso non autorizzato per questa route");
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(currentUser, null,
                    currentUser.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);
        } catch (NotFoundException e) {
            throw new UnauthorizedException("Utente non trovato");
        }
    }


    private boolean isPublicRoute(String servletPath) {
        for (String publicRoute : PUBLIC_ROUTES) {
            if (new AntPathMatcher().match(publicRoute, servletPath)) {
                return true;
            }
        }

        return new AntPathMatcher().match("/product/**", servletPath);
    }


    private boolean isAdminRoute(String servletPath) {
        for (String adminRoute : ADMIN_ROUTES) {
            if (new AntPathMatcher().match(adminRoute, servletPath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        if (new AntPathMatcher().match("/products/**", request.getServletPath())){
            return true;
        }

        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}