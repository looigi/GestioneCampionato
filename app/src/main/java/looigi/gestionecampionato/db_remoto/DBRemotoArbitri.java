package looigi.gestionecampionato.db_remoto;

import android.content.Context;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

public class DBRemotoArbitri {
	private String ws = "wsArbitri.asmx/";
	private String NS="http://cvcalcio_arb.org/";
	private String SA="http://cvcalcio_arb.org/";

	public void SalvaArbitro(Context context, String idCategoria, String idArbitro, String Cognome, String Nome, String EMail, String Telefono, String Maschera) {
		String Urletto="SalvaArbitro?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idCategoria=" + idCategoria;
		Urletto+="&idArbitro=" + idArbitro;
		Urletto+="&Cognome=" + Cognome;
		Urletto+="&Nome=" + Nome;
		Urletto+="&EMail=" + EMail;
		Urletto+="&Telefono=" + Telefono;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "SalvaArbitro", "", Maschera, NS, SA);
	}

	public void RitornaArbitri(Context context, String Maschera) {
		String Urletto="RitornaArbitri?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idCategoria=0";

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaArbitri", "", Maschera, NS, SA);
	}

	public void EliminaArbitro(Context context, String idArbitro, String Maschera) {
		String Urletto="EliminaArbitro?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idArbitro=" + idArbitro;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "EliminaArbitro", "", Maschera, NS, SA);
	}
}
