package looigi.gestionecampionato.ritorni_ws;

import android.content.Context;
import android.os.Handler;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheDirigenti;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.db_remoto.DBRemotoArbitri;
import looigi.gestionecampionato.db_remoto.DBRemotoDirigenti;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.Dirigenti;
import looigi.gestionecampionato.maschere.NuovaPartita;

public class wsDirigenti {
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    private String ToglieTag(String Cosa) {
        return Cosa;
    }

    public void SalvaDirigente(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(), Appoggio,
                    true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                    "Dirigente salvato",
                    false,
                    VariabiliStaticheGlobali.NomeApplicazione);
            //VariabiliStaticheMain.getInstance().setPartite(null);
            VariabiliStaticheDirigenti.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoDirigenti dbr = new DBRemotoDirigenti();
                    dbr.RitornaDirigentiCategoria(VariabiliStaticheDirigenti.getInstance().getContext(),
                            Integer.toString(VariabiliStaticheDirigenti.getInstance().idCategoriaScelta),
                            NomiMaschere.getInstance().getDirigenti());
                }
            }, 50);
        }
    }

    public void RitornaDirigentiCategoria(Context context, String Ritorno, final String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), Appoggio,
                    true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<String> descDirigente = new ArrayList<>();
            List<Integer> idDirigente = new ArrayList<>();

            String cc[]=Appoggio.split("§");
            for (String ccc : cc) {
                String c[]=ccc.split(";",-1);
                idDirigente.add(Integer.parseInt(c[0]));

                if (Maschera.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                    descDirigente.add(c[1]+" "+c[2]);
                } else {
                    if (Maschera.equals(NomiMaschere.getInstance().getDirigenti())) {
                        descDirigente.add(c[0] + ";" + c[1] + ";" + c[2] + ";" + c[3] + ";" + c[4] + ";");
                    }
                }
            }

            if (Maschera.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                VariabiliStaticheNuovaPartita.getInstance().setDirigente(descDirigente);
                VariabiliStaticheNuovaPartita.getInstance().setIdDirigente(idDirigente);

                NuovaPartita.fillSpinnerDirigenti();

                hSelezionaRiga = new Handler();
                hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                    @Override
                    public void run() {
                        DBRemotoArbitri dbr = new DBRemotoArbitri();
                        dbr.RitornaArbitri(VariabiliStaticheDirigenti.getInstance().getContext(),
                                Maschera);
                    }
                }, 50);
            } else {
                if (Maschera.equals(NomiMaschere.getInstance().getDirigenti())) {
                    VariabiliStaticheDirigenti.getInstance().setDirigenti(descDirigente);
                    VariabiliStaticheDirigenti.getInstance().setIdDirigenti(idDirigente);

                    Dirigenti.fillListViewDirigenti();
                }
            }
        }
    }

    public void EliminaDirigente(String Ritorno, final String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), "Dirigente eliminato", false, VariabiliStaticheGlobali.NomeApplicazione);

            VariabiliStaticheDirigenti.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoDirigenti dbr = new DBRemotoDirigenti();
                    dbr.RitornaDirigentiCategoria(VariabiliStaticheDirigenti.getInstance().getContext(),
                            Integer.toString(VariabiliStaticheDirigenti.getInstance().idCategoriaScelta),
                            NomiMaschere.getInstance().getDirigenti());
                }
            }, 50);
        }
    }
}
