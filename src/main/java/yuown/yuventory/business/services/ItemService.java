package yuown.yuventory.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.Item;
import yuown.yuventory.jpa.services.ItemsRepositoryService;
import yuown.yuventory.model.ItemModel;
import yuown.yuventory.transformer.ItemTransformer;

@Service
public class ItemService extends AbstractServiceImpl<Integer, ItemModel, Item, ItemsRepositoryService, ItemTransformer> {

	@Autowired
	private ItemsRepositoryService itemsRepositoryService;

	@Autowired
	private ItemTransformer itemTransformer;

	@Override
	protected ItemsRepositoryService repoService() {
		return itemsRepositoryService;
	}

	@Override
	protected ItemTransformer transformer() {
		return itemTransformer;
	}
}
