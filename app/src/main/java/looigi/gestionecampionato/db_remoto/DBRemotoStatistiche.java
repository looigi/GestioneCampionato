package looigi.gestionecampionato.db_remoto;

import android.content.Context;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheStatistiche;
import looigi.gestionecampionato.utilities.Utility;

public class DBRemotoStatistiche {
    private String ws = "wsStatistiche.asmx/";
    private String NS="http://cvcalcio_stat.org/";
    private String SA="http://cvcalcio_stat.org/";

    public void RitornaStatisticheAvversari(Context context, String Maschera, String SoloAnno, String idCategoria) {
        String Urletto="RitornaStatisticheAvversari?";
        Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto+="&SoloAnno=" + SoloAnno;
        Urletto+="&idCategoria=" + idCategoria;

        Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaStatisticheAvversari", "", Maschera, NS, SA);
    }

    public void StatisticheAnnue(Context context) {
        String Urletto="RitornaStatisticheStagione?";
        Urletto += "idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto += "&idCategoria=" + VariabiliStaticheStatistiche.getInstance().idCategoriaScelta;

        Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaStatisticheStagione",
                "", "", NS, SA);
    }

    public void RitornaStatisticheConvocati(Context context, String Maschera, String SoloAnno, String idCategoria) {
        String Urletto="RitornaStatisticheConvocati?";
        Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto+="&SoloAnno=" + SoloAnno;
        Urletto+="&idCategoria=" + idCategoria;

        Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaStatisticheConvocati", "", Maschera, NS, SA);
    }

    public void RitornaStatisticheMarcatori(Context context, String Maschera, String SoloAnno, String idCategoria) {
        String Urletto="RitornaStatisticheMarcatori?";
        Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto+="&SoloAnno=" + SoloAnno;
        Urletto+="&idCategoria=" + idCategoria;

        Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaStatisticheMarcatori", "", Maschera, NS, SA);
    }

	public void RitornaStatistichePartite(Context context, String Maschera, String SoloAnno, String idCategoria) {
		String Urletto="RitornaStatisticheRisultati?";
		Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&SoloAnno=" + SoloAnno;
        Urletto+="&idCategoria=" + idCategoria;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaStatisticheRisultati", "", Maschera, NS, SA);
	}

    public void RitornaStatisticheMappa(Context context, String Maschera, String SoloAnno, String idCategoria) {
        String Urletto="RitornaStatisticheMappa?";
        Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto+="&SoloAnno=" + SoloAnno;
        Urletto+="&idCategoria=" + idCategoria;

        Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaStatisticheMappa", "", Maschera, NS, SA);
    }

    public void RitornaStatisticheMeteo(Context context, String Maschera, String SoloAnno, String idCategoria) {
        String Urletto="RitornaStatisticheMeteo?";
        Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto+="&SoloAnno=" + SoloAnno;
        Urletto+="&idCategoria=" + idCategoria;

        Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaStatisticheMeteo", "", Maschera, NS, SA);
    }

    public void RitornaStatisticheMinutiGoal(Context context, String Maschera, String SoloAnno, String idCategoria) {
        String Urletto="RitornaStatisticheMinutiGoal?";
        Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto+="&SoloAnno=" + SoloAnno;
        Urletto+="&idCategoria=" + idCategoria;

        Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaStatisticheMinutiGoal", "", Maschera, NS, SA);
    }

    public void RitornaStatisticheGoalSegnatiSubiti(Context context, String Maschera, String SoloAnno, String idCategoria) {
        String Urletto="RitornaStatisticheGoalSegnatiSubiti?";
        Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto+="&SoloAnno=" + SoloAnno;
        Urletto+="&idCategoria=" + idCategoria;

        Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaStatisticheGoalSegnatiSubiti", "", Maschera, NS, SA);
    }

    public void RitornaAndamento(Context context, String Maschera, String SoloAnno, String idCategoria) {
        String Urletto="RitornaAndamento?";
        Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto+="&SoloAnno=" + SoloAnno;
        Urletto+="&idCategoria=" + idCategoria;

        Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaAndamento", "", Maschera, NS, SA);
    }

    public void RitornaTipologiePartite(Context context, String Maschera, String SoloAnno, String idCategoria) {
        String Urletto="RitornaTipologiePartite?";
        Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto+="&SoloAnno=" + SoloAnno;
        Urletto+="&idCategoria=" + idCategoria;

        Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaTipologiaPartite", "", Maschera, NS, SA);
    }

    public void RitornaPartiteCasaFuori(Context context, String Maschera, String SoloAnno, String idCategoria) {
        String Urletto="RitornaPartiteCasaFuori?";
        Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto+="&SoloAnno=" + SoloAnno;
        Urletto+="&idCategoria=" + idCategoria;

        Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaPartiteCasaFuori", "", Maschera, NS, SA);
    }

    public void RitornaPartiteAllenatore(Context context, String Maschera, String SoloAnno, String idCategoria) {
        String Urletto="RitornaPartiteAllenatore?";
        Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
        Urletto+="&SoloAnno=" + SoloAnno;
        Urletto+="&idCategoria=" + idCategoria;

        Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaPartiteAllenatore", "", Maschera, NS, SA);
    }
}
