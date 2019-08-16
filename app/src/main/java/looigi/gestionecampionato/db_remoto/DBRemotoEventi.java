package looigi.gestionecampionato.db_remoto;

import android.content.Context;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

public class DBRemotoEventi {
	private String ws = "wsEventi.asmx/";
	private String NS="http://cvcalcio_dir.org/";
	private String SA="http://cvcalcio_dir.org/";

	public void SalvaEvento(Context context, String idEvento, String Evento, String Maschera) {
		String Urletto="SalvaEvento?";
		Urletto+="idEvento=" + idEvento;
		Urletto+="&Evento=" + Evento;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "SalvaEvento",
				"", Maschera, NS, SA);
	}

	public void RitornaEventi(Context context, String Maschera) {
		String Urletto="RitornaEventi";

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaEventi",
				"", Maschera, NS, SA);
	}

	public void EliminaEvento(Context context, String idEvento, String Maschera) {
		String Urletto="EliminaEvento?";
		Urletto+="idEvento=" + idEvento;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "EliminaEvento",
				"", Maschera, NS, SA);
	}
}
