package looigi.gestionecampionato.db_remoto;

import android.content.Context;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

public class DBRemotoAllenatori {
	private String ws = "wsAllenatori.asmx/";
	private String NS="http://cvcalcio_all.org/";
	private String SA="http://cvcalcio_all.org/";

	public void SalvaAllenatore(Context context, String idCategoria, String idAllenatore, String Cognome, String Nome, String EMail, String Telefono, String Maschera) {
		String Urletto="SalvaAllenatore?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idCategoria=" + idCategoria;
		Urletto+="&idAllenatore=" + idAllenatore;
		Urletto+="&Cognome=" + Cognome;
		Urletto+="&Nome=" + Nome;
		Urletto+="&EMail=" + EMail;
		Urletto+="&Telefono=" + Telefono;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "SalvaAllenatore", "", Maschera, NS, SA);
	}

	public void RitornaAllenatoriCategoria(Context context, String idCategoria, String Maschera) {
		String Urletto="RitornaAllenatoriCategoria?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idCategoria=" + idCategoria;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaAllenatoriCategoria", "", Maschera, NS, SA);
	}

	public void EliminaAllenatore(Context context, String idAllenatore, String Maschera) {
		String Urletto="EliminaAllenatore?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idAllenatore=" + idAllenatore;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "EliminaAllenatore", "", Maschera, NS, SA);
	}
}
