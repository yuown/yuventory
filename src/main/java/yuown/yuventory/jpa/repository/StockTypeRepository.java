package yuown.yuventory.jpa.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import yuown.yuventory.entity.StockType;

@Repository
public interface StockTypeRepository extends BaseRepository<StockType, Integer> {

	public List<StockType> findAllByMethod(String method);

}
