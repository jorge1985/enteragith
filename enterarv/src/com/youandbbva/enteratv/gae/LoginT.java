package com.youandbbva.enteratv.gae;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.dao.UserDAO;

public class LoginT extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String nombre;

		String thisURL = "/user/home1.html";
//		String thisURLintranet = "http://intranet.bbva.com";
		resp.setContentType("text/html");

		Connection conn = DSManager.getConnection();
		UserDAO userDao = new UserDAO(conn);
//		String cuenta1 = req.getUserPrincipal().getName();
//		System.out.println(cuenta1);
		
		String Nombre = req.getParameter("Nombre");
		String CCorreo = req.getParameter("Correo");
		String IdEmpleado = req.getParameter("Id");
				
		if (req.getUserPrincipal() != null) {
			boolean validar = false;
			String cuenta = req.getUserPrincipal().getName();
			boolean dominio = cuenta.endsWith("bbva.com");

			

			try {
				validar = userDao.validarCorreo(cuenta);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (validar) {
				if (user != null) {
					System.out.println("Welcome, " + cuenta);
				}

				 if (dominio) {
				resp.sendRedirect(thisURL);
				 }
			} else {
				if (user != null) {
					userDao.agregarUsuario(cuenta, "aaaa", CCorreo, IdEmpleado );
					System.out.println("Se agrego la cuenta a la BD: "
							+ cuenta + " Del usuario: "
							+ user.getNickname());
					resp.sendRedirect(thisURL);
				}

			}
		} else {
			
			resp.getWriter().println(
					"<p>Please <a href=\""
							+ userService.createLoginURL("/logint")
							+ "\">sign in</a>.</p>");
			//<meta http-equiv="Refresh" content="5;url=http://www.cristalab.com">
//			resp.sendRedirect(thisURL);
					
		}

	}	
	
}




