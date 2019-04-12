package looigi.gestionecampionato.dati;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import looigi.gestionecampionato.adapter.AdapterClassificaCampionato;
import looigi.gestionecampionato.adapter.AdapterPartiteCampionato;
import looigi.gestionecampionato.adapter.AdapterSquadreCampionato;

import java.util.List;

public class VariabiliStaticheCampionato {
    //-------- Singleton ----------//
    private static VariabiliStaticheCampionato instance = null;

    private VariabiliStaticheCampionato() {
    }

    public static VariabiliStaticheCampionato getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheCampionato();
        }

        return instance;
    }

    private StrutturaCampionato DatiCampionato;

    private View view;
    private List<String> Categorie;
    private List<Integer> idCategorie;
    private Spinner spnCategorie;
    private Context context;
    private LinearLayout rlMascheraCampionato;
    private RelativeLayout rlMascheraNuovaPartita;
    private int idCategoriaScelta;
    private LinearLayout layPagina1;
    private LinearLayout layPagina2;
    private LinearLayout layPagina3;
    private LinearLayout layTastoPagina1;
    private LinearLayout layTastoPagina2;
    private LinearLayout layTastoPagina3;
    private Spinner spnAvversario;
    private String AvversarioScelto;

    private TextView txtGiornata;
    private TextView txtClassifica;
    private EditText edtRisultato;
    private EditText edtNote;
    private Button cmdIndietro;
    private Button cmdAvanti;
    private Button cmdIndietroClass;
    private Button cmdAvantiClass;

    private List<String> Avversari;
    private List<Integer> AvversariID;
    private List<Integer> AvversariCampoID;
    private List<String> AvversariCampo;
    private List<String> AvversariCampoIndirizzo;
    private List<String> AvversariLat;
    private List<String> AvversariLon;

    private Spinner spnCasa;
    private Spinner spnFuori;
    private String SquadraSceltaCasa;
    private String SquadraSceltaFuori;
    private Button cmdOkSquadra;
    private Button cmdAnnullaSquadra;
    private TextView txtData;
    private TextView txtOra;
    private int ModalitaInserimento;
    private int idPartitaDaModificare;
    private boolean StaInserendo;
    private int idUnioneCalendario;

    private String MascheraSelezionata;

    public TextView getTxtClassifica() {
        return txtClassifica;
    }

    public void setTxtClassifica(TextView txtClassifica) {
        this.txtClassifica = txtClassifica;
    }

    public Button getCmdIndietroClass() {
        return cmdIndietroClass;
    }

    public void setCmdIndietroClass(Button cmdIndietroClass) {
        this.cmdIndietroClass = cmdIndietroClass;
    }

    public Button getCmdAvantiClass() {
        return cmdAvantiClass;
    }

    public void setCmdAvantiClass(Button cmdAvantiClass) {
        this.cmdAvantiClass = cmdAvantiClass;
    }

    public int getIdUnioneCalendario() {
        return idUnioneCalendario;
    }

    public void setIdUnioneCalendario(int idUnioneCalendario) {
        this.idUnioneCalendario = idUnioneCalendario;
    }

    public boolean isStaInserendo() {
        return StaInserendo;
    }

    public void setStaInserendo(boolean staInserendo) {
        StaInserendo = staInserendo;
    }

    public int getIdPartitaDaModificare() {
        return idPartitaDaModificare;
    }

    public void setIdPartitaDaModificare(int idPartitaDaModificare) {
        this.idPartitaDaModificare = idPartitaDaModificare;
    }

    public int getModalitaInserimento() {
        return ModalitaInserimento;
    }

    public void setModalitaInserimento(int modalitaInserimento) {
        ModalitaInserimento = modalitaInserimento;
    }

    public EditText getEdtNote() {
        return edtNote;
    }

    public void setEdtNote(EditText edtNote) {
        this.edtNote = edtNote;
    }

    public EditText getEdtRisultato() {
        return edtRisultato;
    }

    public void setEdtRisultato(EditText edtRisultato) {
        this.edtRisultato = edtRisultato;
    }

    public TextView getTxtData() {
        return txtData;
    }

    public void setTxtData(TextView txtData) {
        this.txtData = txtData;
    }

    public TextView getTxtOra() {
        return txtOra;
    }

    public void setTxtOra(TextView txtOra) {
        this.txtOra = txtOra;
    }

    public Button getCmdOkSquadra() {
        return cmdOkSquadra;
    }

    public void setCmdOkSquadra(Button cmdOkSquadra) {
        this.cmdOkSquadra = cmdOkSquadra;
    }

    public Button getCmdAnnullaSquadra() {
        return cmdAnnullaSquadra;
    }

    public void setCmdAnnullaSquadra(Button cmdAnnullaSquadra) {
        this.cmdAnnullaSquadra = cmdAnnullaSquadra;
    }

    public String getSquadraSceltaCasa() {
        return SquadraSceltaCasa;
    }

    public void setSquadraSceltaCasa(String squadraSceltaCasa) {
        SquadraSceltaCasa = squadraSceltaCasa;
    }

    public String getSquadraSceltaFuori() {
        return SquadraSceltaFuori;
    }

    public void setSquadraSceltaFuori(String squadraSceltaFuori) {
        SquadraSceltaFuori = squadraSceltaFuori;
    }

    public Spinner getSpnCasa() {
        return spnCasa;
    }

    public void setSpnCasa(Spinner spnCasa) {
        this.spnCasa = spnCasa;
    }

    public Spinner getSpnFuori() {
        return spnFuori;
    }

    public void setSpnFuori(Spinner spnFuori) {
        this.spnFuori = spnFuori;
    }

    public RelativeLayout getRlMascheraNuovaPartita() {
        return rlMascheraNuovaPartita;
    }

    public void setRlMascheraNuovaPartita(RelativeLayout rlMascheraNuovaPartita) {
        this.rlMascheraNuovaPartita = rlMascheraNuovaPartita;
    }

    public Button getCmdIndietro() {
        return cmdIndietro;
    }

    public void setCmdIndietro(Button cmdIndietro) {
        this.cmdIndietro = cmdIndietro;
    }

    public Button getCmdAvanti() {
        return cmdAvanti;
    }

    public void setCmdAvanti(Button cmdAvanti) {
        this.cmdAvanti = cmdAvanti;
    }

    public TextView getTxtGiornata() {
        return txtGiornata;
    }

    public void setTxtGiornata(TextView txtGiornata) {
        this.txtGiornata = txtGiornata;
    }

    // public TextView getTxtDataGiornata() {
    //     return txtDataGiornata;
    // }

    // public void setTxtDataGiornata(TextView txtDataGiornata) {
    //     this.txtDataGiornata = txtDataGiornata;
    // }

    public List<String> getAvversariLat() {
        return AvversariLat;
    }

    public void setAvversariLat(List<String> avversariLat) {
        AvversariLat = avversariLat;
    }

    public List<String> getAvversariLon() {
        return AvversariLon;
    }

    public void setAvversariLon(List<String> avversariLon) {
        AvversariLon = avversariLon;
    }

    public String getAvversarioScelto() {
        return AvversarioScelto;
    }

    public void setAvversarioScelto(String avversarioScelto) {
        AvversarioScelto = avversarioScelto;
    }

    public String getMascheraSelezionata() {
        return MascheraSelezionata;
    }

    public void setMascheraSelezionata(String mascheraSelezionata) {
        MascheraSelezionata = mascheraSelezionata;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public List<Integer> getAvversariID() {
        return AvversariID;
    }

    public void setAvversariID(List<Integer> avversariID) {
        AvversariID = avversariID;
    }

    public List<Integer> getAvversariCampoID() {
        return AvversariCampoID;
    }

    public void setAvversariCampoID(List<Integer> avversariCampoID) {
        AvversariCampoID = avversariCampoID;
    }

    public List<String> getAvversariCampo() {
        return AvversariCampo;
    }

    public void setAvversariCampo(List<String> avversariCampo) {
        AvversariCampo = avversariCampo;
    }

    public List<String> getAvversariCampoIndirizzo() {
        return AvversariCampoIndirizzo;
    }

    public void setAvversariCampoIndirizzo(List<String> avversariCampoIndirizzo) {
        AvversariCampoIndirizzo = avversariCampoIndirizzo;
    }

    public List<String> getAvversari() {
        return Avversari;
    }

    public void setAvversari(List<String> avversari) {
        Avversari = avversari;
    }

    private AdapterSquadreCampionato adapterLvSquadre;
    private ListView lvSquadre;

    private AdapterPartiteCampionato adapterLvPartite;
    private ListView lvPartite;

    private AdapterClassificaCampionato adapterLvClassifica;
    private ListView lvClassifica;

    public Spinner getSpnAvversario() {
        return spnAvversario;
    }

    public void setSpnAvversario(Spinner spnAvversario) {
        this.spnAvversario = spnAvversario;
    }

    public AdapterPartiteCampionato getAdapterLvPartite() {
        return adapterLvPartite;
    }

    public void setAdapterLvPartite(AdapterPartiteCampionato adapterLvPartite) {
        this.adapterLvPartite = adapterLvPartite;
    }

    public ListView getLvPartite() {
        return lvPartite;
    }

    public void setLvPartite(ListView lvPartite) {
        this.lvPartite = lvPartite;
    }

    public AdapterClassificaCampionato getAdapterLvClassifica() {
        return adapterLvClassifica;
    }

    public void setAdapterLvClassifica(AdapterClassificaCampionato adapterLvClassifica) {
        this.adapterLvClassifica = adapterLvClassifica;
    }

    public ListView getLvClassifica() {
        return lvClassifica;
    }

    public void setLvClassifica(ListView lvClassifica) {
        this.lvClassifica = lvClassifica;
    }

    public ListView getLvSquadre() {
        return lvSquadre;
    }

    public void setLvSquadre(ListView lvSquadre) {
        this.lvSquadre = lvSquadre;
    }

    public AdapterSquadreCampionato getAdapterLvSquadre() {
        return adapterLvSquadre;
    }

    public void setAdapterLvSquadre(AdapterSquadreCampionato adapterLvSquadre) {
        this.adapterLvSquadre = adapterLvSquadre;
    }

    public LinearLayout getLayTastoPagina1() {
        return layTastoPagina1;
    }

    public void setLayTastoPagina1(LinearLayout layTastoPagina1) {
        this.layTastoPagina1 = layTastoPagina1;
    }

    public LinearLayout getLayTastoPagina2() {
        return layTastoPagina2;
    }

    public void setLayTastoPagina2(LinearLayout layTastoPagina2) {
        this.layTastoPagina2 = layTastoPagina2;
    }

    public LinearLayout getLayTastoPagina3() {
        return layTastoPagina3;
    }

    public void setLayTastoPagina3(LinearLayout layTastoPagina3) {
        this.layTastoPagina3 = layTastoPagina3;
    }

    public LinearLayout getLayPagina1() {
        return layPagina1;
    }

    public void setLayPagina1(LinearLayout layPagina1) {
        this.layPagina1 = layPagina1;
    }

    public LinearLayout getLayPagina2() {
        return layPagina2;
    }

    public void setLayPagina2(LinearLayout layPagina2) {
        this.layPagina2 = layPagina2;
    }

    public LinearLayout getLayPagina3() {
        return layPagina3;
    }

    public void setLayPagina3(LinearLayout layPagina3) {
        this.layPagina3 = layPagina3;
    }

    public StrutturaCampionato getDatiCampionato() {
        return DatiCampionato;
    }

    public void setDatiCampionato(StrutturaCampionato datiCampionato) {
        DatiCampionato = datiCampionato;
    }

    public int getIdCategoriaScelta() {
        return idCategoriaScelta;
    }

    public void setIdCategoriaScelta(int idCategoriaScelta) {
        this.idCategoriaScelta = idCategoriaScelta;
    }

    public List<String> getCategorie() {
        return Categorie;
    }

    public void setCategorie(List<String> categorie) {
        Categorie = categorie;
    }

    public List<Integer> getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(List<Integer> idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Spinner getSpnCategorie() {
        return spnCategorie;
    }

    public void setSpnCategorie(Spinner spnCategorie) {
        this.spnCategorie = spnCategorie;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public LinearLayout getRlMascheraCampionato() {
        return rlMascheraCampionato;
    }

    public void setRlMascheraCampionato(LinearLayout rlMascheraCampionato) {
        this.rlMascheraCampionato = rlMascheraCampionato;
    }
}
