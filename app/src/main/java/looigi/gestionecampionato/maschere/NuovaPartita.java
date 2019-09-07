package looigi.gestionecampionato.maschere;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.adapter.AdapterDirigenti;
import looigi.gestionecampionato.adapter.AdapterDirigentiConvocati;
import looigi.gestionecampionato.adapter.AdapterEventi;
import looigi.gestionecampionato.adapter.AdapterEventiPartita;
import looigi.gestionecampionato.adapter.AdapterGiocatore;
import looigi.gestionecampionato.adapter.AdapterGiocatoreEventi;
import looigi.gestionecampionato.adapter.AdapterGiocatoreRigori;
import looigi.gestionecampionato.adapter.AdapterMinutiGoalAvversari;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheArbitri;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMeteo;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.db_remoto.DBRemotoAllenatori;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.db_remoto.DBRemotoGiocatori;
import looigi.gestionecampionato.db_remoto.DBRemotoPartite;
import looigi.gestionecampionato.dialog.DialogDomanda;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.dialog.DialogTempo;
import looigi.gestionecampionato.gps.GPSStatusBroadcastReceiver;
import looigi.gestionecampionato.gps.PrendeCoordinateGPS;
import looigi.gestionecampionato.utilities.MostraPannelloData;
import looigi.gestionecampionato.utilities.MostraPannelloOra;
import looigi.gestionecampionato.utilities.TimerTempo;
import looigi.gestionecampionato.utilities.TranslatorBackgroundTask;
import looigi.gestionecampionato.utilities.Utility;

import static looigi.gestionecampionato.dati.VariabiliStaticheGlobali.RadiceWS;

public class NuovaPartita extends android.support.v4.app.Fragment {
    // private Context context;
    private static boolean ModificaEffettuata = false;
    private static String TAG = NomiMaschere.getInstance().getNuovaPartita();
    // private static VariabiliStaticheNuovaPartita vnp;

