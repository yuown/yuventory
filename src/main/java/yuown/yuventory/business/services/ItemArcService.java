package yuown.yuventory.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.ItemArc;
import yuown.yuventory.jpa.services.ItemsArcRepositoryService;
import yuown.yuventory.model.ItemArcModel;
import yuown.yuventory.transformer.ItemArcTransformer;

@Service
public class ItemArcService extends AbstractServiceImpl<Integer, ItemArcModel, ItemArc, ItemsArcRepositoryService, ItemArcTransformer> {

	@Autowired
	private ItemArcTransformer itemTransformer;

	@Autowired
	private ItemsArcRepositoryService itemsArcRepositoryService;

	@Override
	protected ItemsArcRepositoryService repoService() {
		return itemsArcRepositoryService;
	}

	@Override
	public ItemArcTransformer transformer() {
		return itemTransformer;
	}

	public ItemArcModel getByItemId(int id) {
		return transformer().transformTo(repoService().findByItemId(id));
	}
}
