package co.com.psl.training.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter @NoArgsConstructor(access=AccessLevel.PROTECTED)
public class InvoiceDetail {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private Invoice invoice;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private Product product;
	
	private double quantity;
	
	private double totalValue;
	
	private double totalTax;
	
	public InvoiceDetail(Invoice invoice, Product product, double quantity) {
		this.invoice = invoice;
		this.product = product;
		this.quantity = quantity;
		double previous = quantity * product.getPrice();
		this.totalTax = product.getTax() * previous;
		this.totalValue = previous + this.totalTax;
	}
	
	@Override
	public String toString() {
		return String.format("InvoiceDetail[invoice=%s, product=%s, quantity=%s, totalTax=%s, totalValue=%s]", invoice.getId(), product.getCode(), quantity, totalTax, totalValue);
	}
}
