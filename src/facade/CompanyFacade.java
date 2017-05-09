package facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import beans.Company;
import beans.Coupon;
import beans.CouponType;
import db.CompanyDAO;
import db.CompanyDBDAO;
import db.CouponDAO;
import db.CouponDBDAO;
import system.CouponSystemException;

public class CompanyFacade extends ClientFacade {

	private CompanyDAO compDAO = new CompanyDBDAO();
	private CouponDAO coupDAO = new CouponDBDAO();
	private static Company currCompany = new Company();

	public static Company getCurrCompany() {
		return currCompany;
	}

	public static void setCurrCompany(Company currCompany) {
		CompanyFacade.currCompany = currCompany;
	}

	public CompanyFacade() {
	}

	/**
	 * This method get Coupon as object, first we check that the Coupon title
	 * not exist, than we send the Coupon to create line in the Coupon table &
	 * Company_Coupon table.
	 */
	public void addCoupon(Coupon coup) throws CouponSystemException {

		if ((coupDAO.readAllCoupons()).contains(coup)) {
			throw new CouponSystemException("The title " + coup.getTitle() + " is already exist, try different name");
		} else {
			coupDAO.createCoupon(coup);
			coupDAO.createCompany_Coupon(currCompany.getId(), coup.getId());
		}
	}

	/**
	 * This method get Coupon as object and delete all the Customers who
	 * purchase its in Customer_Coupon table and delete in Company_Coupon table
	 * & Coupon table.
	 */
	public void deleteCoupon(Coupon coup) throws CouponSystemException {
		coupDAO.deleteCustomer_Coupon(coup.getId());
		coupDAO.deleteCompany_Coupon(coup.getId());
		coupDAO.deleteCoupon(coup);

	}

	/**
	 * This method get Coupon as object and update the price & end_Date in
	 * Coupon table.
	 */
	public void updateCoupon(Coupon coup) throws CouponSystemException {
		coupDAO.updateCoupon(coup);

	}

	/** get specific coupon by id */
	public Coupon getCoupon(long coupid) throws CouponSystemException {

		return coupDAO.readCoupon(coupid);
	}

	public List<Coupon> getCompanyCoupons() {

		List<Coupon> coupons = new ArrayList<Coupon>();

		try {
			coupons = compDAO.getCoupons(currCompany.getId());

		} catch (CouponSystemException e) {
			e.getMessage();
		}

		return coupons;

	}

	/** get all company's coupons filtered by type */
	public List<Coupon> getCouponsByType(CouponType couponType) {
		List<Coupon> coupons = new ArrayList<>();
		/** all coupons */
		try {
			coupons = compDAO.getCoupons(currCompany.getId());
			for (Iterator<Coupon> iterator = coupons.iterator(); iterator.hasNext();) {
				Coupon coupon = iterator.next();
				if (coupon.getType().equals(couponType)) {
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

	/** get all company's coupons filtered by price */
	public List<Coupon> getCouponsByPrice(double price) {
		List<Coupon> coupons = new ArrayList<>();
		try {
			coupons = compDAO.getCoupons(currCompany.getId());
			for (Coupon coupon : coupons) {
				/**
				 * if coupon's price higher than current price remove it from
				 * the list
				 */
				if ((coupon.getPrice()) > price) {
					coupons.remove(coupon);

				}
			}
		} catch (CouponSystemException e) {
			e.getMessage();
			return null;
		}

		return coupons;

	}

	/**
	 * get all company's coupons filtered by expiring date
	 */
	public List<Coupon> getCouponsByDate(Date date) {
		List<Coupon> coupons = new ArrayList<>();
		try {
			coupons = compDAO.getCoupons(currCompany.getId());
		} catch (CouponSystemException e) {
			e.getMessage();
		}
		for (Coupon coupon : coupons) {
			/**
			 * if coupon's expiring date after current date remove it from the
			 * list
			 */
			if ((coupon.getEndDate().after(date))) {
				coupons.remove(coupon);
				return coupons;

			}
		}
		return coupons;

	}

	/**
	 * This method send the name & password to login method in DAO and get the
	 * id company, its update the local object "currComany" to this company.
	 */
	public boolean login(String name, String password) throws CouponSystemException {
		long id = compDAO.login(name, password);
		// if the id is 0, so the login did not work
		if (id == 0) {
			return false;
			// if we got correct id we want to save it in a local company
			// object-"currCompany":
		} else {
			currCompany = compDAO.readCompany(id);
		}
		return true;
	}

}