    public static int PartitaNuova;
    private static int idUnioneCalendario;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Context context = this.getActivity();
        final VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();
        vnp.setContext(this.getActivity());
        // vnp=VariabiliStaticheNuovaPartita.getInstance();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.nuova_partita, container, false));
        } catch (Exception ignored) {

        }

        if (view!=null) {
            ModificaEffettuata = false;
            vnp.setViewActivity(view);

            Bundle args = getArguments();
            int NumeroPartita = args.getInt("NumeroPartita", -1);
            idUnioneCalendario = args.getInt("idUnioneCalendario", -1);

            if (NumeroPartita!=-1) {
                PartitaNuova=NumeroPartita;
                //DialogMessaggio.getInstance().show(context, "Numero Partita: "+Integer.toString(NumeroPartita), false, VariabiliStaticheGlobali.NomeApplicazione);
            } else {
                PartitaNuova=-1;
            }

            initializeGraphic();


        }

        return view;
    }

    private static void TraduciTesto() {
        String textToBeTranslated = VariabiliStaticheMeteo.getInstance().getTempo();
        String languagePair = "en-it";
        Translate(textToBeTranslated,languagePair);
    }

    private static void Translate(String textToBeTranslated, String languagePair){
        TranslatorBackgroundTask translatorBackgroundTask= new TranslatorBackgroundTask(VariabiliStaticheGlobali.getInstance().getContext());
        translatorBackgroundTask.execute(textToBeTranslated, languagePair); // Returns the translated text as a String
        // Log.d("Translation Result",translationResult); // Logs the result in Android Monitor
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

    public static void ScriveMeteo() {
        VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                VariabiliStaticheNuovaPartita.getInstance().getTxtTempo().setText(ConverteDaNull(VariabiliStaticheMeteo.getInstance().getTempo()));
                if (ConverteDaNull(VariabiliStaticheMeteo.getInstance().getGradi())==null || ConverteDaNull(VariabiliStaticheMeteo.getInstance().getGradi()).isEmpty()) {
                    VariabiliStaticheNuovaPartita.getInstance().getTxtGradi().setText("");
                } else {
                    VariabiliStaticheNuovaPartita.getInstance().getTxtGradi().setText("Gradi: " + ConverteDaNull(VariabiliStaticheMeteo.getInstance().getGradi()) + "°");
                }
                if (ConverteDaNull(VariabiliStaticheMeteo.getInstance().getUmidita())==null || ConverteDaNull(VariabiliStaticheMeteo.getInstance().getUmidita()).isEmpty()) {
                    VariabiliStaticheNuovaPartita.getInstance().getTxtUmidita().setText("");
                } else {
                    VariabiliStaticheNuovaPartita.getInstance().getTxtUmidita().setText("Umidità: " + ConverteDaNull(VariabiliStaticheMeteo.getInstance().getUmidita()) + "%");
                }
                if (ConverteDaNull(VariabiliStaticheMeteo.getInstance().getPressione())==null || ConverteDaNull(VariabiliStaticheMeteo.getInstance().getPressione()).isEmpty()) {
                    VariabiliStaticheNuovaPartita.getInstance().getTxtPressione().setText("");
                } else {
                    VariabiliStaticheNuovaPartita.getInstance().getTxtPressione().setText("Pressione: " + ConverteDaNull(VariabiliStaticheMeteo.getInstance().getPressione()));
                }
            }
        });

        TraduciTesto();
    }

    private static String ConverteDaNull(String Valore) {
        if (Valore == null || Valore.isEmpty()) {
            return "";
        } else {
            return Valore;
        }
    }

    public static void ScriveCoords() {
        if (VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale() != null) {
            VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (VariabiliStaticheMeteo.getInstance().getLat() == null || VariabiliStaticheMeteo.getInstance().getLat().isEmpty()) {
                        VariabiliStaticheNuovaPartita.getInstance().getTxtLat().setText("");
                    } else {
                        // if (VariabiliStaticheMeteo.getInstance().getLat().length() > 10) {
                        //     VariabiliStaticheNuovaPartita.getInstance().getTxtLat().setText("Lat.: " + VariabiliStaticheMeteo.getInstance().getLat().substring(0, 10));
                        // } else {
                        VariabiliStaticheNuovaPartita.getInstance().getTxtLat().setText("Lat.: " + VariabiliStaticheMeteo.getInstance().getLat());
                        // }
                    }
                    if (VariabiliStaticheMeteo.getInstance().getLon() == null || VariabiliStaticheMeteo.getInstance().getLon().isEmpty()) {
                        VariabiliStaticheNuovaPartita.getInstance().getTxtLon().setText("");
                    } else {
                        // if (VariabiliStaticheMeteo.getInstance().getLon().length() > 10) {
                        //     VariabiliStaticheNuovaPartita.getInstance().getTxtLon().setText("Lon.: " + VariabiliStaticheMeteo.getInstance().getLon().substring(0, 10));
                        // } else {
                        VariabiliStaticheNuovaPartita.getInstance().getTxtLon().setText("Lon.: " + VariabiliStaticheMeteo.getInstance().getLon());
                        // }
                    }
                }
            });
        }
    }

    private void initializeGraphic() {
        VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();
        Context context = VariabiliStaticheGlobali.getInstance().getContext();
        View view = vnp.getViewActivity();

        ImpostaSchermata(view);

        if (view != null) {
            TextView t = view.findViewById(R.id.txtTempo);
            VariabiliStaticheNuovaPartita.getInstance().setTxtTempo(t);
            t = view.findViewById(R.id.txtGradi);
            VariabiliStaticheNuovaPartita.getInstance().setTxtGradi(t);
            t = view.findViewById(R.id.txtUmidita);
            VariabiliStaticheNuovaPartita.getInstance().setTxtUmidita(t);
            t = view.findViewById(R.id.txtPressione);
            VariabiliStaticheNuovaPartita.getInstance().setTxtPressione(t);
            t = view.findViewById(R.id.txtLat);
            VariabiliStaticheNuovaPartita.getInstance().setTxtLat(t);
            t = view.findViewById(R.id.txtLon);
            VariabiliStaticheNuovaPartita.getInstance().setTxtLon(t);

            vnp.setTempiGAvvPrimoTempo(new ArrayList<Integer>());
            vnp.setTempiGAvvSecondoTempo(new ArrayList<Integer>());
            vnp.setTempiGAvvTerzoTempo(new ArrayList<Integer>());

            String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

            Button cmdAggiornaMeteo = view.findViewById(R.id.btnAggiornaMeteo);
            if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                cmdAggiornaMeteo.setVisibility(LinearLayout.VISIBLE);
                cmdAggiornaMeteo.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        ModificaEffettuata = true;
                        try {
                            VariabiliStaticheNuovaPartita.getInstance().intentGPS = new Intent(VariabiliStaticheGlobali.getInstance().getContext(), GPSStatusBroadcastReceiver.class);
                            VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale().startService(VariabiliStaticheNuovaPartita.getInstance().intentGPS);
                            PrendeCoordinateGPS.getInstance().AttivaGPS(VariabiliStaticheGlobali.getInstance().getContext());
                        } catch (Exception ignored) {

                        }
                    }
                });
            } else {
                cmdAggiornaMeteo.setVisibility(LinearLayout.INVISIBLE);
            }

            VariabiliStaticheMeteo.getInstance().PulisceTutto();

            // ScriveMeteo();

            TimerTempo.getInstance().AzzeraTimerTuttiITimer();

            // cmdAggiornaMeteo.setEnabled(false);

            if (PartitaNuova==-1) {
                if (vnp.getListMarcPrimoTempo()!=null) vnp.getListMarcPrimoTempo().clear();
                if (vnp.getListMarcSecondoTempo()!=null) vnp.getListMarcSecondoTempo().clear();
                if (vnp.getListMarcTerzoTempo()!=null) vnp.getListMarcTerzoTempo().clear();
                if (vnp.getTxtRisAvv1Tempo()!=null) vnp.getTxtRisAvv1Tempo().setText("0");
                if (vnp.getTxtRisAvv2Tempo()!=null) vnp.getTxtRisAvv2Tempo().setText("0");
                if (vnp.getTxtRisAvv3Tempo()!=null) vnp.getTxtRisAvv3Tempo().setText("0");
                if (vnp.getEdtGiochetti()!=null) vnp.getEdtGiochetti().setText("");

                /* try {
                    VariabiliStaticheNuovaPartita.getInstance().intentGPS = new Intent(context, GPSStatusBroadcastReceiver.class);
                    this.getActivity().startService(VariabiliStaticheNuovaPartita.getInstance().intentGPS);
                    PrendeCoordinateGPS.getInstance().AttivaGPS(context);
                    cmdAggiornaMeteo.setEnabled(true);
                } catch (Exception ignored) {

                } */

                DBRemotoPartite dbr = new DBRemotoPartite();
                dbr.RitornaIdPartita(context, TAG);
            } else {
                //if (vnp.getDescrizioneCategorie()==null) {
                    DBRemotoCategorie dbr = new DBRemotoCategorie();
                    dbr.RitornaCategorie(context, TAG);
                /* } else {
                    fillSpinnersCategorie();

                    if (vnp.getDescrizioneTipologie()==null) {
                        DBRemotoGenerale dbr = new DBRemotoGenerale();
                        dbr.RitornaTipologie(context, TAG);
                    } else {
                        fillSpinnersTipologie();

                        if (vnp.getAvversari()==null) {
                            DBRemotoAvversari dbr = new DBRemotoAvversari();
                            dbr.RitornaAvversari(context, "", TAG);
                        } else {
                            fillSpinnersAvversari(
                                    vnp.getAvversari(),
                                    vnp.getAvversariID(),
                                    vnp.getAvversariCampoID(),
                                    vnp.getAvversariCampo(),
                                    vnp.getAvversariCampoIndirizzo()
                            );

                            if (PartitaNuova!=-1) {
                                // Carica dati partita
                                DBRemotoPartite dbr = new DBRemotoPartite();
                                dbr.RitornaPartitaDaID(context, Integer.toString(PartitaNuova), TAG);
                            } else {
                                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                                    vnp.getCmdSalva().setVisibility(LinearLayout.VISIBLE);
                                }
                            }
                        }
                    }
                } */
            }
        }
    }

    public static void RiempieCampiPartita(String DatiPartita) {
        VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();
        DatiPartita = DatiPartita.replace("|","£");

        String[] Campi=DatiPartita.split("£",-1);

        String[] DatiPartitaGen = Campi[0].replace("§","").split(";", -1);

        vnp.idCategoriaScelta=Integer.parseInt(DatiPartitaGen[0]);
        vnp.idAvversarioScelto=Integer.parseInt(DatiPartitaGen[1]);
        vnp.idTipologiaScelta=Integer.parseInt(DatiPartitaGen[2]);
        vnp.idCampoScelto=Integer.parseInt(DatiPartitaGen[3]);
        if (!DatiPartitaGen[20].isEmpty()) {
             vnp.idAllenatoreScelto = Integer.parseInt(DatiPartitaGen[20]);
        }
        if (!DatiPartitaGen[5].isEmpty() && DatiPartitaGen[5].contains(" ")) {
            String[] DataIntera = DatiPartitaGen[5].split(" ");
            vnp.getTxtData().setText(DataIntera[0].trim());
            vnp.getTxtOra().setText(DataIntera[1].trim());
        } else {
            vnp.getTxtData().setText("");
            vnp.getTxtOra().setText("");
        }
        vnp.getEdtNote().setText(DatiPartitaGen[8]);
        vnp.getEdtGiochetti().setText(DatiPartitaGen[9]);
        if (DatiPartitaGen[10].isEmpty()) {
            vnp.getTxtRisAvv1Tempo().setText("0");
        } else {
            vnp.getTxtRisAvv1Tempo().setText(DatiPartitaGen[10]);
        }

        String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
        View view = VariabiliStaticheNuovaPartita.getInstance().getViewActivity();
        Button btnAvvenimentiSecondoTempo = view.findViewById(R.id.btnAvvenimentiSecondoTempo);
        Button btnAvvenimentiTerzoTempo = view.findViewById(R.id.btnAvvenimentiTerzoTempo);

        if (DatiPartitaGen[11].isEmpty()) {
            vnp.getTxtRisAvv2Tempo().setText("0");
        } else {
            if (DatiPartitaGen[11].equals("-1")) {
                btnAvvenimentiSecondoTempo.setVisibility(LinearLayout.GONE);

                vnp.getTxtRisAvv2Tempo().setText("Non giocato");

                vnp.getLvvMarcatoriSecondoTempo().setVisibility(LinearLayout.GONE);
                vnp.getSpnMarcatoriSecondoTempo().setVisibility(LinearLayout.GONE);

                vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.GONE);
                Button cmdMenoAvv2Tempo = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.btnMenoAvvSecondoTempo);
                cmdMenoAvv2Tempo.setVisibility(LinearLayout.GONE);

                vnp.getCmdReset2Tempo().setVisibility(LinearLayout.GONE);
            } else {
                btnAvvenimentiSecondoTempo.setVisibility(LinearLayout.VISIBLE);

                vnp.getTxtRisAvv2Tempo().setText(DatiPartitaGen[11]);

                vnp.getLvvMarcatoriSecondoTempo().setVisibility(LinearLayout.VISIBLE);
                vnp.getSpnMarcatoriSecondoTempo().setVisibility(LinearLayout.VISIBLE);

                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.VISIBLE);
                    Button cmdMenoAvv2Tempo = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.btnMenoAvvSecondoTempo);
                    cmdMenoAvv2Tempo.setVisibility(LinearLayout.VISIBLE);

                    vnp.getCmdReset2Tempo().setVisibility(LinearLayout.VISIBLE);
                } else {
                    vnp.getLvvMarcatoriSecondoTempo().setEnabled(false);
                    vnp.getSpnMarcatoriSecondoTempo().setEnabled(false);
                }
            }
        }
        if (DatiPartitaGen[12].isEmpty()) {
            vnp.getTxtRisAvv3Tempo().setText("0");
        } else {
            if (DatiPartitaGen[12].equals("-1")) {
                /* btnAvvenimentiTerzoTempo.setVisibility(LinearLayout.GONE);

                vnp.getTxtRisAvv3Tempo().setText("Non giocato");

                vnp.getLvvMarcatoriTerzoTempo().setVisibility(LinearLayout.GONE);
                vnp.getSpnMarcatoriTerzoTempo().setVisibility(LinearLayout.GONE);

                vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.GONE);
                Button cmdMenoAvv3Tempo = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.btnMenoAvvTerzoTempo);
                cmdMenoAvv3Tempo.setVisibility(LinearLayout.GONE);

                vnp.getCmdReset3Tempo().setVisibility(LinearLayout.GONE); */

                btnAvvenimentiTerzoTempo.setEnabled(false);

                vnp.getTxtRisAvv3Tempo().setText("Non giocato");

                vnp.getLvvMarcatoriTerzoTempo().setEnabled(false);
                vnp.getSpnMarcatoriTerzoTempo().setEnabled(false);

                vnp.getCmdStartSopTimer3Tempo().setEnabled(false);
                Button cmdMenoAvv3Tempo = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.btnMenoAvvTerzoTempo);
                cmdMenoAvv3Tempo.setEnabled(false);

                RelativeLayout layNoTerzoTempo = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.layNoTerzoTempo);
                layNoTerzoTempo.setVisibility(LinearLayout.VISIBLE);

                vnp.getCmdReset3Tempo().setEnabled(false);
            } else {
                /* btnAvvenimentiTerzoTempo.setVisibility(LinearLayout.VISIBLE);

                vnp.getTxtRisAvv3Tempo().setText(DatiPartitaGen[12]);

                vnp.getLvvMarcatoriTerzoTempo().setVisibility(LinearLayout.VISIBLE);
                vnp.getSpnMarcatoriTerzoTempo().setVisibility(LinearLayout.VISIBLE); */

                btnAvvenimentiTerzoTempo.setEnabled(true);

                vnp.getTxtRisAvv3Tempo().setText(DatiPartitaGen[12]);

                vnp.getLvvMarcatoriTerzoTempo().setEnabled(true);
                vnp.getSpnMarcatoriTerzoTempo().setEnabled(true);

                RelativeLayout layNoTerzoTempo = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.layNoTerzoTempo);
                layNoTerzoTempo.setVisibility(LinearLayout.GONE);

                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    /* vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.VISIBLE);
                    Button cmdMenoAvv3Tempo = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.btnMenoAvvTerzoTempo);
                    cmdMenoAvv3Tempo.setVisibility(LinearLayout.VISIBLE);
                    vnp.getCmdReset3Tempo().setVisibility(LinearLayout.VISIBLE);
                    */

                    vnp.getCmdStartSopTimer3Tempo().setEnabled(true);
                    Button cmdMenoAvv3Tempo = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.btnMenoAvvTerzoTempo);
                    cmdMenoAvv3Tempo.setEnabled(true);

                    vnp.getCmdReset3Tempo().setEnabled(true);
                } else {
                    vnp.getLvvMarcatoriTerzoTempo().setEnabled(false);
                    vnp.getSpnMarcatoriTerzoTempo().setEnabled(false);
                }
            }
        }
        int pos = Utility.getInstance().CercaESettaStringaInSpinner(vnp.getSpnAvversario(), DatiPartitaGen[13]);
        if (pos>-1) {
            vnp.getSpnAvversario().setSelection(pos);
        }
        vnp.getTxtAvversario().setText(DatiPartitaGen[13]);

        pos = Utility.getInstance().CercaESettaStringaInSpinner(vnp.getSpnTipologie(), DatiPartitaGen[18]);
        if (pos>-1) {
            vnp.getSpnTipologie().setSelection(pos);
        }
        vnp.getTxtTipologia().setText(DatiPartitaGen[18]);
        if (DatiPartitaGen[19].equals("S")) {
            vnp.getChkInCasa().setChecked(true);
            vnp.getChkEsterno().setChecked(false);
            vnp.getLayCampoIndirizzo().setVisibility(LinearLayout.VISIBLE);
            vnp.getLayCampoEsterno().setVisibility(LinearLayout.GONE);
        } else {
            if (DatiPartitaGen[19].equals("E")) {
                vnp.getChkEsterno().setChecked(true);
                vnp.getLayCampoEsterno().setVisibility(LinearLayout.VISIBLE);
                vnp.getLayCampoIndirizzo().setVisibility(LinearLayout.INVISIBLE);
                vnp.getEdtCampoEsterno().setText(DatiPartitaGen[14]);
            } else {
                vnp.getChkInCasa().setChecked(false);

                vnp.getTxtCampo().setText(DatiPartitaGen[14]);
                vnp.getTxtCampoIndirizzo().setText(DatiPartitaGen[17]);
                vnp.getLayCampoIndirizzo().setVisibility(LinearLayout.VISIBLE);
                vnp.getLayCampoEsterno().setVisibility(LinearLayout.GONE);
            }
        }
        pos = Utility.getInstance().CercaESettaStringaInSpinner(vnp.getSpnCategorie(), DatiPartitaGen[16]);
        if (pos>-1) {
            vnp.getSpnCategorie().setSelection(pos);
        }
        vnp.getTxtCategoria().setText(DatiPartitaGen[16]);

        String cate = DatiPartitaGen[16];
        if (cate.contains("-")) {
            cate = cate.substring(cate.indexOf("-")+1, cate.length());
        }
        cate = cate.trim();

        vnp.getTxtCasa().setText(cate);

        cate = DatiPartitaGen[13];
        if (cate.contains("-")) {
            cate = cate.substring(cate.indexOf("-")+1, cate.length());
        }
        cate = cate.trim();

        vnp.getTxtFuori().setText(cate);

        Utility.getInstance().PrendeImmagineCategoria(DatiPartitaGen[0], vnp.getImgCasa());
        Utility.getInstance().PrendeImmagineAvversario(DatiPartitaGen[1], vnp.getImgFuori());

        try {
            vnp.MarcatoriPerRicarica = Campi[1];
            vnp.ConvocatiPerRicarica = Campi[2];
        } catch (Exception ignored) {

        }

        vnp.getTxtTimer1Tempo().setText(DatiPartitaGen[22]);
        vnp.getTxtTimer2Tempo().setText(DatiPartitaGen[23]);
        vnp.getTxtTimer3Tempo().setText(DatiPartitaGen[24]);

        TimerTempo.getInstance().SetTempo(1, DatiPartitaGen[22]);
        TimerTempo.getInstance().SetTempo(2, DatiPartitaGen[23]);
        TimerTempo.getInstance().SetTempo(3, DatiPartitaGen[24]);

        VariabiliStaticheMeteo.getInstance().setLat(DatiPartitaGen[25]);
        VariabiliStaticheMeteo.getInstance().setLon(DatiPartitaGen[26]);

        VariabiliStaticheMeteo.getInstance().setTempo(DatiPartitaGen[27]);
        VariabiliStaticheMeteo.getInstance().setGradi(DatiPartitaGen[28]);
        VariabiliStaticheMeteo.getInstance().setUmidita(DatiPartitaGen[29]);
        VariabiliStaticheMeteo.getInstance().setPressione(DatiPartitaGen[30]);

        ScriveCoords();
        ScriveMeteo();

        // Tempi Goal Avversari
        vnp.setTempiGAvvPrimoTempo(new ArrayList<Integer>());
        vnp.setTempiGAvvSecondoTempo(new ArrayList<Integer>());
        vnp.setTempiGAvvTerzoTempo(new ArrayList<Integer>());

        String Tga1 = DatiPartitaGen[31];
        String Tga2 = DatiPartitaGen[32];
        String Tga3 = DatiPartitaGen[33];

        if (!Tga1.isEmpty()) {
            if (Tga1.contains("#")) {
                for (String s : Tga1.split("#")) {
                    Integer t = Integer.parseInt(s);
                    vnp.getTempiGAvvPrimoTempo().add(t);
                }
            }
        }
        if (!Tga2.isEmpty()) {
            if (Tga2.contains("#")) {
                for (String s : Tga2.split("#")) {
                    Integer t = Integer.parseInt(s);
                    vnp.getTempiGAvvSecondoTempo().add(t);
                }
            }
        }
        if (!Tga3.isEmpty()) {
            if (Tga3.contains("#")) {
                for (String s : Tga3.split("#")) {
                    Integer t = Integer.parseInt(s);
                    vnp.getTempiGAvvTerzoTempo().add(t);
                }
            }
        }

        fillSpinnerMinutiGoalAvversari();
        // Tempi Goal Avversari

        String Dirigenti = DatiPartitaGen[34];

        VariabiliStaticheNuovaPartita.getInstance().setDirigenteSelezionato(new ArrayList<String>());
        VariabiliStaticheNuovaPartita.getInstance().setIdDirigenteSelezionato(new ArrayList<Integer>());

        if (!Dirigenti.isEmpty()) {
            String d[] = Dirigenti.split("%");
            for (String dd : d) {
                if (!dd.isEmpty()) {
                    String ddd[] = dd.split("!");

                    VariabiliStaticheNuovaPartita.getInstance().getIdDirigenteSelezionato().add(Integer.parseInt(ddd[0]));
                    VariabiliStaticheNuovaPartita.getInstance().getDirigenteSelezionato().add(ddd[1]);
                }
            }
        }

        RiempieListaDirigentiSelezionati();

        String Arbitro = DatiPartitaGen[35];
        if (Arbitro.contains("-")) {
            String[] a = Arbitro.split("-");

            if (a.length > 1) {
                if (a[0].isEmpty()) {
                    VariabiliStaticheArbitri.getInstance().idArbitroScelto = -1;
                } else {
                    VariabiliStaticheArbitri.getInstance().idArbitroScelto = Integer.parseInt(a[0]);
                }
                if (a[1].isEmpty()) {
                    vnp.getTxtArbitro().setText("");
                } else {
                    vnp.getTxtArbitro().setText(a[1]);
                }
            } else {
                VariabiliStaticheArbitri.getInstance().idArbitroScelto = -1;
                vnp.getTxtArbitro().setText("");
            }
        } else {
            VariabiliStaticheArbitri.getInstance().idArbitroScelto = -1;
            vnp.getTxtArbitro().setText("");
        }

        String RisultatoATempi = DatiPartitaGen[36];
        if (RisultatoATempi.equals("S")) {
            VariabiliStaticheNuovaPartita.getInstance().getChkTempi().setChecked(true);
            vnp.getTxtTempi().setVisibility(LinearLayout.VISIBLE);
        } else {
            VariabiliStaticheNuovaPartita.getInstance().getChkTempi().setChecked(false);
            vnp.getTxtTempi().setVisibility(LinearLayout.GONE);
        }

        String RigoriPropri = DatiPartitaGen[37];
        String RigoriAvv = DatiPartitaGen[38];

        if (RigoriPropri.contains("%")) {
            vnp.setGiocatoreConvocatoRigori(new ArrayList<String>());
            String[] rp = RigoriPropri.split("%", -1);
            for (String s : rp) {
                if (!s.isEmpty()) {
                    s = s.replace("!", ";");
                    vnp.getGiocatoreConvocatoRigori().add(s);
                }
            }
        }

        TextView tse = vnp.getViewActivity().findViewById(R.id.txtRigoriSegnAvversari);
        TextView tsb = vnp.getViewActivity().findViewById(R.id.txtRigoriSbaAvversari);
        if (RigoriAvv.isEmpty()) {
            tse.setText("0");
            tsb.setText("0");
        } else {
            if (RigoriAvv.contains("!")) {
                String[] ra = RigoriAvv.split("!", -1);
                if (ra.length>0) {
                    tse.setText(ra[0]);
                } else {
                    tse.setText("0");
                }
                if (ra.length>1) {
                    tsb.setText(ra[1]);
                } else {
                    tsb.setText("0");
                }
            }
        }

        String EventiPrimoTempo = DatiPartitaGen[39];
        String EventiSecondoTempo = DatiPartitaGen[40];
        String EventiTerzoTempo = DatiPartitaGen[41];

        vnp.setEventiPrimoTempo(new ArrayList<String>());
        if (!EventiPrimoTempo.isEmpty()) {
            String[] e1 = EventiPrimoTempo.split("%", -1);
            for (String e2 : e1) {
                if (!e2.isEmpty()) {
                    e2=e2.replace("!",";");
                    vnp.getEventiPrimoTempo().add(e2);
                }
            }
            int quale = VariabiliStaticheNuovaPartita.getInstance().getQualeTempoEvento();
            VariabiliStaticheNuovaPartita.getInstance().setQualeTempoEvento(1);
            fillSpinnerEventiTempi();
            VariabiliStaticheNuovaPartita.getInstance().setQualeTempoEvento(quale);
        }

        vnp.setEventiSecondoTempo(new ArrayList<String>());
        if (!EventiSecondoTempo.isEmpty()) {
            String[] e1 = EventiSecondoTempo.split("%", -1);
            for (String e2 : e1) {
                if (!e2.isEmpty()) {
                    e2=e2.replace("!",";");
                    vnp.getEventiSecondoTempo().add(e2);
                }
            }
            int quale = VariabiliStaticheNuovaPartita.getInstance().getQualeTempoEvento();
            VariabiliStaticheNuovaPartita.getInstance().setQualeTempoEvento(2);
            fillSpinnerEventiTempi();
            VariabiliStaticheNuovaPartita.getInstance().setQualeTempoEvento(quale);
        }

        vnp.setEventiTerzoTempo(new ArrayList<String>());
        if (!EventiTerzoTempo.isEmpty()) {
            String[] e1 = EventiTerzoTempo.split("%", -1);
            for (String e2 : e1) {
                if (!e2.isEmpty()) {
                    e2=e2.replace("!",";");
                    vnp.getEventiTerzoTempo().add(e2);
                }
            }
            int quale = VariabiliStaticheNuovaPartita.getInstance().getQualeTempoEvento();
            VariabiliStaticheNuovaPartita.getInstance().setQualeTempoEvento(3);
            fillSpinnerEventiTempi();
            VariabiliStaticheNuovaPartita.getInstance().setQualeTempoEvento(quale);
        }

        VariabiliStaticheGlobali.getInstance().ApreDialogWS=false;
        vnp.AllenatorePerRicarica=DatiPartitaGen[15];
        DBRemotoAllenatori dbr = new DBRemotoAllenatori();
        dbr.RitornaAllenatoriCategoria(VariabiliStaticheGlobali.getInstance().getContext(),
                Integer.toString(vnp.idCategoriaScelta),
                TAG);
    }

    public static void fillSpinnerAllenatori() {
        final VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();
        if (vnp.getAllenatore() != null) {
            // Carica allenatori nella lista
            final ArrayAdapter<String> adapterAllenatori = new ArrayAdapter<String>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_piccolissimo, vnp.getAllenatore());
            adapterAllenatori.setDropDownViewResource(R.layout.spinner_item_piccolissimo);
            vnp.getSpnAllenatore().setAdapter(adapterAllenatori);
            // Carica allenatori nella lista

            vnp.getSpnAllenatore().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    vnp.idAllenatoreScelto = vnp.getIdAllenatore().get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            if (PartitaNuova!=-1) {
                int pos =  Utility.getInstance().CercaESettaStringaInSpinner(vnp.getSpnAllenatore(),
                        vnp.AllenatorePerRicarica);
                if (pos>-1) {
                    vnp.getSpnAllenatore().setSelection(pos);
                }

                DBRemotoGiocatori dbr = new DBRemotoGiocatori();
                dbr.RitornaGiocatoriCategoria(VariabiliStaticheGlobali.getInstance().getContext(),
                        Integer.toString(vnp.idCategoriaScelta),
                        TAG);
            }
        }
    }

    public static void fillSpinnerDirigenti() {
        if (VariabiliStaticheNuovaPartita.getInstance().getDirigente() != null) {
            VariabiliStaticheNuovaPartita.getInstance().setAdapterDirigentiDaConvocare(new AdapterDirigenti(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, VariabiliStaticheNuovaPartita.getInstance().getDirigente()));
            VariabiliStaticheNuovaPartita.getInstance().getSpnDirigenti().setAdapter(VariabiliStaticheNuovaPartita.getInstance().getAdapterDirigentiDaConvocare());
        }

        RiempieListaDirigentiSelezionati();
    }

    public static void RiempieListaDirigentiSelezionati() {
        if (VariabiliStaticheNuovaPartita.getInstance().getDirigenteSelezionato() != null) {
            VariabiliStaticheNuovaPartita.getInstance().setAdapterDirigenti(new AdapterDirigentiConvocati(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, VariabiliStaticheNuovaPartita.getInstance().getDirigenteSelezionato()));
            VariabiliStaticheNuovaPartita.getInstance().getSpnDirigentiConvocati().setAdapter(VariabiliStaticheNuovaPartita.getInstance().getAdapterDirigenti());
        }

        VariabiliStaticheNuovaPartita.getInstance().getSpnDirigentiConvocati().invalidate();
    }

    public static void fillSpinnerArbitri() {
        final VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();
        if (vnp.getArbitro() != null) {
            // Carica allenatori nella lista
            final ArrayAdapter<String> adapterArbitri = new ArrayAdapter<String>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_piccolo, vnp.getArbitro());
            adapterArbitri.setDropDownViewResource(R.layout.spinner_item_piccolo);
            vnp.getSpnArbitro().setAdapter(adapterArbitri);
            // Carica allenatori nella lista

            vnp.getSpnArbitro().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    vnp.idArbitroScelto = vnp.getIdArbitro().get(position);
                    vnp.getTxtArbitro().setText(VariabiliStaticheNuovaPartita.getInstance().getArbitro().get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            /* if (PartitaNuova!=-1) {
                int pos =  Utility.getInstance().CercaESettaStringaInSpinner(vnp.getSpnAllenatore(),
                        vnp.AllenatorePerRicarica);
                if (pos>-1) {
                    vnp.getSpnAllenatore().setSelection(pos);
                }

                DBRemotoGiocatori dbr = new DBRemotoGiocatori();
                dbr.RitornaGiocatoriCategoria(VariabiliStaticheGlobali.getInstance().getContext(),
                        Integer.toString(vnp.idCategoriaScelta),
                        TAG);
            } */
        }
    }

    public static void fillSpinnerMinutiGoalAvversari() {
        final VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();

        vnp.setAdapterTempiGAvvPrimoTempo(new AdapterMinutiGoalAvversari(VariabiliStaticheGlobali.getInstance().getContext(),
                android.R.layout.simple_list_item_1, vnp.getTempiGAvvPrimoTempo()));
        vnp.getLvTempiGAvvPrimoTempo().setAdapter(vnp.getAdapterTempiGAvvPrimoTempo());

        vnp.setAdapterTempiGAvvSecondoTempo(new AdapterMinutiGoalAvversari(VariabiliStaticheGlobali.getInstance().getContext(),
                android.R.layout.simple_list_item_1, vnp.getTempiGAvvSecondoTempo()));
        vnp.getLvTempiGAvvSecondoTempo().setAdapter(vnp.getAdapterTempiGAvvSecondoTempo());

        vnp.setAdapterTempiGAvvTerzoTempo(new AdapterMinutiGoalAvversari(VariabiliStaticheGlobali.getInstance().getContext(),
                android.R.layout.simple_list_item_1, vnp.getTempiGAvvTerzoTempo()));
        vnp.getLvTempiGAvvTerzoTempo().setAdapter(vnp.getAdapterTempiGAvvTerzoTempo());

        vnp.getLvTempiGAvvPrimoTempo().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Integer m = vnp.getTempiGAvvPrimoTempo().get(position);

                AggiungeGoal(true, Integer.toString(m), position, 1);
                // DialogMinutoDelGoal.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                //         "Minuto del goal", false, "Inserimento valore per goal n° " + (position+1),
                //         position, 1, Integer.toString(m), true);
            }

        });

        vnp.getLvTempiGAvvSecondoTempo().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Integer m= vnp.getTempiGAvvSecondoTempo().get(position);

                AggiungeGoal(true, Integer.toString(m), position, 2);
                // DialogMinutoDelGoal.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                //         "Minuto del goal", false, "Inserimento valore per goal n° " + (position+1),
                //         position, 2, Integer.toString(m), true);
            }

        });

        vnp.getLvTempiGAvvTerzoTempo().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                Integer m= vnp.getTempiGAvvTerzoTempo().get(position);

                AggiungeGoal(true, Integer.toString(m), position, 3);
                // DialogMinutoDelGoal.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                //         "Minuto del goal", false, "Inserimento valore per goal n° " + (position+1),
                //         position, 3, Integer.toString(m), true);
            }

        });

    }

    private static void AggiungeGoal(boolean Avversari, String minuto, int position, int tempo) {
        String m = "";

        if (minuto.isEmpty()) {
            minuto="0";
        }

        if (!Avversari) {
            m = VariabiliStaticheNuovaPartita.getInstance().getGiocatoriPerMarcature().get(position); //  + ";" + minuto + ";";
            if (m.contains(";")) {
                String mm[] = m.split(";");

                mm[4] = minuto;
                m = "";
                for (String mmm : mm) {
                    m += mmm + ";";
                }
            } else {
                if (m.equals("Autorete")) {
                    m="999;;Autorete;;"+minuto+";;";
                }
            }
        }
        switch(tempo)  {
            case 1:
                if (!Avversari) {
                    VariabiliStaticheNuovaPartita.getInstance().getListMarcPrimoTempo().add(m);
                    VariabiliStaticheNuovaPartita.getInstance().getAdapterLvPrimoTempo().notifyDataSetChanged();
                } else {
                    VariabiliStaticheNuovaPartita.getInstance().getTempiGAvvPrimoTempo().set(position, Integer.parseInt(minuto));
                }
                break;
            case 2:
                if (!Avversari) {
                    VariabiliStaticheNuovaPartita.getInstance().getListMarcSecondoTempo().add(m);
                    VariabiliStaticheNuovaPartita.getInstance().getAdapterLvSecondoTempo().notifyDataSetChanged();
                } else {
                    VariabiliStaticheNuovaPartita.getInstance().getTempiGAvvSecondoTempo().set(position, Integer.parseInt(minuto));
                }
                break;
            case 3:
                if (!Avversari) {
                    VariabiliStaticheNuovaPartita.getInstance().getListMarcTerzoTempo().add(m);
                    VariabiliStaticheNuovaPartita.getInstance().getAdapterLvTerzoTempo().notifyDataSetChanged();
                } else {
                    VariabiliStaticheNuovaPartita.getInstance().getTempiGAvvTerzoTempo().set(position, Integer.parseInt(minuto));
                }
                break;
        }

        if (!Avversari) {
            ScriveRisultato();
        } else {
            fillSpinnerMinutiGoalAvversari();
        }
    }

    public static void fillSpinnerConvocati(List<String> GiocatoreNC, List<Integer> idGiocatore, List<String> Ruolo,
                                            List<Integer> idRuolo, List<Integer> NumeroMaglia, boolean PerRicerca) {
        final VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();

        if (GiocatoreNC != null) {
            vnp.setGiocatoreDaConvocare(new ArrayList<String>());
            vnp.setGiocatoreDaConvocareRigori(new ArrayList<String>());
            vnp.setEventiGiocatori(new ArrayList<String>());

            int i=0;
            for (String s : GiocatoreNC) {
                vnp.getGiocatoreDaConvocare().add(
                        idGiocatore.get(i)+";"+
                                Ruolo.get(i)+";"+s+";;;"+
                                NumeroMaglia.get(i) + ";");
                vnp.getEventiGiocatori().add(
                        idGiocatore.get(i)+";"+
                                Ruolo.get(i)+";"+s+";;;"+
                                NumeroMaglia.get(i) + ";");
                vnp.getGiocatoreDaConvocareRigori().add(
                        idGiocatore.get(i)+";"+
                                Ruolo.get(i)+";"+s+";;;"+
                                NumeroMaglia.get(i) + ";"+
                                "-1;");
                i++;
            }

            if (!PerRicerca) {
                vnp.setGiocatoreConvocato(new ArrayList<String>());
                if (vnp.getGiocatoreConvocatoRigori()==null) {
                    vnp.setGiocatoreConvocatoRigori(new ArrayList<String>());
                }
                if (vnp.getEventiGiocatori()==null) {
                    vnp.setEventiGiocatori(new ArrayList<String>());
                }
            }

            // Carica giocatori nella lista
            vnp.setAdapterGiocatoriDaConvocare(new AdapterGiocatore(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, vnp.getGiocatoreDaConvocare()));
            vnp.getSpnDaConvocare().setAdapter(vnp.getAdapterGiocatoriDaConvocare());
            if (PerRicerca) {
                vnp.getSpnDaConvocare().invalidate();
            }

            // Carica giocatori per rigori nella lista
            vnp.setAdapterGiocatoriDaConvocareRigori(new AdapterGiocatore(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, vnp.getGiocatoreDaConvocareRigori()));
            vnp.getSpnDaConvocareRigori().setAdapter(vnp.getAdapterGiocatoriDaConvocareRigori());
            if (PerRicerca) {
                vnp.getSpnDaConvocareRigori().invalidate();
            }

            if (!PerRicerca) {
                vnp.setAdapterGiocatoriConvocati(new AdapterGiocatore(VariabiliStaticheGlobali.getInstance().getContext(),
                        android.R.layout.simple_list_item_1, vnp.getGiocatoreConvocato()));
                vnp.getSpnConvocati().setAdapter(vnp.getAdapterGiocatoriConvocati());

                vnp.setAdapterGiocatoriConvocatiRigori(new AdapterGiocatoreRigori(VariabiliStaticheGlobali.getInstance().getContext(),
                        android.R.layout.simple_list_item_1, vnp.getGiocatoreConvocatoRigori()));
                vnp.getSpnConvocatiRigori().setAdapter(vnp.getAdapterGiocatoriConvocatiRigori());

                vnp.setGiocatoriPerMarcature(new ArrayList<String>());
                vnp.getGiocatoriPerMarcature().add("Autorete");
                vnp.getGiocatoriPerMarcature().addAll(vnp.getGiocatoreConvocato());

                vnp.setAdapterMarcatoriPrimoTempo(new AdapterGiocatore(VariabiliStaticheGlobali.getInstance().getContext(),
                        R.layout.listview_giocatore, vnp.getGiocatoriPerMarcature()));
                vnp.getSpnMarcatoriPrimoTempo().setAdapter(vnp.getAdapterMarcatoriPrimoTempo());

                vnp.setAdapterMarcatoriSecondoTempo(new AdapterGiocatore(VariabiliStaticheGlobali.getInstance().getContext(),
                        android.R.layout.simple_list_item_1, vnp.getGiocatoriPerMarcature()));
                vnp.getSpnMarcatoriSecondoTempo().setAdapter(vnp.getAdapterMarcatoriSecondoTempo());

                vnp.setAdapterMarcatoriTerzoTempo(new AdapterGiocatore(VariabiliStaticheGlobali.getInstance().getContext(),
                        android.R.layout.simple_list_item_1, vnp.getGiocatoriPerMarcature()));
                vnp.getSpnMarcatoriTerzoTempo().setAdapter(vnp.getAdapterMarcatoriTerzoTempo());

                vnp.getSpnDaConvocare().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        ModificaEffettuata = false;

                        String s= vnp.getGiocatoreDaConvocare().get(position);
                        boolean Ok = true;
                        for (String ss : vnp.getGiocatoreConvocato()) {
                            if (s.equals(ss)) {
                                Ok=false;
                                break;
                            }
                        }

                        if (Ok) {
                            vnp.getGiocatoreConvocato().add(s);
                            vnp.getGiocatoreDaConvocare().remove(position);
                            vnp.getGiocatoriPerMarcature().add(s);

                            vnp.getAdapterGiocatoriConvocati().notifyDataSetChanged();
                            vnp.getAdapterGiocatoriDaConvocare().notifyDataSetChanged();
                            vnp.getAdapterMarcatoriPrimoTempo().notifyDataSetChanged();
                            vnp.getAdapterMarcatoriSecondoTempo().notifyDataSetChanged();
                            vnp.getAdapterMarcatoriTerzoTempo().notifyDataSetChanged();

                            vnp.getTxtConvocati().setText(Integer.toString(vnp.getGiocatoreConvocato().size()));
                        } else {
                            DialogMessaggio.getInstance().show(VariabiliStaticheNuovaPartita.getInstance().getContext(),
                                    "Giocatore già in lista",true, VariabiliStaticheGlobali.NomeApplicazione);
                        }
                    }
                });

                vnp.getSpnConvocati().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        ModificaEffettuata = false;

                        String s = vnp.getGiocatoreConvocato().get(position);
                        vnp.getGiocatoreDaConvocare().add(s);
                        vnp.getGiocatoreConvocato().remove(position);
                        vnp.getGiocatoriPerMarcature().remove(s);

                        vnp.getAdapterGiocatoriConvocati().notifyDataSetChanged();
                        vnp.getAdapterGiocatoriDaConvocare().notifyDataSetChanged();
                        vnp.getAdapterMarcatoriPrimoTempo().notifyDataSetChanged();
                        vnp.getAdapterMarcatoriSecondoTempo().notifyDataSetChanged();
                        vnp.getAdapterMarcatoriTerzoTempo().notifyDataSetChanged();

                        vnp.getTxtConvocati().setText(Integer.toString(vnp.getGiocatoreConvocato().size()));
                    }

                });
                // Carica giocatori nella lista

                vnp.getSpnDaConvocareRigori().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        ModificaEffettuata = false;

                        String s= vnp.getGiocatoreDaConvocareRigori().get(position);
                        boolean Ok = true;
                        for (String ss : vnp.getGiocatoreConvocatoRigori()) {
                            if (s.equals(ss)) {
                                Ok=false;
                                break;
                            }
                        }

                        if (Ok) {
                            vnp.getGiocatoreConvocatoRigori().add(s);
                            vnp.getGiocatoreDaConvocareRigori().remove(position);

                            vnp.getAdapterGiocatoriConvocatiRigori().notifyDataSetChanged();
                            vnp.getAdapterGiocatoriDaConvocareRigori().notifyDataSetChanged();
                        } else {
                            DialogMessaggio.getInstance().show(VariabiliStaticheNuovaPartita.getInstance().getContext(),
                                    "Giocatore già in lista",true, VariabiliStaticheGlobali.NomeApplicazione);
                        }
                    }
                });

                vnp.getSpnConvocatiRigori().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> parent, final View sview, int position, long id) {
                         ModificaEffettuata = false;

                         View view = vnp.getViewActivity();
                         String s = vnp.getGiocatoreConvocatoRigori().get(position);
                         String[] c = s.split(";", -1);

                         final CheckBox r1 = view.findViewById(R.id.chkDaTirare);
                         final CheckBox r2 = view.findViewById(R.id.chkSegnato);
                         final CheckBox r3 = view.findViewById(R.id.chkSbagliato);

                         switch(c[6]) {
                             case "-1":
                                 r1.setChecked(true);
                                 r2.setChecked(false);
                                 r3.setChecked(false);
                                 break;
                             case "1":
                                 r1.setChecked(false);
                                 r2.setChecked(true);
                                 r3.setChecked(false);
                                 break;
                             case "0":
                                 r1.setChecked(false);
                                 r2.setChecked(false);
                                 r3.setChecked(true);
                                 break;
                         }
                         r1.setOnClickListener(new View.OnClickListener() {
                             public void onClick(View v) {
                                 if (r1.isChecked()) {
                                     r2.setChecked(false);
                                     r3.setChecked(false);
                                 }
                             }
                         });
                         r2.setOnClickListener(new View.OnClickListener() {
                             public void onClick(View v) {
                                 if (r2.isChecked()) {
                                     r1.setChecked(false);
                                     r3.setChecked(false);
                                 }
                             }
                         });
                         r3.setOnClickListener(new View.OnClickListener() {
                             public void onClick(View v) {
                                 if (r3.isChecked()) {
                                     r1.setChecked(false);
                                     r2.setChecked(false);
                                 }
                             }
                         });

                         vnp.setGiocatoreSelezionatoRigore(position);

                         GestisceSchermate("RIGORI");

                         TextView txtRigore = view.findViewById(R.id.txtRigoreTiratoDa);
                         txtRigore.setText("Rigore di " + c[2]);

                     }
                });

                Button cmdOkRigore = vnp.getViewActivity().findViewById(R.id.cmdOkRigore);
                cmdOkRigore.setOnClickListener(new View.OnClickListener() {
                     public void onClick(View v) {
                         View view = vnp.getViewActivity();
                         view.findViewById(R.id.idRigori).setVisibility(View.GONE);
                         if (vnp.getGiocatoreSelezionatoRigore()>-1) {
                             int posizione = vnp.getGiocatoreSelezionatoRigore();
                             String s = vnp.getGiocatoreConvocatoRigori().get(posizione);
                             String[] c = s.split(";", -1);
                             String rigore = "";
                             CheckBox r1 = view.findViewById(R.id.chkDaTirare);
                             CheckBox r2 = view.findViewById(R.id.chkSegnato);
                             CheckBox r3 = view.findViewById(R.id.chkSbagliato);
                             if (r1.isChecked()) {
                                 rigore="-1";
                             } else {
                                 if (r2.isChecked()) {
                                     rigore="1";
                                 } else {
                                     if (r3.isChecked()) {
                                         rigore = "0";
                                     } else {
                                         rigore ="-1";
                                     }
                                 }
                             }
                             String gioc = "";
                             int pos = 0;
                             for (String cc : c) {
                                 if (pos!=6) {
                                     gioc += cc+";";
                                 } else {
                                     gioc += rigore+";";
                                 }

                                 pos++;
                             }
                             vnp.getGiocatoreConvocatoRigori().set(posizione, gioc);
                             vnp.getAdapterGiocatoriConvocatiRigori().notifyDataSetChanged();

                             // 448;Centrocampista;Cataldi Lorenzo;;;14;-1;

                             vnp.setGiocatoreSelezionatoRigore(-1);

                             ScriveRisultato();

                             VariabiliStaticheNuovaPartita.getInstance().getRlMaschera().setVisibility(LinearLayout.GONE);
                             VariabiliStaticheNuovaPartita.getInstance().getLlContenuto().setEnabled(true);
                         }
                     }
                 });
                Button cmdAnnullaRigore = vnp.getViewActivity().findViewById(R.id.cmdAnnullaRigore);
                cmdAnnullaRigore.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        View view = vnp.getViewActivity();
                        view.findViewById(R.id.idRigori).setVisibility(View.GONE);
                        vnp.setGiocatoreSelezionatoRigore(-1);

                        VariabiliStaticheNuovaPartita.getInstance().getRlMaschera().setVisibility(LinearLayout.GONE);
                        VariabiliStaticheNuovaPartita.getInstance().getLlContenuto().setEnabled(true);
                    }
                });

                vnp.getSpnConvocatiRigori().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                   int pos, long id) {
                        ModificaEffettuata = false;

                        String s = vnp.getGiocatoreConvocatoRigori().get(pos);
                        vnp.getGiocatoreDaConvocareRigori().add(s);
                        vnp.getGiocatoreConvocatoRigori().remove(pos);

                        vnp.getAdapterGiocatoriConvocatiRigori().notifyDataSetChanged();
                        vnp.getAdapterGiocatoriDaConvocareRigori().notifyDataSetChanged();

                        return true;
                    }
                });

                final String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

                vnp.getSpnMarcatoriPrimoTempo().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            ModificaEffettuata = false;

                            int minuto = TimerTempo.getInstance().RitornaMinuto(1);
                            String min = "";
                            if (minuto > 0) {
                                min = Integer.toString(minuto);
                            }

                            AggiungeGoal(false, min, position, 1);
                            // DialogMinutoDelGoal.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                            //         "Minuto del goal", false, "Inserimento valore", position, 1, min, false);
                        }
                    }
                });
                vnp.getSpnMarcatoriPrimoTempo().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            ModificaEffettuata = false;

                            v.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        return false;
                    }
                });

                vnp.getSpnMarcatoriSecondoTempo().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            ModificaEffettuata = false;

                            int minuto = TimerTempo.getInstance().RitornaMinuto(2);
                            String min = "";
                            if (minuto > 0) {
                                min = Integer.toString(minuto);
                            }

                            AggiungeGoal(false, min, position, 2);
                            // DialogMinutoDelGoal.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                            //         "Minuto del goal", false, "Inserimento valore", position, 2, min, false);
                        }
                    }
                });
                vnp.getSpnMarcatoriSecondoTempo().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            ModificaEffettuata = false;

                            v.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        return false;
                    }
                });

                vnp.getSpnMarcatoriTerzoTempo().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            ModificaEffettuata = false;

                            int minuto = TimerTempo.getInstance().RitornaMinuto(3);
                            String min = "";
                            if (minuto > 0) {
                                min = Integer.toString(minuto);
                            }

                            AggiungeGoal(false, min, position, 3);
                            // DialogMinutoDelGoal.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                            //         "Minuto del goal", false, "Inserimento valore", position, 3, min, false);
                        }
                    }
                });
                vnp.getSpnMarcatoriTerzoTempo().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            ModificaEffettuata = false;

                            v.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        return false;
                    }
                });

                vnp.setListMarcPrimoTempo(new ArrayList<String>());
                vnp.setListMarcSecondoTempo(new ArrayList<String>());
                vnp.setListMarcTerzoTempo(new ArrayList<String>());

                vnp.setAdapterLvPrimoTempo(new AdapterGiocatore(VariabiliStaticheGlobali.getInstance().getContext(),
                        android.R.layout.simple_list_item_1, vnp.getListMarcPrimoTempo()));
                vnp.getLvvMarcatoriPrimoTempo().setAdapter(vnp.getAdapterLvPrimoTempo());
                vnp.getLvvMarcatoriPrimoTempo().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            ModificaEffettuata = false;

                            vnp.getListMarcPrimoTempo().remove(position);
                            vnp.getAdapterLvPrimoTempo().notifyDataSetChanged();
                            ScriveRisultato();
                        }
                    }
                });
                vnp.getLvvMarcatoriPrimoTempo().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            ModificaEffettuata = false;

                            v.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        return false;
                    }
                });

                vnp.setAdapterLvSecondoTempo(new AdapterGiocatore(VariabiliStaticheGlobali.getInstance().getContext(),
                        android.R.layout.simple_list_item_1, vnp.getListMarcSecondoTempo()));
                vnp.getLvvMarcatoriSecondoTempo().setAdapter(vnp.getAdapterLvSecondoTempo());
                vnp.getLvvMarcatoriSecondoTempo().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            ModificaEffettuata = false;

                            vnp.getListMarcSecondoTempo().remove(position);
                            vnp.getAdapterLvSecondoTempo().notifyDataSetChanged();
                            ScriveRisultato();
                        }
                    }
                });
                vnp.getLvvMarcatoriSecondoTempo().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            ModificaEffettuata = false;

                            v.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        return false;
                    }
                });

                vnp.setAdapterLvTerzoTempo(new AdapterGiocatore(VariabiliStaticheGlobali.getInstance().getContext(),
                        android.R.layout.simple_list_item_1, vnp.getListMarcTerzoTempo()));
                vnp.getLvvMarcatoriTerzoTempo().setAdapter(vnp.getAdapterLvTerzoTempo());
                vnp.getLvvMarcatoriTerzoTempo().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            ModificaEffettuata = false;

                            vnp.getListMarcTerzoTempo().remove(position);
                            vnp.getAdapterLvTerzoTempo().notifyDataSetChanged();
                            ScriveRisultato();
                        }
                    }
                });
                vnp.getLvvMarcatoriTerzoTempo().setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            ModificaEffettuata = false;

                            v.getParent().requestDisallowInterceptTouchEvent(true);
                        }
                        return false;
                    }
                });

                if (PartitaNuova != -1) {
                    VariabiliStaticheGlobali.getInstance().ApreDialogWS = true;

                    if (vnp.ConvocatiPerRicarica != null && !vnp.ConvocatiPerRicarica.isEmpty()) {
                        String[] Convocati = vnp.ConvocatiPerRicarica.split("§");

                        for (String c : Convocati) {
                            if (!c.isEmpty()) {
                                String[] DatiConvocati = c.split(";", -1);

                                String s = DatiConvocati[1] + ";" +
                                        DatiConvocati[4] + ";" +
                                        DatiConvocati[2] + " " + DatiConvocati[3] + ";;;" +
                                        DatiConvocati[6] + ";";
                                vnp.getGiocatoreConvocato().add(s);
                                vnp.getGiocatoriPerMarcature().add(s);
                            }
                        }
                    }

                    if (vnp.MarcatoriPerRicarica != null && !vnp.MarcatoriPerRicarica.isEmpty()) {
                        String[] Marcatori = vnp.MarcatoriPerRicarica.split("§");

                        for (String m : Marcatori) {
                            if (!m.isEmpty()) {
                                String[] DatiMarcatori = m.split(";", -1);

                                String s = DatiMarcatori[2] + ";" +
                                        DatiMarcatori[6] + ";" +
                                        DatiMarcatori[4] + " " + DatiMarcatori[5] + ";;" +
                                        DatiMarcatori[3] + ";" + DatiMarcatori[7] + ";";

                                switch (DatiMarcatori[0]) {
                                    case "1":
                                        vnp.getListMarcPrimoTempo().add(s);
                                        break;
                                    case "2":
                                        vnp.getListMarcSecondoTempo().add(s);
                                        break;
                                    case "3":
                                        vnp.getListMarcTerzoTempo().add(s);
                                        break;
                                }
                            }
                        }
                    }
                }

                vnp.getAdapterGiocatoriConvocati().notifyDataSetChanged();
                vnp.getAdapterMarcatoriPrimoTempo().notifyDataSetChanged();
                vnp.getAdapterMarcatoriSecondoTempo().notifyDataSetChanged();
                vnp.getAdapterMarcatoriTerzoTempo().notifyDataSetChanged();

                vnp.getAdapterLvPrimoTempo().notifyDataSetChanged();
                vnp.getAdapterLvSecondoTempo().notifyDataSetChanged();
                vnp.getAdapterLvTerzoTempo().notifyDataSetChanged();
            }
            // Refresh liste

            vnp.getAdapterGiocatoriDaConvocare().notifyDataSetChanged();

            if (!PerRicerca) {
                vnp.getTxtConvocati().setText(Integer.toString(vnp.getGiocatoreConvocato().size()));
                // Refresh liste

                String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    vnp.getCmdSalva().setVisibility(LinearLayout.VISIBLE);
                }
                vnp.getCmdUscita().setVisibility(LinearLayout.VISIBLE);

                ScriveRisultato();
            }

            fillSpinnerEventiGiocatori();
        }
    }

    public static void fillSpinnersCategorie() {
        final VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();
        if (vnp.getDescrizioneCategorie() != null) {
            // Carica categorie nella lista
            final ArrayAdapter<String> adapterCategorie = new ArrayAdapter<>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_per_categorie, vnp.getDescrizioneCategorie());
            adapterCategorie.setDropDownViewResource(R.layout.spinner_item_per_categorie);
            vnp.getSpnCategorie().setAdapter(adapterCategorie);

            vnp.getSpnCategorie().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    vnp.idCategoriaScelta = vnp.getIdCategorie().get(position);
                    vnp.getTxtCategoria().setText(adapterCategorie.getItem(position));

                    //DBRemotoGenerale dbr = new DBRemotoGenerale();
                    //dbr.RitornaAllenatoriCategoria(VariabiliStaticheGlobali.getInstance().getContext(),
                    //        Integer.toString(vnp.idCategoriaScelta1),
                    //        TAG);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            // Carica categorie nella lista
        }
    }

    public static void fillSpinnersTipologie() {
        final VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();
        if (vnp.getDescrizioneTipologie() != null) {
            // Carica tipologie nella lista
            final ArrayAdapter<String> adapterTipologie = new ArrayAdapter<>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_per_categorie, vnp.getDescrizioneTipologie());
            adapterTipologie.setDropDownViewResource(R.layout.spinner_item_per_categorie);
            vnp.getSpnTipologie().setAdapter(adapterTipologie);
            // Carica tipologie nella lista

            vnp.getSpnTipologie().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    vnp.idTipologiaScelta = vnp.getIdTipologie().get(position);
                    vnp.getTxtTipologia().setText(adapterTipologie.getItem(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public static void fillSpinnersAvversari(List<String> Avv, final List<Integer> AvvID, final List<Integer> AvvCampoID, final List<String> AvvCampo, final List<String> AvvCampoIndirizzo) {
        final VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();
        if (Avv != null) {
            // Carica avversari nella lista
            final ArrayAdapter<String> adapterAvversari = new ArrayAdapter<>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_per_categorie, Avv);
            adapterAvversari.setDropDownViewResource(R.layout.spinner_item_per_categorie);
            vnp.getSpnAvversario().setAdapter(adapterAvversari);
            // Carica avversari nella lista

            vnp.getSpnAvversario().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    vnp.idAvversarioScelto = AvvID.get(position);
                    vnp.getTxtAvversario().setText(adapterAvversari.getItem(position));
                    vnp.idCampoScelto = AvvCampoID.get(position);
                    vnp.getTxtCampo().setText(AvvCampo.get(position));
                    vnp.getTxtCampoIndirizzo().setText(AvvCampoIndirizzo.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    private void pressCheckInCasa() {
        VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();

        if (vnp.getChkInCasa().isChecked()) {
            vnp.getTxtCampo().setVisibility(View.INVISIBLE);
            vnp.getTxtCampoIndirizzo().setVisibility(View.INVISIBLE);
        } else {
            vnp.getTxtCampo().setVisibility(View.VISIBLE);
            vnp.getTxtCampoIndirizzo().setVisibility(View.VISIBLE);
        }
    }

    private void pressCheckEsterno() {
        VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();

        if (vnp.getChkEsterno().isChecked()) {
            vnp.getChkInCasa().setVisibility(LinearLayout.INVISIBLE);
            vnp.getTxtCampo().setVisibility(View.INVISIBLE);
            vnp.getLayCampoEsterno().setVisibility(LinearLayout.VISIBLE);
            vnp.getLayCampoIndirizzo().setVisibility(LinearLayout.INVISIBLE);
        } else {
            vnp.getChkInCasa().setVisibility(LinearLayout.VISIBLE);
            vnp.getTxtCampo().setVisibility(View.VISIBLE);
            vnp.getLayCampoEsterno().setVisibility(LinearLayout.GONE);
            vnp.getLayCampoIndirizzo().setVisibility(LinearLayout.VISIBLE);
        }
    }

    public void ImpostaSchermata(final View view) {
        final VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();

        vnp.setRlMaschera((RelativeLayout) view.findViewById(R.id.layMascheraModUtenti));
        vnp.setLlContenuto((LinearLayout) view.findViewById(R.id.layContenuto));

        LinearLayout layHeader=view.findViewById(R.id.layHeader);
        LinearLayout layPTHeader=view.findViewById(R.id.layHeaderPrimoTempo);
        LinearLayout laySTHeader=view.findViewById(R.id.layHeaderSecondoTempo);
        LinearLayout layTTHeader=view.findViewById(R.id.layHeaderTerzoTempo);
        // RelativeLayout layMascheraModUtenti=view.findViewById(R.id.layMascheraModUtenti);
        LinearLayout layTasti=view.findViewById(R.id.layBarraTasti);
        TextView txtMarcPtTit = view.findViewById(R.id.txtMarcPTTit);
        TextView txtMarcStTit = view.findViewById(R.id.txtMarcSTTit);
        TextView txtMarcTtTit = view.findViewById(R.id.txtMarcTTTit);
        TextView txtPrimoTempo = view.findViewById(R.id.txtPrimoTempo);
        TextView txtSecondoTempo = view.findViewById(R.id.txtSecondoTempo);
        TextView txtTerzoTempo = view.findViewById(R.id.txtTerzoTempo);

        CheckBox c = view.findViewById(R.id.chkTempi);
        VariabiliStaticheNuovaPartita.getInstance().setChkTempi(c);
        VariabiliStaticheNuovaPartita.getInstance().getChkTempi().setChecked(true);

        VariabiliStaticheNuovaPartita.getInstance().getChkTempi().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    if (vnp.getChkTempi().isChecked()) {
                        vnp.getTxtTempi().setVisibility(LinearLayout.VISIBLE);
                    } else {
                        vnp.getTxtTempi().setVisibility(LinearLayout.GONE);
                    }
                    ScriveRisultato();
                }
            }
        });

        // rlMaschera.setBackgroundResource(R.drawable.trasparente);

        /* if (VariabiliStaticheMain.getInstance().getSquadra()!=null &&
                VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraCastelVerde)) {
            layHeader.setBackgroundResource(R.drawable.bordo_arrotondato_verde);
            layPTHeader.setBackgroundResource(R.drawable.bordo_arrotondato_verde);
            laySTHeader.setBackgroundResource(R.drawable.bordo_arrotondato_verde);
            layTTHeader.setBackgroundResource(R.drawable.bordo_arrotondato_verde);
            txtMarcPtTit.setBackgroundResource(R.drawable.bordo_arrotondato_verde);
            txtMarcStTit.setBackgroundResource(R.drawable.bordo_arrotondato_verde);
            txtMarcTtTit.setBackgroundResource(R.drawable.bordo_arrotondato_verde);
            // layMascheraModUtenti.setBackgroundResource(R.drawable.bordo_arrotondato_verde_chiaro);
            layTasti.setBackgroundResource(R.drawable.bordo_arrotondato_verde);
            txtPrimoTempo.setTextColor(Color.BLACK);
            txtSecondoTempo.setTextColor(Color.BLACK);
            txtTerzoTempo.setTextColor(Color.BLACK);
            txtMarcPtTit.setTextColor(Color.BLACK);
            txtMarcStTit.setTextColor(Color.BLACK);
            txtMarcTtTit.setTextColor(Color.BLACK);
        } else {
            if (VariabiliStaticheMain.getInstance().getSquadra()==null ||
                    VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraPonteDiNona)) {
                layHeader.setBackgroundResource(R.drawable.bordo_arrotondato_rosso);
                layPTHeader.setBackgroundResource(R.drawable.bordo_arrotondato_rosso);
                laySTHeader.setBackgroundResource(R.drawable.bordo_arrotondato_rosso);
                layTTHeader.setBackgroundResource(R.drawable.bordo_arrotondato_rosso);
                txtMarcPtTit.setBackgroundResource(R.drawable.bordo_arrotondato_rosso);
                txtMarcStTit.setBackgroundResource(R.drawable.bordo_arrotondato_rosso);
                txtMarcTtTit.setBackgroundResource(R.drawable.bordo_arrotondato_rosso);
                // layMascheraModUtenti.setBackgroundResource(R.drawable.bordo_arrotondato_rosso_chiaro);
                layTasti.setBackgroundResource(R.drawable.bordo_arrotondato_rosso);
                txtPrimoTempo.setTextColor(Color.WHITE);
                txtSecondoTempo.setTextColor(Color.WHITE);
                txtTerzoTempo.setTextColor(Color.WHITE);
                txtMarcPtTit.setTextColor(Color.WHITE);
                txtMarcStTit.setTextColor(Color.WHITE);
                txtMarcTtTit.setTextColor(Color.WHITE);
            }
        } */

        vnp.setSpnCategorie((Spinner) view.findViewById(R.id.spnCategoria));
        vnp.setSpnTipologie((Spinner) view.findViewById(R.id.spnTipologia));
        vnp.setSpnAvversario((Spinner) view.findViewById(R.id.spnAvversario));
        vnp.setSpnAllenatore((Spinner) view.findViewById(R.id.spnAllenatore));
        vnp.setSpnArbitro((Spinner) view.findViewById(R.id.spnArbitro));
        vnp.setSpnConvocati((ListView) view.findViewById(R.id.spnConvocati));
        vnp.setSpnDaConvocare((ListView) view.findViewById(R.id.spnDaConvocare));
        vnp.setSpnConvocatiRigori((ListView) view.findViewById(R.id.spnConvocatiRigori));
        vnp.setSpnDaConvocareRigori((ListView) view.findViewById(R.id.spnDaConvocareRigori));
        vnp.setSpnDirigenti((ListView) view.findViewById(R.id.lstvDirigenti));
        vnp.setSpnDirigentiConvocati((ListView) view.findViewById(R.id.lstvDirigentiConvocati));
        vnp.setSpnMarcatoriPrimoTempo((ListView) view.findViewById(R.id.spnMarcatoriPrimoTempo));
        vnp.setSpnMarcatoriSecondoTempo((ListView) view.findViewById(R.id.spnMarcatoriSecondoTempo));
        vnp.setSpnMarcatoriTerzoTempo((ListView) view.findViewById(R.id.spnMarcatoriTerzoTempo));

        vnp.setLvvMarcatoriPrimoTempo((ListView) view.findViewById(R.id.lstvMarcatoriPrimoTempo));
        vnp.setLvvMarcatoriSecondoTempo((ListView) view.findViewById(R.id.lstvMarcatoriSecondoTempo));
        vnp.setLvvMarcatoriTerzoTempo((ListView) view.findViewById(R.id.lstvMarcatoriTerzoTempo));

        vnp.setLvTempiGAvvPrimoTempo((ListView) view.findViewById(R.id.lvTempiGAvvPrimoTempo));
        vnp.setLvTempiGAvvSecondoTempo((ListView) view.findViewById(R.id.lvTempiGAvvSecondoTempo));
        vnp.setLvTempiGAvvTerzoTempo((ListView) view.findViewById(R.id.lvTempiGAvvTerzoTempo));

        ImageView cmdCategoria = view.findViewById(R.id.btnCategoria);
        ImageView cmdData = view.findViewById(R.id.btnData);
        ImageView cmdOra = view.findViewById(R.id.btnOra);
        ImageView cmdAvversario = view.findViewById(R.id.btnAvversario);
        ImageView cmdTipologia = view.findViewById(R.id.btnTipologia);
        ImageView cmdConvocati = view.findViewById(R.id.btnConvocati);
        ImageView cmdMeteo = view.findViewById(R.id.btnMeteo);
        ImageView cmdAltroPartita = view.findViewById(R.id.btnAltroPartita);
        ImageView cmdWeb = view.findViewById(R.id.btnWeb);
        ImageView cmdArbitro = view.findViewById(R.id.btnArbitro);

        final String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

        // Eventi
        VariabiliStaticheNuovaPartita.getInstance().PulisceEvento();

        vnp.setEventiPrimoTempo(new ArrayList<String>());
        vnp.setEventiSecondoTempo(new ArrayList<String>());
        vnp.setEventiTerzoTempo(new ArrayList<String>());

        Button btnAvvenimentiPrimoTempo = view.findViewById(R.id.btnAvvenimentiPrimoTempo);
        final Button btnAvvenimentiSecondoTempo = view.findViewById(R.id.btnAvvenimentiSecondoTempo);
        final Button btnAvvenimentiTerzoTempo = view.findViewById(R.id.btnAvvenimentiTerzoTempo);

        Button btnOkEventi = view.findViewById(R.id.cmdOkEventi);
        Button btnAnnullaEventi = view.findViewById(R.id.cmdAnnullaEventi);
        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            btnOkEventi.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String descEvento=VariabiliStaticheNuovaPartita.getInstance().getDescEvento();
                    int idEvento=VariabiliStaticheNuovaPartita.getInstance().getIdEvento();
                    String MinutoEvento=VariabiliStaticheNuovaPartita.getInstance().getMinutoEvento();
                    String aFavoreDi=VariabiliStaticheNuovaPartita.getInstance().getaFavoreDi();
                    int idAFavore=VariabiliStaticheNuovaPartita.getInstance().getIdAFavore();

                    if (descEvento.isEmpty() || idEvento==-1 || MinutoEvento.isEmpty() ||
                        aFavoreDi.isEmpty() || idAFavore==-1) {
                        DialogMessaggio.getInstance().show(VariabiliStaticheNuovaPartita.getInstance().getContext(),
                                "Inserire tutti i dettagli dell'evento",
                                false,
                                VariabiliStaticheGlobali.NomeApplicazione);
                    } else {
                        String evento = MinutoEvento+";"+idEvento+";"+descEvento+";"+idAFavore+";"+ aFavoreDi+";";
                        int tempo = VariabiliStaticheNuovaPartita.getInstance().getQualeTempoEvento();
                        switch (tempo) {
                            case 1:
                                vnp.getEventiPrimoTempo().add(evento);
                                break;
                            case 2:
                                vnp.getEventiSecondoTempo().add(evento);
                                break;
                            case 3:
                                vnp.getEventiTerzoTempo().add(evento);
                                break;
                        }

                        fillSpinnerEventiTempi();

                        VariabiliStaticheNuovaPartita.getInstance().PulisceEvento();
                        ChiudeMaschera();
                    }
                }
            });
        }
        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            btnAnnullaEventi.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ChiudeMaschera();
                }
            });
        }

        vnp.setLvEventiPrimoTempo((ListView) view.findViewById(R.id.lstvEventiPrimoTempo));
        /* if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            vnp.getLvEventiPrimoTempo().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {
                    vnp.getEventiPrimoTempo().remove(pos);

                    int appo = vnp.getQualeTempoEvento();
                    vnp.setQualeTempoEvento(1);
                    fillSpinnerEventiTempi();
                    vnp.setQualeTempoEvento(appo);

                    return true;
                }
            });
        } */
        vnp.setLvEventiSecondoTempo((ListView) view.findViewById(R.id.lstvEventiSecondoTempo));
        /* if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            vnp.getLvEventiSecondoTempo().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {
                    vnp.getEventiSecondoTempo().remove(pos);

                    int appo = vnp.getQualeTempoEvento();
                    vnp.setQualeTempoEvento(2);
                    fillSpinnerEventiTempi();
                    vnp.setQualeTempoEvento(appo);

                    return true;

                }
            });
        } */
        vnp.setLvEventiTerzoTempo((ListView) view.findViewById(R.id.lstvEventiTerzoTempo));
        /* if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            vnp.getLvEventiTerzoTempo().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                               int pos, long id) {
                    vnp.getEventiTerzoTempo().remove(pos);

                    int appo = vnp.getQualeTempoEvento();
                    vnp.setQualeTempoEvento(3);
                    fillSpinnerEventiTempi();
                    vnp.setQualeTempoEvento(appo);

                    return true;
                }
            });
        } */

        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            btnAvvenimentiPrimoTempo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    vnp.PulisceEvento();

                    GestisceSchermate("EVENTI");
                }
            });
        }
        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            btnAvvenimentiSecondoTempo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    vnp.PulisceEvento();

                    GestisceSchermate("EVENTI");
                }
            });
        }
        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            btnAvvenimentiTerzoTempo.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    vnp.PulisceEvento();

                    GestisceSchermate("EVENTI");
                }
            });
        }

        vnp.setLvEventiGiocatori((ListView) view.findViewById(R.id.lvGiocatoreEventi));
        vnp.setLvEventiLista((ListView) view.findViewById(R.id.lvListaEventi));

        vnp.setTxtEvento((TextView) view.findViewById(R.id.txtEvento));

        final CheckBox chkAvversario = view.findViewById(R.id.chkAvversario);
        chkAvversario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (chkAvversario.isChecked()) {
                    vnp.getLvEventiGiocatori().setVisibility(LinearLayout.GONE);
                    VariabiliStaticheNuovaPartita.getInstance().setaFavoreDi("Avversario");
                    VariabiliStaticheNuovaPartita.getInstance().setIdAFavore(9999);
                    VariabiliStaticheNuovaPartita.getInstance().StampaEvento();
                } else {
                    vnp.getLvEventiGiocatori().setVisibility(LinearLayout.VISIBLE);
                    VariabiliStaticheNuovaPartita.getInstance().setaFavoreDi("");
                    VariabiliStaticheNuovaPartita.getInstance().setIdAFavore(-1);
                    VariabiliStaticheNuovaPartita.getInstance().StampaEvento();
                }
            }
        });
        // Eventi

        // Rigori
        final TextView txtSegnatiAvversario = view.findViewById(R.id.txtRigoriSegnAvversari);
        Button cmdMenoAvvSegnati = view.findViewById(R.id.cmdMenoRigoreSegnAvversari);
        cmdMenoAvvSegnati.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int quanti;
                if (!txtSegnatiAvversario.getText().toString().isEmpty()){
                    quanti = Integer.parseInt(txtSegnatiAvversario.getText().toString());
                } else {
                    quanti = 0;
                }
                if (quanti>0) {
                    quanti--;
                    txtSegnatiAvversario.setText(Integer.toString(quanti));
                    ModificaEffettuata = false;

                    ScriveRisultato();
                }
            }
        });
        Button cmdPiuAvvSegnati = view.findViewById(R.id.cmdPiuRigoreSegnAvversari);
        cmdPiuAvvSegnati.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int quanti;
                if (!txtSegnatiAvversario.getText().toString().isEmpty()){
                    quanti = Integer.parseInt(txtSegnatiAvversario.getText().toString());
                } else {
                    quanti = 0;
                }
                quanti++;
                txtSegnatiAvversario.setText(Integer.toString(quanti));
                ModificaEffettuata = false;

                ScriveRisultato();
            }
        });
        if (!idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            cmdMenoAvvSegnati.setEnabled(false);
            cmdPiuAvvSegnati.setEnabled(false);
        }
        txtSegnatiAvversario.setEnabled(false);

        final TextView txtSbagliatiAvversario = view.findViewById(R.id.txtRigoriSbaAvversari);
        Button cmdMenoAvvSbagliati = view.findViewById(R.id.cmdMenoRigoreSbaAvversari);
        cmdMenoAvvSbagliati.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int quanti;
                if (!txtSbagliatiAvversario.getText().toString().isEmpty()){
                    quanti = Integer.parseInt(txtSbagliatiAvversario.getText().toString());
                } else {
                    quanti = 0;
                }
                if (quanti>0) {
                    quanti--;
                    txtSbagliatiAvversario.setText(Integer.toString(quanti));
                    ModificaEffettuata = false;
                }
            }
        });
        Button cmdPiuAvvSbagliati = view.findViewById(R.id.cmdPiuRigoreSbaAvversari);
        cmdPiuAvvSbagliati.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int quanti;
                if (!txtSbagliatiAvversario.getText().toString().isEmpty()){
                    quanti = Integer.parseInt(txtSbagliatiAvversario.getText().toString());
                } else {
                    quanti = 0;
                }
                quanti++;
                txtSbagliatiAvversario.setText(Integer.toString(quanti));
                ModificaEffettuata = false;
            }
        });
        if (!idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            cmdMenoAvvSbagliati.setEnabled(false);
            cmdPiuAvvSbagliati.setEnabled(false);
        }
        txtSbagliatiAvversario.setEnabled(false);
        // Rigori

        Button cmdRefresh = view.findViewById(R.id.btnRefresh);
        cmdRefresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    ScriveRisultato();
                }
            }
        });

        Button cmdMenoAvv1Tempo = view.findViewById(R.id.btnMenoAvvPrimoTempo);
        Button cmdPiuAvv1Tempo = view.findViewById(R.id.btnPiuAvvPrimoTempo);
        final Button cmdMenoAvv2Tempo = view.findViewById(R.id.btnMenoAvvSecondoTempo);
        Button cmdPiuAvv2Tempo = view.findViewById(R.id.btnPiuAvvSecondoTempo);
        final Button cmdMenoAvv3Tempo = view.findViewById(R.id.btnMenoAvvTerzoTempo);
        Button cmdPiuAvv3Tempo = view.findViewById(R.id.btnPiuAvvTerzoTempo);
        final RelativeLayout layNoTerzoTempo = view.findViewById(R.id.layNoTerzoTempo);

        vnp.setTxtRisAvv1Tempo((TextView) view.findViewById(R.id.txtGoalAvvPrimoTempo));
        vnp.setTxtRisAvv2Tempo((TextView) view.findViewById(R.id.txtGoalAvvSecondoTempo));
        vnp.setTxtRisAvv3Tempo((TextView) view.findViewById(R.id.txtGoalAvvTerzoTempo));

        vnp.setTxtOra((TextView) view.findViewById(R.id.txtOra));
        vnp.setTxtData((TextView) view.findViewById(R.id.txtdata));
        vnp.setEdtGiochetti((EditText) view.findViewById(R.id.edtGiochetti));
        vnp.setEdtNote((EditText) view.findViewById(R.id.edtNote));

        vnp.setTxtGoal((TextView) view.findViewById(R.id.txtGoal));
        vnp.setTxtRigori((TextView) view.findViewById(R.id.txtRigori));
        vnp.setTxtTempi((TextView) view.findViewById(R.id.txtTempi));
        vnp.getTxtGoal().setText("");
        vnp.getTxtRigori().setText("");
        vnp.getTxtTempi().setText("");

        vnp.setTxtCampo((TextView) view.findViewById(R.id.txtCampo));
        vnp.setTxtCampoIndirizzo((TextView) view.findViewById(R.id.txtCampoIndirizzo));

        vnp.setTxtTimer1Tempo((TextView) view.findViewById(R.id.txtTimer1Tempo));
        vnp.setTxtTimer2Tempo((TextView) view.findViewById(R.id.txtTimer2Tempo));
        vnp.setTxtTimer3Tempo((TextView) view.findViewById(R.id.txtTimer3Tempo));

        /* if (VariabiliStaticheMain.getInstance().getSquadra()==null) {
            VariabiliStaticheMain.getInstance().setSquadra(VariabiliStaticheGlobali.NomeSquadraPonteDiNona);
        }
        if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraCastelVerde)) {
            vnp.getTxtTimer1Tempo().setTextColor(Color.BLACK);
            vnp.getTxtTimer2Tempo().setTextColor(Color.BLACK);
            vnp.getTxtTimer3Tempo().setTextColor(Color.BLACK);
        } else {
            vnp.getTxtTimer1Tempo().setTextColor(Color.WHITE);
            vnp.getTxtTimer2Tempo().setTextColor(Color.WHITE);
            vnp.getTxtTimer3Tempo().setTextColor(Color.WHITE);
        } */

        VariabiliStaticheNuovaPartita.getInstance().setDirigenteSelezionato(new ArrayList<String>());
        VariabiliStaticheNuovaPartita.getInstance().setIdDirigenteSelezionato(new ArrayList<Integer>());

        ImageView cmdCoordinate = view.findViewById(R.id.btnCoordinate);
        cmdCoordinate .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    View view = VariabiliStaticheNuovaPartita.getInstance().getViewActivity();
                    Utility.getInstance().PrendeCoordinateDaIndirizzo(view);

                    DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), "Indirizzo impostato",
                            false, VariabiliStaticheGlobali.NomeApplicazione);
                }
            }
        });

        vnp.getTxtTimer1Tempo().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    DialogTempo.getInstance().show(VariabiliStaticheGlobali.getInstance().getContextPrincipale(),
                            "Inserire il tempo",
                            false,
                            VariabiliStaticheGlobali.NomeApplicazione,
                            0,
                            1,
                            vnp.getTxtTimer1Tempo().getText().toString(),
                            vnp.getTxtTimer1Tempo()
                    );
                }
            }
        });

        vnp.getTxtTimer2Tempo().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    DialogTempo.getInstance().show(VariabiliStaticheGlobali.getInstance().getContextPrincipale(),
                            "Inserire il tempo",
                            false,
                            VariabiliStaticheGlobali.NomeApplicazione,
                            0,
                            2,
                            vnp.getTxtTimer2Tempo().getText().toString(),
                            vnp.getTxtTimer2Tempo()
                    );
                }
            }
        });

        vnp.getTxtTimer3Tempo().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    DialogTempo.getInstance().show(VariabiliStaticheGlobali.getInstance().getContextPrincipale(),
                            "Inserire il tempo",
                            false,
                            VariabiliStaticheGlobali.NomeApplicazione,
                            0,
                            3,
                            vnp.getTxtTimer3Tempo().getText().toString(),
                            vnp.getTxtTimer3Tempo()
                    );
                }
            }
        });

        vnp.setCmdReset1Tempo((ImageView) view.findViewById(R.id.btnReset1Tempo));
        vnp.setCmdReset2Tempo((ImageView) view.findViewById(R.id.btnReset2Tempo));
        vnp.setCmdReset3Tempo((ImageView) view.findViewById(R.id.btnReset3Tempo));

        vnp.getCmdReset1Tempo().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    TimerTempo.getInstance().ResetTempo(1, vnp.getTxtTimer1Tempo());
                }
            }
        });

        vnp.getCmdReset2Tempo().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    TimerTempo.getInstance().ResetTempo(2, vnp.getTxtTimer2Tempo());
                }
            }
        });

        vnp.getCmdReset3Tempo().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    TimerTempo.getInstance().ResetTempo(3, vnp.getTxtTimer3Tempo());
                }
            }
        });

        vnp.setCmdStartSopTimer1Tempo((ImageView) view.findViewById(R.id.btnStartStopTimer1Tempo));
        vnp.setCmdStartSopTimer2Tempo((ImageView) view.findViewById(R.id.btnStartStopTimer2Tempo));
        vnp.setCmdStartSopTimer3Tempo((ImageView) view.findViewById(R.id.btnStartStopTimer3Tempo));

        vnp.getCmdStartSopTimer1Tempo().setBackgroundResource(R.drawable.play);
        vnp.getCmdStartSopTimer2Tempo().setBackgroundResource(R.drawable.play);
        vnp.getCmdStartSopTimer3Tempo().setBackgroundResource(R.drawable.play);

        vnp.getCmdStartSopTimer1Tempo().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    if (TimerTempo.getInstance().getQualeTimerPartito() != 1) {
                        TimerTempo.getInstance().FaiPartireTimer(1, vnp.getTxtTimer1Tempo());

                        vnp.getCmdStartSopTimer1Tempo().setBackgroundResource(R.drawable.pausa);

                        vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.INVISIBLE);
                        vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.INVISIBLE);

                        vnp.getCmdReset1Tempo().setVisibility(LinearLayout.INVISIBLE);
                        vnp.getCmdReset2Tempo().setVisibility(LinearLayout.INVISIBLE);
                        vnp.getCmdReset3Tempo().setVisibility(LinearLayout.INVISIBLE);
                    } else {
                        TimerTempo.getInstance().FermaTimer();

                        vnp.getCmdStartSopTimer1Tempo().setBackgroundResource(R.drawable.play);

                        vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.VISIBLE);
                        vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.VISIBLE);

                        vnp.getCmdReset1Tempo().setVisibility(LinearLayout.VISIBLE);
                        vnp.getCmdReset2Tempo().setVisibility(LinearLayout.VISIBLE);
                        vnp.getCmdReset3Tempo().setVisibility(LinearLayout.VISIBLE);

                        /* if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.VISIBLE);
                            vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.VISIBLE);

                            vnp.getCmdReset1Tempo().setVisibility(LinearLayout.VISIBLE);
                            vnp.getCmdReset2Tempo().setVisibility(LinearLayout.VISIBLE);
                            vnp.getCmdReset3Tempo().setVisibility(LinearLayout.VISIBLE);
                        } else {
                            vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.INVISIBLE);
                            vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.INVISIBLE);

                            vnp.getCmdReset1Tempo().setVisibility(LinearLayout.INVISIBLE);
                            vnp.getCmdReset2Tempo().setVisibility(LinearLayout.INVISIBLE);
                            vnp.getCmdReset3Tempo().setVisibility(LinearLayout.INVISIBLE);
                        } */
                    }
                } else {
                    vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.INVISIBLE);
                    vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.INVISIBLE);

                    vnp.getCmdReset1Tempo().setVisibility(LinearLayout.INVISIBLE);
                    vnp.getCmdReset2Tempo().setVisibility(LinearLayout.INVISIBLE);
                    vnp.getCmdReset3Tempo().setVisibility(LinearLayout.INVISIBLE);
                }
            }
        });

        vnp.getCmdStartSopTimer2Tempo().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    if (TimerTempo.getInstance().getQualeTimerPartito() != 2) {
                        TimerTempo.getInstance().FaiPartireTimer(2, vnp.getTxtTimer2Tempo());

                        vnp.getCmdStartSopTimer2Tempo().setBackgroundResource(R.drawable.pausa);

                        vnp.getCmdStartSopTimer1Tempo().setVisibility(LinearLayout.INVISIBLE);
                        vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.INVISIBLE);

                        vnp.getCmdReset1Tempo().setVisibility(LinearLayout.INVISIBLE);
                        vnp.getCmdReset2Tempo().setVisibility(LinearLayout.INVISIBLE);
                        vnp.getCmdReset3Tempo().setVisibility(LinearLayout.INVISIBLE);
                    } else {
                        TimerTempo.getInstance().FermaTimer();

                        vnp.getCmdStartSopTimer2Tempo().setBackgroundResource(R.drawable.play);

                        vnp.getCmdStartSopTimer1Tempo().setVisibility(LinearLayout.VISIBLE);
                        vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.VISIBLE);

                        vnp.getCmdReset1Tempo().setVisibility(LinearLayout.VISIBLE);
                        vnp.getCmdReset2Tempo().setVisibility(LinearLayout.VISIBLE);
                        vnp.getCmdReset3Tempo().setVisibility(LinearLayout.VISIBLE);

                        /* if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            vnp.getCmdStartSopTimer1Tempo().setVisibility(LinearLayout.VISIBLE);
                            vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.VISIBLE);

                            vnp.getCmdReset1Tempo().setVisibility(LinearLayout.VISIBLE);
                            vnp.getCmdReset2Tempo().setVisibility(LinearLayout.VISIBLE);
                            vnp.getCmdReset3Tempo().setVisibility(LinearLayout.VISIBLE);
                        } else {
                            vnp.getCmdStartSopTimer1Tempo().setVisibility(LinearLayout.INVISIBLE);
                            vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.INVISIBLE);

                            vnp.getCmdReset1Tempo().setVisibility(LinearLayout.INVISIBLE);
                            vnp.getCmdReset2Tempo().setVisibility(LinearLayout.INVISIBLE);
                            vnp.getCmdReset3Tempo().setVisibility(LinearLayout.INVISIBLE);
                        } */
                    }
                } else {
                    vnp.getCmdStartSopTimer1Tempo().setVisibility(LinearLayout.INVISIBLE);
                    vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.INVISIBLE);

                    vnp.getCmdReset1Tempo().setVisibility(LinearLayout.INVISIBLE);
                    vnp.getCmdReset2Tempo().setVisibility(LinearLayout.INVISIBLE);
                    vnp.getCmdReset3Tempo().setVisibility(LinearLayout.INVISIBLE);
                }
            }
        });

        vnp.getCmdStartSopTimer3Tempo().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    if (TimerTempo.getInstance().getQualeTimerPartito() != 3) {
                        TimerTempo.getInstance().FaiPartireTimer(3, vnp.getTxtTimer3Tempo());

                        vnp.getCmdStartSopTimer3Tempo().setBackgroundResource(R.drawable.pausa);

                        vnp.getCmdStartSopTimer1Tempo().setVisibility(LinearLayout.INVISIBLE);
                        vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.INVISIBLE);

                        vnp.getCmdReset1Tempo().setVisibility(LinearLayout.INVISIBLE);
                        vnp.getCmdReset2Tempo().setVisibility(LinearLayout.INVISIBLE);
                        vnp.getCmdReset3Tempo().setVisibility(LinearLayout.INVISIBLE);
                    } else {
                        TimerTempo.getInstance().FermaTimer();

                        vnp.getCmdStartSopTimer3Tempo().setBackgroundResource(R.drawable.play);

                        vnp.getCmdStartSopTimer1Tempo().setVisibility(LinearLayout.VISIBLE);
                        vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.VISIBLE);

                        vnp.getCmdReset1Tempo().setVisibility(LinearLayout.VISIBLE);
                        vnp.getCmdReset2Tempo().setVisibility(LinearLayout.VISIBLE);
                        vnp.getCmdReset3Tempo().setVisibility(LinearLayout.VISIBLE);

                        /* if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                            vnp.getCmdStartSopTimer1Tempo().setVisibility(LinearLayout.VISIBLE);
                            vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.VISIBLE);

                            vnp.getCmdReset1Tempo().setVisibility(LinearLayout.VISIBLE);
                            vnp.getCmdReset2Tempo().setVisibility(LinearLayout.VISIBLE);
                            vnp.getCmdReset3Tempo().setVisibility(LinearLayout.VISIBLE);
                        } else {
                            vnp.getCmdStartSopTimer1Tempo().setVisibility(LinearLayout.INVISIBLE);
                            vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.INVISIBLE);

                            vnp.getCmdReset1Tempo().setVisibility(LinearLayout.INVISIBLE);
                            vnp.getCmdReset2Tempo().setVisibility(LinearLayout.INVISIBLE);
                            vnp.getCmdReset3Tempo().setVisibility(LinearLayout.INVISIBLE);
                        } */
                    }
                } else {
                    vnp.getCmdStartSopTimer1Tempo().setVisibility(LinearLayout.INVISIBLE);
                    vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.INVISIBLE);

                    vnp.getCmdReset1Tempo().setVisibility(LinearLayout.INVISIBLE);
                    vnp.getCmdReset2Tempo().setVisibility(LinearLayout.INVISIBLE);
                    vnp.getCmdReset3Tempo().setVisibility(LinearLayout.INVISIBLE);
                }
            }
        });

        /* if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraCastelVerde)) {
            vnp.getCmdStartSopTimer1Tempo().setColorFilter(Color.argb(255, 0, 0, 0));
            vnp.getCmdStartSopTimer2Tempo().setColorFilter(Color.argb(255, 0, 0, 0));
            vnp.getCmdStartSopTimer3Tempo().setColorFilter(Color.argb(255, 0, 0, 0));

            vnp.getCmdReset1Tempo().setColorFilter(Color.argb(255, 0, 0, 0));
            vnp.getCmdReset2Tempo().setColorFilter(Color.argb(255, 0, 0, 0));
            vnp.getCmdReset3Tempo().setColorFilter(Color.argb(255, 0, 0, 0));
        } else {
            vnp.getCmdStartSopTimer1Tempo().setColorFilter(Color.argb(255, 90,10,10));
            vnp.getCmdStartSopTimer2Tempo().setColorFilter(Color.argb(255, 90,10,10));
            vnp.getCmdStartSopTimer3Tempo().setColorFilter(Color.argb(255, 90,10,10));

            vnp.getCmdReset1Tempo().setColorFilter(Color.argb(255, 90,10,10));
            vnp.getCmdReset2Tempo().setColorFilter(Color.argb(255, 90,10,10));
            vnp.getCmdReset3Tempo().setColorFilter(Color.argb(255, 90,10,10));
        } */

        vnp.setChkInCasa((CheckBox) view.findViewById(R.id.chkInCasa));
        vnp.getChkInCasa().setChecked(false);
        pressCheckInCasa();
        vnp.getChkInCasa().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pressCheckInCasa();

                Utility.getInstance().PrendeCoordinateDaIndirizzo(vnp.getViewActivity());
                }
            }
        );

        vnp.setLayCampoEsterno((LinearLayout) view.findViewById(R.id.layCampoEsterno));
        vnp.setLayCampoIndirizzo((LinearLayout) view.findViewById(R.id.layCampoIndirizzo));
        vnp.setEdtCampoEsterno((EditText) view.findViewById(R.id.edtCampoEsterno));
        vnp.getEdtCampoEsterno().setText("");

        vnp.setChkEsterno((CheckBox) view.findViewById(R.id.chkEsterno));
        vnp.getChkEsterno().setChecked(false);
        vnp.getChkEsterno().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              pressCheckEsterno();

              Utility.getInstance().PrendeCoordinateDaIndirizzo(vnp.getViewActivity());
          }
        });

        vnp.setTxtConvocati((TextView) view.findViewById(R.id.txtConvocati));
        vnp.setTxtTipologia((TextView) view.findViewById(R.id.txtTipologia));
        vnp.setTxtAvversario((TextView) view.findViewById(R.id.txtAvversario));
        vnp.setTxtCategoria((TextView) view.findViewById(R.id.txtCategoria));
        vnp.setTxtArbitro((TextView) view.findViewById(R.id.txtArbitro));

        vnp.getTxtConvocati().setText("0");

        vnp.getTxtData().setText("");
        vnp.getTxtOra().setText("");
        vnp.getEdtGiochetti().setText("");
        vnp.getEdtNote().setText("");
        vnp.getTxtCampo().setText("");

        vnp.getTxtRisAvv1Tempo().setText("0");
        vnp.getTxtRisAvv2Tempo().setText("0");
        vnp.getTxtRisAvv3Tempo().setText("0");

        cmdMenoAvv1Tempo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    if (Integer.parseInt(vnp.getTxtRisAvv1Tempo().getText().toString()) > 0) {
                        vnp.getTxtRisAvv1Tempo().setText(Integer.toString(Integer.parseInt(vnp.getTxtRisAvv1Tempo().getText().toString()) - 1));

                        if (vnp.getTempiGAvvPrimoTempo().size()>0) {
                            vnp.getTempiGAvvPrimoTempo().remove(vnp.getTempiGAvvPrimoTempo().size() - 1);
                            fillSpinnerMinutiGoalAvversari();
                        }

                        ScriveRisultato();
                    } else {
                        vnp.setTempiGAvvPrimoTempo(new ArrayList<Integer>());
                        fillSpinnerMinutiGoalAvversari();
                    }
                }
            }
        });
        cmdMenoAvv2Tempo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    if (!vnp.getTxtRisAvv2Tempo().getText().equals("Non giocato")) {
                        if (Integer.parseInt(vnp.getTxtRisAvv2Tempo().getText().toString()) > 0) {
                            vnp.getTxtRisAvv2Tempo().setText(Integer.toString(Integer.parseInt(vnp.getTxtRisAvv2Tempo().getText().toString()) - 1));
                            if (vnp.getTempiGAvvSecondoTempo().size()>0) {
                                vnp.getTempiGAvvSecondoTempo().remove(vnp.getTempiGAvvSecondoTempo().size() - 1);
                                fillSpinnerMinutiGoalAvversari();
                            }
                            ScriveRisultato();
                        } else {
                            btnAvvenimentiSecondoTempo.setVisibility(LinearLayout.GONE);

                            vnp.getTxtRisAvv2Tempo().setText("Non giocato");

                            vnp.getLvvMarcatoriSecondoTempo().setVisibility(LinearLayout.GONE);
                            vnp.getSpnMarcatoriSecondoTempo().setVisibility(LinearLayout.GONE);

                            vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.GONE);
                            cmdMenoAvv2Tempo.setVisibility(LinearLayout.GONE);

                            vnp.getCmdReset2Tempo().setVisibility(LinearLayout.GONE);

                            vnp.setTempiGAvvSecondoTempo(new ArrayList<Integer>());
                            fillSpinnerMinutiGoalAvversari();
                            ScriveRisultato();
                        }
                    }
                }
            }
        });
        cmdMenoAvv3Tempo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    if (!vnp.getTxtRisAvv3Tempo().getText().equals("Non giocato")) {
                        if (Integer.parseInt(vnp.getTxtRisAvv3Tempo().getText().toString()) > 0) {
                            vnp.getTxtRisAvv3Tempo().setText(Integer.toString(Integer.parseInt(vnp.getTxtRisAvv3Tempo().getText().toString()) - 1));
                            if (vnp.getTempiGAvvTerzoTempo().size()>0) {
                                vnp.getTempiGAvvTerzoTempo().remove(vnp.getTempiGAvvTerzoTempo().size() - 1);
                                fillSpinnerMinutiGoalAvversari();
                            }

                            ScriveRisultato();
                        } else {
                            /* btnAvvenimentiTerzoTempo.setVisibility(LinearLayout.GONE);

                            vnp.getTxtRisAvv3Tempo().setText("Non giocato");

                            vnp.getLvvMarcatoriTerzoTempo().setVisibility(LinearLayout.GONE);
                            vnp.getSpnMarcatoriTerzoTempo().setVisibility(LinearLayout.GONE);

                            vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.GONE);
                            cmdMenoAvv3Tempo.setVisibility(LinearLayout.GONE);

                            vnp.getCmdReset3Tempo().setVisibility(LinearLayout.GONE); */

                            btnAvvenimentiTerzoTempo.setEnabled(false);

                            vnp.getTxtRisAvv3Tempo().setText("Non giocato");

                            vnp.getLvvMarcatoriTerzoTempo().setEnabled(false);
                            vnp.getSpnMarcatoriTerzoTempo().setEnabled(false);

                            vnp.getCmdStartSopTimer3Tempo().setEnabled(false);
                            cmdMenoAvv3Tempo.setEnabled(false);

                            vnp.getCmdReset3Tempo().setEnabled(false);

                            RelativeLayout layNoTerzoTempo = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.layNoTerzoTempo);
                            layNoTerzoTempo.setVisibility(LinearLayout.VISIBLE);

                            vnp.setTempiGAvvTerzoTempo(new ArrayList<Integer>());
                            fillSpinnerMinutiGoalAvversari();
                            ScriveRisultato();
                        }
                    }
                }
            }
        });

        cmdPiuAvv1Tempo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    vnp.getTxtRisAvv1Tempo().setText(Integer.toString(Integer.parseInt(vnp.getTxtRisAvv1Tempo().getText().toString()) + 1));

                    int minuto = TimerTempo.getInstance().RitornaMinuto(1);
                    if (minuto>-1) {
                        vnp.getTempiGAvvPrimoTempo().add(minuto);
                    } else {
                        vnp.getTempiGAvvPrimoTempo().add(0);
                    }
                    fillSpinnerMinutiGoalAvversari();

                    ScriveRisultato();
                }
            }
        });
        cmdPiuAvv2Tempo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    if (vnp.getTxtRisAvv2Tempo().getText().equals("Non giocato")) {
                        vnp.getTxtRisAvv2Tempo().setText("0");

                        btnAvvenimentiSecondoTempo.setVisibility(LinearLayout.VISIBLE);

                        vnp.getLvvMarcatoriSecondoTempo().setVisibility(LinearLayout.VISIBLE);
                        vnp.getLvvMarcatoriSecondoTempo().setEnabled(true);
                        vnp.getSpnMarcatoriSecondoTempo().setVisibility(LinearLayout.VISIBLE);
                        vnp.getSpnMarcatoriSecondoTempo().setEnabled(true);

                        vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.VISIBLE);
                        cmdMenoAvv2Tempo.setVisibility(LinearLayout.VISIBLE);

                        vnp.getCmdReset2Tempo().setVisibility(LinearLayout.VISIBLE);
                        vnp.setTempiGAvvSecondoTempo(new ArrayList<Integer>());
                        fillSpinnerMinutiGoalAvversari();
                    } else {
                        vnp.getTxtRisAvv2Tempo().setText(Integer.toString(Integer.parseInt(vnp.getTxtRisAvv2Tempo().getText().toString()) + 1));

                        int minuto = TimerTempo.getInstance().RitornaMinuto(2);
                        if (minuto>-1) {
                            vnp.getTempiGAvvSecondoTempo().add(minuto);
                        } else {
                            vnp.getTempiGAvvSecondoTempo().add(0);
                        }
                        fillSpinnerMinutiGoalAvversari();
                    }
                    ScriveRisultato();
                }
            }
        });
        cmdPiuAvv3Tempo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    if (vnp.getTxtRisAvv3Tempo().getText().equals("Non giocato")) {
                        /* btnAvvenimentiTerzoTempo.setVisibility(LinearLayout.VISIBLE);

                        vnp.getTxtRisAvv3Tempo().setText("0");

                        vnp.getLvvMarcatoriTerzoTempo().setVisibility(LinearLayout.VISIBLE);
                        vnp.getLvvMarcatoriTerzoTempo().setEnabled(true);
                        vnp.getSpnMarcatoriTerzoTempo().setVisibility(LinearLayout.VISIBLE);
                        vnp.getSpnMarcatoriTerzoTempo().setEnabled(true);

                        vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.VISIBLE);
                        cmdMenoAvv3Tempo.setVisibility(LinearLayout.VISIBLE);

                        vnp.getCmdReset3Tempo().setVisibility(LinearLayout.VISIBLE); */

                        btnAvvenimentiTerzoTempo.setEnabled(true);

                        vnp.getTxtRisAvv3Tempo().setText("0");

                        vnp.getLvvMarcatoriTerzoTempo().setEnabled(true);
                        vnp.getSpnMarcatoriTerzoTempo().setEnabled(true);

                        vnp.getCmdStartSopTimer3Tempo().setEnabled(true);
                        cmdMenoAvv3Tempo.setEnabled(true);

                        vnp.getCmdReset3Tempo().setEnabled(true);

                        layNoTerzoTempo.setVisibility(LinearLayout.GONE);

                        vnp.setTempiGAvvTerzoTempo(new ArrayList<Integer>());
                        fillSpinnerMinutiGoalAvversari();
                    } else {
                        vnp.getTxtRisAvv3Tempo().setText(Integer.toString(Integer.parseInt(vnp.getTxtRisAvv3Tempo().getText().toString()) + 1));

                        layNoTerzoTempo.setVisibility(LinearLayout.VISIBLE);

                        int minuto = TimerTempo.getInstance().RitornaMinuto(3);
                        if (minuto>-1) {
                            vnp.getTempiGAvvTerzoTempo().add(minuto);
                        } else {
                            vnp.getTempiGAvvTerzoTempo().add(0);
                        }
                        fillSpinnerMinutiGoalAvversari();
                    }
                    ScriveRisultato();
                }
            }
        });

        vnp.getTxtConvocati().setText("");
        vnp.getTxtTipologia().setText("");
        vnp.getTxtAvversario().setText("");
        vnp.getTxtCategoria().setText("");
        vnp.getTxtArbitro().setText("");

        cmdCategoria.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    GestisceSchermate("CATEGORIE");
                }
            }
        });

        cmdData.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    GestisceSchermate("DATA");

                    new MostraPannelloData(vnp.getContext(), vnp.getTxtData(), view.findViewById(R.id.idData));
                }
            }
        });

        cmdOra.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    GestisceSchermate("ORA");

                    new MostraPannelloOra(vnp.getContext(), vnp.getTxtOra(), view.findViewById(R.id.idOra));
                }
            }
        });

        EditText edtRicAvv = view.findViewById(R.id.edtRicAvv);
        edtRicAvv.setText("");

        ImageView imgRicAvv = view.findViewById(R.id.imgRicercaAvversario);
        imgRicAvv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    EditText edtRicAvv = view.findViewById(R.id.edtRicAvv);
                    String Ricerca = edtRicAvv.getText().toString().trim();

                    List<String> Avv = new ArrayList<>();
                    List<Integer> AvvID = new ArrayList<>();
                    List<String> AvvCampo = new ArrayList<>();
                    List<String> AvvCampoIndirizzo = new ArrayList<>();
                    List<Integer> AvvCampoID = new ArrayList<>();

                    if (!Ricerca.isEmpty()) {
                        int pos = -1;

                        for (String a : vnp.getAvversari()) {
                            pos++;
                            if (a.toUpperCase().trim().contains(Ricerca.toUpperCase().trim())) {
                                Avv.add(vnp.getAvversari().get(pos));
                                AvvID.add(vnp.getAvversariID().get(pos));
                                AvvCampo.add(vnp.getAvversariCampo().get(pos));
                                AvvCampoIndirizzo.add(vnp.getAvversariCampoIndirizzo().get(pos));
                                AvvCampoID.add(vnp.getAvversariCampoID().get(pos));
                            }
                        }
                    } else {
                        Avv = vnp.getAvversari();
                        AvvID = vnp.getAvversariID();
                        AvvCampo = vnp.getAvversariCampo();
                        AvvCampoIndirizzo = vnp.getAvversariCampoIndirizzo();
                        AvvCampoID = vnp.getAvversariCampoID();
                    }

                    fillSpinnersAvversari(Avv, AvvID, AvvCampoID, AvvCampo, AvvCampoIndirizzo);
                }
            }
        });

        EditText edtRicGioc = view.findViewById(R.id.edtRicGioc);
        edtRicGioc.setText("");

        ImageView imgAnnRic = view.findViewById(R.id.imgAnnullaRicerca);
        imgAnnRic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    EditText edtRicGioc = view.findViewById(R.id.edtRicGioc);
                    edtRicGioc.setText("");

                    List<String> GiocatoreNomeECognome = new ArrayList<>();
                    List<Integer> GiocatoreID = new ArrayList<>();
                    List<String> GiocatoreRuolo = new ArrayList<>();
                    List<Integer> GiocatoreIDRuolo = new ArrayList<>();
                    List<Integer> GiocatoreNumeroMaglia = new ArrayList<>();

                    GiocatoreNomeECognome = vnp.getGiocatoreCognomeNome();
                    GiocatoreID = vnp.getGiocatoreID();
                    GiocatoreRuolo = vnp.getGiocatoreRuolo();
                    GiocatoreIDRuolo = vnp.getGiocatoreIDRuolo();
                    GiocatoreNumeroMaglia = vnp.getGiocatoreNumeroMaglia();

                    fillSpinnerConvocati(GiocatoreNomeECognome,
                            GiocatoreID,
                            GiocatoreRuolo,
                            GiocatoreIDRuolo,
                            GiocatoreNumeroMaglia,
                            true);
                }
            }
        });

        ImageView imgRicGioc = view.findViewById(R.id.imgRicercaGiocatore);
        imgRicGioc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    EditText edtRicGioc = view.findViewById(R.id.edtRicGioc);
                    String Ricerca = edtRicGioc.getText().toString().trim();

                    List<String> GiocatoreNomeECognome = new ArrayList<>();
                    List<Integer> GiocatoreID = new ArrayList<>();
                    List<String> GiocatoreRuolo = new ArrayList<>();
                    List<Integer> GiocatoreIDRuolo = new ArrayList<>();
                    List<Integer> GiocatoreNumeroMaglia = new ArrayList<>();

                    if (!Ricerca.isEmpty()) {
                        int pos = -1;

                        for (String a : vnp.getGiocatoreCognomeNome()) {
                            pos++;
                            if (a.toUpperCase().trim().contains(Ricerca.toUpperCase().trim()) ||
                                    vnp.getGiocatoreNumeroMaglia().get(pos).toString().contains(Ricerca.toUpperCase().trim())) {
                                GiocatoreNomeECognome.add(vnp.getGiocatoreCognomeNome().get(pos));
                                GiocatoreID.add(vnp.getGiocatoreID().get(pos));
                                GiocatoreRuolo.add(vnp.getGiocatoreRuolo().get(pos));
                                GiocatoreIDRuolo.add(vnp.getGiocatoreIDRuolo().get(pos));
                                GiocatoreNumeroMaglia.add(vnp.getGiocatoreNumeroMaglia().get(pos));
                            }
                        }
                    } else {
                        GiocatoreNomeECognome = vnp.getGiocatoreCognomeNome();
                        GiocatoreID = vnp.getGiocatoreID();
                        GiocatoreRuolo = vnp.getGiocatoreRuolo();
                        GiocatoreIDRuolo = vnp.getGiocatoreIDRuolo();
                        GiocatoreNumeroMaglia = vnp.getGiocatoreNumeroMaglia();
                    }

                    fillSpinnerConvocati(GiocatoreNomeECognome,
                            GiocatoreID,
                            GiocatoreRuolo,
                            GiocatoreIDRuolo,
                            GiocatoreNumeroMaglia,
                            true);
                }
            }
        });

        cmdAvversario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    GestisceSchermate("AVVERSARI");
                }
            }
        });

        cmdTipologia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    GestisceSchermate("TIPOLOGIA");
                }
            }
        });

        cmdConvocati.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    GestisceSchermate("CONVOCATI");
                }
            }
        });

        cmdMeteo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    GestisceSchermate("METEO");
                }
            }
        });

        cmdAltroPartita.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    GestisceSchermate("ALTRO");
                }
            }
        });

        cmdArbitro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    GestisceSchermate("ARBITRO");
                }
            }
        });

        cmdWeb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String idPartita = Integer.toString(vnp.idPartita);
                if (PartitaNuova!=-1) {
                    idPartita=Integer.toString(PartitaNuova);
                }
                String pagina_web = RadiceWS+"/Partite/" +
                        VariabiliStaticheGlobali.getInstance().getAnnoInCorso() + "_" + idPartita + ".html";

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pagina_web));
                startActivity(browserIntent);
            }
        });

        setOkAnnulla(view);

        ScriveRisultato();

        vnp.setTxtCasa((TextView) view.findViewById(R.id.txtCasa));
        vnp.getTxtCasa().setText("");
        vnp.setImgCasa((ImageView) view.findViewById(R.id.imgCasa));
        vnp.setImgFuori((ImageView) view.findViewById(R.id.imgFuori));
        vnp.getImgCasa().setVisibility(LinearLayout.GONE);
        vnp.getImgFuori().setVisibility(LinearLayout.GONE);
        vnp.setTxtFuori((TextView) view.findViewById(R.id.txtFuori));

        /* if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraCastelVerde)) {
            vnp.getTxtCasa().setTextColor(Color.BLACK);
            vnp.getTxtFuori().setTextColor(Color.BLACK);
            vnp.getTxtGoal().setTextColor(Color.BLACK);
            vnp.getTxtTempi().setTextColor(Color.BLACK);
            vnp.getTxtRigori().setTextColor(Color.BLACK);
        } else {
            if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraPonteDiNona)) {
                vnp.getTxtCasa().setTextColor(Color.WHITE);
                vnp.getTxtFuori().setTextColor(Color.WHITE);
                vnp.getTxtGoal().setTextColor(Color.WHITE);
                vnp.getTxtTempi().setTextColor(Color.WHITE);
                vnp.getTxtRigori().setTextColor(Color.WHITE);
            }
        } */

        vnp.getTxtFuori().setText("");

        vnp.setCmdSalva((ImageView) view.findViewById(R.id.btnSalva));
        vnp.getCmdSalva().setVisibility(LinearLayout.GONE);
        vnp.getCmdSalva().setVisibility(LinearLayout.GONE);
        vnp.getCmdSalva().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    SalvaPartita();
                }
            }
        });

        vnp.setCmdCreaConv((ImageView) view.findViewById(R.id.btnCreaConvocazioni));
        vnp.getCmdCreaConv().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    TextView txt = (TextView) VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.txtAvversario);
                    String avve = txt.getText().toString();
                    if (avve.isEmpty()) {
                        DialogMessaggio.getInstance().show(
                                VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                                "Selezionare l'avversario",
                                true, "Errore");
                    } else {
                        CheckBox inc = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.chkInCasa);
                        boolean inCasa = inc.isChecked();
                        CheckBox est = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.chkEsterno);
                        boolean esterno = est.isChecked();
                        String campo = "";
                        if (inCasa) {
                            txt = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.txtCampo);
                            campo = txt.getText().toString();
                        } else {
                            campo = "inCasa";
                        }
                        if (esterno && campo.isEmpty()) {
                            campo = "esterno";
                        }
                        if (campo.isEmpty()) {
                            DialogMessaggio.getInstance().show(
                                    VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                                    "Selezionare il campo",
                                    true, "Errore");
                        } else {
                            txt = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.txtCategoria);
                            String cate = txt.getText().toString();
                            if (cate.isEmpty()) {
                                DialogMessaggio.getInstance().show(
                                        VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                                        "Selezionare la categoria",
                                        true, "Errore");
                            } else {
                                String ind = "";
                                if (!esterno) {
                                    txt = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.txtCampoIndirizzo);
                                    ind = txt.getText().toString();
                                } else {
                                    EditText edtxt = VariabiliStaticheNuovaPartita.getInstance().getViewActivity(). findViewById(R.id.edtCampoEsterno);
                                    ind = edtxt.getText().toString();
                                }
                                if (ind.isEmpty()) {
                                    DialogMessaggio.getInstance().show(
                                            VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                                            "Selezionare l'indirizzo del campo",
                                            true, "Errore");
                                } else {
                                    txt = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.txtdata);
                                    String data = txt.getText().toString();
                                    if (data.isEmpty()) {
                                        DialogMessaggio.getInstance().show(
                                                VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                                                "Inserire la data",
                                                true, "Errore");
                                    } else {
                                        txt = VariabiliStaticheNuovaPartita.getInstance().getViewActivity().findViewById(R.id.txtOra);
                                        String ora = txt.getText().toString();
                                        if (ora.isEmpty()) {
                                            DialogMessaggio.getInstance().show(
                                                    VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                                                    "Inserire l'ora",
                                                    true, "Errore");
                                        } else {
                                            if (VariabiliStaticheNuovaPartita.getInstance().getGiocatoreConvocato() == null ||
                                                    VariabiliStaticheNuovaPartita.getInstance().getGiocatoreConvocato().size() < 10) {
                                                DialogMessaggio.getInstance().show(
                                                        VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                                                        "Pochi convocati in lista",
                                                        true, "Errore");
                                            } else {
                                                if (VariabiliStaticheNuovaPartita.getInstance().idAllenatoreScelto == -1) {
                                                    DialogMessaggio.getInstance().show(
                                                            VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
                                                            "Selezionare l'allenatore",
                                                            true, "Errore");
                                                } else {
                                                    String idPartita = Integer.toString(vnp.idPartita);
                                                    if (PartitaNuova!=-1) {
                                                        idPartita=Integer.toString(PartitaNuova);
                                                    }

                                                    DBRemotoPartite dbr = new DBRemotoPartite();
                                                    dbr.CreaConvocazione(
                                                            VariabiliStaticheGlobali.getInstance().getContext(),
                                                            idPartita,
                                                            idPartita);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        vnp.setCmdUscita((ImageView) view.findViewById(R.id.btnUscita));
        vnp.getCmdUscita().setVisibility(LinearLayout.GONE);
        vnp.getCmdUscita().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ModificaEffettuata && idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    DialogDomanda.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                            "Si vuole veramente uscire ?\nSi perderanno tutte le modifiche effettuate",
                            VariabiliStaticheGlobali.NomeApplicazione,
                            "NUOVA_PARTITA");
                } else {
                    Utility.getInstance().CambiaMaschera(R.id.home, -1, -1);
                }
            }
        });

        LinearLayout layTastoPagina1 = view.findViewById(R.id.Pagina1);
        LinearLayout layTastoPagina2 = view.findViewById(R.id.Pagina2);
        LinearLayout layTastoPagina3 = view.findViewById(R.id.Pagina3);
        LinearLayout layTastoPagina4 = view.findViewById(R.id.Pagina4);
        LinearLayout layTastoPagina5 = view.findViewById(R.id.Pagina5);

        final LinearLayout layPagina1 = view.findViewById(R.id.layPagina1);
        final LinearLayout layPagina2 = view.findViewById(R.id.layPagina2);
        final LinearLayout layPagina3 = view.findViewById(R.id.layPagina3);
        final LinearLayout layPagina4 = view.findViewById(R.id.layPagina4);
        final LinearLayout layPagina5 = view.findViewById(R.id.layPagina5);

        TextView txtPagina1 = view.findViewById(R.id.txtPagina1);
        TextView txtPagina2 = view.findViewById(R.id.txtPagina2);
        TextView txtPagina3 = view.findViewById(R.id.txtPagina3);
        TextView txtPagina4 = view.findViewById(R.id.txtPagina4);
        TextView txtPagina5 = view.findViewById(R.id.txtPagina5);

        ImageView imgPagina1 = view.findViewById(R.id.imgPagina1);
        ImageView imgPagina2 = view.findViewById(R.id.imgPagina2);
        ImageView imgPagina3 = view.findViewById(R.id.imgPagina3);
        ImageView imgPagina4 = view.findViewById(R.id.imgPagina4);
        ImageView imgPagina5 = view.findViewById(R.id.imgPagina5);

        /* if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraCastelVerde)) {
            layTastoPagina1.setBackgroundResource(R.drawable.bordo_arrotondato_verde);
            layTastoPagina2.setBackgroundResource(R.drawable.bordo_arrotondato_verde);
            layTastoPagina3.setBackgroundResource(R.drawable.bordo_arrotondato_verde);
            layTastoPagina4.setBackgroundResource(R.drawable.bordo_arrotondato_verde);

            txtPagina1.setTextColor(Color.BLACK);
            txtPagina2.setTextColor(Color.BLACK);
            txtPagina3.setTextColor(Color.BLACK);
            txtPagina4.setTextColor(Color.BLACK);

            imgPagina1.setColorFilter(Color.argb(255, 0, 0, 0));
            imgPagina2.setColorFilter(Color.argb(255, 0, 0, 0));
            imgPagina3.setColorFilter(Color.argb(255, 0, 0, 0));
            imgPagina4.setColorFilter(Color.argb(255, 0, 0, 0));
        } else {
            if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraPonteDiNona)) {
                layTastoPagina1.setBackgroundResource(R.drawable.bordo_arrotondato_rosso);
                layTastoPagina2.setBackgroundResource(R.drawable.bordo_arrotondato_rosso);
                layTastoPagina3.setBackgroundResource(R.drawable.bordo_arrotondato_rosso);
                layTastoPagina4.setBackgroundResource(R.drawable.bordo_arrotondato_rosso);
                layTastoPagina5.setBackgroundResource(R.drawable.bordo_arrotondato_rosso);

                txtPagina1.setTextColor(Color.WHITE);
                txtPagina2.setTextColor(Color.WHITE);
                txtPagina3.setTextColor(Color.WHITE);
                txtPagina4.setTextColor(Color.WHITE);
                txtPagina5.setTextColor(Color.WHITE);

                imgPagina1.setColorFilter(Color.argb(255, 90,10,10));
                imgPagina2.setColorFilter(Color.argb(255, 90,10,10));
                imgPagina3.setColorFilter(Color.argb(255, 90,10,10));
                imgPagina4.setColorFilter(Color.argb(255, 90,10,10));
                imgPagina5.setColorFilter(Color.argb(255, 90,10,10));
            }
        } */

        layPagina1.setVisibility(LinearLayout.VISIBLE);
        layPagina2.setVisibility(LinearLayout.GONE);
        layPagina3.setVisibility(LinearLayout.GONE);
        layPagina4.setVisibility(LinearLayout.GONE);
        layPagina5.setVisibility(LinearLayout.GONE);

        layTastoPagina1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    VariabiliStaticheNuovaPartita.getInstance().setQualeTempoEvento(-1);
                    ModificaEffettuata=true;
                    layPagina1.setVisibility(LinearLayout.VISIBLE);
                    layPagina2.setVisibility(LinearLayout.GONE);
                    layPagina3.setVisibility(LinearLayout.GONE);
                    layPagina4.setVisibility(LinearLayout.GONE);
                    layPagina5.setVisibility(LinearLayout.GONE);
                }
            }
        });

        layTastoPagina2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    VariabiliStaticheNuovaPartita.getInstance().setQualeTempoEvento(1);
                    layPagina1.setVisibility(LinearLayout.GONE);
                    layPagina2.setVisibility(LinearLayout.VISIBLE);
                    layPagina3.setVisibility(LinearLayout.GONE);
                    layPagina4.setVisibility(LinearLayout.GONE);
                    layPagina5.setVisibility(LinearLayout.GONE);
                }
            }
        });

        layTastoPagina3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    VariabiliStaticheNuovaPartita.getInstance().setQualeTempoEvento(2);
                    layPagina1.setVisibility(LinearLayout.GONE);
                    layPagina2.setVisibility(LinearLayout.GONE);
                    layPagina3.setVisibility(LinearLayout.VISIBLE);
                    layPagina4.setVisibility(LinearLayout.GONE);
                    layPagina5.setVisibility(LinearLayout.GONE);
                }
            }
        });

        layTastoPagina4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    VariabiliStaticheNuovaPartita.getInstance().setQualeTempoEvento(3);
                    layPagina1.setVisibility(LinearLayout.GONE);
                    layPagina2.setVisibility(LinearLayout.GONE);
                    layPagina3.setVisibility(LinearLayout.GONE);
                    layPagina4.setVisibility(LinearLayout.VISIBLE);
                    layPagina5.setVisibility(LinearLayout.GONE);
                }
            }
        });

        layTastoPagina5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    VariabiliStaticheNuovaPartita.getInstance().setQualeTempoEvento(-1);
                    ModificaEffettuata = true;
                    layPagina1.setVisibility(LinearLayout.GONE);
                    layPagina2.setVisibility(LinearLayout.GONE);
                    layPagina3.setVisibility(LinearLayout.GONE);
                    layPagina4.setVisibility(LinearLayout.GONE);
                    layPagina5.setVisibility(LinearLayout.VISIBLE);
                }
            }
        });

        vnp.getRlMaschera().setVisibility(RelativeLayout.GONE);

        if (!idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            cmdCategoria.setVisibility(LinearLayout.INVISIBLE);
            cmdData.setVisibility(LinearLayout.INVISIBLE);
            cmdOra.setVisibility(LinearLayout.INVISIBLE);
            cmdAvversario.setVisibility(LinearLayout.INVISIBLE);
            cmdTipologia.setVisibility(LinearLayout.INVISIBLE);
            cmdConvocati.setVisibility(LinearLayout.INVISIBLE);
            cmdRefresh.setVisibility(LinearLayout.INVISIBLE);

            cmdMenoAvv1Tempo.setVisibility(LinearLayout.INVISIBLE);
            cmdPiuAvv1Tempo.setVisibility(LinearLayout.INVISIBLE);
            cmdMenoAvv2Tempo.setVisibility(LinearLayout.INVISIBLE);
            cmdPiuAvv2Tempo.setVisibility(LinearLayout.INVISIBLE);
            cmdMenoAvv3Tempo.setVisibility(LinearLayout.INVISIBLE);
            cmdPiuAvv3Tempo.setVisibility(LinearLayout.INVISIBLE);

            vnp.getCmdStartSopTimer1Tempo().setVisibility(LinearLayout.INVISIBLE);
            vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.INVISIBLE);
            vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.INVISIBLE);

            vnp.getCmdStartSopTimer1Tempo().setVisibility(LinearLayout.GONE);
            vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.GONE);
            vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.GONE);

            vnp.getChkInCasa().setEnabled(false);
            vnp.getChkEsterno().setEnabled(false);

            vnp.getEdtCampoEsterno().setEnabled(false);
            vnp.getEdtGiochetti().setEnabled(false);
            vnp.getEdtNote().setEnabled(false);

            vnp.getCmdReset1Tempo().setEnabled(false);
            vnp.getCmdReset2Tempo().setEnabled(false);
            vnp.getCmdReset3Tempo().setEnabled(false);

            vnp.getTxtTimer1Tempo().setEnabled(false);
            vnp.getTxtTimer2Tempo().setEnabled(false);
            vnp.getTxtTimer3Tempo().setEnabled(false);

            vnp.getSpnAllenatore().setEnabled(false);
            vnp.getSpnMarcatoriPrimoTempo().setEnabled(false);
            vnp.getSpnMarcatoriSecondoTempo().setEnabled(false);
            vnp.getSpnMarcatoriTerzoTempo().setEnabled(false);
            vnp.getSpnConvocati().setEnabled(false);
            vnp.getSpnConvocatiRigori().setEnabled(false);
            vnp.getSpnAvversario().setEnabled(false);
            vnp.getSpnCategorie().setEnabled(false);
            vnp.getSpnDaConvocare().setEnabled(false);
            vnp.getSpnDaConvocareRigori().setEnabled(false);
            vnp.getSpnTipologie().setEnabled(false);

            vnp.getSpnMarcatoriPrimoTempo().setEnabled(false);
            vnp.getSpnMarcatoriSecondoTempo().setEnabled(false);
            vnp.getSpnMarcatoriTerzoTempo().setEnabled(false);

            vnp.getLvvMarcatoriPrimoTempo().setEnabled(false);
            vnp.getLvvMarcatoriSecondoTempo().setEnabled(false);
            vnp.getLvvMarcatoriTerzoTempo().setEnabled(false);

            vnp.getLvTempiGAvvPrimoTempo().setEnabled(false);
            vnp.getLvTempiGAvvSecondoTempo().setEnabled(false);
            vnp.getLvTempiGAvvTerzoTempo().setEnabled(false);
        } else {
            cmdCategoria.setVisibility(LinearLayout.VISIBLE);
            cmdData.setVisibility(LinearLayout.VISIBLE);
            cmdOra.setVisibility(LinearLayout.VISIBLE);
            cmdAvversario.setVisibility(LinearLayout.VISIBLE);
            cmdTipologia.setVisibility(LinearLayout.VISIBLE);
            cmdConvocati.setVisibility(LinearLayout.VISIBLE);
            cmdRefresh.setVisibility(LinearLayout.VISIBLE);

            cmdMenoAvv1Tempo.setVisibility(LinearLayout.VISIBLE);
            cmdPiuAvv1Tempo.setVisibility(LinearLayout.VISIBLE);
            cmdMenoAvv2Tempo.setVisibility(LinearLayout.VISIBLE);
            cmdPiuAvv2Tempo.setVisibility(LinearLayout.VISIBLE);
            cmdMenoAvv3Tempo.setVisibility(LinearLayout.VISIBLE);
            cmdPiuAvv3Tempo.setVisibility(LinearLayout.VISIBLE);

            vnp.getCmdStartSopTimer1Tempo().setVisibility(LinearLayout.VISIBLE);
            vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.VISIBLE);
            vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.VISIBLE);

            vnp.getCmdStartSopTimer1Tempo().setVisibility(LinearLayout.VISIBLE);
            vnp.getCmdStartSopTimer2Tempo().setVisibility(LinearLayout.VISIBLE);
            vnp.getCmdStartSopTimer3Tempo().setVisibility(LinearLayout.VISIBLE);

            vnp.getChkInCasa().setEnabled(true);
            vnp.getChkEsterno().setEnabled(true);

            vnp.getEdtCampoEsterno().setEnabled(true);
            vnp.getEdtGiochetti().setEnabled(true);
            vnp.getEdtNote().setEnabled(true);

            vnp.getCmdReset1Tempo().setEnabled(true);
            vnp.getCmdReset2Tempo().setEnabled(true);
            vnp.getCmdReset3Tempo().setEnabled(true);

            vnp.getTxtTimer1Tempo().setEnabled(true);
            vnp.getTxtTimer2Tempo().setEnabled(true);
            vnp.getTxtTimer3Tempo().setEnabled(true);

            vnp.getSpnAllenatore().setEnabled(true);
            vnp.getSpnMarcatoriPrimoTempo().setEnabled(true);
            vnp.getSpnMarcatoriSecondoTempo().setEnabled(true);
            vnp.getSpnMarcatoriTerzoTempo().setEnabled(true);
            vnp.getSpnConvocati().setEnabled(true);
            vnp.getSpnConvocatiRigori().setEnabled(true);
            vnp.getSpnAvversario().setEnabled(true);
            vnp.getSpnCategorie().setEnabled(true);
            vnp.getSpnDaConvocare().setEnabled(true);
            vnp.getSpnDaConvocareRigori().setEnabled(true);
            vnp.getSpnTipologie().setEnabled(true);

            vnp.getSpnMarcatoriPrimoTempo().setEnabled(true);
            vnp.getSpnMarcatoriSecondoTempo().setEnabled(true);
            vnp.getSpnMarcatoriTerzoTempo().setEnabled(true);

            vnp.getLvvMarcatoriPrimoTempo().setEnabled(true);
            vnp.getLvvMarcatoriSecondoTempo().setEnabled(true);
            vnp.getLvvMarcatoriTerzoTempo().setEnabled(true);

            vnp.getLvTempiGAvvPrimoTempo().setEnabled(true);
            vnp.getLvTempiGAvvSecondoTempo().setEnabled(true);
            vnp.getLvTempiGAvvTerzoTempo().setEnabled(true);
        }
    }

    public static void ScriveRisultato() {
        VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();

        int goalPrimoTempo=0;
        if (vnp.getListMarcPrimoTempo()!=null) {
            for (String s : vnp.getListMarcPrimoTempo()) {
                if (!s.trim().isEmpty()) {
                    goalPrimoTempo++;
                }
            }
        }
        int goalSecondoTempo=0;
        if (vnp.getListMarcSecondoTempo()!=null) {
            for (String s : vnp.getListMarcSecondoTempo()) {
                if (!s.trim().isEmpty()) {
                    goalSecondoTempo++;
                }
            }
        }
        int goalTerzoTempo=0;
        if (vnp.getListMarcTerzoTempo()!=null) {
            if (vnp.getListMarcTerzoTempo().isEmpty()){

            } else {
                for (String s : vnp.getListMarcTerzoTempo()) {
                    if (!s.trim().isEmpty()) {
                        goalTerzoTempo++;
                    }
                }
            }
        }

        int goalPrimoTempoAvv=0;
        if (!vnp.getTxtRisAvv1Tempo().getText().toString().isEmpty()) {
            goalPrimoTempoAvv = Integer.parseInt(vnp.getTxtRisAvv1Tempo().getText().toString());
        }
        int goalSecondoTempoAvv=0;
        if (!vnp.getTxtRisAvv2Tempo().getText().toString().isEmpty() &&
                !vnp.getTxtRisAvv2Tempo().getText().toString().equals("Non giocato")) {
            goalSecondoTempoAvv = Integer.parseInt(vnp.getTxtRisAvv2Tempo().getText().toString());
        }
        int goalTerzoTempoAvv=0;
        if (!vnp.getTxtRisAvv3Tempo().getText().toString().isEmpty() &&
                !vnp.getTxtRisAvv3Tempo().getText().toString().equals("Non giocato")) {
            goalTerzoTempoAvv = Integer.parseInt(vnp.getTxtRisAvv3Tempo().getText().toString());
        }

        int TotGoal1 = goalPrimoTempo+goalSecondoTempo+goalTerzoTempo;
        int TotGoal2 = goalPrimoTempoAvv+goalSecondoTempoAvv+goalTerzoTempoAvv;

        int tempi1 = 0;
        int tempi2 = 0;

        if (!vnp.getEdtGiochetti().getText().toString().isEmpty()) {
            int goalGiochetti1 = 0;
            int goalGiochetti2 = 0;
            if (!vnp.getEdtGiochetti().getText().toString().isEmpty()) {
                if (vnp.getEdtGiochetti().getText().toString().contains("-")) {
                    String c[] = vnp.getEdtGiochetti().getText().toString().split("-");
                    goalGiochetti1 = Integer.parseInt(c[0].trim());
                    goalGiochetti2 = Integer.parseInt(c[1].trim());
                }
            }

            if (goalGiochetti1 > goalGiochetti2) {
                tempi1++;
            } else {
                if (goalGiochetti1 == goalGiochetti2) {
                    tempi1++;
                    tempi2++;
                } else {
                    tempi2++;
                }
            }
        }

        if (goalPrimoTempo>goalPrimoTempoAvv) {
            tempi1++;
        } else {
            if (goalPrimoTempo==goalPrimoTempoAvv) {
                tempi1++;
                tempi2++;
            } else {
                tempi2++;
            }
        }

        if (!vnp.getTxtRisAvv2Tempo().getText().equals("Non giocato")) {
            if (goalSecondoTempo > goalSecondoTempoAvv) {
                tempi1++;
            } else {
                if (goalSecondoTempo == goalSecondoTempoAvv) {
                    tempi1++;
                    tempi2++;
                } else {
                    tempi2++;
                }
            }
        }

        if (!vnp.getTxtRisAvv3Tempo().getText().equals("Non giocato")) {
            if (goalTerzoTempo>goalTerzoTempoAvv) {
                tempi1++;
            } else {
                if (goalTerzoTempo==goalTerzoTempoAvv) {
                    tempi1++;
                    tempi2++;
                } else {
                    tempi2++;
                }
            }
        }

        // Sezione rigori
        vnp.getTxtRigori().setVisibility(LinearLayout.GONE);
        boolean RigoriTirati = false;
        int rigoriSegnati = 0;
        if (vnp.getGiocatoreConvocatoRigori()!=null) {
            for (String r : vnp.getGiocatoreConvocatoRigori()) {
                if (!r.isEmpty()) {
                    String[] c = r.split(";", -1);
                    if (c[6].equals("1")) {
                        rigoriSegnati++;
                        RigoriTirati = true;
                    } else {
                        if (c[6].equals("0")) {
                            RigoriTirati = true;
                        }
                    }
                }
            }
        }
        int rigoriSubiti = 0;
        // if (RigoriTirati) {
            TextView txtRigAvv = vnp.getViewActivity().findViewById(R.id.txtRigoriSegnAvversari);
            String r = txtRigAvv.getText().toString().trim();
            if (!r.isEmpty()) {
                rigoriSubiti = Integer.parseInt(r);
                if (rigoriSubiti>0) {
                    RigoriTirati=true;
                }
            }

        // }

        if (RigoriTirati) {
            if (vnp.getChkTempi().isChecked()) {
                vnp.getTxtRigori().setVisibility(LinearLayout.VISIBLE);
                vnp.getTxtRigori().setText("Ris. finale: " + Integer.toString(tempi1) + "-" + Integer.toString(tempi2));
                if (rigoriSegnati > rigoriSubiti) {
                    tempi1++;
                } else {
                    if (rigoriSegnati < rigoriSubiti) {
                        tempi2++;
                    } else {
                        tempi1++;
                        tempi2++;
                    }
                }
            } else {
                vnp.getTxtRigori().setText("Ris. finale: " + Integer.toString(TotGoal1) + "-" + Integer.toString(TotGoal2));
                vnp.getTxtRigori().setVisibility(LinearLayout.VISIBLE);
                TotGoal1 += rigoriSegnati;
                TotGoal2 += rigoriSubiti;
            }
        }
        // Sezione rigori

        vnp.getTxtGoal().setText("Goal: "+Integer.toString(TotGoal1)+"-"+Integer.toString(TotGoal2));
        if (vnp.getChkTempi().isChecked()) {
            vnp.getTxtTempi().setText("Tempi: "+Integer.toString(tempi1)+"-"+Integer.toString(tempi2));
            vnp.getTxtTempi().setVisibility(LinearLayout.VISIBLE);
        } else {
            vnp.getTxtTempi().setVisibility(LinearLayout.GONE);
        }
    }

    private void setOkAnnulla(View view) {
        final VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();

        Button cmdOkCategoria = view.findViewById(R.id.cmdOkCategoria);
        Button cmdAnnullaCategoria = view.findViewById(R.id.cmdAnnullaCategoria);
        Button cmdOkAvversario = view.findViewById(R.id.cmdOkAvversario);
        Button cmdAnnullaAvversario = view.findViewById(R.id.cmdAnnullaAvversario);
        Button cmdOkTipologia = view.findViewById(R.id.cmdOkTipologia);
        Button cmdAnnullaTipologia = view.findViewById(R.id.cmdAnnullaTipologia);
        Button cmdOkConvocati = view.findViewById(R.id.cmdOkConvocati);
        Button cmdOkMeteo = view.findViewById(R.id.cmdOkMeteo);
        Button cmdOkAltro = view.findViewById(R.id.cmdOkAltro);
        Button cmdAnnullaConvocati = view.findViewById(R.id.cmdAnnullaConvocati);
        Button cmdOkArbitri = view.findViewById(R.id.cmdOkArbitro);
        Button cmdAnnullaArbitri = view.findViewById(R.id.cmdAnnullaArbitro);

        cmdAnnullaArbitri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ChiudeMaschera();
            }
        });
        cmdAnnullaCategoria.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ChiudeMaschera();
            }
        });
        cmdAnnullaAvversario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ChiudeMaschera();
            }
        });
        cmdAnnullaTipologia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ChiudeMaschera();
            }
        });
        cmdAnnullaConvocati.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ChiudeMaschera();
            }
        });

        cmdOkCategoria.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    View view = vnp.getViewActivity();

                    ChiudeMaschera();

                    DBRemotoGiocatori dbr = new DBRemotoGiocatori();
                    dbr.RitornaGiocatoriCategoria(vnp.getContext(), Integer.toString(vnp.idCategoriaScelta), TAG);

                    TextView tCasa = view.findViewById(R.id.txtCasa);

                    String cate = vnp.getTxtCategoria().getText().toString();
                    if (cate.contains("-")) {
                        cate = cate.substring(cate.indexOf("-")+1, cate.length());
                    }
                    cate = cate.trim();

                    tCasa.setText(cate);

                    ImageView imgCasa = view.findViewById(R.id.imgCasa);
                    imgCasa.setVisibility(LinearLayout.GONE);

                    Utility.getInstance().PrendeImmagineCategoria(Integer.toString(vnp.idCategoriaScelta), imgCasa);

                    DBRemotoAllenatori dba = new DBRemotoAllenatori();
                    dba.RitornaAllenatoriCategoria(vnp.getContext(), Integer.toString(vnp.idCategoriaScelta), TAG);
                }
            }
        });
        cmdOkAvversario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    View view = vnp.getViewActivity();

                    ChiudeMaschera();

                    TextView tFuori = view.findViewById(R.id.txtFuori);
                    tFuori.setText(vnp.getTxtAvversario().getText());
                    final ImageView imgFuori = view.findViewById(R.id.imgFuori);
                    imgFuori.setVisibility(LinearLayout.GONE);

                    Utility.getInstance().PrendeImmagineAvversario(Integer.toString(vnp.idAvversarioScelto), imgFuori);

                    Utility.getInstance().PrendeCoordinateDaIndirizzo(view);
                }
            }
        });
        cmdOkArbitri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;
                    View view = vnp.getViewActivity();

                    ChiudeMaschera();

                /* TextView tFuori = view.findViewById(R.id.txtFuori);
                tFuori.setText(vnp.getTxtAvversario().getText());
                final ImageView imgFuori = view.findViewById(R.id.imgFuori);
                imgFuori.setVisibility(LinearLayout.GONE);

                Utility.getInstance().PrendeImmagineAvversario(Integer.toString(vnp.idAvversarioScelto), imgFuori); */
                }
            }
        });
        cmdOkConvocati.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;

                    ChiudeMaschera();
                }
            }
        });
        cmdOkTipologia .setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;

                    ChiudeMaschera();
                }
            }
        });
        cmdOkMeteo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;

                    ChiudeMaschera();
                }
            }
        });
        cmdOkAltro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
                if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                    ModificaEffettuata = true;

                    ChiudeMaschera();
                }
            }
        });
    }

    private void SalvaPartita() {
        ModificaEffettuata = false;
        VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();

        boolean Ok = true;
        Activity act = VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale();
        Context context = VariabiliStaticheGlobali.getInstance().getContext();
        String Tipologia = vnp.getTxtTipologia().getText().toString().trim();
        String Avversario = vnp.getTxtAvversario().getText().toString().trim();
        String Allenatore = Integer.toString(vnp.idAllenatoreScelto);
        String Categoria = vnp.getTxtCategoria().toString().trim();
        String Coordinate = VariabiliStaticheMeteo.getInstance().getLat() + ";" + VariabiliStaticheMeteo.getInstance().getLon() +";";
        String Tempo = VariabiliStaticheMeteo.getInstance().getTempo() +";" + VariabiliStaticheMeteo.getInstance().getGradi() + ";" +
                VariabiliStaticheMeteo.getInstance().getUmidita() +";"+ VariabiliStaticheMeteo.getInstance().getPressione()+";";
        String Tga1="";
        String Tga2="";
        String Tga3="";
        for (Integer t : vnp.getTempiGAvvPrimoTempo()) {
            Tga1+=Integer.toString(t)+"#";
        }
        for (Integer t : vnp.getTempiGAvvSecondoTempo()) {
            Tga2+=Integer.toString(t)+"#";
        }
        for (Integer t : vnp.getTempiGAvvTerzoTempo()) {
            Tga3+=Integer.toString(t)+"#";
        }

        String Campo;
        if (vnp.getChkEsterno().isChecked()) {
            Campo = vnp.getEdtCampoEsterno().getText().toString().trim();
        } else {
            Campo = vnp.getTxtCampo().getText().toString().trim();
        }
        String Data = vnp.getTxtData().getText().toString().trim();
        String Ora = vnp.getTxtOra().getText().toString().trim();

        if (Tipologia.isEmpty()) {
            DialogMessaggio.getInstance().show(act, "Selezionare la tipologia", true, "Errore");
            Ok = false;
        }
        if (Avversario.isEmpty() && Ok) {
            DialogMessaggio.getInstance().show(act, "Selezionare l'avversario", true, "Errore");
            Ok = false;
        }
        if ((Allenatore.isEmpty() || Allenatore.equals("-1")) && Ok) {
            DialogMessaggio.getInstance().show(act, "Selezionare l'allenatore", true, "Errore");
            Ok = false;
        }
        if (Categoria.isEmpty() && Ok) {
            DialogMessaggio.getInstance().show(act, "Selezionare la categoria", true, "Errore");
            Ok = false;
        }
        if (Data.isEmpty() && Ok) {
            DialogMessaggio.getInstance().show(act, "Selezionare la data", true, "Errore");
            Ok = false;
        }
        if (Ora.isEmpty() && Ok) {
            DialogMessaggio.getInstance().show(act, "Selezionare l'ora", true, "Errore");
            Ok = false;
        }
        if (Campo.isEmpty() && Ok && !vnp.getChkInCasa().isChecked()) {
            DialogMessaggio.getInstance().show(act, "Selezionare il campo", true, "Errore");
            Ok = false;
        }

        if (Ok) {
            String idPartita = Integer.toString(vnp.idPartita);
            if (PartitaNuova!=-1) {
                idPartita=Integer.toString(PartitaNuova);
            }
            String idCategoria = Integer.toString(vnp.idCategoriaScelta);
            String idAllenatore = Integer.toString(vnp.idAllenatoreScelto);
            String idAvversario = Integer.toString(vnp.idAvversarioScelto);
            String idTipologia = Integer.toString(vnp.idTipologiaScelta);
            String idCampo;
            if (vnp.getChkEsterno().isChecked()) {
                idCampo = "-1";
            } else {
                idCampo = Integer.toString(vnp.idCampoScelto);
            }
            String Risultato = vnp.getTxtTempi().getText().toString().replace("Tempi: ", "").trim();
            String Note = vnp.getEdtNote().getText().toString();
            String RisGiochetti = vnp.getEdtGiochetti().getText().toString();
            String GoalAvversari = vnp.getTxtRisAvv1Tempo().getText().toString()+";";
            if (!vnp.getTxtRisAvv2Tempo().getText().equals("Non giocato")) {
                GoalAvversari += vnp.getTxtRisAvv2Tempo().getText().toString() + ";";
            } else {
                GoalAvversari +="-1;";
            }

            if (!vnp.getTxtRisAvv3Tempo().getText().equals("Non giocato")) {
                GoalAvversari += vnp.getTxtRisAvv3Tempo().getText().toString() + ";";
            } else {
                GoalAvversari +="-1;";
            }

            String Casa = "S";
            if (vnp.getChkEsterno().isChecked()) {
                Casa="E";
            } else {
                if (!vnp.getChkInCasa().isChecked()) {
                    Casa = "N";
                }
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm");
            Date myDate = null;
            try {
                myDate = dateFormat.parse(vnp.getTxtData().getText() + " " + vnp.getTxtOra().getText());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss");
            String finalDate = timeFormat.format(myDate);

            List<String> Marcatori = new ArrayList<>();
            if (vnp.getListMarcPrimoTempo()!=null) {
                for (String s : vnp.getListMarcPrimoTempo()) {
                    Marcatori.add("1;" + s);
                }
            }
            if (!vnp.getTxtRisAvv2Tempo().getText().equals("Non giocato")) {
                if (vnp.getListMarcSecondoTempo() != null) {
                    for (String s : vnp.getListMarcSecondoTempo()) {
                        Marcatori.add("2;" + s);
                    }
                }
            }
            if (!vnp.getTxtRisAvv3Tempo().getText().equals("Non giocato")) {
                if (vnp.getListMarcTerzoTempo() != null) {
                    for (String s : vnp.getListMarcTerzoTempo()) {
                        Marcatori.add("3;" + s);
                    }
                }
            }

            String sMarcatori = "";
            for (String s : Marcatori) {
                sMarcatori += s + "§";
            }
            String Convocati = "";
            for (String s : vnp.getGiocatoreConvocato()) {
                Convocati += (s + "§");
            }

            String Tempo1Tempo = vnp.getTxtTimer1Tempo().getText().toString();
            String Tempo2Tempo = vnp.getTxtTimer2Tempo().getText().toString();
            String Tempo3Tempo = vnp.getTxtTimer3Tempo().getText().toString();

            String sDirigentiConvocati="";
            int i=0;
            for (String d : VariabiliStaticheNuovaPartita.getInstance().getDirigenteSelezionato()) {
                sDirigentiConvocati+=(Integer.toString(VariabiliStaticheNuovaPartita.getInstance().getIdDirigenteSelezionato().get(i))+";"+d+"§");
                i++;
            }

            String RisultatoATempi = "S";
            if (!VariabiliStaticheNuovaPartita.getInstance().getChkTempi().isChecked()) {
                RisultatoATempi = "N";
            }

            // Rigori
            String RigoriPropri = "";
            String RigoriAvv = "";
            if (vnp.getGiocatoreConvocatoRigori()!=null) {
                for (String s : vnp.getGiocatoreConvocatoRigori()) {
                    RigoriPropri += s + "§";
                }
                if (!RigoriPropri.isEmpty()) {
                    TextView tSegnAvv = vnp.getViewActivity().findViewById(R.id.txtRigoriSegnAvversari);
                    TextView tSbaAvv = vnp.getViewActivity().findViewById(R.id.txtRigoriSbaAvversari);
                    String segn = tSegnAvv.getText().toString().trim();
                    String sba = tSbaAvv.getText().toString().trim();
                    if (segn.isEmpty()) {
                        segn = "0";
                    }
                    if (sba.isEmpty()) {
                        sba = "0";
                    }
                    RigoriAvv += segn + "§" + sba + "§";
                }
            }
            // Rigori

            // Eventi
            String eventiPrimoTempo = "";
            for (String e : vnp.getEventiPrimoTempo()) {
                eventiPrimoTempo += e + "§";
            }
            String eventiSecondoTempo = "";
            for (String e : vnp.getEventiSecondoTempo()) {
                eventiSecondoTempo += e + "§";
            }
            String eventiTerzoTempo = "";
            for (String e : vnp.getEventiTerzoTempo()) {
                eventiTerzoTempo += e + "§";
            }
            // Eventi

            if (vnp.idArbitroScelto==0) {
                DialogMessaggio.getInstance().show(vnp.getContext(),
                        "Selezionare l'arbitro dell'incontro",
                        true,
                        VariabiliStaticheGlobali.NomeApplicazione);
            } else {
                DBRemotoPartite dbr = new DBRemotoPartite();
                dbr.SalvaPartita(context, idPartita, idCategoria, idAvversario, idAllenatore, finalDate, Casa,
                        idTipologia, idCampo, Risultato, Note, sMarcatori, Convocati, RisGiochetti, GoalAvversari,
                        Campo, Tempo1Tempo, Tempo2Tempo, Tempo3Tempo, Coordinate, Tempo,
                        Integer.toString(idUnioneCalendario), Tga1, Tga2, Tga3, sDirigentiConvocati,
                        Integer.toString(vnp.idArbitroScelto), RisultatoATempi, RigoriPropri, RigoriAvv,
                        eventiPrimoTempo, eventiSecondoTempo, eventiTerzoTempo, TAG);
            }
        }
    }

    private void ChiudeMaschera() {
        VariabiliStaticheNuovaPartita.getInstance().getRlMaschera().setVisibility(LinearLayout.GONE);
        VariabiliStaticheNuovaPartita.getInstance().getLlContenuto().setEnabled(true);
    }

    private static void GestisceSchermate(String quale) {
        View view = VariabiliStaticheNuovaPartita.getInstance().getViewActivity();

        if (!quale.isEmpty()) {
            ModificaEffettuata = true;
        }
        VariabiliStaticheNuovaPartita.getInstance().getRlMaschera().setVisibility(RelativeLayout.VISIBLE);
        VariabiliStaticheNuovaPartita.getInstance().getLlContenuto().setEnabled(false);

        view.findViewById(R.id.idCategoria1).setVisibility(View.GONE);
        view.findViewById(R.id.idData).setVisibility(View.GONE);
        view.findViewById(R.id.idOra).setVisibility(View.GONE);
        view.findViewById(R.id.idAvversario).setVisibility(View.GONE);
        view.findViewById(R.id.idTipologia).setVisibility(View.GONE);
        view.findViewById(R.id.idConvocati).setVisibility(View.GONE);
        view.findViewById(R.id.idMeteo).setVisibility(View.GONE);
        view.findViewById(R.id.idAltroPartita).setVisibility(View.GONE);
        view.findViewById(R.id.idArbitro).setVisibility(View.GONE);
        view.findViewById(R.id.idRigori).setVisibility(View.GONE);
        view.findViewById(R.id.idEventi).setVisibility(View.GONE);

        switch (quale) {
            case "CATEGORIE":
                view.findViewById(R.id.idCategoria1).setVisibility(View.VISIBLE);
                break;
            case "DATA":
                view.findViewById(R.id.idData).setVisibility(View.VISIBLE);
                break;
            case "ORA":
                view.findViewById(R.id.idOra).setVisibility(View.VISIBLE);
                break;
            case "AVVERSARI":
                view.findViewById(R.id.idAvversario).setVisibility(View.VISIBLE);
                break;
            case "TIPOLOGIA":
                view.findViewById(R.id.idTipologia).setVisibility(View.VISIBLE);
                break;
            case "CONVOCATI":
                view.findViewById(R.id.idConvocati).setVisibility(View.VISIBLE);
                break;
            case "METEO":
                view.findViewById(R.id.idMeteo).setVisibility(View.VISIBLE);
                break;
            case "ALTRO":
                view.findViewById(R.id.idAltroPartita).setVisibility(View.VISIBLE);
                break;
            case "ARBITRO":
                view.findViewById(R.id.idArbitro).setVisibility(View.VISIBLE);
                break;
            case "RIGORI":
                view.findViewById(R.id.idRigori).setVisibility(View.VISIBLE);
                break;
            case "EVENTI":
                view.findViewById(R.id.idEventi).setVisibility(View.VISIBLE);
                break;
        }
    }

    public static void fillSpinnerEventiGiocatori() {
        final VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();

        vnp.setAdapterEventiGiocatori(new AdapterGiocatoreEventi(VariabiliStaticheGlobali.getInstance().getContext(),
                android.R.layout.simple_list_item_1, vnp.getEventiGiocatori()));
        vnp.getLvEventiGiocatori().setAdapter(vnp.getAdapterEventiGiocatori());
    }

    public static void fillSpinnerEventiLista() {
        final VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();

        vnp.setAdapterListaEventi(new AdapterEventi(VariabiliStaticheGlobali.getInstance().getContext(),
                android.R.layout.simple_list_item_1, vnp.getEventiLista()));
        vnp.getLvEventiLista().setAdapter(vnp.getAdapterListaEventi());
    }

    public static void fillSpinnerEventiTempi() {
        final VariabiliStaticheNuovaPartita vnp = VariabiliStaticheNuovaPartita.getInstance();

        if (VariabiliStaticheNuovaPartita.getInstance().getQualeTempoEvento()==1) {
            vnp.setAdapterEventiPrimoTempo(new AdapterEventiPartita(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, vnp.getEventiPrimoTempo()));
            vnp.getLvEventiPrimoTempo().setAdapter(vnp.getAdapterEventiPrimoTempo());
        }

        if (VariabiliStaticheNuovaPartita.getInstance().getQualeTempoEvento()==2) {
            vnp.setAdapterEventiSecondoTempo(new AdapterEventiPartita(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, vnp.getEventiSecondoTempo()));
            vnp.getLvEventiSecondoTempo().setAdapter(vnp.getAdapterEventiSecondoTempo());
        }

        if (VariabiliStaticheNuovaPartita.getInstance().getQualeTempoEvento()==3) {
            vnp.setAdapterEventiTerzoTempo(new AdapterEventiPartita(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, vnp.getEventiTerzoTempo()));
            vnp.getLvEventiTerzoTempo().setAdapter(vnp.getAdapterEventiTerzoTempo());
        }
    }
}
