package looigi.gestionecampionato.ritorni_ws;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheGrafici;
import looigi.gestionecampionato.dati.VariabiliStaticheStatistiche;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.Grafici;
import looigi.gestionecampionato.maschere.Mappa;
import looigi.gestionecampionato.maschere.Statistiche;

import static looigi.gestionecampionato.dati.VariabiliStaticheGlobali.RadiceWS;

public class wsStatistiche {
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    private String ToglieTag(String Cosa) {
        return Cosa;
    }

    public void RitornaStatisticheStagione(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            String pagina_web = RadiceWS+"/Statistiche/" +
                    VariabiliStaticheGlobali.getInstance().getAnnoInCorso() + "_" +
                    VariabiliStaticheStatistiche.getInstance().idCategoriaScelta + ".html";

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pagina_web));
            VariabiliStaticheGlobali.getInstance().getContextPrincipale().startActivity(browserIntent);
        }
    }

    public void RitornaStatisticheAvversari(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            Statistiche.fillListViewStatistiche(Appoggio);
        }
    }

    public void RitornaStatisticheConvocati(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            Statistiche.fillListViewStatistiche(Appoggio);
        }
    }

    public void RitornaStatisticheMarcatori(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            Statistiche.fillListViewStatistiche(Appoggio);
        }
    }

    public void RitornaStatisticheRisultati(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            Statistiche.fillListViewStatistiche(Appoggio);
        }
    }

    public void RitornaStatisticheMeteo(String Ritorno, final String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<Float> Gradi = new ArrayList<>();
            List<Float> Umidita = new ArrayList<>();
            List<Float> Pressione = new ArrayList<>();

            List<String> Tempo = new ArrayList<>();
            List<Integer> Quanti = new ArrayList<>();

            String c[] = Appoggio.split("§");
            for (String cc : c) {
                String ccc[]=cc.split(";");
                if (ccc[0].equals("1")) {
                    if (ccc.length > 3) {
                        Gradi.add(Float.parseFloat(ccc[1]));
                        Umidita.add(Float.parseFloat(ccc[2]));
                        Pressione.add(Float.parseFloat(ccc[3]));
                    } else {
                        if (ccc.length > 2) {
                            Gradi.add(Float.parseFloat(ccc[1]));
                            Umidita.add(Float.parseFloat(ccc[2]));
                            Pressione.add(0F);
                        } else {
                            if (ccc.length > 1) {
                                Gradi.add(Float.parseFloat(ccc[1]));
                                Umidita.add(0F);
                                Pressione.add(0F);
                            } else {
                                Gradi.add(0F);
                                Umidita.add(0F);
                                Pressione.add(0F);
                            }
                        }
                    }
                } else {
                    Tempo.add(ccc[1]);
                    Quanti.add(Integer.parseInt(ccc[2]));
                }
            }

            VariabiliStaticheGrafici.getInstance().setGradi(Gradi);
            VariabiliStaticheGrafici.getInstance().setUmidita(Umidita);
            VariabiliStaticheGrafici.getInstance().setPressione(Pressione);
            VariabiliStaticheGrafici.getInstance().setTempo(Tempo);
            VariabiliStaticheGrafici.getInstance().setQuanti(Quanti);

            if (Maschera.contains("_3")) {
                Grafici.DisegnaGraficoMeteo1();
            } else {
                Grafici.DisegnaGraficoMeteo2();
            }
        }
    }

    public void RitornaStatisticheMinutiGoal(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<Integer> FattiMinuto = new ArrayList<>();
            List<Integer> FattiQuanti = new ArrayList<>();

            List<Integer> SubitiMinuto = new ArrayList<>();
            List<Integer> SubitiQuanti = new ArrayList<>();

            String c[] = Appoggio.split("§");
            for (String cc : c) {
                String ccc[]=cc.split(";");
                if (ccc[0].equals("1")) {
                    FattiMinuto.add(Integer.parseInt(ccc[1]));
                    FattiQuanti.add(Integer.parseInt(ccc[2]));
                } else {
                    SubitiMinuto.add(Integer.parseInt(ccc[1]));
                    SubitiQuanti.add(Integer.parseInt(ccc[2]));
                }
            }

            VariabiliStaticheGrafici.getInstance().setFattiMinuto(FattiMinuto);
            VariabiliStaticheGrafici.getInstance().setFattiQuanti(FattiQuanti);
            VariabiliStaticheGrafici.getInstance().setSubitiMinuto(SubitiMinuto);
            VariabiliStaticheGrafici.getInstance().setSubitiQuanti(SubitiQuanti);

            if (Maschera.contains("_1")) {
                Grafici.DisegnaGraficoGoalFattiPerMinuto();
            } else {
                Grafici.DisegnaGraficoGoalSubitiPerMinuto();
            }
        }
    }

    public void RitornaStatisticheGoalSegnatiSubiti(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<Integer> Segnati = new ArrayList<>();
            List<Integer> Subiti = new ArrayList<>();

            List<Integer> SegnatiPartita = new ArrayList<>();
            List<Integer> SubitiPartita = new ArrayList<>();

            String c[] = Appoggio.split("§");
            int i1=0;
            int i2=0;
            for (String cc : c) {
                String ccc[]=cc.split(";");
                if (ccc[0].equals("1")) {
                    SegnatiPartita.add(i1);
                    Segnati.add(Integer.parseInt(ccc[2]));
                    i1++;
                } else {
                    SubitiPartita.add(i2);
                    Subiti.add(Integer.parseInt(ccc[2]));
                    i2++;
                }
            }

            VariabiliStaticheGrafici.getInstance().setGoalSegnati(Segnati);
            VariabiliStaticheGrafici.getInstance().setGoalSegnatiPartita(SegnatiPartita);
            VariabiliStaticheGrafici.getInstance().setGoalSubiti(Subiti);
            VariabiliStaticheGrafici.getInstance().setGoalSubitiPartita(SubitiPartita);

            if (Maschera.contains("_5")) {
                Grafici.DisegnaGraficoGoalSegnati();
            } else {
                Grafici.DisegnaGraficoGoalSubiti();
            }
        }
    }

    public void RitornaAndamento(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<Integer> Andamento = new ArrayList<>();
            List<Integer> AndamentoPartita = new ArrayList<>();

            String c[] = Appoggio.split("§");
            int i=0;
            for (String cc : c) {
                String ccc[]=cc.split(";");
                AndamentoPartita.add(i);
                Andamento.add(Integer.parseInt(ccc[1]));
                i++;
            }

            VariabiliStaticheGrafici.getInstance().setAndamento(Andamento);
            VariabiliStaticheGrafici.getInstance().setAndamentoPartita(AndamentoPartita);

            Grafici.DisegnaGraficoAndamento();
        }
    }

    public void RitornaStatisticheMappa(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            VariabiliStaticheStatistiche.getInstance().setCoords(new ArrayList<LatLng>());
            VariabiliStaticheStatistiche.getInstance().setDescrMarkers(new ArrayList<String>());
            VariabiliStaticheStatistiche.getInstance().setAvversari(new ArrayList<String>());

            VariabiliStaticheStatistiche.getInstance().getDescrMarkers().add("");

            String[] Righe=Appoggio.split("§");
            int Partite=0;
            for (String s : Righe) {
                if (!s.isEmpty()) {
                    String c[] = s.split(";");
                    if (c.length>6) {
                        if (!c[5].trim().isEmpty() && !c[6].trim().isEmpty()) {
                            c[5] = c[5].replace(",", ".");
                            c[6] = c[6].replace(",", ".");
                            Partite++;
                            LatLng l = new LatLng(Double.parseDouble(c[5]), Double.parseDouble(c[6]));
                            if (!c[9].equals("S")) {
                                boolean Ok = true;
                                int C = 0;

                                for (LatLng i : VariabiliStaticheStatistiche.getInstance().getCoords()) {
                                    String ii = i.latitude + ";" + i.longitude;
                                    C++;
                                    if (ii.equals(c[5] + ";" + c[6])) {
                                        Ok = false;
                                        break;
                                    }
                                }
                                if (Ok) {
                                    String m = c[8] + ">;" + c[1] + ";" + c[2] + ";" + c[3] + ";";

                                    VariabiliStaticheStatistiche.getInstance().getCoords().add(l);
                                    VariabiliStaticheStatistiche.getInstance().getDescrMarkers().add(m);
                                    VariabiliStaticheStatistiche.getInstance().getAvversari().add(c[7]);
                                } else {
                                    String[] d = VariabiliStaticheStatistiche.getInstance().getDescrMarkers().get(C).split(">");
                                    String m = c[8] + "\n" + d[0] + ">";
                                    d[1]=";"+c[1]+"\n"+d[1].substring(1,d[1].length());
                                    int cc = 0;
                                    for (String ss : d) {
                                        if (cc > 0) {
                                            m += ss;
                                        }
                                        cc++;
                                    }

                                    VariabiliStaticheStatistiche.getInstance().getDescrMarkers().set(C, m);
                                }
                            } else {
                                VariabiliStaticheStatistiche.getInstance().setCoordsCasa(l);
                                String mm = VariabiliStaticheStatistiche.getInstance().getDescrMarkers().get(0);
                                String m = "";
                                if (!c[1].equals("0")) {
                                    m = c[8]+": "+c[1]+"\n";
                                    mm += m;
                                } else {
                                    mm="Casa\n\n";
                                }
                                VariabiliStaticheStatistiche.getInstance().getDescrMarkers().set(0, mm);
                            }
                        }
                    }
                }
            }

            VariabiliStaticheStatistiche.getInstance().NumPartite=Partite;
            Mappa.DisegnaMappa();
        }
    }

    public void RitornaTipologiaPartite(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<String> TipologiaPartite = new ArrayList<>();
            List<Integer> Quante = new ArrayList<>();

            String c[] = Appoggio.split("§");
            for (String cc : c) {
                String ccc[]=cc.split(";");
                TipologiaPartite.add(ccc[0]);
                Quante.add(Integer.parseInt(ccc[1]));
            }

            VariabiliStaticheGrafici.getInstance().setTipologiaPartite(TipologiaPartite);
            VariabiliStaticheGrafici.getInstance().setqTipologiaPartite(Quante);

            Grafici.DisegnaGraficoTipologiaPartite();
        }
    }

    public void RitornaPartiteCasaFuori(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<String> CasaFuori = new ArrayList<>();
            List<Integer> Quante = new ArrayList<>();

            String c[] = Appoggio.split("§");
            for (String cc : c) {
                String ccc[]=cc.split(";");
                CasaFuori.add(ccc[0]);
                Quante.add(Integer.parseInt(ccc[1]));
            }

            VariabiliStaticheGrafici.getInstance().setCasaFuori(CasaFuori);
            VariabiliStaticheGrafici.getInstance().setqCasaFuori(Quante);

            Grafici.DisegnaGraficoCasaFuori();
        }
    }

    public void RitornaPartiteAllenatore(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<String> Allenatori = new ArrayList<>();
            List<Integer> Quanti = new ArrayList<>();

            String c[] = Appoggio.split("§");
            for (String cc : c) {
                String ccc[]=cc.split(";");
                Allenatori.add(ccc[0]);
                Quanti.add(Integer.parseInt(ccc[1]));
            }

            VariabiliStaticheGrafici.getInstance().setAllenatori(Allenatori);
            VariabiliStaticheGrafici.getInstance().setqAllenatori(Quanti);

            Grafici.DisegnaGraficoAllenatori();
        }
    }

}
