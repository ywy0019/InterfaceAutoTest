package com.auto.test.ITest;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendFileTest{
	public static void main(String[] args){
	OkHttpClient client = new OkHttpClient();
	try{
	//定义请求中传输媒体类型为多文件格式
	MediaType mediaType =  MediaType.parse("multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW");
	//定义上传文件的位置
	String file_path="./src/test/resources/Penguins.jpg";
	File file=new File(file_path);
	//定义一个builder
	MultipartBody.Builder builder = new MultipartBody.Builder();
	builder.setType(mediaType);
	builder.addFormDataPart("fileField",file.getPath(),RequestBody.create(mediaType, file));
	
	MultipartBody mul = builder.build();
	//发送请求，在请求中需要加入Cookie进行身份验证
	Request request = new Request.Builder()
	  .url("https://www.imooc.com/user/postpic")
	  .post(mul)
	  .addHeader("content-type",  "multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW")
	  .addHeader("Cache-Control", "no-cache")
	  .addHeader("Cookie", "test")
	  .build();

	Response response = client.newCall(request).execute();
	response.body().string();
	
	}catch(IOException e){
		e.printStackTrace();
	}
	}
}
