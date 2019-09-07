package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import looigi.gestionecampionato.R;
import looigi.gestionecampionato.adapter.AdapterGiocatoriAllAssenti;
import looigi.gestionecampionato.adapter.AdapterGiocatoriAllPresenti;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheAllenamenti;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.dati.VariabiliStaticheUtenti;
import looigi.gestionecampionato.db_remoto.DBRemotoAllenamenti;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.db_remoto.DBRemotoGiocatori;
import looigi.gestionecampionato.upload_download.CropUtility;
import looigi.gestionecampionato.utilities.MostraPannelloData;
import looigi.gestionecampionato.utilities.Utility;

public class Allenamenti extends Fragment {
    private static String TAG = NomiMaschere.getInstance().getAllenamenti();
    private static CropUtility cu;
    /* renamed from: a */
    private Fragment f28a;

    /* renamed from: looigi.gestionecampionato.maschere.Allenamenti$1 */
    class C05981 implements OnClickListener {
        C05981() {
        }

        public void onClick(View v) {
            VariabiliStaticheAllenamenti.getInstance().setGiocatoriPresenti(new ArrayList());
            VariabiliStaticheAllenamenti.getInstance().setGiocatoriAssenti(new ArrayList());
            Allenamenti.fillListViewGiocatoriPresenti();
            Allenamenti.fillListViewGiocatoriAssenti();
            VariabiliStaticheAllenamenti.getInstance().getCmdSalvaAllenamenti().setVisibility(LinearLayout.VISIBLE);
            VariabiliStaticheAllenamenti.getInstance().getLayDataOra().setVisibility(LinearLayout.VISIBLE);
            VariabiliStaticheAllenamenti.getInstance().getLayListe().setVisibility(LinearLayout.VISIBLE);
            VariabiliStaticheAllenamenti.getInstance().getLayTasti().setVisibility(LinearLayout.VISIBLE);
            VariabiliStaticheAllenamenti.getInstance().setGiocatoriAssenti(new ArrayList());
            VariabiliStaticheAllenamenti.getInstance().setGiocatoriPresenti(new ArrayList());
            new DBRemotoGiocatori().RitornaGiocatoriCategoria(VariabiliStaticheAllenamenti.getInstance().getContext(), Integer.toString(VariabiliStaticheAllenamenti.getInstance().idCategoriaScelta), Allenamenti.TAG);
        }
    }

    /* renamed from: looigi.gestionecampionato.maschere.Allenamenti$5 */
    class C06025 implements OnClickListener {
        C06025() {
        }

        public void onClick(View v) {
            if (VariabiliStaticheAllenamenti.getInstance().idCategoriaScelta > -1 && !VariabiliStaticheAllenamenti.getInstance().getTxtData().getText().toString().isEmpty()) {
                String date = Utility.getInstance().SistemaData(VariabiliStaticheAllenamenti.getInstance().getTxtData().getText().toString());
                VariabiliStaticheAllenamenti.getInstance().getTxtData().setText(date);
                new DBRemotoAllenamenti().RitornaAllenamentiCategoria(VariabiliStaticheAllenamenti.getInstance().getContext(), Integer.toString(VariabiliStaticheAllenamenti.getInstance().idCategoriaScelta), date, "00:00:00", Allenamenti.TAG);
                VariabiliStaticheAllenamenti.getInstance().getCmdSalvaAllenamenti().setVisibility(LinearLayout.GONE);
            }
        }
    }

    /* renamed from: looigi.gestionecampionato.maschere.Allenamenti$7 */
    static class C06047 implements OnItemSelectedListener {
        C06047() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
            VariabiliStaticheAllenamenti.getInstance().idCategoriaScelta = ((Integer) VariabiliStaticheAllenamenti.getInstance().getIdCategorie().get(position)).intValue();
            VariabiliStaticheUtenti.getInstance().setIdCategoriaScelta(Integer.valueOf(VariabiliStaticheAllenamenti.getInstance().idCategoriaScelta));
            VariabiliStaticheGlobali.getInstance().getDatiUtente().setDescCategoria((String) VariabiliStaticheAllenamenti.getInstance().getCategorie().get(position));
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        VariabiliStaticheAllenamenti.getInstance().setContext(getActivity());
        this.f28a = this;
        cu = new CropUtility();
        View view = null;
        try {
            view = inflater.inflate(R.layout.allenamenti, container, false);
        } catch (Exception e) {
        }
        if (view != null) {
            VariabiliStaticheGlobali.getInstance().setViewActivity(view);
            initializeGraphic();
        }
        return view;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    public void onResume() {
        super.onResume();
    }

