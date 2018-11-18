package com.example.vlad.edittext;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ServerJob {

    static class NetTask extends AsyncTask<Void,Void,Void> {

        String body;
        NetTask(String body){
            this.body =body;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            executePost("http://stormy-sea-14278.herokuapp.com/send", body);
            return null;
        }
    }

    static class NetTask2 extends AsyncTask<Void,Void,Void>{

        String body;
        TextView view;
        String message;
        NetTask2(String body, TextView view){
            this.body =body;
            this.view=view;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            message=executePost("http://stormy-sea-14278.herokuapp.com/get", body);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            view.setText(message);
        }
    }
    public static void sendMessage(String message, String receiver){
        NetTask task=new NetTask(message+"\n"+receiver);
        task.execute();
    }
    public static void getMessages(String receiver, TextView view){
        NetTask2 task2=new NetTask2(receiver,view);
        task2.execute();
    }

    private static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                    "text/plain");

            connection.setRequestProperty("Content-Length",
                    Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                    connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
