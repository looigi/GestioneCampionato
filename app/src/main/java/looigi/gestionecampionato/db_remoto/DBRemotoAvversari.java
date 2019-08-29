package looigi.gestionecampionato.db_remoto;

import android.content.Context;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

public class DBRemotoAvversari {
	private String ws = "wsAvversari.asmx/";
	private String NS="http://cvcalcio_avv.org/";
	private String SA="http://cvcalcio_avv.org/";

	public void SalvaAvversario(Context context, String idAvversario, String idCampo, String Avversario, String Campo,
                                String Indirizzo, String Ricerca, String Coords, String Maschera) {
		String Urletto="SalvaAvversario?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idAvversario=" + idAvversario;
		Urletto+="&idCampo=" + idCampo;
		Urletto+="&Avversario=" + Avversario;
		Urletto+="&Campo=" + Campo;
		Urletto+="&Indirizzo=" + Indirizzo;
		Urletto+="&Coords=" + Coords;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "SalvaAvversario", Ricerca, Maschera, NS, SA);
	}

	public void RitornaAvversari(Context context, String Ricerca, String Maschera) {
		String Urletto="RitornaAvversari?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&Ricerca=" + Ricerca;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaAvversari", "", Maschera, NS, SA);
	}

	public void RitornaStatisticheAvversario(Context context, String idAvversario) {
		String Urletto="RitornaStatisticheAvversario?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idAvversario=" + idAvversario;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaStatisticheAvversario", "", "", NS, SA);
	}

	public void EliminaAvversario(Context context, String idAvversario, String Ricerca, String Maschera) {
		String Urletto="EliminaAvversario?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idAvversario=" + idAvversario;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "EliminaAvversario", Ricerca, Maschera, NS, SA);
	}

}
