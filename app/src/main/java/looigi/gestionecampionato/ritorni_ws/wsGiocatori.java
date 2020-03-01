package looigi.gestionecampionato.ritorni_ws;

import android.content.Context;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheAllenamenti;
import looigi.gestionecampionato.dati.VariabiliStaticheAvversari;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.dati.VariabiliStaticheRose;
import looigi.gestionecampionato.db_remoto.DBRemotoGiocatori;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.Allenamenti;
import looigi.gestionecampionato.maschere.NuovaPartita;
import looigi.gestionecampionato.maschere.Rose;
import looigi.gestionecampionato.utilities.Utility;

public class wsGiocatori {
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    private String ToglieTag(String Cosa) {
        return Cosa;
    }

    public void SalvaGiocatore(String Ritorno, final String Ricerca, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                    "Giocatore salvato",
                    false,
                    VariabiliStaticheGlobali.NomeApplicazione);
            VariabiliStaticheRose.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoGiocatori dbr = new DBRemotoGiocatori();
                    dbr.RitornaGiocatoriCategoria(VariabiliStaticheAvversari.getInstance().getContext(),
                            Integer.toString(VariabiliStaticheRose.getInstance().idCategoriaScelta1),
                            NomiMaschere.getInstance().getRose());
                }
            }, 50);
        }
    }

    public void RitornaGiocatoriCategoria(Context context, String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<String> CognomeNome = new ArrayList<>();
            List<Integer> idGiocatore = new ArrayList<>();
            List<String> Ruolo = new ArrayList<>();
            List<Integer> idRuolo = new ArrayList<>();
            List<Integer> NumeroMaglia = new ArrayList<>();
            List<Integer> Categoria2 = new ArrayList<>();

            String[] gg=Appoggio.split("§");
            for (String ggg : gg) {
                String[] g=ggg.split(";", -1);

                if (Maschera.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                    idGiocatore.add(Integer.parseInt(g[0]));
                    idRuolo.add(Integer.parseInt(g[1]));
                    CognomeNome.add(g[2] + " " + g[3]);
                    Ruolo.add(g[4]);
                    if (Utility.getInstance().isNumeric(g[14])) {
                        NumeroMaglia.add(Integer.parseInt(g[14]));
                    } else {
                        NumeroMaglia.add(0);
                    }
                } else {
                    if (Maschera.equals(NomiMaschere.getInstance().getRose()) ||
                            Maschera.equals(NomiMaschere.getInstance().getAllenamenti())) {
                        String g1 = "";

                        for (String gc : g) {
                            g1 += gc + ";";
                        }

                        CognomeNome.add(g1);
                    }
                }
            }

            if (Maschera.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                VariabiliStaticheNuovaPartita.getInstance().setGiocatoreCognomeNome(CognomeNome);
                VariabiliStaticheNuovaPartita.getInstance().setGiocatoreID(idGiocatore);
                VariabiliStaticheNuovaPartita.getInstance().setGiocatoreRuolo(Ruolo);
                VariabiliStaticheNuovaPartita.getInstance().setGiocatoreIDRuolo(idRuolo);
                VariabiliStaticheNuovaPartita.getInstance().setGiocatoreNumeroMaglia(NumeroMaglia);

                NuovaPartita.fillSpinnerConvocati(VariabiliStaticheNuovaPartita.getInstance().getGiocatoreCognomeNome(),
                       VariabiliStaticheNuovaPartita.getInstance().getGiocatoreID(),
                        VariabiliStaticheNuovaPartita.getInstance().getGiocatoreRuolo(),
                        VariabiliStaticheNuovaPartita.getInstance().getGiocatoreIDRuolo(),
                        VariabiliStaticheNuovaPartita.getInstance().getGiocatoreNumeroMaglia(),
                        false);
            } else {
                if (Maschera.equals(NomiMaschere.getInstance().getRose())) {
                    VariabiliStaticheRose.getInstance().setGiocatori(CognomeNome);

                    Rose.fillListViewGiocatori();
                } else {
                    if (Maschera.equals(NomiMaschere.getInstance().getAllenamenti())) {
                        VariabiliStaticheAllenamenti.getInstance().setGiocatoriAssenti(CognomeNome);

                        VariabiliStaticheAllenamenti.getInstance().getLayDataOra().setVisibility(LinearLayout.VISIBLE);
                        VariabiliStaticheAllenamenti.getInstance().getLayTasti().setVisibility(LinearLayout.VISIBLE);
                        VariabiliStaticheAllenamenti.getInstance().getBtnRitornaAllenamenti().setVisibility(LinearLayout.VISIBLE);

                        Allenamenti.fillListViewGiocatoriAssenti();
                    }
                }
            }
        }
    }

    public void RitornaGiocatoriCategoriaSenzaAltri(Context context, String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<String> CognomeNome = new ArrayList<>();
            List<Integer> idGiocatore = new ArrayList<>();
            List<String> Ruolo = new ArrayList<>();
            List<Integer> idRuolo = new ArrayList<>();
            List<Integer> NumeroMaglia = new ArrayList<>();
            List<Integer> Categoria2 = new ArrayList<>();

            String[] gg=Appoggio.split("§");
            for (String ggg : gg) {
                String[] g=ggg.split(";", -1);

                if (Maschera.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                    idGiocatore.add(Integer.parseInt(g[0]));
                    idRuolo.add(Integer.parseInt(g[1]));
                    CognomeNome.add(g[2] + " " + g[3]);
                    Ruolo.add(g[4]);
                    if (Utility.getInstance().isNumeric(g[14])) {
                        NumeroMaglia.add(Integer.parseInt(g[14]));
                    } else {
                        NumeroMaglia.add(0);
                    }
                } else {
                    if (Maschera.equals(NomiMaschere.getInstance().getRose()) ||
                            Maschera.equals(NomiMaschere.getInstance().getAllenamenti())) {
                        String g1 = "";

                        for (String gc : g) {
                            g1 += gc + ";";
                        }

                        CognomeNome.add(g1);
                    }
                }
            }

                 VariabiliStaticheAllenamenti.getInstance().setGiocatoriAssenti(CognomeNome);

                VariabiliStaticheAllenamenti.getInstance().getLayDataOra().setVisibility(LinearLayout.VISIBLE);
                VariabiliStaticheAllenamenti.getInstance().getLayTasti().setVisibility(LinearLayout.VISIBLE);
                VariabiliStaticheAllenamenti.getInstance().getBtnRitornaAllenamenti().setVisibility(LinearLayout.VISIBLE);

                Allenamenti.fillListViewGiocatoriAssenti();
            // }
        }
    }

    public void EliminaGiocatore(String Ritorno, final String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), "Giocatore eliminato", false, VariabiliStaticheGlobali.NomeApplicazione);

            VariabiliStaticheRose.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoGiocatori dbr = new DBRemotoGiocatori();
                    dbr.RitornaGiocatoriCategoria(VariabiliStaticheRose.getInstance().getContext(),
                            Integer.toString(VariabiliStaticheRose.getInstance().idCategoriaScelta1),
                            NomiMaschere.getInstance().getRose());
                }
            }, 50);
        }
    }

}
