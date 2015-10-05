package yuown.yuventory.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.Customer;
import yuown.yuventory.model.CustomerModel;

@Component
public class CustomerTransformer extends AbstractDTOTransformer<CustomerModel, Customer> {

	private static final String[] FROM_EXCLUDES = new String[] {};
	private static final String[] TO_EXCLUDES = new String[] {};

	@Override
	public Customer transformFrom(CustomerModel source) {
		Customer dest = null;
		if (source != null) {
			try {
				dest = new Customer();
				BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);
				dest.setName(dest.getName().toUpperCase());
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}

	@Override
	public CustomerModel transformTo(Customer source) {
		CustomerModel dest = null;
		if (source != null) {
			try {
				dest = new CustomerModel();
				BeanUtils.copyProperties(source, dest, TO_EXCLUDES);
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}
}
