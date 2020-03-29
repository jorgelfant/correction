package io.rtx.sales;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class SalesPartialInput {

    @Size(min = 2, max = 2)
    private String country;

    @Past
    private LocalDate date;

    @Min(7)
    @Max(7)
    private String product;


    @Positive
    private long profit;

    @Positive
    private long value;


    public String getCountry() {
        return country;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getProduct() {
        return product;
    }

    public Long getProfit() {
        return profit;
    }

    public Long getValue() {
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
