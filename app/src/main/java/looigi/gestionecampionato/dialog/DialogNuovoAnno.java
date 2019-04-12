package looigi.gestionecampionato.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovoAnno;
import looigi.gestionecampionato.db_remoto.DBRemotoGenerale;

public class DialogNuovoAnno
{
    //-------- Singleton ----------//
    private static DialogNuovoAnno instance = null;
    private String Message;
    private Context context;
    private String Codice;

    private DialogNuovoAnno() {
    }

    public static DialogNuovoAnno getInstance() {
        if (instance == null) instance = new DialogNuovoAnno();
        return instance;
    }

    //-------- Variables ----------//
    private Dialog dialog;

    //-------- Methods ----------//
    public void show(Context a, String message)
    {
        this.context=a;
        this.Codice=Codice;

        Message = message;

        create(a);
    }

    private void create(Context context)
    {
        View inflate = View.inflate(context, R.layout.dialog_messaggio, null);
        TextView txtLog = inflate.findViewById(R.id.dialog_tp_log);

        txtLog.setText(Message);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setView(inflate);
        builder.setIcon(R.drawable.domanda);
        builder.setTitle(VariabiliStaticheGlobali.NomeApplicazione);
        builder.setPositiveButton("Ok", onClickOK);
        builder.setNegativeButton("Annulla", onClickAnnulla);

        AlertDialog alert = builder.create();
        alert.show();
    }

    private DialogInterface.OnClickListener onClickAnnulla = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.cancel();
        }
    };

    private DialogInterface.OnClickListener onClickOK = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            dialog.cancel();

            String idAnno = VariabiliStaticheNuovoAnno.getInstance().getTxtAnno().getText().toString().trim();
            String descAnno = VariabiliStaticheNuovoAnno.getInstance().getEdtAnno().getText().toString().trim();
            String nomeSquadra = VariabiliStaticheNuovoAnno.getInstance().getEdtNomeSquadra().getText().toString().trim();

            DBRemotoGenerale dbr = new DBRemotoGenerale();
            dbr.CreaNuovoAnno(context, idAnno, descAnno, nomeSquadra);
        }
    };
}
