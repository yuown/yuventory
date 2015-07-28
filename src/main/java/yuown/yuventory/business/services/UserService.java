package yuown.yuventory.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import yuown.yuventory.entity.User;
import yuown.yuventory.jpa.services.UserRepositoryService;
import yuown.yuventory.model.UserModel;
import yuown.yuventory.transformer.UserTransformer;

@Service
public class UserService extends AbstractServiceImpl<Integer, UserModel, User, UserRepositoryService, UserTransformer> implements UserDetailsService {

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Autowired
    private UserTransformer userTransformer;

    @Override
    protected UserRepositoryService repoService() {
        return userRepositoryService;
    }

    @Override
    protected UserTransformer transformer() {
        return userTransformer;
    }

    public UserModel loadUserByUsername(String userName) throws UsernameNotFoundException {
        User userFromDB = userRepositoryService.findByUsername(userName);
        if(null == userFromDB) {
            throw new UsernameNotFoundException("User " + userName + " doesn't Exists");
        }
        return transformer().transformTo(userFromDB);
    }
}
