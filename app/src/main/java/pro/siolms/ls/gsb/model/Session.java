package pro.siolms.ls.gsb.model;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private static Session session = null ;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private final static String PREF_NAME = "app_prefs";
    private final static int PRIVATE_MODE = 0;
    private final static String IS_LOGGED = "is_logged";
    private static Visiteur visiteur = null;
    private static Rapport rapport = null;
    private Context context;

    /**
     * Instancie la session
     * @param context
     */
    public Session(Context context){
        this.context = context ;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    /**
     * Si la session existe
     * @return
     */
    public boolean islogged(){
        return preferences.getBoolean(IS_LOGGED, false);
    }

    /**
     * Permet d'insérer les infos du visiteur dans la session
     * @param matricule
     * @param nom
     * @param prenom
     * @param login
     * @param mdp
     */
    public void insertVisiteur(String matricule,String nom, String prenom, String login, String mdp){

        editor.putBoolean(IS_LOGGED, true);
        editor.putString("matricule", matricule);
        editor.putString("nom", nom);
        editor.putString("prenom", prenom);
        editor.putString("login", login);
        editor.putString("mdp", mdp);
        editor.commit();
    }



    /**
     * Supprime les infos de la session
     */
    public void loggout(){
        editor.clear().commit();
    }

    /**
     * Instancie le visiteur s'il n'est pas enocre instancié sinon le renvoie directement
     * @return
     */
    public Visiteur getVisiteur(){
        if(visiteur==null){
            String mat = preferences.getString("matricule", null);
            String nom = preferences.getString("nom", null);
            String prenom = preferences.getString("prenom", null);
            String login = preferences.getString("login", null);
            String mdp = preferences.getString("mdp", null);
            visiteur = new Visiteur(mat, nom, prenom, login, mdp);
        }

        return visiteur ;
    }

    /**
     * Permet d'insérer les infos du rapport selectionnée dans la session
     * @param rapport
     */
    public void selectionRapport(Rapport rapport){

        String date = preferences.getString("date",null);
        if(date!=null){
            editor.remove("rcode");
            editor.remove("date");
            editor.remove("bilan");
            editor.remove("motif");
            editor.remove("pcode");
            editor.remove("pnom");
            editor.remove("pprenom");
            editor.remove("adresse");
            editor.remove("ville");
            editor.remove("codePostal");
            editor.commit();
            this.rapport = null;
        }

        editor.putInt("rcode",rapport.getId());
        editor.putString("date",rapport.getDate());
        editor.putString("bilan",rapport.getBilan());
        editor.putString("motif",rapport.getMotif());
        editor.putInt("pcode",rapport.getPraticien().getId());
        editor.putString("pnom",rapport.getPraticien().getNom());
        editor.putString("pprenom",rapport.getPraticien().getPrenom());
        editor.putString("adresse",rapport.getPraticien().getAdresse());
        editor.putString("ville",rapport.getPraticien().getVille());
        editor.putInt("codePostal",rapport.getPraticien().getCodePostal());
        editor.commit();
    }

    /**
     * Renvoie le rapport selectionnée
     * @return
     */
    public Rapport getRapportSelectionnee(){
        if(rapport==null){
            int rcode = preferences.getInt("rcode", 0);
            String date = preferences.getString("date", null);
            String bilan = preferences.getString("bilan", null);
            String motif = preferences.getString("motif", null);
            int pcode = preferences.getInt("pcode", 0);
            String nom = preferences.getString("pnom", null);
            String prenom = preferences.getString("pprenom", null);
            String adresse = preferences.getString("adresse", null);
            String ville = preferences.getString("ville", null);
            int codePostal = preferences.getInt("codePostal", 0);

            Praticien praticien = new Praticien(pcode, nom, prenom, adresse, ville, codePostal);
            rapport = new Rapport(rcode, date, bilan, motif, praticien);
        }
        return rapport;
    }
}
