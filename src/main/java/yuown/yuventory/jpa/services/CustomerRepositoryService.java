package yuown.yuventory.jpa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.Customer;
import yuown.yuventory.jpa.repository.CustomerRepository;

@Service("customerRepositoryService")
public class CustomerRepositoryService extends AbstractRepositoryService<CustomerRepository, Customer, Integer> {

	@Autowired
	private CustomerRepository repository;

	@Override
	public CustomerRepository repository() {
		return repository;
	}

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}

	@Override
	public List<Customer> findAll() {
		return super.findAll();
	}

	@Override
	public Customer findOne(Integer id) {
		return super.findOne(id);
	}

	@Override
	public Customer save(Customer entity) {
		return super.save(entity);
	}
}
