package looigi.gestionecampionato.ritorni_ws;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.StrutturaCampionato;
import looigi.gestionecampionato.dati.StrutturaPartita;
import looigi.gestionecampionato.dati.StrutturaSquadreCampionato;
import looigi.gestionecampionato.dati.StrutturaSquadreClassifica;
import looigi.gestionecampionato.dati.VariabiliStaticheCampionato;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.db_remoto.DBRemotoCampionato;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.Campionato;
import looigi.gestionecampionato.utilities.Utility;

import java.util.ArrayList;
import java.util.List;

public class wsCampionato {
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    private String ToglieTag(String Cosa) {
        String C = "£26;Accademia Real Tuscolano;25;Moscarelli Marco;Via Selinunte 87;41.857889;12.5489809;§169;Accademia Sporting Roma;166;Bernardini Fulvio;Via Dell'acqua Marcia;41.9194361;12.555200600000001;§173;Real Torres;171;Real Torres;Via Marco Stazio Prisco, 2, 00173  ;41.869783399999996;12.5685183;§167;Acquacetosa;116;Sport Village;Nuova Lottizzazione La Sorgente, Guidonia Rm;41.9646311;12.711578399999999;§126;Lepanto;131;Fiore Domenico;Via Ferentum Snc, Marino;41.7682035;12.663493599999999;§206;Campo di carne;206;Campo di carne;campo di carne;41.551961;12.635128;§1;Borghesiana FC;1;Brasili Maurizio;Via Lentini;41.8597785;12.6663278;§191;Citta Di Ciampino;193;superga;via superga, Ciampino;41.785493;12.6181737;§£1;1;1593;26;169;27/07/2018 14:48:00;Accademia Real Tuscolano;Accademia Sporting Roma;;0;2-2;;;;;27/07/2018 14:48:00;S;§1;3;1595;206;1;29/07/2018 18:28:00;Campo di carne;Borghesiana FC;;0;;;;;;29/07/2018 18:28:00;N;§2;1;1596;169;-1;27/07/2018 18:36:00;Accademia Sporting Roma;;;0;1-3;;;;;27/07/2018 18:36:00;S;§2;2;1597;173;206;28/07/2018 19:36:00;Real Torres;Campo di carne;;0;;;;;;28/07/2018 19:36:00;N;§";
        return Cosa;
    }

