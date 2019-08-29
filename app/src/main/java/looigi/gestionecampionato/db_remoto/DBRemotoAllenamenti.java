package looigi.gestionecampionato.db_remoto;

import android.content.Context;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

public class DBRemotoAllenamenti {
    private String NS = "http://cvcalcio_allti.org/";
    private String SA = "http://cvcalcio_allti.org/";
    private String ws = "wsAllenamenti.asmx/";

    public void SalvaAllenamenti(Context context, String idCategoria, String Data, String Ora, String Giocatori, String Maschera) {
        String Urletto="SalvaAllenamenti?";
        Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
        Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto+="&idCategoria=" + idCategoria;
        Urletto+="&Data=" + Data;
        Urletto+="&Ora=" + Ora;
        Urletto+="&Giocatori=" + Giocatori;

        Utility.getInstance().EsegueChiamata(context, this.ws, Urletto, "SalvaAllenamenti", "", Maschera, this.NS, this.SA);
    }

    public void RitornaAllenamentiCategoria(Context context, String idCategoria, String Data, String Ora, String Maschera) {
        String Urletto="RitornaAllenamentiCategoria?";
        Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
        Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto+="&idCategoria=" + idCategoria;
        Urletto+="&Data=" + Data;
        Urletto+="&Ora=" + Ora;

        Utility.getInstance().EsegueChiamata(context, this.ws, Urletto, "RitornaAllenamentiCategoria", "", Maschera, this.NS, this.SA);
    }
}