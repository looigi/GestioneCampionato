package looigi.gestionecampionato.ritorni_ws;

import looigi.gestionecampionato.dati.VariabiliStaticheMeteo;
import looigi.gestionecampionato.maschere.NuovaPartita;

public class wsMeteo {
    public void RitornaMeteo(String Ritorno) {
        String Appoggio = Ritorno;

        Appoggio=Appoggio.replace("{","");
        Appoggio=Appoggio.replace("}","");
        Appoggio=Appoggio.replace("[","");
        Appoggio=Appoggio.replace("]","");
        Appoggio=Appoggio.replace("\"","");
        Appoggio=Appoggio.replace("main:temp","temp");

        String[] c = Appoggio.split(",");
        for (String cc : c) {
            String[] cc2 = cc.split(":");

            switch (cc2[0]) {
                case "description":
                    VariabiliStaticheMeteo.getInstance().setTempo(cc2[1]);
                    break;
                case "temp":
                    VariabiliStaticheMeteo.getInstance().setGradi(cc2[1]);
                    break;
                case "pressure":
                    VariabiliStaticheMeteo.getInstance().setPressione(cc2[1]);
                    break;
                case "humidity":
                    VariabiliStaticheMeteo.getInstance().setUmidita(cc2[1]);
                    break;
            }
        }

        NuovaPartita.ScriveMeteo();
    }
}
