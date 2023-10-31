package com.demo.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.entities.OrderDetails;
import com.demo.entities.Product;
import com.demo.entities.Store;
import com.demo.exception.ResourceNotFoundException;
import com.demo.model.ProductDTO;
import com.demo.model.StoreDTO;
import com.demo.repositories.StoreRepository;
import com.demo.service.StoreService;
import com.demo.util.Converter;

@Service
public class StoreServiceImpl implements StoreService{

	@Autowired
	private StoreRepository storeRepository;
	
	@Autowired
private Converter converter;
	
	@Override
	public StoreDTO creatStore(Store store) {
		
		return converter.convertToStoreDTO(storeRepository.save(store));
		
	}

	@Override
	public StoreDTO updateStore(int id, Store store) {
		// TODO Auto-generated method stub
//		Store s=storeRepository.findById(id).get();
		Store s=storeRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Store","Id",id));
		s.setStoreName(store.getStoreName());
		s.setAddress(store.getAddress());
		//s.setStock(product.getStock());
		
		storeRepository.save(s);
		return converter.convertToStoreDTO(s);
		
	}

	@Override
	public String deleteStore(int id) {
		// TODO Auto-generated method stub
		//Store s=storeRepository.findById(id).get();
		Store s=storeRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Product","Id",id));
		storeRepository.delete(s);
		return "store delete successfully";
	}

	@Override
	public List<StoreDTO> getAllStore() {
		// TODO Auto-generated method stub
		List<Store> stores=storeRepository.findAll();
		
		List<StoreDTO> storeDTOS=new ArrayList<>();
		
		for(Store s:stores)
		{
		storeDTOS.add(converter.convertToStoreDTO(s));
		}
			return storeDTOS;
	}

	@Override
	public StoreDTO getStoreById(int id) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		Optional<Store> st=storeRepository.findById(id);
		Store s;
		if(st.isPresent())
		{
			 s=st.get();
		}
		else {
			throw new ResourceNotFoundException("Store","id",id);
		}
		return converter.convertToStoreDTO(s);
	}

	

	

}
