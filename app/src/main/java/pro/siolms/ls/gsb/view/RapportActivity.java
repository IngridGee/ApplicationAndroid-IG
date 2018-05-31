package pro.siolms.ls.gsb.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

import pro.siolms.ls.gsb.R;
import pro.siolms.ls.gsb.model.Rapport;
import pro.siolms.ls.gsb.model.Session;
import pro.siolms.ls.gsb.model.Visiteur;


public class RapportActivity extends AppCompatActivity {

    private Session session;
    private Visiteur visiteur;
    private Rapport rapport;
    private ArrayList<Rapport> lesRapports;
    private TextView title, visit, date, motif, pratic, bilan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapport);

        //Instanciation des varaibles
        session = new Session(this);
        visiteur = session.getVisiteur();
        rapport = session.getRapportSelectionnee();

        //Définition des vues
        title = findViewById(R.id.title);
        visit = findViewById(R.id.visit);
        date = findViewById(R.id.date);
        motif = findViewById(R.id.motif);
        pratic = findViewById(R.id.pratic);
        bilan = findViewById(R.id.bilan);

        //Modification des vues
        title.setText("Rapport n°" + rapport.getId());
        visit.setText(visiteur.getNom() + " " + visiteur.getPrenom());
        date.setText("Date : " + rapport.getDate());
        motif.setText("Motif : " + rapport.getMotif());
        pratic.setText(rapport.getPraticien().getNom() + " " + rapport.getPraticien().getPrenom());
        bilan.setText("Bilan : \n \n \n" + rapport.getBilan());

    }

}
