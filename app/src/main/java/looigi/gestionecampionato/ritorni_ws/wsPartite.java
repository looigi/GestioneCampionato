package looigi.gestionecampionato.ritorni_ws;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.db_remoto.DBRemotoDirigenti;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.Home;
import looigi.gestionecampionato.maschere.NuovaPartita;

import static looigi.gestionecampionato.dati.VariabiliStaticheGlobali.RadiceWS;

public class wsPartite {
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    private String ToglieTag(String Cosa) {
        return Cosa;
    }

    public void RitornaPartitaDaID(final Context context, String Ritorno, final String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            NuovaPartita.RiempieCampiPartita(Ritorno);

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoDirigenti dbr = new DBRemotoDirigenti();
                    dbr.RitornaDirigentiCategoria(context, Integer.toString(VariabiliStaticheNuovaPartita.getInstance().idCategoriaScelta), Maschera);
                }
            }, 50);
        }
    }

    public void RitornaPartite(final Context context, String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            if (!Appoggio.contains("Nessuna partita")) {
                DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                        Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
            } else {
                List<String> Partite=new ArrayList<>();

                VariabiliStaticheMain.getInstance().setPartite(Partite);

                Home.RiempieListaPartite();
            }
        } else {
            List<String> Partite=new ArrayList<>();
            String Righe[]=Appoggio.split("§");
            for (String s : Righe) {
                Partite.add(s);
            }

            VariabiliStaticheMain.getInstance().setPartite(Partite);

            Home.RiempieListaPartite();
        }
    }

    public void RitornaIdPartita(final Context context, String Ritorno, final String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            VariabiliStaticheNuovaPartita.getInstance().idPartita=Integer.parseInt(Appoggio);

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoCategorie dbr = new DBRemotoCategorie();
                    dbr.RitornaCategorie(context, Maschera);
                }
            }, 50);
        }
    }

    public void CreaFoglioConvocazioni(final Context context, String Ritorno, final String idPartita) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            String pagina_web = RadiceWS+"Convocazioni/" + VariabiliStaticheGlobali.getInstance().getNomeSquadra() + "/" +
                    idPartita + ".html";

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pagina_web));
            VariabiliStaticheGlobali.getInstance().getContextPrincipale().startActivity(browserIntent);
        }
    }

    public void SalvaPartita(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            VariabiliStaticheNuovaPartita.getInstance().PartitaSalvata=true;
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                    "Partita salvata",
                    false,
                    VariabiliStaticheGlobali.NomeApplicazione);
            VariabiliStaticheMain.getInstance().setPartite(null);
            // Utility.getInstance().CambiaMaschera(R.id.home, -1, -1);
        }
    }
}
