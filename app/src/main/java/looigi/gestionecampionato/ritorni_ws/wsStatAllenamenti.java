package looigi.gestionecampionato.ritorni_ws;

import android.content.Context;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.dati.VariabiliStaticheAllenamenti;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheStatAllenamenti;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.StatisticheAllenamenti;

public class wsStatAllenamenti {
    private Handler hSelezionaRiga;
    private Runnable runRiga;

    private String ToglieTag(String Cosa) {
        return Cosa;
    }

    public void RitornaInfo(Context context, String Ritorno, String Maschera) {
        String Appoggio = ToglieTag(Ritorno);
        if (Appoggio.toUpperCase().contains("ERROR:")) {
            VariabiliStaticheStatAllenamenti.getInstance().setInfo(new ArrayList());

            StatisticheAllenamenti.fillListViewInfo();

            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<String> lista = new ArrayList<>();
            String[] c = Appoggio.split("§",-1);
            for (String cc : c) {
                if (!cc.trim().isEmpty()) {
                    lista.add(cc);
                }
            }

            VariabiliStaticheStatAllenamenti.getInstance().setInfo(lista);

            StatisticheAllenamenti.fillListViewInfo();
        }
    }

    public void RitornaStatAllenamentiCategoria(Context context, String Ritorno, String Maschera) {
        String Appoggio = ToglieTag(Ritorno);
        if (Appoggio.toUpperCase().contains("ERROR:")) {
            VariabiliStaticheStatAllenamenti.getInstance().setStatisticheAllenamenti(new ArrayList());

            StatisticheAllenamenti.fillListViewStatAllenamenti();
        } else {
            List<String> lista = new ArrayList<>();
            String[] c = Appoggio.split("§",-1);
            for (String cc : c) {
                if (!cc.trim().isEmpty()) {
                    lista.add(cc);
                }
            }
            VariabiliStaticheStatAllenamenti.getInstance().setStatisticheAllenamenti(lista);

            StatisticheAllenamenti.fillListViewStatAllenamenti();
        }
    }
}
