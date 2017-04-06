package service.basicFunctions;


import database.models.WebTokenFactory;

public interface WebTokenFactoryService {

	public WebTokenFactory getByTypeAndId(String baseId,Integer type);

	public void update(WebTokenFactory webTokenFactory);

	public void save(WebTokenFactory webTokenFactory);

}
