package database.models.vo.lorawan;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoraWanDataModel {

	private String Time;
	private String DevEUI;
	private String FPort;
	private String FCntUp;
	private String ADRbit;
	private String MType;
	private String FCntDn;
	private String payload_hex;
	private String mic_hex;
	private String Lrcid;
	private String LrrRSSI;
	private String LrrSNR;
	private String SpFact;
	private String SubBand;
	private String Channel;
	private String DevLrrCnt;
	private String Lrrid;
	private String Late;
	private LoraWanLrrsModel Lrrs;
	private String CustomerID;
	private LoraWanCustomerDataModel CustomerData;
	private String ModelCfg;
	private String DevAddr;
	
}
