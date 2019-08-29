package looigi.gestionecampionato.db_remoto;

import android.content.Context;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

public class DBRemotoCategorie {
	private String ws = "wsCategorie.asmx/";
	private String NS="http://cvcalcio_cat.org/";
	private String SA="http://cvcalcio_cat.org/";

	public void RitornaCategorie(Context context, String Maschera) {
		if (VariabiliStaticheGlobali.getInstance().getDatiUtente()!=null) {
			String Urletto = "RitornaCategorie?";
			Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
			Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
			Urletto += "&idUtente=" + VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdUtente();

			Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaCategorie", "", Maschera, NS, SA);
		}
	}

	public void RitornaCategoriePerAnno(Context context, String Maschera) {
		if (VariabiliStaticheGlobali.getInstance().getDatiUtente()!=null) {
			String Urletto = "RitornaCategoriePerAnno?";
			Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
			Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();

			Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaCategoriePerAnno", "", Maschera, NS, SA);
		}
	}

	public void SalvaCategoria(Context context, String idCategoria, String Categoria, String Maschera) {
		String Urletto="SalvaCategoria?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idCategoria=" + idCategoria;
		Urletto+="&Categoria=" + Categoria;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "SalvaCategoria", "", Maschera, NS, SA);
	}

	public void EliminaCategoria(Context context, String idCategoria, String Maschera) {
		String Urletto="EliminaCategoria?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idCategoria=" + idCategoria;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "EliminaCategoria", "", Maschera, NS, SA);
	}
}
