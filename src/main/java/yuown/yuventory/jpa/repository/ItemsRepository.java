package yuown.yuventory.jpa.repository;

import org.springframework.stereotype.Repository;

import yuown.yuventory.entity.Item;

@Repository
public interface ItemsRepository extends BaseRepository<Item, Integer> {

}
