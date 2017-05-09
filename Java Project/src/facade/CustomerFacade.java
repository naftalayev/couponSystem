package facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import beans.Coupon;
import beans.CouponType;
import beans.Customer;
import db.CompanyDAO;
import db.CompanyDBDAO;
import db.CouponDBDAO;
import db.CustomerDBDAO;
import system.CouponSystemException;

public class CustomerFacade extends ClientFacade {

	private CustomerDBDAO custDAO = new CustomerDBDAO();
	private CouponDBDAO coupDAO = new CouponDBDAO();
	private static Customer currentCustomer = new Customer();

	public static Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public static void setCurrentCustomer(Customer currentCustomer) {
		CustomerFacade.currentCustomer = currentCustomer;
	}

	public CustomerFacade() {
		super();
	}

	/**
	 * This method get Coupon as object, and put it in Customer_Coupon table. in
	 * addition, its add the coupon to currentCustomer`s couponsList.
	 */
	public void purchaseCoupon(Coupon coup) throws CouponSystemException {
		List<Coupon> custCoupons = currentCustomer.getCustCoupons();
		if (custCoupons != null) {
			if (custCoupons.contains(coup)) {
				throw new CouponSystemException("You have already purchased this coupon");
			}
		}
		if (coup.getAmount() > 0 && coup.getEndDate().after(new Date(System.currentTimeMillis()))) {
			coupDAO.createCustomer_Coupon(currentCustomer.getId(), coup.getId());
			custCoupons.add(coup);
			currentCustomer.setCustCoupons(custCoupons);
		} else {
			throw new CouponSystemException("the coupon is not available");
		}
	}
	
	public List<Coupon> getAllCouponsCustomer(){
		List<Coupon> coupons = new ArrayList<>();
		try {
			coupons = coupDAO.readAllCoupons();
		} catch (CouponSystemException e) {
			e.getMessage();
		}

		return coupons;

		
	}

	/**
	 * this method returns all the coupons that relevant for current customer
	 */
	public List<Coupon> getAllPurchasedCoupons() {

		List<Coupon> coupons = new ArrayList<>();

		try {
			coupons = custDAO.getCoupons(currentCustomer.getId());
		} catch (CouponSystemException e) {
			e.getMessage();
		}

		return coupons;

	}

	/**
	 * this method returns a list of coupons that relevant for current customer
	 * filtered by type
	 */
	public List<Coupon> getAllPurchasedByType(CouponType couponType) {
		List<Coupon> coupons = new ArrayList<>();
		/** first, lets get all the coupons of this customer */
		try {
			coupons = custDAO.getCoupons(currentCustomer.getId());
		} catch (CouponSystemException e) {
			e.getMessage();
		}
		/** check every single coupon - selection */
		for (Coupon coupon : coupons) {
			/** if its doest belong to the type - it is out! */
			if (!(coupon.getType() == couponType)) {
				/** all the irrelevant are removed */
				coupons.remove(coupon);

			}

		}

		return coupons;

	}

	/**
	 * this method returns a list of coupons that relevant for current customer
	 * filtered by price
	 */
	public List<Coupon> getAllPurchasedByPrice(double price) {
		List<Coupon> coupons = new ArrayList<>();
		try {
			coupons = custDAO.getCoupons(currentCustomer.getId());
			for (Iterator<Coupon> iterator = coupons.iterator(); iterator.hasNext();) {
				Coupon coupon = iterator.next();
				if ((coupon.getPrice())<price) {
				} else {
					iterator.remove();
				}
			}
		} catch (CouponSystemException e) {
			e.getMessage();
			return null;
		}

		return coupons;

	}

	/**
	 * This method send the name & password to login method in DAO and get the
	 * id customer, its update the local object "currentCustomer" to this
	 * customer.
	 */
	public boolean login(String name, String password) throws CouponSystemException {
		long id = custDAO.login(name, password);
		// if the id is 0, so the login did not work
		if (id == 0) {
			return false;
			// if we got correct id we want to save it in a local company
			// object-"theComany":
		} else {
			currentCustomer = custDAO.readCustomer(id);
			List<Coupon> custCoupons = custDAO.getCoupons(currentCustomer.getId());
			currentCustomer.setCustCoupons(custCoupons);
		}
		return true;
	}

}
