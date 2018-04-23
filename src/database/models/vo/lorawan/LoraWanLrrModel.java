package database.models.vo.lorawan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoraWanLrrModel {

//	"Lrrid": "00000462",
//	"Chain": "0",
//	"LrrRSSI": "-76.000000",
//	"LrrSNR": "8.250000",
//	"LrrESP": "-76.605560"
	private String Lrrid;
	private String Chain;
	private String LrrRSSI;
	private String LrrSNR;
	private String LrrESP;
	
	
}
