package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheGrafici;
import looigi.gestionecampionato.dati.VariabiliStaticheStatistiche;
import looigi.gestionecampionato.db_remoto.DBRemotoStatistiche;
import looigi.gestionecampionato.utilities.Utility;

public class Grafici extends android.support.v4.app.Fragment {
    private static String TAG = "GRAFICO";
    private static int NumeroGrafico;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = this.getActivity();
        VariabiliStaticheGrafici.getInstance().setContext(context);

        View view=null;

        try {
            view=(inflater.inflate(R.layout.grafici, container, false));
        } catch (Exception ignored) {

        }

        if (view!=null) {
            VariabiliStaticheGlobali.getInstance().setViewActivity(view);

            Bundle args = getArguments();
            NumeroGrafico = args.getInt("NumeroGrafico", -1);

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
            DBRemotoStatistiche dbr = new DBRemotoStatistiche();

            VariabiliStaticheGrafici.getInstance().setAnyChartView1((AnyChartView) view.findViewById(R.id.any_chart_view1));

            VariabiliStaticheGrafici.getInstance().setTxtTitolo1((TextView) view.findViewById(R.id.txtTitolo1));

            // LinearLayout layScelte = view.findViewById(R.id.layScelte);
            // layScelte.setVisibility(LinearLayout.GONE);

            switch (NumeroGrafico) {
                case 1:
                    VariabiliStaticheGrafici.getInstance().getTxtTitolo1().setText("Minuti goal fatti");
                    if (VariabiliStaticheGrafici.getInstance().getFattiMinuto()!=null) {
                        dbr.RitornaStatisticheMinutiGoal(context, TAG + "_1", "S", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta));
                    } else {
                        DisegnaGraficoGoalFattiPerMinuto();
                    }
                    break;
                case 2:
                    VariabiliStaticheGrafici.getInstance().getTxtTitolo1().setText("Minuti goal subiti");
                    if (VariabiliStaticheGrafici.getInstance().getSubitiMinuto()!=null) {
                        dbr.RitornaStatisticheMinutiGoal(context, TAG + "_2", "S", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta));
                    } else  {
                        DisegnaGraficoGoalSubitiPerMinuto();
                    }
                    break;
                case 3:
                    VariabiliStaticheGrafici.getInstance().tipoGraficoMeteo=1;
                    VariabiliStaticheGrafici.getInstance().getTxtTitolo1().setText("Statistiche tempo");
                    if (VariabiliStaticheGrafici.getInstance().getGradi()!=null) {
                        dbr.RitornaStatisticheMeteo(context, TAG + "_3", "S", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta));
                    } else {
                        DisegnaGraficoMeteo1();
                    }
                    break;
                case 4:
                    VariabiliStaticheGrafici.getInstance().getTxtTitolo1().setText("Statistiche Tipologia tempo");
                    if (VariabiliStaticheGrafici.getInstance().getTempo()!=null) {
                        dbr.RitornaStatisticheMeteo(context, TAG + "_4", "S", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta));
                    } else {
                        DisegnaGraficoMeteo2();
                    }
                    break;
                case 5:
                    VariabiliStaticheGrafici.getInstance().getTxtTitolo1().setText("Statistiche goal segnati");
                    if (VariabiliStaticheGrafici.getInstance().getGoalSegnati()!=null) {
                        dbr.RitornaStatisticheGoalSegnatiSubiti(context, TAG + "_5", "S", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta));
                    } else {
                        DisegnaGraficoGoalSegnati();
                    }
                    break;
                case 6:
                    VariabiliStaticheGrafici.getInstance().getTxtTitolo1().setText("Statistiche goal subiti");
                    if (VariabiliStaticheGrafici.getInstance().getGoalSubiti()!=null) {
                        dbr.RitornaStatisticheGoalSegnatiSubiti(context, TAG + "_6", "S", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta));
                    } else {
                        DisegnaGraficoGoalSubiti();
                    }
                    break;
                case 7:
                    VariabiliStaticheGrafici.getInstance().getTxtTitolo1().setText("Andamento punti");
                    if (VariabiliStaticheGrafici.getInstance().getAndamento()!=null) {
                        dbr.RitornaAndamento(context, TAG + "_7", "S", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta));
                    } else {
                        DisegnaGraficoAndamento();
                    }
                    break;
                case 8:
                    VariabiliStaticheGrafici.getInstance().getTxtTitolo1().setText("Tipologia partite");
                    if (VariabiliStaticheGrafici.getInstance().getTipologiaPartite()!=null) {
                        dbr.RitornaTipologiePartite(context, TAG + "_8", "S", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta));
                    } else {
                        DisegnaGraficoTipologiaPartite();
                    }
                    break;
                case 9:
                    VariabiliStaticheGrafici.getInstance().getTxtTitolo1().setText("Casa / Fuori");
                    if (VariabiliStaticheGrafici.getInstance().getCasaFuori()!=null) {
                        dbr.RitornaPartiteCasaFuori(context, TAG + "_9", "S", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta));
                    } else {
                        DisegnaGraficoCasaFuori();
                    }
                    break;
                case 10:
                    VariabiliStaticheGrafici.getInstance().getTxtTitolo1().setText("Allenatori");
                    if (VariabiliStaticheGrafici.getInstance().getAllenatori()!=null) {
                        dbr.RitornaPartiteAllenatore(context, TAG + "_10", "S", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta));
                    } else {
                        DisegnaGraficoAllenatori();
                    }
                    break;
            }

            android.support.design.widget.FloatingActionButton btnBack=view.findViewById(R.id.fabBackMappa);
            btnBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Utility.getInstance().CambiaMaschera(R.id.statistiche , -1, -1);
                }
            });
        }
    }

    public static void DisegnaGraficoMeteo1() {
        List<Float> l=new ArrayList<>();
        String tit = "";

        switch (VariabiliStaticheGrafici.getInstance().tipoGraficoMeteo) {
            case 1:
                VariabiliStaticheGrafici.getInstance().getTxtTitolo1().setText("Gradi");
                tit="Gradi";
                l=VariabiliStaticheGrafici.getInstance().getGradi();
                break;
            case 2:
                VariabiliStaticheGrafici.getInstance().getTxtTitolo1().setText("Umidità");
                tit="Umidità";
                l=VariabiliStaticheGrafici.getInstance().getUmidita();
                break;
            case 3:
                VariabiliStaticheGrafici.getInstance().getTxtTitolo1().setText("Pressione");
                tit="Pressione";
                l=VariabiliStaticheGrafici.getInstance().getPressione();
                break;
        }

        Cartesian pie1 = AnyChart.line();

        List<DataEntry> data = new ArrayList<>();
        int i=0;
        for (Float s : l) {
            data.add(new ValueDataEntry(i, s));
            i++;
        }

        pie1.animation(true);
        pie1.title(tit);
        pie1.xAxis(0).title("Partita");
        pie1.yAxis(0).title(tit);
        pie1.data(data);

        VariabiliStaticheGrafici.getInstance().getAnyChartView1().setChart(pie1);
    }

    public static void DisegnaGraficoMeteo2() {
        Cartesian pie = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        int i=0;
        int min=999;
        int max=0;
        for (String s : VariabiliStaticheGrafici.getInstance().getTempo()) {
            int q = VariabiliStaticheGrafici.getInstance().getQuanti().get(i);
            if (q<min) {
                min=q;
            }
            if (q>max) {
                max=q;
            }
            data.add(new ValueDataEntry(s, q));
            i++;
        }

        pie.animation(true);
        pie.title("Tipologia di tempo");
        pie.xAxis(0).title("Tempo");
        pie.yAxis(0).title("Volte");
        pie.data(data);

        VariabiliStaticheGrafici.getInstance().getAnyChartView1().setChart(pie);
        VariabiliStaticheGrafici.getInstance().getAnyChartView1().invalidate();
    }

    public static void DisegnaGraficoGoalFattiPerMinuto() {
        Cartesian pie = AnyChart.line();

        List<DataEntry> data = new ArrayList<>();
        int i=0;
        int min=999;
        int max=0;
        for (Integer s : VariabiliStaticheGrafici.getInstance().getFattiMinuto()) {
            int q = VariabiliStaticheGrafici.getInstance().getFattiQuanti().get(i);
            if (q<min) {
                min=q;
            }
            if (q>max) {
                max=q;
            }
            data.add(new ValueDataEntry(Integer.toString(s), q));
            i++;
        }

        pie.animation(true);
        pie.title("Goals fatti per minuto");
        //pie.yScale().minimum(0);
        //pie.yScale().maximum(min+1);
        pie.xAxis(0).title("Minuto");
        pie.yAxis(0).title("Quanti");
        pie.data(data);

        VariabiliStaticheGrafici.getInstance().getAnyChartView1().setChart(pie);
        VariabiliStaticheGrafici.getInstance().getAnyChartView1().invalidate();
    }

    public static void DisegnaGraficoGoalSubitiPerMinuto() {
        Cartesian pie = AnyChart.line();

        List<DataEntry> data = new ArrayList<>();
        int i=0;
        int min=999;
        int max=0;
        for (Integer s : VariabiliStaticheGrafici.getInstance().getSubitiMinuto()) {
            int q = VariabiliStaticheGrafici.getInstance().getSubitiQuanti().get(i);
            if (q<min) {
                min=q;
            }
            if (q>max) {
                max=q;
            }
            data.add(new ValueDataEntry(Integer.toString(s), q));
            i++;
        }

        pie.animation(true);
        pie.title("Goals subiti per minuto");
        //pie.yScale().minimum(0);
        //pie.yScale().maximum(min+1);
        pie.xAxis(0).title("Minuto");
        pie.yAxis(0).title("Quanti");
        pie.data(data);

        VariabiliStaticheGrafici.getInstance().getAnyChartView1().setChart(pie);
        VariabiliStaticheGrafici.getInstance().getAnyChartView1().invalidate();
    }

    public static void DisegnaGraficoGoalSegnati() {
        Cartesian pie = AnyChart.line();

        List<DataEntry> data = new ArrayList<>();
        int i=0;
        int min=999;
        int max=0;
        for (Integer s : VariabiliStaticheGrafici.getInstance().getGoalSegnatiPartita()) {
            int q = VariabiliStaticheGrafici.getInstance().getGoalSegnati().get(i);
            if (q<min) {
                min=q;
            }
            if (q>max) {
                max=q;
            }
            data.add(new ValueDataEntry(Integer.toString(s), q));
            i++;
        }

        pie.animation(true);
        pie.title("Goals segnati");
        //pie.yScale().minimum(0);
        //pie.yScale().maximum(min+1);
        pie.xAxis(0).title("Partita");
        pie.yAxis(0).title("Quanti");
        pie.data(data);

        VariabiliStaticheGrafici.getInstance().getAnyChartView1().setChart(pie);
        VariabiliStaticheGrafici.getInstance().getAnyChartView1().invalidate();
    }

    public static void DisegnaGraficoGoalSubiti() {
        Cartesian pie = AnyChart.line();

        List<DataEntry> data = new ArrayList<>();
        int i=0;
        int min=999;
        int max=0;
        for (Integer s : VariabiliStaticheGrafici.getInstance().getGoalSubitiPartita()) {
            int q = VariabiliStaticheGrafici.getInstance().getGoalSubiti().get(i);
            if (q<min) {
                min=q;
            }
            if (q>max) {
                max=q;
            }
            data.add(new ValueDataEntry(Integer.toString(s), q));
            i++;
        }

        pie.animation(true);
        pie.title("Goals subiti");
        //pie.yScale().minimum(0);
        //pie.yScale().maximum(min+1);
        pie.xAxis(0).title("Partita");
        pie.yAxis(0).title("Quanti");
        pie.data(data);

        VariabiliStaticheGrafici.getInstance().getAnyChartView1().setChart(pie);
        VariabiliStaticheGrafici.getInstance().getAnyChartView1().invalidate();
    }

    public static void DisegnaGraficoAndamento() {
        Cartesian pie = AnyChart.line();

        List<DataEntry> data = new ArrayList<>();
        int i=0;
        int min=999;
        int max=0;
        for (Integer s : VariabiliStaticheGrafici.getInstance().getAndamentoPartita()) {
            int q = VariabiliStaticheGrafici.getInstance().getAndamento().get(i);
            if (q<min) {
                min=q;
            }
            if (q>max) {
                max=q;
            }
            data.add(new ValueDataEntry(Integer.toString(s), q));
            i++;
        }

        pie.animation(true);
        pie.title("Andamento");
        //pie.yScale().minimum(0);
        //pie.yScale().maximum(min+1);
        pie.xAxis(0).title("Partita");
        pie.yAxis(0).title("Punti");
        pie.data(data);

        VariabiliStaticheGrafici.getInstance().getAnyChartView1().setChart(pie);
        VariabiliStaticheGrafici.getInstance().getAnyChartView1().invalidate();
    }

    public static void DisegnaGraficoTipologiaPartite() {
        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        int i=0;
        int min=999;
        int max=0;
        for (String s : VariabiliStaticheGrafici.getInstance().getTipologiaPartite()) {
            int q = VariabiliStaticheGrafici.getInstance().getqTipologiaPartite().get(i);
            if (q<min) {
                min=q;
            }
            if (q>max) {
                max=q;
            }
            data.add(new ValueDataEntry(s, q));
            i++;
        }

        pie.animation(true);
        pie.title("Tipologia partite");
        //pie.yScale().minimum(0);
        //pie.yScale().maximum(min+1);
        // pie.xAxis(0).title("Partita");
        // pie.yAxis(0).title("Punti");
        pie.data(data);

        VariabiliStaticheGrafici.getInstance().getAnyChartView1().setChart(pie);
        VariabiliStaticheGrafici.getInstance().getAnyChartView1().invalidate();
    }

    public static void DisegnaGraficoCasaFuori() {
        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        int i=0;
        int min=999;
        int max=0;
        for (String s : VariabiliStaticheGrafici.getInstance().getCasaFuori()) {
            int q = VariabiliStaticheGrafici.getInstance().getqCasaFuori().get(i);
            if (q<min) {
                min=q;
            }
            if (q>max) {
                max=q;
            }
            data.add(new ValueDataEntry(s, q));
            i++;
        }

        pie.animation(true);
        pie.title("Casa / fuori");
        //pie.yScale().minimum(0);
        //pie.yScale().maximum(min+1);
        // pie.xAxis(0).title("Partita");
        // pie.yAxis(0).title("Punti");
        pie.data(data);

        VariabiliStaticheGrafici.getInstance().getAnyChartView1().setChart(pie);
        VariabiliStaticheGrafici.getInstance().getAnyChartView1().invalidate();
    }

    public static void DisegnaGraficoAllenatori() {
        Pie pie = AnyChart.pie();

        List<DataEntry> data = new ArrayList<>();
        int i=0;
        int min=999;
        int max=0;
        for (String s : VariabiliStaticheGrafici.getInstance().getAllenatori()) {
            int q = VariabiliStaticheGrafici.getInstance().getqAllenatori().get(i);
            if (q<min) {
                min=q;
            }
            if (q>max) {
                max=q;
            }
            data.add(new ValueDataEntry(s, q));
            i++;
        }

        pie.animation(true);
        pie.title("Allenatori");
        //pie.yScale().minimum(0);
        //pie.yScale().maximum(min+1);
        // pie.xAxis(0).title("Partita");
        // pie.yAxis(0).title("Punti");
        pie.data(data);

        VariabiliStaticheGrafici.getInstance().getAnyChartView1().setChart(pie);
        VariabiliStaticheGrafici.getInstance().getAnyChartView1().invalidate();
    }
}
