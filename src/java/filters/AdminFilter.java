package filters;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;

/**
 *
 * @author 791662
 */
public class AdminFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
            
        HttpServletRequest hsrequest = (HttpServletRequest)request;
        HttpSession session = hsrequest.getSession();
        
        HttpServletResponse hsresponse = (HttpServletResponse)response;
        
        AccountService as = new AccountService();
        
        String email = (String)session.getAttribute("email");
        String password = (String)session.getAttribute("password");
        
        User user = as.login(email, password);
        
        if(user == null) {
            hsresponse.sendRedirect("login");
            return;
        }
        
        if(user.getRole().getRoleId() != 1) {
            hsresponse.sendRedirect("notes");
            return;
        }
        
        chain.doFilter(request, response);
        
    }

    @Override
    public void destroy() {        
    }

    @Override
    public void init(FilterConfig filterConfig) {        
        
    }
}
