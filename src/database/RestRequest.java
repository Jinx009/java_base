package database;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestRequest<T> {

	private String resCode;
	private String msg;
	private T params;
	
}
