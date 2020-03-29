package io.rtx.sales;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api")
public class SalesController {

	@Autowired
	private SalesService service;

	@PostMapping("sales")
	@ApiOperation("Add a new Sales")
	public SalesEntity add(@RequestBody SalesInput sales) {
		return service.add(sales);
	}

	@DeleteMapping("sales/{id}")
	@ApiOperation(value = "Delete an existing Sales by id", notes = "Return 404 if Sales does not exists")
	public ResponseEntity<String> delete(@PathVariable long id) {
		boolean found = service.delete(id);
		if (found) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sales not found: " + id);
		}
	}

	@GetMapping("sales/searchByCountry")
	@ApiOperation("Return all Sales in a given country")
	public List<SalesEntity> findByCountry(@RequestParam String country) {
		return service.findByCountry(country);
	}

	//*************************************** 2.2 end-point ************************************************************
	@GetMapping("sales/searchByProduct")
	@ApiOperation("Return all Sales by product")
	public List<SalesEntity> findByProduct(@RequestParam String product) {
		return service.findByProduct(product);
	}
	//************************************** Modif findByDate **********************************************************

	@GetMapping("sales/searchByDate")
	@ApiOperation("Return all Sales between two given dates")
	public List<SalesEntity> findByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
										@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
		if (end == null) {
			return service.findByDate(start, LocalDate.now());
		}
		return service.findByDate(start, end);
	}

	@GetMapping("sales/{id}")
	@ApiOperation("Return an existing Sales by id")
	public Optional<SalesEntity> getById(@PathVariable long id) {
		return service.findById(id);
	}

	@GetMapping("sales")
	@ApiOperation("Return all Sales")
	public List<SalesEntity> listSales() {
		return service.getAll();
	}

	@GetMapping("country")
	@ApiOperation("Return all Country")
	public List<String> listCountry() {
		return service.listCountry();
	}

	//*************************************** 2.3 end-point ************************************************************
	@GetMapping("product")
	@ApiOperation("Return all product")
	public List<String> listProduct() {
		return service.listProduct();
	}

	//******************************************************************************************************************
	@PutMapping("sales/{id}")
	@ApiOperation("Update an existing Sales by id")
	public Optional<SalesEntity> update(@PathVariable long id, @RequestBody SalesInput sales) {
		return service.update(id, sales);
	}

	//************************************** 2.5 nouvel endpoint *******************************************************
	@PatchMapping("sales/{id}")
	@ApiOperation("Update an partial existing Sales by id")
	public Optional<SalesEntity> partialUpdate(@PathVariable long id, @RequestBody SalesPartialInput sales) {
		return service.partialUpdate(id, sales);
	}
	//************************************** 2.6 nouvel endpoint *******************************************************

	@PutMapping("/api/sales/search)")
	@ApiOperation("Update an existing Sales by id")
	public List<List<SalesEntity>> search(@RequestParam String pays, @RequestParam String product, @RequestBody SalesInput sales, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
										  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

		return service.search(pays, product, start, end);
	}
}
