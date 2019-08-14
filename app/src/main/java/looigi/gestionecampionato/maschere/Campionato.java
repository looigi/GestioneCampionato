package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import looigi.gestionecampionato.adapter.AdapterClassificaCampionato;
import looigi.gestionecampionato.adapter.AdapterPartiteCampionato;
import looigi.gestionecampionato.adapter.AdapterSquadreCampionato;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.StrutturaPartita;
import looigi.gestionecampionato.dati.StrutturaSquadreCampionato;
import looigi.gestionecampionato.dati.VariabiliStaticheCampionato;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.dati.VariabiliStaticheUtenti;
import looigi.gestionecampionato.db_remoto.DBRemotoAvversari;
import looigi.gestionecampionato.db_remoto.DBRemotoCampionato;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.utilities.MostraPannelloData;
import looigi.gestionecampionato.utilities.MostraPannelloOra;
import looigi.gestionecampionato.utilities.Utility;

public class Campionato extends android.support.v4.app.Fragment {
    private static String TAG = NomiMaschere.getInstance().getCampionato();
    private android.support.v4.app.Fragment a;
    private VariabiliStaticheCampionato vv = VariabiliStaticheCampionato.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vv.setContext(this.getActivity());
        a=this;

        View view=null;

        try {
            view=(inflater.inflate(R.layout.campionato, container, false));
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
        final View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            vv.setView(view);

            vv.setRlMascheraCampionato((LinearLayout) view.findViewById(R.id.layMascheraCampionato));
            vv.setRlMascheraNuovaPartita((RelativeLayout) view.findViewById(R.id.layMascheraNuovaPartita));
            vv.getRlMascheraCampionato().setVisibility(RelativeLayout.GONE);
            vv.getRlMascheraNuovaPartita().setVisibility(RelativeLayout.GONE);

            vv.setSpnCategorie((Spinner) view.findViewById(R.id.spnCategorie));

            Utility.getInstance().SettaColoreSceltaCategoria(view);

            vv.setIdCategoriaScelta(-1);

            if (vv.getCategorie()==null) {
                DBRemotoCategorie dbr = new DBRemotoCategorie();
                dbr.RitornaCategorie(context, TAG);
            } else {
                RiempieListaCategorie();
            }

            vv.setLayTastoPagina1((LinearLayout) view.findViewById(R.id.Pagina1));
            vv.setLayTastoPagina2((LinearLayout) view.findViewById(R.id.Pagina2));
            vv.setLayTastoPagina3((LinearLayout) view.findViewById(R.id.Pagina3));

            vv.setLayPagina1((LinearLayout) view.findViewById(R.id.layPagina1Camp));
            vv.setLayPagina2((LinearLayout) view.findViewById(R.id.layPagina2Camp));
            vv.setLayPagina3((LinearLayout) view.findViewById(R.id.layPagina3Camp));

            TextView txtPagina1 = view.findViewById(R.id.txtPagina1);
            TextView txtPagina2 = view.findViewById(R.id.txtPagina2);
            TextView txtPagina3 = view.findViewById(R.id.txtPagina3);

            ImageView imgPagina1 = view.findViewById(R.id.imgPagina1);
            ImageView imgPagina2 = view.findViewById(R.id.imgPagina2);
            ImageView imgPagina3 = view.findViewById(R.id.imgPagina3);

            RelativeLayout layMascheraModUtenti=view.findViewById(R.id.layMascheraAvversari);
            LinearLayout layCampNP=view.findViewById(R.id.layCampionatoNuovaPartita);

            if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraCastelVerde)) {
                vv.getLayTastoPagina1().setBackgroundResource(R.drawable.bordo_arrotondato_verde);
                vv.getLayTastoPagina2().setBackgroundResource(R.drawable.bordo_arrotondato_verde);
                vv.getLayTastoPagina3().setBackgroundResource(R.drawable.bordo_arrotondato_verde);

                layMascheraModUtenti.setBackgroundResource(R.drawable.bordo_arrotondato_verde_chiaro);
                layCampNP.setBackgroundResource(R.drawable.bordo_arrotondato_verde_chiaro);

                txtPagina1.setTextColor(Color.BLACK);
                txtPagina2.setTextColor(Color.BLACK);
                txtPagina3.setTextColor(Color.BLACK);

                imgPagina1.setColorFilter(Color.argb(255, 0, 0, 0));
                imgPagina2.setColorFilter(Color.argb(255, 0, 0, 0));
                imgPagina3.setColorFilter(Color.argb(255, 0, 0, 0));
            } else {
                if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraPonteDiNona)) {
                    vv.getLayTastoPagina1().setBackgroundResource(R.drawable.bordo_arrotondato_rosso);
                    vv.getLayTastoPagina2().setBackgroundResource(R.drawable.bordo_arrotondato_rosso);
                    vv.getLayTastoPagina3().setBackgroundResource(R.drawable.bordo_arrotondato_rosso);

                    layMascheraModUtenti.setBackgroundResource(R.drawable.bordo_arrotondato_rosso_chiaro);
                    layCampNP.setBackgroundResource(R.drawable.bordo_arrotondato_verde_chiaro);

                    txtPagina1.setTextColor(Color.WHITE);
                    txtPagina2.setTextColor(Color.WHITE);
                    txtPagina3.setTextColor(Color.WHITE);

                    imgPagina1.setColorFilter(Color.argb(255, 90,10,10));
                    imgPagina2.setColorFilter(Color.argb(255, 90,10,10));
                    imgPagina3.setColorFilter(Color.argb(255, 90,10,10));
                }
            }

            vv.getLayPagina1().setVisibility(LinearLayout.GONE);
            vv.getLayPagina2().setVisibility(LinearLayout.GONE);
            vv.getLayPagina3().setVisibility(LinearLayout.GONE);

            vv.setLvSquadre((ListView) view.findViewById(R.id.lstvSquadre));
            vv.setLvPartite((ListView) view.findViewById(R.id.lstvPartite));
            vv.setLvClassifica((ListView) view.findViewById(R.id.lstvClassifica));

            vv.setStaInserendo(false);

            view.findViewById(R.id.layMascheraAvversari).setVisibility(View.GONE);

            vv.setSpnAvversario((Spinner) view.findViewById(R.id.spnAvversario));
            vv.setSpnCasa((Spinner) view.findViewById(R.id.spnCasa));
            vv.setSpnFuori((Spinner) view.findViewById(R.id.spnFuori));

            vv.getLayTastoPagina1().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    vv.setMascheraSelezionata("Squadre");
                    vv.getLayPagina1().setVisibility(LinearLayout.VISIBLE);
                    vv.getLayPagina2().setVisibility(LinearLayout.GONE);
                    vv.getLayPagina3().setVisibility(LinearLayout.GONE);
                }
            });

            vv.getLayTastoPagina2().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    vv.setMascheraSelezionata("Partite");
                    vv.getLayPagina1().setVisibility(LinearLayout.GONE);
                    vv.getLayPagina2().setVisibility(LinearLayout.VISIBLE);
                    vv.getLayPagina3().setVisibility(LinearLayout.GONE);
                }
            });

            vv.getLayTastoPagina3().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    vv.setMascheraSelezionata("Classifica");
                    vv.getLayPagina1().setVisibility(LinearLayout.GONE);
                    vv.getLayPagina2().setVisibility(LinearLayout.GONE);
                    vv.getLayPagina3().setVisibility(LinearLayout.VISIBLE);
                }
            });

            Button cmdScegliCat = view.findViewById(R.id.cmdScegliCat);
            cmdScegliCat.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (vv.getIdCategoriaScelta()>-1) {
                        DBRemotoCampionato dbr= new DBRemotoCampionato();
                        dbr.RitornaCampionatoCategoria(context, Integer.toString(vv.getIdCategoriaScelta()), TAG);
                    }
                }
            });

            Button cmdOkAvversario = view.findViewById(R.id.cmdOkAvversario);
            cmdOkAvversario.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String c[] = vv.getAvversarioScelto().split(";",-1);
                    Boolean ok=true;

                    for (StrutturaSquadreCampionato s: vv.getDatiCampionato().getSquadre()) {
                        if (Integer.parseInt(c[0]) == s.getIdSquadre()) {
                            ok=false;
                            break;
                        }
                    }

                    if (ok) {
                        DBRemotoCampionato dbr = new DBRemotoCampionato();
                        dbr.AggiungeSquadraAvversaria(vv.getContext(),
                                Integer.toString(vv.getIdCategoriaScelta()), c[0]);
                    } else  {
                        DialogMessaggio.getInstance().show(vv.getContext(),
                                "Squadra già presente", true,
                                VariabiliStaticheGlobali.NomeApplicazione);
                    }
                }
            });

            Button cmdAnnullaAvversario = view.findViewById(R.id.cmdAnnullaAvversario);
            cmdAnnullaAvversario.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    view.findViewById(R.id.layMascheraAvversari).setVisibility(View.GONE);
                }
            });

            final EditText edtRicAvv = (EditText) view.findViewById(R.id.edtRicAvv);
            edtRicAvv.setText("");

            ImageView imgRicAvv = view.findViewById(R.id.imgRicercaAvversario);
            imgRicAvv.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String Ricerca = edtRicAvv.getText().toString().trim();

                    List<String> Avv = new ArrayList<>();
                    List<Integer> AvvID = new ArrayList<>();
                    List<String> AvvCampo = new ArrayList<>();
                    List<String> AvvCampoIndirizzo = new ArrayList<>();
                    List<Integer> AvvCampoID = new ArrayList<>();
                    List<String> AvvLat = new ArrayList<>();
                    List<String> AvvLon = new ArrayList<>();

                    if (!Ricerca.isEmpty()) {
                        int pos = -1;

                        for (String a : vv.getAvversari()) {
                            pos++;
                            if (a.toUpperCase().trim().contains(Ricerca.toUpperCase().trim())) {
                                Avv.add(vv.getAvversari().get(pos));
                                AvvID.add(vv.getAvversariID().get(pos));
                                AvvCampo.add(vv.getAvversariCampo().get(pos));
                                AvvCampoIndirizzo.add(vv.getAvversariCampoIndirizzo().get(pos));
                                AvvCampoID.add(vv.getAvversariCampoID().get(pos));
                                AvvLat.add(vv.getAvversariLat().get(pos));
                                AvvLon.add(vv.getAvversariLon().get(pos));
                            }
                        }
                    } else {
                        Avv=vv.getAvversari();
                        AvvID=vv.getAvversariID();
                        AvvCampo=vv.getAvversariCampo();
                        AvvCampoIndirizzo=vv.getAvversariCampoIndirizzo();
                        AvvCampoID=vv.getAvversariCampoID();
                        AvvLat=vv.getAvversariLat();
                        AvvLon=vv.getAvversariLon();
                    }

                    fillSpinnersAvversari(Avv, AvvID, AvvCampoID, AvvCampo, AvvCampoIndirizzo, AvvLat, AvvLon);
                }
            });

            // SEZIONE PARTITE
            vv.setTxtGiornata((TextView) view.findViewById(R.id.txtGiornata));
            vv.setEdtRisultato((EditText) view.findViewById(R.id.edtRisultato));
            vv.setEdtNote((EditText) view.findViewById(R.id.edtNote));
            vv.setTxtOra((TextView) view.findViewById(R.id.txtOra));
            vv.setTxtData((TextView) view.findViewById(R.id.txtdata));
            Button cmdData = view.findViewById(R.id.btnData);
            Button cmdOra = view.findViewById(R.id.btnOra);

            cmdData.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    view.findViewById(R.id.idData).setVisibility(View.VISIBLE);

                    new MostraPannelloData(context, vv.getTxtData(), view.findViewById(R.id.idData));
                }
            });

            cmdOra.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    view.findViewById(R.id.idOra).setVisibility(View.VISIBLE);

                    new MostraPannelloOra(context, vv.getTxtOra(), view.findViewById(R.id.idOra));
                }
            });

            vv.setCmdAvanti((Button) view.findViewById(R.id.btnAvantiGiornata));
            vv.getCmdAvanti().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int g = vv.getDatiCampionato().getGiornataAttuale();
                    if (g<vv.getDatiCampionato().getNumeroGiornate()) {
                        g++;
                        vv.getDatiCampionato().setGiornataAttuale(g);
                        ScriveGiornata(false);

                        DBRemotoCampionato dbr = new DBRemotoCampionato();
                        dbr.SalvaGiornataUtenteCategoria(context,
                                Integer.toString(VariabiliStaticheCampionato.getInstance().getIdCategoriaScelta()),
                                Integer.toString(g));
                    }
                }
            });

            vv.setCmdIndietro((Button) view.findViewById(R.id.btnIndietroGiornata));
            vv.getCmdIndietro().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int g = vv.getDatiCampionato().getGiornataAttuale();
                    if (g>1) {
                        g--;
                        vv.getDatiCampionato().setGiornataAttuale(g);
                        ScriveGiornata(false);

                        DBRemotoCampionato dbr = new DBRemotoCampionato();
                        dbr.SalvaGiornataUtenteCategoria(context,
                                Integer.toString(VariabiliStaticheCampionato.getInstance().getIdCategoriaScelta()),
                                Integer.toString(g));
                    }
                }
            });

            vv.setTxtClassifica((TextView) view.findViewById(R.id.txtClassifica));

            vv.setCmdAvantiClass((Button) view.findViewById(R.id.cmdAvantiClass));
            vv.getCmdAvantiClass().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int g = vv.getDatiCampionato().getGiornataClassificaAttuale();
                    if (g<vv.getDatiCampionato().getNumeroGiornate()) {
                        g++;
                        vv.getDatiCampionato().setGiornataClassificaAttuale(g);

                        DBRemotoCampionato dbr = new DBRemotoCampionato();
                        dbr.RitornaClassifica(context,
                                Integer.toString(g),
                                Integer.toString(VariabiliStaticheCampionato.getInstance().getIdCategoriaScelta())
                                );
                    }
                }
            });

            vv.setCmdIndietroClass((Button) view.findViewById(R.id.cmdIndietroClass));
            vv.getCmdIndietroClass().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int g = vv.getDatiCampionato().getGiornataClassificaAttuale();
                    if (g>1) {
                        g--;
                        vv.getDatiCampionato().setGiornataClassificaAttuale(g);

                        DBRemotoCampionato dbr = new DBRemotoCampionato();
                        dbr.RitornaClassifica(context,
                                Integer.toString(g),
                                Integer.toString(VariabiliStaticheCampionato.getInstance().getIdCategoriaScelta())
                        );
                    }
                }
            });

            vv.setCmdOkSquadra((Button) view.findViewById(R.id.cmdOkNuovaPartita));
            vv.getCmdOkSquadra().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String Data = "";
                    String Ora = "";
                    String Casa ="";
                    String Fuori="";

                    if (vv.getTxtData().getText().toString().trim().isEmpty()) {
                        DialogMessaggio.getInstance().show(vv.getContext(),
                                "Inserire la data",
                                true,
                                VariabiliStaticheGlobali.NomeApplicazione);
                    } else {
                        Data = vv.getTxtData().getText().toString().trim();
                        if (vv.getTxtOra().getText().toString().trim().isEmpty()) {
                            DialogMessaggio.getInstance().show(vv.getContext(),
                                    "Inserire l'ora",
                                    true,
                                    VariabiliStaticheGlobali.NomeApplicazione);
                        } else {
                            Ora = vv.getTxtOra().getText().toString().trim();
                            if (vv.getSquadraSceltaCasa() == null || vv.getSquadraSceltaCasa().isEmpty()) {
                                DialogMessaggio.getInstance().show(vv.getContext(),
                                        "Selezionare la squadra in casa",
                                        true,
                                        VariabiliStaticheGlobali.NomeApplicazione);
                            } else {
                                Casa = vv.getSquadraSceltaCasa();
                                if (vv.getSquadraSceltaFuori() == null || vv.getSquadraSceltaFuori().isEmpty()) {
                                    DialogMessaggio.getInstance().show(vv.getContext(),
                                            "Selezionare la squadra fuori casa",
                                            true,
                                            VariabiliStaticheGlobali.NomeApplicazione);
                                } else {
                                    Fuori = vv.getSquadraSceltaFuori();

                                    if (Casa.equals(Fuori)) {
                                        DialogMessaggio.getInstance().show(vv.getContext(),
                                                "Squadra di casa uguale a quella fuori casa",
                                                true,
                                                VariabiliStaticheGlobali.NomeApplicazione);
                                    } else {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm");
                                        Date myDate = null;
                                        try {
                                            myDate = dateFormat.parse(Data + " " + Ora);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        SimpleDateFormat timeFormatD = new SimpleDateFormat("dd/MM/yyyy");
                                        SimpleDateFormat timeFormatO = new SimpleDateFormat("kk:mm:ss");
                                        Data = timeFormatD.format(myDate);
                                        Ora = timeFormatO.format(myDate);

                                        DBRemotoCampionato dbr = new DBRemotoCampionato();
                                        if (vv.getModalitaInserimento()==0) {
                                            dbr.InserisceNuovaPartita(vv.getContext(),
                                                    Integer.toString(vv.getDatiCampionato().getGiornataAttuale()),
                                                    Integer.toString(vv.getIdCategoriaScelta()),
                                                    Data,
                                                    Ora,
                                                    Casa,
                                                    Fuori);
                                        } else {
                                            String idUnioneCalendario = Integer.toString(vv.getModalitaInserimento());
                                            String ProgressivoPartita = Integer.toString(vv.getIdPartitaDaModificare());
                                            String Risultato = vv.getEdtRisultato().getText().toString();

                                            if (!Risultato.isEmpty() && !Risultato.contains("-")) {
                                                DialogMessaggio.getInstance().show(vv.getContext(),
                                                        "Risultato non valido. Immetterlo nel formato X-X",
                                                        true,
                                                        VariabiliStaticheGlobali.NomeApplicazione);
                                            } else {
                                                dbr.ModificaPartitaAltre(vv.getContext(),
                                                        Integer.toString(vv.getDatiCampionato().getGiornataAttuale()),
                                                        Integer.toString(vv.getIdCategoriaScelta()),
                                                        Data,
                                                        Ora,
                                                        Casa,
                                                        Fuori,
                                                        idUnioneCalendario,
                                                        ProgressivoPartita,
                                                        Risultato);
                                            }
                                        }

                                        vv.setStaInserendo(false);
                                        view.findViewById(R.id.layMascheraNuovaPartita).setVisibility(View.GONE);
                                    }
                                }
                            }
                        }
                    }
                }
            });

            vv.setCmdAnnullaSquadra((Button) view.findViewById(R.id.cmdAnnullaNuovaPartita));
            vv.getCmdAnnullaSquadra().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    vv.setStaInserendo(false);
                    view.findViewById(R.id.layMascheraNuovaPartita).setVisibility(View.GONE);
                }
            });
        }
    }

    public static void ScriveGiornata(boolean Solotitolo) {
        final VariabiliStaticheCampionato vv = VariabiliStaticheCampionato.getInstance();

        vv.getTxtGiornata().setText("Giornata " + vv.getDatiCampionato().getGiornataAttuale() +
            "/" +
            vv.getDatiCampionato().getNumeroGiornate());

        if (!Solotitolo) {
            RiempieListaPartite();
        }
    }

    public static void NuovaSquadra() {
        String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
        final VariabiliStaticheCampionato vv = VariabiliStaticheCampionato.getInstance();

        if (!vv.isStaInserendo()) {
            if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                if (vv.getMascheraSelezionata() != null) {
                    vv.setStaInserendo(true);
                    switch (vv.getMascheraSelezionata()) {
                        case "Squadre":
                            if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                                vv.getView().findViewById(R.id.layMascheraAvversari).setVisibility(View.VISIBLE);

                                if (vv.getAvversari() == null) {
                                    DBRemotoAvversari dbr = new DBRemotoAvversari();
                                    dbr.RitornaAvversari(vv.getContext(), "", TAG);
                                } else {
                                    fillSpinnersAvversari(
                                            vv.getAvversari(),
                                            vv.getAvversariID(),
                                            vv.getAvversariCampoID(),
                                            vv.getAvversariCampo(),
                                            vv.getAvversariCampoIndirizzo(),
                                            vv.getAvversariLat(),
                                            vv.getAvversariLon());
                                }
                            } else {
                                DialogMessaggio.getInstance().show(VariabiliStaticheCampionato.getInstance().getContext(),
                                        "Non si hanno i permessi per inserire squadre", true,
                                        VariabiliStaticheGlobali.NomeApplicazione);
                            }
                            break;
                        case "Partite":
                            if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                                vv.getRlMascheraNuovaPartita().setVisibility(RelativeLayout.VISIBLE);

                                List<StrutturaSquadreCampionato> Squadre = new ArrayList<>(vv.getDatiCampionato().getSquadre());

                                List<String> Inserite = vv.getDatiCampionato().RitornaSquadreGiornata(vv.getDatiCampionato().getGiornataAttuale());
                                if (Inserite != null) {
                                    for (String s : Inserite) {
                                        String[] s1 = s.split(";", -1);
                                        for (StrutturaSquadreCampionato c : Squadre) {
                                            if (c.getIdSquadre() == Integer.parseInt(s1[0])) {
                                                Squadre.remove(c);
                                                break;
                                            }
                                        }
                                    }
                                }

                                vv.setModalitaInserimento(0);
                                vv.getTxtData().setText("");
                                vv.getTxtOra().setText("");
                                vv.getEdtRisultato().setText("");
                                vv.getEdtNote().setText("");

                                vv.getEdtRisultato().setEnabled(false);
                                vv.getEdtNote().setEnabled(false);

                                fillSpinnersSquadra(true, Squadre);
                                fillSpinnersSquadra(false, Squadre);
                            } else {
                                DialogMessaggio.getInstance().show(VariabiliStaticheCampionato.getInstance().getContext(),
                                        "Non si hanno i permessi per inserire partite", true,
                                        VariabiliStaticheGlobali.NomeApplicazione);
                            }
                            break;
                        case "Classifica":
                            break;
                    }
                }
            }
        }
    }

    private static void fillSpinnersSquadra(final Boolean Casa, final List<StrutturaSquadreCampionato> Squadra) {
        final VariabiliStaticheCampionato vnp = VariabiliStaticheCampionato.getInstance();

        if (Squadra != null && Squadra.size()>0) {
            // Carica squadre nella lista
            List<String> lSquadra = new ArrayList<>();
            // lSquadra.add("Scegliere una squadra");
            for (StrutturaSquadreCampionato sc : Squadra) {
                lSquadra.add(sc.getSquadre());
            }
            final ArrayAdapter<String> adapterSquadra = new ArrayAdapter<>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_piccolo, lSquadra);
            adapterSquadra.setDropDownViewResource(R.layout.spinner_item_piccolo);

            if (Casa) {
                vnp.getSpnCasa().setAdapter(adapterSquadra);
            } else {
                vnp.getSpnFuori().setAdapter(adapterSquadra);
            }
            // Carica squadre nella lista

            vnp.getSpnCasa().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    StrutturaSquadreCampionato sc = Squadra.get(position);
                    vnp.setSquadraSceltaCasa(sc.getIdSquadre()+";"+sc.getSquadre()+";");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            vnp.getSpnFuori().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    StrutturaSquadreCampionato sc = Squadra.get(position);
                    vnp.setSquadraSceltaFuori(sc.getIdSquadre()+";"+sc.getSquadre()+";");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public static void fillSpinnersAvversari(final List<String> Avv, final List<Integer> AvvID,
                                             final List<Integer> AvvCampoID, final List<String> AvvCampo,
                                             final List<String> AvvCampoIndirizzo, final List<String> AvvLat,
                                             final List<String> AvvLon) {
        final VariabiliStaticheCampionato vnp = VariabiliStaticheCampionato.getInstance();

        if (Avv != null && Avv.size()>0) {
            // Carica avversari nella lista
            final ArrayAdapter<String> adapterAvversari = new ArrayAdapter<>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_piccolo, Avv);
            adapterAvversari.setDropDownViewResource(R.layout.spinner_item_piccolo);
            vnp.getSpnAvversario().setAdapter(adapterAvversari);
            // Carica avversari nella lista

            vnp.getSpnAvversario().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    vnp.setAvversarioScelto(AvvID.get(position) +";"+
                            Avv.get(position)+";"+
                            AvvCampoID.get(position)+";"+
                            AvvCampo.get(position)+";"+
                            AvvCampoIndirizzo.get(position)+";"+
                            AvvLat.get(position)+";"+
                            AvvLon.get(position)+";");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public static void RiempieListaCategorie() {
        final VariabiliStaticheCampionato vv = VariabiliStaticheCampionato.getInstance();

        if (vv.getCategorie() != null) {
            final ArrayAdapter<String> adapterCategorie = new ArrayAdapter<String>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_piccolo, vv.getCategorie());
            adapterCategorie.setDropDownViewResource(R.layout.spinner_item_piccolo);
            vv.getSpnCategorie().setAdapter(adapterCategorie);

            int pos = Utility.getInstance().CercaESettaStringaInSpinner(vv.getSpnCategorie(),
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria1());
            if (pos>-1) {
                vv.getSpnCategorie().setSelection(pos);
            }
            vv.setIdCategoriaScelta(Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1()));

            vv.getSpnCategorie().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int idCategoria=vv.getIdCategorie().get(position);
                    vv.setIdCategoriaScelta(idCategoria);

                    VariabiliStaticheUtenti.getInstance().setIdCategoriaScelta(vv.getIdCategoriaScelta());
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().setDescCategoria(VariabiliStaticheCampionato.getInstance().getCategorie().get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public static void RiempieListaSquadre() {
        final VariabiliStaticheCampionato vv = VariabiliStaticheCampionato.getInstance();

        // Riempie Lista Squadre
        if (vv.getDatiCampionato().getSquadre() != null) {
            vv.setAdapterLvSquadre(new AdapterSquadreCampionato(
                    VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1,
                    vv.getDatiCampionato().getSquadre()));
            vv.getLvSquadre().setAdapter(vv.getAdapterLvSquadre());
            vv.getLvSquadre().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                }
            });
        }
        // Riempie Lista Squadre

        ScriveGiornata(true);
    }

    public static void RiempieListaClassifica() {
        final VariabiliStaticheCampionato vv = VariabiliStaticheCampionato.getInstance();

        vv.getTxtClassifica().setText("Classifica giorn.: " +
                vv.getDatiCampionato().getGiornataClassificaAttuale() + "/" +
                vv.getDatiCampionato().getNumeroGiornate());

        // Riempie Lista Classifica
        if (vv.getDatiCampionato().getSquadreClass() != null){
            vv.setAdapterLvClassifica(new AdapterClassificaCampionato(
                    VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1,
                    vv.getDatiCampionato().getSquadreClass()));
            vv.getLvClassifica().setAdapter(vv.getAdapterLvClassifica());
            vv.getLvClassifica().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                }
            });
        }
        // Riempie Lista Classifica
    }

    public static void RiempieListaPartite() {
        final VariabiliStaticheCampionato vv = VariabiliStaticheCampionato.getInstance();

        // Riempie Lista Partite
        int Giornata = vv.getDatiCampionato().getGiornataAttuale();
        List<StrutturaPartita> lsp = vv.getDatiCampionato().getListaPartiteGiornata(
                Giornata);
        if (lsp != null) {
            vv.setAdapterLvPartite(new AdapterPartiteCampionato(
                    VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1,
                    lsp));
            vv.getLvPartite().setAdapter(vv.getAdapterLvPartite());
            vv.getLvPartite().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                }
            });
            vv.getLvPartite().setVisibility(LinearLayout.VISIBLE);
            TextView t =(TextView) vv.getView().findViewById(R.id.emptyElement);
            t.setVisibility(LinearLayout.GONE);
        } else {
            vv.getLvPartite().setVisibility(LinearLayout.GONE);
            TextView t =(TextView) vv.getView().findViewById(R.id.emptyElement);
            t.setVisibility(LinearLayout.VISIBLE);
        }
        // Riempie Lista Partite
    }

    public static void RiempieDatiCampionato() {
        final VariabiliStaticheCampionato vv = VariabiliStaticheCampionato.getInstance();

        vv.getRlMascheraCampionato().setVisibility(RelativeLayout.VISIBLE);

        vv.getLayPagina1().setVisibility(LinearLayout.VISIBLE);
        vv.setMascheraSelezionata("Squadre");

        RiempieListaSquadre();

        RiempieListaClassifica();

        ScriveGiornata(false);
    }
}
