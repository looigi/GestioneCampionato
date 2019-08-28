package looigi.gestionecampionato.db_remoto;

import android.content.Context;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

public class DBRemotoStatAllenamenti {
    private String NS = "http://cvcalcio_stat_allti.org/";
    private String SA = "http://cvcalcio_stat_allti.org/";
    private String ws = "wsStatAllenamenti.asmx/";

    public void RitornaStatAllenamentiCategoria(Context context, String idCategoria, String Mese, String Maschera) {
        String Urletto="RitornaStatAllenamentiCategoria?idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto+="&idCategoria=" + idCategoria;
        Urletto+="&Mese=" + Mese;

        Utility.getInstance().EsegueChiamata(context, this.ws, Urletto,
                "RitornaStatAllenamentiCategoria", "", Maschera, this.NS, this.SA);
    }

    public void RitornaInfo(Context context, String idCategoria, String idGiocatore, String Mese, String Maschera) {
        String Urletto="RitornaInfo?idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto+="&idCategoria=" + idCategoria;
        Urletto+="&idGiocatore=" + idGiocatore;
        Urletto+="&Mese=" + Mese;

        Utility.getInstance().EsegueChiamata(context, this.ws, Urletto,
                "RitornaInfo", "", Maschera, this.NS, this.SA);
    }
}