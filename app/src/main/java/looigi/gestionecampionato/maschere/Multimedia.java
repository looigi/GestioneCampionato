package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.adapter.AdapterMultimedia;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMultimedia;
import looigi.gestionecampionato.dati.VariabiliStaticheRose;
import looigi.gestionecampionato.db_remoto.DBRemotoMultimedia;

public class Multimedia extends android.support.v4.app.Fragment {
    private Context context;
    private static String TAG= NomiMaschere.getInstance().getMultimedia();
    public static String MascheraApertaPer;
    private int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = this.getActivity();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.multimedia, container, false));
        } catch (Exception ignored) {

        }

        if (view!=null) {
            VariabiliStaticheGlobali.getInstance().setViewActivity(view);

            initializeGraphic();
        }

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //isVisible=isVisibleToUser;

        //if (isVisible) {
        //    initializeGraphic();
        //}
    }

    @Override
    public void onResume()
    {
        super.onResume();

        //if (isVisible) {
        //    initializeGraphic();
        //}
    }

    private void initializeGraphic() {
        Context context = VariabiliStaticheGlobali.getInstance().getContext();
        View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            MascheraApertaPer=VariabiliStaticheGlobali.MascheraAttuale;

            VariabiliStaticheMultimedia.getInstance().setLstMultimedia((ListView) view.findViewById(R.id.lstMultimedia));

            if (NomiMaschere.getInstance().getRose()!=null && MascheraApertaPer.equals(NomiMaschere.getInstance().getRose())) {
                id = VariabiliStaticheRose.getInstance().idGiocatoreScelto;
            }

            if (NomiMaschere.getInstance().getNuovaPartita()!=null && MascheraApertaPer.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                id = NuovaPartita.PartitaNuova;
                MascheraApertaPer="Partite";
            }

            DBRemotoMultimedia dbm = new DBRemotoMultimedia();
            dbm.RitornaMultimedia(VariabiliStaticheGlobali.getInstance().getContext(),
                    Integer.toString(id),
                    MascheraApertaPer,
                    "Multimedia");
        }
    }

    public static void RiempieListaMultimedia(List<String> lista) {
        VariabiliStaticheMultimedia.getInstance().setAdapterMultimedia(new AdapterMultimedia(VariabiliStaticheGlobali.getInstance().getContext(),
                android.R.layout.simple_list_item_1, lista));
        VariabiliStaticheMultimedia.getInstance().getLstMultimedia().setAdapter(VariabiliStaticheMultimedia.getInstance().getAdapterMultimedia());
    }
}
