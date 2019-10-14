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
import looigi.gestionecampionato.db_remoto.DBRemotoPartite;
import looigi.gestionecampionato.utilities.Utility;

public class DialogDomanda
{
    //-------- Singleton ----------//
    private static DialogDomanda instance = null;
    private String Message;
    private String TAG="DialogDomanda";
    private boolean Error;
    private String titleDialog;
    private EditText edtValore;
    private Context context;
    private String QualeMaschera;
    private String NumeroPartita;

    private DialogDomanda() {
    }

    public static DialogDomanda getInstance() {
        if (instance == null) instance = new DialogDomanda();
        return instance;
    }

    //-------- Variables ----------//
    private Dialog dialog;

    //-------- Methods ----------//
    public void show(Context a, String message, String titleDialog, String QualeMaschera, String numeroPartita)
    {
        this.Error=Error;
        this.context=a;
        this.titleDialog=titleDialog;
        this.QualeMaschera = QualeMaschera;
        this.NumeroPartita = numeroPartita;

        Message = message;

        create(a);
    }

    private void create(Context context)
    {
        View inflate = View.inflate(context, R.layout.dialog_messaggio, null);
        TextView txtLog = inflate.findViewById(R.id.dialog_tp_log);

        txtLog.setText(Message);

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(inflate);
        builder.setTitle(titleDialog);
        if (Error) {
            builder.setIcon(R.drawable.error);
            builder.setTitle(titleDialog);
        } else {
            builder.setIcon(R.drawable.completed);
            builder.setTitle(VariabiliStaticheGlobali.NomeApplicazione);
        }
        builder.setPositiveButton("Ok", onClickOK);
        builder.setNegativeButton("Annulla", onClickAnnulla);

        VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale().runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    private OnClickListener onClickAnnulla = new OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.cancel();
        }
    };

    private OnClickListener onClickOK = new OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            if (QualeMaschera.equals("NUOVA_PARTITA")) {
                Utility.getInstance().CambiaMaschera(R.id.home, -1, -1);
            } else {
                if (QualeMaschera.equals("ELIMINA PARTITA")) {
                    DBRemotoPartite dbr = new DBRemotoPartite();
                    dbr.EliminaPartita(VariabiliStaticheGlobali.getInstance().getContext(),
                            NumeroPartita, TAG);
                }
            }
            dialog.cancel();
        }
    };
}
