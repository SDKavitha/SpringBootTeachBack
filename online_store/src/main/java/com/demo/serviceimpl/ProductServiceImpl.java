package com.demo.serviceimpl;

import java.lang.module.ResolutionException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.OrderDetails;
import com.demo.entities.Product;
import com.demo.entities.Store;
import com.demo.exception.ResourceNotFoundException;
import com.demo.model.ProductDTO;
import com.demo.repositories.OrderRepository;
import com.demo.repositories.ProductRepository;
import com.demo.repositories.StoreRepository;
import com.demo.service.ProductService;
import com.demo.util.Converter;

import ch.qos.logback.core.util.Duration;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
private	ProductRepository productRepository;
	@Autowired
private Converter converter;

	@Autowired
	private StoreRepository storeRepository;
   @Autowired
	private OrderRepository orderRepository;
	
	@Override
	public ProductDTO createProduct(Product product) {
	//Product p=productRepository.save(product);
		return converter.convertToProductDTO(productRepository.save(product));
	}


	@Override
	public ProductDTO updateProduct(int id, Product product){
	//Product p=productRepository.findById(id).get();
	Product p=productRepository.findById(id).orElseThrow(()->
	new ResourceNotFoundException("Product","Id",id)
			);
	  //Optional<Product> p=productRepository.findById(id);
		//try {
	  //if(p!=null)
			//{
			p.setProductName(product.getProductName());
			p.setProductPrice(product.getProductPrice());
			p.setStock(product.getStock());
			
			productRepository.save(p);
			return converter.convertToProductDTO(p);
		//}
		//}
		//catch(Exception e)
//		 {
//		System.out.println("product id not found");
//		}
//		return null;
		
		
       }
		
//		Optional<Product> p=productRepository.findById(id);
//		if(p.isPresent())
//		{
//			Product prod=p.get();
//		}
//		else
//			throw new Exception("product id not found");
		


	@Override
	public String deleteProduct(int id) {
		//Product p=productRepository.findById(id).get();
		Product p=productRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Product","Id",id));
//		try {
//			if(p!=null) {
		productRepository.delete(p);
		return "Product delete successfully";
//		}}
//		catch(Exception e)
//		 {
//		System.out.println("product id not found");
//		}
//		return null;
		
	}


	@Override
	public List<ProductDTO> getAllProduct() {
	List<Product> products=productRepository.findAll();
	
	List<ProductDTO> productDTOS=new ArrayList<>();
	
	for(Product p:products)
	{
	productDTOS.add(converter.convertToProductDTO(p));
	}
		return productDTOS;
	}


	@Override
	public ProductDTO getProductById(int id) throws ResourceNotFoundException {
		Optional<Product> prod=productRepository.findById(id);
		Product p;
		if(prod.isPresent())
		{
			 p=prod.get();
		}
		else {
			throw new ResourceNotFoundException("Product","id",id);
		}
		return converter.convertToProductDTO(p);
	}


	@Override
	public String assignStoreToProduct(int storeId, int pid) {
		//Store store=storeRepository.findById(storeId).get();
		Store store=storeRepository.findById(storeId).orElseThrow(()->
		new ResourceNotFoundException("Store","Id",storeId));
		//Product p=productRepository.findById(pid).get();
		Product p=productRepository.findById(pid).orElseThrow(()->
		new ResourceNotFoundException("Product","Id",pid));
		
//		try {
//			if(store!=null && p!=null) {
		p.setStore(store);
		
		List<Product> products=new ArrayList<>();
		products.add(p);
		
		store.setProducts(products);
		
		productRepository.save(p);
		return "Store Id added successfully";
//		}}
//		catch(Exception e)
//		 {
//		System.out.println("product ");
//		}
//		return null;
	}


	@Override
	public String orderProduct(int productId,OrderDetails orderDetails) {
		//Product p=productRepository.findById(productId).get();
		Product p=productRepository.findById(productId).orElseThrow(()->
		new ResourceNotFoundException("Product","Id",productId));
		double totalAmount=0.0;
		String message;
		if(p!=null)
		{
			 totalAmount=(orderDetails.getQuantity()*p.getProductPrice());
			orderDetails.setTotalAmount(totalAmount);
			List<Product> products=new ArrayList<>();
			products.add(p);
			orderDetails.setProducts(products);
			p.setStock(p.getStock()-orderDetails.getQuantity()); //30-2=28
			productRepository.save(p);
			orderRepository.save(orderDetails);
			message="Your order has been placed successfully!!"
					+ "Your total Amount is:"+totalAmount+"Your order will deliver within 7days";
		}
		else
			message="Product is null";
		
		return message;
	}

	@Override
	public String cancelProduct(Date purDate,int prodId,OrderDetails ordDetails) {
		
		String message;
		Product pd=productRepository.findById(prodId).get();
		Calendar cal=Calendar.getInstance();
		cal.setTime(new Date());
		
		cal.add(Calendar.DAY_OF_MONTH, -7);
		
		Date SvDaysBefore=cal.getTime();
		
		if(purDate.before(SvDaysBefore))
		{
			message="Cannot be cancelled as it is more than 7 days";
		}
		else {
			pd.setStock(pd.getStock()+ordDetails.getQuantity());
			productRepository.save(pd);
			
			message="Product has been cancelled,Thank you";
		}
		return message;
	}
}
	
	/*@Override
	public String cancelProduct(int productId,OrderDetails orderDetails) {
		//Product p=productRepository.findById(productId).get();
		Product p=productRepository.findById(productId).orElseThrow(()->
		new ResourceNotFoundException("Product","Id",productId));
	   //OrderDetails od=OrderRepository.findById(OrderDet).orElseThrow(()->
		//new ResourceNotFoundException("Order","Id",orderId));
		//double totalAmount=0.0;
		String message;
		
		//LocalDate d1 = LocalDate.parse(LocalDateTime.now().toString(DateTimeFormatter.ISO_LOCAL_DATE));
		LocalDate d1 = LocalDate.parse(LocalDateTime.now().toString(),(DateTimeFormatter.ISO_LOCAL_DATE));
		LocalDate d2 = LocalDate.parse(orderDetails.getOrderDate().toString(), DateTimeFormatter.ISO_LOCAL_DATE);
		Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
		long diffDays = diff.toDays();
		
		LocalDateTime date=LocalDateTime.now();
		System.out.println(date);
		//long count=date-orderDetails.getOrderDate();
		if(p!=null)
		{
			//if(!count>7){
			
			orderDetails.setStatus("Cancelled");
			List<Product> products=new ArrayList<>();
			products.add(p);
			orderDetails.setProducts(products);
			p.setStock(p.getStock()+orderDetails.getQuantity()); 
			productRepository.save(p);
			orderRepository.save(orderDetails);
			message="Your order has been cancelled successfully!!"
					+ "Your order will deliver within 7days";
			//}
		}
		else
		{
			message="Your order can be cancelled only within 7 days!!";
						
		}
		
		return message;
	}*/

