package utils.sign;

public class SignTest {

	public static void main(String[] args) {
//		{"m":"00043cde0a3d","d":{},"s":"63db2c9705cc8b46a9732de817859f3f499fa6adeccf186259bc98b4a930899a"}
		String signStr = "mac=00043cde0a3d";
		 String ret = SignUtils.sign(signStr);
		 try {
			ret = StringUtils.toHexString(CipherUtil.encrypt("FQ2vaGi0aMNYH6ru".getBytes("utf-8"), ret.getBytes("utf-8")));
			System.out.println(ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
