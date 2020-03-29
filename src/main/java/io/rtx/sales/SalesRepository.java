package io.rtx.sales;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SalesRepository extends JpaRepository<SalesEntity, Long> {

	List<SalesEntity> findByCountry(String product);

	//nouvelle fonctionnalité
	List<SalesEntity> findByProduct(String product);

	//nouvelle fonctionnalité
	@Query(value = "SELECT DISTINCT s.product from SalesEntity s")
	List<String> listProduct();

	//nouvelle fonctionnalité
	@Query(value = "SELECT distinct s.country,sum(s.profit)as profit,sum(s.value)as value from SalesEntity s WHERE s.date BETWEEN :startDate AND :endDate group by s.country")
	List<String> findByDateAndCountries(LocalDate startDate, LocalDate endDate);
	/*
	@Query(value = "SELECT s from SalesEntity s WHERE s.country = :country AND s.product = :product AND  s.date BETWEEN :startDate AND :endDate")
	List<List<SalesEntity>> search(String pays, String product, LocalDate start, LocalDate end) ;
    */

	//nouvelle fonctionnalité
	/*
	@Query(value = "SELECT DISTINCT s.product from SalesEntity s")
	QuerydslPredicateExecutor<SalesEntity> search2();
*/
	@Query(value = "SELECT s from SalesEntity s WHERE s.date BETWEEN :startDate AND :endDate")
	List<SalesEntity> findByDateBetween(LocalDate startDate, LocalDate endDate);

	@Query(value = "SELECT DISTINCT s.country from SalesEntity s")
	List<String> listCountry();


}
