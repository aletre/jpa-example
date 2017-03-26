package co.com.psl.training;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.LazyInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import co.com.psl.training.model.Customer;
import co.com.psl.training.model.IdType;
import co.com.psl.training.model.Invoice;
import co.com.psl.training.model.InvoiceDetail;
import co.com.psl.training.model.Product;
import co.com.psl.training.repository.CustomerRepository;
import co.com.psl.training.repository.IdTypeRepository;
import co.com.psl.training.repository.InvoiceDetailRepository;
import co.com.psl.training.repository.InvoiceRepository;
import co.com.psl.training.repository.ProductRepository;

/**
 * Aplicación que permite ilustrar el uso de JPA usando springboot.<br>
 * Se hacen pruebas sobre el uso de estrategis de recuperación de información y majeno de índices.
 * 
 * @author aletre
 */
@SpringBootApplication
public class Application {

	/** Manejo de log de información. */
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	/**
	 * Main para inicial la aplicación.
	 * @param args Argumentos.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Bean
	@Order(value=1)
	public CommandLineRunner setUpData(IdTypeRepository idTypeRepository, CustomerRepository customerRepository, ProductRepository productRepository, 
			InvoiceDetailRepository invoiceDetailRepository, InvoiceRepository invoiceRepository) {
		return (args) -> {
			
			IdType cc = idTypeRepository.findOne("CC");
			if (cc != null) {
				log.info("Ya existe el tipo de identificación CC, se omite la creación de la DB.");
				return; // Ya se realizó la configuración de la base de datos.
			}
			
			log.info("Se Crea CC.");
			cc = new IdType("CC", "Cédula Ciudadanía");
			idTypeRepository.save(cc);
			
			log.info("Se Crean algunos clientes con ese tipo de ID.");
			List<Customer> customers = new ArrayList<>();
			customers.add(new Customer("Jack", "Bauer", cc));
			customers.add(new Customer("Chloe", "O'Brian", cc));
			customers.add(new Customer("Kim", "Bauer", cc));
			customers.add(new Customer("David", "Palmer", cc));
			customers.add(new Customer("Michelle", "Dessler", cc));
			customers.add(new Customer("Tonny", "Almeida", cc));
			customers.add(new Customer("Tonny", "Almeida", cc));
			customerRepository.save(customers);
			
			log.info("Se crean 20 productos con un precio aleatorio y 19% de impuesto.");
			List<Product> products = new ArrayList<>();
			for (int i = 0; i < 20; i++) {
				String codigoProducto = String.format("P%02d", i);
				products.add(new Product(codigoProducto, "Producto " + codigoProducto, .19f, new Random().nextInt(10_000 - 200) + 200));
			}
			productRepository.save(products);
			
			log.info("Se crean 50.000 Factuaras con 20 detalles cada una y con una cantidad aleatorio de cada detalle.");
			List<Invoice> invoices = new ArrayList<>();
			List<InvoiceDetail> invoiceDetails = new ArrayList<>();
			int customer = 0;
			for (int i = 0; i < 50000; i++) {
				Invoice invoice = new Invoice(customers.get(customer));
				for (Product product : products) {
					invoice.addDetail(new InvoiceDetail(invoice, product, new Random().nextInt(10 - 1) + 1));
				}
				invoices.add(invoice);
				invoiceDetails.addAll(invoice.getDetails());
				if (++customer == customers.size()) {
					customer = 0;
				}
			}
			
			invoiceRepository.save(invoices);
			invoiceDetailRepository.save(invoiceDetails);
			
			log.info("------------------------Finalización de inicialización de información----------------------------");
		};
	}
	
	@Bean
	@Order(value=2)
	public CommandLineRunner simplyCustomerOperations(CustomerRepository customerRepository) {
		return (args) -> {
			log.info("----------- Operaciones con Customer ----------");
			// fetch all customers
			log.info("Buscando clientes con findAll(): SE USA 'SELECT' fetching.");
			log.info("-------------------------------");
			for (Customer customer : customerRepository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Customer customer = customerRepository.findOne(1L);
			log.info("Cuscando clientes por ID findOne(1L): SE USA 'JOIN' fetching.");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Buscando clientes con un atributo determinado findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			for (Customer bauer : customerRepository.findByLastName("Bauer")) {
				log.info(bauer.toString());
			}
			log.info("----------- Fin operaciones con customer  ----------");
		};
	}
	
	@Bean
	@Order(value = 3)
	public CommandLineRunner invoiceOperations(InvoiceRepository invoiceRepository) {
		return (args) -> {
			log.info("----------- Operaciones con Invoice  ----------");
			Invoice invoice = invoiceRepository.findOne(1L);
			Customer customer = invoice.getCustomer();
			try {
				log.info(customer.toString());
			} catch (LazyInitializationException e) {
				// No se permite acceder a un objeto que tenga que hacer una operación con la BD si la sesión en la que se consultó ya se cerró!
				// por defecto, los métodos de los repositorios cierran de forma automática la sesión con la DB en el momento que retornan la información.
				log.info("Error! no hay sesión para buscar la información de cliente.");
			}

			invoice = invoiceRepository.findCustom(1L);
			customer = invoice.getCustomer();
			log.info(customer.toString());
			List<InvoiceDetail> details = invoice.getDetails();
			log.info("Nro. detalles de la factura: " + details.size());
			
			List<Invoice> invoiceList = invoiceRepository.findInvoiceListByCustomer(1L);
			Invoice anyInvoice = invoiceList.get(0);
			log.info(anyInvoice.getCustomer().toString());
			
			invoiceList = invoiceRepository.findInvoiceListWithCustomer();
			anyInvoice = invoiceList.get(0);
			log.info(anyInvoice.getCustomer().toString());
			
			invoiceList = invoiceRepository.getInvoiceListCustomer();
			anyInvoice = invoiceList.get(0);
			log.info(anyInvoice.getCustomer().toString());
			
			invoiceList = invoiceRepository.getInvoiceListCustomer(10, 20);
			anyInvoice = invoiceList.get(0);
			log.info(anyInvoice.getCustomer().toString());
		};
	}
	
	@Bean
	@Order(value = 4)
	public CommandLineRunner productOperations(ProductRepository productRepository) {
		return (args) -> {
			log.info("----------- Operaciones con Product  ----------");
			Product product = productRepository.findOne(1L);
			log.info(product.toString());
			try {
				log.info(String.valueOf(product.getInvoiceDetails().size()));
			} catch (LazyInitializationException e) {
				// No se permite acceder a un objeto que tenga que hacer una operación con la BD si la sesión en la que se consultó ya se cerró!
				// por defecto, los métodos de los repositorios cierran de forma automática la sesión con la DB en el momento que retornan la información.
				log.info("Error! no hay sesión para buscar la información de cliente.");
			}
			
			product = productRepository.findProductWithInvoiceDetailList("P00");
			log.info(product.toString());
			log.info(String.valueOf(product.getInvoiceDetails().size()));
		};
	}
	
	@Bean
	@Order(value = 5)
	public CommandLineRunner invoiceDetailOperations(InvoiceDetailRepository invoiceDetailRepository) {
		return (args) -> {
			log.info("----------- Operaciones con InvoiceDetail  ----------");
			InvoiceDetail invoiceDetail = invoiceDetailRepository.findOne(1L);
			log.info(invoiceDetail.toString());
			
			invoiceDetail = invoiceDetailRepository.findByInvoiceAndProductCode(1L, "P00");
			log.info(invoiceDetail.toString());
			log.info(invoiceDetail.getProduct().toString());
		};
	}
}