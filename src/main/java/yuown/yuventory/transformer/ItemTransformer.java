package yuown.yuventory.transformer;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.Item;
import yuown.yuventory.jpa.services.CategoryRepositoryService;
import yuown.yuventory.jpa.services.ItemsRepositoryService;
import yuown.yuventory.jpa.services.StockTypeRepositoryService;
import yuown.yuventory.jpa.services.SupplierRepositoryService;
import yuown.yuventory.jpa.services.UserRepositoryService;
import yuown.yuventory.model.ItemModel;

@Component
public class ItemTransformer extends AbstractDTOTransformer<ItemModel, Item> {

	private static final String[] FROM_EXCLUDES = new String[] { "supplier", "stockType", "user", "lendTo" };
	private static final String[] TO_EXCLUDES = new String[] { "supplier", "stockType", "user", "lendTo" };

	@Autowired
	private SupplierRepositoryService supplierRepositoryService;

	@Autowired
	private CategoryRepositoryService categoryRepositoryService;

	@Autowired
	private StockTypeRepositoryService stockTypeRepositoryService;

	@Autowired
	private UserRepositoryService userRepositoryService;

	@Autowired
	private ItemsRepositoryService itemsRepositoryService;

	@Override
	public Item transformFrom(ItemModel source) {
		Item dest = null;
		if (source != null) {
			try {
				if (source.getId() != null) {
					dest = itemsRepositoryService.findOne(source.getId());
					dest.setUpdateDate(new Date());
					BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);

					if (source.getLendTo() > 0) {
						decideLending(source, dest);
					} else {
						decideLending(null, dest);
					}

				} else {
					dest = new Item();
					dest.setCreateDate(new Date());
					BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);

					dest.setSupplier(supplierRepositoryService.findOne(source.getSupplier()));
					dest.setCategory(categoryRepositoryService.findOne(source.getCategory()));
					dest.setStockType(stockTypeRepositoryService.findOne(source.getStockType()));
					dest.setUser(userRepositoryService.findOne(source.getUser()));

					decideLending(null, dest);
					dest.setSold(false);
				}

				dest.setName(dest.getName().toUpperCase());

			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}

	private void decideLending(ItemModel source, Item dest) {
		if (source == null) {
			dest.setLendDate(null);
			dest.setLendDescription(null);
			dest.setLendTo(null);
		} else {
			dest.setLendDate(source.getLendDate());
			dest.setLendDescription(source.getLendDescription());
			dest.setLendTo(supplierRepositoryService.findOne(source.getLendTo()));
		}
	}

	@Override
	public ItemModel transformTo(Item source) {
		ItemModel dest = null;
		if (source != null) {
			try {
				dest = new ItemModel();
				BeanUtils.copyProperties(source, dest, TO_EXCLUDES);
				dest.setSupplier(source.getSupplier().getId());
				dest.setCategory(source.getCategory().getId());
				dest.setStockType(source.getStockType().getId());
				dest.setUser(source.getUser().getId());
				dest.setSold(source.getSold());
				if (source.getLendTo() != null) {
					dest.setLendTo(source.getLendTo().getId());
					dest.setLendDescription(source.getLendDescription());
					dest.setLendDate(source.getLendDate());
				}
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}
}
