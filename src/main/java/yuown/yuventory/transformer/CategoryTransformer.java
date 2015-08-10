package yuown.yuventory.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import yuown.yuventory.entity.Category;
import yuown.yuventory.model.CategoryModel;

@Component
public class CategoryTransformer extends AbstractDTOTransformer<CategoryModel, Category> {

	private static final String[] FROM_EXCLUDES = new String[] {};
	private static final String[] TO_EXCLUDES = new String[] {};

	@Override
	public Category transformFrom(CategoryModel source) {
		Category dest = null;
		if (source != null) {
			try {
				dest = new Category();
				BeanUtils.copyProperties(source, dest, FROM_EXCLUDES);
				dest.setName(dest.getName().toUpperCase());
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}

	@Override
	public CategoryModel transformTo(Category source) {
		CategoryModel dest = null;
		if (source != null) {
			try {
				dest = new CategoryModel();
				BeanUtils.copyProperties(source, dest, TO_EXCLUDES);
			} catch (Exception e) {
				dest = null;
			}
		}
		return dest;
	}
}
