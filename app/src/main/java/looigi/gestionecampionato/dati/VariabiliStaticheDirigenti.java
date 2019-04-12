package looigi.gestionecampionato.dati;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import looigi.gestionecampionato.adapter.AdapterDirigenti;

public class VariabiliStaticheDirigenti {
    //-------- Singleton ----------//
    private static VariabiliStaticheDirigenti instance = null;

    private VariabiliStaticheDirigenti() {
    }

    public static VariabiliStaticheDirigenti getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheDirigenti();
        }

        return instance;
    }

    private List<String> Dirigenti;
    private List<Integer> idDirigenti;
    private List<String> Categorie;
    private List<Integer> idCategorie;
    private ListView lstDirigenti;
    private AdapterDirigenti adapterDirigenti;
    private Spinner spnCategorie;
    private Context context;
    private RelativeLayout rlMaschera;
    private EditText edtCognome;
    private EditText edtNome;
    private EditText edtEMail;
    private EditText edttelefono;
    private TextView txtId;
    private ImageView imgDirigente;
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

    public ImageView getImgDirigente() {
        return imgDirigente;
    }

    public void setImgDirigente(ImageView imgDirigente) {
        this.imgDirigente = imgDirigente;
    }

    public RelativeLayout getRlMaschera() {
        return rlMaschera;
    }

    public void setRlMaschera(RelativeLayout rlMaschera) {
        this.rlMaschera = rlMaschera;
    }

    public List<Integer> getIdDirigenti() {
        return idDirigenti;
    }

    public void setIdDirigenti(List<Integer> idDirigenti) {
        this.idDirigenti = idDirigenti;
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

    public ListView getLstDirigenti() {
        return lstDirigenti;
    }

    public void setLstDirigenti(ListView lstDirigenti) {
        this.lstDirigenti = lstDirigenti;
    }

    public AdapterDirigenti getAdapterDirigenti() {
        return adapterDirigenti;
    }

    public void setAdapterDirigenti(AdapterDirigenti adapterDirigenti) {
        this.adapterDirigenti = adapterDirigenti;
    }

    public List<String> getDirigenti() {
        return Dirigenti;
    }

    public void setDirigenti(List<String> Dirigenti) {
        this.Dirigenti = Dirigenti;
    }
}
