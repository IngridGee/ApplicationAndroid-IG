package pro.siolms.ls.gsb.model;

public class Praticien {

    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String ville;
    private int codePostal;

    /**
     * Permet d'instancié le praticien
     * @param id
     * @param nom
     * @param prenom
     * @param adresse
     * @param ville
     * @param codePostal
     */
    public Praticien(int id, String nom, String prenom, String adresse, String ville, int codePostal) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.ville = ville;
        this.codePostal = codePostal;
    }

    /**
     * Pour obtenir l'id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Pour obtenir le nom
     * @return
     */
    public String getNom() {
        return nom;
    }

    /**
     * Pour obtenir le prenom
     * @return
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Pour obtenir l'adresse
     * @return
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     * Pour obtenir la ville
     * @return
     */
    public String getVille() {
        return ville;
    }

    /**
     * Pour obtenir le code postal
     * @return
     */
    public int getCodePostal() {
        return codePostal;
    }


}
