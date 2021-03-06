package looigi.gestionecampionato.utilities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;

import java.util.Calendar;
import java.util.TimeZone;

public class MostraPannelloData implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private TextView _editText;
    private int _day;
    private int _month;
    private int _birthYear;
    private View _MascheraContenuta;
    private RelativeLayout _MascheraContenitore;

    public MostraPannelloData(Context context, TextView txt, View v)
    {
        this._editText = txt;
        this._MascheraContenuta =v;
        this._MascheraContenitore = VariabiliStaticheNuovaPartita.getInstance().getRlMaschera();

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        DatePickerDialog dialog = new DatePickerDialog(VariabiliStaticheGlobali.getInstance().getContext(),
                this,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Annulla", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    _MascheraContenuta.setVisibility(View.GONE);
                    if (_MascheraContenitore != null) {
                        _MascheraContenitore.setVisibility(RelativeLayout.GONE);
                    }
                }
            }
        });

        dialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        _birthYear = year;
        _month = monthOfYear;
        _day = dayOfMonth;

        updateDisplay();
    }

    @Override
    public void onClick(View v) {
    }

    private void updateDisplay() {
        _editText.setText(new StringBuilder()
                .append(_day).append("/").append(_month + 1).append("/").append(_birthYear).append(" "));

        _MascheraContenuta.setVisibility(View.GONE);
        if (_MascheraContenitore != null) {
            _MascheraContenitore.setVisibility(RelativeLayout.GONE);
        }
    }
}

