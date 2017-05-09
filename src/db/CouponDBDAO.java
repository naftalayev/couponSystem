package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Coupon;
import beans.CouponType;
import system.CouponSystemException;

public class CouponDBDAO implements CouponDAO{
	
	private ConnectionPool pool=ConnectionPool.getInstance();

	public CouponDBDAO() {
		super();
	}
	/**
	 * This method get Coupon object and put it in the Coupon table.
	 * */
	public void createCoupon(Coupon coup)throws CouponSystemException{
	Connection c= pool.getConnection();
		String sql = "insert into Coupon values(?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps= c.prepareStatement(sql);
			ps.setLong(1, coup.getId());
			ps.setString(2, coup.getTitle());
			ps.setDate(3, new java.sql.Date(coup.getStartDate().getTime()));
			ps.setDate(4, new java.sql.Date(coup.getEndDate().getTime()));
			ps.setInt(5, coup.getAmount());
			ps.setString(6, coup.getType().toString());
			ps.setString(7, coup.getMessage());
			ps.setDouble(8, coup.getPrice());
			ps.setString(9, coup.getImage());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("operation 'create Coupon' failed "+e);
		}finally {
			pool.returnConnection(c);	
		}
	}
	/**
	 * This method get Company id & Coupon id and put them in the Company_Coupon table.
	 * */
	public void createCompany_Coupon(Long Comp_id, Long coup_id)throws CouponSystemException{
		Connection c= pool.getConnection();
		String sql="insert into Company_Coupon values(?,?)";
		
			try {
				PreparedStatement st=c.prepareStatement(sql);
				st.setLong(1, Comp_id);
				st.setLong(2, coup_id);
				st.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				pool.returnConnection(c);
			}
	}
	/**
	 * This method get Customer id & Coupon id and put them in the Customer_Coupon table.
	 * */
	public void createCustomer_Coupon(Long Cust_id, Long coup_id)throws CouponSystemException{
		Connection con = pool.getConnection();
		String sql = "INSERT INTO Customer_Coupon VALUES (?,?)";
		
		try {
				PreparedStatement pst = con.prepareStatement(sql);
				pst.setLong(1, Cust_id);
				pst.setLong(2, coup_id);
				pst.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(con);
		}

	}
	
	/**
	 * This method get Coupon id and return Coupon from Coupon table as object.
	 * */
	public Coupon readCoupon(Long id)throws CouponSystemException{
		Connection c= pool.getConnection();
		String sql= "select * from Coupon where id = ?";
		Coupon coup=new Coupon();
		try {
			PreparedStatement ps= c.prepareStatement(sql);
			ps.setLong(1, id);
			ResultSet rs= ps.executeQuery();
			while(rs.next()){
				coup.setId(id);
				coup.setTitle(rs.getString(2));
				coup.setStartDate(rs.getDate(3));
				coup.setEndDate(rs.getDate(4));
				coup.setAmount(rs.getInt(5));
				coup.setType(CouponType.valueOf(rs.getString(6)));
				coup.setMessage(rs.getString(7));
				coup.setPrice(rs.getDouble(8));
				coup.setImage(rs.getString(9));	
		}} catch (SQLException e) {
			
			throw new CouponSystemException("operation failed "+e);
		}finally {
			pool.returnConnection(c);			
		}
		return coup;
	}
	/**
	 * This method get Coupon as object and update the line in the Coupon table (according to id).
	 * */
	public void updateCoupon(Coupon coup)throws CouponSystemException{
		Connection c= pool.getConnection();
		String sql= "update Coupon set end_date=?, amount=?, price=? where id=?";
		try {
			PreparedStatement ps= c.prepareStatement(sql);
//		ps.setString(1, coup.getTitle());
//		ps.setDate(2, new java.sql.Date(coup.getStartDate().getTime()));
		ps.setDate(1, new java.sql.Date(coup.getEndDate().getTime()));
		ps.setInt(2, coup.getAmount());
//		ps.setString(5, coup.getType().toString());
//		ps.setString(6, coup.getMessage());
		ps.setDouble(3, coup.getPrice());
//		ps.setString(8, coup.getImage());
		ps.setLong(4, coup.getId());
		ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(c);
		}
		
	}
	/**
	 * This method get Coupon as object and delete the line in the Coupon table (according to id).
	 * */
	public void deleteCoupon(Coupon coup)throws CouponSystemException{
		Connection c= pool.getConnection();
		String sql="delete from Coupon where id= ?";
		
		try {
			PreparedStatement st= c.prepareStatement(sql);
			st.setLong(1, coup.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(c);
		}
		
	}
	/**
	 * This method get Coupon id and delete all the lines in Customer_Coupon table that contains this id.
	 * */
	public void deleteCustomer_Coupon(Long Coupon_id)throws CouponSystemException{
		Connection c= pool.getConnection();
		String sql="delete from Customer_Coupon where Coupon_id= ?";
		try {
			PreparedStatement st= c.prepareStatement(sql);
			st.setLong(1, Coupon_id);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(c);
		}

	}
	/**
	 * This method get Coupon id and delete all the lines in Company_Coupon table that contains this id.
	 * */
	public void deleteCompany_Coupon(Long Coupon_id)throws CouponSystemException{
		Connection c= pool.getConnection();
		String sql="delete from Company_Coupon where Coupon_id= ?";
		try {
			PreparedStatement st= c.prepareStatement(sql);
			st.setLong(1, Coupon_id);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(c);
		}

	}
	/**
	 * This method return all coupons from the Coupon table as a list.
	 * */
	public List<Coupon> readAllCoupons()throws CouponSystemException{
		Connection c= pool.getConnection();
		String sql= "select * from Coupon";
		List<Coupon> couponList= new ArrayList<>();
		try {
			Statement st = c.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while (rs.next()){
				Coupon coup= new Coupon();
				coup.setId(rs.getLong(1));
				coup.setTitle(rs.getString(2));
				coup.setStartDate(rs.getDate(3));
				coup.setEndDate(rs.getDate(4));
				coup.setAmount(rs.getInt(5));
				coup.setType(CouponType.valueOf(rs.getString(6)));
				coup.setMessage(rs.getString(7));
				coup.setPrice(rs.getDouble(8));
				coup.setImage(rs.getString(9));
				
				couponList.add(coup);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(c);			
		}
		return couponList;
	}
	
	/**
	 * This method get Coupon type and return all coupons of this type from the Coupon table as a list.
	 * */
	public List<Coupon> getCouponByType(CouponType type)throws CouponSystemException{
		Connection c= pool.getConnection();
		String sql= "select * from Coupon where type= ?";
		Coupon coup= new Coupon();
		List<Coupon> couponByTypeList= new ArrayList<>();
		try {
			PreparedStatement ps= c.prepareStatement(sql);
			ps.setString(1, type.toString());
			ResultSet rs= ps.executeQuery();
			int a=0;
			while (rs.next()){
				a++;
				coup.setId(rs.getLong(1));
				coup.setTitle(rs.getString(2));
				coup.setStartDate(rs.getDate(3));
				coup.setEndDate(rs.getDate(4));
				coup.setAmount(rs.getInt(5));
				coup.setType(CouponType.valueOf(rs.getString(6)));
				coup.setMessage(rs.getString(7));
				coup.setPrice(rs.getDouble(8));
				coup.setImage(rs.getString(9));
				
				couponByTypeList.add(coup);
			
			}
			if(a==0){
				throw new CouponSystemException("Coupon from the type "+type+" not found");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(c);			
		}
		return couponByTypeList;
		
	}

}
