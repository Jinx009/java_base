package utils;

//import java.math.BigDecimal;
//import java.text.DecimalFormat;

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

	public static String ECEFtoWGS84(double x, double y, double z) {
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

	public static double[] BLHtoXYZ(double B, double L, double H, double aAxis, double bAxis) {
		double dblD2R = Math.PI / 180;
		double e1 = Math.sqrt(Math.pow(aAxis, 2) - Math.pow(bAxis, 2)) / aAxis;

		double N = aAxis / Math.sqrt(1.0 - Math.pow(e1, 2) * Math.pow(Math.sin(B * dblD2R), 2));
		double targetX = (N + H) * Math.cos(B * dblD2R) * Math.cos(L * dblD2R);
		double targetY = (N + H) * Math.cos(B * dblD2R) * Math.sin(L * dblD2R);
		double targetZ = (N * (1.0 - Math.pow(e1, 2)) + H) * Math.sin(B * dblD2R);
		return new double[] { targetX, targetY, targetZ };
	}

	public static double[] ecefToEnu(double x, double y, double z, double lat, double lng, double height) {
		double a = 6378137;
		double b = 6356752.3142;
		double f = (a - b) / a;
		double e_sq = f * (2 - f);
		double lamb = Math.toRadians(lat);
		double phi = Math.toRadians(lng);
		double s = Math.sin(lamb);
		double N = a / Math.sqrt(1 - e_sq * s * s);
		double sin_lambda = Math.sin(lamb);
		double cos_lambda = Math.cos(lamb);
		double sin_phi = Math.sin(phi);
		double cos_phi = Math.cos(phi);

		double x0 = (height + N) * cos_lambda * cos_phi;
		double y0 = (height + N) * cos_lambda * sin_phi;
		double z0 = (height + (1 - e_sq) * N) * sin_lambda;

		double xd = x - x0;
		double yd = y - y0;
		double zd = z - z0;

		double t = -cos_phi * xd - sin_phi * yd;

		double xEast = -sin_phi * xd + cos_phi * yd;
		double yNorth = t * sin_lambda + cos_lambda * zd;
		double zUp = cos_lambda * cos_phi * xd + cos_lambda * sin_phi * yd + sin_lambda * zd;
//		DecimalFormat df = new DecimalFormat("###################.###########");
//		System.out.println(df.format(new BigDecimal(xEast).setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue()) + "-"
//				+ df.format(new BigDecimal(yNorth).setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue()) + "-"
//				+ df.format(new BigDecimal(zUp).setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue()));
		return new double[] { xEast, yNorth, zUp };
	}

	public static double[] wgs84ToEcef(double lat, double lon, double h) {
		double a = 6378137;
		double b = 6356752.3142;
		double f = (a - b) / a;
		double e_sq = f * (2 - f);
		double lamb = Math.toRadians(lat);
		double phi = Math.toRadians(lon);
		double s = Math.sin(lamb);
		double N = a / Math.sqrt(1 - e_sq * s * s);

		double sin_lambda = Math.sin(lamb);
		double cos_lambda = Math.cos(lamb);
		double sin_phi = Math.sin(phi);
		double cos_phi = Math.cos(phi);

		double x = (h + N) * cos_lambda * cos_phi;
		double y = (h + N) * cos_lambda * sin_phi;
		double z = (h + (1 - e_sq) * N) * sin_lambda;
//		DecimalFormat df = new DecimalFormat("###################.###########");
//		System.out.println(df.format(new BigDecimal(x).setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue()) + "-"
//				+ df.format(new BigDecimal(y).setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue()) + "-"
//				+ df.format(new BigDecimal(z).setScale(10, BigDecimal.ROUND_HALF_UP).doubleValue()));
		return new double[]{x,y,z};
	}

	public static void main(String[] args) {
		// System.out.println(MapUtils.GetDistance(29.490295, 106.486654,
		// 29.615467,
		// 106.581515));
		// System.out.println(Double.valueOf("29.490295"));
		// double[] d = WGS84toECEF(30.7335894 / 1000, 103.9681949 / 1000,
		// 495441 / 1000);
		// System.out.println(d[0] + "--" + d[1] + "---" + d[2]);
		// System.out.println(ECEFtoWGS84(-1426814.8942052593, 5736207.14002754,
		// 3492541.7182463093));
		// BLHtoXYZ(30.7335894, 103.9681949, 495441, 6378137, 6356752.31424518);
		// DecimalFormat decimalFormat = new
		// DecimalFormat("###################.###########");
		// double[] d = WGS84toECEF(30.7335918, 103.9681825, 494.356);
		// System.out.println(decimalFormat.format(d[0] * 1000) + "--" +
		// decimalFormat.format(d[1] * 1000) + "---"
		// + decimalFormat.format(d[2] * 1000));
		// d = WGS84toECEF(30.7335918, 103.9681824, 494.356);
		// System.out.println(decimalFormat.format(d[0] * 1000) + "--" +
		// decimalFormat.format(d[1] * 1000) + "---"
		// + decimalFormat.format(d[2] * 1000));
		double[] arr1 = wgs84ToEcef(30.7335905, 103.968191, 493.080);
		double[] arr2 = wgs84ToEcef(30.7335904, 103.968191, 493.087);
		ecefToEnu(arr1[0],arr1[1], arr1[2],30.7335905, 103.968191,  493.080);
		ecefToEnu(arr2[0],arr2[1], arr2[2],30.7335905, 103.968191,  493.080);
	}

}
