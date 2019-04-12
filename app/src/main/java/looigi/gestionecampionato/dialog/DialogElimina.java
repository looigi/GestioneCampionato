package looigi.gestionecampionato.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.StrutturaPartita;
import looigi.gestionecampionato.dati.VariabiliStaticheAllenatori;
import looigi.gestionecampionato.dati.VariabiliStaticheAvversari;
import looigi.gestionecampionato.dati.VariabiliStaticheCampionato;
import looigi.gestionecampionato.dati.VariabiliStaticheCategorie;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheRose;
import looigi.gestionecampionato.db_remoto.DBRemotoAllenatori;
import looigi.gestionecampionato.db_remoto.DBRemotoAvversari;
import looigi.gestionecampionato.db_remoto.DBRemotoCampionato;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.db_remoto.DBRemotoGiocatori;
import looigi.gestionecampionato.db_remoto.DBRemotoMultimedia;

public class DialogElimina
{
    //-------- Singleton ----------//
    private static DialogElimina instance = null;
    private String Message;
    private Context context;
    private String Codice;
    private String Maschera;

    private DialogElimina() {
    }

    public static DialogElimina getInstance() {
        if (instance == null) instance = new DialogElimina();
        return instance;
    }

    //-------- Variables ----------//
    private Dialog dialog;

    //-------- Methods ----------//
    public void show(Context a, String message, String Codice, String Maschera)
    {
        this.context=a;
        this.Maschera=Maschera;
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

            if (Maschera.equals(NomiMaschere.getInstance().getAllenatori())) {
                DBRemotoAllenatori dba = new DBRemotoAllenatori();
                dba.EliminaAllenatore(VariabiliStaticheAllenatori.getInstance().getContext(),
                        Codice,
                        Maschera);
            }
            if (Maschera.equals(NomiMaschere.getInstance().getAllenatori()+"Immagine")) {
                DBRemotoMultimedia dbm = new DBRemotoMultimedia();
                dbm.EliminaImmagine(VariabiliStaticheAllenatori.getInstance().getContext(),
                        NomiMaschere.getInstance().getAllenatoriPerTitolo(),
                        Codice,
                        Maschera);
            }

            if (Maschera.equals(NomiMaschere.getInstance().getAvversari())) {
                DBRemotoAvversari dbav = new DBRemotoAvversari();
                dbav.EliminaAvversario(VariabiliStaticheAvversari.getInstance().getContext(),
                        Codice,
                        VariabiliStaticheAvversari.getInstance().getEdtRicerca().getText().toString(),
                        Maschera);
            }
            if (Maschera.equals(NomiMaschere.getInstance().getAvversari()+"Immagine")) {
                DBRemotoMultimedia dbm = new DBRemotoMultimedia();
                dbm.EliminaImmagine(VariabiliStaticheAvversari.getInstance().getContext(),
                        NomiMaschere.getInstance().getAvversariPerTitolo(),
                        Codice,
                        Maschera);
            }

            if (Maschera.equals(NomiMaschere.getInstance().getCategorie())) {
                DBRemotoCategorie dbc = new DBRemotoCategorie();
                dbc.EliminaCategoria(VariabiliStaticheCategorie.getInstance().getContext(),
                        Codice,
                        Maschera);
            }
            if (Maschera.equals(NomiMaschere.getInstance().getCategorie()+"Immagine")) {
                DBRemotoMultimedia dbm = new DBRemotoMultimedia();
                dbm.EliminaImmagine(VariabiliStaticheCategorie.getInstance().getContext(),
                        NomiMaschere.getInstance().getCategoriePerTitolo(),
                        Codice,
                        Maschera);
            }

            if (Maschera.equals(NomiMaschere.getInstance().getRose())) {
                DBRemotoGiocatori dbg = new DBRemotoGiocatori();
                dbg.EliminaGiocatore(VariabiliStaticheRose.getInstance().getContext(),
                        Codice,
                        Maschera);
            }
            if (Maschera.equals(NomiMaschere.getInstance().getRose()+"Immagine")) {
                DBRemotoMultimedia dbm = new DBRemotoMultimedia();
                dbm.EliminaImmagine(VariabiliStaticheRose.getInstance().getContext(),
                        NomiMaschere.getInstance().getRosePerTitolo(),
                        Codice,
                        Maschera);
            }
            if (Maschera.equals(NomiMaschere.getInstance().getCampionato())) {
                VariabiliStaticheCampionato vv = VariabiliStaticheCampionato.getInstance();
                StrutturaPartita lsp = vv.getDatiCampionato().getPartitaGiornata(vv.getDatiCampionato().getGiornataAttuale(),
                        Integer.parseInt(Codice));

                DBRemotoCampionato dbr = new DBRemotoCampionato();
                dbr.EliminaPartita(vv.getContext(),
                        Integer.toString(vv.getDatiCampionato().getGiornataAttuale()),
                        Integer.toString(vv.getIdCategoriaScelta()),
                        Integer.toString(lsp.getIdPartitaGen()));
            }
        }
    };
}
