package br.com.menu.menudigital.user;

public interface UserService {

	boolean hasMaxRestaurantsForPlan(User user);

	boolean hasMaxMenusForPlan(User user);

	boolean hasMaxProductForPlan(User user);

}
