package db;

import java.util.List;

import beans.Company;
import beans.Coupon;
import system.CouponSystemException;

public interface CompanyDAO {
	
	/**
	 * This method get Company object and put it in the Company table.
	 * */
	public void createCompany(Company comp)throws CouponSystemException;
	/**
	 * This method get Company id and return Company as object from Company table.
	 * @throws CouponSystemException 
	 * */
	public Company readCompany(Long id) throws CouponSystemException;
	/**
	 * This method get Company object and update the line in the Company table according to id Company.
	 * its not update the name!
	 * @throws CouponSystemException 
	 * */
	public void updateCompany(Company comp) throws CouponSystemException;
	/**
	 * This method get Company and delete it in the Company table.
	 * @throws CouponSystemException 
	 * */
	public void deleteCompany(Company comp) throws CouponSystemException;
	/**
	 * This method return all the Companies from Company table as a list.
	 * */
	public List<Company> readAllCompanies()throws CouponSystemException;
	/**
	 * This method get Company id and return all the Coupons of this Company from Coupon table.
	 * first it's take all the Company's coupons id from Company_Coupon table.
	 * @throws CouponSystemException 
	 * */
	public List<Coupon> getCoupons(Long Comp_id) throws CouponSystemException;
	/**
	 * This method get name and password. it's check if the name exists in the Company table,
	 * and if the name's password fitting to the password we got. return true if it's okay, 
	 * else throw exception 
	 * */
	public long login(String compName, String password)throws CouponSystemException;

	public Company readByNamePass(String compName, String password) throws CouponSystemException;

}
