package semestrovka.module.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface ISecurityService {
    /**
     * @param contextPath context path of your web-app, you could get it using the getContextPath() method of the ServletContext class
     * @return true, if you have to forward user to jsp, otherwise false
     * @throws IOException if there is problem with redirect
     */
    boolean processGetLoginRequest(HttpServletRequest req, HttpServletResponse resp, String contextPath) throws IOException;

    /**
     * @param contextPath context path of your web-app, you could get it using the getContextPath() method of the ServletContext class
     * @return true, if you have to forward user to jsp, otherwise false
     * @throws IOException if there is problem with redirect
     */
    boolean processLoginRequest(HttpServletRequest req, HttpServletResponse resp, String contextPath) throws IOException;

    /**
     * @param contextPath context path of your web-app, you could get it using the getContextPath() method of the ServletContext class
     * @return true, if you have to forward user to jsp, otherwise false
     * @throws IOException if there is problem with redirect
     */
    boolean processRegisterRequest(HttpServletRequest req, HttpServletResponse resp, String contextPath) throws IOException;
}
