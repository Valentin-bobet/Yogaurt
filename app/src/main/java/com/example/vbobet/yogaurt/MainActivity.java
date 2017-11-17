package com.example.vbobet.yogaurt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import metier.Enchainement;
import metier.EnchainementAdapter;
import metier.Exportation;
import metier.Importation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPref = (Button) findViewById(R.id.btnPref);
        btnPref.setOnClickListener(observerClickButton);

        Button btnExport = (Button) findViewById(R.id.btnExport);
        btnExport.setOnClickListener(observerClickButton);

        Button btnImport = (Button) findViewById(R.id.btnImport);
        btnImport.setOnClickListener(observerClickButton);

        Button btnFile = (Button) findViewById(R.id.btnFile);
        btnFile.setOnClickListener(observerClickButton);

        Button btnSQLite = (Button) findViewById(R.id.btnSQLite);
        btnSQLite.setOnClickListener(observerClickButton);

        Button btnVoirPref = (Button) findViewById(R.id.btnVoirPref);
        btnVoirPref.setOnClickListener(observerClickButton);

        Button btnVoirFile = (Button) findViewById(R.id.btnVoirFile);
        btnVoirFile.setOnClickListener(observerClickButton);

        Button btnVoirSQLite = (Button) findViewById(R.id.btnVoirSQLite);
        btnVoirSQLite.setOnClickListener(observerClickButton);
    }

    private View.OnClickListener observerClickButton = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Log.i("Parseur","obs");
            switch (v.getId()){
                case R.id.btnExport:
                    TextView txtPosture = (TextView) findViewById(R.id.txtPosture);
                    TextView txtRes = (TextView) findViewById(R.id.txtRespi);
                    String posture = txtPosture.getText().toString();
                    int nbResp = Integer.parseInt(txtRes.getText().toString());
                    Exportation tacheExport = new Exportation();
                    Enchainement exo = new Enchainement(posture, nbResp);
                    Gson gson = new Gson();
                    tacheExport.execute("http://10.0.2.2/yogappli/export.php",gson.toJson(exo));
                    try{
                        if(tacheExport.get()){
                            Log.i("Parseur","Enregistrement efectué");
                        }
                        else{
                            Log.i("Parseur","Problèmes lors de la lecture du fichier");
                        }
                    }catch (InterruptedException e){
                        Log.i("Parseur", "Interruption lecture fichier"+e.getMessage());
                    }catch (ExecutionException e) {
                        Log.i("Parseur","Erreur execution"+e.getMessage());
                    }
                    break;

                case R.id.btnImport:
                    Spinner spin = (Spinner) findViewById(R.id.spinnerPostures);
                    Importation tacheImport = new Importation();
                    tacheImport.execute("http://10.0.2.2/yogappli/import.php");
                    try{
                        ArrayList<Enchainement> listeImportee = tacheImport.get();
                        if(listeImportee!=null){
                            EnchainementAdapter adapter;
                            adapter=new EnchainementAdapter(getApplicationContext(), listeImportee);
                            spin.setAdapter(adapter);
                            Log.i("Parseur","ok");
                        }
                        else{
                            Log.i("Parseur","Problèmes lors de la lecture du fichier");
                        }
                    }catch (InterruptedException e) {
                        Log.i("Parseur", "Interruption lecture fichier"+ e.getMessage());
                    }catch (ExecutionException e){
                        Log.i("Parseur", "Erreur execution"+ e.getMessage());
                    }
                    break;

                case R.id.btnPref:
                    txtPosture = (TextView) findViewById(R.id.txtPosture);
                    txtRes = (TextView) findViewById(R.id.txtRespi);
                    posture = txtPosture.getText().toString();
                    nbResp = Integer.parseInt(txtRes.getText().toString());
                    ArrayList<Enchainement> list;
                    SharedPreferences mesPrefs;
                    mesPrefs = getApplicationContext().getSharedPreferences("maSeanceDeYoga",0);
                    SharedPreferences.Editor monEditeur = mesPrefs.edit();
                    gson = new Gson();
                    String str=mesPrefs.getString("liste", "");
                    if(str.equals("")){
                        list = new ArrayList<>();
                    }
                    else{
                        list = gson.fromJson(str, new TypeToken<ArrayList<Enchainement>>(){}.getType());
                    }
                    Enchainement.setTousLesEnchainements(list);
                    Enchainement.addEnchainement(posture, nbResp);
                    monEditeur.putString("liste", gson.toJson(Enchainement.tousLesEnchainements));
                    monEditeur.apply();
                    break;

                    // Enchainement exo = new Enchainement(txtPosture.getText().toString(), txtRespi);
                    // SharedPreferences mesPrefs;
                    // mesPrefs = getApplicationContext().getSharedPreferences("mesVarGlobales",0);
                    // SharedPreferences.Editor monEditeurDePreferences = mesPrefs.edit();
                    // int nb=mesPrefs.getInt("nbEnchainements",0);
                    //  nb++;
                    // monEditeurDePreferences.putInt("nbEnchainements",nb);
                    // Gson gson = new Gson();
                    // monEditeurDePreferences.putString(String.valueOf(nb),gson.toJson(exo));
                    // monEditeurDePreferences.apply();
                    // Toast.makeText(getApplicationContext(),
                    //          "Enregistrement effectué ",
                    //           Toast.LENGTH_LONG).show();
                    //   break;

                case R.id.btnVoirPref:
                    Intent i;
                    i = new Intent(getApplicationContext(),Result.class);
                    i.putExtra("choixMethode","Prefs");
                    startActivity(i);
            }
        }
    };
}
