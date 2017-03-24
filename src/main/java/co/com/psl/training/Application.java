package co.com.psl.training;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import co.com.psl.training.model.Invoice;
import co.com.psl.training.repository.CustomerRepository;
import co.com.psl.training.repository.IdTypeRepository;
import co.com.psl.training.repository.InvoiceDetailRepository;
import co.com.psl.training.repository.InvoiceRepository;
import co.com.psl.training.repository.ProductRepository;

@SpringBootApplication
public class Application {

//	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner demo(IdTypeRepository idTypeRepository, CustomerRepository customerRepository, ProductRepository productRepository, 
			InvoiceDetailRepository invoiceDetailRepository, InvoiceRepository invoiceRepository) {
		return (args) -> {
			Invoice invoice = invoiceRepository.findOne(1L);
			invoice.getCustomer();
			//createCustomers(customerRepository);
		};
	}

//	private void createCustomers(CustomerRepository customerRepository) {
//
//		// fetch all customers
//		log.info("Customers found with findAll():");
//		log.info("-------------------------------");
//		for (Customer customer : customerRepository.findAll()) {
//			log.info(customer.toString());
//		}
//		log.info("");
//
//		// fetch an individual customer by ID
//		Customer customer = customerRepository.findOne(1L);
//		log.info("Customer found with findOne(1L):");
//		log.info("--------------------------------");
//		log.info(customer.toString());
//		log.info("");
//
//		// fetch customers by last name
//		log.info("Customer found with findByLastName('Bauer'):");
//		log.info("--------------------------------------------");
//		for (Customer bauer : customerRepository.findByLastName("Bauer")) {
//			log.info(bauer.toString());
//		}
//		log.info("");
//	}

}