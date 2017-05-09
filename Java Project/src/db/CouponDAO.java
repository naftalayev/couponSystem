package db;

import java.util.List;

import beans.Coupon;
import beans.CouponType;
import system.CouponSystemException;

public interface CouponDAO {
	
	/**
	 * This method get Coupon object and put it in the Coupon table.
	 * */
	public void createCoupon(Coupon coup)throws CouponSystemException;
	/**
	 * This method get Company id & Coupon id and put them in the Company_Coupon table.
	 * */
	public void createCompany_Coupon(Long Comp_id, Long coup_id)throws CouponSystemException;
	/**
	 * This method get Customer id & Coupon id and put them in the Customer_Coupon table.
	 * */
	public void createCustomer_Coupon(Long Cust_id, Long coup_id)throws CouponSystemException;
	/**
	 * This method get Coupon id and return Coupon from Coupon table as object.
	 * */
	public Coupon readCoupon(Long id)throws CouponSystemException;
	/**
	 * This method get Coupon as object and update the line in the Coupon table (according to id).
	 * */
	public void updateCoupon(Coupon coup)throws CouponSystemException;
	/**
	 * This method get Coupon as object and delete the line in the Coupon table (according to id).
	 * */
	public void deleteCoupon(Coupon coup)throws CouponSystemException;
	/**
	 * This method get Coupon id and delete all the lines in Customer_Coupon table that contains this id.
	 * */
	public void deleteCustomer_Coupon(Long Coupon_id)throws CouponSystemException;
	/**
	 * This method get Coupon id and delete all the lines in Company_Coupon table that contains this id.
	 * */
	public void deleteCompany_Coupon(Long Coupon_id)throws CouponSystemException;
	/**
	 * This method return all coupons from the Coupon table as a list.
	 * */
	public List<Coupon> readAllCoupons()throws CouponSystemException;
	/**
	 * This method get Coupon type and return all coupons of this type from the Coupon table as a list.
	 * */
	public List<Coupon> getCouponByType(CouponType type)throws CouponSystemException;

}
