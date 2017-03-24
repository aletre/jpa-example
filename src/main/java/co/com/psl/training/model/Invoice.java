package co.com.psl.training.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter @NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Invoice {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Customer customer;
	
	@OneToMany(mappedBy="invoice")
	private List<InvoiceDetail> details = new ArrayList<>();
	
	private double totalValue;
	
	private double totalTax;
	
	public Invoice(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return String.format("Invoice[customer=%s, totalValue='%d', totalTax='%d', details=%s]", customer, totalValue, totalTax, details);
    }

	public void addDetail(InvoiceDetail invoiceDetail) {
		details.add(invoiceDetail);
		totalTax += invoiceDetail.getTotalTax();
		totalValue += invoiceDetail.getTotalValue();
	}
}
