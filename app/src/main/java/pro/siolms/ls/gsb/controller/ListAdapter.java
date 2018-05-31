package pro.siolms.ls.gsb.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import pro.siolms.ls.gsb.R;
import pro.siolms.ls.gsb.view.UserActivity;
import pro.siolms.ls.gsb.model.Rapport;

public class ListAdapter extends BaseAdapter {

    private ArrayList<Rapport> lesRapports;
    private LayoutInflater inflater;
    private Context context;

    /**
     * Instancie la classe ListAdapter
     * @param context
     * @param lesRapports
     */
    public ListAdapter(Context context, ArrayList<Rapport> lesRapports){
        this.lesRapports = lesRapports;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**
     * Retourne le nombre de lignes
     * @return
     */
    @Override
    public int getCount() {
        return lesRapports.size();
    }

    /**
     * Retourne l'item de la ligne actuelle
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return lesRapports.get(i);
    }

    /**
     * Retourne un indice par rapport à ligne actuelle
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Retourne la ligne formaté avec la gestion des évènements
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Declaration d'un Holder
        ViewHolder holder;

        //Si la ligne n'existe pas
        if(view == null){

            holder = new ViewHolder();

            //La ligne est construite avec un formatage relie à layout_liste_rapport
            view = inflater.inflate(R.layout.layout_liste_rapport, null);

            //Chaque propriété du holder est relié à une propriété graphique
            holder.lst_date = view.findViewById(R.id.lst_date);
            holder.lst_motif = view.findViewById(R.id.lst_motif);
            holder.lst_praticien = view.findViewById(R.id.lst_praticien);
            holder.btn_lstRapport = view.findViewById(R.id.btn_lstRapport);

            //Affectation du holder
            view.setTag(holder);

        }else {

            //Récupération du holder dans la vue existante
            holder = (ViewHolder) view.getTag();
        }

        //Valorisation du contenue du holder
        holder.lst_date.setText("Date : " + lesRapports.get(i).getDate().toString());
        holder.lst_motif.setText("Motif : " + lesRapports.get(i).getMotif().toString());
        holder.lst_praticien.setText("Praticien : " + lesRapports.get(i).getPraticien().getNom() + " " + lesRapports.get(i).getPraticien().getPrenom());

        //Pour récupérer l'indice
        holder.btn_lstRapport.setTag(i);

        //Envoie vers la pages que affiche les détails du rapport selectionné
        holder.btn_lstRapport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int)v.getTag();
                ((UserActivity)context).viewRepport(position);
            }
        });

        return view;
    }

    //Sous classe holder
    private class ViewHolder{
        ImageButton btn_lstRapport;
        TextView lst_date, lst_motif, lst_praticien;

    }
}
