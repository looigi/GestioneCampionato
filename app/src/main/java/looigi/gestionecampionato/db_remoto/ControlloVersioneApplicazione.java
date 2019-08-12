package looigi.gestionecampionato.db_remoto;

import android.content.Context;

import java.io.File;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

public class ControlloVersioneApplicazione {
    private static String verAttuale = "";

    public void ControllaVersione(Context context) {
        verAttuale = Utility.getInstance().getVersion(context);

        DBRemotoGenerale dbr = new DBRemotoGenerale();
        dbr.RitornaVersioneApplicazione(VariabiliStaticheGlobali.getInstance().getContext(),"");
    }

    public static void ControlloVersione(String NuovaVersione) {
        String sNuovaVersione = NuovaVersione.trim();

        if (!sNuovaVersione.equals(verAttuale) && !sNuovaVersione.isEmpty()) {
            String path = VariabiliStaticheGlobali.RadiceWS+"NuoveVersioni/GestioneCampionato.apk";

            UpdateApp atualizaApp = new UpdateApp();
            atualizaApp.setContext(VariabiliStaticheGlobali.getInstance().getContext());
            atualizaApp.execute(path);
        } else {
            String outapk = VariabiliStaticheGlobali.getInstance().PercorsoDIR + "/update.apk";
            File f = new File(outapk);
            if (f.exists()) {
                f.delete();
            }
        }
    }
}
