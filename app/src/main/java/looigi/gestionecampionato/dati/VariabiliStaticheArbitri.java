package looigi.gestionecampionato.dati;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import looigi.gestionecampionato.adapter.AdapterArbitri;

public class VariabiliStaticheArbitri {
    //-------- Singleton ----------//
    private static VariabiliStaticheArbitri instance = null;

    private VariabiliStaticheArbitri() {
    }

    public static VariabiliStaticheArbitri getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheArbitri();
        }

        return instance;
    }

    private List<String> Arbitri;
    private List<Integer> idArbitri;
    private Context context;
    private EditText edtCognome;
    private EditText edtNome;
    private EditText edtMail;
    private EditText edtTelefono;
    private TextView txtId;
    private ImageView imgArbitro;
    private Button cmdOk;
    private Button cmdElimina;
    private Button cmdAnnulla;
    public int idArbitroScelto;
    private ImageView imgScegliFoto;
    private ImageView imgEliminaFoto;
    private ImageView imgRefreshFoto;
    private ListView lstvArbitri;
    private RelativeLayout rlMaschera;
    private AdapterArbitri adapterArbitri;

    public AdapterArbitri getAdapterArbitri() {
        return adapterArbitri;
    }

    public void setAdapterArbitri(AdapterArbitri adapterArbitri) {
        this.adapterArbitri = adapterArbitri;
    }

    public RelativeLayout getRlMaschera() {
        return rlMaschera;
    }

    public void setRlMaschera(RelativeLayout rlMaschera) {
        this.rlMaschera = rlMaschera;
    }

    public ListView getLstvArbitri() {
        return lstvArbitri;
    }

    public void setLstvArbitri(ListView lstvArbitri) {
        this.lstvArbitri = lstvArbitri;
    }

    public EditText getEdtMail() {
        return edtMail;
    }

    public void setEdtMail(EditText edtMail) {
        this.edtMail = edtMail;
    }

    public EditText getEdtTelefono() {
        return edtTelefono;
    }

    public void setEdtTelefono(EditText edtTelefono) {
        this.edtTelefono = edtTelefono;
    }

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

    public ImageView getImgArbitro() {
        return imgArbitro;
    }

    public void setImgArbitro(ImageView imgArbitro) {
        this.imgArbitro = imgArbitro;
    }

    public List<Integer> getIdArbitri() {
        return idArbitri;
    }

    public void setIdArbitri(List<Integer> idArbitri) {
        this.idArbitri = idArbitri;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<String> getArbitri() {
        return Arbitri;
    }

    public void setArbitri(List<String> Arbitri) {
        this.Arbitri = Arbitri;
    }
}
