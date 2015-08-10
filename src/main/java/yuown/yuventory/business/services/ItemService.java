package yuown.yuventory.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.Item;
import yuown.yuventory.jpa.services.ItemsRepositoryService;
import yuown.yuventory.model.ConfigurationModel;
import yuown.yuventory.model.ItemModel;
import yuown.yuventory.transformer.ItemTransformer;

@Service
public class ItemService extends AbstractServiceImpl<Integer, ItemModel, Item, ItemsRepositoryService, ItemTransformer> {

	private static final String ITEM_PAGE_SIZE = "item_pagesize";

	@Autowired
	private ItemsRepositoryService itemsRepositoryService;

	@Autowired
	private ItemTransformer itemTransformer;

	@Autowired
	private ConfigurationService configurationService;

	@Override
	protected ItemsRepositoryService repoService() {
		return itemsRepositoryService;
	}

	@Override
	public ItemTransformer transformer() {
		return itemTransformer;
	}

	public PageImpl<Item> getAll(Integer page, Integer size) {
		if (page == null || page < 0) {
			page = 0;
		}
		Integer fromSystem = Integer.parseInt(System.getProperty(ITEM_PAGE_SIZE));
		if (size == null || (size < 0 || size > fromSystem)) {
			size = fromSystem;
		}
		return repoService().findAll(new PageRequest(page, size));
	}

	public Integer getPageSize() {
		ConfigurationModel pageSize = configurationService.getByName(ITEM_PAGE_SIZE);
		Integer size = 10;
		if (pageSize != null) {
			size = pageSize.getValue();
		}
		return size;
	}

	public void setPageSize(Integer size) {
		ConfigurationModel pageSize = configurationService.getByName(ITEM_PAGE_SIZE);
		if (pageSize == null) {
			pageSize = new ConfigurationModel();
			pageSize.setName(ITEM_PAGE_SIZE);
		}
		if(size == null || size <= 0) {
			size = 10;
		}
		pageSize.setValue(size);
		configurationService.save(pageSize);
		setPageSizeToSystem(pageSize);
	}

	public void setPageSizeToSystem(ConfigurationModel pageSize) {
		try {
			System.setProperty(ITEM_PAGE_SIZE, Integer.toString(pageSize.getValue()));
		} catch (Exception e) {
			System.setProperty(ITEM_PAGE_SIZE, "10");
			ConfigurationModel newPageSize = new ConfigurationModel();
			newPageSize.setName(ITEM_PAGE_SIZE);
			newPageSize.setValue(10);
			configurationService.save(newPageSize);
		}
	}

	public void setPageSizeToSystem() {
		ConfigurationModel pageSize = configurationService.getByName(ITEM_PAGE_SIZE);
		setPageSizeToSystem(pageSize);
	}
}
