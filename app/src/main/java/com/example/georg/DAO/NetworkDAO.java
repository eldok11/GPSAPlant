package com.example.georg.DAO;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;

/**
 * Created by Georg on 23.12.2017.
 */

public class NetworkDAO {
    /**
     * navigate to given uri, and return data from uri
     * @param uri
     * @return
     */
    public String request(String uri){
        String returnString="";
        StringBuilder sb=new StringBuilder();
        HttpURLConnection urlConnection=null;
        try {
            URL url=new URL(uri);
            urlConnection=(HttpURLConnection)url.openConnection();
            //not read all at once, so buffer, better form memory
            InputStream in=new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bin=new BufferedReader(new InputStreamReader(in));
            String inputline;
            while((inputline=bin.readLine()) != null){
                sb.append(inputline);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }


        return sb.toString();

    }
}
