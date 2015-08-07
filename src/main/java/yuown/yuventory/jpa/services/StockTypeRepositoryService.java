package yuown.yuventory.jpa.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.StockType;
import yuown.yuventory.jpa.repository.StockTypeRepository;

@Service("stockTypeRepositoryService")
public class StockTypeRepositoryService extends AbstractRepositoryService<StockTypeRepository, StockType, Integer> {

	@Autowired
	private StockTypeRepository repository;

	@Override
	public StockTypeRepository repository() {
		return repository;
	}

	@Override
	public void delete(Integer id) {
		super.delete(id);
	}

	@Override
	public List<StockType> findAll() {
		return super.findAll();
	}

	@Override
	public StockType findOne(Integer id) {
		return super.findOne(id);
	}

	@Override
	public StockType save(StockType entity) {
		return super.save(entity);
	}

	public List<StockType> findAllByMethod(String method) {
		return repository().findAllByMethod(method);
	}
	
	public List<StockType> findAllByMethodAndRemove(String method, boolean remove) {
		return repository().findAllByMethodAndRemove(method, remove);
	}
}
