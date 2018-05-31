package pro.siolms.ls.gsb.controller;


import android.content.Context;

import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pro.siolms.ls.gsb.model.Praticien;
import pro.siolms.ls.gsb.model.Rapport;


public class MyRequest {

    private Context context;
    private RequestQueue queue;

    /**
     * Intancie la class MyRequest.java.
     * @param context
     * @param queue
     */
    public MyRequest(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }

    /**
     * Lance une requête SQL pour la connection de l'utilisateur.
     * @param mat
     * @param login
     * @param mdp
     * @param callback
     */
    public void connexion(final String mat, final String login, final String mdp, final LoginCallback callback){

        String url = "http://192.168.0.30/android/connection.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            //Lorsqu'il y a une reponse
            @Override
            public void onResponse(String response) {

                JSONObject json = null;
                try {

                    json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    //S'il n'a pas d'erreur dans le Matricule, le Login et le Mote de Passe
                    if(!error){

                        //Recuperation de la reponse sous format JSON
                        String matricule = json.getString("matricule");
                        String nom = json.getString("nom");
                        String prenom = json.getString("prenom");
                        String login = json.getString("login");
                        String mdp = json.getString("mdp");

                        callback.onSuccess(matricule, nom, prenom, login, mdp);

                    }else{

                        callback.onError(json.getString("message"));

                    }
                } catch (JSONException e) {

                    callback.onError("Une erreur s'est produite");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            //Lorsqu'il y n'a pas de reponse
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof NetworkError){

                    callback.onError("Impossible de se connecter");

                }else if (error instanceof VolleyError){

                    callback.onError("Une erreur s'est produite");

                }
                Log.d("APP", "ERROR = "+error);
            }
        }){
            //Parametres qui seront envoyee avec la methode POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("mat", mat); //%_POST["mat"]
                map.put("log", login); //%_POST["log"]
                map.put("mdp", mdp); //%_POST["mdp"]

                return map;
            }
        };
        queue.add(request);
    }

    /**
     * Lance une requête SQL pour recuperer les rapports du visiteur qui est connecte.
     * @param mat
     * @param callback
     */
    public void report(final String mat, final ReportCallback callback){

        String url = "http://192.168.0.30/android/rapportvisite.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            //Lorsqu'il y a une reponse
            @Override
            public void onResponse(String response) {

                JSONObject json = null;
                try {

                    json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");
                    ArrayList<Rapport> lesRapports = new ArrayList<>();

                    //S'il n'a pas d'erreur dans le matricule
                    if(!error){
                        //Recuperation de la reponse sous format JSON
                        int nb = json.getInt("nb");

                        for (int c = 0; c < nb ; c++){

                            //initialisation du praticien
                            int pcode = json.getInt("praticien_code"+c);
                            String nom = json.getString("praticien_nom"+c);
                            String prenom = json.getString("praticien_prenom"+c);
                            String adresse = json.getString("praticien_adresse"+c);
                            String ville = json.getString("praticien_ville"+c);
                            int cp = json.getInt("praticien_cp"+c);
                            Praticien praticien = new Praticien(pcode, nom, prenom, adresse, ville, cp);

                            //Initialisation du rapport
                            int rcode = json.getInt("rapport_code"+c);
                            String date = json.getString("rapport_date"+c);
                            String bilan = json.getString("rapport_bilan"+c);
                            String motif = json.getString("rapport_motif"+c);
                            Rapport rapport = new Rapport(rcode, date, bilan, motif, praticien);

                            //Ajout du rapport
                            lesRapports.add(rapport);
                        }

                        callback.onSuccess(lesRapports);

                    }else{

                        callback.onError(json.getString("message"));

                    }
                } catch (JSONException e) {

                    callback.onError("Une erreur s'est produite");
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            //Lorsqu'il y n'a pas de reponse
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof NetworkError){

                    callback.onError("Impossible de se connecter");

                }else if (error instanceof VolleyError){

                    callback.onError("Une erreur s'est produite");

                }
                Log.d("APP", "ERROR = "+error);
            }
        }){
            //Parametres qui seront envoyee avec la methode POST
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("mat", mat); //%_POST["mat"]

                return map;
            }
        };
        queue.add(request);

    }

    /**
     * Creation du onSuccess et du onError pour la connection.
     */
    public interface LoginCallback{
        //Fonctions qui seront defini dans MainActivity
        void onSuccess(String matricule, String nom, String prenom, String login, String mdp);
        void onError(String message);
    }

    /**
     * Creation du onSuccess et du onError pour les rapports de visites.
     */
    public interface ReportCallback{
        //Fonctions qui seront defini dans MainActivity
        void onSuccess (ArrayList<Rapport> lesRapports);
        void onError(String message);
    }
}