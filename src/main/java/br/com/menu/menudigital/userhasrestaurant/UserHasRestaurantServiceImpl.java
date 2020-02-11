package br.com.menu.menudigital.userhasrestaurant;

import org.springframework.stereotype.Service;

@Service
public class UserHasRestaurantServiceImpl implements UserHasRestaurantService {

	private UserHasRestaurantRepository userHasRestaurantRepository;

	public UserHasRestaurantServiceImpl(UserHasRestaurantRepository userHasRestaurantRepository) {
		super();
		this.userHasRestaurantRepository = userHasRestaurantRepository;
	}

	public boolean softDeleteRelationship(UserHasRestaurant userHasRestaurant) {

		userHasRestaurant.setDeleted(true);
		userHasRestaurantRepository.save(userHasRestaurant);
		
		return true;
	}
}
