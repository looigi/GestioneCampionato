package looigi.gestionecampionato.dati;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import looigi.gestionecampionato.adapter.AdapterAvversari;

import java.util.ArrayList;
import java.util.List;

public class VariabiliStaticheAvversari {
    //-------- Singleton ----------//
    private static VariabiliStaticheAvversari instance = null;

    private VariabiliStaticheAvversari() {
    }

    public static VariabiliStaticheAvversari getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheAvversari();
        }

        return instance;
    }

    private List<String> Avversari;

    public void PulisceTuttiGliArray() {
        Avversari = new ArrayList<>();
    }

    private AdapterAvversari adapterAvversari;
    private ListView lstAvversari;
    private Context context;
    private RelativeLayout rlMaschera;
    private RelativeLayout rlMascheraStat;
    private EditText edtAvversario;
    private EditText edtCampo;
    private EditText edtIndirizzo;
    private ImageView imgAvversario;
    private ImageView imgAvversarioStat;
    private Button cmdOk;
    private Button cmdElimina;
    private Button cmdAnnulla;
    private Button cmdtrovaCoord;
    private EditText edtRicerca;
    private ImageView imgScegliFoto;
    private ImageView imgEliminaFoto;
    private TextView txtId;
    private TextView txtCoord;

    private TextView txtStat1;
    private TextView txtStat1Anno;
    private TextView txtStat2;
    private TextView txtStat2Anno;
    private TextView txtStat3;
    private TextView txtStat3Anno;
    private TextView txtStat4;
    private TextView txtStat4Anno;
    private TextView txtStat5;
    private TextView txtStat5Anno;
    private TextView txtStat6;
    private TextView txtStat6Anno;
    private TextView txtStat7;
    private TextView txtStat7Anno;
    private TextView txtStat8;
    private TextView txtStat8Anno;
    private TextView txtStat9;
    private TextView txtStat9Anno;
    private TextView txtStat10;
    private TextView txtStat10Anno;
    private TextView txtStat11;
    private TextView txtStat11Anno;
    private TextView txtStat12;
    private TextView txtStat12Anno;
    private TextView txtStat13;
    private TextView txtStat13Anno;
    private Button cmdStatAvversario;

    public Button getCmdStatAvversario() {
        return cmdStatAvversario;
    }

    public void setCmdStatAvversario(Button cmdStatAvversario) {
        this.cmdStatAvversario = cmdStatAvversario;
    }

    public ImageView getImgAvversarioStat() {
        return imgAvversarioStat;
    }

    public void setImgAvversarioStat(ImageView imgAvversarioStat) {
        this.imgAvversarioStat = imgAvversarioStat;
    }

    public TextView getTxtStat1() {
        return txtStat1;
    }

    public void setTxtStat1(TextView txtStat1) {
        this.txtStat1 = txtStat1;
    }

    public TextView getTxtStat1Anno() {
        return txtStat1Anno;
    }

    public void setTxtStat1Anno(TextView txtStat1Anno) {
        this.txtStat1Anno = txtStat1Anno;
    }

    public TextView getTxtStat2() {
        return txtStat2;
    }

    public void setTxtStat2(TextView txtStat2) {
        this.txtStat2 = txtStat2;
    }

    public TextView getTxtStat2Anno() {
        return txtStat2Anno;
    }

    public void setTxtStat2Anno(TextView txtStat2Anno) {
        this.txtStat2Anno = txtStat2Anno;
    }

    public TextView getTxtStat3() {
        return txtStat3;
    }

    public void setTxtStat3(TextView txtStat3) {
        this.txtStat3 = txtStat3;
    }

    public TextView getTxtStat3Anno() {
        return txtStat3Anno;
    }

    public void setTxtStat3Anno(TextView txtStat3Anno) {
        this.txtStat3Anno = txtStat3Anno;
    }

    public TextView getTxtStat4() {
        return txtStat4;
    }

    public void setTxtStat4(TextView txtStat4) {
        this.txtStat4 = txtStat4;
    }

    public TextView getTxtStat4Anno() {
        return txtStat4Anno;
    }

    public void setTxtStat4Anno(TextView txtStat4Anno) {
        this.txtStat4Anno = txtStat4Anno;
    }

    public TextView getTxtStat5() {
        return txtStat5;
    }

    public void setTxtStat5(TextView txtStat5) {
        this.txtStat5 = txtStat5;
    }

    public TextView getTxtStat5Anno() {
        return txtStat5Anno;
    }

    public void setTxtStat5Anno(TextView txtStat5Anno) {
        this.txtStat5Anno = txtStat5Anno;
    }

    public TextView getTxtStat6() {
        return txtStat6;
    }

    public void setTxtStat6(TextView txtStat6) {
        this.txtStat6 = txtStat6;
    }

    public TextView getTxtStat6Anno() {
        return txtStat6Anno;
    }

    public void setTxtStat6Anno(TextView txtStat6Anno) {
        this.txtStat6Anno = txtStat6Anno;
    }

    public TextView getTxtStat7() {
        return txtStat7;
    }

    public void setTxtStat7(TextView txtStat7) {
        this.txtStat7 = txtStat7;
    }

    public TextView getTxtStat7Anno() {
        return txtStat7Anno;
    }

    public void setTxtStat7Anno(TextView txtStat7Anno) {
        this.txtStat7Anno = txtStat7Anno;
    }

    public TextView getTxtStat8() {
        return txtStat8;
    }

    public void setTxtStat8(TextView txtStat8) {
        this.txtStat8 = txtStat8;
    }

    public TextView getTxtStat8Anno() {
        return txtStat8Anno;
    }

    public void setTxtStat8Anno(TextView txtStat8Anno) {
        this.txtStat8Anno = txtStat8Anno;
    }

    public TextView getTxtStat9() {
        return txtStat9;
    }

    public void setTxtStat9(TextView txtStat9) {
        this.txtStat9 = txtStat9;
    }

    public TextView getTxtStat9Anno() {
        return txtStat9Anno;
    }

    public void setTxtStat9Anno(TextView txtStat9Anno) {
        this.txtStat9Anno = txtStat9Anno;
    }

    public TextView getTxtStat10() {
        return txtStat10;
    }

    public void setTxtStat10(TextView txtStat10) {
        this.txtStat10 = txtStat10;
    }

    public TextView getTxtStat10Anno() {
        return txtStat10Anno;
    }

    public void setTxtStat10Anno(TextView txtStat10Anno) {
        this.txtStat10Anno = txtStat10Anno;
    }

    public TextView getTxtStat11() {
        return txtStat11;
    }

    public void setTxtStat11(TextView txtStat11) {
        this.txtStat11 = txtStat11;
    }

    public TextView getTxtStat11Anno() {
        return txtStat11Anno;
    }

    public void setTxtStat11Anno(TextView txtStat11Anno) {
        this.txtStat11Anno = txtStat11Anno;
    }

    public TextView getTxtStat12() {
        return txtStat12;
    }

    public void setTxtStat12(TextView txtStat12) {
        this.txtStat12 = txtStat12;
    }

    public TextView getTxtStat12Anno() {
        return txtStat12Anno;
    }

    public void setTxtStat12Anno(TextView txtStat12Anno) {
        this.txtStat12Anno = txtStat12Anno;
    }

    public TextView getTxtStat13() {
        return txtStat13;
    }

    public void setTxtStat13(TextView txtStat13) {
        this.txtStat13 = txtStat13;
    }

    public TextView getTxtStat13Anno() {
        return txtStat13Anno;
    }

    public void setTxtStat13Anno(TextView txtStat13Anno) {
        this.txtStat13Anno = txtStat13Anno;
    }

    public RelativeLayout getRlMascheraStat() {
        return rlMascheraStat;
    }

    public void setRlMascheraStat(RelativeLayout rlMascheraStat) {
        this.rlMascheraStat = rlMascheraStat;
    }

    public TextView getTxtCoord() {
        return txtCoord;
    }

    public void setTxtCoord(TextView txtCoord) {
        this.txtCoord = txtCoord;
    }

    public Button getCmdtrovaCoord() {
        return cmdtrovaCoord;
    }

    public void setCmdtrovaCoord(Button cmdtrovaCoord) {
        this.cmdtrovaCoord = cmdtrovaCoord;
    }

    public TextView getTxtId() {
        return txtId;
    }

    public void setTxtId(TextView txtId) {
        this.txtId = txtId;
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

    public EditText getEdtRicerca() {
        return edtRicerca;
    }

    public void setEdtRicerca(EditText edtRicerca) {
        this.edtRicerca = edtRicerca;
    }

    public ListView getLstAvversari() {
        return lstAvversari;
    }

    public void setLstAvversari(ListView lstAvversari) {
        this.lstAvversari = lstAvversari;
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

    public EditText getEdtAvversario() {
        return edtAvversario;
    }

    public void setEdtAvversario(EditText edtAvversario) {
        this.edtAvversario = edtAvversario;
    }

    public EditText getEdtCampo() {
        return edtCampo;
    }

    public void setEdtCampo(EditText edtCampo) {
        this.edtCampo = edtCampo;
    }

    public EditText getEdtIndirizzo() {
        return edtIndirizzo;
    }

    public void setEdtIndirizzo(EditText edtIndirizzo) {
        this.edtIndirizzo = edtIndirizzo;
    }

    public ImageView getImgAvversario() {
        return imgAvversario;
    }

    public void setImgAvversario(ImageView imgAvversario) {
        this.imgAvversario = imgAvversario;
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

    public List<String> getAvversari() {
        return Avversari;
    }

    public void setAvversari(List<String> avversari) {
        Avversari = avversari;
    }

    public AdapterAvversari getAdapterAvversari() {
        return adapterAvversari;
    }

    public void setAdapterAvversari(AdapterAvversari adapterAvversari) {
        this.adapterAvversari = adapterAvversari;
    }
}
