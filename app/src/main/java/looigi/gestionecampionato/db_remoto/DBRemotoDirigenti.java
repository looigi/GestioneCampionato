package looigi.gestionecampionato.db_remoto;

import android.content.Context;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

public class DBRemotoDirigenti {
	private String ws = "wsDirigenti.asmx/";
	private String NS="http://cvcalcio_dir.org/";
	private String SA="http://cvcalcio_dir.org/";

	public void SalvaDirigente(Context context, String idCategoria, String idDirigente, String Cognome, String Nome, String EMail, String Telefono, String Maschera) {
		String Urletto="SalvaDirigente?";
		Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idCategoria=" + idCategoria;
		Urletto+="&idDirigente=" + idDirigente;
		Urletto+="&Cognome=" + Cognome;
		Urletto+="&Nome=" + Nome;
		Urletto+="&EMail=" + EMail;
		Urletto+="&Telefono=" + Telefono;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "SalvaDirigente", "", Maschera, NS, SA);
	}

	public void RitornaDirigentiCategoria(Context context, String idCategoria, String Maschera) {
		String Urletto="RitornaDirigentiCategoria?idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idCategoria=" + idCategoria;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaDirigentiCategoria", "", Maschera, NS, SA);
	}

	public void EliminaDirigente(Context context, String idDirigente, String Maschera) {
		String Urletto="EliminaDirigente?";
		Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idDirigente=" + idDirigente;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "EliminaDirigente", "", Maschera, NS, SA);
	}
}
