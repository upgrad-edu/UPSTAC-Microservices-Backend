package org.upgrad.upstac.users;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.upstac.users.models.AccountStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserName(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByPhoneNumber(String email);
    void deleteByUserName(String username);

    List<User> findAll();
    List<User> findByStatus(AccountStatus status);

    void deleteById(Long id);

    Optional<User> findById(Long id);


}
