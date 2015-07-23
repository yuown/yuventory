package yuown.yuventory.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.StockType;
import yuown.yuventory.jpa.services.StockTypeRepositoryService;
import yuown.yuventory.model.StockTypeModel;
import yuown.yuventory.transformer.StockTypeTransformer;

@Service
public class StockTypeService extends AbstractServiceImpl<Integer, StockTypeModel, StockType, StockTypeRepositoryService, StockTypeTransformer> {

	@Autowired
	private StockTypeRepositoryService stockTypeRepositoryService;

	@Autowired
	private StockTypeTransformer stockTypeTransformer;

	@Override
	protected StockTypeRepositoryService repoService() {
		return stockTypeRepositoryService;
	}

	@Override
	protected StockTypeTransformer transformer() {
		return stockTypeTransformer;
	}
}
