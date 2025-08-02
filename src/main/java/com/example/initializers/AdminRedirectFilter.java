package com.example.initializers;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AdminRedirectFilter extends OncePerRequestFilter {
    private final AdminInitializationChecker adminInitializationChecker;

    public AdminRedirectFilter(AdminInitializationChecker adminInitializationChecker) {
        this.adminInitializationChecker = adminInitializationChecker;
    }

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        boolean adminExists = adminInitializationChecker.adminExists();
        boolean isInitForm = path.startsWith("/init-admin") || path.startsWith("/init-reboot");
        boolean isStatic = path.startsWith("/css") || path.startsWith("/js") || path.startsWith("/webjars");

        if (!adminExists && !isInitForm && !isStatic) {
            response.sendRedirect("/init-admin");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
