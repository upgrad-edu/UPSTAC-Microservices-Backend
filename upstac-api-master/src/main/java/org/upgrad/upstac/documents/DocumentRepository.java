package org.upgrad.upstac.documents;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.upgrad.upstac.users.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {

    Optional<Document> findById(Long id);
    Optional<Document> findByUser(User user);

    List<Document> findAllByUserIn(Set<User> users);


}
