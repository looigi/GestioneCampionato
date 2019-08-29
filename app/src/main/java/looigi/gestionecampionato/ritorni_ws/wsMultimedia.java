package looigi.gestionecampionato.ritorni_ws;

import android.os.Handler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheAlbum;
import looigi.gestionecampionato.dati.VariabiliStaticheAllenatori;
import looigi.gestionecampionato.dati.VariabiliStaticheAvversari;
import looigi.gestionecampionato.dati.VariabiliStaticheCategorie;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheRose;
import looigi.gestionecampionato.db_remoto.DBRemotoMultimedia;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.Album;
import looigi.gestionecampionato.maschere.Multimedia;
import looigi.gestionecampionato.maschere.NuovaPartita;
import looigi.gestionecampionato.utilities.Utility;

public class wsMultimedia {
    private Runnable runRiga;
    private Handler hSelezionaRiga;

    private String ToglieTag(String Cosa) {
        return Cosa;
    }

    public void RitornaMultimedia(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            if (!Appoggio.toUpperCase().contains("NESSUN")) {
                DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                        Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
            }
        } else {
            final List<String> lista = new ArrayList<>();

            for (String l : Appoggio.split("§")) {
                lista.add(l);
            }

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    Multimedia.RiempieListaMultimedia(lista);
                }
            }, 50);
        }
    }

    public void EliminaImmagine(String Ritorno, final String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            String id = "";
            String Percorso="";

            if (Maschera.toUpperCase().equals((NomiMaschere.getInstance().getAllenatoriPerTitolo()+"Immagine").toUpperCase())) {
                DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                        "Immagine allenatore eliminata", false, VariabiliStaticheGlobali.NomeApplicazione);

                id = VariabiliStaticheAllenatori.getInstance().getTxtId().getText().toString();
                Percorso=VariabiliStaticheGlobali.getInstance().PercorsoDIR + "/" + VariabiliStaticheGlobali.getInstance().getNomeSquadra() + "/Allenatori/";

                Percorso += VariabiliStaticheGlobali.getInstance().getAnnoInCorso()+"_"+id+".jpg";

                File file = new File(Percorso);
                if (file.exists()) {
                    file.delete();
                }

                Utility.getInstance().PrendeImmagineAllenatore(VariabiliStaticheAllenatori.getInstance().getTxtId().getText().toString()
                        , VariabiliStaticheAllenatori.getInstance().getImgAllenatore());
            } else {
                if (Maschera.toUpperCase().equals((NomiMaschere.getInstance().getAvversariPerTitolo()+"Immagine").toUpperCase())) {
                    DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                            "Immagine avversario eliminata", false, VariabiliStaticheGlobali.NomeApplicazione);

                    id = VariabiliStaticheAvversari.getInstance().getTxtId().getText().toString();
                    Percorso=VariabiliStaticheGlobali.getInstance().PercorsoDIR + "/Avversari/";

                    Percorso += id+".jpg";

                    File file = new File(Percorso);
                    if (file.exists()) {
                        file.delete();
                    }

                    Utility.getInstance().PrendeImmagineAvversario(VariabiliStaticheAvversari.getInstance().getTxtId().getText().toString()
                            , VariabiliStaticheAvversari.getInstance().getImgAvversario());
                } else {
                    if (Maschera.toUpperCase().equals((NomiMaschere.getInstance().getCategoriePerTitolo()+"Immagine").toUpperCase())) {
                        DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                                "Immagine categoria eliminata", false, VariabiliStaticheGlobali.NomeApplicazione);

                        id = VariabiliStaticheCategorie.getInstance().getTxtId().getText().toString();
                        Percorso=VariabiliStaticheGlobali.getInstance().PercorsoDIR + "/" + VariabiliStaticheGlobali.getInstance().getNomeSquadra() + "/Categorie/";

                        Percorso += VariabiliStaticheGlobali.getInstance().getAnnoInCorso()+"_"+id+".jpg";

                        File file = new File(Percorso);
                        if (file.exists()) {
                            file.delete();
                        }

                        Utility.getInstance().PrendeImmagineCategoria(VariabiliStaticheCategorie.getInstance().getTxtId().getText().toString()
                                , VariabiliStaticheCategorie.getInstance().getImgCategoria());
                    }
                }
            }
        }
    }

    public void RitornoUploadImmagine() {
        if (!VariabiliStaticheGlobali.MascheraAttualePerMultimedia.isEmpty()) {
            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    DBRemotoMultimedia dbr = new DBRemotoMultimedia();
                    if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getRose())) {
                        dbr.RitornaMultimedia(VariabiliStaticheGlobali.getInstance().getContext(),
                                Integer.toString(VariabiliStaticheRose.getInstance().idGiocatoreScelto),
                                Multimedia.MascheraApertaPer,
                                "Multimedia");
                    }
                    if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                        dbr.RitornaMultimedia(VariabiliStaticheGlobali.getInstance().getContext(),
                                Integer.toString(NuovaPartita.PartitaNuova),
                                Multimedia.MascheraApertaPer,
                                "Multimedia");
                    }
                }
            }, 50);

        }

        DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                "Immagine caricata", false, VariabiliStaticheGlobali.NomeApplicazione);
    }

    public void EliminaMultimedia(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            File file = new File(VariabiliStaticheGlobali.ImmagineDaEliminare);
            file.delete();

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    String Tipologia="";
                    int id=-1;

                    if (Multimedia.MascheraApertaPer.equals(NomiMaschere.getInstance().getRose())) {
                        id = VariabiliStaticheRose.getInstance().idGiocatoreScelto;
                        Tipologia=Multimedia.MascheraApertaPer;
                    }

                    if (Multimedia.MascheraApertaPer.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                        id = NuovaPartita.PartitaNuova;
                        Tipologia="Partite";
                    }

                    DBRemotoMultimedia dbm = new DBRemotoMultimedia();
                    dbm.RitornaMultimedia(VariabiliStaticheGlobali.getInstance().getContext(),
                            Integer.toString(id),
                            Tipologia,
                            "Multimedia");
                }
            }, 50);

            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    "Immagine eliminata", true,
                    VariabiliStaticheGlobali.NomeApplicazione);
        }
    }

    public void RitornaAlbumPerCategoria(String Ritorno, String Maschera) {
        String Appoggio=ToglieTag(Ritorno);

        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            boolean Ok = false;
            List<String> Immagini = new ArrayList<>();

            for (String l : Appoggio.split("§")) {
                Immagini.add(l);
                Ok=true;
            }
            VariabiliStaticheAlbum.getInstance().setImmagini(Immagini);
            if (Ok) {
                VariabiliStaticheAlbum.getInstance().QualeImmagine = 0;
                VariabiliStaticheAlbum.getInstance().QuanteImmagini = Immagini.size() - 1;
            } else {
                VariabiliStaticheAlbum.getInstance().QualeImmagine = -1;
                VariabiliStaticheAlbum.getInstance().QuanteImmagini = -1;
            }

            Album.SettaThumbs();
            Album.VisualizzaImmagine();
        }
    }
}
