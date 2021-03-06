package yuown.yuventory.transformer;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.Item;
import yuown.yuventory.entity.Location;
import yuown.yuventory.jpa.services.CategoryRepositoryService;
import yuown.yuventory.jpa.services.ItemsRepositoryService;
import yuown.yuventory.jpa.services.LocationRepositoryService;
import yuown.yuventory.jpa.services.StockTypeRepositoryService;
import yuown.yuventory.jpa.services.SupplierRepositoryService;
import yuown.yuventory.jpa.services.UserRepositoryService;
import yuown.yuventory.model.ItemModel;

@Component
public class ItemTransformer extends AbstractDTOTransformer<ItemModel, Item> {

	private static final String[] FROM_EXCLUDES = new String[] { "supplier", "stockType", "user", "lendTo", "createDate", "lendDate" };
	private static final String[] TO_EXCLUDES = new String[] { "supplier", "stockType", "user", "lendTo" };

	@Autowired
	private SupplierRepositoryService supplierRepositoryService;

	@Autowired
	private CategoryRepositoryService categoryRepositoryService;
	
	@Autowired
	private LocationRepositoryService locationRepositoryService;

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
					BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);
					dest.setUpdateDate(new Date().getTime());

					if (source.getLendTo() > 0) {
						decideLending(source, dest);
					} else {
						decideLending(null, dest);
					}
				} else {
					dest = new Item();
					BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);
					dest.setCreateDate(new Date().getTime());

					decideLending(null, dest);
					dest.setSold(false);
				}
				dest.setSupplier(supplierRepositoryService.findOne(source.getSupplier()));
				dest.setCategory(categoryRepositoryService.findOne(source.getCategory()));
				dest.setStockType(stockTypeRepositoryService.findOne(source.getStockType()));
				dest.setUser(userRepositoryService.findOne(source.getUser()));
				
				Location loc = locationRepositoryService.findOne(source.getLocation());
				if(loc != null) {
					dest.setLocation(loc);
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
			dest.setLendDate(0);
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
				Location loc = source.getLocation();
				if(loc != null) {
					dest.setLocation(loc.getId());
				}
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
