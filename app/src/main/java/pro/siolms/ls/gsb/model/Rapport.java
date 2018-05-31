package pro.siolms.ls.gsb.model;

import java.util.ArrayList;

public class Rapport {

    private int id;
    private String date;
    private String bilan;
    private String motif;
    private Praticien praticien;


    /**
     * Intstacie le rapport
     * @param id
     * @param date
     * @param bilan
     * @param motif
     * @param praticien
     */
    public Rapport(int id, String date, String bilan, String motif, Praticien praticien) {
        this.id = id;
        this.date = date;
        this.bilan = bilan;
        this.motif = motif;
        this.praticien = praticien;
    }

    /**
     * Pour obtenir l'id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Pour obtenir la date
     * @return
     */
    public String getDate() {
        return date;
    }

    /**
     * Pour obtenir le bilan
     * @return
     */
    public String getBilan() {
        return bilan;
    }

    /**
     * Pour obtenir le motif
     * @return
     */
    public String getMotif() {
        return motif;
    }

    /**
     * Pour obtenir le praticien
     * @return
     */
    public Praticien getPraticien() {
        return praticien;
    }

}
