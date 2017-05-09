package db;

import java.util.ArrayList;
import java.util.List;

import beans.Coupon;
import beans.Customer;
import system.CouponSystemException;

public interface CustomerDAO {
	
	/**
	 * This method get Customer object and put it in the Customer table.
	 * */
	public void createCustomer(Customer cust)throws CouponSystemException;
	
	/**
	 * This method get Customer id and return Customer as object from Customer table.
	 * */
		public Customer readCustomer(long cust_id)throws CouponSystemException;
		
		/**
		 * This method get Customer object and update the line in the Customer table according to id Customer.
		 * its not update the name! in this case its update the password only.
		 * */
		public void updateCustomer(Customer customer)throws CouponSystemException;
		
		/**
		 * This method get Customer and delete it in the Customer table.
		 * */
		public void deleteCustomer(Customer customer)throws CouponSystemException;
		
		/**
		 * This method return all the Customers from Customer table as a list.
		 * */
		public ArrayList<Customer> getAllCustomers()throws CouponSystemException;
		
		/**
		 * This method get Customer id and return all the Coupons of this Customer from Customer table.
		 * first it's take all the Customer's coupons id from Customer_Coupon table.
		 * */
		public List<Coupon> getCoupons(Long cust_id)throws CouponSystemException;
		
		/**
		 * This method get cust_name and his password. it's check if the name exists in the Customer table,
		 * and if the name's password fitting to the password we got. return true if it's okay, 
		 * else throw exception 
		 * */
		public long login(String customerName, String password)throws CouponSystemException;

		public Customer readByNamePass(String name, String password) throws CouponSystemException;

}
