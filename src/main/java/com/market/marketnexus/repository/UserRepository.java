package com.market.marketnexus.repository;

import com.market.marketnexus.model.Credentials;
import com.market.marketnexus.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
   public boolean existsByEmail(String email);

   public Optional<User> findByEmail(String email);

   public Optional<User> findByCredentials(Credentials credentials);

   @Query("SELECT n.name AS nationName, COUNT(DISTINCT u.id) AS numbersOfUsers " +
           "FROM User u JOIN u.nation n " +
           "GROUP BY n.name")
   public List<Object[]> countUsersByNation();

}
