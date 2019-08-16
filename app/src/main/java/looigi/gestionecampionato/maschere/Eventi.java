package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.adapter.AdapterEventiModifica;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheEventi;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.db_remoto.DBRemotoEventi;
import looigi.gestionecampionato.dialog.DialogMessaggio;

public class Eventi extends android.support.v4.app.Fragment {
    private static String TAG = NomiMaschere.getInstance().getEventi();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        VariabiliStaticheEventi.getInstance().setContext(this.getActivity());

        View view=null;

        try {
            view=(inflater.inflate(R.layout.eventi, container, false));
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
            RelativeLayout rlMaschera = view.findViewById(R.id.layMascheraModEventi);
            Button btnOkEventi = view.findViewById(R.id.cmdOkEventi);
            Button btnAnnullaEventi = view.findViewById(R.id.cmdAnnullaEventi);
            Button btnEliminaEventi = view.findViewById(R.id.cmdEliminaEventi);
            EditText edtEvento = view.findViewById(R.id.edtEvento);
            ListView lstEventi = view.findViewById(R.id.lstvEventi);

            VariabiliStaticheEventi.getInstance().setLayMascheraModEventi((RelativeLayout) rlMaschera);
            VariabiliStaticheEventi.getInstance().setCmdOk((Button) btnOkEventi);
            VariabiliStaticheEventi.getInstance().setCmdAnnulla((Button) btnAnnullaEventi);
            VariabiliStaticheEventi.getInstance().setCmdElimina((Button) btnEliminaEventi);
            VariabiliStaticheEventi.getInstance().setEdtEvento((EditText) edtEvento);
            VariabiliStaticheEventi.getInstance().setLstEventi((ListView) lstEventi);

            VariabiliStaticheEventi.getInstance().getLayMascheraModEventi().setVisibility(LinearLayout.GONE);

            DBRemotoEventi dbr = new DBRemotoEventi();
            dbr.RitornaEventi(context, NomiMaschere.getInstance().getEventiPerTitolo());
        }
    }

    public static void NuovoEvento() {
        String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            VariabiliStaticheEventi.getInstance().getEdtEvento().setText("");

            VariabiliStaticheEventi.getInstance().getLayMascheraModEventi().setVisibility(RelativeLayout.VISIBLE);

            VariabiliStaticheEventi.getInstance().getCmdOk().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String Evento=VariabiliStaticheEventi.getInstance().getEdtEvento().getText().toString();

                    if (Evento.isEmpty()) {
                        DialogMessaggio.getInstance().show(VariabiliStaticheEventi.getInstance().getContext(), "Inserire il cognome",
                                true, VariabiliStaticheGlobali.NomeApplicazione);
                    } else {
                        DBRemotoEventi dbr = new DBRemotoEventi();
                        dbr.SalvaEvento(VariabiliStaticheEventi.getInstance().getContext(),
                                "-1", Evento, NomiMaschere.getInstance().getEventi());

                        VariabiliStaticheEventi.getInstance().getLayMascheraModEventi().setVisibility(RelativeLayout.GONE);
                    }
                }
            });

            VariabiliStaticheEventi.getInstance().getCmdAnnulla().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    VariabiliStaticheEventi.getInstance().getLayMascheraModEventi().setVisibility(RelativeLayout.GONE);
                }
            });
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContextPrincipale(),
                    "Non si hanno i permessi per inserire eventi", true, VariabiliStaticheGlobali.NomeApplicazione);
        }
    }

    public static void fillListViewEventi() {
        if (VariabiliStaticheEventi.getInstance().getEventi() != null) {
            VariabiliStaticheEventi.getInstance().setAdapterEventi(new AdapterEventiModifica(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, VariabiliStaticheEventi.getInstance().getEventi()));
            if (VariabiliStaticheEventi.getInstance().getLstEventi()!=null) {
                VariabiliStaticheEventi.getInstance().getLstEventi().setAdapter(VariabiliStaticheEventi.getInstance().getAdapterEventi());
            }
        }
    }
}
