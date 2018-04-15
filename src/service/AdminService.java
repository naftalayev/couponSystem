package service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import beans.Company;
import beans.Customer;
import facade.AdminFacade;
import system.CouponSystemException;

@Path("/admin_service")
public class AdminService {

	private AdminFacade admin;

	@Context
	private HttpServletRequest req;

	public AdminService() {

	}

	// //
	// http://localhost:8080/NHwe/rest/admin_service/createcomp/123/yahoo/yahoo123/yahoo@walla.com
	// @POST
	// @Consumes(MediaType.TEXT_PLAIN)
	// @Produces(MediaType.TEXT_PLAIN)
	// @Path("/createcomp/{comp_id}/{comp_name}/{password}/{email}")
	// public String CreateComp(@PathParam("comp_id") long compId,
	// @PathParam("comp_name") String compName,
	// @PathParam("password") String password, @PathParam("email") String email)
	// throws CouponSystemException {
	// Company company = new Company();
	// company.setId(compId);
	// company.setCompName(compName);
	// company.setPassword(password);
	// company.setEmail(email);
	// HttpSession httpSession = req.getSession();
	//
	// admin = (AdminFacade) httpSession.getAttribute("facade");
	// admin.createCompany(company);
	//
	// return "company created" + compId;
	// }

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/create-company")
	public String createCompany(Company company) {
		System.out.println(company);
		HttpSession httpSession = req.getSession();
		admin = (AdminFacade) httpSession.getAttribute("facade");
		try {
			admin.createCompany(company);
		} catch (CouponSystemException e) {
			return e.getMessage();
		}

		return "company created: " + company.getCompName();

	}

	// http://localhost:8080/NHwe/rest/admin_service/get_company/123
	@GET
	@Path("/get_company/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany(@PathParam("id") long companyId) {
		Company company = new Company();
		HttpSession httpSession = req.getSession();
		try {
			admin = (AdminFacade) httpSession.getAttribute("facade");
			company = admin.getCompany(companyId);
		} catch (Exception e) {
			e.getMessage();
		}
		System.out.println(company);
		return company;

	}

	// localhost:8080/NHwe/rest/admin_service/remove-company
	@PUT
	@Path("/remove-company")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String removeCompany(Company company) {
		HttpSession httpSession = req.getSession();
		try {
			admin = (AdminFacade) httpSession.getAttribute("facade");
			admin.deleteCompany(company);
		} catch (Exception e) {
			e.getMessage();
		}
		return "company deleted " + company.getId();

	}

	// http://localhost:8080/NHwe/rest/admin_service/get-all-companies
	@GET
	@Path("/get-all-companies")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Company> getAllCompanies() {

		HttpSession httpSession = req.getSession();
		List<Company> allCompanies = new ArrayList<Company>();
		try {
			admin = (AdminFacade) httpSession.getAttribute("facade");
			allCompanies = admin.getAllCompanies();
		} catch (Exception e) {
			e.getMessage();
		}
		return allCompanies;
	}

	@PUT
	@Path("/update-company")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCompany(Company company) {
		HttpSession httpSession = req.getSession();
		try {
			admin = (AdminFacade) httpSession.getAttribute("facade");
			admin.updateCompany(company);
		} catch (Exception e) {
			e.getMessage();
		}
		return "company updated " + company.getCompName();

	}

	// http://localhost:8080/NHwe/rest/admin_service/create-customer
	@POST
	@Path("/create-customer")
	@Consumes(MediaType.APPLICATION_JSON)
	public String createCustomer(Customer customer) {

		HttpSession httpSession = req.getSession();
		try {
			admin = (AdminFacade) httpSession.getAttribute("facade");
			admin.createCustomer(customer);
		} catch (Exception e) {
			e.getMessage();
		}
		return "customer created " + customer.getId();

	}

	@PUT
	@Path("remove-customer")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_JSON)
	public String removeCustomer(Customer customer) {

		HttpSession httpSession = req.getSession();
		try {
			admin = (AdminFacade) httpSession.getAttribute("facade");
			admin.deleteCustomer(customer);
		} catch (Exception e) {
			e.getMessage();
		}
		return "customer removed " + customer.getId();

	}

	@PUT
	@Path("/update-customer")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCustomer(Customer customer) {
		HttpSession httpSession = req.getSession();
		try {
			admin = (AdminFacade) httpSession.getAttribute("facade");
			admin.updateCustomer(customer);
		} catch (Exception e) {
			e.getMessage();
		}
		return "customer updated " + customer.getId();

	}

	@GET
	@Path("/get-customer/{company-id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@PathParam("company-id") long id) {

		HttpSession httpSession = req.getSession();
		Customer customer = new Customer();
		try {
			admin = (AdminFacade) httpSession.getAttribute("facade");
			customer = admin.getCustomer(id);
		} catch (Exception e) {
			e.getMessage();
		}
		return customer;
	}

	@GET
	@Path("/get-all-customers")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Customer> getAllCustomers() {

		HttpSession httpSession = req.getSession();
		List<Customer> allCustomers = new ArrayList<Customer>();
		try {
			admin = (AdminFacade) httpSession.getAttribute("facade");
			allCustomers = admin.getAllCustomers();
		} catch (Exception e) {
			e.getMessage();
		}
		return allCustomers;
	}

}