    public void RitornaIdPartitaDaUnione(final Context context, final String Ritorno) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    Utility.getInstance().CambiaMaschera(R.id.nuova_partita,
                            Integer.parseInt(Ritorno),
                            VariabiliStaticheCampionato.getInstance().getIdUnioneCalendario());
                }
            }, 50);
        }
    }

    public void SalvaGiornataUtenteCategoria(String Ritorno) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        }
    }

    public void EliminaPartita(String Ritorno) {
        String Appoggio = ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            String c[] = Appoggio.split(";", -1);
            VariabiliStaticheCampionato.getInstance().getDatiCampionato().RimuovePartitaGiornata(Integer.parseInt(c[0]), Integer.parseInt(c[1]));
            Campionato.RiempieListaPartite();
        }
    }

    public void RitornaClassifica(String Ritorno) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            // Prende prima la classifica
            List<StrutturaSquadreClassifica> lClassifica = new ArrayList<>();
            String[] ss = Appoggio.split("§", -1);
            for (String sss : ss) {
                if (!sss.isEmpty()) {
                    String[] ssss = sss.split(";", -1);

                    StrutturaSquadreClassifica sqcl = new StrutturaSquadreClassifica();
                    sqcl.setIdSquadraClass(Integer.parseInt(ssss[0]));
                    sqcl.setSquadreClass(ssss[1]);
                    sqcl.setPunti(Integer.parseInt(ssss[2]));
                    sqcl.setGiocate(Integer.parseInt(ssss[3]));
                    sqcl.setVinte(Integer.parseInt(ssss[4]));
                    sqcl.setPareggiate(Integer.parseInt(ssss[5]));
                    sqcl.setPerse(Integer.parseInt(ssss[6]));
                    sqcl.setGf(Integer.parseInt(ssss[7]));
                    sqcl.setGs(Integer.parseInt(ssss[8]));
                    lClassifica.add(sqcl);
                }
            }

            VariabiliStaticheCampionato.getInstance().getDatiCampionato().setSquadreClass(lClassifica);

            Campionato.RiempieListaClassifica();
        }
    }

    public void RitornaCampionatoCategoria(final Context context, String Ritorno) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            StrutturaCampionato s = new StrutturaCampionato();
            String Squadre = "";

            // Poi passa alle squadre partecipanti
            List<StrutturaSquadreCampionato> lCampionato = new ArrayList<>();
            StrutturaSquadreCampionato sqcam = new StrutturaSquadreCampionato();
            sqcam.setIdSquadre(-VariabiliStaticheCampionato.getInstance().getIdCategoriaScelta());
            sqcam.setSquadre(VariabiliStaticheGlobali.getInstance().getNomeSquadraAnno());
            sqcam.setIdCampo(-1);
            sqcam.setCampo("");
            sqcam.setIndirizzoCampo("");
            sqcam.setLat("");
            sqcam.setLon("");
            lCampionato.add(sqcam);

            // Giornata
            String Giornata = "1";
            if (Appoggio.contains("^")) {
                Giornata=Appoggio.substring(Appoggio.indexOf("^")+1, Appoggio.length());
                if (Giornata.contains("^")) {
                    Giornata = Giornata.substring(0, Giornata.indexOf("^"));
                    Appoggio = Appoggio.replace("^" + Giornata + "^", "");
                }
            }
            s.setGiornataAttuale(Integer.parseInt(Giornata));
            s.setGiornataClassificaAttuale(Integer.parseInt(Giornata));

            // Squadre avversarie
            if (Appoggio.contains("£")) {
                Squadre=Appoggio.substring(Appoggio.indexOf("£")+1, Appoggio.length());
                if (Squadre.contains("£")) {
                    Squadre = Squadre.substring(0, Squadre.indexOf("£"));
                    Appoggio = Appoggio.replace("£"+Squadre+"£", "");

                    String[] ss = Squadre.split("§", -1);
                    for (String sss : ss) {
                        if (!sss.isEmpty()) {
                            String[] ssss = sss.split(";", -1);

                            sqcam = new StrutturaSquadreCampionato();
                            sqcam.setIdSquadre(Integer.parseInt(ssss[0]));
                            sqcam.setSquadre(ssss[1]);
                            sqcam.setIdCampo(Integer.parseInt(ssss[2]));
                            sqcam.setCampo(ssss[3]);
                            sqcam.setIndirizzoCampo(ssss[4]);
                            sqcam.setLat(ssss[5]);
                            sqcam.setLon(ssss[6]);
                            lCampionato.add(sqcam);
                        }
                    }
                }
            }
            s.setSquadre(lCampionato);

            // Infine le giornate con tutti i dati
            String[] G = Appoggio.split("§", -1);

            Integer NumeroPartite = 0;

            for (String g : G) {
                if (!g.isEmpty()) {
                    String ggg[] = g.split(";", -1);
                    if (Integer.parseInt(ggg[1]) > NumeroPartite) {
                        NumeroPartite = Integer.parseInt(ggg[1]);
                    }
                    // s.AggiungeGiornataSeNonEsiste(Integer.parseInt(ggg[0]));

                    String gg = "";

                    // Prende i convocati
                    // if (!g.isEmpty() && g.contains("!")) {
                    //     gg = Appoggio.substring(g.indexOf("!") + 1, g.length());
                    //     if (gg.contains("!")) {
                    //         gg = gg.substring(0, gg.indexOf("!"));
                    //         g = g.replace("!" + gg + "!", "");
                    //     }
                    //     Convocati.add(gg);
                    //     // Ritorno=Ritorno.replace(gg,"");
                    // } else {
                    //     Convocati.add("");
                    //     g = g.replace("!!", "");
                    // }

                    // Prende i marcatori
                    // if (!g.isEmpty() && g.contains("*")) {
                    //     gg = Appoggio.substring(g.indexOf("!") + 1, g.length());
                    //     if (gg.contains("*")) {
                    //         gg = gg.substring(0, gg.indexOf("*"));
                    //         g = g.replace("*" + gg + "*", "");
                    //     }
                    //     Marcatori.add(gg);
                    //     // Ritorno=Ritorno.replace(gg,"");
                    // } else {
                    //     Marcatori.add("");
                    //     g = g.replace("**", "");
                    // }
                }

                // s.DimensionGliArray(NumeroGiornate, NumeroPartite);

                // Riempie la struttura
                // List<StrutturaConvocati> lConvocati = new ArrayList<>();
                // for (String c : Convocati) {
                //     if (!c.isEmpty()) {
                //         String[] cc = c.split(";", -1);
//
                //         StrutturaConvocati conv = new StrutturaConvocati();
                //         try {
                //             conv.setIdGiocatoreConv(Integer.parseInt(cc[0]));
                //             conv.setCognomeConv(cc[1]);
                //             conv.setNomeConv(cc[2]);
                //             conv.setIdRuolo(Integer.parseInt(cc[3]));
                //             conv.setRuolo(cc[4]);
                //             lConvocati.add(conv);
                //         } catch (Exception ignored) {
                //         }
//
                //         // s.AggiungeConvocato(Integer.parseInt(cc[0]), cc[1],
                //         //         cc[2], Integer.parseInt(cc[3]), cc[4]);
                //     }
                // }

                // List<StrutturaMarcatori> lMarcatori = new ArrayList<>();
                // for (String m : Marcatori) {
                //     if (!m.isEmpty()) {
                //         String[] mm = m.split(";", -1);
//
                //         StrutturaMarcatori marc = new StrutturaMarcatori();
                //         try {
                //             marc.setIdGiocatoreMarc(Integer.parseInt(mm[0]));
                //             marc.setCognomeMarc(mm[1]);
                //             marc.setNomeMarc(mm[2]);
                //             marc.setMinuto(Integer.parseInt(mm[3]));
                //             lMarcatori.add(marc);
                //         } catch (Exception ignored) {
                //         }
//
                //         // s.AggiungeMarcatore(Integer.parseInt(mm[0]), mm[1],
                //         //         mm[2], Integer.parseInt(mm[4]));
                //     }
                // }

                // for (String g1 : G) {
                    if (!g.isEmpty()) {
                        String[] gg = g.split(";", -1);
                        StrutturaPartita sp = new StrutturaPartita();
                        sp.setIdPartitaGiorno(Integer.parseInt(gg[1]));
                        sp.setIdPartitaGen(Integer.parseInt(gg[2]));
                        sp.setIdSqCasa(Integer.parseInt(gg[3]));
                        sp.setIdSqFuori(Integer.parseInt(gg[4]));
                        sp.setDatella(gg[5]);
                        sp.setCasa(gg[6]);
                        sp.setFuori(gg[7]);
                        sp.setRisGiochetti(gg[8]);
                        sp.setGoalAvv(Integer.parseInt(gg[9]));
                        sp.setRisultato1(gg[10]);
                        sp.setRisultato2(gg[11]);
                        sp.setNotelle(gg[12]);
                        sp.setInCasa(gg[13]);
                        sp.setOraConv(gg[14]);
                        sp.setGiocata(gg[15]);
                        // sp.setConvocati(lConvocati);
                        // sp.setMarcatori(lMarcatori);

                        s.AggiungePartita(Integer.parseInt(gg[0].trim()), sp);
                    // }
                }
            }

            s.CalcolaNumeroGiornate();
            VariabiliStaticheCampionato.getInstance().setDatiCampionato(s);

            Campionato.RiempieDatiCampionato();

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoCampionato dbr = new DBRemotoCampionato();
                    dbr.RitornaClassifica(context,
                            Integer.toString(VariabiliStaticheCampionato.getInstance().getDatiCampionato().getGiornataAttuale()),
                            Integer.toString(VariabiliStaticheCampionato.getInstance().getIdCategoriaScelta()));
                }
            }, 50);
        }
    }

    public void AggiungeSquadraAvversaria(String Ritorno) {
        String Appoggio=ToglieTag(Ritorno);
        StrutturaCampionato sc = VariabiliStaticheCampionato.getInstance().getDatiCampionato();

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            String c[] = VariabiliStaticheCampionato.getInstance().getAvversarioScelto().split(";",-1);

            List<StrutturaSquadreCampionato> lCamp = sc.getSquadre();
            StrutturaSquadreCampionato sqcam = new StrutturaSquadreCampionato();
            sqcam.setIdSquadre(Integer.parseInt(c[0]));
            sqcam.setSquadre(c[1]);
            sqcam.setIdCampo(Integer.parseInt(c[2]));
            sqcam.setCampo(c[3]);
            sqcam.setIndirizzoCampo(c[4]);
            sqcam.setLat(c[5]);
            sqcam.setLon(c[6]);
            lCamp.add(sqcam);
            sc.setSquadre(lCamp);

            // sc.AggiungeSquadra(Integer.parseInt(c[0]),
            //         c[1], Integer.parseInt(c[2]), c[3], c[4], c[5], c[6]);

            List<StrutturaSquadreClassifica> lClass = sc.getSquadreClass();
            StrutturaSquadreClassifica sqcl = new StrutturaSquadreClassifica();
            sqcl.setIdSquadraClass(Integer.parseInt(c[0]));
            sqcl.setSquadreClass(c[1]);
            sqcl.setPunti(0);
            sqcl.setGiocate(0);
            sqcl.setVinte(0);
            sqcl.setPareggiate(0);
            sqcl.setPerse(0);
            sqcl.setGf(0);
            sqcl.setGs(0);
            lClass.add(sqcl);
            sc.setSquadreClass(lClass);

            sc.CalcolaNumeroGiornate();
            // sc.AggiungeSquadraClassifica(Integer.parseInt(c[0]),
            //         c[1], 0, 0, 0, 0 ,0,0,0);

            Campionato.RiempieDatiCampionato();

            VariabiliStaticheCampionato.getInstance().getView().findViewById(R.id.layMascheraAvversari).setVisibility(View.GONE);
        }
    }

    public void EliminaSquadraAvversaria(String Ritorno) {
        StrutturaCampionato sc = VariabiliStaticheCampionato.getInstance().getDatiCampionato();
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            String c[] = VariabiliStaticheCampionato.getInstance().getAvversarioScelto().split(";",-1);

            List<StrutturaSquadreCampionato> lCamp = new ArrayList<>();
            for (StrutturaSquadreCampionato s: sc.getSquadre()) {
                if (Integer.parseInt(c[0]) != s.getIdSquadre()) {
                    lCamp.add(s);
                }
            }
            sc.setSquadre(lCamp);

            List<StrutturaSquadreClassifica> lClass = new ArrayList<>();
            for (StrutturaSquadreClassifica s: sc.getSquadreClass()) {
                if (Integer.parseInt(c[0]) != s.getIdSquadraClass()) {
                    lClass.add(s);
                }
            }
            sc.setSquadreClass(lClass);

            sc.CalcolaNumeroGiornate();

            Campionato.RiempieListaSquadre();

            VariabiliStaticheCampionato.getInstance().getView().findViewById(R.id.layMascheraAvversari).setVisibility(View.GONE);
        }
    }

    public void InserisceNuovaPartita(String Ritorno) {
        StrutturaCampionato sc = VariabiliStaticheCampionato.getInstance().getDatiCampionato();
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            // idGiornata & ";" & idUnioneCalendario & ";" & ProgressivoPartita & ";" & Data & ";" & Ora & ";" & Casa & Fuori & idNuovaPartita & ";"
            try {
                String Rit[] = Ritorno.split(";", -1);
                int Giornata = sc.getGiornataAttuale();
                StrutturaPartita s = new StrutturaPartita();
                s.setIdPartitaGen(Integer.parseInt(Rit[1]));
                s.setIdPartitaGiorno(Integer.parseInt(Rit[2]));
                s.setGiocata("N");
                s.setDatella(Rit[3] + " " + Rit[4]);
                s.setIdSqCasa(Integer.parseInt(Rit[5]));
                s.setCasa(Rit[6]);
                s.setIdSqFuori(Integer.parseInt(Rit[7]));
                s.setFuori(Rit[8]);
                s.setRisultato1("");
                s.setRisGiochetti("");
                s.setRisultato2("");
                if (Integer.parseInt(Rit[5]) < 0) {
                    s.setInCasa("S");
                } else {
                    s.setInCasa("N");
                }
                // s.setMarcatori(new ArrayList<StrutturaMarcatori>());
                // s.setConvocati(new ArrayList<StrutturaConvocati>());
                sc.AggiungePartita(Giornata, s);

                Campionato.ScriveGiornata(false);
            } catch (Exception ignored) {
                DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                        "Errore nell'inserire la partita nella struttura", true,
                        VariabiliStaticheGlobali.NomeApplicazione);
            }
        }
    }

    public void ModificaPartitaAltre(final Context context, String Ritorno) {
        StrutturaCampionato sc = VariabiliStaticheCampionato.getInstance().getDatiCampionato();
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            // idGiornata & ";" & idUnioneCalendario & ";" & ProgressivoPartita & ";" & Data & ";" & Ora & ";" & Casa & Fuori & idNuovaPartita & ";"
            try {
                String Rit[] = Ritorno.split(";", -1);
                int Giornata = sc.getGiornataAttuale();
                StrutturaPartita s = new StrutturaPartita();
                s.setIdPartitaGen(Integer.parseInt(Rit[1]));
                s.setIdPartitaGiorno(Integer.parseInt(Rit[2]));
                s.setGiocata(Rit[9]);
                s.setDatella(Rit[3] + " " + Rit[4]);
                s.setIdSqCasa(Integer.parseInt(Rit[5]));
                s.setCasa(Rit[6]);
                s.setIdSqFuori(Integer.parseInt(Rit[7]));
                s.setFuori(Rit[8]);
                s.setRisultato1(Rit[10]);
                s.setNotelle("");
                s.setRisGiochetti("");
                s.setRisultato2("");
                if (Integer.parseInt(Rit[5]) < 0) {
                    s.setInCasa("S");
                } else {
                    s.setInCasa("N");
                }
                sc.ModificaPartita(Giornata, Integer.parseInt(Rit[2]), s);

                if (!Rit[10].isEmpty() && Rit[10].contains("-")) {
                    String r[] = Rit[10].split("-",-1);
                    int GoalC = Integer.parseInt(r[0]);
                    int GoalF = Integer.parseInt(r[1]);
                    List<StrutturaSquadreClassifica> SquadreClass = VariabiliStaticheCampionato.getInstance().getDatiCampionato().getSquadreClass();
                    for (StrutturaSquadreClassifica ssc : SquadreClass) {
                        if (ssc.getIdSquadraClass() == Integer.parseInt(Rit[5])) {
                            ssc.setGiocate(ssc.getGiocate()+1);
                            ssc.setGf(ssc.getGf()+GoalC);
                            ssc.setGs(ssc.getGs()+GoalF);
                            if (GoalC > GoalF) {
                                ssc.setVinte(ssc.getVinte()+1);
                            } else {
                                if (GoalC < GoalF) {
                                    ssc.setPerse(ssc.getPerse()+1);
                                } else {
                                    ssc.setPareggiate(ssc.getPareggiate()+1);
                                }
                            }
                        }
                        if (ssc.getIdSquadraClass() == Integer.parseInt(Rit[7])) {
                            ssc.setGiocate(ssc.getGiocate()+1);
                            ssc.setGf(ssc.getGf()+GoalF);
                            ssc.setGs(ssc.getGs()+GoalC);
                            if (GoalF > GoalC) {
                                ssc.setVinte(ssc.getVinte()+1);
                            } else {
                                if (GoalC < GoalF) {
                                    ssc.setPerse(ssc.getPerse()+1);
                                } else {
                                    ssc.setPareggiate(ssc.getPareggiate()+1);
                                }
                            }
                        }
                    }
                }
                Campionato.ScriveGiornata(false);
                // Campionato.RiempieListaClassifica();

                hSelezionaRiga = new Handler();
                hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                    @Override
                    public void run() {
                        DBRemotoCampionato dbr = new DBRemotoCampionato();
                        dbr.RitornaClassifica(context,
                                "1",
                                Integer.toString(VariabiliStaticheCampionato.getInstance().getIdCategoriaScelta()));
                    }
                }, 50);
            } catch (Exception ignored) {
                DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                        "Errore nell'inserire la partita nella struttura", true,
                        VariabiliStaticheGlobali.NomeApplicazione);
            }
        }
    }
}
