package io.rtx.sales;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Validated
public class SalesInput {
	@NotNull
	@Size(min = 2, max = 2)
	private String country;

	@NotNull
	@Past
	private LocalDate date;

	@NotNull
	@Min(7)
	@Max(7)
	private String product;

	@NotNull
	@Positive
	private Long profit;

	@NotNull
	@Positive
	private Long value;


	public String getCountry() {
		return country;
	}

	public LocalDate getDate() {
		return date;
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

