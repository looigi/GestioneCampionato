package looigi.gestionecampionato.utilities;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;

import java.util.Calendar;
import java.util.TimeZone;

public class MostraPannelloOra implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    private TextView _editText;
    private int _minutes;
    private int _hour;
    private View _MascheraContenuta;
    private RelativeLayout _MascheraContenitore;

    public MostraPannelloOra(Context context, TextView txt, RelativeLayout rl, View v)
    {
        this._editText = txt;
        this._MascheraContenuta =v;
        this._MascheraContenitore =rl;

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        TimePickerDialog dialog = new TimePickerDialog(VariabiliStaticheGlobali.getInstance().getContext(),
                this,
                calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
        dialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minutes) {
        _hour = hour;
        _minutes = minutes;

        updateDisplay();
    }

    @Override
    public void onClick(View v) {
    }

    private void updateDisplay() {
        _editText.setText(new StringBuilder()
                .append(_hour).append(":").append(_minutes).append(":00").append(" "));

        _MascheraContenuta.setVisibility(View.GONE);
        if (_MascheraContenitore != null) {
            _MascheraContenitore.setVisibility(RelativeLayout.GONE);
        }
    }
}

