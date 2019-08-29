package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheSettings;
import looigi.gestionecampionato.db_remoto.DBRemotoGenerale;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.utilities.Utility;

public class Settings extends android.support.v4.app.Fragment {
    private Context context;
    private static String TAG= NomiMaschere.getInstance().getSettings();
    private static String AnnoSelezionato;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        VariabiliStaticheSettings.getInstance().setContext(this.getActivity());

        View view=null;

        try {
            view=(inflater.inflate(R.layout.settings, container, false));
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

        //isVisible=isVisibleToUser;

        //if (isVisible) {
        //    initializeGraphic();
        //}
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //if (isVisible) {
        //    initializeGraphic();
        //}
    }

    private void initializeGraphic() {
        final Context context = VariabiliStaticheGlobali.getInstance().getContext();
        View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            TextView txtUtente = view.findViewById(R.id.txtUtente);
            VariabiliStaticheSettings.getInstance().setSpnAnni((Spinner) view.findViewById(R.id.spnAnno));

            String tipo = "";
            if (VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia().equals("1")) {
                tipo = "Amministratore";
            } else {
                tipo = "Utente";
            }
            txtUtente.setText(VariabiliStaticheGlobali.getInstance().getDatiUtente().getUtente() + " ("+tipo+")");

            String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

            Button cmdOkAnno = view.findViewById(R.id.cmdOkAnno);
            if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                cmdOkAnno.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (!AnnoSelezionato.isEmpty()) {
                            String[] c = AnnoSelezionato.split(";", -1);

                            DBRemotoGenerale dbr = new DBRemotoGenerale();
                            dbr.ImpostaAnnoAttualeUtente(context, c[0]);

                        /* VariabiliStaticheGlobali.getInstance().setAnnoInCorso(Integer.parseInt(c[0]));
                        VariabiliStaticheGlobali.getInstance().setDescAnnoInCorso(c[1]);
                        VariabiliStaticheGlobali.getInstance().PulisceTutteLeVariabili();

                        Utility.getInstance().ScriveAnno();

                        DBLocale dbl = new DBLocale();
                        dbl.SalvaOpzioni(VariabiliStaticheSettings.getInstance().getContext()); */

                            // DialogMessaggio.getInstance().show(VariabiliStaticheSettings.getInstance().getContext(), "Anno modificato", false, VariabiliStaticheGlobali.NomeApplicazione);
                        }
                    }
                });
                VariabiliStaticheSettings.getInstance().getSpnAnni().setEnabled(true);
            } else {
                cmdOkAnno.setEnabled(false);
                VariabiliStaticheSettings.getInstance().getSpnAnni().setEnabled(false);
            }

            Button cmdCreaAnno = view.findViewById(R.id.cmdCreaAnno);
            if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                cmdCreaAnno.setEnabled(true);
                cmdCreaAnno.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Utility.getInstance().CambiaMaschera(-1, -1, -1);
                    }
                });
                cmdCreaAnno.setEnabled(true);
            } else {
                cmdCreaAnno.setEnabled(false);
            }

            Button cmdPermessiUtenti = view.findViewById(R.id.cmdPermessiUtenti);
            if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                cmdPermessiUtenti.setEnabled(true);
                cmdPermessiUtenti.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Utility.getInstance().CambiaMaschera(-2, -1, -1);
                    }
                });
                cmdPermessiUtenti.setEnabled(true);
            } else {
                cmdPermessiUtenti.setEnabled(false);
            }

            if (VariabiliStaticheSettings.getInstance().getAnni()==null){
                DBRemotoGenerale dbr = new DBRemotoGenerale();
                dbr.RitornaAnni(context, TAG);
            } else {
                fillSpinnersAnni();
            }

            Button cmdRefreshFoto = view.findViewById(R.id.cmdRefreshFoto);
            cmdRefreshFoto.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String NomeSquadra = VariabiliStaticheGlobali.getInstance().getNomeSquadra();

                    File d= new File(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/"+NomeSquadra+"/Allenatori");
                    Utility.getInstance().deleteFolder(d);
                    d= new File(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/Arbitri");
                    Utility.getInstance().deleteFolder(d);
                    d= new File(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/Avversari");
                    Utility.getInstance().deleteFolder(d);
                    d= new File(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/"+NomeSquadra+"/Categorie");
                    Utility.getInstance().deleteFolder(d);
                    d= new File(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/"+NomeSquadra+"/Dirigenti");
                    Utility.getInstance().deleteFolder(d);
                    d= new File(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/"+NomeSquadra+"/Giocatori");
                    Utility.getInstance().deleteFolder(d);

                    Utility.getInstance().CreaCartelle(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/"+NomeSquadra+"/Giocatori");
                    Utility.getInstance().CreaCartelle(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/"+NomeSquadra+"/Categorie");
                    Utility.getInstance().CreaCartelle(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/Avversari");
                    Utility.getInstance().CreaCartelle(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/"+NomeSquadra+"/Allenatori");
                    Utility.getInstance().CreaCartelle(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/"+NomeSquadra+"/Dirigenti");
                    Utility.getInstance().CreaCartelle(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/Arbitri");

                    DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                            "Immagini di base eliminate.\nAl successivo accesso si ricaricheranno.",
                            true, VariabiliStaticheGlobali.NomeApplicazione);
                }
            });
        }
    }

    public static void fillSpinnersAnni() {
        if (VariabiliStaticheSettings.getInstance().getAnni() != null) {
            List<String> AnniDesc = new ArrayList<>();
            String Ricerca = "";

            for (String s : VariabiliStaticheSettings.getInstance().getAnni()) {
                String c[] = s.split(";", -1);

                if (Integer.parseInt(c[0]) == VariabiliStaticheGlobali.getInstance().getAnnoInCorso()) {
                    Ricerca=c[1];
                }
                AnniDesc.add(c[1]);
            }

            final ArrayAdapter<String> adapterAnni = new ArrayAdapter<>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_per_utenti, AnniDesc);
            adapterAnni.setDropDownViewResource(R.layout.spinner_item_per_utenti);
            VariabiliStaticheSettings.getInstance().getSpnAnni().setAdapter(adapterAnni);

            int pos = Utility.getInstance().CercaESettaStringaInSpinner(VariabiliStaticheSettings.getInstance().getSpnAnni(), Ricerca);
            if (pos>-1) {
                VariabiliStaticheSettings.getInstance().getSpnAnni().setSelection(pos);
            }

            VariabiliStaticheSettings.getInstance().getSpnAnni().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String s = VariabiliStaticheSettings.getInstance().getAnni().get(position);
                    AnnoSelezionato=s;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

        }
    }
}
