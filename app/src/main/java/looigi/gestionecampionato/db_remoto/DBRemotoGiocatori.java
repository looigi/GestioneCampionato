package looigi.gestionecampionato.db_remoto;

import android.content.Context;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

public class DBRemotoGiocatori {
	private String ws = "wsGiocatori.asmx/";
	private String NS="http://cvcalcio_gioc.org/";
	private String SA="http://cvcalcio_gioc.org/";

	public void RitornaGiocatoriCategoria(Context context, String idCategoria, String Maschera) {
		String Urletto="RitornaGiocatoriCategoria?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idCategoria=" + idCategoria;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaGiocatoriCategoria", "", Maschera, NS, SA);
	}

	public void RitornaGiocatoriCategoriaSenzaAltri(Context context, String idCategoria, String Maschera) {
		String Urletto="RitornaGiocatoriCategoriaSenzaAltri?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idCategoria=" + idCategoria;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaGiocatoriCategoriaSenzaAltri", "", Maschera, NS, SA);
	}

	public void SalvaGiocatore(Context context, String idCategoria, String idGiocatore, String idRuolo, String Cognome, String Nome, String EMail,
                               String Telefono, String Soprannome, String DataDiNascita, String Indirizzo, String CodFiscale, String Maschio, String Citta,
                               String Matricola, String NumeroMaglia, String Ricerca, String Maschera,
							   String idCategoria2, String idCategoria3) {
		String Urletto="SalvaGiocatore?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idCategoria=" + idCategoria;
		Urletto+="&idGiocatore=" + idGiocatore;
		Urletto+="&idRuolo=" + idRuolo;
		Urletto+="&Cognome=" + Cognome;
		Urletto+="&Nome=" + Nome;
		Urletto+="&EMail=" + EMail;
		Urletto+="&Telefono=" + Telefono;
		Urletto+="&Soprannome=" + Soprannome;
		Urletto+="&DataDiNascita=" + DataDiNascita;
		Urletto+="&Indirizzo=" + Indirizzo;
		Urletto+="&CodFiscale=" + CodFiscale;
		Urletto+="&Maschio=" + Maschio;
		Urletto+="&Citta=" + Citta;
		Urletto+="&Matricola=" + Matricola;
		Urletto+="&NumeroMaglia=" + NumeroMaglia;
		Urletto+="&idCategoria2=" + idCategoria2;
		Urletto+="&idCategoria3=" + idCategoria3;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "SalvaGiocatore", Ricerca, Maschera, NS, SA);
	}

	public void EliminaGiocatore(Context context, String idGiocatore, String Maschera) {
		String Urletto="EliminaGiocatore?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idGiocatore=" + idGiocatore;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "EliminaGiocatore", "", Maschera, NS, SA);
	}
}
