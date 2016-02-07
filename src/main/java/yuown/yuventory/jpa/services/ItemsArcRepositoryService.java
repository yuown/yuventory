package yuown.yuventory.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.ItemArc;
import yuown.yuventory.jpa.repository.ItemsArcRepository;

@Service
public class ItemsArcRepositoryService extends AbstractRepositoryService<ItemsArcRepository, ItemArc, Integer> {

	@Autowired
	private ItemsArcRepository repository;

	@Override
	public ItemsArcRepository repository() {
		return repository;
	}

	public ItemArc findByItemId(int id) {
		return repository().findByItemId(id);
	}
}
