package looigi.gestionecampionato.utilities;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.upload_download.DownloadMultimediaImage;
import looigi.gestionecampionato.upload_download.DownloadMultimediaVideo;

public class ScaricaMultimedia {
    private static ScaricaMultimedia instance = null;

    private ScaricaMultimedia() {
    }

    public static ScaricaMultimedia getInstance() {
        if (instance == null) {
            instance = new ScaricaMultimedia();
        }

        return instance;
    }

    private List<String> NomeFile;
    private List<String> Percorsi;
    private List<ImageView> Immagini;
    private Thread tScoda=null;
    private String RitornoDownload;

    public void setRitornoDownload(String ritornoDaWS) {
        RitornoDownload = ritornoDaWS;
    }

    public void AggiungeFileMultimedialeDaScaricare(String Url, ImageView v, String Percorso) {
        if (NomeFile==null) {
            NomeFile=new ArrayList<>();
        }
        if (Immagini==null) {
            Immagini=new ArrayList<>();
        }
        if (Percorsi==null) {
            Percorsi=new ArrayList<>();
        }

        NomeFile.add(Url);
        Immagini.add(v);
        Percorsi.add(Percorso);

        if (tScoda==null || tScoda.isInterrupted()) {
            tScoda = new ScaricaMultimedia.EffettuaDownload();
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
                    String ritorno = EsegueDownload(s, Immagini.get(0), Percorsi.get(0));

                    if (!ritorno.equals("*")) {
                    }

                    NomeFile.remove(0);
                    Immagini.remove(0);
                    Percorsi.remove(0);
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

    private String EsegueDownload(String Url, ImageView v, String PathDestinazione) {
        RitornoDownload = "";

        if (PathDestinazione.toUpperCase().contains(".JPG")) {
            DownloadMultimediaImage a = new DownloadMultimediaImage();
            a.setIv(v);
            a.setContext(VariabiliStaticheGlobali.getInstance().getContext());
            a.setPath(PathDestinazione);
            a.startDownload(Url);

            while (RitornoDownload.isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else {
            DownloadMultimediaVideo a = new DownloadMultimediaVideo();
            a.setIv(v);
            a.setContext(VariabiliStaticheGlobali.getInstance().getContext());
            a.setPath(PathDestinazione);
            a.startDownload(Url);

            while (RitornoDownload.isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        return RitornoDownload;
    }
}
