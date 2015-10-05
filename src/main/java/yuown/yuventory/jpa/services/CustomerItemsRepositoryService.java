package yuown.yuventory.jpa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.CustomerItems;
import yuown.yuventory.jpa.repository.CustomerItemsRepository;

@Service("customerItemsRepositoryService")
public class CustomerItemsRepositoryService extends AbstractRepositoryService<CustomerItemsRepository, CustomerItems, Integer> {

	@Autowired
	private CustomerItemsRepository repository;

	@Override
	public CustomerItemsRepository repository() {
		return repository;
	}

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}

	@Override
	public List<CustomerItems> findAll() {
		return super.findAll();
	}

	@Override
	public CustomerItems findOne(Integer id) {
		return super.findOne(id);
	}

	@Override
	public CustomerItems save(CustomerItems entity) {
		return super.save(entity);
	}
}
