package looigi.gestionecampionato.maschere;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.MainActivity;
import looigi.gestionecampionato.R;
import looigi.gestionecampionato.adapter.AdapterSquadre;
import looigi.gestionecampionato.adapter.AdapterStatAllenamenti;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.dati.VariabiliStaticheStatAllenamenti;
import looigi.gestionecampionato.db_locale.DBLocaleUtenti;
import looigi.gestionecampionato.db_remoto.DBRemotoAvversari;
import looigi.gestionecampionato.db_remoto.DBRemotoGenerale;
import looigi.gestionecampionato.db_remoto.DBRemotoUtenti;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.utilities.Utility;

public class SceltaSquadra extends android.support.v4.app.Fragment {
    private Context context;
    private static ListView lv;
    private static TextView txtSquadra;
    public static String SquadraScelta = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=null;

        try {
            view=(inflater.inflate(R.layout.scelta_squadra, container, false));
        } catch (Exception ignored) {

        }

        if (view!=null) {
            VariabiliStaticheGlobali.getInstance().setViewActivity(view);

            initializeGraphic();
        }

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    private void initializeGraphic() {
        final View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            lv = view.findViewById(R.id.lstSceltaSquadra);
            txtSquadra = view.findViewById(R.id.txtNomeSquadra);

            LinearLayout layAnnulla = view.findViewById(R.id.layAnnullaSquadra);
            Button btnAnnulla = view.findViewById(R.id.btnAnnullaSquadra);
            layAnnulla.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    System.exit(0);
                }
            });
            btnAnnulla.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    System.exit(0);
                }
            });

            LinearLayout layOk = view.findViewById(R.id.layOkSquadra);
            Button btnOk = view.findViewById(R.id.btnOkSquadra);
            layOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ControllaSquadra();
                }
            });
            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ControllaSquadra();
                }
            });

            DBRemotoGenerale dbr = new DBRemotoGenerale();
            dbr.RitornaSquadrePerSceltaIniziale(VariabiliStaticheGlobali.getInstance().getContext());
        }
    }

    public static void fillListView(List<String> lista) {
        AdapterSquadre adp = new AdapterSquadre(
                VariabiliStaticheGlobali.getInstance().getContext(),
                android.R.layout.simple_list_item_1,
                lista);
        lv.setAdapter(adp);
    }

    public static void ScriveSquadra(String Sq) {
        SquadraScelta = Sq;
        txtSquadra.setText("Squadra scelta: " + Sq);
    }

    private void ControllaSquadra() {
        if (SquadraScelta.isEmpty()) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    "Selezionare una squadra", true,
                    VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            String SquadraScelta = SceltaSquadra.SquadraScelta.trim().replace(" ","_");

            DBRemotoGenerale dbr = new DBRemotoGenerale();
            dbr.ControllaEsistenzaDB(VariabiliStaticheGlobali.getInstance().getContext(), SquadraScelta);
        }
    }
}
