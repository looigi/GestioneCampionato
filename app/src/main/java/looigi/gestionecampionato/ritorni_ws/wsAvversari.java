package looigi.gestionecampionato.ritorni_ws;

import android.content.Context;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheAvversari;
import looigi.gestionecampionato.dati.VariabiliStaticheCampionato;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.db_remoto.DBRemotoAvversari;
import looigi.gestionecampionato.db_remoto.DBRemotoDirigenti;
import looigi.gestionecampionato.db_remoto.DBRemotoPartite;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.Avversari;
import looigi.gestionecampionato.maschere.Campionato;
import looigi.gestionecampionato.maschere.NuovaPartita;

public class wsAvversari {
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    private String ToglieTag(String Cosa) {
        return Cosa;
    }

    public void SalvaAvversario(String Ritorno, final String Ricerca, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                    "Avversario salvato",
                    false,
                    VariabiliStaticheGlobali.NomeApplicazione);
            //VariabiliStaticheMain.getInstance().setPartite(null);
            VariabiliStaticheAvversari.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoAvversari dbr = new DBRemotoAvversari();
                    dbr.RitornaAvversari(VariabiliStaticheAvversari.getInstance().getContext(), Ricerca,
                            NomiMaschere.getInstance().getAvversari());
                }
            }, 50);
        }
    }

    public void RitornaAvversari(final Context context, String Ritorno, final String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            final List<String> descAvversario = new ArrayList<>();
            final List<Integer> idAvversario = new ArrayList<>();
            final List<Integer> idCampo = new ArrayList<>();
            final List<String> Campo = new ArrayList<>();
            final List<String> Indirizzo = new ArrayList<>();
            final List<String> Lat = new ArrayList<>();
            final List<String> Lon = new ArrayList<>();

            String aa[]=Appoggio.split("§");
            for (String aaa : aa) {
                String a[]=aaa.split(";",-1);

                if (Maschera.equals(NomiMaschere.getInstance().getNuovaPartita()) ||
                        Maschera.equals(NomiMaschere.getInstance().getCampionato())) {
                    idAvversario.add(Integer.parseInt(a[0]));
                    idCampo.add(Integer.parseInt(a[1]));
                    descAvversario.add(a[2]);
                    try {
                        Campo.add(a[3]);
                        Indirizzo.add(a[4]);
                        Lat.add(a[7]);
                        Lon.add(a[8]);
                    } catch (Exception ignored) {
                        Campo.add("Campo");
                        Indirizzo.add("Indirizzo");
                        Lat.add("Lat");
                        Lon.add("Lon");
                    }
                }
                if (Maschera.equals(NomiMaschere.getInstance().getAvversari())) {
                    descAvversario.add(a[0]+";"+a[1]+";"+a[2]+";"+a[3]+";"+a[4]+";"+a[5]+";"+a[6]+";");
                }
            }

            if (Maschera.equals(NomiMaschere.getInstance().getNuovaPartita()) ||
                    Maschera.equals(NomiMaschere.getInstance().getCampionato())) {

                hSelezionaRiga = new Handler();
                hSelezionaRiga.postDelayed(runRiga = new Runnable() {
                    @Override
                    public void run() {
                        if (Maschera.equals(NomiMaschere.getInstance().getCampionato())) {
                            VariabiliStaticheCampionato.getInstance().setAvversari(descAvversario);
                            VariabiliStaticheCampionato.getInstance().setAvversariID(idAvversario);
                            VariabiliStaticheCampionato.getInstance().setAvversariCampoID(idCampo);
                            VariabiliStaticheCampionato.getInstance().setAvversariCampo(Campo);
                            VariabiliStaticheCampionato.getInstance().setAvversariCampoIndirizzo(Indirizzo);
                            VariabiliStaticheCampionato.getInstance().setAvversariLat(Lat);
                            VariabiliStaticheCampionato.getInstance().setAvversariLon(Lon);

                            VariabiliStaticheCampionato.getInstance().setAvversari(descAvversario);

                            Campionato.fillSpinnersAvversari(descAvversario, idAvversario, idCampo,
                                    Campo, Indirizzo, Lat, Lon);
                        } else {
                            VariabiliStaticheNuovaPartita.getInstance().setAvversari(descAvversario);
                            VariabiliStaticheNuovaPartita.getInstance().setAvversariID(idAvversario);
                            VariabiliStaticheNuovaPartita.getInstance().setAvversariCampoID(idCampo);
                            VariabiliStaticheNuovaPartita.getInstance().setAvversariCampo(Campo);
                            VariabiliStaticheNuovaPartita.getInstance().setAvversariCampoIndirizzo(Indirizzo);

                            NuovaPartita.fillSpinnersAvversari(descAvversario, idAvversario, idCampo,
                                    Campo, Indirizzo);

                            if (NuovaPartita.PartitaNuova != -1) {
                                // Carica dati partita
                                DBRemotoPartite dbr = new DBRemotoPartite();
                                dbr.RitornaPartitaDaID(context, Integer.toString(NuovaPartita.PartitaNuova), Maschera);
                            } else {
                                DBRemotoDirigenti dbr = new DBRemotoDirigenti();
                                dbr.RitornaDirigentiCategoria(VariabiliStaticheGlobali.getInstance().getContext(),
                                        Integer.toString(VariabiliStaticheNuovaPartita.getInstance().idCategoriaScelta),
                                        Maschera);

                                VariabiliStaticheNuovaPartita.getInstance().getCmdSalva().setVisibility(LinearLayout.VISIBLE);
                                VariabiliStaticheNuovaPartita.getInstance().getCmdUscita().setVisibility(LinearLayout.VISIBLE);
                            }
                        }
                    }
                }, 50);
            } else {
                if (Maschera.equals(NomiMaschere.getInstance().getAvversari())) {
                    VariabiliStaticheAvversari.getInstance().setAvversari(descAvversario);

                    Avversari.fillListViewAvversari();
                }
            }
        }
    }

    public void EliminaAvversario(String Ritorno, final String Ricerca, final String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), "Avversario eliminato", false, VariabiliStaticheGlobali.NomeApplicazione);

            VariabiliStaticheAvversari.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
        }
    }

    public void RitornaStatisticheAvversario(String Ritorno) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            VariabiliStaticheAvversari.getInstance().getTxtStat1().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat2().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat3().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat4().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat5().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat6().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat7().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat8().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat9().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat10().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat11().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat12().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat13().setText("");

            VariabiliStaticheAvversari.getInstance().getTxtStat1Anno().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat2Anno().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat3Anno().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat4Anno().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat5Anno().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat6Anno().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat7Anno().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat8Anno().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat9Anno().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat10Anno().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat11Anno().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat12Anno().setText("");
            VariabiliStaticheAvversari.getInstance().getTxtStat13Anno().setText("");

            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            String c[] = Ritorno.split(";",-1);

            VariabiliStaticheAvversari.getInstance().getTxtStat1().setText(c[0]);
            VariabiliStaticheAvversari.getInstance().getTxtStat2().setText(c[1]);
            VariabiliStaticheAvversari.getInstance().getTxtStat3().setText(c[2]);
            VariabiliStaticheAvversari.getInstance().getTxtStat4().setText(c[3]);
            VariabiliStaticheAvversari.getInstance().getTxtStat5().setText(c[4]);
            VariabiliStaticheAvversari.getInstance().getTxtStat6().setText(c[5]);
            VariabiliStaticheAvversari.getInstance().getTxtStat7().setText(c[6]);
            VariabiliStaticheAvversari.getInstance().getTxtStat8().setText(c[7]);
            VariabiliStaticheAvversari.getInstance().getTxtStat9().setText(c[8]);
            VariabiliStaticheAvversari.getInstance().getTxtStat10().setText(c[9]);
            VariabiliStaticheAvversari.getInstance().getTxtStat11().setText(c[10]);
            VariabiliStaticheAvversari.getInstance().getTxtStat12().setText(c[11]);
            VariabiliStaticheAvversari.getInstance().getTxtStat13().setText(c[12]);

            VariabiliStaticheAvversari.getInstance().getTxtStat1Anno().setText(c[13]);
            VariabiliStaticheAvversari.getInstance().getTxtStat2Anno().setText(c[14]);
            VariabiliStaticheAvversari.getInstance().getTxtStat3Anno().setText(c[15]);
            VariabiliStaticheAvversari.getInstance().getTxtStat4Anno().setText(c[16]);
            VariabiliStaticheAvversari.getInstance().getTxtStat5Anno().setText(c[17]);
            VariabiliStaticheAvversari.getInstance().getTxtStat6Anno().setText(c[18]);
            VariabiliStaticheAvversari.getInstance().getTxtStat7Anno().setText(c[19]);
            VariabiliStaticheAvversari.getInstance().getTxtStat8Anno().setText(c[20]);
            VariabiliStaticheAvversari.getInstance().getTxtStat9Anno().setText(c[21]);
            VariabiliStaticheAvversari.getInstance().getTxtStat10Anno().setText(c[22]);
            VariabiliStaticheAvversari.getInstance().getTxtStat11Anno().setText(c[23]);
            VariabiliStaticheAvversari.getInstance().getTxtStat12Anno().setText(c[24]);
            VariabiliStaticheAvversari.getInstance().getTxtStat13Anno().setText(c[25]);
        }
    }
}
