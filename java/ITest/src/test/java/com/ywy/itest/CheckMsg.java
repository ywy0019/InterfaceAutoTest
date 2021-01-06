package com.ywy.itest;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.mail.MessagingException;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.ywy.itest.utils.Common;


public class CheckMsg {

	private static String url = "test.rjson";

	@Test
	public void test_changchun() throws IOException, MessagingException, GeneralSecurityException {
		boolean result = false;
		String data = Common.get(url);
		if (data == null) {
			Common.sendMail("can't access server");
		} else {
			JSONObject jdata = Common.jsonObj(data);
			if (jdata.has("error")) {
				Common.sendMail("failed to get data");
			} else {
				Object bounds = jdata.get("bounds");
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
