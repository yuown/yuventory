package yuown.yuventory.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.Item;
import yuown.yuventory.model.ItemModel;

@Component
public class ItemTransformer extends AbstractDTOTransformer<ItemModel, Item> {

	private static final String[] FROM_EXCLUDES = new String[] {};
	private static final String[] TO_EXCLUDES = new String[] {};

	@Override
	public Item transformFrom(ItemModel source) {
		Item dest = null;
		if (source != null) {
			try {
				dest = new Item();
				BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);
				dest.setName(dest.getName().toUpperCase());
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
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}
}
