package yuown.yuventory.transformer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.StockType;
import yuown.yuventory.holders.StockTypeMethod;
import yuown.yuventory.model.StockTypeModel;

@Component
public class StockTypeTransformer extends AbstractDTOTransformer<StockTypeModel, StockType> {

	private static final String[] FROM_EXCLUDES = new String[] { "method" };
	private static final String[] TO_EXCLUDES = new String[] { "method" };

	@Override
	public StockType transformFrom(StockTypeModel source) {
		StockType dest = null;
		if (source != null) {
			try {
				dest = new StockType();
				BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);
				dest.setName(dest.getName().toUpperCase());
				dest.setMethod(source.getMethod().name());
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
				if (StringUtils.isNotBlank(source.getMethod())) {
					dest.setMethod(StockTypeMethod.valueOf(source.getMethod()));
				}
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}
}
