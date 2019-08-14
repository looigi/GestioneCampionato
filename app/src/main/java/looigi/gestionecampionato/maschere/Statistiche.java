package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.adapter.AdapterStatistiche;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.dati.VariabiliStaticheStatistiche;
import looigi.gestionecampionato.dati.VariabiliStaticheUtenti;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.db_remoto.DBRemotoGenerale;
import looigi.gestionecampionato.db_remoto.DBRemotoStatistiche;
import looigi.gestionecampionato.utilities.Utility;

public class Statistiche extends android.support.v4.app.Fragment {
    private Context context;
    private static String TAG = NomiMaschere.getInstance().getStatistiche();
    private static ListView lstStatistiche;
    private static RelativeLayout lMaschera;
    private static android.support.design.widget.FloatingActionButton btnBack;

    public static int QualeStatistica;
    private static String StatisticheAvversariPerAnno="";
    //private static String StatisticheAvversariTotale="";
    private static String StatisticheConvocatiPerAnno="";
    //private static String StatisticheConvocatiTotale="";
    private static String StatisticheMarcatoriPerAnno="";
    //private static String StatisticheMarcatoriTotale="";
    private static String StatistichePartitePerAnno="";
    //private static String StatistichePartiteTotale="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = this.getActivity();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.statistiche, container, false));
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
            lMaschera=view.findViewById(R.id.layMascheraModUtenti);
            lMaschera.setVisibility(LinearLayout.GONE);

            lstStatistiche = view.findViewById(R.id.lstStatistiche);

            final TextView txtTitolo = view.findViewById(R.id.txtTitolo);

