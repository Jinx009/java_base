package service.basicFunctions;

import java.util.List;

import database.models.User;

public interface UserService {

	public List<User> getById(Integer id);

	public User getByOpenId(String openId);

	public User save(User user);
	
	public void update(User user);

}
