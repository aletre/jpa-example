package co.com.psl.training.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter @NoArgsConstructor(access=AccessLevel.PROTECTED)
public class InvoiceDetail {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne
	private Invoice invoice;
	
	@ManyToOne
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
		return String.format("InvoiceDetail[invoice=%s, product=%s, quantity=%d, totalTax=%d, totalValue=%d]", invoice.getId(), product.getCode(), quantity, totalTax, totalValue);
	}
}
