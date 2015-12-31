package com.youandbbva.enteratv.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.youandbbva.enteratv.Constants;
import com.youandbbva.enteratv.DSManager;
import com.youandbbva.enteratv.Utils;
import com.youandbbva.enteratv.dao.SystemDAO;
import com.youandbbva.enteratv.dao.UserDAO;
import com.youandbbva.enteratv.dao.UtilityDAO;
import com.youandbbva.enteratv.domain.UserInfo;

/**
 * Handle all action for System.
 * 		such as add System Options, Search 
 *  
 * @author CJH
 *
 */

@Controller
@RequestMapping("/system/")
@Component("SystemController")
public class SystemController extends com.youandbbva.enteratv.Controller{

	@ExceptionHandler(value = Exception.class)
	public ModelAndView handlerException(HttpServletRequest req) {
		return new ModelAndView("error");
	}
	
	@ExceptionHandler(com.youandbbva.enteratv.beans.ExceptionHandler.class)
	public ModelAndView handleCustomException(ExceptionHandler ex) {
		return new ModelAndView("error");
	}

	@ExceptionHandler(com.youandbbva.enteratv.beans.ExceptionHandler.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ModelAndView handle404Exception(ExceptionHandler ex) {
		return new ModelAndView("error");
	}
	
	/**
	 * Options page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("options.html")
	public ModelAndView options(
			HttpServletRequest req, HttpServletResponse res){

		UserInfo user = session.getUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/adios.html");
		}
		
		return new ModelAndView("options");
	}

	/**
	 * Load option data.
	 * 
	 * @param req
	 * @param res
	 */
	@RequestMapping("loadOptions.html")
	public void loadOptions(
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		try{
			//dao connection
			SystemDAO dao = new SystemDAO();
			
			result = setResponse(result, Constants.OPTION_ONLINE, dao.getOptions(Constants.OPTION_ONLINE));
			result = setResponse(result, Constants.OPTION_ALLOW_IP, dao.getOptions(Constants.OPTION_ALLOW_IP));
			result = setResponse(result, Constants.OPTION_ALLOW_HOST, dao.getOptions(Constants.OPTION_ALLOW_HOST));			
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("SystemController", "loadOptions", e.toString());
			
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
	/**
	 * Save option data.
	 * 
	 * @param online Yes or No
	 * @param ips List of IP. separated by comma.
	 * @param hosts List of Host. separated by comma.
	 * @param req
	 * @param res
	 */
	@RequestMapping("saveOptions.html")
	public void saveOptions(
			@RequestParam(value = "online", required = false) String online,
			@RequestParam(value = "ips", required = false) String ips,
			@RequestParam(value = "hosts", required = false) String hosts,
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));
		
		online = Utils.checkNull(online);
		ips = Utils.checkNull(ips);
		hosts = Utils.checkNull(hosts);

		try{
			conn.setAutoCommit(false);
			//dao connection
			SystemDAO dao = new SystemDAO();
			
			dao.update(Constants.OPTION_ONLINE, online);
			dao.delete(Constants.OPTION_ALLOW_IP);
			dao.delete(Constants.OPTION_ALLOW_HOST);

			if (ips.length()>0){
				String[] ip = ips.split(",");
				for (int i=0; i<ip.length; i++){
					dao.insert(Constants.OPTION_ALLOW_IP, ip[i]);
				}
			}
			
			if (hosts.length()>0){
				String[] host = hosts.split(",");
				for (int i=0; i<host.length; i++){
					dao.insert(Constants.OPTION_ALLOW_HOST, host[i]);
				}
			}

			conn.commit();
			
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("SystemController", "saveOptions", e.toString());

			try{
				conn.rollback();
			}catch (Exception f){}
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
	/**
	 * Users page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("users.html")
	public ModelAndView users(
			HttpServletRequest req, HttpServletResponse res){

		UserInfo user = session.getUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/adios.html");
		}
		
		ModelAndView mv = new ModelAndView("users");
		
		Connection conn = DSManager.getConnection();
		
		try{
			//dao connection
			UtilityDAO codeDao = new UtilityDAO();
			//handle the form submission
			mv.addObject("securitylist", codeDao.getCodeList("chn1", session.getLanguage(req.getSession())));
			mv.addObject("levellist", codeDao.getCodeList("usr1", session.getLanguage(req.getSession())));			
			mv.addObject("direccionlist", codeDao.getList("maindirection"));
			mv.addObject("empresalist", codeDao.getList("company"));
			mv.addObject("ciudadlist", codeDao.getList("city"));		
			
		}catch (Exception e){
			//handle the form submission
			mv.addObject("securitylist", new ArrayList());
			mv.addObject("levellist", new ArrayList());			
			mv.addObject("direccionlist", new ArrayList());
			mv.addObject("empresalist", new ArrayList());
			mv.addObject("ciudadlist", new ArrayList());
			
			log.error("SystemController", "users", e.toString());
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		return mv;
	}

	/**
	 * load Users with DataTable.
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("loadUser.html")
	public void loadUser(
			HttpServletRequest request, HttpServletResponse response){

	    String[] cols = { " UserLocation", "UserRol", "UserName", "UserFirstName", "UserLastName",  "UserEmail", "UserMuser", "userEmployeeNumber" };
	  
	     
	    JSONObject result = new JSONObject();
	    int amount = 10;
	    int start = 0;
	    int col = 7;
	 
	    String dir = "desc";	 
	    String sStart = Utils.checkNull(request.getParameter("start"));
	    String sAmount = Utils.checkNull(request.getParameter("length"));
	    String sCol = Utils.checkNull(request.getParameter("iSortCol_0"));
	    String sdir = Utils.checkNull(request.getParameter("sSortDir_0"));

	    Map<String, String[]> parameters = request.getParameterMap();
	    sdir = parameters.get("order[0][dir]")[0];
	    sCol = parameters.get("order[0][column]")[0];
	    
	    String searchTerm = Utils.checkNull(request.getParameter("search[value]"));
	    String individualSearch = "";
	    
	    if (sStart .length()>0) {
	        start = Integer.parseInt(sStart);
	        if (start < 0)
	            start = 0;
	    }

	    if (sAmount .length()>0) {
	        amount = Integer.parseInt(sAmount);
	        if (amount < 10 || amount > 100)
	            amount = 10;
	    }
	    
	    if (sCol .length()>0 ) {
	        col = Integer.parseInt(sCol);
	        if (col < 0 || col > 7)
	            col = 7;
	    }
	    
	    if (sdir .length()>0 ) {
	        if (!sdir.equals("desc"))
	            dir = "asc";
	    }
	    
	    String colName = cols[col];
	    Long total = (long)0;
	    Long totalAfterFilter = (long)0;
	    
	    Connection conn = DSManager.getConnection();

		try{
			//dao connection
			UserDAO dao = new UserDAO();
			
			String sql = " select count(userId) from user ";
			total = dao.getCount(sql);
			totalAfterFilter = total;
			
			sql = " select UserRol, UserName,"
					+ " UserFirstName, UserLastName, UserEmployeeNumber,"
					+ " UserAccessLevel, UserToken, UserKeyJob, UserKeyDeparment,"
					+ " UserDateOfBirth, UserGender, UserLocation, UserAppoiment,"
					+ " UserEmail, UserAdmisionDate, UserEntered, UserHorary,"
					+ " UserHiererchy, UserStatus, Maindirection_MaindirectionId,"
					+ " UserMuser, City_CityId, Company_CompanyId from user";
			
			String searchSQL = "";
			
			String globeSearch =  " ( UserName like '%"+searchTerm+"%' "
                    + " or UserFirstName like '%"+searchTerm+"%'"
                    + " or UserLastName like '%"+searchTerm+"%'"
                    + " or UserEmail like '%"+searchTerm+"%'"
                    + " or UserMuser like '%"+searchTerm+"%'"
                    + " or UserEmployeeNumber like '%"+searchTerm+"%'"
            		+ " ) " ;
			
	        if(searchTerm.length()>0 && individualSearch.length()>0){
	            searchSQL = " ( " + globeSearch + " and " + individualSearch + " ) ";
	        } else if(individualSearch.length()>0){
	            searchSQL = individualSearch;
	        }else if(searchTerm.length()>0){
	            searchSQL = globeSearch;
	        }
	        
	        if (searchSQL.length()>0)
	        	searchSQL = " where " + searchSQL;
			
	        sql += searchSQL;
	        sql += " order by " + colName + " " + dir;
	        sql += " limit " + start + ", " + amount;	        
			
	        JSONArray array = dao.getContent(sql, session.getLanguage(request.getSession()));
	        
			sql = " select count (UserId) from user ";
			
			if (searchTerm!=""){
				sql += searchSQL;
				totalAfterFilter = dao.getCount(sql);
			}
			// Initialize value.
			result.put("recordsTotal", total);
	        result.put("recordsFiltered", totalAfterFilter);
	        result.put("data", array);
			
		}catch (Exception e){ 
			log.error("SystemController", "loadUser", e.toString());

		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		try{
			response.setContentType("application/json");
	        response.setHeader("Cache-Control", "no-store");
	        response.getWriter().print(result);			
		}catch (Exception ee){}
	}

	/**
	 * Get user data.
	 * 		used in Edit user.
	 * 
	 * @param user_id
	 * @param req
	 * @param res
	 */
	@RequestMapping("getUser.html")
	public void getUser(
			@RequestParam(value = "user_id", required = false) String user_id,
			HttpServletRequest req, HttpServletResponse res){
		
		user_id = Utils.checkNull(user_id);
				
		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){  
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			//dao connection
			UserDAO dao = new UserDAO();
			
			int userId = dao.searchEmployeeNumber(user_id);
			UserInfo item = dao.getUserInfo(userId);
			
			result = setResponse(result, "user", item.toJSONObject());
			result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){
			log.error("SystemController", "getUser", e.toString());
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}
		
		response(res, result);
	}
	
	/**
	 * Update user. (add, edit or delete)
	 * 
	 * @param Type
	 * @param NumberEmpleyoo
	 * @param UseName
	 * @param FirstName
	 * @param LastName
	 * @param Email
	 * @param Password
	 * @param Muser
	 * @param Gender
	 * @param Birthday
	 * @param Keyjob
	 * @param KeyDepartament
	 * @param Admission
	 * @param Location
	 * @param Appoiment
	 * @param Horary
	 * @param Entered
	 * @param Hierarchy
	 * @param Company
	 * @param City
	 * @param Direction
	 * @param Status
	 * @param RolProfile
	 * @param Acceslevel
	 * @param req
	 * @param res
	 */
	@RequestMapping("updateUser.html")
	public void updateUser(
			@RequestParam(value = "type", required = false) String Type,
			@RequestParam(value = "user_id", required = false) String NumberEmpleyoo,
			@RequestParam(value = "user_name", required = false) String UseName,
			@RequestParam(value = "firstname", required = false) String FirstName,
			@RequestParam(value = "lastname", required = false) String LastName,			
			@RequestParam(value = "email", required = false) String Email,
			@RequestParam(value = "password", required = false) String Password,			
			@RequestParam(value = "musuario", required = false) String Muser,
			@RequestParam(value = "gender", required = false) String Gender,
			@RequestParam(value = "birthday", required = false) String Birthday,
			@RequestParam(value = "employee", required = false) String Keyjob,
			@RequestParam(value = "jobarea", required = false) String KeyDepartament,
			@RequestParam(value = "admission", required = false) String Admission,
			@RequestParam(value = "location", required = false) String Location,
			@RequestParam(value = "nombramiento", required = false) String Appoiment,
			@RequestParam(value = "horario", required = false) String Horary,
			@RequestParam(value = "entered", required = false) String Entered,
			@RequestParam(value = "arbol", required = false) String Hierarchy,
			@RequestParam(value = "token", required = false) String Token,						
			@RequestParam(value = "empresa", required = false) String Company,
			@RequestParam(value = "ciudad", required = false) String City,
			@RequestParam(value = "direccion", required = false) String Direction,			
			@RequestParam(value = "active", required = false) String Status,
			@RequestParam(value = "level", required = false) String RolProfile,
			@RequestParam(value = "security_level", required = false) String Acceslevel,
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		Type = Utils.checkNull(Type);
		NumberEmpleyoo = Utils.checkNull(NumberEmpleyoo);
		UseName = Utils.encode(Utils.checkNull(UseName));
		FirstName = Utils.encode(Utils.checkNull(FirstName));
		LastName = Utils.encode(Utils.checkNull(LastName));		
		Email = Utils.checkNull(Email);
		Password = Utils.checkNull(Password);		
		Muser = Utils.checkNull(Muser);
		Gender = Utils.checkNull(Gender);
		Birthday = Utils.checkNull(Birthday);
		Keyjob = Utils.checkNull(Keyjob);
		KeyDepartament = Utils.checkNull(KeyDepartament);
		Admission = Utils.checkNull(Admission);
		Location = Utils.checkNull(Location);
		Appoiment = Utils.checkNull(Appoiment);
		Horary = Utils.checkNull(Horary);
		Entered = Utils.checkNull(Entered);
		Hierarchy = Utils.checkNull(Hierarchy);		
		Token = Utils.checkNull(Token);		
		Company = Utils.checkNull(Company);
		City = Utils.checkNull(City);
		Direction = Utils.checkNull(Direction);		
		Status = Utils.checkNull(Status);		
		RolProfile = Utils.checkNull(RolProfile);
		Acceslevel = Utils.checkNull(Acceslevel);

		try{
			UserInfo user = session.getUserInfo(req.getSession());
			if (user==null){	
				result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}
			//evaluated value Type 
			if (Type.length()==0){
				result.put(Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			if (!Type.equals("add") && !Type.equals("edit") && !Type.equals("delete")){
				result.put(Constants.ERROR_MSG, reg.getMessage("ACT0003", session.getLanguage(req.getSession())));
				throw new Exception(reg.getMessage("ACT0003"));
			}

			conn.setAutoCommit(false);
			
			//dao connection
			UserDAO dao = new UserDAO();
			
			Password = DigestUtils.md5Hex(Password + Constants.PASSWORD_HASH);
			int userId = dao.searchEmployeeNumber(NumberEmpleyoo);
			UserInfo item = dao.getUserInfo(userId);
			//evaluated value Type 
			if (Type.equals("add")){
				if (item!=null){
					result.put(Constants.ERROR_MSG, reg.getMessage("USR0001", session.getLanguage(req.getSession())));
					throw new Exception(reg.getMessage("USR0001"));
				}
				                                            //Towa SAB inicio
				if (dao.isValidUserWithMusuario(Muser, "")){
					result.put(Constants.ERROR_MSG, reg.getMessage("USR0001", session.getLanguage(req.getSession())));
					throw new Exception(reg.getMessage("USR0001"));
				
				}
				                                            
				dao.insert(NumberEmpleyoo, UseName, FirstName, LastName, Email, Password, Status, RolProfile, Acceslevel, Utils.getInt(Direction), Utils.getInt(Company), Utils.getInt(City));
				conn.commit();												
				dao.updateAdditional(NumberEmpleyoo, Gender, Keyjob, Token, KeyDepartament, Birthday, Location, Appoiment, Admission, Muser, Horary, Entered, Hierarchy);
				
			}else if (Type.equals("edit")){
				if (item==null){
					result.put(Constants.ERROR_MSG, reg.getMessage("USR0004", session.getLanguage(req.getSession())));
					throw new Exception(reg.getMessage("USR0004"));
				}
				
				                                           
				if (!dao.isValidUserWithMusuario(Muser, NumberEmpleyoo)){
					result.put(Constants.ERROR_MSG, reg.getMessage("USR0001", session.getLanguage(req.getSession())));
					throw new Exception(reg.getMessage("USR0001"));
				}
				                                            
				dao.update(NumberEmpleyoo, UseName, FirstName, LastName, Email, Status, RolProfile, Acceslevel, Utils.getInt(Direction), Utils.getInt(Company), Utils.getInt(City));
				conn.commit();
				dao.updateAdditional(NumberEmpleyoo, Gender, Keyjob, Token, KeyDepartament, Birthday, Location, Appoiment, Admission, Muser, Horary, Entered, Hierarchy);

			}else if (Type.equals("delete")){
				if (item==null){
					result.put(Constants.ERROR_MSG, reg.getMessage("USR0004", session.getLanguage(req.getSession())));
					throw new Exception(reg.getMessage("USR0004"));
				}
				
				dao.delete(userId);
			}

			conn.commit();
			
			if (Type.equals("edit") && NumberEmpleyoo.equals(user.getUserEmployeeNumber())){
				UserInfo n = dao.getUserInfo(user.getUserId());
				session.setUserInfo(req.getSession(), n);
			}
			
			result.put(Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result.put(Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("SystemController", "updateUser", e.toString());

			try{
				conn.rollback();
			}catch (Exception ex){}
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}

	@RequestMapping("insertUser.html")
	public void insertUser(
			@RequestParam(value = "header", required = false) String header,
			@RequestParam(value = "length", required = false) String length,
			HttpServletRequest req, HttpServletResponse res){

		Connection conn = DSManager.getConnection();

		JSONObject result = new JSONObject();
		result = setResponse(result, Constants.ERROR_CODE, Constants.ACTION_FAILED);
		result = setResponse(result, Constants.ERROR_MSG, reg.getMessage("ACT0002", session.getLanguage(req.getSession())));

		header = Utils.encode(Utils.checkNull(header));
		length = Utils.encode(Utils.checkNull(length));

		try{
			conn.setAutoCommit(false);
			
			//dao connection
			UserDAO dao = new UserDAO();
			
			int user_id=-1, firstname=-1, lastname=-1, username=-1, email=-1, password=-1;
			int newhire=-1, manager=-1, owner=-1, promote=-1, jobgrade=-1;
			int geographical=-1,city=-1, division=-1, sub_division=-1;
			int ciudad=-1, direccion=-1, empresa=-1;
			int sex=-1, employment_code=-1, token=-1, jobarea=-1, birthday=-1, location=-1, nombramiento=-1, admission=-1, musuario=-1;

			
			String[] row_header = header.split(",");
			for (int i=0; i<row_header.length; i++){
				String field = row_header[i].toLowerCase();
				if (field.indexOf("numempleado")>-1 || field.indexOf("employee")>-1){
					user_id=i;
					continue;
				}
				if (field.indexOf("firstname")>-1 || field.indexOf("appaterno")>-1){
					firstname=i;
					continue;
				}
				
				if (field.indexOf("lastname")>-1 || field.indexOf("apmaterno")>-1){
					lastname=i;
					continue;
				}
				
				if (field.indexOf("nombre")>-1 || field.indexOf("a.k.a")>-1){
					username=i;
					continue;
				}
				
				if (field.indexOf("email")>-1 || field.indexOf("e-mail")>-1){
					email=i;
					continue;
				}
				
				if (field.indexOf("password")>-1){
					password=i;
					continue;
				}

				if (field.indexOf("ciudad")>-1){
					ciudad=i;
					continue;
				}

				if (field.indexOf("empresa")>-1){
					empresa=i;
					continue;
				}
				
				if (field.indexOf("dirgeneral")>-1){
					direccion=i;
					continue;
				}

				if (field.indexOf("sexo")>-1){
					sex=i;
					continue;
				}
				if (field.indexOf("clavepuesto")>-1){
					employment_code=i;
					continue;
				}
				if (field.indexOf("token")>-1){
					token=i;
					continue;
				}
				if (field.indexOf("clavedepartamento")>-1){
					jobarea=i;
					continue;
				}
				if (field.indexOf("fechanacimiemto")>-1){
					birthday=i;
					continue;
				}
				if (field.indexOf("ubicacion")>-1){
					location=i;
					continue;
				}
				if (field.indexOf("nombramiento")>-1){
					nombramiento=i;
					continue;
				}
				if (field.indexOf("fechaingresobanco")>-1){
					admission=i;
					continue;
				}
				if (field.indexOf("musuario")>-1){
					musuario=i;
					continue;
				}
				

			}
			
			for (int i=0; i<Utils.getInt(length); i++){
				String data = Utils.checkNull(req.getParameter("data["+i+"][line]")); 
				
				try{
				String[] row = data.split(",");
				if (user_id!=-1){
					String csv_userid = row[user_id];
					csv_userid = Utils.toStringfromCSV(csv_userid);
					
					String csv_firstname = "";
					if (firstname!=-1)
						csv_firstname = row[firstname];
					csv_firstname = Utils.toStringfromCSV(csv_firstname);
					
					String csv_lastname = "";
					if (lastname!=-1)
						csv_lastname = row[lastname];
					csv_lastname = Utils.toStringfromCSV(csv_lastname);
					
					String csv_username = "";
					if (username!=-1)
						csv_username = row[username];
					csv_username = Utils.toStringfromCSV(csv_username);
					
					String csv_email="";
					if (email!=-1)
						csv_email = row[email];
					csv_email = Utils.toStringfromCSV(csv_email);
					
					String csv_password="user";
					if (password!=-1)
						csv_password = row[password];
					csv_password = Utils.toStringfromCSV(csv_password);
					csv_password = DigestUtils.md5Hex(csv_password + Constants.PASSWORD_HASH);
					

					
					int csv_direccion = 0;
					if (direccion!=-1){
						String temp = row[direccion];
						temp = Utils.toStringfromCSV(temp);
						if (temp!=null && temp.length()>0){
							try{
								csv_direccion = Utils.getInt(temp);
							}catch (Exception rrr){}
						}
					}
					
					int csv_empresa = 0;
					if (empresa!=-1){
						String temp = row[empresa];
						temp = Utils.toStringfromCSV(temp);
						if (temp!=null && temp.length()>0){
							try{
								csv_empresa = Utils.getInt(temp);
							}catch (Exception rrr){}
						}
					}
					
					int csv_ciudad = 0;
					if (ciudad!=-1){
						String temp = row[ciudad];
						temp = Utils.toStringfromCSV(temp);
						if (temp!=null && temp.length()>0){
							try{
								csv_ciudad = Utils.getInt(temp);
							}catch (Exception rrr){}
						}
					}
					
					String csv_sex = "M";
					if (sex!=-1){
						String temp = row[sex];
						temp = Utils.toStringfromCSV(temp);
						if (temp!=null && temp.length()>0){
							if (temp.equals("F") || temp.equals("M"))
								csv_sex = temp;
						}
					}
					
					String csv_employment_code = "";
					if (employment_code!=-1){
						String temp = row[employment_code];
						temp = Utils.toStringfromCSV(temp);
						if (temp!=null && temp.length()>0){
							csv_employment_code = temp;
						}
					}
					
					String csv_token = "";
					if (token!=-1){
						String temp = row[token];
						temp = Utils.toStringfromCSV(temp);
						if (temp!=null && temp.length()>0){
							csv_token = temp;
						}
					}

					String csv_jobarea = "";
					if (jobarea!=-1){
						String temp = row[jobarea];
						temp = Utils.toStringfromCSV(temp);
						if (temp!=null && temp.length()>0){
							csv_jobarea = temp;
						}
					}
					
					String csv_birthday = "";
					if (birthday!=-1){
						String temp = row[birthday];
						temp = Utils.toStringfromCSV(temp);
						if (temp!=null && temp.length()>0){
							csv_birthday = temp;
						}
					}
					
					String csv_location = "";
					if (location!=-1){
						String temp = row[location];
						temp = Utils.toStringfromCSV(temp);
						if (temp!=null && temp.length()>0){
							csv_location = temp;
						}
					}
					
					String csv_nombramiento = "";
					if (nombramiento!=-1){
						String temp = row[nombramiento];
						temp = Utils.toStringfromCSV(temp);
						if (temp!=null && temp.length()>0){
							csv_nombramiento = temp;
						}
					}
					
					String csv_admission = "";
					if (admission!=-1){
						String temp = row[admission];
						temp = Utils.toStringfromCSV(temp);
						if (temp!=null && temp.length()>0){
							csv_admission = temp;
						}
					}

					String csv_musuario = "";
					if (musuario!=-1){
						String temp = row[musuario];
						temp = Utils.toStringfromCSV(temp);
						if (temp!=null && temp.length()>0){
							csv_musuario = temp;
						}
					}
					
					String csv_active = "1";
					
					UserInfo u = dao.getUserInfo(Integer.parseInt(csv_userid));
					if (u==null){
						                                    //Towa SAB inicio
						if (dao.isValidUserWithMusuario(csv_musuario, csv_userid)){
							                                //Towa SAB Fin
						} else {
							dao.insert(csv_userid, csv_username, csv_firstname, csv_lastname, csv_email, csv_password, csv_active, "002", "000", csv_direccion, csv_empresa, csv_ciudad);
							conn.commit();
							
							dao.updateAdditional(csv_userid, csv_sex, csv_employment_code, csv_token, csv_jobarea, csv_birthday, csv_location, csv_nombramiento, csv_admission, csv_musuario, "", "", "");
						}
					}
				}
				}catch (Exception nb){
					log.error("AA", nb.toString());
					nb.printStackTrace();
				}
			}
			
			conn.commit();
			
			result.put(Constants.ERROR_CODE, Constants.ACTION_SUCCESS);
			result.put(Constants.ERROR_MSG, reg.getMessage("ACT0001", session.getLanguage(req.getSession())));
			
		}catch (Exception e){ 
			log.error("SystemController", "insertUser", e.toString());

			try{
				conn.rollback();
			}catch (Exception ex){}
		}finally{
			try{
				if (conn!=null)
					conn.close();
			}catch (Exception f){}
		}

		response(res, result);
	}
	
	/**
	 * Global search page.
	 * 
	 * @param req
	 * @param res
	 * @return view
	 */
	@RequestMapping("search.html")
	public ModelAndView search(
			@RequestParam(value = "search", required = false) String search,
			HttpServletRequest req, HttpServletResponse res){

		search = Utils.encode(Utils.checkNull(search));
		UserInfo user = session.getUserInfo(req.getSession());
		if (user==null){
			return new ModelAndView("redirect:/user/adios.html");
		}
		
		ModelAndView mv = new ModelAndView("search");
		//handle the form submission
		mv.addObject("global_search", search);
		
		return mv;
	}
}
