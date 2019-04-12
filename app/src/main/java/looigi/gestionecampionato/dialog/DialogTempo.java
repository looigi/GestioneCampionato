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
import looigi.gestionecampionato.utilities.HelperKeyboard;
import looigi.gestionecampionato.utilities.TimerTempo;

public class DialogTempo
{
    //-------- Singleton ----------//
    private static DialogTempo instance = null;
    private String Message;
    private String TAG="DialogTempo";
    private boolean Error;
    private int position;
    private String titleDialog;
    private EditText edtValore;
    private int tempo;
    private String Minuto;
    private Context context;
    private TextView txtDestinazione;

    private DialogTempo() {
    }

    public static DialogTempo getInstance() {
        if (instance == null) instance = new DialogTempo();
        return instance;
    }

    //-------- Variables ----------//
    private Dialog dialog;

    //-------- Methods ----------//
    public void show(Context a, String message, boolean Error, String titleDialog, int Position, int Tempo, String Minuto, TextView t)
    {
        this.Error=Error;
        this.context=a;
        this.tempo=Tempo;
        this.position=Position;
        this.titleDialog=titleDialog;
        this.Minuto=Minuto;
        this.txtDestinazione=t;

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

            int minuti = 0;
            int secondi = 0;

            String tempoEditato=edtValore.getText().toString();
            if (!tempoEditato.contains(":")) {

            } else {
                String t[] = tempoEditato.split(":",-1);

                try {
                    minuti = Integer.parseInt(t[0]);
                    secondi = Integer.parseInt(t[1]);
                } catch (Exception ignored) {

                }

                String tt=String.format("%02d", minuti) + ":" + String.format("%02d", secondi);
                txtDestinazione.setText(tt);

                switch(tempo)  {
                    case 1:
                        TimerTempo.getInstance().SetTempo(1, tt);
                        break;
                    case 2:
                        TimerTempo.getInstance().SetTempo(2, tt);
                        break;
                    case 3:
                        TimerTempo.getInstance().SetTempo(3, tt);
                        break;
                }

                HelperKeyboard.closeKeyboard(context);
                dialog.cancel();
            }
        }
    };
}
