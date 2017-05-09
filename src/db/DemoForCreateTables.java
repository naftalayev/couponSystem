package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DemoForCreateTables {
	public static void main(String[] args) {
		
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String url= "jdbc:derby://localhost:1527/dbNH;create=true";
		try (Connection c= DriverManager.getConnection(url)){
			System.out.println("connect to "+ c);
		String sqlCompanyTable= "create table Company(id bigint primary key, comp_name varchar(20), password varchar(20), email varchar(20))";
		String sqlCustomerTable= "create table Customer(id bigint primary key, cust_name varchar(20), password varchar(20))";
		String sqlCouponTable= "create table Coupon(id bigint primary key, title varchar(20), start_date date, end_date date, amount integer, type varchar(20), massage varchar(20), price double, image varchar(20))";
		String sqlCustomerCouponTable= "create table Customer_Coupon(Customer_id bigint, Coupon_id bigint, primary key(Customer_id, Coupon_id))";
		String sqlCompanyCouponTable= "create table Company_Coupon(Company_id bigint, Coupon_id bigint, primary key(Company_id, Coupon_id))";
			
		
		Statement st= c.createStatement();
			st.executeUpdate(sqlCompanyTable);
			st.executeUpdate(sqlCustomerTable);
			st.executeUpdate(sqlCouponTable);
			st.executeUpdate(sqlCustomerCouponTable);
			st.executeUpdate(sqlCompanyCouponTable);
		
	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
