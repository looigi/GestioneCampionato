package looigi.gestionecampionato.db_remoto;

import android.content.Context;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

public class DBRemotoGenerale {
	private String ws = "wsGenerale.asmx/";
	private String NS="http://cvcalcio.org/";
	private String SA="http://cvcalcio.org/";

	public void RitornaAnni(Context context, String Maschera) {
		String Urletto="RitornaAnni?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaAnni",
				"", Maschera, NS, SA);
	}

	public void RitornaTipologie(Context context, String Maschera) {
		String Urletto="RitornaTipologie?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaTipologie",
				"", Maschera, NS, SA);
	}

	public void RitornaRuoli(Context context, String Maschera) {
		String Urletto="RitornaRuoli?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaRuoli",
				"", Maschera, NS, SA);
	}

	public void RitornaVersioneApplicazione(Context context, String Maschera) {
		String Urletto="RitornaVersioneApplicazione?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaVersioneApplicazione",
				"", Maschera, NS, SA);
	}

	public void RitornaMaxAnno(Context context) {
		String Urletto="RitornaMaxAnno?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaMaxAnno",
				"", "", NS, SA);
	}

	public void RitornaAnnoAttualeUtenti(Context context, String MascheraChiamante) {
		if (VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdUtente()!=null) {
			String Urletto = "RitornaAnnoAttualeUtente?";
			Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
			Urletto += "&idUtente=" + VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdUtente();

			Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaAnnoAttualeUtenti",
					"", MascheraChiamante, NS, SA);
		}
	}

	// public void RitornaEventi(Context context, String MascheraChiamante) {
	// 	if (VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdUtente()!=null) {
	// 		String Urletto = "RitornaEventi";
//
	// 		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaEventi",
	// 				"", MascheraChiamante, NS, SA);
	// 	}
	// }

	public void CreaNuovoAnno(Context context, String idAnno, String descAnno, String nomeSquadra, String TuttiIDati) {
		String Urletto="CreaNuovoAnno?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + idAnno;
		Urletto += "&descAnno=" + descAnno;
		Urletto += "&nomeSquadra=" + nomeSquadra;
		Urletto += "&idAnnoAttuale=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto += "&idUtente=" + VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdUtente();
		Urletto += "&CreazioneTuttiIDati=" + TuttiIDati;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "CreaNuovoAnno",
				"", "", NS, SA);
	}

	public void ImpostaAnnoAttualeUtente(Context context, String idAnno) {
		String Urletto="ImpostaAnnoAttualeUtente?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + idAnno;
		Urletto += "&idUtente=" + VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdUtente();

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "ImpostaAnnoAttualeUtente",
				"", "", NS, SA);
	}

	public void CreaNuovoAnno(Context context, String idAnno, String descAnno) {
		String Urletto="CreaNuovoAnno?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + idAnno;
		Urletto += "Descrizione=" + descAnno;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "CreaNuovoAnno",
				"", "", NS, SA);
	}

	public void RitornaValoriPerRegistrazione(Context context, String Maschera) {
		String Urletto="RitornaValoriPerRegistrazione?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaValoriPerRegistrazione",
				"", Maschera, NS, SA);
	}

	public void RitornaSquadrePerSceltaIniziale(Context context) {
		String Urletto="RitornaSquadrePerSceltaIniziale";

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaSquadrePerSceltaIniziale",
				"", "", NS, SA);
	}

	public void ControllaEsistenzaDB(Context context, String Squadra) {
		String Urletto="ControllaEsistenzaDB?";
		Urletto+="Squadra=" + Squadra;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "ControllaEsistenzaDB",
				"", "", NS, SA);
	}
}
