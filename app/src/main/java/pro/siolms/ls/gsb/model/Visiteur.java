package pro.siolms.ls.gsb.model;

import java.util.ArrayList;

public class Visiteur {
    private String matricule;
    private String login;
    private String nom;
    private String prenom;
    private String mdp;
    private ArrayList<Rapport> rapports;

    /**
     * Instancie le visiteur
     * @param matricule
     * @param nom
     * @param prenom
     * @param login
     * @param mdp
     */
    public Visiteur(String matricule, String nom, String prenom, String login, String mdp) {
        this.matricule = matricule;
        this.login = login;
        this.nom = nom;
        this.prenom = prenom;
        this.mdp = mdp;
    }

    /**
     * Pour obtenir le matricule
     * @return
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * Pour obtenir le login
     * @return
     */
    public String getLogin() {
        return login;
    }

    /**
     * Pour obtenir le nom
     * @return
     */
    public String getNom() {
        return nom;
    }

    /**
     * Pour obtenir le pronem
     * @return
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Pour obtenir le mot de passe
     * @return
     */
    public String getMdp() {
        return mdp;
    }

    /**
     * Pour obtenir les Rapports
     * @return
     */
    public ArrayList<Rapport> getRapports(){
        return rapports;
    }

    /**
     * Permet de modifier les rapports
     * @param rapports
     */
    public void setRapports(ArrayList<Rapport> rapports){
        this.rapports = rapports;
    }
}
