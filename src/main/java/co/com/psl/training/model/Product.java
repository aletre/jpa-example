package co.com.psl.training.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = { @Index(name = "idx_prod_code", unique = true, columnList = "code") })
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String code;

	private String name;

	private float tax;

	private double price;

	@OneToMany(mappedBy="product")
	@Fetch(FetchMode.SUBSELECT)
	private List<InvoiceDetail> invoiceDetails;

	public Product(String code, String name, float tax, double price) {
		this.code = code;
		this.name = name;
		this.tax = tax;
		this.price = price;
	}

	@Override
	public String toString() {
		return String.format("Producto[code=%s, name=%s, tax='%s', price='%s']", code, name, tax, price);
	}
}
