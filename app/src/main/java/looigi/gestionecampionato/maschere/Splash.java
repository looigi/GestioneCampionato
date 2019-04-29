package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.db_locale.DBLocaleUtenti;
import looigi.gestionecampionato.db_remoto.ControlloVersioneApplicazione;
import looigi.gestionecampionato.db_remoto.DBRemotoGenerale;
import looigi.gestionecampionato.db_remoto.DBRemotoUtenti;
import looigi.gestionecampionato.utilities.Utility;

public class Splash extends android.support.v4.app.Fragment {
    private Context context;
    private static String TAG= NomiMaschere.getInstance().getSplash();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = this.getActivity();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.splash, container, false));
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

    private Runnable runRiga;
    private Handler hSelezionaRiga;
    private boolean TastoPremuto=false;

    private void initializeGraphic() {
        final View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            // ControlloVersioneApplicazione c = new ControlloVersioneApplicazione();
            // c.ControllaVersione();

            DBLocaleUtenti dbl = new DBLocaleUtenti();
            dbl.CreaDB(VariabiliStaticheGlobali.getInstance().getContext());
            final boolean CeUtente=dbl.RitornaUtenteSalvato(VariabiliStaticheGlobali.getInstance().getContext());
            VariabiliStaticheMain.getInstance().setLstPartite((ListView) view.findViewById(R.id.lstvPartite));

            int quantiSecondi;
            if (CeUtente) {
                quantiSecondi = 5000;
            } else {
                quantiSecondi = 100;
            }

            DBRemotoGenerale dbr = new DBRemotoGenerale();
            dbr.RitornaAnnoAttualeUtenti(context);

            VariabiliStaticheMain.getInstance().setImgSplash((ImageView) view.findViewById(R.id.imgSplash));
            VariabiliStaticheMain.getInstance().getImgSplash().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    VariabiliStaticheGlobali.getInstance().getContextPrincipale().getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                    TastoPremuto=true;
                    VariabiliStaticheMain.getInstance().getAppBar().setVisibility(LinearLayout.VISIBLE);

                    if (CeUtente) {
                        DBRemotoUtenti dbr = new DBRemotoUtenti();
                        dbr.RitornaUtenteDaId(VariabiliStaticheGlobali.getInstance().getContextPrincipale(),
                                VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdUtente(), TAG);
                    } else {
                        Utility.getInstance().CambiaMaschera(R.id.utenti, -1, -1);
                    }
                }
            });

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga=new Runnable() {
                @Override
                public void run() {
                    if (!TastoPremuto) {
                        VariabiliStaticheGlobali.getInstance().getContextPrincipale().getWindow().getDecorView().setBackgroundColor(Color.WHITE);
                        VariabiliStaticheMain.getInstance().getAppBar().setVisibility(LinearLayout.VISIBLE);
                        if (CeUtente) {
                            DBRemotoUtenti dbr = new DBRemotoUtenti();
                            dbr.RitornaUtenteDaId(VariabiliStaticheGlobali.getInstance().getContextPrincipale(),
                                    VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdUtente(), TAG);
                        } else {
                            Utility.getInstance().CambiaMaschera(R.id.utenti, -1, -1);
                        }
                    }
                }
            }, quantiSecondi);
        }
    }
}
