package br.com.menu.menudigital.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
	
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.restaurants WHERE u.username = :username")
	User findByUsernameWithRestaurants(String username);

	User findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.restaurants WHERE u.email = :email")
	User findByEmailWithRestaurants(String email);

 }
