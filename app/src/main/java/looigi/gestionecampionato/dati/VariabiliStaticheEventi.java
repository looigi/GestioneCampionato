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

import looigi.gestionecampionato.adapter.AdapterEventi;
import looigi.gestionecampionato.adapter.AdapterEventiModifica;

public class VariabiliStaticheEventi {
    //-------- Singleton ----------//
    private static VariabiliStaticheEventi instance = null;

    private VariabiliStaticheEventi() {
    }

    public static VariabiliStaticheEventi getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheEventi();
        }

        return instance;
    }

    private List<String> Eventi;
    private ListView lstEventi;
    private AdapterEventiModifica adapterEventi;
    private Context context;
    private RelativeLayout layMascheraModEventi;
    private EditText edtEvento;
    private Button cmdOk;
    private Button cmdElimina;
    private Button cmdAnnulla;

    public EditText getEdtEvento() {
        return edtEvento;
    }

    public void setEdtEvento(EditText edtEvento) {
        this.edtEvento = edtEvento;
    }

    public Button getCmdOk() {
        return cmdOk;
    }

    public void setCmdOk(Button cmdOk) {
        this.cmdOk = cmdOk;
    }

    public Button getCmdElimina() {
        return cmdElimina;
    }

    public void setCmdElimina(Button cmdElimina) {
        this.cmdElimina = cmdElimina;
    }

    public Button getCmdAnnulla() {
        return cmdAnnulla;
    }

    public void setCmdAnnulla(Button cmdAnnulla) {
        this.cmdAnnulla = cmdAnnulla;
    }

    public RelativeLayout getLayMascheraModEventi() {
        return layMascheraModEventi;
    }

    public void setLayMascheraModEventi(RelativeLayout layMascheraModEventi) {
        this.layMascheraModEventi = layMascheraModEventi;
    }

    public List<String> getEventi() {
        return Eventi;
    }

    public void setEventi(List<String> eventi) {
        Eventi = eventi;
    }

    public ListView getLstEventi() {
        return lstEventi;
    }

    public void setLstEventi(ListView lstEventi) {
        this.lstEventi = lstEventi;
    }

    public AdapterEventiModifica getAdapterEventi() {
        return adapterEventi;
    }

    public void setAdapterEventi(AdapterEventiModifica adapterEventi) {
        this.adapterEventi = adapterEventi;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
