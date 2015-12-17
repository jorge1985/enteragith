package com.youandbbva.enteratv.gae;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
 
public class UserFilter implements Filter{
	 
    private FilterConfig filterConfig = null;
 
    @Override
    public void destroy() {
 
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse)response;
        HttpServletRequest req = (HttpServletRequest)request;
 
        //Obtenemos el servicio de usuarios
        UserService userService = UserServiceFactory.getUserService();
 
        if(userService.getCurrentUser() == null){ //Si el usuario no está logado
            //Capturamos la URL en que nos encontramos
            String currentURL = req.getRequestURL().toString();
            //Generamos URL de login contra Google. Una vez logados, volveremos a la URL actual
            String loginURL = userService.createLoginURL(currentURL);
            //En lugar de ir a donde íbamos, redirigimos a la URL de login
            resp.sendRedirect(loginURL);
            return;
        }
 
        //En caso de estar logado, proseguir de manera normal
        chain.doFilter(request, response);
    }
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
 
}
