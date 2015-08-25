package yuown.yuventory.jpa.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.User;
import yuown.yuventory.jpa.repository.UserRepository;

import java.util.List;

@Service("userRepositoryService")
public class UserRepositoryService extends AbstractRepositoryService<UserRepository, User, Integer> {

    @Autowired
    private UserRepository repository;

    @Override
    public UserRepository repository() {
        return repository;
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @Override
    public User findOne(Integer id) {
        return super.findOne(id);
    }

    @Override
    public User save(User entity) {
        return super.save(entity);
    }

    public User findByUsername(String userName) {
        return repository().findByUsername(userName);
    }
    
    public Integer findMaxId() {
    	return repository().findMaxId();
    }
}
