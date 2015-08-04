package yuown.yuventory.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.Item;
import yuown.yuventory.jpa.services.StockTypeRepositoryService;
import yuown.yuventory.jpa.services.SupplierRepositoryService;
import yuown.yuventory.jpa.services.UserRepositoryService;
import yuown.yuventory.model.ItemModel;

@Component
public class ItemTransformer extends AbstractDTOTransformer<ItemModel, Item> {

	private static final String[] FROM_EXCLUDES = new String[] { "supplier", "stockType", "user" };
	private static final String[] TO_EXCLUDES = new String[] { "supplier", "stockType", "user" };

	@Autowired
	private SupplierRepositoryService supplierRepositoryService;

	@Autowired
	private StockTypeRepositoryService stockTypeRepositoryService;

	@Autowired
	private UserRepositoryService userRepositoryService;

	@Override
	public Item transformFrom(ItemModel source) {
		Item dest = null;
		if (source != null) {
			try {
				dest = new Item();
				BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);
				dest.setName(dest.getName().toUpperCase());
				dest.setSupplier(supplierRepositoryService.findOne(source.getSupplier()));
				dest.setStockType(stockTypeRepositoryService.findOne(source.getStockType()));
				dest.setUser(userRepositoryService.findOne(source.getUser()));
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}

	@Override
	public ItemModel transformTo(Item source) {
		ItemModel dest = null;
		if (source != null) {
			try {
				dest = new ItemModel();
				BeanUtils.copyProperties(source, dest, TO_EXCLUDES);
				dest.setSupplier(source.getSupplier().getId());
				dest.setStockType(source.getStockType().getId());
				dest.setUser(source.getUser().getId());
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}
}
