package looigi.gestionecampionato.dati;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import looigi.gestionecampionato.adapter.AdapterAllenatori;

import java.util.ArrayList;
import java.util.List;

public class VariabiliStaticheAllenatori {
    //-------- Singleton ----------//
    private static VariabiliStaticheAllenatori instance = null;

    private VariabiliStaticheAllenatori() {
    }

    public static VariabiliStaticheAllenatori getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheAllenatori();
        }

        return instance;
    }

    private List<String> Allenatori;
    private List<Integer> idAllenatori;
    private List<String> Categorie;
    private List<Integer> idCategorie;

    public void PulisceTuttiGliArray() {
        Allenatori=new ArrayList<String>();
        idAllenatori=new ArrayList<Integer>();
        Categorie=new ArrayList<String>();
        idCategorie=new ArrayList<Integer>();
    }

    private ListView lstAllenatori;
    private AdapterAllenatori adapterAllenatori;
    private Spinner spnCategorie;
    private Context context;
    private RelativeLayout rlMaschera;
    private EditText edtCognome;
    private EditText edtNome;
    private EditText edtEMail;
    private EditText edttelefono;
    private TextView txtId;
    private ImageView imgAllenatore;
    private Button cmdOk;
    private Button cmdElimina;
    private Button cmdAnnulla;
    public int idCategoriaScelta;
    private ImageView imgScegliFoto;
    private ImageView imgEliminaFoto;
    private ImageView imgRefreshFoto;

    public ImageView getImgRefreshFoto() {
        return imgRefreshFoto;
    }

    public void setImgRefreshFoto(ImageView imgRefreshFoto) {
        this.imgRefreshFoto = imgRefreshFoto;
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

    public ImageView getImgAllenatore() {
        return imgAllenatore;
    }

    public void setImgAllenatore(ImageView imgAllenatore) {
        this.imgAllenatore = imgAllenatore;
    }

    public RelativeLayout getRlMaschera() {
        return rlMaschera;
    }

    public void setRlMaschera(RelativeLayout rlMaschera) {
        this.rlMaschera = rlMaschera;
    }

    public List<Integer> getIdAllenatori() {
        return idAllenatori;
    }

    public void setIdAllenatori(List<Integer> idAllenatori) {
        this.idAllenatori = idAllenatori;
    }

    public List<Integer> getIdCategorie() {
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

    public List<String> getCategorie() {
        return Categorie;
    }

    public void setCategorie(List<String> categorie) {
        Categorie = categorie;
    }

    public Spinner getSpnCategorie() {
        return spnCategorie;
    }

    public void setSpnCategorie(Spinner spnCategorie) {
        this.spnCategorie = spnCategorie;
    }

    public ListView getLstAllenatori() {
        return lstAllenatori;
    }

    public void setLstAllenatori(ListView lstAllenatori) {
        this.lstAllenatori = lstAllenatori;
    }

    public AdapterAllenatori getAdapterAllenatori() {
        return adapterAllenatori;
    }

    public void setAdapterAllenatori(AdapterAllenatori adapterAllenatori) {
        this.adapterAllenatori = adapterAllenatori;
    }

    public List<String> getAllenatori() {
        return Allenatori;
    }

    public void setAllenatori(List<String> allenatori) {
        Allenatori = allenatori;
    }
}
