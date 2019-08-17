package looigi.gestionecampionato.ritorni_ws;

import android.content.Context;
import android.os.Handler;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheDirigenti;
import looigi.gestionecampionato.dati.VariabiliStaticheEventi;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.db_remoto.DBRemotoArbitri;
import looigi.gestionecampionato.db_remoto.DBRemotoDirigenti;
import looigi.gestionecampionato.db_remoto.DBRemotoEventi;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.Dirigenti;
import looigi.gestionecampionato.maschere.Eventi;
import looigi.gestionecampionato.maschere.NuovaPartita;

public class wsEventi {
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    private String ToglieTag(String Cosa) {
        return Cosa;
    }

    public void SalvaEvento(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(), Appoggio,
                    true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            //VariabiliStaticheMain.getInstance().setPartite(null);
            VariabiliStaticheEventi.getInstance().getLayMascheraModEventi().setVisibility(RelativeLayout.GONE);

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoEventi dbr = new DBRemotoEventi();
                    dbr.RitornaEventi(VariabiliStaticheEventi.getInstance().getContext(),
                            NomiMaschere.getInstance().getEventiPerTitolo());
                }
            }, 50);

            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                    "Evento salvato",
                    false,
                    VariabiliStaticheGlobali.NomeApplicazione);
        }
    }

    public void RitornaEventi(Context context, String Ritorno, final String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), Appoggio,
                    true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<String> Evento = new ArrayList<>();

            String[] cc=Appoggio.split("§");
            for (String ccc : cc) {
                Evento.add(ccc);
            }

            if (Maschera.equals(NomiMaschere.getInstance().getNuovaPartitaPerTitolo())) {
                VariabiliStaticheNuovaPartita.getInstance().setEventiLista(Evento);
                NuovaPartita.fillSpinnerEventiLista();
            }
            if (Maschera.equals(NomiMaschere.getInstance().getEventiPerTitolo())) {
                VariabiliStaticheEventi.getInstance().setEventi(Evento);
                Eventi.fillListViewEventi();
            }
        }
    }

    public void EliminaEvento(String Ritorno, final String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    "Evento eliminato", false, VariabiliStaticheGlobali.NomeApplicazione);

            VariabiliStaticheEventi.getInstance().getLayMascheraModEventi().setVisibility(RelativeLayout.GONE);

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoEventi dbr = new DBRemotoEventi();
                    dbr.RitornaEventi(VariabiliStaticheEventi.getInstance().getContext(),
                            NomiMaschere.getInstance().getEventi());
                }
            }, 50);
        }
    }
}
