package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.utilities.TimerTempo;
import looigi.gestionecampionato.utilities.Utility;

public class AdapterEventi extends ArrayAdapter
{
	private Context context;
	private List<String> lista;

	public AdapterEventi(Context context, int textViewResourceId, List<String> objects)
	{	
		super(context, textViewResourceId, objects);
		
		this.context = context;
		this.lista=objects;
	}
	
	@Override
	@Nullable
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.listview_eventi, null);

		String riga = lista.get(position);
		final String[] Campi=riga.split(";", -1);

		TextView txtEvento = convertView.findViewById(R.id.txtEvento);
		txtEvento.setText(Campi[1]);

		convertView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String idDesc = Campi[0];
				String desc = Campi[1];
				int tempo = VariabiliStaticheNuovaPartita.getInstance().getQualeTempoEvento();
				int minuto = TimerTempo.getInstance().RitornaMinuto(tempo);

				if (VariabiliStaticheNuovaPartita.getInstance().getMinutoEvento().isEmpty()) {
					VariabiliStaticheNuovaPartita.getInstance().setMinutoEvento(Integer.toString(minuto));
				}
				VariabiliStaticheNuovaPartita.getInstance().setDescEvento(desc);
                VariabiliStaticheNuovaPartita.getInstance().setIdEvento(Integer.parseInt(idDesc));

				VariabiliStaticheNuovaPartita.getInstance().StampaEvento();
			}
		});

		return convertView;
	}
}
