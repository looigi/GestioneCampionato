package looigi.gestionecampionato.db_remoto;

import android.content.Context;

import looigi.gestionecampionato.dati.StrutturaDatiUtente;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

public class DBRemotoUtenti {
	private String ws = "wsUtenti.asmx/";
	private String NS="http://cvcalcio_ute.org/";
	private String SA="http://cvcalcio_ute.org/";

	public void RitornaListaUtenti(Context context) {
		String Urletto="RitornaListaUtenti?";
		Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaListaUtenti", "", "", NS, SA);
	}

	public void RitornaUtenteDaId(Context context, String idUtente, String Maschera) {
		String Urletto="RitornaUtenteDaID?";
		Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idUtente=" + idUtente;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaUtenteDaID", "", Maschera, NS, SA);
	}

	public void RitornaUtentePerLogin(Context context, String Utente, String Password, String Maschera) {
		String Urletto="RitornaUtentePerLogin?";
		Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&Utente=" + Utente;
		Urletto+="&Password=" + Password;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaUtentePerLogin", "", Maschera, NS, SA);
	}

	public void SalvaUtente(Context context, String idAnno, StrutturaDatiUtente s, String Maschera) {
		String Urletto="SalvaUtente?";
		Urletto+="idAnno=" + idAnno;
		Urletto+="&Utente=" + s.getUtente();
		Urletto+="&Cognome=" + s.getCognome();
		Urletto+="&Nome=" + s.getNome();
		Urletto+="&EMail=" + s.getEMail();
		Urletto+="&Password=" + s.getPassword();
		Urletto+="&idCategoria=" + s.getIdCategoria1();
		Urletto+="&idTipologia=" + s.getIdTipologia();

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "SalvaUtente", "", Maschera, NS, SA);
	}

	public void ModificaUtente(Context context, String idAnno, StrutturaDatiUtente s, String Maschera) {
		String Urletto="ModificaUtente?";
		Urletto+="idAnno=" + idAnno;
		Urletto+="&Utente=" + s.getUtente();
		Urletto+="&Cognome=" + s.getCognome();
		Urletto+="&Nome=" + s.getNome();
		Urletto+="&EMail=" + s.getEMail();
		Urletto+="&Password=" + s.getPassword();
		Urletto+="&idCategoria=" + s.getIdCategoria1();
		Urletto+="&idTipologia=" + s.getIdTipologia();
		Urletto+="&idUtente=" + s.getIdUtente();

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "ModificaUtente", "", Maschera, NS, SA);
	}
}
