package looigi.gestionecampionato.dati;

import android.content.Context;
import android.widget.TextView;

import com.anychart.AnyChartView;

import java.util.ArrayList;
import java.util.List;

public class VariabiliStaticheGrafici {
    //-------- Singleton ----------//
    private static VariabiliStaticheGrafici instance = null;

    private VariabiliStaticheGrafici() {
    }

    public static VariabiliStaticheGrafici getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheGrafici();
        }

        return instance;
    }

    private Context context;
    private AnyChartView anyChartView1;
    private AnyChartView anyChartView2;
    private List<Float> Gradi = new ArrayList<>();
    private List<Float> Umidita = new ArrayList<>();
    private List<Float> Pressione = new ArrayList<>();

    private List<String> Tempo = new ArrayList<>();
    private List<Integer> Quanti = new ArrayList<>();

    private List<Integer> FattiMinuto = new ArrayList<>();
    private List<Integer> FattiQuanti = new ArrayList<>();

    private List<Integer> SubitiMinuto = new ArrayList<>();
    private List<Integer> SubitiQuanti = new ArrayList<>();

    private List<Integer> GoalSegnati = new ArrayList<>();
    private List<Integer> GoalSubiti = new ArrayList<>();
    private List<Integer> GoalSegnatiPartita = new ArrayList<>();
    private List<Integer> GoalSubitiPartita = new ArrayList<>();

    private List<Integer> Andamento = new ArrayList<>();
    private List<Integer> AndamentoPartita = new ArrayList<>();

    private List<Integer> qTipologiaPartite = new ArrayList<>();
    private List<String> TipologiaPartite = new ArrayList<>();

    private List<Integer> qCasaFuori = new ArrayList<>();
    private List<String> CasaFuori = new ArrayList<>();

    private List<Integer> qAllenatori = new ArrayList<>();
    private List<String> Allenatori = new ArrayList<>();

    private TextView txtTitolo1;
    private TextView txtTitolo2;
    public int tipoGraficoMeteo;

    public List<Integer> getqTipologiaPartite() {
        return qTipologiaPartite;
    }

    public void setqTipologiaPartite(List<Integer> qTipologiaPartite) {
        this.qTipologiaPartite = qTipologiaPartite;
    }

    public List<String> getTipologiaPartite() {
        return TipologiaPartite;
    }

    public void setTipologiaPartite(List<String> tipologiaPartite) {
        TipologiaPartite = tipologiaPartite;
    }

    public List<Integer> getqCasaFuori() {
        return qCasaFuori;
    }

    public void setqCasaFuori(List<Integer> qCasaFuori) {
        this.qCasaFuori = qCasaFuori;
    }

    public List<String> getCasaFuori() {
        return CasaFuori;
    }

    public void setCasaFuori(List<String> casaFuori) {
        CasaFuori = casaFuori;
    }

    public List<Integer> getqAllenatori() {
        return qAllenatori;
    }

    public void setqAllenatori(List<Integer> qAllenatori) {
        this.qAllenatori = qAllenatori;
    }

    public List<String> getAllenatori() {
        return Allenatori;
    }

    public void setAllenatori(List<String> allenatori) {
        Allenatori = allenatori;
    }

    public List<Integer> getAndamento() {
        return Andamento;
    }

    public void setAndamento(List<Integer> andamento) {
        Andamento = andamento;
    }

    public List<Integer> getAndamentoPartita() {
        return AndamentoPartita;
    }

    public void setAndamentoPartita(List<Integer> andamentoPartita) {
        AndamentoPartita = andamentoPartita;
    }

    public List<Integer> getGoalSegnatiPartita() {
        return GoalSegnatiPartita;
    }

    public void setGoalSegnatiPartita(List<Integer> goalSegnatiPartita) {
        GoalSegnatiPartita = goalSegnatiPartita;
    }

    public List<Integer> getGoalSubitiPartita() {
        return GoalSubitiPartita;
    }

    public void setGoalSubitiPartita(List<Integer> goalSubitiPartita) {
        GoalSubitiPartita = goalSubitiPartita;
    }

    public List<Integer> getGoalSegnati() {
        return GoalSegnati;
    }

    public void setGoalSegnati(List<Integer> goalSegnati) {
        GoalSegnati = goalSegnati;
    }

    public List<Integer> getGoalSubiti() {
        return GoalSubiti;
    }

    public void setGoalSubiti(List<Integer> goalSubiti) {
        GoalSubiti = goalSubiti;
    }

    public AnyChartView getAnyChartView1() {
        return anyChartView1;
    }

    public void setAnyChartView1(AnyChartView anyChartView1) {
        this.anyChartView1 = anyChartView1;
    }

    public TextView getTxtTitolo1() {
        return txtTitolo1;
    }

    public void setTxtTitolo1(TextView txtTitolo1) {
        this.txtTitolo1 = txtTitolo1;
    }

    public TextView getTxtTitolo2() {
        return txtTitolo2;
    }

    public void setTxtTitolo2(TextView txtTitolo2) {
        this.txtTitolo2 = txtTitolo2;
    }

    public AnyChartView getAnyChartView2() {
        return anyChartView2;
    }

    public void setAnyChartView2(AnyChartView anyChartView2) {
        this.anyChartView2 = anyChartView2;
    }

    public List<Integer> getFattiMinuto() {
        return FattiMinuto;
    }

    public void setFattiMinuto(List<Integer> fattiMinuto) {
        FattiMinuto = fattiMinuto;
    }

    public List<Integer> getFattiQuanti() {
        return FattiQuanti;
    }

    public void setFattiQuanti(List<Integer> fattiQuanti) {
        FattiQuanti = fattiQuanti;
    }

    public List<Integer> getSubitiMinuto() {
        return SubitiMinuto;
    }

    public void setSubitiMinuto(List<Integer> subitiMinuto) {
        SubitiMinuto = subitiMinuto;
    }

    public List<Integer> getSubitiQuanti() {
        return SubitiQuanti;
    }

    public void setSubitiQuanti(List<Integer> subitiQuanti) {
        SubitiQuanti = subitiQuanti;
    }

    public List<Float> getGradi() {
        return Gradi;
    }

    public void setGradi(List<Float> gradi) {
        Gradi = gradi;
    }

    public List<Float> getUmidita() {
        return Umidita;
    }

    public void setUmidita(List<Float> umidita) {
        Umidita = umidita;
    }

    public List<Float> getPressione() {
        return Pressione;
    }

    public void setPressione(List<Float> pressione) {
        Pressione = pressione;
    }

    public List<String> getTempo() {
        return Tempo;
    }

    public void setTempo(List<String> tempo) {
        Tempo = tempo;
    }

    public List<Integer> getQuanti() {
        return Quanti;
    }

    public void setQuanti(List<Integer> quanti) {
        Quanti = quanti;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
