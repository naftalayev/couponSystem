package service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import beans.Coupon;
import beans.CouponType;
import facade.CompanyFacade;
import facade.CustomerFacade;
import system.CouponSystemException;

@Path("/customer-service")

public class CustomerService {
	private CustomerFacade customerFacade;

	@Context
	private HttpServletRequest req;
	
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get-all-coupon-customer")
	public List<Coupon> getAllCupon() {
		List<Coupon> allCoupons = new ArrayList<>();
		HttpSession httpSession = req.getSession();

		customerFacade = (CustomerFacade) httpSession.getAttribute("facade");
		allCoupons = customerFacade.getAllCouponsCustomer();

		return allCoupons;

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/purchase-coupon")
	public String purchaseCoupon(Coupon coupon) {

		HttpSession httpSession = req.getSession();

		customerFacade = (CustomerFacade) httpSession.getAttribute("facade");
		try {
			customerFacade.purchaseCoupon(coupon);
		} catch (CouponSystemException e) {
			return e.getMessage();
		}

		return "coupon purchased " + coupon.getTitle();
	}

	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get-all-purchased")
	public List<Coupon> getAllPurchased() {
		List<Coupon> allCoupons = new ArrayList<>();
		HttpSession httpSession = req.getSession();

		customerFacade = (CustomerFacade) httpSession.getAttribute("facade");
		allCoupons = customerFacade.getAllPurchasedCoupons();

		return allCoupons;

	}

	
	@GET
	@Path("/get-all-purchased-coupon-by-type/{coupon-type}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coupon> getAllPurchasedCouponByType(@PathParam("coupon-type") CouponType couponType ) {
		List <Coupon> couponsByType= new ArrayList<>();
		HttpSession httpSession = req.getSession();
		customerFacade = (CustomerFacade) httpSession.getAttribute("facade");
		couponsByType=customerFacade.getAllPurchasedByType(couponType);

		return couponsByType;
	}
	@GET
	@Path("/get-all-purchased-coupon-by-price/{price}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coupon> getAllPurchasedCouponByPrice(@PathParam("price") double price ) {
		List <Coupon> couponsByprice= new ArrayList<>();
		HttpSession httpSession = req.getSession();
		customerFacade = (CustomerFacade) httpSession.getAttribute("facade");
		couponsByprice=customerFacade.getAllPurchasedByPrice(price);
		
		return couponsByprice;
	}

	

}
