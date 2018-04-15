package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import system.CouponSystemException;

@Path("/company-service")
public class CompanyService {

	private CompanyFacade companyFacade;

	@Context
	private HttpServletRequest req;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/create-coupon")
	public String CreateCoupon(Coupon coupon) {

		HttpSession httpSession = req.getSession();

		companyFacade = (CompanyFacade) httpSession.getAttribute("facade");
		try {
			companyFacade.addCoupon(coupon);
		} catch (CouponSystemException e) {
			return e.getMessage();
		}

		return "coupon created" + coupon.getId();
	}

	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/get-all-coupon")
	public List<Coupon> getAllCupon() {
		List<Coupon> allCoupons = new ArrayList<>();
		HttpSession httpSession = req.getSession();

		companyFacade = (CompanyFacade) httpSession.getAttribute("facade");
		allCoupons = companyFacade.getCompanyCoupons();

		return allCoupons;

	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/remove-coupon")
	public String removeCoupon(Coupon coupon) {
		HttpSession httpSession = req.getSession();

		companyFacade = (CompanyFacade) httpSession.getAttribute("facade");
		try {
			companyFacade.deleteCoupon(coupon);
		} catch (CouponSystemException e) {
			return e.getMessage();
		}

		return "coupon " + coupon.getTitle() + " deleted";

	}

	@GET
	@Path("/get-coupon/{coupon-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Coupon getCoupon(@PathParam("coupon-id") long id) {
		Coupon coupon = new Coupon();
		HttpSession httpSession = req.getSession();
		companyFacade = (CompanyFacade) httpSession.getAttribute("facade");
		try {
		coupon = companyFacade.getCoupon(id);
//		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//		String newStartDate = format.format(coupon.getStartDate());
//		String newEndDate = format.format(coupon.getEndDate());
//		coupon.setStartDate(format.parse(newStartDate));
//		coupon.setEndDate(format.parse(newEndDate));
		
		} catch (Exception e) {
			return null;
		}

		return coupon;
	}
	
	@GET
	@Path("/get-coupon-by-type/{coupon-type}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Coupon> getCouponsByType(@PathParam("coupon-type") CouponType couponType ) {
		List <Coupon> couponsByType= new ArrayList<>();
		HttpSession httpSession = req.getSession();
		companyFacade = (CompanyFacade) httpSession.getAttribute("facade");
		couponsByType=companyFacade.getCouponsByType(couponType);

		return couponsByType;
	}
	@PUT
	@Path("/update-coupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCoupon(Coupon coupon) {
		HttpSession httpSession = req.getSession();
		try {
			companyFacade = (CompanyFacade) httpSession.getAttribute("facade");
			companyFacade.updateCoupon(coupon);
		} catch (Exception e) {
			e.getMessage();
		}
		return "coupon updated " + coupon.getTitle();

	}
	
}
