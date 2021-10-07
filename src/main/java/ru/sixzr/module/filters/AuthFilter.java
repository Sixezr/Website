package ru.sixzr.module.filters;

import ru.sixzr.module.helpers.Constants;
import ru.sixzr.module.managers.SessionManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/account", "/cart/*"})
public class AuthFilter implements Filter {

    private ServletContext context;
    private SessionManager sessionManager;

    @Override
    public void init(FilterConfig filterConfig) {
        context = filterConfig.getServletContext();
        sessionManager = (SessionManager) context.getAttribute(Constants.sessionManager);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        if (!sessionManager.isAuthenticated(request) && !sessionManager.authenticate(request)) {
            response.sendRedirect(context.getContextPath()+"/login");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
