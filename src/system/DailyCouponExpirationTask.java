package system;

import java.util.Date;
import java.util.List;

import beans.Coupon;
import db.CompanyDBDAO;
import db.CouponDBDAO;

public class DailyCouponExpirationTask implements Runnable{

	private String name;
	CompanyDBDAO compDAO= new CompanyDBDAO();
	CouponDBDAO coupDAO= new CouponDBDAO();
	
	public DailyCouponExpirationTask() {
		super();
	}

	public DailyCouponExpirationTask(String name) {
		super();
		this.name = name;
	}


	@Override
	public void run() {
		try {
		List<Coupon> allCoupons= coupDAO.readAllCoupons();
		for (Coupon coupon : allCoupons) {
			if(coupon.getEndDate().before(new Date(System.currentTimeMillis()))){
				coupDAO.deleteCompany_Coupon(coupon.getId());
				coupDAO.deleteCustomer_Coupon(coupon.getId());
				coupDAO.deleteCoupon(coupon);
			}
		}
//		Thread task= Thread.currentThread();
			Thread.sleep(1000*60*60*24);
		} catch (Exception e) {
			
		}
		
		
	}
	
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	
	

	
	
}
