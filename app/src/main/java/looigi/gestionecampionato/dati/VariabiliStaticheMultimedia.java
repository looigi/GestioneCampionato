package looigi.gestionecampionato.dati;

import android.widget.ListView;

import looigi.gestionecampionato.adapter.AdapterMultimedia;

public class VariabiliStaticheMultimedia {
    //-------- Singleton ----------//
    private static VariabiliStaticheMultimedia instance = null;

    private VariabiliStaticheMultimedia() {
    }

    public static VariabiliStaticheMultimedia getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheMultimedia();
        }

        return instance;
    }

    private ListView lstMultimedia;
    private AdapterMultimedia adapterMultimedia;

    public AdapterMultimedia getAdapterMultimedia() {
        return adapterMultimedia;
    }

    public void setAdapterMultimedia(AdapterMultimedia adapterMultimedia) {
        this.adapterMultimedia = adapterMultimedia;
    }

    public ListView getLstMultimedia() {
        return lstMultimedia;
    }

    public void setLstMultimedia(ListView lstMultimedia) {
        this.lstMultimedia = lstMultimedia;
    }
}
