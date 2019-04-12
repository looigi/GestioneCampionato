package looigi.gestionecampionato.dati;

import android.content.Context;
import android.widget.Spinner;

import java.util.List;

public class VariabiliStaticheSettings {
    //-------- Singleton ----------//
    private static VariabiliStaticheSettings instance = null;

    private VariabiliStaticheSettings() {
    }

    public static VariabiliStaticheSettings getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheSettings();
        }

        return instance;
    }

    private List<String> Anni;

    private Spinner spnAnni;
    private Context context;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Spinner getSpnAnni() {
        return spnAnni;
    }

    public void setSpnAnni(Spinner spnAnni) {
        this.spnAnni = spnAnni;
    }

    public List<String> getAnni() {
        return Anni;
    }

    public void setAnni(List<String> anni) {
        Anni = anni;
    }
}
