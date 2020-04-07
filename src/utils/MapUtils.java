package utils;

public class MapUtils {

	// private static double EARTH_RADIUS = 6378.137;
	private static double EARTH_RADIUS = 6371.393;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 计算两个经纬度之间的距离
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.abs(
				Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2))));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 1000 * 1000);
		return s;
	}

	public static void main(String[] args) {
		// System.out.println(MapUtils.GetDistance(29.490295, 106.486654, 29.615467,
		// 106.581515));
		// System.out.println(Double.valueOf("29.490295"));
//		double[] d = WGS84toECEF(30.7335894 / 1000, 103.9681949 / 1000, 495441 / 1000);
//		System.out.println(d[0] + "--" + d[1] + "---" + d[2]);
		System.out.println(ECEFtoWGS84(-1426814.8942052593, 5736207.14002754, 3492541.7182463093));
	}

	public static double[] WGS84toECEF(double latitude, double longitude, double height) {
		double X;
		double Y;
		double Z;
		double a = 6378137.0;
		double b = 6356752.31424518;
		double E = (a * a - b * b) / (a * a);
		double COSLAT = Math.cos(latitude * Math.PI / 180);
		double SINLAT = Math.sin(latitude * Math.PI / 180);
		double COSLONG = Math.cos(longitude * Math.PI / 180);
		double SINLONG = Math.sin(longitude * Math.PI / 180);
		double N = a / (Math.sqrt(1 - E * SINLAT * SINLAT));
		double NH = N + height;
		X = NH * COSLAT * COSLONG;
		Y = NH * COSLAT * SINLONG;
		Z = (b * b * N / (a * a) + height) * SINLAT;
		return new double[] { X, Y, Z };
	}

	public static String ECEFtoWGS84(double x, double y, double z){
		double a, b, c, d;
		double Longitude;// 经度
		double Latitude;// 纬度
		double Altitude;// 海拔高度
		double p, q;
		double N;
		a = 6378137.0;
		b = 6356752.31424518;
		c = Math.sqrt(((a * a) - (b * b)) / (a * a));
		d = Math.sqrt(((a * a) - (b * b)) / (b * b));
		p = Math.sqrt((x * x) + (y * y));
		q = Math.atan2((z * a), (p * b));
		Longitude = Math.atan2(y, x);
		Latitude = Math.atan2((z + (d * d) * b * Math.pow(Math.sin(q), 3)),
				(p - (c * c) * a * Math.pow(Math.cos(q), 3)));
		N = a / Math.sqrt(1 - ((c * c) * Math.pow(Math.sin(Latitude), 2)));
		Altitude = (p / Math.cos(Latitude)) - N;
		Longitude = Longitude * 180.0 / Math.PI;
		Latitude = Latitude * 180.0 / Math.PI;
		return Longitude + "," + Latitude + "," + Altitude;
	}

}
