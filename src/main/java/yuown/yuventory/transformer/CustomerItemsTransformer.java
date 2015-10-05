package yuown.yuventory.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.CustomerItems;
import yuown.yuventory.model.CustomerItemsModel;

@Component
public class CustomerItemsTransformer extends AbstractDTOTransformer<CustomerItemsModel, CustomerItems> {

	private static final String[] FROM_EXCLUDES = new String[] {};
	private static final String[] TO_EXCLUDES = new String[] {};

	@Override
	public CustomerItems transformFrom(CustomerItemsModel source) {
		CustomerItems dest = null;
		if (source != null) {
			try {
				dest = new CustomerItems();
				BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}

	@Override
	public CustomerItemsModel transformTo(CustomerItems source) {
		CustomerItemsModel dest = null;
		if (source != null) {
			try {
				dest = new CustomerItemsModel();
				BeanUtils.copyProperties(source, dest, TO_EXCLUDES);
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}
}
