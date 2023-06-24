package org.mediscribe.repository;

import org.mediscribe.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByUserId(String userId);
    Optional<User> findUserByUsername(String username);

    void deleteUserById(long id);

//    boolean existsUserByUserId(String userId);
}
