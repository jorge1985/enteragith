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

public class Daotos extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String nombre;

		String thisURL = "/user/home.html";
		String thisURLintranet = "http://intranet.bbva.com";
		resp.setContentType("text/html");

		Connection conn = DSManager.getConnection();
		UserDAO userDao = new UserDAO(conn);

		String Nombre = req.getParameter("Nombre");
		String CCorreo = req.getParameter("Correo");
		String IdEmpleado = req.getParameter("Id");
		
		
		System.out.println("nombre: "+Nombre);
		System.out.println("correo "+CCorreo);
		System.out.println("Id "+IdEmpleado);
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		int Maximo = 0;
		int contador = 0;
		
		String nombre1 = "";
		String apellpat = "";
		String apellmat = "";
		
		
		String segmento = Nombre;
		
		StringTokenizer st = new StringTokenizer(segmento);
		
		Maximo= st.countTokens();
		
		String Nombres[]= new String[Maximo];
		
		while (st.hasMoreTokens())
		{
			Nombres[contador] = (String) st.nextElement();
			System.out.println (Nombres[contador]);
			
			contador= contador + 1;
		}
		
		System.out.println (Nombres[0]);
		
		nombre1 = Nombres[0];
		System.out.println("Nombre : " + nombre1);
				
		if(Nombres[Maximo - 2].equals("DE") || Nombres[Maximo - 2].equals("Y") || Nombres[Maximo - 2].equals("I")  )
		{
			apellpat = Nombres[Maximo - 3];
			System.out.println("Apellido paterno : " + apellpat);
			apellmat = Nombres[Maximo - 2] +" "+ Nombres[Maximo - 1]  ;
			System.out.println("Apellido materno : " + apellmat);
		}
		else
		{
			apellpat = Nombres[Maximo - 2];
			System.out.println("Apellido paterno : " + apellpat);
			apellmat = Nombres[Maximo - 1];
			System.out.println("Apellido materno : " + apellmat);
		}
		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
			boolean validar = false;
			String cuenta = req.getUserPrincipal().getName();
			boolean dominio = cuenta.endsWith("bbva.com");

			

			try {
				userDao.actualizadatos(cuenta, nombre1, apellpat, apellmat);
				validar = userDao.validarCorreo(cuenta);
				if (validar) {
					if (user != null) {
						System.out.println("Welcome, " + nombre1);
					}

					if (dominio) {
						resp.sendRedirect(thisURL);
					}
				} else {
					if (user != null) {
						
						userDao.agregarUsuario(cuenta, nombre1, apellpat, apellmat );
						System.out.println("Se agrego la cuenta a la BD: "
								+ cuenta + " Del usuario: "
								+ user.getNickname());
						
						resp.sendRedirect(thisURL);
					}

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	}

	
	
	
	
	
	
	
	


	
	
}




