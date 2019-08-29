package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.maschere.NuovaPartita;
import looigi.gestionecampionato.utilities.TimerTempo;

public class AdapterEventiPartita extends ArrayAdapter
{
    private Context context;
    private List<String> lista;

    public AdapterEventiPartita(Context context, int textViewResourceId, List<String> objects)
    {
        super(context, textViewResourceId, objects);

        this.context = context;
        this.lista=objects;
    }

    @Override
    @Nullable
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview_eventi_partite, null);

        String riga = lista.get(position);
        final String[] Campi=riga.split(";", -1);

        String evento = Campi[0]+"° - "+Campi[2]+" "+ Campi[4];

        TextView txtEvento = convertView.findViewById(R.id.txtEvento);
        txtEvento.setText(evento);

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int tempo = VariabiliStaticheNuovaPartita.getInstance().getQualeTempoEvento();
                switch(tempo) {
                    case 1:
                        VariabiliStaticheNuovaPartita.getInstance().getEventiPrimoTempo().remove(position);
                        break;
                    case 2:
                        VariabiliStaticheNuovaPartita.getInstance().getEventiSecondoTempo().remove(position);
                        break;
                    case 3:
                        VariabiliStaticheNuovaPartita.getInstance().getEventiTerzoTempo().remove(position);
                        break;
                }

                NuovaPartita.fillSpinnerEventiTempi();

                return true;
            }
        });

        return convertView;
    }
}
