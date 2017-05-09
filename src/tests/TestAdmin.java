package tests;

import beans.ClientType;
import facade.AdminFacade;
import system.CouponSystem;
import system.CouponSystemException;

public class TestAdmin {
	
	public static void main(String[] args) {
		try{
		CouponSystem system= CouponSystem.getInstance();
		AdminFacade facade= (AdminFacade)system.login("admin", "1234", ClientType.ADMIN);
//		Company comp= new Company(1233, "Teva", "teva123", "teva@gmail.com");
//		Company comp= new Company(1234, "Osem", "osem444", "osem@gmail.com");
//		Customer cust= new Customer(3235, "Bueni", "beni5");
//		facade.createCompany(comp);
//		facade.createCustomer(cust);
//		facade.deleteCustomer(cust);
//		facade.deleteCompany(comp);
//		facade.updateCompany(comp);
//		facade.updateCustomer(cust);
//		System.out.println(facade.getCompany(123456789L));
//		System.out.println(facade.getCustomer(3233L));
		
		System.out.println(facade.getAllCustomers());
		System.out.println(facade.getAllCompanies());
		system.shutdown();
		}
		catch(CouponSystemException e){
			System.out.println(e.getMessage());
		}
		
	}

}
