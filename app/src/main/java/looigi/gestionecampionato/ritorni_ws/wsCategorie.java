package looigi.gestionecampionato.ritorni_ws;

import android.content.Context;
import android.os.Handler;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheAlbum;
import looigi.gestionecampionato.dati.VariabiliStaticheAllenamenti;
import looigi.gestionecampionato.dati.VariabiliStaticheAllenatori;
import looigi.gestionecampionato.dati.VariabiliStaticheCampionato;
import looigi.gestionecampionato.dati.VariabiliStaticheCategorie;
import looigi.gestionecampionato.dati.VariabiliStaticheDirigenti;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheHome;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.dati.VariabiliStaticheRose;
import looigi.gestionecampionato.dati.VariabiliStaticheStatistiche;
import looigi.gestionecampionato.dati.VariabiliStaticheUtenti;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.db_remoto.DBRemotoGenerale;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.Album;
import looigi.gestionecampionato.maschere.Allenamenti;
import looigi.gestionecampionato.maschere.Allenatori;
import looigi.gestionecampionato.maschere.Campionato;
import looigi.gestionecampionato.maschere.Categorie;
import looigi.gestionecampionato.maschere.Dirigenti;
import looigi.gestionecampionato.maschere.Home;
import looigi.gestionecampionato.maschere.ModificaUtente;
import looigi.gestionecampionato.maschere.NuovaPartita;
import looigi.gestionecampionato.maschere.Rose;
import looigi.gestionecampionato.maschere.Statistiche;
import looigi.gestionecampionato.maschere.Utenti;

public class wsCategorie {
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    private String ToglieTag(String Cosa) {
        return Cosa;
    }

