package pro.siolms.ls.gsb.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import pro.siolms.ls.gsb.R;
import pro.siolms.ls.gsb.controller.MyRequest;
import pro.siolms.ls.gsb.controller.Singleton;
import pro.siolms.ls.gsb.model.Session;

public class MainActivity extends AppCompatActivity {
    //proprietes
    private Button btn;
    private EditText Login, MotDePasse, Matricule;
    private ProgressBar loader;
    private Handler handler;
    private RequestQueue queue;
    private MyRequest request;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn_connection);
        Login = findViewById(R.id.login);
        MotDePasse = findViewById(R.id.mdp);
        Matricule = findViewById(R.id.matricule);
        loader = findViewById(R.id.loader);

        queue = Singleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this, queue);
        handler = new Handler();
        session = new Session(this);
        isLogged();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String mat = Matricule.getText().toString().trim();
                final String log = Login.getText().toString().trim();
                final String mdp = MotDePasse.getText().toString().trim();
                loader.setVisibility(View.VISIBLE);

                //Si les champs sont complete
                if(mat.length()>0 && log.length()>0 && mdp.length()>0){

                    //Animation pour l'affichage du loader
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //Requete a la base de donnees
                            request.connexion(mat, log, mdp, new MyRequest.LoginCallback() {

                                //Lorsque que la requète fonctionne
                                @Override
                                public void onSuccess(String matricule, String nom, String prenom, String login, String mdp) {
                                    loader.setVisibility(View.GONE);
                                    session.insertVisiteur(matricule, nom, prenom, login, mdp);
                                    //Envoie les information vers une autre page
                                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                                    startActivity(intent);
                                    finish();
                                }

                                //lorsque la requête ne fonctionne pas
                                @Override
                                public void onError(String message) {
                                    loader.setVisibility(View.GONE);
                                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    },1000); //Une seconde

                }else {
                    loader.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Veuillez rempir tous les champs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Vérifie si l'utilisateur est connecté et envoie vers la sélection des rapports
     */
    public void isLogged(){
        if(session.islogged()){
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
            finish();
        }
    }


}