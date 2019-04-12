package looigi.gestionecampionato.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.maschere.NuovaPartita;
import looigi.gestionecampionato.utilities.HelperKeyboard;

public class DialogMinutoDelGoal
{
    //-------- Singleton ----------//
    private static DialogMinutoDelGoal instance = null;
    private String Message;
    private Boolean Avversari;
    private String TAG="DialogMessageP";
    private boolean Error;
    private int position;
    private String titleDialog;
    private EditText edtValore;
    private int tempo;
    private String Minuto;
    private Context context;

    private DialogMinutoDelGoal() {
    }

    public static DialogMinutoDelGoal getInstance() {
        if (instance == null) instance = new DialogMinutoDelGoal();
        return instance;
    }

    //-------- Variables ----------//
    private Dialog dialog;

    //-------- Methods ----------//
    public void show(Context a, String message, boolean Error, String titleDialog, int Position,
                     int Tempo, String Minuto, Boolean Avversari)
    {
        this.Error=Error;
        this.context=a;
        this.tempo=Tempo;
        this.position=Position;
        this.titleDialog=titleDialog;
        this.Minuto=Minuto;
        this.Avversari=Avversari;

        Message = message;

        create(a);
    }

    private void create(Context context)
    {
        View inflate = View.inflate(context, R.layout.dialog_minuto_del_goal, null);
        edtValore=inflate.findViewById(R.id.edtValore);
        edtValore.setText(Minuto);
        TextView txtLog = inflate.findViewById(R.id.dialog_tp_log);

        txtLog.setText(Message);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(inflate);
        builder.setTitle(titleDialog);
        builder.setNegativeButton("Annulla", onClickAnnulla);
        if (Error) {
            builder.setIcon(R.drawable.error);
            builder.setTitle(titleDialog);
        } else {
            builder.setIcon(R.drawable.completed);
            builder.setTitle(VariabiliStaticheGlobali.NomeApplicazione);
        }
        builder.setPositiveButton("Ok", onClickOK);

        AlertDialog alert = builder.create();
        alert.show();

        //Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        //pbutton.setTextColor(Color.argb(255,141,198,63));

        HelperKeyboard.openKeyboard(context);
    }

    private OnClickListener onClickAnnulla = new OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            HelperKeyboard.closeKeyboard(context);
            dialog.cancel();
        }
    };

    private OnClickListener onClickOK = new OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            //HelperKeyboard.closeKeyboard(context);

            String minuto =edtValore.getText().toString();
            String m = "";
            if (!Avversari) {
                m = VariabiliStaticheNuovaPartita.getInstance().getGiocatoriPerMarcature().get(position); //  + ";" + minuto + ";";
                String mm[] = m.split(";");
                mm[4] = minuto;
                m = "";
                for (String mmm : mm) {
                    m += mmm + ";";
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
                NuovaPartita.ScriveRisultato();
            } else {
                NuovaPartita.fillSpinnerMinutiGoalAvversari();
            }

            HelperKeyboard.closeKeyboard(context);
            dialog.cancel();
        }
    };
}
