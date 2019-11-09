package looigi.gestionecampionato.dati;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.adapter.AdapterGiocatori;

public class VariabiliStaticheRose {
    //-------- Singleton ----------//
    private static VariabiliStaticheRose instance = null;

    private VariabiliStaticheRose() {
    }

    public static VariabiliStaticheRose getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheRose();
        }

        return instance;
    }

    private List<String> Giocatori;
    private List<String> Categorie;
    private List<Integer> idCategorie;
    private List<String> Ruoli;

    public void PulisceTuttiGliArray() {
        Giocatori=new ArrayList<String>();
        idCategorie=new ArrayList<Integer>();
        Ruoli=new ArrayList<>();
    }

    private ListView lstGiocatori;
    private AdapterGiocatori adapterGiocatori;
    private Spinner spnCategorie;
    private Spinner spnRuoli;
    private Spinner spnCategorie1;
    private Spinner spnCategorie2;
    private Spinner spnCategorie3;
    private Context context;
    private RelativeLayout rlMaschera;
    private EditText edtCognome;
    private EditText edtNome;
    private EditText edtEMail;
    private EditText edttelefono;
    private EditText edtSoprannome;
    private EditText edtDataNascita;
    private EditText edtIndirizzo;
    private EditText edtCodFisc;
    private EditText edtCitta;
    private EditText edtMatricola;
    private EditText edtNumeroMaglia;
    private TextView txtId;
    private ImageView imgGiocatore;
    private Button cmdOk;
    private Button cmdElimina;
    private Button cmdAnnulla;
    public int idCategoriaScelta;
    public int idCategoriaScelta1;
    public int idCategoriaScelta2;
    public int idCategoriaScelta3;
    public int idGiocatoreScelto;
    //public String RuoloPerModifica;
    private ImageView imgScegliFoto;
    private ImageView imgEliminaFoto;
    private RadioButton optMaschio;
    private RadioButton optFemmina;
    private ImageView imgRefreshFoto;

    public List<String> getCategorie() {
        return Categorie;
    }

    public void setCategorie(List<String> categorie) {
        Categorie = categorie;
    }

    public Spinner getSpnCategorie1() {
        return spnCategorie1;
    }

    public void setSpnCategorie1(Spinner spnCategorie1) {
        this.spnCategorie1 = spnCategorie1;
    }

    public Spinner getSpnCategorie3() {
        return spnCategorie3;
    }

    public void setSpnCategorie3(Spinner spnCategorie3) {
        this.spnCategorie3 = spnCategorie3;
    }

    public Spinner getSpnCategorie2() {
        return spnCategorie2;
    }

    public void setSpnCategorie2(Spinner spnCategorie2) {
        this.spnCategorie2 = spnCategorie2;
    }

    public EditText getEdtNumeroMaglia() {
        return edtNumeroMaglia;
    }

    public void setEdtNumeroMaglia(EditText edtNumeroMaglia) {
        this.edtNumeroMaglia = edtNumeroMaglia;
    }

    public ImageView getImgRefreshFoto() {
        return imgRefreshFoto;
    }

    public void setImgRefreshFoto(ImageView imgRefreshFoto) {
        this.imgRefreshFoto = imgRefreshFoto;
    }

    public List<String> getRuoli() {
        return Ruoli;
    }

    public void setRuoli(List<String> ruoli) {
        Ruoli = ruoli;
    }

    public Spinner getSpnRuoli() {
        return spnRuoli;
    }

    public void setSpnRuoli(Spinner spnRuoli) {
        this.spnRuoli = spnRuoli;
    }

    public RadioButton getOptMaschio() {
        return optMaschio;
    }

    public void setOptMaschio(RadioButton optMaschio) {
        this.optMaschio = optMaschio;
    }

    public RadioButton getOptFemmina() {
        return optFemmina;
    }

    public void setOptFemmina(RadioButton optFemmina) {
        this.optFemmina = optFemmina;
    }

    public EditText getEdtSoprannome() {
        return edtSoprannome;
    }

    public void setEdtSoprannome(EditText edtSoprannome) {
        this.edtSoprannome = edtSoprannome;
    }

    public EditText getEdtDataNascita() {
        return edtDataNascita;
    }

    public void setEdtDataNascita(EditText edtDataNascita) {
        this.edtDataNascita = edtDataNascita;
    }

    public EditText getEdtIndirizzo() {
        return edtIndirizzo;
    }

    public void setEdtIndirizzo(EditText edtIndirizzo) {
        this.edtIndirizzo = edtIndirizzo;
    }

    public EditText getEdtCodFisc() {
        return edtCodFisc;
    }

    public void setEdtCodFisc(EditText edtCodFisc) {
        this.edtCodFisc = edtCodFisc;
    }

    public EditText getEdtCitta() {
        return edtCitta;
    }

    public void setEdtCitta(EditText edtCitta) {
        this.edtCitta = edtCitta;
    }

    public EditText getEdtMatricola() {
        return edtMatricola;
    }

    public void setEdtMatricola(EditText edtMatricola) {
        this.edtMatricola = edtMatricola;
    }

    public Button getCmdElimina() {
        return cmdElimina;
    }

    public void setCmdElimina(Button cmdElimina) {
        this.cmdElimina = cmdElimina;
    }

    public ImageView getImgScegliFoto() {
        return imgScegliFoto;
    }

    public void setImgScegliFoto(ImageView imgScegliFoto) {
        this.imgScegliFoto = imgScegliFoto;
    }

    public ImageView getImgEliminaFoto() {
        return imgEliminaFoto;
    }

    public void setImgEliminaFoto(ImageView imgEliminaFoto) {
        this.imgEliminaFoto = imgEliminaFoto;
    }

    public Button getCmdOk() {
        return cmdOk;
    }

    public void setCmdOk(Button cmdOk) {
        this.cmdOk = cmdOk;
    }

    public Button getCmdAnnulla() {
        return cmdAnnulla;
    }

    public void setCmdAnnulla(Button cmdAnnulla) {
        this.cmdAnnulla = cmdAnnulla;
    }

    public TextView getTxtId() {
        return txtId;
    }

    public void setTxtId(TextView txtId) {
        this.txtId = txtId;
    }

    public EditText getEdtCognome() {
        return edtCognome;
    }

    public void setEdtCognome(EditText edtCognome) {
        this.edtCognome = edtCognome;
    }

    public EditText getEdtNome() {
        return edtNome;
    }

    public void setEdtNome(EditText edtNome) {
        this.edtNome = edtNome;
    }

    public EditText getEdtEMail() {
        return edtEMail;
    }

    public void setEdtEMail(EditText edtEMail) {
        this.edtEMail = edtEMail;
    }

    public EditText getEdttelefono() {
        return edttelefono;
    }

    public void setEdttelefono(EditText edttelefono) {
        this.edttelefono = edttelefono;
    }

    public ImageView getImgGiocatore() {
        return imgGiocatore;
    }

    public void setImgGiocatore(ImageView imgGiocatore) {
        this.imgGiocatore = imgGiocatore;
    }

    public RelativeLayout getRlMaschera() {
        return rlMaschera;
    }

    public void setRlMaschera(RelativeLayout rlMaschera) {
        this.rlMaschera = rlMaschera;
    }

    public List<Integer> getIdCategorie1() {
        return idCategorie;
    }

    public void setIdCategorie(List<Integer> idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Spinner getSpnCategorie() {
        return spnCategorie;
    }

    public void setSpnCategorie(Spinner spnCategorie) {
        this.spnCategorie = spnCategorie;
    }

    public ListView getLstGiocatori() {
        return lstGiocatori;
    }

    public void setLstGiocatori(ListView lstGiocatori) {
        this.lstGiocatori = lstGiocatori;
    }

    public AdapterGiocatori getAdapterGiocatori() {
        return adapterGiocatori;
    }

    public void setAdapterGiocatori(AdapterGiocatori adapterGiocatori) {
        this.adapterGiocatori = adapterGiocatori;
    }

    public List<String> getGiocatori() {
        return Giocatori;
    }

    public void setGiocatori(List<String> giocatori) {
        Giocatori = giocatori;
    }
}
