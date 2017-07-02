package com.tasks.repository;

import com.tasks.domain.CoreUser;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

/**
 * Created by andre on 7/1/17.
 */
public class CoreUserRepository extends AbstractDAO<CoreUser> {

    public CoreUserRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public CoreUser createUser(CoreUser user) {
        return persist(user);
    }

    public List<CoreUser> findAll() {
        return list(namedQuery("com.tasks.domain.CoreUser.findAll"));
    }

    public Optional<CoreUser> findById(Long id) {
        return Optional.ofNullable(get(id));
    }

    public CoreUser updateUser(CoreUser coreUser) {
        long userId = coreUser.getId();
        if (findById(userId).isPresent()) {
            CoreUser currUserUpdate = findById(userId).get();
            if (coreUser.getFirst_name() != null) {
                currUserUpdate.setFirst_name(coreUser.getFirst_name());
            }

            if (coreUser.getLast_name() != null) {
                currUserUpdate.setLast_name(coreUser.getFirst_name());
            }

            if (coreUser.getUsername() != null) {
                currUserUpdate.setUsername(coreUser.getUsername());
            }
            currentSession().update(currUserUpdate);
        }

        return findById(userId).get();
    }

}
