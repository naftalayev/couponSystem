package beans;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Customer {
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((custName == null) ? 0 : custName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (custName == null) {
			if (other.custName != null)
				return false;
		} else if (!custName.equals(other.custName))
			return false;
		return true;
	}
	private long id;
	private String custName;
	private String password;
	private List<Coupon> custCoupons;
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Customer(long id, String custName, String password) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Coupon> getCustCoupons() {
		return custCoupons;
	}
	public void setCustCoupons(List<Coupon> custCoupons) {
		this.custCoupons = custCoupons;
	}
	@Override
	public String toString() {
		return "\n Customer [id=" + id + ", custName=" + custName + ", password=" + password + ", custCoupons="
				+ custCoupons + "]";
	}

	

}