    private void initializeGraphic() {
        final Context context = VariabiliStaticheGlobali.getInstance().getContext();
        final View view = VariabiliStaticheGlobali.getInstance().getViewActivity();
        if (view != null) {
            VariabiliStaticheAllenamenti.getInstance().setSpnCategorie((Spinner) view.findViewById(R.id.spnCategorie));
            // Utility.getInstance().SettaColoreSceltaCategoria(view);
            // TextView txtAllTit = (TextView) view.findViewById(R.id.txtAllenamentiTit);
            // Utility.getInstance().SettaColoreSfondoPerNomeSquadra(txtAllTit);
            /* if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraCastelVerde)) {
                txtAllTit.setTextColor(ViewCompat.MEASURED_STATE_MASK);
            } else if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraPonteDiNona)) {
                txtAllTit.setTextColor(-1);
            }
            RelativeLayout layMascheraModUtenti = (RelativeLayout) view.findViewById(R.id.layMascheraModUtenti);
            if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraCastelVerde)) {
                layMascheraModUtenti.setBackgroundResource(R.drawable.bordo_arrotondato_verde_chiaro);
            } else if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraPonteDiNona)) {
                layMascheraModUtenti.setBackgroundResource(R.drawable.bordo_arrotondato_rosso_chiaro);
            } */
            VariabiliStaticheAllenamenti.getInstance().idCategoriaScelta = -1;
            if (VariabiliStaticheAllenamenti.getInstance().getCategorie() == null) {
                new DBRemotoCategorie().RitornaCategorie(context, TAG);
            } else {
                RiempieListaCategorie();
            }
            ((Button) view.findViewById(R.id.cmdScegliCat)).setOnClickListener(new C05981());
            ImageView cmdData = (ImageView) view.findViewById(R.id.btnData);
            VariabiliStaticheAllenamenti.getInstance().setTxtData((TextView) view.findViewById(R.id.txtdata));
            VariabiliStaticheAllenamenti.getInstance().getTxtData().setText(DateFormat.format("dd/MM/yyyy", Calendar.getInstance().getTime()).toString());
            VariabiliStaticheAllenamenti.getInstance().setCmdSalvaAllenamenti((Button) view.findViewById(R.id.cmdSalvaAllenamenti));
            VariabiliStaticheAllenamenti.getInstance().setLstGiocatoriAssenti((ListView) view.findViewById(R.id.lstvDaAllenare));
            VariabiliStaticheAllenamenti.getInstance().setLstGiocatoriPresenti((ListView) view.findViewById(R.id.lstvAllenanti));
            final RelativeLayout rlMaschera = (RelativeLayout) view.findViewById(R.id.layMascheraModUtenti);
            VariabiliStaticheAllenamenti.getInstance().setLayDataOra((LinearLayout) view.findViewById(R.id.layDataOra));
            VariabiliStaticheAllenamenti.getInstance().setLayListe((LinearLayout) view.findViewById(R.id.layListe));
            VariabiliStaticheAllenamenti.getInstance().setLayTasti((LinearLayout) view.findViewById(R.id.layTasti));
            rlMaschera.setVisibility(LinearLayout.VISIBLE);
            VariabiliStaticheAllenamenti.getInstance().getLayDataOra().setVisibility(LinearLayout.VISIBLE);
            VariabiliStaticheAllenamenti.getInstance().getLayListe().setVisibility(LinearLayout.VISIBLE);
            VariabiliStaticheAllenamenti.getInstance().getLayTasti().setVisibility(LinearLayout.VISIBLE);
            VariabiliStaticheAllenamenti.getInstance().setGiocatoriPresenti(new ArrayList());
            cmdData.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    rlMaschera.setVisibility(LinearLayout.GONE);
                    view.findViewById(R.id.idData).setVisibility(LinearLayout.GONE);
                    view.findViewById(R.id.idOra).setVisibility(LinearLayout.VISIBLE);
                    MostraPannelloData mostraPannelloData = new MostraPannelloData(context, VariabiliStaticheAllenamenti.getInstance().getTxtData(), view.findViewById(R.id.idData));
                }
            });
            if (VariabiliStaticheGlobali.getInstance().getDatiUtente()!=null) {
                final String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
                ((Button) view.findViewById(R.id.cmdMetteTutti)).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            for (String a : VariabiliStaticheAllenamenti.getInstance().getGiocatoriAssenti()) {
                                VariabiliStaticheAllenamenti.getInstance().getGiocatoriPresenti().add(a);
                            }
                            VariabiliStaticheAllenamenti.getInstance().setGiocatoriAssenti(new ArrayList());
                            Allenamenti.fillListViewGiocatoriAssenti();
                            Allenamenti.fillListViewGiocatoriPresenti();
                        }
                    }
                });
                ((Button) view.findViewById(R.id.cmdToglieTutti)).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            for (String p : VariabiliStaticheAllenamenti.getInstance().getGiocatoriPresenti()) {
                                VariabiliStaticheAllenamenti.getInstance().getGiocatoriAssenti().add(p);
                            }
                            VariabiliStaticheAllenamenti.getInstance().setGiocatoriPresenti(new ArrayList());
                            Allenamenti.fillListViewGiocatoriAssenti();
                            Allenamenti.fillListViewGiocatoriPresenti();
                        }
                    }
                });
            }
            VariabiliStaticheAllenamenti.getInstance().setBtnRitornaAllenamenti((Button) view.findViewById(R.id.cmdCercaAllenamenti));
            VariabiliStaticheAllenamenti.getInstance().getBtnRitornaAllenamenti().setOnClickListener(new C06025());
            VariabiliStaticheAllenamenti.getInstance().getCmdSalvaAllenamenti().setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    String Lista = "";
                    for (String s : VariabiliStaticheAllenamenti.getInstance().getGiocatoriPresenti()) {
                        String[] c = s.split(";");
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(Lista);
                        stringBuilder.append(c[0]);
                        stringBuilder.append(";");
                        Lista = stringBuilder.toString();
                    }
                    new DBRemotoAllenamenti().SalvaAllenamenti(context, Integer.toString(VariabiliStaticheAllenamenti.getInstance().idCategoriaScelta), Utility.getInstance().SistemaData(VariabiliStaticheAllenamenti.getInstance().getTxtData().getText().toString()), "00:00:00", Lista, Allenamenti.TAG);
                }
            });
            VariabiliStaticheAllenamenti.getInstance().getCmdSalvaAllenamenti().setVisibility(LinearLayout.VISIBLE);
        }
    }

    public static void RiempieListaCategorie() {
        if (VariabiliStaticheAllenamenti.getInstance().getCategorie() != null) {
            ArrayAdapter<String> adapterCategorie = new ArrayAdapter(VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_piccolo, VariabiliStaticheAllenamenti.getInstance().getCategorie());
            adapterCategorie.setDropDownViewResource(R.layout.spinner_item_piccolo);
            VariabiliStaticheAllenamenti.getInstance().getSpnCategorie().setAdapter(adapterCategorie);
            Utility.getInstance().CercaESettaStringaInSpinner(VariabiliStaticheAllenamenti.getInstance().getSpnCategorie(), VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria1());
            VariabiliStaticheAllenamenti.getInstance().idCategoriaScelta = Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1());
            int pos = Utility.getInstance().CercaESettaStringaInSpinner(VariabiliStaticheAllenamenti.getInstance().getSpnCategorie(), VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria1()).intValue();
            if (pos > -1) {
                VariabiliStaticheAllenamenti.getInstance().getSpnCategorie().setSelection(pos);
            }
            VariabiliStaticheAllenamenti.getInstance().idCategoriaScelta = Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1());
            VariabiliStaticheAllenamenti.getInstance().getSpnCategorie().setOnItemSelectedListener(new C06047());
        }
    }

    public static void fillListViewGiocatoriAssenti() {
        VariabiliStaticheAllenamenti vv = VariabiliStaticheAllenamenti.getInstance();
        if (vv.getGiocatoriAssenti() != null) {
            vv.setAdapterGiocatoriAssenti(new AdapterGiocatoriAllAssenti(VariabiliStaticheGlobali.getInstance().getContext(), 17367043, vv.getGiocatoriAssenti()));
            vv.getLstGiocatoriAssenti().setAdapter(vv.getAdapterGiocatoriAssenti());
        }
    }

    public static void fillListViewGiocatoriPresenti() {
        VariabiliStaticheAllenamenti vv = VariabiliStaticheAllenamenti.getInstance();
        if (vv.getGiocatoriPresenti() != null) {
            vv.setAdapterGiocatoriPresenti(new AdapterGiocatoriAllPresenti(VariabiliStaticheGlobali.getInstance().getContext(), 17367043, vv.getGiocatoriPresenti()));
            vv.getLstGiocatoriPresenti().setAdapter(vv.getAdapterGiocatoriPresenti());
        }
    }
}