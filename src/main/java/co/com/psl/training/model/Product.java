package co.com.psl.training.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter @NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Product {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	private String code;
	
	private String name;
	
	private float tax;
	
	private double price;

	public Product(String code, String name, float tax, double price) {
		this.code = code;
		this.name = name;
		this.tax = tax;
		this.price = price;
	}
	
	@Override
	public String toString() {
		return String.format("Producto[code=%s, name=%, tax='f%', price='%d']", code, name, tax, price);
	}
}
