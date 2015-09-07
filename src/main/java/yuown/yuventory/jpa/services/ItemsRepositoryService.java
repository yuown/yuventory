package yuown.yuventory.jpa.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.Item;
import yuown.yuventory.jpa.repository.ItemsRepository;

@Service("itemsRepositoryService")
public class ItemsRepositoryService extends AbstractRepositoryService<ItemsRepository, Item, Integer> {

	@Autowired
	private ItemsRepository repository;

	@Override
	public ItemsRepository repository() {
		return repository;
	}

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}

	@Override
	public List<Item> findAll() {
		return super.findAll();
	}

	@Override
	public Item findOne(Integer id) {
		return super.findOne(id);
	}

	@Override
	public Item save(Item entity) {
		return super.save(entity);
	}

	public Double findWeightSumByType(String type) {
		return repository().findAllWeightByItemType(type);
	}
	
	public Set<String> findAllItemNames() {
		return repository().findAllItemNames();
	}
}
