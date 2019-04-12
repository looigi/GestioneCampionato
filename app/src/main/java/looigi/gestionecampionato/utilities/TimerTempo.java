package looigi.gestionecampionato.utilities;

import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;

public class TimerTempo {
    //-------- Singleton ----------//
    private static TimerTempo instance = null;

    private TimerTempo() {
    }

    public static TimerTempo getInstance() {
        if (instance == null) {
            instance = new TimerTempo();
        }

        return instance;
    }

    private int Min1Tempo;
    private int Min2Tempo;
    private int Min3Tempo;
    private int Sec1Tempo;
    private int Sec2Tempo;
    private int Sec3Tempo;
    private int QualeTimerPartito;
    private Timer timer;
    private String tTesto = "";

    public int getQualeTimerPartito() {
        return QualeTimerPartito;
    }

    public void AzzeraTimerTuttiITimer() {
        Min1Tempo=0;
        Min2Tempo=0;
        Min3Tempo=0;

        Sec1Tempo=0;
        Sec2Tempo=0;
        Sec3Tempo=0;

        QualeTimerPartito=0;
    }

    public void FaiPartireTimer(final int QualeTempo, final TextView txt) {
        if (QualeTimerPartito != QualeTempo) {
            QualeTimerPartito = QualeTempo;

            timer = new Timer();
            TimerTask t = new TimerTask() {
                int sec = 0;

                @Override
                public void run() {
                    tTesto = "";

                    switch (QualeTempo) {
                        case 1:
                            Sec1Tempo++;
                            if (Sec1Tempo == 60) {
                                Sec1Tempo = 0;
                                Min1Tempo++;
                            }
                            tTesto = String.format("%02d", Min1Tempo) + ":" + String.format("%02d", Sec1Tempo);
                            break;
                        case 2:
                            Sec2Tempo++;
                            if (Sec2Tempo == 60) {
                                Sec2Tempo = 0;
                                Min2Tempo++;
                            }
                            tTesto = String.format("%02d", Min2Tempo) + ":" + String.format("%02d", Sec2Tempo);
                            break;
                        case 3:
                            Sec3Tempo++;
                            if (Sec3Tempo == 60) {
                                Sec3Tempo = 0;
                                Min3Tempo++;
                            }
                            tTesto = String.format("%02d", Min3Tempo) + ":" + String.format("%02d", Sec3Tempo);
                            break;
                    }

                    VariabiliStaticheGlobali.getInstance().getContextPrincipale().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            txt.setText(tTesto);
                        }
                    });
                }
            };
            timer.scheduleAtFixedRate(t, 0, 1000);
        }
    }

    public int RitornaMinuto(int Tempo) {
        int ritorno=-1;

        switch (Tempo) {
            case 1:
                ritorno=Min1Tempo;
               break;
            case 2:
                ritorno=Min2Tempo;
                break;
            case 3:
                ritorno=Min3Tempo;
                break;
        }

        return ritorno;
    }

    public void ResetTempo(int Tempo, TextView t) {
        switch (Tempo) {
            case 1:
                Min1Tempo=0;
                Sec1Tempo=0;
                break;
            case 2:
                Min2Tempo=0;
                Sec2Tempo=0;
                break;
            case 3:
                Min3Tempo=0;
                Sec3Tempo=0;
                break;
        }
        t.setText("00:00");
    }

    public void SetTempo(int Tempo, String TotTempo) {
        if (!TotTempo.isEmpty() && TotTempo.contains(":")) {
            String t[] = TotTempo.split(":", -1);

            switch (Tempo) {
                case 1:
                    Min1Tempo=Integer.parseInt(t[0]);
                    Sec1Tempo=Integer.parseInt(t[1]);
                    break;
                case 2:
                    Min2Tempo=Integer.parseInt(t[0]);
                    Sec2Tempo=Integer.parseInt(t[1]);
                    break;
                case 3:
                    Min3Tempo=Integer.parseInt(t[0]);
                    Sec3Tempo=Integer.parseInt(t[1]);
                    break;
            }
        }
    }

    public void FermaTimer() {
        timer.cancel();
        timer.purge();
        timer=null;

        QualeTimerPartito =0;
    }
}
