package looigi.gestionecampionato.ritorni_ws;

import android.content.Context;
import android.os.Handler;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheArbitri;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.db_remoto.DBRemotoArbitri;
import looigi.gestionecampionato.db_remoto.DBRemotoEventi;
import looigi.gestionecampionato.db_remoto.DBRemotoGenerale;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.Arbitri;
import looigi.gestionecampionato.maschere.NuovaPartita;

public class wsArbitri {
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    private String ToglieTag(String Cosa) {
        return Cosa;
    }

    public void SalvaArbitro(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(), Appoggio,
                    true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                    "Arbitro salvato",
                    false,
                    VariabiliStaticheGlobali.NomeApplicazione);
            // VariabiliStaticheArbitri.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoArbitri dbr = new DBRemotoArbitri();
                    dbr.RitornaArbitri(VariabiliStaticheArbitri.getInstance().getContext(),
                            NomiMaschere.getInstance().getArbitri());
                }
            }, 50);
        }
    }

    public void RitornaArbitri(Context context, String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), Appoggio,
                    true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<String> descArbitro = new ArrayList<>();
            List<Integer> idArbitro = new ArrayList<>();

            String[] cc=Appoggio.split("§");
            for (String ccc : cc) {
                String[] c=ccc.split(";",-1);
                idArbitro.add(Integer.parseInt(c[0]));

                if (Maschera.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                    descArbitro.add(c[1]+" "+c[2]);
                } else {
                    if (Maschera.equals(NomiMaschere.getInstance().getArbitri())) {
                        descArbitro.add(c[0] + ";" + c[1] + ";" + c[2] + ";" + c[3] + ";" + c[4] + ";");
                    }
                }
            }

            if (Maschera.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                VariabiliStaticheNuovaPartita.getInstance().setArbitro(descArbitro);
                VariabiliStaticheNuovaPartita.getInstance().setIdArbitro(idArbitro);

                NuovaPartita.fillSpinnerArbitri();

                hSelezionaRiga = new Handler();
                hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                    @Override
                    public void run() {
                        DBRemotoEventi dbr = new DBRemotoEventi();
                        dbr.RitornaEventi(VariabiliStaticheArbitri.getInstance().getContext(),
                                NomiMaschere.getInstance().getNuovaPartitaPerTitolo());
                    }
                }, 50);
            } else {
                if (Maschera.equals(NomiMaschere.getInstance().getArbitri())) {
                    VariabiliStaticheArbitri.getInstance().setArbitri(descArbitro);
                    VariabiliStaticheArbitri.getInstance().setIdArbitri(idArbitro);

                    Arbitri.fillListViewArbitri();
                }
            }
        }
    }

    public void EliminaArbitro(String Ritorno, final String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), "Arbitro eliminato", false, VariabiliStaticheGlobali.NomeApplicazione);

            VariabiliStaticheArbitri.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoArbitri dbr = new DBRemotoArbitri();
                    dbr.RitornaArbitri(VariabiliStaticheArbitri.getInstance().getContext(),
                            NomiMaschere.getInstance().getArbitri());
                }
            }, 50);
        }
    }
}
