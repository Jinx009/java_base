package test;

import java.util.Collection;

public class PPrinter {

	public static String pformat(Collection<?> c){
		if(c.size()==0){
			return "[]";
		}
		StringBuilder stringBuilder = new StringBuilder("[");
		for(Object o:c){
			if(c.size()!=1){
				stringBuilder.append("\n     ");
			}
			stringBuilder.append(o);
		}
		if(c.size()!=1){
			stringBuilder.append("\n");
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

}
