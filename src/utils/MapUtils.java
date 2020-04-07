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
		System.out.println(MapUtils.GetDistance(29.490295, 106.486654, 29.615467, 106.581515));
		System.out.println(Double.valueOf("29.490295"));
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
		return new double[] {X,Y,Z};
	}

}
