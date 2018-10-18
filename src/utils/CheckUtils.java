package utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import copyleaks.sdk.api.*;
import copyleaks.sdk.api.exceptions.CommandFailedException;
import copyleaks.sdk.api.models.ProcessOptions;
import copyleaks.sdk.api.models.ResultRecord;

public class CheckUtils {
	
	private static final Logger log = LoggerFactory.getLogger(CheckUtils.class);
	
	private final static String ARG_EMAIL_VALUE = "albert_1111@126.com";
	private final static String ARG_APIKEY_VALUE = "CAE70852-576E-4015-855D-519394CF2D19";


	static String DisplayBar(int i) {
		StringBuilder sb = new StringBuilder();
		int x = i / 2;
		sb.append("|");
		for (int k = 0; k < 50; k++)
			sb.append(((x <= k) ? " " : "="));
		sb.append("|");

		return sb.toString();
	}

	public static String compare(String urlPath,String filePath) {

		try {
			CopyleaksCloud copyleaks = new CopyleaksCloud(eProduct.Businesses);
			log.warn("Login to Copyleaks cloud...");
			copyleaks.Login(ARG_EMAIL_VALUE,ARG_APIKEY_VALUE);
			log.warn("Login Done...{}");
			log.warn("Checking account balance...");
			int creditsBalance = copyleaks.getCredits();
			log.warn("Done (" + creditsBalance + " credits)!");
			if (creditsBalance == 0) {
				log.warn("ERROR: You have insufficient credits for scanning content! (current credits balance = {})",creditsBalance);
				return "fail";
			}
			ProcessOptions scanOptions = new ProcessOptions();
			scanOptions.setSandboxMode(true);
			ResultRecord[] results;
			CopyleaksProcess createdProcess;
			if (!"".equals(urlPath)) {
				createdProcess = copyleaks.CreateByUrl(new URI(urlPath), scanOptions);
			} else {
				createdProcess = copyleaks.CreateByFile(new File(filePath), scanOptions);
			}

			log.warn("Scanning...");
			int percents = 0;
			while (percents != 100 && (percents = createdProcess.getCurrentProgress()) <= 100) {
				log.warn("\r{} {}%",DisplayBar(percents),percents);
				if (percents != 100)
					Thread.sleep(5000);
			}
			results = createdProcess.GetResults();
			log.warn(JSONObject.toJSONString(results));
			if (results.length == 0) {
				return "fail1";
			} else {
				String res = "";
				for (int i = 0; i < results.length; ++i) {
					res+= "similar words:"+results[i].getNumberOfCopiedWords()+";identical:" + results[i].getPercents()+"%;detail: "+results[i].getEmbededComparison()+"\n\r";
				}
				return res;
			}
		} catch (CommandFailedException copyleaksException) {
			log.error("Failed!");
			System.out.format("*** Error (%d):\n", copyleaksException.getCopyleaksErrorCode());
			log.error(copyleaksException.getMessage());
		} catch (Exception ex) {
			log.error("Failed !Unhandled Exception,{}",ex);
		}
		return "fail2";
	}
	
	public static void main(String[] args) throws IOException, CommandFailedException {
//		compare("", "/Users/jinx/Downloads/q.txt");
		System.out.println();
	}
}
