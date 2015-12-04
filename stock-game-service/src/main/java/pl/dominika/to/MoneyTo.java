package pl.dominika.to;

public class MoneyTo {

	private String currency;
	private double value;

	public MoneyTo(String currency, double value) {
		this.currency = currency;
		this.value = value;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((currency == null) ? 0 : currency.hashCode());
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		final MoneyTo other = (MoneyTo) obj;
		if (currency.equals(other.currency))
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return currency + " " + value;
	}
}
