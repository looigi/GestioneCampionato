package looigi.gestionecampionato.ritorni_ws;

import android.content.Context;
import android.os.Handler;
import android.widget.RelativeLayout;

import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheAllenatori;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.db_remoto.DBRemotoAllenatori;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.Allenatori;
import looigi.gestionecampionato.maschere.NuovaPartita;

import java.util.ArrayList;
import java.util.List;

public class wsAllenatori {
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    private String ToglieTag(String Cosa) {
        return Cosa;
    }

    public void SalvaAllenatore(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(), Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                    "Allenatore salvato",
                    false,
                    VariabiliStaticheGlobali.NomeApplicazione);
            //VariabiliStaticheMain.getInstance().setPartite(null);
            VariabiliStaticheAllenatori.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoAllenatori dbr = new DBRemotoAllenatori();
                    dbr.RitornaAllenatoriCategoria(VariabiliStaticheAllenatori.getInstance().getContext(),
                            Integer.toString(VariabiliStaticheAllenatori.getInstance().idCategoriaScelta),
                            NomiMaschere.getInstance().getAllenatori());
                }
            }, 50);
        }
    }

    public void RitornaAllenatoriCategoria(Context context, String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<String> descAllenatore = new ArrayList<>();
            List<Integer> idAllenatore = new ArrayList<>();

            String cc[]=Appoggio.split("§");
            for (String ccc : cc) {
                String c[]=ccc.split(";",-1);
                idAllenatore.add(Integer.parseInt(c[0]));

                if (Maschera.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                    descAllenatore.add(c[1]+" "+c[2]);
                } else {
                    if (Maschera.equals(NomiMaschere.getInstance().getAllenatori())) {
                        descAllenatore.add(c[0] + ";" + c[1] + ";" + c[2] + ";" + c[3] + ";" + c[4] + ";");
                    }
                }
            }

            if (Maschera.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                VariabiliStaticheNuovaPartita.getInstance().setAllenatore(descAllenatore);
                VariabiliStaticheNuovaPartita.getInstance().setIdAllenatore(idAllenatore);

                NuovaPartita.fillSpinnerAllenatori();
            } else {
                if (Maschera.equals(NomiMaschere.getInstance().getAllenatori())) {
                    VariabiliStaticheAllenatori.getInstance().setAllenatori(descAllenatore);
                    VariabiliStaticheAllenatori.getInstance().setIdAllenatori(idAllenatore);

                    Allenatori.fillListViewAllenatori();
                }
            }
        }
    }

    public void EliminaAllenatore(String Ritorno, final String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), "Allenatore eliminato", false, VariabiliStaticheGlobali.NomeApplicazione);

            VariabiliStaticheAllenatori.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoAllenatori dbr = new DBRemotoAllenatori();
                    dbr.RitornaAllenatoriCategoria(VariabiliStaticheAllenatori.getInstance().getContext(),
                            Integer.toString(VariabiliStaticheAllenatori.getInstance().idCategoriaScelta),
                            NomiMaschere.getInstance().getAllenatori());
                }
            }, 50);
        }
    }
}
