package looigi.gestionecampionato.dati;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import looigi.gestionecampionato.adapter.AdapterCategorie;

import java.util.ArrayList;
import java.util.List;

public class VariabiliStaticheCategorie {
    //-------- Singleton ----------//
    private static VariabiliStaticheCategorie instance = null;

    private VariabiliStaticheCategorie() {
    }

    public static VariabiliStaticheCategorie getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheCategorie();
        }

        return instance;
    }

    private List<String> Categorie;
    private List<Integer> idCategorie;

    public void PulisceTuttiGliArray() {
        Categorie=new ArrayList<String>();
        idCategorie=new ArrayList<Integer>();
    }

    private ListView lstCategorie;
    private AdapterCategorie adapterCategorie;
    private Context context;
    private RelativeLayout rlMaschera;
    private EditText edtCategoria;
    private ImageView imgCategoria;
    private Button cmdOk;
    private Button cmdElimina;
    private Button cmdAnnulla;
    public int idCategoriaScelta;
    private ImageView imgScegliFoto;
    private ImageView imgEliminaFoto;
    private TextView txtId;
    private ImageView imgRefreshFoto;

    public ImageView getImgRefreshFoto() {
        return imgRefreshFoto;
    }

    public void setImgRefreshFoto(ImageView imgRefreshFoto) {
        this.imgRefreshFoto = imgRefreshFoto;
    }

    public TextView getTxtId() {
        return txtId;
    }

    public void setTxtId(TextView txtId) {
        this.txtId = txtId;
    }

    public List<Integer> getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(List<Integer> idCategorie) {
        this.idCategorie = idCategorie;
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

    public EditText getEdtCategoria() {
        return edtCategoria;
    }

    public void setEdtCategoria(EditText edtCategoria) {
        this.edtCategoria = edtCategoria;
    }

    public ImageView getImgCategoria() {
        return imgCategoria;
    }

    public void setImgCategoria(ImageView imgCategoria) {
        this.imgCategoria = imgCategoria;
    }

    public RelativeLayout getRlMaschera() {
        return rlMaschera;
    }

    public void setRlMaschera(RelativeLayout rlMaschera) {
        this.rlMaschera = rlMaschera;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ListView getLstCategorie() {
        return lstCategorie;
    }

    public void setLstCategorie(ListView lstCategorie) {
        this.lstCategorie = lstCategorie;
    }

    public AdapterCategorie getAdapterCategorie() {
        return adapterCategorie;
    }

    public void setAdapterCategorie(AdapterCategorie adapterCategorie) {
        this.adapterCategorie = adapterCategorie;
    }

    public List<String> getCategorie() {
        return Categorie;
    }

    public void setCategorie(List<String> Categorie) {
        this.Categorie = Categorie;
    }
}
