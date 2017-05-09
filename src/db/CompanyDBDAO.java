package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import beans.Company;
import beans.Coupon;
import system.CouponSystemException;

public class CompanyDBDAO implements CompanyDAO{
	
	private ConnectionPool pool=ConnectionPool.getInstance();
	private CouponDBDAO coupDAO= new CouponDBDAO();

	public CompanyDBDAO() {
		super();
	}
	/**
	 * This method get Company object and put it in the Company table.
	 * */
	public void createCompany(Company comp)throws CouponSystemException{
	Connection c= pool.getConnection();
		String sql = "insert into Company values(?,?,?,?)";
		try {
			PreparedStatement toComp= c.prepareStatement(sql);
			toComp.setLong(1, comp.getId());
			toComp.setString(2, comp.getCompName());
			toComp.setString(3, comp.getPassword());
			toComp.setString(4, comp.getEmail());
			toComp.executeUpdate();
		} catch (SQLException e) {
			throw new CouponSystemException("operation 'create Company' failed "+e);
		}finally {
			pool.returnConnection(c);	
		}
		
	}
	/**
	 * This method get Company id and return Company as object from Company table.
	 * */
	public Company readCompany(Long comp_id)throws CouponSystemException{
		Connection c= pool.getConnection();
		String sql= "select * from Company where id = ?";
		Company comp=new Company();
		try {
			PreparedStatement ps= c.prepareStatement(sql);
			ps.setLong(1, comp_id);
			ResultSet rs= ps.executeQuery();
			if (rs.next()){
				comp.setId(comp_id);;
				comp.setCompName(rs.getString(2));
				comp.setPassword(rs.getString(3));
				comp.setEmail(rs.getString(4));
			}else
				throw new CouponSystemException("A company with id "+comp_id+ " not found");
		} catch (SQLException e) {
			throw new CouponSystemException("operation failed "+e);
		}finally {
			pool.returnConnection(c);			
		}
	return comp;
	}
	/**
	 * This method get Company object and update the line in the Company table according to id Company.
	 * its not update the name!
	 * */
	public void updateCompany(Company comp)throws CouponSystemException{
		Connection c= pool.getConnection();
		String sql= "update Company set password=?, email=? where id=?";
		try {
			PreparedStatement ps= c.prepareStatement(sql);
//			not possible to change company name:
//		ps.setString(1, comp.getCompName());
		ps.setString(1, comp.getPassword());
		ps.setString(2, comp.getEmail());
		ps.setLong(3, comp.getId());
		ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(c);
		}
	}
	/**
	 * This method get Company and delete it in the Company table.
	 * */
	public void deleteCompany(Company comp)throws CouponSystemException{
		Connection c= pool.getConnection();
		String sql="delete from Company where id= ?";
		
		try {
			PreparedStatement st= c.prepareStatement(sql);
			st.setLong(1, comp.getId());
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(c);
		}
		
	}
	/**
	 * This method return all the Companies from Company table as a list.
	 * */
	public List<Company> readAllCompanies()throws CouponSystemException{
		Connection c= pool.getConnection();
		String sql= "select * from Company";
		List<Company> compList= new ArrayList<>();
		try {
			Statement st = c.createStatement();
			ResultSet rs=st.executeQuery(sql);
			while (rs.next()){
				Company comp= new Company();
				comp.setId(rs.getLong(1));
				comp.setCompName(rs.getString(2));
				comp.setPassword(rs.getString(3));
				comp.setEmail(rs.getString(4));
				compList.add(comp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(c);			
		}
		return compList;
	}
	/**
	 * This method get Company id and return all the Coupons of this Company from Coupon table.
	 * first it's take all the Company's coupons id from Company_Coupon table.
	 * */
	public List<Coupon> getCoupons(Long comp_id)throws CouponSystemException{
		Connection c= pool.getConnection();
		List<Coupon> couponsList= new ArrayList<>();
		String sql1= "select Coupon_id from Company_Coupon where Company_id = ?";
		try {
			PreparedStatement ps1= c.prepareStatement(sql1);
			ps1.setLong(1, comp_id);
			ResultSet result= ps1.executeQuery();
			while (result.next()){
				Coupon coup=new Coupon();
				coup=coupDAO.readCoupon(result.getLong(1));
				couponsList.add(coup);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(c);
		}
		return couponsList;
	}
	/**
	 * This method get name and password. it's check if the name exists in the Company table,
	 * and if the name's password fitting to the password we got. return true if it's okay, 
	 * else throw exception 
	 * */
	public long login(String compName, String password)throws CouponSystemException{
		Connection c= pool.getConnection();
		String sql= "select id, password from Company where comp_name = ?";
		long id = 0;
		try {
			PreparedStatement ps= c.prepareStatement(sql);
			ps.setString(1, compName);
			ResultSet rs= ps.executeQuery();
//			check if the password we got from the ResultSet equals to the password we got in this method:
			if (!rs.next()){
				throw new CouponSystemException("The name "+compName+ " not exist");
			}
				if (rs.getString(2).equals(password)){
					id= rs.getLong(1);
				}else{
					throw new CouponSystemException("The password "+password+ " not correct");
				}
			
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			pool.returnConnection(c);			
		}
		return id;
	}
	

	public Company readByNamePass(String compName, String password) throws CouponSystemException {
		Connection con = pool.getConnection();
		String sql = "SELECT * from Company WHERE comp_name= ?";
		Company company = new Company();

		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, compName);

			ResultSet rs = stm.executeQuery();

			/** if this company exist so there is next */

			if (rs.next()) {
				company.setId(rs.getLong(1));
				company.setCompName(rs.getString(2));
				company.setPassword(rs.getString(3));
				company.setEmail(rs.getString(4));

			} else {
				throw new CouponSystemException("Company with name " + compName + " not found");
			}

		} catch (SQLException e) {
			throw new CouponSystemException("Read opertion is faild", e);
		} finally {

			pool.returnConnection(con);
		}
		return company;
	}
	
	
}
