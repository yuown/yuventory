package yuown.yuventory.jpa.repository;

import org.springframework.stereotype.Repository;

import yuown.yuventory.entity.User;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {

}
