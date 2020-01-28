package br.com.menu.menudigital.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
	
    @Query("SELECT u FROM User u JOIN FETCH u.restaurants WHERE u.username = (:username)")
	User findByUsernameWithRestaurants(String username);

 }
