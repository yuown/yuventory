package yuown.yuventory.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.Customer;
import yuown.yuventory.jpa.services.CustomerRepositoryService;
import yuown.yuventory.model.CustomerModel;
import yuown.yuventory.transformer.CustomerTransformer;

@Service
public class CustomerService extends AbstractServiceImpl<Integer, CustomerModel, Customer, CustomerRepositoryService, CustomerTransformer> {

	@Autowired
	private CustomerRepositoryService categoryRepositoryService;

	@Autowired
	private CustomerTransformer categoryTransformer;

	@Override
	protected CustomerRepositoryService repoService() {
		return categoryRepositoryService;
	}

	@Override
	protected CustomerTransformer transformer() {
		return categoryTransformer;
	}
}
