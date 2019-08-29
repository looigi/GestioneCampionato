package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.maschere.Statistiche;
import looigi.gestionecampionato.utilities.Utility;

import java.util.List;

public class AdapterStatistiche extends ArrayAdapter {
    private Context context;
    private List<String> lista;

    public AdapterStatistiche(Context context, int textViewResourceId, List<String> objects)
    {
        super(context, textViewResourceId, objects);

        this.context = context;
        this.lista=objects;
    }

    @Override
    @Nullable
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        String[] campi = {};
        TextView campo1;
        TextView campo2;
        TextView campo3;
        ImageView imgStat;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (Statistiche.QualeStatistica) {
            case 1:
            case 2:
                convertView = inflater.inflate(R.layout.listview_statistiche, null);

                imgStat=convertView.findViewById(R.id.imgStat);
                campo1=convertView.findViewById(R.id.campo1);
                campo2=convertView.findViewById(R.id.campo2);
                campo3=convertView.findViewById(R.id.txtNumero);

                campi = lista.get(position).split(";",-1);

                campo1.setText(campi[1]);
                campo2.setText(campi[2]);
                campo3.setText("");

                Utility.getInstance().PrendeImmagineAvversario(campi[0], imgStat);

                break;
            case 3:
            case 4:
            case 5:
            case 6:
                convertView = inflater.inflate(R.layout.listview_statistiche, null);

                imgStat=convertView.findViewById(R.id.imgStat);
                campo1=convertView.findViewById(R.id.campo1);
                campo2=convertView.findViewById(R.id.campo2);
                campo3=convertView.findViewById(R.id.txtNumero);

                campi = lista.get(position).split(";",-1);

                // if (campi.length > 2) {
                    campo1.setText(campi[1] + " " + campi[2]);
                    // if (campi.length > 3) {
                        String goals = campi[3];
                        if (goals.contains("§")) {
                            String[] c = goals.split("§", -1);
                            int g = Integer.parseInt(c[0]);
                            int r = Integer.parseInt(c[1]);
                            goals = Integer.toString(g+r) + " (" + c[1] +")";
                        }
                        campo2.setText(goals);

                        campo3.setText(campi[4]);
                    // } else {
                    //     campo2.setText("");
                    // }
                // } else {
                // if (campi.length > 1) {
                        // campo1.setText(campi[1]);
                        //     }
                //     campo2.setText("");
                // }

                Utility.getInstance().PrendeImmagineGiocatore(campi[0], imgStat);

                break;
            case 7:
            case 8:
                convertView = inflater.inflate(R.layout.listview_statistiche, null);

                imgStat=convertView.findViewById(R.id.imgStat);
                campo1=convertView.findViewById(R.id.campo1);
                campo2=convertView.findViewById(R.id.campo2);
                campo3=convertView.findViewById(R.id.txtNumero);

                campi = lista.get(position).split(";",-1);

                int id = -1;
                int coloreTesto=-1;
                String qualeImmagine="";

                if (campi[0].toUpperCase().contains ("VITTOR")) {
                    qualeImmagine="vittoria";
                    coloreTesto=Color.GREEN;
                } else if (campi[0].toUpperCase().contains ("PAREGG")) {
                    qualeImmagine="pareggio";
                    coloreTesto=Color.BLACK;
                } else if (campi[0].toUpperCase().contains ("SCONFIT")) {
                    qualeImmagine="sconfitta";
                    coloreTesto=Color.RED;
                } else if (campi[0].toUpperCase().contains ("GOAL") && campi[0].toUpperCase().contains ("FATT")) {
                    qualeImmagine="goal_fatti";
                    coloreTesto=Color.argb(255, 0, 180, 0);
                } else if (campi[0].toUpperCase().contains ("GOAL") && campi[0].toUpperCase().contains ("SUBIT")) {
                    qualeImmagine="goal_subiti";
                    coloreTesto=Color.RED;
                } else if (campi[0].toUpperCase().contains ("GIOCAT")) {
                    qualeImmagine="giocate";
                    coloreTesto=Color.BLACK;
                }

                if (qualeImmagine.isEmpty()) {
                    imgStat.setVisibility(LinearLayout.GONE);
                } else {
                    id = VariabiliStaticheGlobali.getInstance().getContextPrincipale().getResources()
                            .getIdentifier("looigi.gestionecampionato:drawable/" + qualeImmagine, null, null);

                    Drawable myIcon = VariabiliStaticheGlobali.getInstance().getContextPrincipale().getResources().getDrawable(id);
                    int colore=-1;

                    if (campi[0].toUpperCase().contains("CASA")) {
                        colore=Color.BLUE;
                    } else if (campi[0].toUpperCase().contains("FUORI")) {
                        colore=Color.RED;
                    } else {
                        colore=Color.BLACK;
                    }

                    myIcon=setTint(myIcon, colore);

                    imgStat.setBackgroundDrawable(myIcon);
                    imgStat.setVisibility(LinearLayout.VISIBLE);
                }

                campo2.setTextColor(coloreTesto);

                campo1.setText(campi[0]);
                campo2.setText(campi[1]);
                campo3.setText("");

                break;
        }

        if (Utility.getInstance().ePari(position)) {
            convertView.setBackgroundColor(Color.WHITE);
        } else {
            convertView.setBackgroundColor(Color.argb(255,230,230,230));
        }

        return convertView;
    }

    private Drawable setTint(Drawable d, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }
}
