package looigi.gestionecampionato.ritorni_ws;

import android.content.Context;
import android.os.Handler;
import java.util.ArrayList;
import java.util.List;
import looigi.gestionecampionato.dati.VariabiliStaticheAllenamenti;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.Allenamenti;

public class wsAllenamenti {
    private Handler hSelezionaRiga;
    private Runnable runRiga;

    private String ToglieTag(String Cosa) {
        return Cosa;
    }

    public void SalvaAllenamenti(String Ritorno, String Maschera) {
        String Appoggio = ToglieTag(Ritorno);
        if (Appoggio.toUpperCase().contains("ERROR:")) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(), Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(), "Allenamenti salvati", false, VariabiliStaticheGlobali.NomeApplicazione);
        }
    }

    public void RitornaAllenamentiCategoria(Context context, String Ritorno, String Maschera) {
        String Appoggio = ToglieTag(Ritorno);
        if (Appoggio.toUpperCase().contains("ERROR:")) {
            VariabiliStaticheAllenamenti.getInstance().setGiocatoriPresenti(new ArrayList());
        } else {
            List<String> CognomeNome = new ArrayList();
            for (String ggg : Appoggio.split("§")) {
                String g1 = "";
                for (String gc : ggg.split(";", -1)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(g1);
                    stringBuilder.append(gc);
                    stringBuilder.append(";");
                    g1 = stringBuilder.toString();
                }
                CognomeNome.add(g1);
            }
            VariabiliStaticheAllenamenti.getInstance().setGiocatoriPresenti(CognomeNome);
            for (String p : VariabiliStaticheAllenamenti.getInstance().getGiocatoriPresenti()) {
                int quale = 0;
                if (VariabiliStaticheAllenamenti.getInstance().getGiocatoriAssenti()!=null) {
                    for (String a : VariabiliStaticheAllenamenti.getInstance().getGiocatoriAssenti()) {
                        if (p.equals(a)) {
                            VariabiliStaticheAllenamenti.getInstance().getGiocatoriAssenti().remove(quale);
                            break;
                        }
                        quale++;
                    }
                }
            }
        }
        VariabiliStaticheAllenamenti.getInstance().getLayListe().setVisibility(0);
        Allenamenti.fillListViewGiocatoriPresenti();
    }
}
