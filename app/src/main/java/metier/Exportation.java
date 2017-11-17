package metier;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vbobet on 17/11/2017.
 */

public class Exportation extends AsyncTask<String,Void,Boolean> {
    @Override
    protected Boolean doInBackground(String... urls) {
        try{
            URL url = new URL(urls[0]);
            HttpURLConnection cnx = (HttpURLConnection)url.openConnection();
            cnx.setDoOutput(true);
            OutputStreamWriter osw = new OutputStreamWriter(cnx.getOutputStream());
            osw.write(urls[1]);
            osw.flush();
            osw.close();
            int httpResul = cnx.getResponseCode();
            if(httpResul==HttpURLConnection.HTTP_OK) {
                return true;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
