package yuown.yuventory.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.Category;
import yuown.yuventory.jpa.services.CategoryRepositoryService;
import yuown.yuventory.model.CategoryModel;
import yuown.yuventory.transformer.CategoryTransformer;

@Service
public class CategoryService extends AbstractServiceImpl<Integer, CategoryModel, Category, CategoryRepositoryService, CategoryTransformer> {

	@Autowired
	private CategoryRepositoryService categoryRepositoryService;

	@Autowired
	private CategoryTransformer categoryTransformer;

	@Override
	protected CategoryRepositoryService repoService() {
		return categoryRepositoryService;
	}

	@Override
	protected CategoryTransformer transformer() {
		return categoryTransformer;
	}
}
