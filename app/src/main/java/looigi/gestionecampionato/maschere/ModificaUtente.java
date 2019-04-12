package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.adapter.AdapterModificaUtenti;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheUtenti;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.db_remoto.DBRemotoGenerale;
import looigi.gestionecampionato.db_remoto.DBRemotoUtenti;
import looigi.gestionecampionato.utilities.Utility;

public class ModificaUtente extends android.support.v4.app.Fragment {
    private Context context;
    private static String TAG = NomiMaschere.getInstance().getModificaUtenti();
    private static VariabiliStaticheUtenti v=VariabiliStaticheUtenti.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = this.getActivity();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.modifica_utenti, container, false));
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
        VariabiliStaticheUtenti.getInstance().setContext(context);

        if (view != null) {
            v.setRlMaschera((RelativeLayout) view.findViewById(R.id.layMascheraModUtenti));
            v.getRlMaschera().setVisibility(RelativeLayout.GONE);

            v.setEdtUtente((EditText) view.findViewById(R.id.edtUtente));
            v.setEdtPassword((EditText) view.findViewById(R.id.edtPassword));
            v.setEdtCognome((EditText) view.findViewById(R.id.edtCognome));
            v.setEdtNome((EditText) view.findViewById(R.id.edtNome));
            v.setEdtMail((EditText) view.findViewById(R.id.edtEMail));
            v.setEdtCategoria((EditText) view.findViewById(R.id.edtCategoria));
            v.setOptAdmin((RadioButton) view.findViewById(R.id.optAdmin));
            v.setOptUser((RadioButton) view.findViewById(R.id.optUser));
            v.setCmdSalva((Button) view.findViewById(R.id.cmdOkUtente));
            v.setCmdAnnulla((Button) view.findViewById(R.id.cmdAnnullaUtente));
            v.setSpnCategorie((Spinner) view.findViewById(R.id.spnCategorie));
            v.setSpnNomeSquadra((Spinner) view.findViewById(R.id.spnNomeSquadra));

            v.setLstUtenti((ListView) view.findViewById(R.id.lstvUtenti));

            if (VariabiliStaticheUtenti.getInstance().getUtenti()==null) {
                DBRemotoUtenti dbr = new DBRemotoUtenti();
                dbr.RitornaListaUtenti(context);
            } else {
                RiempieListaUtenti();
            }
        }
    }

    public static void RiempieListaSquadre() {
        if (v.getNomiSquadre() != null) {
            VariabiliStaticheGlobali.getInstance().vPerPassaggio=null;

            final ArrayAdapter<String> adapterSquadre = new ArrayAdapter<String>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item, v.getNomiSquadre());
            adapterSquadre.setDropDownViewResource(R.layout.spinner_item);
            v.getSpnNomeSquadra().setAdapter(adapterSquadre);

            v.getSpnNomeSquadra().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    VariabiliStaticheUtenti.NomeSquadraScelta=v.getNomiSquadre().get(position);
                    VariabiliStaticheUtenti.idAnnoScelto=v.getIdAnno().get(position);

                    int appo=VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
                    VariabiliStaticheGlobali.getInstance().setAnnoInCorso(VariabiliStaticheUtenti.idAnnoScelto);
                    DBRemotoCategorie dbr = new DBRemotoCategorie();
                    dbr.RitornaCategorie(VariabiliStaticheGlobali.getInstance().getContext(), TAG);
                    VariabiliStaticheGlobali.getInstance().setAnnoInCorso(appo);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public static void RiempieListaCategorie() {
        if (v.getNomiCategorie() != null) {
            VariabiliStaticheGlobali.getInstance().vPerPassaggio=null;

            final ArrayAdapter<String> adapterCategorie = new ArrayAdapter<String>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item, v.getNomiCategorie());
            adapterCategorie.setDropDownViewResource(R.layout.spinner_item);
            v.getSpnCategorie().setAdapter(adapterCategorie);

            int pos = Utility.getInstance().CercaESettaStringaInSpinner(v.getSpnCategorie(),
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria1());
            if (pos>-1) {
                v.getSpnCategorie().setSelection(pos);
            }
            v.setIdCategoriaScelta(Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1()));

            v.getSpnCategorie().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int idCategoria=v.getIdNomiCategorie().get(position);
                    VariabiliStaticheUtenti.getInstance().setIdCategoriaScelta(idCategoria);

                    VariabiliStaticheUtenti.getInstance().setIdCategoriaScelta(idCategoria);
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().setDescCategoria(v.getNomiCategorie().get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public static void RiempieListaUtenti() {
        v.setAdapterModificaUtenti(new AdapterModificaUtenti(VariabiliStaticheGlobali.getInstance().getContext(),
                android.R.layout.simple_list_item_1, v.getUtenti()));
        v.getLstUtenti().setAdapter(v.getAdapterModificaUtenti());

        if (VariabiliStaticheUtenti.getInstance().getNomiSquadre()==null) {
            DBRemotoGenerale dbr = new DBRemotoGenerale();
            dbr.RitornaValoriPerRegistrazione(VariabiliStaticheUtenti.getInstance().getContext(), TAG);
        } else {
            RiempieListaSquadre();
        }
    }
}
