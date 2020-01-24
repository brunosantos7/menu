package br.com.menu.menudigital.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);

	Optional<User> findByRestaurantId(Long id);

 }
