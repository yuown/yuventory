package yuown.yuventory.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.CustomerItems;
import yuown.yuventory.jpa.services.CustomerItemsRepositoryService;
import yuown.yuventory.model.CustomerItemsModel;
import yuown.yuventory.transformer.CustomerItemsTransformer;

@Service
public class CustomerItemsService extends AbstractServiceImpl<Integer, CustomerItemsModel, CustomerItems, CustomerItemsRepositoryService, CustomerItemsTransformer> {

	@Autowired
	private CustomerItemsRepositoryService categoryRepositoryService;

	@Autowired
	private CustomerItemsTransformer categoryTransformer;

	@Override
	protected CustomerItemsRepositoryService repoService() {
		return categoryRepositoryService;
	}

	@Override
	protected CustomerItemsTransformer transformer() {
		return categoryTransformer;
	}
}
