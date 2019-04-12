package looigi.gestionecampionato.dati;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import looigi.gestionecampionato.adapter.AdapterModificaUtenti;

import java.util.List;

public class VariabiliStaticheUtenti {
    //-------- Singleton ----------//
    private static VariabiliStaticheUtenti instance = null;

    private VariabiliStaticheUtenti() {
    }

    public static VariabiliStaticheUtenti getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheUtenti();
        }

        return instance;
    }

    private Context context;
    private Button cmdSalva;
    private Button cmdAnnulla;
    private Button cmdLogin;
    private EditText edtUtente;
    private EditText edtCognome;
    private EditText edtNome;
    private EditText edtCategoria;
    private EditText edtUtenteLogin;
    private EditText edtPasswordLogin;
    private RadioButton optAdmin;
    private RadioButton optUser;
    private EditText edtPassword;
    private EditText edtMail;
    private Spinner spnNomeSquadra;
    private List<String> NomiSquadre;
    private List<Integer> idAnno;
    private ListView lstUtenti;
    private AdapterModificaUtenti adapterModificaUtenti;
    private List<String> Utenti;
    private RelativeLayout rlMaschera;
    public static String NomeSquadraScelta="";
    public static int idAnnoScelto;
    private Integer idCategoriaScelta;
    private Spinner spnCategorie;
    private List<String> NomiCategorie;
    private List<Integer> idNomiCategorie;

    public List<String> getNomiCategorie() {
        return NomiCategorie;
    }

    public void setNomiCategorie(List<String> nomiCategorie) {
        NomiCategorie = nomiCategorie;
    }

    public void setIdNomiCategorie(List<Integer> idNomiCategorie) {
        this.idNomiCategorie = idNomiCategorie;
    }

    public Spinner getSpnCategorie() {
        return spnCategorie;
    }

    public void setSpnCategorie(Spinner spnCategorie) {
        this.spnCategorie = spnCategorie;
    }

    /* public List<Integer> getIdCategorie1() {
        return idCategorie;
    } */

    public List<Integer> getIdNomiCategorie() {
        return this.idNomiCategorie;
    }

    public Integer getIdCategoriaScelta() {
        return idCategoriaScelta;
    }

    public void setIdCategoriaScelta(Integer idCategoriaScelta) {
        this.idCategoriaScelta = idCategoriaScelta;
    }

    public List<Integer> getIdAnno() {
        return idAnno;
    }

    public void setIdAnno(List<Integer> idAnno) {
        this.idAnno = idAnno;
    }

    public AdapterModificaUtenti getAdapterModificaUtenti() {
        return adapterModificaUtenti;
    }

    public void setAdapterModificaUtenti(AdapterModificaUtenti adapterModificaUtenti) {
        this.adapterModificaUtenti = adapterModificaUtenti;
    }

    /* public List<Integer> getIdCategorie1() {
        return idCategorie;
    }

    public void setIdCategorie(List<Integer> idCategorie) {
        this.idCategorie = idCategorie;
    } */

    public Button getCmdLogin() {
        return cmdLogin;
    }

    public void setCmdLogin(Button cmdLogin) {
        this.cmdLogin = cmdLogin;
    }

    public EditText getEdtUtenteLogin() {
        return edtUtenteLogin;
    }

    public void setEdtUtenteLogin(EditText edtUtenteLogin) {
        this.edtUtenteLogin = edtUtenteLogin;
    }

    public EditText getEdtPasswordLogin() {
        return edtPasswordLogin;
    }

    public void setEdtPasswordLogin(EditText edtPasswordLogin) {
        this.edtPasswordLogin = edtPasswordLogin;
    }

    public RadioButton getOptAdmin() {
        return optAdmin;
    }

    public void setOptAdmin(RadioButton optAdmin) {
        this.optAdmin = optAdmin;
    }

    public RadioButton getOptUser() {
        return optUser;
    }

    public void setOptUser(RadioButton optUser) {
        this.optUser = optUser;
    }

    public EditText getEdtCategoria() {
        return edtCategoria;
    }

    public void setEdtCategoria(EditText edtCategoria) {
        this.edtCategoria = edtCategoria;
    }

    public RelativeLayout getRlMaschera() {
        return rlMaschera;
    }

    public void setRlMaschera(RelativeLayout rlMaschera) {
        this.rlMaschera = rlMaschera;
    }

    public List<String> getUtenti() {
        return Utenti;
    }

    public void setUtenti(List<String> utenti) {
        Utenti = utenti;
    }

    public ListView getLstUtenti() {
        return lstUtenti;
    }

    public void setLstUtenti(ListView lstUtenti) {
        this.lstUtenti = lstUtenti;
    }

    public Button getCmdAnnulla() {
        return cmdAnnulla;
    }

    public void setCmdAnnulla(Button cmdAnnulla) {
        this.cmdAnnulla = cmdAnnulla;
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

    public List<String> getNomiSquadre() {
        return NomiSquadre;
    }

    public void setNomiSquadre(List<String> nomiSquadre) {
        NomiSquadre = nomiSquadre;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Button getCmdSalva() {
        return cmdSalva;
    }

    public void setCmdSalva(Button cmdSalva) {
        this.cmdSalva = cmdSalva;
    }

    public EditText getEdtUtente() {
        return edtUtente;
    }

    public void setEdtUtente(EditText edtUtente) {
        this.edtUtente = edtUtente;
    }

    public EditText getEdtPassword() {
        return edtPassword;
    }

    public void setEdtPassword(EditText edtPassword) {
        this.edtPassword = edtPassword;
    }

    public EditText getEdtMail() {
        return edtMail;
    }

    public void setEdtMail(EditText edtMail) {
        this.edtMail = edtMail;
    }

    public Spinner getSpnNomeSquadra() {
        return spnNomeSquadra;
    }

    public void setSpnNomeSquadra(Spinner spnNomeSquadra) {
        this.spnNomeSquadra = spnNomeSquadra;
    }
}
