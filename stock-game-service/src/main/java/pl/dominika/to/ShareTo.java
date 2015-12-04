package pl.dominika.to;

public class ShareTo {

	private String companyName;
	private double price;

	public ShareTo() {

	}

	public ShareTo(String companyName, double price) {
		this.companyName = companyName;
		this.price = price;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((companyName == null) ? 0 : companyName.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		final ShareTo other = (ShareTo) obj;
		if (companyName.equals(other.companyName))
			return true;
		else
			return false;
	}

	public String toString() {
		return companyName + "price= " + price;
	}
}
