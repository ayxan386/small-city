package com.jsimplec.auth.repository;

import com.jsimplec.auth.model.UserModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserModel, UUID> {

  Optional<UserModel> findByUsername(String username);

  Optional<UserModel> findByEmail(String email);

  Optional<UserModel> findByEmailOrUsername(String email, String username);

}