            RelativeLayout layMascheraModUtenti=view.findViewById(R.id.layMascheraModUtenti);
            if (VariabiliStaticheMain.getInstance().getSquadra()!=null) {
                if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraCastelVerde)) {
                    layMascheraModUtenti.setBackgroundResource(R.drawable.bordo_arrotondato_verde_chiaro);
                } else {
                    layMascheraModUtenti.setBackgroundResource(R.drawable.bordo_arrotondato_rosso_chiaro);
                }
            } else {
                layMascheraModUtenti.setBackgroundResource(R.drawable.bordo_arrotondato_rosso_chiaro);
            }

            // Avversari
            ImageView ivAvvPerAnno=view.findViewById(R.id.imgAvversariPerAnno);
            ivAvvPerAnno.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    txtTitolo.setText("Avversari per anno");

                    QualeStatistica=1;
                    if (StatisticheAvversariPerAnno.isEmpty()) {
                        DBRemotoStatistiche dbr = new DBRemotoStatistiche();
                        dbr.RitornaStatisticheAvversari(context, NomiMaschere.getInstance().getStatistiche(),
                                "S", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta));
                    } else {
                        fillListViewStatistiche(StatisticheAvversariPerAnno);
                    }
                }
            });

            /* ImageView ivAvvTotali=view.findViewById(R.id.imgAvversariTotali);
            ivAvvTotali.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    txtTitolo.setText("Avversari storici");

                    QualeStatistica=2;
                    if (StatisticheAvversariTotale.isEmpty()) {
                        DBRemotoStatistiche dbr = new DBRemotoStatistiche();
                        dbr.RitornaStatisticheAvversari(context, NomiMaschere.getInstance().getStatistiche(),
                                "N", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta1));
                    } else {
                        fillListViewStatistiche(StatisticheAvversariTotale);
                    }
                }
            }); */

            // Convocati
            ImageView ivConvPerAnno=view.findViewById(R.id.imgConvocatiPerAnno);
            ivConvPerAnno.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    txtTitolo.setText("Convocati per anno");

                    QualeStatistica=3;
                    if (StatisticheConvocatiPerAnno.isEmpty()) {
                        DBRemotoStatistiche dbr = new DBRemotoStatistiche();
                        dbr.RitornaStatisticheConvocati(context, NomiMaschere.getInstance().getStatistiche(),
                                "S", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta));
                    } else {
                        fillListViewStatistiche(StatisticheConvocatiPerAnno);
                    }
                }
            });

            /* ImageView ivConvTotali=view.findViewById(R.id.imgConvocatiTotali);
            ivConvTotali.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    txtTitolo.setText("Convocati storici");

                    QualeStatistica=4;
                    if (StatisticheConvocatiTotale.isEmpty()) {
                        DBRemotoStatistiche dbr = new DBRemotoStatistiche();
                        dbr.RitornaStatisticheConvocati(context, NomiMaschere.getInstance().getStatistiche(),
                                "N", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta1));
                    } else {
                        fillListViewStatistiche(StatisticheConvocatiTotale);
                    }
                }
            }); */

            // Marcatori
            ImageView ivMarcPerAnno=view.findViewById(R.id.imgMarcatoriPerAnno);
            ivMarcPerAnno.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    txtTitolo.setText("Marcatori per anno");

                    QualeStatistica=5;
                    if (StatisticheMarcatoriPerAnno.isEmpty()) {
                        DBRemotoStatistiche dbr = new DBRemotoStatistiche();
                        dbr.RitornaStatisticheMarcatori(context, NomiMaschere.getInstance().getStatistiche(),
                                "S", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta));
                    } else {
                        fillListViewStatistiche(StatisticheMarcatoriPerAnno);
                    }
                }
            });

            /* ImageView ivMarcTotali=view.findViewById(R.id.imgMarcatoriTotali);
            ivMarcTotali.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    txtTitolo.setText("Marcatori storici");

                    QualeStatistica=6;
                    if (StatisticheMarcatoriTotale.isEmpty()) {
                        DBRemotoStatistiche dbr = new DBRemotoStatistiche();
                        dbr.RitornaStatisticheMarcatori(context, NomiMaschere.getInstance().getStatistiche(),
                                "N", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta1));
                    } else {
                        fillListViewStatistiche(StatisticheMarcatoriTotale);
                    }
                }
            }); */

            // Partite
            ImageView ivPartPerAnno=view.findViewById(R.id.imgPartitePerAnno);
            ivPartPerAnno.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    txtTitolo.setText("Partite per anno");

                    QualeStatistica=7;
                    if (StatistichePartitePerAnno.isEmpty()) {
                        DBRemotoStatistiche dbr = new DBRemotoStatistiche();
                        dbr.RitornaStatistichePartite(context, NomiMaschere.getInstance().getStatistiche(),
                                "S", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta));
                    } else {
                        fillListViewStatistiche(StatistichePartitePerAnno);
                    }
                }
            });

            // Mappa
            ImageView ivMappa=view.findViewById(R.id.imgMappa);
            ivMappa.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Utility.getInstance().CambiaMaschera(-3, -1, -1);
                }
            });

            // Grafico minuti fatti
            ImageView ivMinutiF=view.findViewById(R.id.imgMinutiGoalFatti);
            ivMinutiF.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Utility.getInstance().CambiaMaschera(-4, 1, -1);
                }
            });

            // Grafico minuti subiti
            ImageView ivMinutiS=view.findViewById(R.id.imgMinutiGoalSubiti);
            ivMinutiS.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Utility.getInstance().CambiaMaschera(-5, 2, -1);
                }
            });

            // Grafico meteo 1
            ImageView ivMeteo1=view.findViewById(R.id.imgMeteo1);
            ivMeteo1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Utility.getInstance().CambiaMaschera(-6, 3, -1);
                }
            });

            // Grafico meteo 2
            ImageView ivMeteo2=view.findViewById(R.id.imgMeteo2);
            ivMeteo2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Utility.getInstance().CambiaMaschera(-7, 4, -1);
                }
            });

            // Grafico goal segnati
            ImageView ivSegnati=view.findViewById(R.id.imgGoalSegnati);
            ivSegnati.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Utility.getInstance().CambiaMaschera(-8, 5, -1);
                }
            });

            // Grafico goal subiti
            ImageView ivSubiti=view.findViewById(R.id.imgGoalSubiti);
            ivSubiti.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Utility.getInstance().CambiaMaschera(-9, 6, -1);
                }
            });

            // Grafico andamento
            ImageView ivAndamento=view.findViewById(R.id.imgAndamento);
            ivAndamento.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Utility.getInstance().CambiaMaschera(-10, 7, -1);
                }
            });

            // Grafico tipologia partite
            ImageView ivTipologia=view.findViewById(R.id.imgTipologiaPartite);
            ivTipologia.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Utility.getInstance().CambiaMaschera(-11, 8, -1);
                }
            });

            // Grafico casa / fuori
            ImageView ivCasaFuori=view.findViewById(R.id.imgCasaFuori);
            ivCasaFuori.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Utility.getInstance().CambiaMaschera(-12, 9, -1);
                }
            });

            // Grafico allenatori
            ImageView ivAllenatore=view.findViewById(R.id.imgAllenatore);
            ivAllenatore.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Utility.getInstance().CambiaMaschera(-13, 10, -1);
                }
            });

            Button cmdScegliCat = view.findViewById(R.id.cmdScegliCat);
            cmdScegliCat.setVisibility(LinearLayout.GONE);

            // Statistiche annue
            ImageView ivStatAnnue=view.findViewById(R.id.imgStatisticheAnnue);
            ivStatAnnue.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DBRemotoStatistiche dbr = new DBRemotoStatistiche();
                    dbr.StatisticheAnnue(context);
                }
            });

            /* ImageView ivPartTotali=view.findViewById(R.id.imgPartiteTotali);
            ivPartTotali.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    txtTitolo.setText("Partite storico");

                    QualeStatistica=8;
                    if (StatistichePartiteTotale.isEmpty()) {
                        DBRemotoStatistiche dbr = new DBRemotoStatistiche();
                        dbr.RitornaStatistichePartite(context, NomiMaschere.getInstance().getStatistiche(),
                                "N", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta1));
                    } else {
                        fillListViewStatistiche(StatistichePartiteTotale);
                    }
                }
            }); */

            btnBack=view.findViewById(R.id.fabBack);
            btnBack.hide(); // .setVisibility(LinearLayout.GONE);
            btnBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    btnBack.hide(); // .setVisibility(LinearLayout.GONE);
                    lMaschera.setVisibility(LinearLayout.GONE);
                }
            });
        }

        VariabiliStaticheStatistiche.getInstance().setSpnCategorie((Spinner) view.findViewById(R.id.spnCategorie));
        if (VariabiliStaticheStatistiche.getInstance().getCategorie()==null) {
            DBRemotoCategorie dbr = new DBRemotoCategorie();
            dbr.RitornaCategorie(context, TAG);
        } else {
            RiempieListaCategorie();
        }

        Utility.getInstance().SettaColoreSceltaCategoria(view);
    }

    public static void RiempieListaCategorie() {
        if (VariabiliStaticheStatistiche.getInstance().getCategorie() != null) {
            final ArrayAdapter<String> adapterCategorie = new ArrayAdapter<String>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_piccolo, VariabiliStaticheStatistiche.getInstance().getCategorie());
            adapterCategorie.setDropDownViewResource(R.layout.spinner_item_piccolo);
            VariabiliStaticheStatistiche.getInstance().getSpnCategorie().setAdapter(adapterCategorie);

            int pos = Utility.getInstance().CercaESettaStringaInSpinner(VariabiliStaticheStatistiche.getInstance().getSpnCategorie(),
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria1());
            if (pos>-1) {
                VariabiliStaticheStatistiche.getInstance().getSpnCategorie().setSelection(pos);
            }
            VariabiliStaticheStatistiche.getInstance().idCategoriaScelta=Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1());

            VariabiliStaticheStatistiche.getInstance().getSpnCategorie().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int idCategoria=VariabiliStaticheStatistiche.getInstance().getIdCategorie().get(position);
                    VariabiliStaticheStatistiche.getInstance().idCategoriaScelta=idCategoria;

                    VariabiliStaticheUtenti.getInstance().setIdCategoriaScelta(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta);
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().setDescCategoria(VariabiliStaticheStatistiche.getInstance().getCategorie().get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public static void fillListViewStatistiche(String Appoggio) {
        AdapterStatistiche statistiche = null;
        List<String> statList=new ArrayList<>();

        for (String s : Appoggio.split("§",-1)) {
            if (!s.isEmpty()) {
                statList.add(s);
            }
        }

        if (QualeStatistica==5){
            // Gestione marcatori con rigori
            List<String> statList2=new ArrayList<>();

            int numeroPrimo = 0;
            int numeroSecondo = 0;
            boolean trovato = false;

            for (String primo : statList) {
                trovato=false;
                if (!primo.isEmpty()) {
                    String[] p = primo.split(";", -1);
                    for (String secondo : statList) {
                        if (!secondo.isEmpty()) {
                            String[] s = secondo.split(";", -1);
                            if (numeroPrimo!=numeroSecondo) {
                                if (p[0].equals(s[0])) {
                                    if (s[5].equals("RIGORE")) {
                                        p[3] += "§" + s[3];
                                        statList2.add(p[0]+";"+p[1]+";"+p[2]+";"+p[3]+";"+p[4]+";");
                                        trovato=true;
                                        break;
                                    }
                                }
                            }
                        }
                        numeroSecondo++;
                    }
                    if (!trovato) {
                        statList2.add(p[0]+";"+p[1]+";"+p[2]+";"+p[3]+";"+p[4]+";");
                    }
                }
                numeroPrimo++;
            }
            numeroPrimo = 0;
            for (String s1 : statList2) {
                String[] ss1 = s1.split(";", -1);
                numeroSecondo = 0;
                for (String s2 : statList2) {
                    if (numeroPrimo!=numeroSecondo) {
                        String[] ss2 = s2.split(";", -1);
                        int g1;
                        int g2;
                        if (ss1[3].contains("§")) {
                            String[] sss1 = ss1[3].split("§", -1);
                            g1 = Integer.parseInt(sss1[0]) + Integer.parseInt(sss1[1]);
                        } else {
                            g1 = Integer.parseInt(ss1[3]);
                        }
                        if (ss2[3].contains("§")) {
                            String[] sss2 = ss2[3].split("§", -1);
                            g2 = Integer.parseInt(sss2[0]) + Integer.parseInt(sss2[1]);
                        } else {
                            g2 = Integer.parseInt(ss2[3]);
                        }
                        if (g1 > g2) {
                            String app = statList2.get(numeroPrimo);
                            statList2.set(numeroPrimo, statList2.get(numeroSecondo));
                            statList2.set(numeroSecondo, app);
                        }
                    }
                    numeroSecondo++;
                }
                numeroPrimo++;
            }
            statList = statList2;
            // Gestione marcatori con rigori
        }

        switch (QualeStatistica) {
            case 1:
                StatisticheAvversariPerAnno=Appoggio;
                break;
            case 2:
                // StatisticheAvversariTotale=Appoggio;
                break;
            case 3:
                StatisticheConvocatiPerAnno=Appoggio;
                break;
            case 4:
                // StatisticheConvocatiTotale=Appoggio;
                break;
            case 5:
                StatisticheMarcatoriPerAnno=Appoggio;
                break;
            case 6:
                // StatisticheMarcatoriTotale=Appoggio;
                break;
            case 7:
                StatistichePartitePerAnno=Appoggio;
                break;
            case 8:
                // StatistichePartiteTotale=Appoggio;
                break;
        }


        statistiche=new AdapterStatistiche(VariabiliStaticheGlobali.getInstance().getContext(),
                android.R.layout.simple_list_item_1, statList);
        lstStatistiche.setAdapter(statistiche);

        lMaschera.setVisibility(LinearLayout.VISIBLE);
        btnBack.show(); // .setVisibility(LinearLayout.VISIBLE);
    }
}
