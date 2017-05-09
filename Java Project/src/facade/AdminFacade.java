package facade;

import java.util.List;

import beans.Company;
import beans.Coupon;
import beans.Customer;
import db.CompanyDBDAO;
import db.CouponDBDAO;
import db.CustomerDBDAO;
import system.CouponSystemException;

public class AdminFacade extends ClientFacade{
	
	private CompanyDBDAO compDAO= new CompanyDBDAO();
	private CouponDBDAO coupDAO= new CouponDBDAO();
	private CustomerDBDAO custDAO= new CustomerDBDAO();

	public AdminFacade() {
		super();
	}

	/**
	 * This method get Company as object and send it to method that create line in the Company table.
	 * first we check that the name not exist. 
	 * */
	public void createCompany(Company comp)throws CouponSystemException{
		if(compDAO.readAllCompanies()!=null){
		if(compDAO.readAllCompanies().contains(comp)){
			throw new CouponSystemException("The name "+comp.getCompName()+ " is already exist, try different name");
		}}
		compDAO.createCompany(comp);
	}
	/**
	 * This method get Company as object and take all its coupons and delete all the coupons
	 * from Company_Coupon table & Customer_Coupon table & Coupon table. in addition, we delete
	 * the company in the Company table.
	 * */
	public void deleteCompany(Company comp)throws CouponSystemException{
		List<Coupon> coupons=compDAO.getCoupons(comp.getId());
		for (Coupon coupon : coupons) {
			coupDAO.deleteCompany_Coupon(coupon.getId());
		}
		for (Coupon coupon : coupons) {
			coupDAO.deleteCustomer_Coupon(coupon.getId());
		}
		for (Coupon coupon : coupons) {
			coupDAO.deleteCoupon(coupon);
		}
		compDAO.deleteCompany(comp);
		
	}
	/**
	 * This method get Company as object and send it to method that update line in the Company table.
	 * The method don't update the name. 
	 * */
	public void updateCompany(Company comp)throws CouponSystemException{
		compDAO.updateCompany(comp);	
	}

	/**
	 * This method return all the Companies as a list.
	 * */
	public List<Company> getAllCompanies()throws CouponSystemException{
		return compDAO.readAllCompanies();
	}

	/**
	 * This method get company id and return the line from Company table as Company object.
	 * */
	public Company getCompany(Long comp_id)throws CouponSystemException{
		return compDAO.readCompany(comp_id);
	}

	/**
	 * This method get Customer as object and send it to create line in Customer table.
	 * first we check that the name not exist.
	 * */
	public void createCustomer(Customer cust)throws CouponSystemException{
		if(custDAO.getAllCustomers()!=null){
		if(custDAO.getAllCustomers().contains(cust)){
			throw new CouponSystemException("The name "+cust.getCustName()+ " is already exist, try different name");
		}}
		custDAO.createCustomer(cust);
	}
	
	/**
	 * This method get Customer as object and take all its coupons and delete all the lines with those coupons
	 * from Customer_Coupon table and the line from Customer table.
	 * */
	public void deleteCustomer(Customer cust)throws CouponSystemException{
		List<Coupon> coupons=custDAO.getCoupons(cust.getId());
		for (Coupon coupon : coupons) {
			coupDAO.deleteCustomer_Coupon(coupon.getId());
		}
		custDAO.deleteCustomer(cust);
	}
	
	/**
	 * This method get Customer as object and send it to method that update line in the Customer table.
	 * The method don't update the name. 
	 * */
	public void updateCustomer(Customer cust)throws CouponSystemException{
		custDAO.updateCustomer(cust);
	}
	
	/**
	 * This method return all the Customers as a list.
	 * */
	public List<Customer> getAllCustomers()throws CouponSystemException{
		return custDAO.getAllCustomers();
	}

	/**
	 * This method get Customer id and return the line from Customer table as Customer object.
	 * */
	public Customer getCustomer(Long cust_id)throws CouponSystemException{
		return custDAO.readCustomer(cust_id);
	}

	
	


}
