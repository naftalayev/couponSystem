package login;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.ClientType;
import facade.AdminFacade;
import facade.ClientFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;
import system.CouponSystem;
import system.CouponSystemException;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   

	@Override
	public void service(HttpServletRequest req, HttpServletResponse  res) throws ServletException, IOException {
//		check the session- security
		HttpSession session= req.getSession(false);
		if(session!=null){
			session.invalidate();
		}
//			send to couponSystem.login
		String name= req.getParameter("username");
		String password= req.getParameter("password");
		ClientType type= ClientType.valueOf(req.getParameter("type"));
		
		try {
			ClientFacade facade= CouponSystem.getInstance().login(name, password, type);
			System.out.println(facade);
			session=req.getSession(true);
			session.setAttribute("facade", facade);
			
			switch (type){
			case ADMIN:
				res.sendRedirect("admin.html");
				break;
			case COMPANY:
				res.sendRedirect("company.html");
				break;
			case CUSTOMER:
				res.sendRedirect("customer.html");
				break;
				
			default:
				break;
			}
			
		} catch (CouponSystemException e) {
			res.sendRedirect("ErrLogin.html");
		}
		
		
	}

	

}




 

       


