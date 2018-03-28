package utils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SocketData {
	
	public static final List<SocketData> list = new ArrayList<SocketData>();

	private String name;
	private String data;
	
}
