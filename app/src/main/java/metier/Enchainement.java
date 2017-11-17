package metier;

import java.util.ArrayList;

/**
 * Created by vbobet on 06/10/2017.
 */

public class Enchainement {

    public static ArrayList<Enchainement> tousLesEnchainements = new ArrayList<>();
    private String posture;
    private int nbResp;


    public Enchainement(String unePosture, int unNbResp){
        posture = unePosture;
        nbResp = unNbResp;
    }

    public static void addEnchainement(String unePosture, int unNbResp){
        tousLesEnchainements.add(new Enchainement(unePosture,unNbResp));
    }

    public static void setTousLesEnchainements(ArrayList<Enchainement> laListe){
        tousLesEnchainements = laListe;
    }

    public String getPosture(){
        return posture;
    }

    public int getNbResp(){
        return nbResp;
    }

    public static ArrayList<Enchainement> getTousLesEnchainements(){ return tousLesEnchainements; }

}
