package yuown.yuventory.jpa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.Category;
import yuown.yuventory.jpa.repository.CategoryRepository;

@Service("categoryRepositoryService")
public class CategoryRepositoryService extends AbstractRepositoryService<CategoryRepository, Category, Integer> {

	@Autowired
	private CategoryRepository repository;

	@Override
	public CategoryRepository repository() {
		return repository;
	}

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}

	@Override
	public List<Category> findAll() {
		return super.findAll();
	}

	@Override
	public Category findOne(Integer id) {
		return super.findOne(id);
	}

	@Override
	public Category save(Category entity) {
		return super.save(entity);
	}
}
