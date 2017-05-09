package tests;

import java.util.Calendar;
import java.util.Date;
import beans.ClientType;
import beans.Coupon;
import beans.CouponType;
import db.CompanyDAO;
import db.CompanyDBDAO;
import db.CouponDAO;
import db.CouponDBDAO;
import facade.CompanyFacade;
import system.CouponSystem;
import system.CouponSystemException;


public class TestCompany {
	

	public static void main(String[] args) {
		try{
	CouponSystem system= CouponSystem.getInstance();
		CompanyFacade facade= (CompanyFacade)system.login("naftalayev's", "12345", ClientType.COMPANY);
	Calendar start= Calendar.getInstance();
	Calendar end= Calendar.getInstance();
	start.set(2016, 10, 28, 0, 0, 0);
	end.set(2017, 11, 20, 0, 0, 0);
	Date startDate= start.getTime();
	Date endDate= end.getTime();
//     Coupon coup3= new Coupon(100, "sasn", startDate, endDate, 10, CouponType.FOOD, "breakfast", 49, "");
//     Coupon coup2= new Coupon(116, "yoga", startDate, endDate, 10, CouponType.SPORTS, "breakfast", 49, "");
//     Coupon coup4= new Coupon(1, "kafa", startDate, endDate, 10, CouponType.FOOD, "breakfast", 49, "");
//     Coupon coup5= new Coupon(145, "iceTea", startDate, endDate, 10, CouponType.FOOD, "breakfast", 49, "");
//		facade.addCoupon(coup5);
//		facade.addCoupon(coup2);
//		facade.addCoupon(coup4);
//		facade.addCoupon(coup3);
//		System.out.println(coup);
//		System.out.println(coup1);
//		System.out.println(facade);
		System.out.println(facade.getCouponsByType(CouponType.FOOD));
		
		CompanyDAO companyDAO= new CompanyDBDAO();
//		CouponDAO couponDAO= new CouponDBDAO();
//	System.out.println(facade.getCouponsByType(CouponType.FOOD));
//		System.out.println(couponDAO.readAllCoupons());
//		couponDAO.createCompany_Coupon(facade.getCurrCompany().getId(), coup5.getId());
//		couponDAO.createCoupon(coup2);
//		couponDAO.createCompany_Coupon(facade.getCurrCompany().getId(), coup2.getId());
//		couponDAO.createCoupon(coup2);
//	
//		System.out.println(companyDAO.getCoupons(facade.getCurrCompany().getId()));
//		for (int i = 0; i < 1000; i++) {
//			System.out.println("end");
//			
//		}
//		
//     System.out.println(facade.getCompanyCoupons());
     
     
 //	facade.updateCoupon(coup);
//	Coupon coup= new Coupon();
//	coup.setId(111);
//		facade.deleteCoupon(coup);
//		System.out.println("All my coupons:");
//		System.out.println(facade.getMyCoupons(1233L));
//		System.out.println("My company details:");
//		System.out.println(facade.getCompany(1233L));
//		System.out.println("My coupons by date:");
//		System.out.println(facade.getMyCouponsByDate(1233L, endDate));
//		System.out.println("My coupons by price:");
//		System.out.println(facade.getMyCouponsByPrice(1233L, 50));
//		System.out.println("My coupons by type:");
//		System.out.println(facade.getMyCouponsByType(1233L, CouponType.FOOD));
		}
	catch(CouponSystemException e){		e.getMessage();
	}
	
}

	}
