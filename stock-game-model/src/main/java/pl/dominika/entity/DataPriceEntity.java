package pl.dominika.entity;



import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;


@Entity
@Table(name = "DATA_PRICE")
public class DataPriceEntity {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 50)
	private Double price;

	 @Column
	 @Type(type="org.joda.time.contrib.hibernate.PersistentLocalDate")
	    private LocalDate date;

	@ManyToOne(optional=false,cascade=CascadeType.ALL)
	@JoinColumn(name="company_id",nullable=false)
	private CompanyEntity company;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double doublePrice) {
		this.price = doublePrice;
	}
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	public CompanyEntity getCompany() {
		return company;
	}

	public void setCompany(CompanyEntity company) {
		this.company = company;
	}

	public String toString(){
		return "id= "+id+ " price= "+ price +" date= "+ date+ "company= "+ company.getName();
	}
}
