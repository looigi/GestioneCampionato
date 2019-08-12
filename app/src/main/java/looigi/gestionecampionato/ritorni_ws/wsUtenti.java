package looigi.gestionecampionato.ritorni_ws;

import android.os.Handler;
import android.support.v4.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.StrutturaDatiUtente;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.dati.VariabiliStaticheUtenti;
import looigi.gestionecampionato.db_locale.DBLocaleUtenti;
import looigi.gestionecampionato.db_remoto.DBRemotoGenerale;
import looigi.gestionecampionato.db_remoto.DBRemotoUtenti;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.ModificaUtente;
import looigi.gestionecampionato.utilities.Utility;

public class wsUtenti {
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    private String ToglieTag(String Cosa) {
        return Cosa;
    }

    public void RitornaUtenteDaID(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            String[] Campi = Appoggio.split(";",-1);

            StrutturaDatiUtente s = new StrutturaDatiUtente();
            s.setIdUtente(Campi[1]);
            s.setUtente(Campi[2]);
            s.setCognome(Campi[3]);
            s.setNome(Campi[4]);
            s.setPassword(Campi[5]);
            s.setEMail(Campi[6]);
            s.setIdCategoria1(Campi[7]);
            s.setIdTipologia(Campi[8]);
            s.setDescCategoria(Campi[9]);

            VariabiliStaticheGlobali.getInstance().setDatiUtente(s);

            Utility.getInstance().CambiaMaschera(R.id.home, -1, -1);
        }
    }

    public void RitornaUtentePerLogin(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            String[] Campi = Appoggio.split(";",-1);

            StrutturaDatiUtente s = new StrutturaDatiUtente();
            s.setIdUtente(Campi[1]);
            s.setUtente(Campi[2]);
            s.setCognome(Campi[3]);
            s.setNome(Campi[4]);
            s.setPassword(Campi[5]);
            s.setEMail(Campi[6]);
            s.setIdCategoria1(Campi[7]);
            s.setIdTipologia(Campi[8]);
            s.setDescCategoria(Campi[9]);

            VariabiliStaticheGlobali.getInstance().setDatiUtente(s);

            VariabiliStaticheMain.getInstance().setPartitaApplicazione(true);
            VariabiliStaticheMain.getInstance().getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            DBLocaleUtenti dbl = new DBLocaleUtenti();
            dbl.SalvaDatiUtente(VariabiliStaticheGlobali.getInstance().getContext(),
                    VariabiliStaticheGlobali.getInstance().getDatiUtente());

            DBRemotoGenerale dbrg = new DBRemotoGenerale();
            dbrg.RitornaAnnoAttualeUtenti(VariabiliStaticheGlobali.getInstance().getContext(), "UTENTI");

            // Utility.getInstance().CambiaMaschera(R.id.home, -1, -1);
        }
    }

    public void SalvaUtente(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            VariabiliStaticheGlobali.getInstance().getDatiUtente().setIdUtente(Appoggio);

            DBLocaleUtenti dbl = new DBLocaleUtenti();
            dbl.SalvaDatiUtente(VariabiliStaticheGlobali.getInstance().getContext(),
                    VariabiliStaticheGlobali.getInstance().getDatiUtente());

            DBRemotoGenerale dbrg = new DBRemotoGenerale();
            dbrg.RitornaAnnoAttualeUtenti(VariabiliStaticheGlobali.getInstance().getContext(), "UTENTI");

            // Utility.getInstance().CambiaMaschera(R.id.home, -1, -1);
        }
    }

    public void RitornaModificaUtente(String Ritorno) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoUtenti dbr = new DBRemotoUtenti();
                    dbr.RitornaListaUtenti(VariabiliStaticheUtenti.getInstance().getContext());
                }
            }, 50);

            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    "Utente modificato", true, VariabiliStaticheGlobali.NomeApplicazione);
        }
    }

    public void RitornaListaUtenti(String Ritorno) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<String> descUtenti = new ArrayList<>();
            // List<String> descCategorie = new ArrayList<>();
            // List<Integer> idCategorie = new ArrayList<>();

            String t[] = Ritorno.split("£", -1);
            String c[] = t[0].split("§", -1);
            for (String cc : c) {
                if (!cc.isEmpty()) {
                    descUtenti.add(cc);
                }
            }
            VariabiliStaticheUtenti.getInstance().setUtenti(descUtenti);

            /* for (String d : descUtenti) {
                String dd[] = d.split(";");
                idCategorie.add(Integer.parseInt(dd[8]));
                descCategorie.add(dd[9]);
            }

            c = t[1].split("§", -1);
            for (String cc : c) {
                if (!cc.isEmpty()) {
                    String ccc[] = cc.split(";",-1);
                    idCategorie.add(Integer.parseInt(ccc[0]));
                    descCategorie.add(ccc[1]);
                }
            }

            VariabiliStaticheUtenti.getInstance().setIdCategorie(idCategorie);
            VariabiliStaticheUtenti.getInstance().setNomiSquadre(descCategorie); */

            ModificaUtente.RiempieListaUtenti();
        }
    }
}
