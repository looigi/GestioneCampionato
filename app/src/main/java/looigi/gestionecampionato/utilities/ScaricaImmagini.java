package looigi.gestionecampionato.utilities;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.upload_download.DownloadPic;

public class ScaricaImmagini {
    private static ScaricaImmagini instance = null;

    private ScaricaImmagini() {
    }

    public static ScaricaImmagini getInstance() {
        if (instance == null) {
            instance = new ScaricaImmagini();
        }

        return instance;
    }

    private List<String> NomeFile;
    private List<String> Categoria;
    private List<ImageView> Immagini;
    private Thread tScoda=null;
    private String RitornoDownload;

    public void setRitornoDownload(String ritornoDaWS) {
        RitornoDownload = ritornoDaWS;
    }

    public void AggiungeImmagineDaScaricare(String Tipologia, int Anno, ImageView v, String id, String Name) {
        String nFile="";

        switch (Tipologia) {
            case "GIOCATORE":
                nFile=Integer.toString(Anno)+"_"+id+".jpg";
                break;
            case "CATEGORIE":
                nFile=Integer.toString(Anno)+"_"+id+".jpg";
                break;
            case "AVVERSARI":
                nFile=id+".jpg";
                break;
            case "ALLENATORI":
                nFile=Integer.toString(Anno)+"_"+id+".jpg";
                break;
            case "DIRIGENTI":
                nFile=Integer.toString(Anno)+"_"+id+".jpg";
                break;
            case "ARBITRI":
                nFile=Integer.toString(Anno)+"_"+id+".jpg";
                break;
            case "PARTITE":
                nFile=id + "/" + Name;
                break;
        }

        if (NomeFile==null) {
            NomeFile=new ArrayList<>();
        }
        if (Immagini==null) {
            Immagini=new ArrayList<>();
        }
        if (Categoria==null) {
            Categoria=new ArrayList<>();
        }

        NomeFile.add(nFile);
        Immagini.add(v);
        Categoria.add(Tipologia);

        if (tScoda==null || tScoda.isInterrupted()) {
            tScoda = new EffettuaDownload();
            tScoda.setPriority(Thread.MIN_PRIORITY);
            tScoda.start();
        }
    }

    private class EffettuaDownload extends Thread {
        @Override
        public void run() {
            Ciclo();
        }
    }

    private void Ciclo() {
        Boolean Cont=true;

        while (Cont) {
            String s;

            while (!NomeFile.isEmpty()) {
                s = NomeFile.get(0);

                if (s != null) {
                    String ritorno= EsegueDownload(Categoria.get(0), s, Immagini.get(0));

                    if (!ritorno.equals("*")) {
                    }

                    NomeFile.remove(0);
                    Immagini.remove(0);
                    Categoria.remove(0);
                } else {
                    Cont=false;
                    break;
                }
            }

            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            if (NomeFile.isEmpty()) {
                Cont=false;
            }
        }

        if (tScoda!=null) {
            tScoda.interrupt();
            tScoda=null;
        }
    }

    private String EsegueDownload(String Categoria, String NomeFiletto, ImageView v) {
        RitornoDownload="";

        DownloadPic a = new DownloadPic();
        a.setFiletto(NomeFiletto);
        switch(Categoria) {
            case "GIOCATORE":
                a.setDirectory(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/Giocatori");
                a.setFileDaDown("http://looigi.no-ip.biz:12345/CvCalcio/App_Themes/Standard/Images/Giocatori/" + NomeFiletto);
                break;
            case "CATEGORIE":
                a.setDirectory(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/Categorie");
                a.setFileDaDown("http://looigi.no-ip.biz:12345/CvCalcio/App_Themes/Standard/Images/Categorie/" + NomeFiletto);
                break;
            case "AVVERSARI":
                a.setDirectory(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/Avversari");
                a.setFileDaDown("http://looigi.no-ip.biz:12345/CvCalcio/App_Themes/Standard/Images/Avversari/" + NomeFiletto);
                break;
            case "ALLENATORI":
                a.setDirectory(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/Allenatori");
                a.setFileDaDown("http://looigi.no-ip.biz:12345/CvCalcio/App_Themes/Standard/Images/Allenatori/" + NomeFiletto);
                break;
            case "DIRIGENTI":
                a.setDirectory(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/Dirigenti");
                a.setFileDaDown("http://looigi.no-ip.biz:12345/CvCalcio/App_Themes/Standard/Images/Dirigenti/" + NomeFiletto);
                break;
            case "ARBITRI":
                a.setDirectory(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/Arbitri");
                a.setFileDaDown("http://looigi.no-ip.biz:12345/CvCalcio/App_Themes/Standard/Images/Arbitri/" + NomeFiletto);
                break;
            case "PARTITE":
                a.setDirectory(VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/Partite");
                a.setFileDaDown("http://looigi.no-ip.biz:12345/CvCalcio/App_Themes/Standard/Images/Partite/" + NomeFiletto);
                break;
        }

        a.setImmagine(v);
        a.startDownload(VariabiliStaticheGlobali.getInstance().getContext(), Categoria);

        while(RitornoDownload.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }

        return RitornoDownload;
    }

}
