package semestrovka.module.filters;

import semestrovka.module.helpers.Constants;
import semestrovka.module.managers.IAuthManager;
import semestrovka.module.managers.AuthManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/account", "/cart/*", "/menu"})
public class AuthFilter implements Filter {

    private ServletContext context;
    private IAuthManager sessionManager;
    private String[] unprotected;

    @Override
    public void init(FilterConfig filterConfig) {
        context = filterConfig.getServletContext();
        sessionManager = (AuthManager) context.getAttribute(Constants.AUTH_MANAGER);
        unprotected = new String[]{context.getContextPath() + "/menu"};
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        boolean flag = true;

        if (sessionManager.isAuthenticated(request)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        for (String element : unprotected) {
            if (request.getRequestURI().equals(element)) {
                sessionManager.authenticate(request);
                flag = !flag;
            }
        }

        if (flag && !sessionManager.authenticate(request)) {
            response.sendRedirect(context.getContextPath() + "/login");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
