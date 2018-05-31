package pro.siolms.ls.gsb.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import pro.siolms.ls.gsb.R;
import pro.siolms.ls.gsb.controller.MyRequest;
import pro.siolms.ls.gsb.controller.Singleton;
import pro.siolms.ls.gsb.model.Rapport;
import pro.siolms.ls.gsb.model.Session;
import pro.siolms.ls.gsb.model.Visiteur;
import pro.siolms.ls.gsb.controller.ListAdapter;

public class UserActivity extends AppCompatActivity {
    private TextView Login, User;
    private Button btn;
    private Session session;
    private Visiteur visiteur;
    private RequestQueue queue;
    private MyRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        btn = findViewById(R.id.deconnection);
        Login = findViewById(R.id.login);
        User = findViewById(R.id.user);

        session = new Session(this);
        visiteur = session.getVisiteur();
        queue = Singleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);


        String log = visiteur.getLogin();
        String user = visiteur.getNom() + " " + visiteur.getPrenom();

        Login.setText(log);
        User.setText(user);

        //Lorsqu'on click sur le boutton
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.loggout();
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //Requete a la base de donnees
        final String mat = visiteur.getMatricule();

        //Ajout de la liste de rapports da la classe visiteur
        request.report(mat, new MyRequest.ReportCallback() {
            @Override
            public void onSuccess(ArrayList<Rapport> lesRapports) {
                visiteur.setRapports(lesRapports);

                //Creation de la ListView
                if(visiteur.getRapports() != null){
                    ListView lstRapport = findViewById(R.id.lstRapport);
                    ListAdapter adapter = new ListAdapter(UserActivity.this, visiteur.getRapports());
                    lstRapport.setAdapter(adapter);
                }

            }

            @Override
            public void onError(String message) {

                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     *Envoie la position sélectionnée vers RappportActivity
     * @param position
     */
    public void viewRepport(int position){

        //Envoie le rapport selectionnée dans la session
        Rapport rapport = visiteur.getRapports().get(position);
        session.selectionRapport(rapport);

        //Redirection vers la page RapportActivity
        Intent intent = new Intent(UserActivity.this, RapportActivity.class);
        startActivity(intent);
    }



}
