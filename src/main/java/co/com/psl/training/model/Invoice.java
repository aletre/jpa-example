package co.com.psl.training.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter @NoArgsConstructor(access=AccessLevel.PROTECTED)
@Table(indexes = { @Index(name = "idx_cus_id", columnList = "customer_id", unique=false) })
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
