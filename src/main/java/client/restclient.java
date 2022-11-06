package client;

import org.apache.http.client.methods.*;
import org.apache.http.entity.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.*;
import org.json.*;

import java.io.*;
import java.util.*;


public class restclient {

 public static CloseableHttpResponse closeablehttpGETresponse;
public static CloseableHttpResponse closeablehttpPOSTresponse;
    public static void GET(String url, HashMap<String,String> headers) throws IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);

        for (Map.Entry<String,String> entry :headers.entrySet()){
            httpget.addHeader(entry.getKey(),entry.getValue());
        }

        httpclient.execute(httpget); //HIT the GET Method
        closeablehttpGETresponse = httpclient.execute(httpget);
    }

    public static void POST(String url,String entityString, HashMap<String,String> headers) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(url);

        httppost.setEntity(new StringEntity(entityString));

        for (Map.Entry<String,String> entry :headers.entrySet()){
            httppost.addHeader(entry.getKey(),entry.getValue());
        }
        httpclient.execute(httppost); //HIT the POST Method
        closeablehttpPOSTresponse = httpclient.execute(httppost);
    }



































    public static String getValueByJPath(String url, String jpath,HashMap<String,String> headers) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);

        for (Map.Entry<String,String> entry :headers.entrySet()){
            httpget.addHeader(entry.getKey(),entry.getValue());
        }

        httpclient.execute(httpget); //HIT the GET Method
        CloseableHttpResponse closeablehttpresponse = httpclient.execute(httpget);
        String response = EntityUtils.toString(closeablehttpresponse.getEntity(), "UTF-8");
        JSONObject jsonresponse =new JSONObject(response);

        Object obj=jsonresponse;
        for (String s : jpath.split("/"))
            if (!s.isEmpty())
                if (!(s.contains("[") || s.contains("]")))
                 obj=((JSONObject) obj).get(s);
        else if (s.contains("[") || s.contains("]"))
            obj=((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(
                    Integer.parseInt(s.split("\\[")[1].replace("]","")));
            return obj.toString();
        }


    }

