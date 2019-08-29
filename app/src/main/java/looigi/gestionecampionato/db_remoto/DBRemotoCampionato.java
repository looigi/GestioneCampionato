package looigi.gestionecampionato.db_remoto;

import android.content.Context;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

public class DBRemotoCampionato {
	private String ws = "wsCampionato.asmx/";
	private String NS="http://cvcalcio_cam.org/";
	private String SA="http://cvcalcio_cam.org/";

	public void RitornaCampionatoCategoria(Context context, String idCategoria, String Maschera) {
		String Urletto="RitornaCampionatoCategoria?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto +="&idCategoria=" + idCategoria;
		Urletto +="&idUtente=" + VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdUtente();

		Utility.getInstance().EsegueChiamata(context, ws, Urletto,
				"RitornaCampionatoCategoria", "", Maschera, NS, SA);
	}

	public void AggiungeSquadraAvversaria(Context context, String idCategoria, String idAvversario) {
		String Urletto="AggiungeSquadraAvversaria?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto +="&idCategoria=" + idCategoria;
		Urletto +="&idAvversario=" + idAvversario;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto,
				"AggiungeSquadraAvversaria", "", "", NS, SA);
	}

	public void EliminaSquadraAvversaria(Context context, String idCategoria, String idAvversario) {
		String Urletto="EliminaSquadraAvversaria?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto +="&idCategoria=" + idCategoria;
		Urletto +="&idAvversario=" + idAvversario;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto,
				"EliminaSquadraAvversaria", "", "", NS, SA);
	}

	public void InserisceNuovaPartita(Context context, String idGiornata, String idCategoria,
                                      String Data, String Ora, String Casa, String Fuori) {
		String Urletto="InserisceNuovaPartita?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto += "&idGiornata=" + idGiornata;
		Urletto +="&idCategoria=" + idCategoria;
		Urletto +="&Data=" + Data;
		Urletto +="&Ora=" + Ora;
		Urletto +="&Casa=" + Casa;
		Urletto +="&Fuori=" + Fuori;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto,
				"InserisceNuovaPartita", "", "", NS, SA);
	}

	public void ModificaPartitaAltre(Context context, String idGiornata, String idCategoria,
                                     String Data, String Ora, String Casa, String Fuori,
                                     String idUnioneCalendario, String ProgressivoPartita,
                                     String Risultato) {
		String Urletto="ModificaPartitaAltre?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto += "&idGiornata=" + idGiornata;
		Urletto +="&idCategoria=" + idCategoria;
		Urletto +="&Data=" + Data;
		Urletto +="&Ora=" + Ora;
		Urletto +="&Casa=" + Casa;
		Urletto +="&Fuori=" + Fuori;
		Urletto +="&idUnioneCalendario=" + idUnioneCalendario;
		Urletto +="&ProgressivoPartita=" + ProgressivoPartita;
		Urletto +="&Risultato=" + Risultato;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto,
				"ModificaPartitaAltre", "", "", NS, SA);
	}

	public void EliminaPartita(Context context, String idGiornata, String idCategoria, String idPartita) {
		String Urletto="EliminaPartita?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto +="&idGiornata=" + idGiornata;
		Urletto +="&idCategoria=" + idCategoria;
		Urletto +="&idPartita=" + idPartita;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto,
				"EliminaPartita", "", "", NS, SA);
	}

	public void RitornaClassifica(Context context, String idGiornata, String idCategoria) {
		String Urletto="CalcolaClassificaAllaGiornata?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto +="&idCategoria=" + idCategoria;
		Urletto +="&idGiornata=" + idGiornata;
		Urletto +="&idUtente=" + VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdUtente();

		Utility.getInstance().EsegueChiamata(context, ws, Urletto,
				"RitornaClassifica", "", "", NS, SA);
	}

	public void RitornaIdPartitaDaUnione(Context context, String idPartita) {
		String Urletto="RitornaIdPartitaDaUnione?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto +="idUnioneCalendario=" + idPartita;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto,
				"RitornaIdPartitaDaUnione", "", "", NS, SA);
	}

    public void SalvaGiornataUtenteCategoria(Context context, String idCategoria, String idGiornata) {
        String Urletto="SalvaGiornataUtenteCategoria?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto +="&idUtente=" + VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdUtente();
        Urletto += "&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto +="&idCategoria=" + idCategoria;
        Urletto +="&idGiornata=" + idGiornata;

        Utility.getInstance().EsegueChiamata(context, ws, Urletto,
                "SalvaGiornataUtenteCategoria", "", "", NS, SA);
    }
}
