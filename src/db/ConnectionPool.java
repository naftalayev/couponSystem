package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import system.CouponSystemException;


public class ConnectionPool {
	
	int size=10;
	private static Set<Connection> connectionList= new HashSet<>();
	
	String url= "jdbc:derby://localhost:1527/dbNH";
	
	private static ConnectionPool instance= new ConnectionPool();
	
	private ConnectionPool(){
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			for (int i = 0; i < 10; i++) {
				try {
					Connection c= DriverManager.getConnection(url);
					connectionList.add(c);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	
	public static ConnectionPool getInstance() {
		return instance;
	}

	
	
	
	public synchronized Connection getConnection()throws CouponSystemException{
		Iterator<Connection> iter= connectionList.iterator();
		if(connectionList.isEmpty()){
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		Connection c=iter.next();
		connectionList.remove(c);
		return c;
	}

	public synchronized void returnConnection(Connection c){
		if(connectionList.size()==size){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		connectionList.add(c);
		notify();
		
	}
	
	public static void closeAllConnections(){
		for (Connection connection : connectionList) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		
	}

		
		
		
}
