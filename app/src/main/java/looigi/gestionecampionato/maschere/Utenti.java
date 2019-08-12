package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.StrutturaDatiUtente;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.dati.VariabiliStaticheUtenti;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.db_remoto.DBRemotoGenerale;
import looigi.gestionecampionato.db_remoto.DBRemotoUtenti;
import looigi.gestionecampionato.dialog.DialogMessaggio;

public class Utenti extends android.support.v4.app.Fragment {
    private Context context;
    private static String TAG= NomiMaschere.getInstance().getUtenti();
    private static VariabiliStaticheUtenti v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = this.getActivity();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.utenti, container, false));
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
            v=VariabiliStaticheUtenti.getInstance();

            v.setEdtUtente ((EditText) view.findViewById(R.id.edtUser));
            v.setEdtCognome ((EditText) view.findViewById(R.id.edtCognome));
            v.setEdtNome ((EditText) view.findViewById(R.id.edtNome));
            v.setEdtPassword ((EditText) view.findViewById(R.id.edtPassword));
            v.setEdtMail ((EditText) view.findViewById(R.id.edtEMail));
            v.setSpnNomeSquadra((Spinner) view.findViewById(R.id.spnNomeSquadra));
            v.setSpnCategorie((Spinner) view.findViewById(R.id.spnCategoria));

            v.setEdtUtenteLogin ((EditText) view.findViewById(R.id.edtUserLogin));
            v.setEdtPasswordLogin ((EditText) view.findViewById(R.id.edtPasswordLogin));

            VariabiliStaticheGlobali.getInstance().vPerPassaggio=v;

            v.getEdtUtente().setText("");
            v.getEdtCognome().setText("");
            v.getEdtNome().setText("");
            v.getEdtPassword().setText("");
            v.getEdtMail().setText("");
            v.getEdtUtenteLogin().setText("");
            v.getEdtPasswordLogin().setText("");

            if (v.getNomiSquadre()==null) {
                DBRemotoGenerale dbr = new DBRemotoGenerale();
                dbr.RitornaValoriPerRegistrazione(context, TAG);
            } else {
                RiempieListaSquadre();
            }
            v.setCmdLogin((Button) view.findViewById(R.id.cmdLogin));
            v.getCmdLogin().setOnClickListener(new View.OnClickListener() {
               public void onClick(View vv) {
                   String Utente = v.getEdtUtenteLogin().getText().toString();
                   String Password = v.getEdtPasswordLogin().getText().toString();

                   if (Utente.isEmpty()) {
                       DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                               "Inserire l'utenza",
                               true,
                               VariabiliStaticheGlobali.NomeApplicazione);
                   } else {
                       if (Password.isEmpty()) {
                           DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                                   "Inserire la password",
                                   true,
                                   VariabiliStaticheGlobali.NomeApplicazione);
                       } else {
                           DBRemotoUtenti dbr = new DBRemotoUtenti();
                           dbr.RitornaUtentePerLogin(VariabiliStaticheGlobali.getInstance().getContext(),
                                   Utente,
                                   Password,
                                   TAG);
                       }
                   }
               }
            });

            v.setCmdSalva((Button) view.findViewById(R.id.cmdRegistra));
            v.getCmdSalva().setOnClickListener(new View.OnClickListener() {
                public void onClick(View vv) {
                    if (VariabiliStaticheUtenti.NomeSquadraScelta.isEmpty()) {
                        DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                                "Selezionare un nome squadra",
                                true,
                                VariabiliStaticheGlobali.NomeApplicazione);
                    } else {
                        StrutturaDatiUtente strutt = new StrutturaDatiUtente();
                        strutt.setUtente(v.getEdtUtente().getText().toString());
                        strutt.setCognome(v.getEdtCognome().getText().toString());
                        strutt.setNome(v.getEdtNome().getText().toString());
                        strutt.setPassword(v.getEdtPassword().getText().toString());
                        strutt.setEMail(v.getEdtMail().getText().toString());
                        if (v.getIdCategoriaScelta()!=null) {
                            strutt.setIdCategoria1(Integer.toString(v.getIdCategoriaScelta()));
                        } else {
                            strutt.setIdCategoria1("-1");
                        }
                        strutt.setIdTipologia("2");

                        if (strutt.getUtente().isEmpty()) {
                            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                                    "Inserire un nome utente",
                                    true,
                                    VariabiliStaticheGlobali.NomeApplicazione);
                        } else {
                            if (strutt.getPassword().isEmpty()) {
                                DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                                        "Inserire una password",
                                        true,
                                        VariabiliStaticheGlobali.NomeApplicazione);
                            } else {
                                if (strutt.getEMail().isEmpty()) {
                                    DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                                            "Inserire una casella EMail",
                                            true,
                                            VariabiliStaticheGlobali.NomeApplicazione);
                                } else {
                                    VariabiliStaticheGlobali.getInstance().setDatiUtente(strutt);

                                    VariabiliStaticheMain.getInstance().setPartitaApplicazione(true);
                                    VariabiliStaticheMain.getInstance().getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                                    DBRemotoUtenti dbr = new DBRemotoUtenti();
                                    dbr.SalvaUtente(VariabiliStaticheGlobali.getInstance().getContext(),
                                            Integer.toString(VariabiliStaticheUtenti.idAnnoScelto),
                                            strutt,
                                            TAG);
                                }
                            }
                        }
                    }
                }
            });
        }
    }

    public static void RiempieListaCategorie() {
        if (v.getNomiSquadre() != null) {
            VariabiliStaticheGlobali.getInstance().vPerPassaggio=null;

            final ArrayAdapter<String> adapterCategorie = new ArrayAdapter<String>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item, v.getNomiCategorie());
            adapterCategorie.setDropDownViewResource(R.layout.spinner_item);
            v.getSpnCategorie().setAdapter(adapterCategorie);

            v.getSpnCategorie().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int idCategoria=v.getIdNomiCategorie().get(position);
                    VariabiliStaticheUtenti.getInstance().setIdCategoriaScelta(idCategoria);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
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
                    // int idCategoria=v.getIdCategorie1().get(position);
                    VariabiliStaticheUtenti.NomeSquadraScelta=v.getNomiSquadre().get(position);
                    VariabiliStaticheUtenti.idAnnoScelto=v.getIdAnno().get(position);

                    int appo=VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
                    VariabiliStaticheGlobali.getInstance().setAnnoInCorso(VariabiliStaticheUtenti.idAnnoScelto);
                    DBRemotoCategorie dbr = new DBRemotoCategorie();
                    dbr.RitornaCategoriePerAnno(VariabiliStaticheGlobali.getInstance().getContext(), TAG);
                    VariabiliStaticheGlobali.getInstance().setAnnoInCorso(appo);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }
}
