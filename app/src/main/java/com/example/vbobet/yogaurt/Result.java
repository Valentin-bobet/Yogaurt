package com.example.vbobet.yogaurt;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import metier.Enchainement;
import metier.EnchainementAdapter;



/**
 * Created by vbobet on 06/10/2017.
 */

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Bundle b = getIntent().getExtras();
        String choix = b.getString("choixMethode");
        switch(choix){
            case "Prefs":
                Gson gson = new Gson();
                SharedPreferences mesPrefs;
                mesPrefs= this.getSharedPreferences("maSeanceDeYoga", 0);
                String str = mesPrefs.getString("liste", "");
                ArrayList<Enchainement> listeValues = gson.fromJson(str, new TypeToken<ArrayList<Enchainement>>() {
                }.getType());
                EnchainementAdapter adapter = new EnchainementAdapter(getApplicationContext(),listeValues);
                ListView liste = (ListView) findViewById(R.id.listeEnchainements);
                liste.setAdapter(adapter);

                liste.setOnItemClickListener(observateurClickItem);

                break;



//        int nb = mesPrefs.getInt("maSeanceDeYoga",0);
//        for(int i=1;i<=nb;i++){
//            String str=mesPrefs.getString(String.valueOf(i), "");
//            Enchainement exo =gson.fromJson(str, Enchainement.class);
//            Toast.makeText(getApplicationContext(),
//                    exo.getPosture(),
//                    Toast.LENGTH_SHORT).show();
//            }
//                break;
        }
    }

    private AdapterView.OnItemClickListener observateurClickItem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(),
                    ((Enchainement) parent.getItemAtPosition(position)).getPosture(),
                    Toast.LENGTH_SHORT).show();
        }
    };

}
