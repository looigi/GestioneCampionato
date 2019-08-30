package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.adapter.AdapterNessunaPartita;
import looigi.gestionecampionato.adapter.AdapterPartite;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheHome;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.dati.VariabiliStaticheUtenti;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.db_remoto.DBRemotoPartite;
import looigi.gestionecampionato.utilities.Utility;

public class Home extends android.support.v4.app.Fragment {
    private Context context;
    private static String TAG = NomiMaschere.getInstance().getHome();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = this.getActivity();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.home, container, false));
        } catch (Exception ignored) {
            int e=0;
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
        final Context context = VariabiliStaticheGlobali.getInstance().getContext();
        View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            VariabiliStaticheMain.getInstance().setLstPartite((ListView) view.findViewById(R.id.lstvPartite));

            VariabiliStaticheHome.getInstance().setSpnCategorie((Spinner) view.findViewById(R.id.spnCategorie));
            // Utility.getInstance().SettaColoreSceltaCategoria(view);

            // if (VariabiliStaticheHome.getInstance().getCategorie()==null) {
                DBRemotoCategorie dbr = new DBRemotoCategorie();
                dbr.RitornaCategorie(context, TAG);
            /* } else {
                if (VariabiliStaticheUtenti.getInstance().getIdCategoriaScelta()==null) {
                    VariabiliStaticheUtenti.getInstance().setIdCategoriaScelta(Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1()));
                }
                if (VariabiliStaticheMain.getInstance().getPartite()==null) {
                    DBRemotoPartite dbrp = new DBRemotoPartite();
                    dbrp.RitornaPartite(VariabiliStaticheGlobali.getInstance().getContext(), TAG);
                } else {
                    Home.RiempieListaPartite();
                }
            } */

            Button cmdScegliCat = view.findViewById(R.id.cmdScegliCat);
            cmdScegliCat.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (VariabiliStaticheHome.getInstance().idCategoriaScelta>-1) {
                        DBRemotoPartite dbrp = new DBRemotoPartite();
                        dbrp.RitornaPartite(context, TAG);
                    }
                }
            });
        }
    }

    public static void RiempieListaCategorie() {
        if (VariabiliStaticheHome.getInstance().getCategorie() != null) {
            final ArrayAdapter<String> adapterCategorie = new ArrayAdapter<String>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_piccolo,
                    VariabiliStaticheHome.getInstance().getCategorie());
            adapterCategorie.setDropDownViewResource(R.layout.spinner_item_piccolo);
            VariabiliStaticheHome.getInstance().getSpnCategorie().setAdapter(adapterCategorie);

            int pos = Utility.getInstance().CercaESettaStringaInSpinner(VariabiliStaticheHome.getInstance().getSpnCategorie(),
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria1());
            if (pos>-1) {
                VariabiliStaticheHome.getInstance().getSpnCategorie().setSelection(pos);
            }

            VariabiliStaticheHome.getInstance().idCategoriaScelta = Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1());

            VariabiliStaticheHome.getInstance().getSpnCategorie().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int idCategoria=VariabiliStaticheHome.getInstance().getIdCategorie().get(position);
                    VariabiliStaticheHome.getInstance().idCategoriaScelta=idCategoria;

                    VariabiliStaticheUtenti.getInstance().setIdCategoriaScelta(VariabiliStaticheHome.getInstance().idCategoriaScelta);
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().setDescCategoria(VariabiliStaticheHome.getInstance().getCategorie().get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

        if (VariabiliStaticheUtenti.getInstance().getIdCategoriaScelta()==null) {
            VariabiliStaticheUtenti.getInstance().setIdCategoriaScelta(Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1()));
        }
        if (VariabiliStaticheMain.getInstance().getPartite()==null) {
            DBRemotoPartite dbrp = new DBRemotoPartite();
            dbrp.RitornaPartite(VariabiliStaticheGlobali.getInstance().getContext(), TAG);
        } else {
            Home.RiempieListaPartite();
        }
    }

    public static void RiempieListaPartite() {
        if (VariabiliStaticheMain.getInstance().getLstPartite()!=null && VariabiliStaticheMain.getInstance().getPartite().size()>0) {
            VariabiliStaticheMain.getInstance().setAdapterPartite(new AdapterPartite(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, VariabiliStaticheMain.getInstance().getPartite()));
            VariabiliStaticheMain.getInstance().getLstPartite().setAdapter(VariabiliStaticheMain.getInstance().getAdapterPartite());
        } else {
            List<String> l = new ArrayList<>();
            l.add("*");
            AdapterNessunaPartita adp= new AdapterNessunaPartita(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, l);
            VariabiliStaticheMain.getInstance().getLstPartite().setAdapter(adp);
        }
    }
}
