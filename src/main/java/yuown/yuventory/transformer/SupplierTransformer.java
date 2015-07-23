package yuown.yuventory.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.Supplier;
import yuown.yuventory.model.SupplierModel;

@Component
public class SupplierTransformer extends AbstractDTOTransformer<SupplierModel, Supplier> {

    private static final String[] FROM_EXCLUDES = new String[] {};
    private static final String[] TO_EXCLUDES = new String[] {};

    @Override
    public Supplier transformFrom(SupplierModel source) {
        Supplier dest = null;
        if (source != null) {
            try {
                dest = new Supplier();
                BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);
                dest.setName(dest.getName().toUpperCase());
            } catch (Exception e) {
                dest = null;
            }
        }
        return dest;
    }

    @Override
    public SupplierModel transformTo(Supplier source) {
        SupplierModel dest = null;
        if (source != null) {
            try {
                dest = new SupplierModel();
                BeanUtils.copyProperties(source, dest, TO_EXCLUDES);
            } catch (Exception e) {
                dest = null;
            }
        }
        return dest;
    }
}
