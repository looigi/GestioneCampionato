package looigi.gestionecampionato.dati;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.List;
import looigi.gestionecampionato.adapter.AdapterGiocatoriAllAssenti;
import looigi.gestionecampionato.adapter.AdapterGiocatoriAllPresenti;

public class VariabiliStaticheAllenamenti {
    private static VariabiliStaticheAllenamenti instance = null;
    private List<String> Categorie;
    private List<String> GiocatoriAssenti;
    private List<String> GiocatoriPresenti;
    private AdapterGiocatoriAllAssenti adapterGiocatoriAssenti;
    private AdapterGiocatoriAllPresenti adapterGiocatoriPresenti;
    private Button btnRitornaAllenamenti;
    private Button cmdSalvaAllenamenti;
    private Context context;
    public int idCategoriaScelta;
    private List<Integer> idCategorie;
    private LinearLayout layDataOra;
    private LinearLayout layListe;
    private LinearLayout layTasti;
    private ListView lstGiocatoriAssenti;
    private ListView lstGiocatoriPresenti;
    private Spinner spnCategorie;
    private TextView txtData;
    private TextView txtOra;

    private VariabiliStaticheAllenamenti() {
    }

    public static VariabiliStaticheAllenamenti getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheAllenamenti();
        }
        return instance;
    }

    public Button getBtnRitornaAllenamenti() {
        return this.btnRitornaAllenamenti;
    }

    public void setBtnRitornaAllenamenti(Button btnSalvaAllenamenti) {
        this.btnRitornaAllenamenti = btnSalvaAllenamenti;
    }

    public LinearLayout getLayTasti() {
        return this.layTasti;
    }

    public void setLayTasti(LinearLayout layTasti) {
        this.layTasti = layTasti;
    }

    public List<String> getGiocatoriPresenti() {
        return this.GiocatoriPresenti;
    }

    public void setGiocatoriPresenti(List<String> giocatoriPresenti) {
        this.GiocatoriPresenti = giocatoriPresenti;
    }

    public ListView getLstGiocatoriPresenti() {
        return this.lstGiocatoriPresenti;
    }

    public void setLstGiocatoriPresenti(ListView lstGiocatoriPresenti) {
        this.lstGiocatoriPresenti = lstGiocatoriPresenti;
    }

    public AdapterGiocatoriAllPresenti getAdapterGiocatoriPresenti() {
        return this.adapterGiocatoriPresenti;
    }

    public void setAdapterGiocatoriPresenti(AdapterGiocatoriAllPresenti adapterGiocatoriPresenti) {
        this.adapterGiocatoriPresenti = adapterGiocatoriPresenti;
    }

    public LinearLayout getLayListe() {
        return this.layListe;
    }

    public void setLayListe(LinearLayout layListe) {
        this.layListe = layListe;
    }

    public ListView getLstGiocatoriAssenti() {
        return this.lstGiocatoriAssenti;
    }

    public void setLstGiocatoriAssenti(ListView lstGiocatori) {
        this.lstGiocatoriAssenti = lstGiocatori;
    }

    public AdapterGiocatoriAllAssenti getAdapterGiocatoriAssenti() {
        return this.adapterGiocatoriAssenti;
    }

    public void setAdapterGiocatoriAssenti(AdapterGiocatoriAllAssenti adapterGiocatori) {
        this.adapterGiocatoriAssenti = adapterGiocatori;
    }

    public List<String> getGiocatoriAssenti() {
        return this.GiocatoriAssenti;
    }

    public void setGiocatoriAssenti(List<String> giocatori) {
        this.GiocatoriAssenti = giocatori;
    }

    public TextView getTxtData() {
        return this.txtData;
    }

    public void setTxtData(TextView txtData) {
        this.txtData = txtData;
    }

    public TextView getTxtOra() {
        return this.txtOra;
    }

    public void setTxtOra(TextView txtOra) {
        this.txtOra = txtOra;
    }

    public Button getCmdSalvaAllenamenti() {
        return this.cmdSalvaAllenamenti;
    }

    public void setCmdSalvaAllenamenti(Button cmdSalvaAllenamenti) {
        this.cmdSalvaAllenamenti = cmdSalvaAllenamenti;
    }

    public LinearLayout getLayDataOra() {
        return this.layDataOra;
    }

    public void setLayDataOra(LinearLayout layDataOra) {
        this.layDataOra = layDataOra;
    }

    public List<Integer> getIdCategorie() {
        return this.idCategorie;
    }

    public void setIdCategorie(List<Integer> idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Context getContext() {
        return this.context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<String> getCategorie() {
        return this.Categorie;
    }

    public void setCategorie(List<String> categorie) {
        this.Categorie = categorie;
    }

    public Spinner getSpnCategorie() {
        return this.spnCategorie;
    }

    public void setSpnCategorie(Spinner spnCategorie) {
        this.spnCategorie = spnCategorie;
    }
}