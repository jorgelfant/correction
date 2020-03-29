package io.rtx.sales;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SalesEntity {

	private String country;

	private LocalDate date;

	@Id
	@GeneratedValue
	private Long id;

	private String product;

	private long profit;

	private long value;

	public String getCountry() {
		return country;
	}

	public LocalDate getDate() {
		return date;
	}

	public Long getId() {
		return id;
	}

	public String getProduct() {
		return product;
	}

	public long getProfit() {
		return profit;
	}

	public long getValue() {
		return value;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public void setProfit(long profit) {
		this.profit = profit;
	}

	public void setValue(long value) {
		this.value = value;
	}
}
