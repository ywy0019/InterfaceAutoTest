package com.ywy.itest;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.mail.MessagingException;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.ywy.itest.utils.Common;


public class CheckMsg {

	private static String url = "map-changchun/rest/maps/³¤´ºÊÐÇøÍ¼.rjson";

	@Test
	public void test_changchun() throws IOException, MessagingException, GeneralSecurityException {
		boolean result = false;
		String data = Common.get(url);
		if (data == null) {
			Common.sendMail("can't access iserver demo");
		} else {
			JSONObject jdata = Common.jsonObj(data);
			if (jdata.has("error")) {
				Common.sendMail("failed to get data");
			} else {
				Object bounds = jdata.get("bounds");
//				String top = Common.getNum("{\"top\":0,\"left\":48.39718933631297,\"bottom\":-7668.245292074532,\"leftBottom\":{\"x\":48.39718933631297,\"y\":-7668.245292074532},\"right\":8958.850874968857,\"rightTop\":{\"x\":8958.850874968857,\"y\":-55.577652310411075}}");
				String top = Common.getNum(bounds.toString());
				if (top.equals("0") || top == null || top == "") {
					System.out.println("");
					Common.sendMail("top is 0");
				}else{
					result = true;
				}
			}
		}
		Assert.assertEquals(true, result);
	}
}
