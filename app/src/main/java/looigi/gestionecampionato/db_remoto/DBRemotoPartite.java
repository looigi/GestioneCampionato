package looigi.gestionecampionato.db_remoto;

import android.content.Context;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheUtenti;
import looigi.gestionecampionato.utilities.Utility;

public class DBRemotoPartite {
	private String ws = "wsPartite.asmx/";
	private String NS="http://cvcalcio_part.org/";
	private String SA="http://cvcalcio_part.org/";

	public void RitornaPartitaDaID(Context context, String idPartita, String Maschera) {
		String Urletto="RitornaPartitaDaID?idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idPartita=" + idPartita;
		
		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaPartitaDaID", "", Maschera, NS, SA);
	}

    public void RitornaIdPartita(Context context, String Maschera) {
        String Urletto="RitornaIdPartita";

        Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaIdPartita","",  Maschera, NS, SA);
    }

	public void RitornaPartite(Context context, String Maschera) {
		String Urletto="RitornaPartite?idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idCategoria=" + VariabiliStaticheUtenti.getInstance().getIdCategoriaScelta();

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaPartite", "", Maschera, NS, SA);
	}

	public void SalvaPartita(Context context, String idPartita, String idCategoria, String idAvversario, String idAllenatore,
                             String DataOra, String Casa, String idTipologia, String idCampo, String Risultato,
                             String Note, String Marcatori, String Convocati, String RisGiochetti, String GoalAvversari,
                             String Campo, String Tempo1Tempo, String Tempo2Tempo, String Tempo3Tempo, String Coordinate,
                             String Tempo, String idUnioneCalendario, String Tga1, String Tga2, String Tga3, String Dirigenti,
							 String idArbitro, String RisultatoATempi, String RigoriPropri, String RigoriAvv, String Maschera) {
		String Urletto="SalvaPartita?";
        Urletto+="idPartita=" + idPartita;
        Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idCategoria=" + idCategoria;
		Urletto+="&idAvversario=" + idAvversario;
		Urletto+="&idAllenatore=" + idAllenatore;
		Urletto+="&DataOra=" + DataOra;
		Urletto+="&Casa=" + Casa;
		Urletto+="&idTipologia=" + idTipologia;
		Urletto+="&idCampo=" + idCampo;
		Urletto+="&Risultato=" + Risultato;
		Urletto+="&Notelle=" + Note;
		Urletto+="&Marcatori=" + Marcatori;
		Urletto+="&Convocati=" + Convocati;
        Urletto+="&RisGiochetti=" + RisGiochetti;
		Urletto+="&RisAvv=" + GoalAvversari;
		Urletto+="&Campo=" + Campo;
		Urletto+="&Tempo1Tempo=" + Tempo1Tempo;
		Urletto+="&Tempo2Tempo=" + Tempo2Tempo;
		Urletto+="&Tempo3Tempo=" + Tempo3Tempo;
		Urletto+="&Coordinate=" + Coordinate;
		Urletto+="&sTempo=" + Tempo;
		Urletto+="&idUnioneCalendario=" + idUnioneCalendario;
		Urletto+="&TGA1=" + Tga1;
		Urletto+="&TGA2=" + Tga2;
		Urletto+="&TGA3=" + Tga3;
		Urletto+="&Dirigenti=" + Dirigenti;
		Urletto+="&idArbitro=" + idArbitro;
		Urletto+="&RisultatoATempi=" + RisultatoATempi;
		Urletto+="&RigoriPropri=" + RigoriPropri;
		Urletto+="&RigoriAvv=" + RigoriAvv;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "SalvaPartita", "", Maschera, NS, SA);
	}
}
