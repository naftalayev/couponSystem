package tests;

import beans.ClientType;
import beans.Coupon;
import beans.CouponType;
import db.CouponDBDAO;
import facade.CustomerFacade;
import system.CouponSystem;
import system.CouponSystemException;

public class TestCustomer {
	
	public static void main(String[] args) {
		try{
		CouponSystem system= CouponSystem.getInstance();
		CustomerFacade facade= (CustomerFacade)system.login("Elad", "elad1", ClientType.CUSTOMER);
		CouponDBDAO coupDAO= new CouponDBDAO();
		Coupon coup=coupDAO.readCoupon(111L);
		
		facade.purchaseCoupon(coup);
		System.out.println("All my purchased coupons:");
		System.out.println(facade.getAllPurchasedCoupons());
		System.out.println("All my coupons by type:");
		System.out.println(facade.getAllPurchasedByType(CouponType.FOOD));
		System.out.println("All my coupons by price:");
		System.out.println(facade.getAllPurchasedByPrice(50));
		}
		catch(CouponSystemException e){
			System.out.println(e.getMessage());
		}
		
		
	}

}
