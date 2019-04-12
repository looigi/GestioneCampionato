package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovoAnno;
import looigi.gestionecampionato.db_remoto.DBRemotoGenerale;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.dialog.DialogNuovoAnno;

public class NuovoAnno extends android.support.v4.app.Fragment {
    private Context context;
    private static String TAG = NomiMaschere.getInstance().getHome();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = this.getActivity();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.nuovo_anno, container, false));
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
        final Context context = VariabiliStaticheGlobali.getInstance().getContext();
        View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            VariabiliStaticheNuovoAnno.getInstance().setContext(context);

            VariabiliStaticheNuovoAnno.getInstance().setTxtAnno((TextView) view.findViewById(R.id.txtAnno));
            VariabiliStaticheNuovoAnno.getInstance().setEdtAnno((EditText) view.findViewById(R.id.edtDescAnno));
            VariabiliStaticheNuovoAnno.getInstance().setEdtNomeSquadra((EditText) view.findViewById(R.id.edtNomeSquadra));

            final String idAnno = VariabiliStaticheNuovoAnno.getInstance().getTxtAnno().getText().toString().trim();

            Button btnOk = view.findViewById(R.id.cmdOkNuovoAnno);
            btnOk.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (VariabiliStaticheNuovoAnno.getInstance().getEdtAnno().getText().toString().trim().isEmpty()) {
                        DialogMessaggio.getInstance().show(context,
                                "Inserire una descrizione per l'anno", true, VariabiliStaticheGlobali.NomeApplicazione);
                    } else {
                        if (VariabiliStaticheNuovoAnno.getInstance().getEdtNomeSquadra().getText().toString().trim().isEmpty()) {
                            DialogMessaggio.getInstance().show(context,
                                    "Inserire un nome squadra", true, VariabiliStaticheGlobali.NomeApplicazione);
                        } else {
                            String descAnno = VariabiliStaticheNuovoAnno.getInstance().getEdtAnno().getText().toString().trim();

                            DialogNuovoAnno.getInstance().show(context,
                                    "Si vuole creare il nuovo anno\n\n" + idAnno + ": " + descAnno);
                        }
                    }
                }
            });
        }

        DBRemotoGenerale dbr = new DBRemotoGenerale();
        dbr.RitornaMaxAnno(context);
    }
}
