package looigi.gestionecampionato.db_remoto;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

public class ControlloVersioneApplicazione {
    private static String verAttuale = "";

    public void ControllaVersione() {
        verAttuale = Utility.getInstance().getVersion(VariabiliStaticheGlobali.getInstance().getContext());

        DBRemotoGenerale dbr = new DBRemotoGenerale();
        dbr.RitornaVersioneApplicazione(VariabiliStaticheGlobali.getInstance().getContext(),"");
    }

    public static void ControlloVersione(String NuovaVersione) {
        if (!NuovaVersione.equals(verAttuale) && !NuovaVersione.isEmpty()) {
            String path = VariabiliStaticheGlobali.RadiceWS+"NuoveVersioni/CvCalcio.apk";

            UpdateApp atualizaApp = new UpdateApp();
            atualizaApp.setContext(VariabiliStaticheGlobali.getInstance().getContext());
            atualizaApp.execute(path);
        }
    }
}
