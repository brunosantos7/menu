package br.com.menu.menudigital.user;

import org.springframework.stereotype.Service;

import br.com.menu.menudigital.userhasrestaurant.UserHasRestaurantRepository;

@Service
public class UserServiceImpl implements UserService {
	
	private UserHasRestaurantRepository userHasRestaurantRepository;
	
	public UserServiceImpl(UserHasRestaurantRepository userHasRestaurantRepository) {
		super();
		this.userHasRestaurantRepository = userHasRestaurantRepository;
	}

	@Override
	public boolean hasMaxRestaurantsForPlan(User user) {
		int numberOfRestaurants = userHasRestaurantRepository.countByUserId(user.getId());
		
		return user.getPremiumTypeId().equals(1l) && numberOfRestaurants == 1;
	}

}
