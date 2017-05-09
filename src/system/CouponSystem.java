package system;

import beans.ClientType;
import db.ConnectionPool;
import facade.AdminFacade;
import facade.ClientFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;

public class CouponSystem {
	
	private static CouponSystem instance= new CouponSystem();
	private DailyCouponExpirationTask task= new DailyCouponExpirationTask();
	private Thread thread= new Thread(task);
	
	CompanyFacade compFac= new CompanyFacade();
	CustomerFacade custFac= new CustomerFacade();
	ClientFacade clientFacade=new ClientFacade();
	ClientFacade adminFacade=new AdminFacade();
	ClientFacade compFacade=new CompanyFacade();
	ClientFacade custFacade=new CustomerFacade();
	

	private CouponSystem() {
		super();
		ConnectionPool.getInstance();
		thread.setDaemon(true);
		thread.start();
		System.out.println("system start: Connections are open and the thread begin");
	}


	public static CouponSystem getInstance() {
		return instance;
	}
	
	public ClientFacade login(String name, String password, ClientType client_type)throws CouponSystemException{
		switch(client_type){
		case ADMIN:
			if(name.equals("admin")&&password.equals("1234")){
				return adminFacade;
			}else{
				throw new CouponSystemException("the name or password is wrong");
			}
		case COMPANY:
			if(compFac.login(name, password)){
				return compFacade;
			}
		case CUSTOMER:
			if(custFac.login(name, password)){
				return custFacade;
			}
		default:
			return clientFacade;
}
	}
	
	public void shutdown()throws CouponSystemException{
		ConnectionPool.closeAllConnections();
		System.out.println("system shutdown");
	}
	
	
	
	
}
