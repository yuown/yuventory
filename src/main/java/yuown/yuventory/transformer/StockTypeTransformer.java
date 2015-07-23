package yuown.yuventory.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.StockType;
import yuown.yuventory.model.StockTypeModel;

@Component
public class StockTypeTransformer extends AbstractDTOTransformer<StockTypeModel, StockType> {

	private static final String[] FROM_EXCLUDES = new String[] {};
	private static final String[] TO_EXCLUDES = new String[] {};

	@Override
	public StockType transformFrom(StockTypeModel source) {
		StockType dest = null;
		if (source != null) {
			try {
				dest = new StockType();
				BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);
				dest.setName(dest.getName().toUpperCase());
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}

	@Override
	public StockTypeModel transformTo(StockType source) {
		StockTypeModel dest = null;
		if (source != null) {
			try {
				dest = new StockTypeModel();
				BeanUtils.copyProperties(source, dest, TO_EXCLUDES);
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}
}