    public void RitornaCategorie(final Context context, String Ritorno, final String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            List<String> descCategoria = new ArrayList<>();
            List<Integer> idCategoria = new ArrayList<>();

            List<String> descCategoria2 = new ArrayList<>();
            List<Integer> idCategoria2 = new ArrayList<>();

            descCategoria2.add("");
            idCategoria2.add(-1);

            String cc[]=Appoggio.split("§");
            for (String ccc : cc) {
                String c[]=ccc.split(";");

                idCategoria.add(Integer.parseInt(c[0]));
                descCategoria.add(c[1]);

                idCategoria2.add(Integer.parseInt(c[0]));
                descCategoria2.add(c[1]);
            }

            if (Maschera.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                VariabiliStaticheNuovaPartita.getInstance().setDescrizioneCategorie(descCategoria);
                VariabiliStaticheNuovaPartita.getInstance().setIdCategorie(idCategoria);

                hSelezionaRiga = new Handler();
                hSelezionaRiga.postDelayed(runRiga = new Runnable() {
                    @Override
                    public void run() {
                        NuovaPartita.fillSpinnersCategorie();

                        DBRemotoGenerale dbr = new DBRemotoGenerale();
                        dbr.RitornaTipologie(context, Maschera);
                    }
                }, 50);
            } else {
                if (Maschera.equals(NomiMaschere.getInstance().getAllenatori())) {
                    VariabiliStaticheAllenatori.getInstance().setCategorie(descCategoria);
                    VariabiliStaticheAllenatori.getInstance().setIdCategorie(idCategoria);
                    VariabiliStaticheAllenatori.getInstance().idCategoriaScelta=idCategoria.get(0);

                    Allenatori.RiempieListaCategorie();
                } else {
                    if (Maschera.equals(NomiMaschere.getInstance().getCategorie())) {
                        VariabiliStaticheCategorie.getInstance().setCategorie(descCategoria);
                        VariabiliStaticheCategorie.getInstance().setIdCategorie(idCategoria);

                        Categorie.fillListViewCategorie();
                    } else {
                        if (Maschera.equals(NomiMaschere.getInstance().getRose())) {
                            VariabiliStaticheRose.getInstance().setCategorie1(descCategoria);
                            VariabiliStaticheRose.getInstance().setIdCategorie(idCategoria);

                            VariabiliStaticheRose.getInstance().setCategorie2(descCategoria2);
                            VariabiliStaticheRose.getInstance().setIdCategorie2(idCategoria2);

                            VariabiliStaticheRose.getInstance().setCategorie3(descCategoria2);
                            VariabiliStaticheRose.getInstance().setIdCategorie3(idCategoria2);

                            Rose.RiempieListaCategorie();

                            hSelezionaRiga = new Handler();
                            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                                @Override
                                public void run() {
                                    DBRemotoGenerale dbr = new DBRemotoGenerale();
                                    dbr.RitornaRuoli(context, Maschera);
                                }
                            }, 50);
                        } else {
                            if (Maschera.equals(NomiMaschere.getInstance().getUtenti())) {
                                VariabiliStaticheUtenti.getInstance().setNomiCategorie(descCategoria);
                                VariabiliStaticheUtenti.getInstance().setIdNomiCategorie(idCategoria);
                                VariabiliStaticheUtenti.getInstance().setIdCategoriaScelta(idCategoria.get(0));

                                Utenti.RiempieListaCategorie();
                            } else {
                                if (Maschera.equals(NomiMaschere.getInstance().getCampionato())) {
                                    VariabiliStaticheCampionato.getInstance().setCategorie(descCategoria);
                                    VariabiliStaticheCampionato.getInstance().setIdCategorie(idCategoria);

                                    Campionato.RiempieListaCategorie();
                                } else {
                                    if (Maschera.equals(NomiMaschere.getInstance().getStatistiche())) {
                                        VariabiliStaticheStatistiche.getInstance().setCategorie(descCategoria);
                                        VariabiliStaticheStatistiche.getInstance().setIdCategorie(idCategoria);
                                        VariabiliStaticheStatistiche.getInstance().idCategoriaScelta = idCategoria.get(0);

                                        Statistiche.RiempieListaCategorie();
                                    } else {
                                        if (Maschera.equals(NomiMaschere.getInstance().getModificaUtenti())) {
                                            VariabiliStaticheUtenti.getInstance().setNomiCategorie(descCategoria);
                                            VariabiliStaticheUtenti.getInstance().setIdNomiCategorie(idCategoria);
                                            VariabiliStaticheUtenti.getInstance().setIdCategoriaScelta(idCategoria.get(0));

                                            ModificaUtente.RiempieListaCategorie();
                                        } else {
                                            if (Maschera.equals(NomiMaschere.getInstance().getAlbum())) {
                                                VariabiliStaticheAlbum.getInstance().setCategorie(descCategoria);
                                                VariabiliStaticheAlbum.getInstance().setIdCategorie(idCategoria);
                                                VariabiliStaticheAlbum.getInstance().idCategoriaScelta=idCategoria.get(0);

                                                Album.RiempieListaCategorie();
                                            } else {
                                                if (Maschera.equals(NomiMaschere.getInstance().getHome())) {
                                                    VariabiliStaticheHome.getInstance().setCategorie(descCategoria);
                                                    VariabiliStaticheHome.getInstance().setIdCategorie(idCategoria);
                                                    VariabiliStaticheHome.getInstance().idCategoriaScelta=idCategoria.get(0);

                                                    Home.RiempieListaCategorie();
                                                } else {
                                                    if (Maschera.equals(NomiMaschere.getInstance().getDirigenti())) {
                                                        VariabiliStaticheDirigenti.getInstance().setCategorie(descCategoria);
                                                        VariabiliStaticheDirigenti.getInstance().setIdCategorie(idCategoria);
                                                        VariabiliStaticheDirigenti.getInstance().idCategoriaScelta=idCategoria.get(0);

                                                        Dirigenti.RiempieListaCategorie();
                                                    } else {
                                                        if (Maschera.equals(NomiMaschere.getInstance().getAllenamenti())) {
                                                            VariabiliStaticheAllenamenti.getInstance().setCategorie(descCategoria);
                                                            VariabiliStaticheAllenamenti.getInstance().setIdCategorie(idCategoria);
                                                            VariabiliStaticheAllenamenti.getInstance().idCategoriaScelta=idCategoria.get(0);

                                                            Allenamenti.RiempieListaCategorie();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void SalvaCategoria(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                    "Categoria salvata",
                    false,
                    VariabiliStaticheGlobali.NomeApplicazione);
            //VariabiliStaticheMain.getInstance().setPartite(null);
            VariabiliStaticheCategorie.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoCategorie dbr = new DBRemotoCategorie();
                    dbr.RitornaCategorie(VariabiliStaticheCategorie.getInstance().getContext(),
                            NomiMaschere.getInstance().getCategorie());
                }
            }, 50);
        }
    }

    public void EliminaCategoria(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), "Categoria eliminata", false, VariabiliStaticheGlobali.NomeApplicazione);

            VariabiliStaticheCategorie.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoCategorie dbr = new DBRemotoCategorie();
                    dbr.RitornaCategorie(VariabiliStaticheCategorie.getInstance().getContext(),
                            NomiMaschere.getInstance().getCategorie());
                }
            }, 50);
        }
    }
}
