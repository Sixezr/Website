package semestrovka.controllers;

import semestrovka.module.entities.UserModel;
import semestrovka.module.exceptions.ValidationException;
import semestrovka.module.helpers.Constants;
import semestrovka.module.services.ISecurityService;
import semestrovka.module.services.IVkService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/vk")
public class VkServlet extends HttpServlet {

    private ServletContext context;
    private IVkService vkService;
    private ISecurityService securityService;

    @Override
    public void init(ServletConfig config) {
        context = config.getServletContext();
        vkService = (IVkService) context.getAttribute(Constants.VK_SERVICE);
        securityService = (ISecurityService) context.getAttribute(Constants.SECURITY_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        if (code == null) {
            resp.sendRedirect("https://oauth.vk.com/authorize?client_id=7981542&display=page&redirect_uri=http://localhost:8080" + context.getContextPath() + "/vk&scope=email&response_type=code&v=5.131");
            return;
        }
        try {
            UserModel user = vkService.getUser(code);
            securityService.signIn(req, user);
            resp.sendRedirect(context.getContextPath() + "/account");
        } catch (ValidationException e) {
            req.setAttribute(Constants.ERROR, e.getMessage());
            context.getRequestDispatcher("/WEB-INF/views/vk.jsp").forward(req, resp);
        }
    }
}
