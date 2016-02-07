package yuown.yuventory.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.ItemArc;
import yuown.yuventory.model.ItemArcModel;

@Component
public class ItemArcTransformer extends AbstractDTOTransformer<ItemArcModel, ItemArc> {

	private static final String[] FROM_EXCLUDES = new String[] {};
	private static final String[] TO_EXCLUDES = new String[] {};

	@Override
	public ItemArc transformFrom(ItemArcModel source) {
		ItemArc dest = null;
		if (source != null) {
			try {
				dest = new ItemArc();
				BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);
				dest.setName(dest.getName().toUpperCase());
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}

	@Override
	public ItemArcModel transformTo(ItemArc source) {
		ItemArcModel dest = null;
		if (source != null) {
			try {
				dest = new ItemArcModel();
				BeanUtils.copyProperties(source, dest, TO_EXCLUDES);
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}
}
