package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.adapter.AdapterInfo;
import looigi.gestionecampionato.adapter.AdapterStatAllenamenti;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheAllenatori;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheStatAllenamenti;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.db_remoto.DBRemotoStatAllenamenti;
import looigi.gestionecampionato.utilities.Utility;

public class StatisticheAllenamenti extends android.support.v4.app.Fragment {
    private Context context;
    private static String TAG = NomiMaschere.getInstance().getStatisticheAllenamenti();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = this.getActivity();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.stat_allenamenti, container, false));
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
            ListView lvStat = view.findViewById(R.id.lstvStatAllenamenti);
            VariabiliStaticheStatAllenamenti.getInstance().setLstStatAllenamenti(lvStat);

            ListView lvInfo = view.findViewById(R.id.lstvInfo);
            VariabiliStaticheStatAllenamenti.getInstance().setLstInfo(lvInfo);

            RelativeLayout layInfo = view.findViewById(R.id.layInfo);
            layInfo.setVisibility(LinearLayout.GONE);
            VariabiliStaticheStatAllenamenti.getInstance().setLayInfo(layInfo);

            Spinner spnCategorie = view.findViewById(R.id.spnCategorie);
            VariabiliStaticheStatAllenamenti.getInstance().setSpnCategorie(spnCategorie);
            ((Button) view.findViewById(R.id.cmdScegliCat)).setOnClickListener(new ClickDaAllenamenti());

            Spinner spnMesi = view.findViewById(R.id.spnMesi);
            VariabiliStaticheStatAllenamenti.getInstance().setSpnMesi(spnMesi);
            RiempieListaMesi(spnMesi);

            Button btnChiude = view.findViewById(R.id.btnChiudiInfo);
            btnChiude.setOnClickListener(new View.OnClickListener() {
                 public void onClick(View v) {
                     VariabiliStaticheStatAllenamenti.getInstance().getLayInfo().setVisibility(LinearLayout.GONE);
                 }
             });

            VariabiliStaticheStatAllenamenti.getInstance().idCategoriaScelta=-1;

            if (VariabiliStaticheStatAllenamenti.getInstance().getCategorie()==null) {
                DBRemotoCategorie dbr = new DBRemotoCategorie();
                dbr.RitornaCategorie(context, TAG);
            } else {
                RiempieListaCategorie();
            }
        }
    }

    private void RiempieListaMesi(Spinner spn) {
        List<String> listaMesi = new ArrayList<>();
        listaMesi.add("Anno in corso");
        listaMesi.add("Settembre");
        listaMesi.add("Ottobre");
        listaMesi.add("Novembre");
        listaMesi.add("Dicembre");
        listaMesi.add("Gennaio");
        listaMesi.add("Febbraio");
        listaMesi.add("Marzo");
        listaMesi.add("Aprile");
        listaMesi.add("Maggio");
        listaMesi.add("Giugno");
        listaMesi.add("Luglio");
        listaMesi.add("Agosto");

        ArrayAdapter<String> adapterMesi = new ArrayAdapter<String>(
                VariabiliStaticheGlobali.getInstance().getContext(),
                R.layout.spinner_item_piccolo,
                listaMesi);
        adapterMesi.setDropDownViewResource(R.layout.spinner_item_piccolo);
        spn.setAdapter(adapterMesi);

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 CaricaValori();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public static void RiempieListaCategorie() {
        if (VariabiliStaticheStatAllenamenti.getInstance().getCategorie() != null) {
            final ArrayAdapter<String> adapterCategorie = new ArrayAdapter<String>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_piccolo,
                    VariabiliStaticheStatAllenamenti.getInstance().getCategorie());
            adapterCategorie.setDropDownViewResource(R.layout.spinner_item_piccolo);
            VariabiliStaticheStatAllenamenti.getInstance().getSpnCategorie().setAdapter(adapterCategorie);

            Utility.getInstance().CercaESettaStringaInSpinner(VariabiliStaticheStatAllenamenti.getInstance().getSpnCategorie(),
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria1());
            VariabiliStaticheStatAllenamenti.getInstance().idCategoriaScelta = Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1());

            int pos = Utility.getInstance().CercaESettaStringaInSpinner(VariabiliStaticheStatAllenamenti.getInstance().getSpnCategorie(),
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria1());
            if (pos>-1) {
                VariabiliStaticheStatAllenamenti.getInstance().getSpnCategorie().setSelection(pos);
            }
            VariabiliStaticheStatAllenamenti.getInstance().idCategoriaScelta=Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1());

            VariabiliStaticheStatAllenamenti.getInstance().getSpnCategorie().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int idCategoria=VariabiliStaticheStatAllenamenti.getInstance().getIdCategorie().get(position);
                    VariabiliStaticheStatAllenamenti.getInstance().idCategoriaScelta=idCategoria;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public static void fillListViewStatAllenamenti() {
        if (VariabiliStaticheStatAllenamenti.getInstance().getStatisticheAllenamenti() != null) {
            VariabiliStaticheStatAllenamenti.getInstance().setAdapterStatAllenamenti(new AdapterStatAllenamenti(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, VariabiliStaticheStatAllenamenti.getInstance().getStatisticheAllenamenti()));
            VariabiliStaticheStatAllenamenti.getInstance().getLstStatAllenamenti().setAdapter(VariabiliStaticheStatAllenamenti.getInstance().getAdapterStatAllenamenti());
        }
    }

    public static void fillListViewInfo() {
        if (VariabiliStaticheStatAllenamenti.getInstance().getInfo() != null) {
            VariabiliStaticheStatAllenamenti.getInstance().setAdapterInfo(new AdapterInfo(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, VariabiliStaticheStatAllenamenti.getInstance().getInfo()));
            VariabiliStaticheStatAllenamenti.getInstance().getLstInfo().setAdapter(VariabiliStaticheStatAllenamenti.getInstance().getAdapterInfo());
            VariabiliStaticheStatAllenamenti.getInstance().getLayInfo().setVisibility(LinearLayout.VISIBLE);
        }
    }

    class ClickDaAllenamenti implements View.OnClickListener {
        ClickDaAllenamenti() {
        }

        public void onClick(View v) {
            CaricaValori();
        }
    }

    private void CaricaValori() {
        String Mese = VariabiliStaticheStatAllenamenti.getInstance().getSpnMesi().getSelectedItem().toString();

        DBRemotoStatAllenamenti dbr = new DBRemotoStatAllenamenti();
        dbr.RitornaStatAllenamentiCategoria(VariabiliStaticheGlobali.getInstance().getContext(),
                Integer.toString(VariabiliStaticheStatAllenamenti.getInstance().idCategoriaScelta),
                Mese,
                TAG
        );
    }
}
