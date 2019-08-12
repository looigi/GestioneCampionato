package looigi.gestionecampionato.dati;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class VariabiliStaticheNuovoAnno {
    //-------- Singleton ----------//
    private static VariabiliStaticheNuovoAnno instance = null;

    private VariabiliStaticheNuovoAnno() {
    }

    public static VariabiliStaticheNuovoAnno getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheNuovoAnno();
        }

        return instance;
    }

    private Context context;
    private EditText edtAnno;
    private EditText edtNomeSquadra;
    private TextView txtAnno;
    private CheckBox chkCopia;

    public Context getContext() {
        return context;
    }

    public CheckBox getChkCopia() {
        return chkCopia;
    }

    public void setChkCopia(CheckBox chkCopia) {
        this.chkCopia = chkCopia;
    }

    public EditText getEdtNomeSquadra() {
        return edtNomeSquadra;
    }

    public void setEdtNomeSquadra(EditText edtNomeSquadra) {
        this.edtNomeSquadra = edtNomeSquadra;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public EditText getEdtAnno() {
        return edtAnno;
    }

    public void setEdtAnno(EditText edtAnno) {
        this.edtAnno = edtAnno;
    }

    public TextView getTxtAnno() {
        return txtAnno;
    }

    public void setTxtAnno(TextView txtAnno) {
        this.txtAnno = txtAnno;
    }
}
