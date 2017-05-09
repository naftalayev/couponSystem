package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Coupon;
import beans.Customer;
import system.CouponSystemException;


public class CustomerDBDAO implements CustomerDAO{
	
	private ConnectionPool pool=ConnectionPool.getInstance();
	private Customer customer= new Customer();
	private CouponDBDAO coupDAO= new CouponDBDAO();

	public CustomerDBDAO() {
		super();
	}
	/**
	 * This method get Customer object and put it in the Customer table.
	 * */
	public void createCustomer(Customer cust)throws CouponSystemException{
	Connection c= pool.getConnection();
			String sql = "INSERT INTO CUSTOMER VALUES (?,?,?)";
			try {
				PreparedStatement stm = c.prepareStatement(sql);
				stm.setLong(1, cust.getId());
				stm.setString(2, cust.getCustName());
				stm.setString(3, cust.getPassword());
				stm.executeUpdate();
			} catch (SQLException e) {

				throw new CouponSystemException("Create opertion is faild: "+ e);
			} finally {
				pool.returnConnection(c);	
			}
		}
	/**
	 * This method get Customer id and return Customer as object from Customer table.
	 * */
//	we need to put "throws"??? its work without that
		public Customer readCustomer(long cust_id) throws CouponSystemException {
			Connection con = pool.getConnection();
			String sql = "SELECT * from CUSTOMER WHERE ID=?";
			try {
				PreparedStatement stm = con.prepareStatement(sql);
				stm.setLong(1, cust_id);
				ResultSet rs = stm.executeQuery();
				
//				if this customer exist in the table so there is next:

				if (rs.next()) {
					customer.setId(cust_id);
					customer.setCustName(rs.getString(2));
					customer.setPassword(rs.getString(3));
				} else {
					pool.returnConnection(con);
					throw new CouponSystemException("Customer with id " + cust_id + " not found");

				}

			} catch (SQLException e) {
				throw new CouponSystemException("Read opertion is faild:", e);
			} finally {
				pool.returnConnection(con);
			}
			return customer;
		}
		/**
		 * This method get Customer object and update the line in the Customer table according to id Customer.
		 * its not update the name! in this case its update the password only.
		 * */
		public void updateCustomer(Customer customer) throws CouponSystemException {
			Connection con = pool.getConnection();
			String sql = "UPDATE CUSTOMER SET PASSWORD = ? WHERE ID = ?";
			try {
				PreparedStatement pst = con.prepareStatement(sql);
//			not possible to change Customer name:
//			pst.setString(1, customer.getCustName());
				pst.setString(1, customer.getPassword());
				pst.setLong(2, customer.getId());
				pst.executeUpdate();
			} catch (Exception e) {
				throw new CouponSystemException("Update operation is faild ", e);
			} finally {
				pool.returnConnection(con);
			}
		}
		/**
		 * This method get Customer and delete it in the Customer table.
		 * */
		public void deleteCustomer(Customer customer) throws CouponSystemException {
			Connection con = pool.getConnection();
			String sql = "DELETE from CUSTOMER WHERE ID = ?";
			try {
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setLong(1, customer.getId());
				pst.executeUpdate();
			} catch (Exception e) {
				throw new CouponSystemException("Delete operation is faild ", e);

			} finally {
				pool.returnConnection(con);
			}

		}
		/**
		 * This method return all the Customers from Customer table as a list.
		 * */
		public ArrayList<Customer> getAllCustomers() throws CouponSystemException {
			ArrayList<Customer> customers = new ArrayList<Customer>();
			Connection con = pool.getConnection();
			String sql = "SELECT * from CUSTOMER";
			try {
				Statement stm = con.createStatement();
				ResultSet rs = stm.executeQuery(sql);

				while (rs.next()) {
					Customer customer= new Customer();
					customer.setId(rs.getLong(1));
					customer.setCustName(rs.getString(2));
					customer.setPassword(rs.getString(3));

					customers.add(customer);
				}

			} catch (SQLException e) {
				throw new CouponSystemException("Table is empty", e);
			} finally {

				pool.returnConnection(con);
			}
			return customers;

		}
		/**
		 * This method get Customer id and return all the Coupons of this Customer from Customer table.
		 * first it's take all the Customer's coupons id from Customer_Coupon table.
		 * */
		public List<Coupon> getCoupons(Long cust_id) throws CouponSystemException {
			Connection con = pool.getConnection();
			List<Coupon> CustCoupons = new ArrayList<>();
			String sql1 = "SELECT Coupon_id FROM Customer_Coupon WHERE Customer_id = ? ";
			try {
				PreparedStatement pst = con.prepareStatement(sql1);
				pst.setLong(1, cust_id);
				ResultSet rs = pst.executeQuery();
//				every Coupon_id we got in ResultSet- we send to readCoupon method that bring me the coupon
//				from Coupon table:
				while (rs.next()) {
					Coupon	coupon = coupDAO.readCoupon(rs.getLong(1));
					CustCoupons.add(coupon);
				}

			} catch (SQLException e) {
				throw new CouponSystemException("id doest exist in the system", e);
			}finally {
				pool.returnConnection(con);
			}

			return CustCoupons;

		}

		/**
		 * This method get cust_name and his password. it's check if the name exists in the Customer table,
		 * and if the name's password fitting to the password we got. return true if it's okay, 
		 * else throw exception 
		 * */
		public long login(String customerName, String password) throws CouponSystemException {
			Connection con = pool.getConnection();
			String sql = "select id, password from Customer where cust_name = ?";
			long id = 0;
			try {
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, customerName);
				ResultSet rs = ps.executeQuery();
				if (!rs.next()) {
					throw new CouponSystemException("The name "+customerName+ " not exist");
				}
//				check if the password we got from the ResultSet equals to the password we got in this method:
					if (rs.getString(2).equals(password)) {
					id= rs.getLong(1);	
					}else{
						throw new CouponSystemException("The password "+password+ " not correct");
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				pool.returnConnection(con);
			}

			return id;
		}
		
		
		public Customer readByNamePass(String name, String password) throws CouponSystemException {
			Connection con = pool.getConnection();
			String sql = "SELECT * from Customer WHERE cust_name= ?";
			Customer customer = new Customer();

			try {
				PreparedStatement stm = con.prepareStatement(sql);
				stm.setString(1, name);
				ResultSet rs = stm.executeQuery();

				/** if this customer exist so there is next */

				if (rs.next()) {
					customer.setId(rs.getLong(1));
					customer.setCustName(rs.getString(2));
					customer.setPassword(rs.getString(3));

				} else {
					throw new RuntimeException("Customer with name " + name + " not found");

				}

			} catch (SQLException e) {
				throw new CouponSystemException("Read opertion is faild", e);
			} finally {

				pool.returnConnection(con);
			}
			return customer;
		}

	}



	