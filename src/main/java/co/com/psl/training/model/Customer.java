package co.com.psl.training.model;

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
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(fetch=FetchType.EAGER)
    private IdType idType;
    
    private String firstName;
    
    private String lastName;
    
    @OneToMany(mappedBy="customer")
    private List<Invoice> invoices;

    public Customer(String firstName, String lastName, IdType idType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.idType = idType;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, idType='%s' firstName='%s', lastName='%s']", id, getIdType().toString(), firstName, lastName);
    }

}