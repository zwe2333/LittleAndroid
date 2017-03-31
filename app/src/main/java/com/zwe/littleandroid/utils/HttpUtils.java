package com.zwe.littleandroid.utils;

import com.google.gson.Gson;
import com.zwe.littleandroid.model.ChatMessage;
import com.zwe.littleandroid.model.Result;

import java.io.IOException;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Asus on 2017/3/31.
 */

public class HttpUtils {
    private static final String URL = "http://www.tuling123.com/openapi/api";
    private static final String API_KEY = "0a881092951c4193b51aeece7d98796d";
    public static String getData(String msg){
        String content="";
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(setParam(msg))
                .build();
        Response response= null;
        try {
            response = client.newCall(request).execute();
            content=response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    public static ChatMessage sendMessage(String msg)
    {
        ChatMessage chatMessage = new ChatMessage();
        String jsonRes = getData(msg);
        Gson gson = new Gson();
        Result result = null;
        try
        {
            result = gson.fromJson(jsonRes, Result.class);
            chatMessage.setMsg(result.getText());
        } catch (Exception e)
        {
            chatMessage.setMsg("服务器繁忙，请稍候再试");
        }
        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.INCOMING);
        return chatMessage;
    }

    private static String setParam(String msg){
        String url="";
        url=URL+"?key="+API_KEY+"&info="+msg;
        return url;
    }
}
