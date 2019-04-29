package looigi.gestionecampionato.dati;

import android.content.Context;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class VariabiliStaticheGlobali {
    //-------- Singleton ----------//
    private static VariabiliStaticheGlobali instance = null;

    private VariabiliStaticheGlobali() {
    }

    public static VariabiliStaticheGlobali getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheGlobali();
        }

        return instance;
    }

    public String PercorsoDIR=Environment.getExternalStorageDirectory().getPath()+"/LooigiSoft/cvCalcio";
    public boolean ApreDialogWS=true;
    private Context context;
    private AppCompatActivity contextPrincipale;
    private FragmentActivity FragmentActivityPrincipale;
    private View ViewActivity;
    private int AnnoInCorso;
    private String DescAnnoInCorso;
    private String NomeSquadraAnno;
    private String OperazioneInCorso;
    private String LatAnno;
    private String LonAnno;
    public static String MascheraAttuale;
    public static String MascheraAttualePerMultimedia;
    public static String ImmagineDaEliminare;
    public static String RadiceWS = "http://looigi.no-ip.biz:12345/wsCvCalcio/";
    public static String RadiceUpload = "http://looigi.no-ip.biz:12345/CvCalcioUploadPic/default.aspx";
    private StrutturaDatiUtente DatiUtente;
    public VariabiliStaticheUtenti vPerPassaggio;
    public static Boolean StaAggiornandoLaVersione=false;
    final public static String ValoreAmministratore = "1";
    final public static String NomeApplicazione="Gestione campionato";

    final public static String NomeSquadraCastelVerde="CASTELVERDE";
    final public static String NomeSquadraPonteDiNona="PONTEDINONA";

    public String getLatAnno() {
        return LatAnno;
    }

    public void setLatAnno(String latAnno) {
        LatAnno = latAnno;
    }

    public String getLonAnno() {
        return LonAnno;
    }

    public void setLonAnno(String lonAnno) {
        LonAnno = lonAnno;
    }

    public String getNomeSquadraAnno() {
        return NomeSquadraAnno;
    }

    public void setNomeSquadraAnno(String nomeSquadraAnno) {
        NomeSquadraAnno = nomeSquadraAnno;
    }

    public StrutturaDatiUtente getDatiUtente() {
        return DatiUtente;
    }

    public void setDatiUtente(StrutturaDatiUtente datiUtente) {
        DatiUtente = datiUtente;
    }

    public String getDescAnnoInCorso() {
        return DescAnnoInCorso;
    }

    public void setDescAnnoInCorso(String descAnnoInCorso) {
        DescAnnoInCorso = descAnnoInCorso;
    }

    public AppCompatActivity getContextPrincipale() {
        return contextPrincipale;
    }

    public void setContextPrincipale(AppCompatActivity contextPrincipale) {
        this.contextPrincipale = contextPrincipale;
    }

    public String getOperazioneInCorso() {
        return OperazioneInCorso;
    }

    public void setOperazioneInCorso(String operazioneInCorso) {
        OperazioneInCorso = operazioneInCorso;
    }

    public int getAnnoInCorso() {
        return AnnoInCorso;
    }

    public void setAnnoInCorso(int annoInCorso) {
        AnnoInCorso = annoInCorso;
    }

    public View getViewActivity() {
        return ViewActivity;
    }

    public void setViewActivity(View viewActivity) {
        ViewActivity = viewActivity;
    }

    public FragmentActivity getFragmentActivityPrincipale() {
        return FragmentActivityPrincipale;
    }

    public void setFragmentActivityPrincipale(FragmentActivity fragmentActivityPrincipale) {
        FragmentActivityPrincipale = fragmentActivityPrincipale;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void PulisceTutteLeVariabili() {
        VariabiliStaticheAllenatori.getInstance().PulisceTuttiGliArray();
        VariabiliStaticheAvversari.getInstance().PulisceTuttiGliArray();
        VariabiliStaticheMain.getInstance().PulisceTuttiGliArray();
        VariabiliStaticheNuovaPartita.getInstance().PulisceTuttiGliArray();
        VariabiliStaticheCategorie.getInstance().PulisceTuttiGliArray();
        VariabiliStaticheRose.getInstance().PulisceTuttiGliArray();
    }
}
