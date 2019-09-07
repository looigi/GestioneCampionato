package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheAllenatori;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheStatAllenamenti;
import looigi.gestionecampionato.db_remoto.DBRemotoAllenatori;
import looigi.gestionecampionato.db_remoto.DBRemotoStatAllenamenti;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.utilities.Utility;

public class AdapterStatAllenamenti extends ArrayAdapter
{
	private Context context;
	private List<String> lista;

	public AdapterStatAllenamenti(Context context, int textViewResourceId, List<String> objects)
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
		convertView = inflater.inflate(R.layout.listview_stat_allen, null);

		if (Utility.getInstance().ePari(position)) {
			convertView.setBackgroundColor(Color.WHITE);
		} else {
			convertView.setBackgroundColor(Color.argb(255,230,230,230));
		}

		String riga = lista.get(position);
		final String[] Campi=riga.split(";",-1);

		((TextView) convertView.findViewById(R.id.id)).setText(Campi[0]);
		((TextView) convertView.findViewById(R.id.name)).setText(Campi[1] + " " + Campi[2]);
		((TextView) convertView.findViewById(R.id.ruolo)).setText(Campi[3]);

		((TextView) convertView.findViewById(R.id.presenze)).setText(Campi[4]);
		((TextView) convertView.findViewById(R.id.totale)).setText(Campi[5]);

		float i2 = Float.parseFloat(Campi[6].replace(",","."));
		DecimalFormat f = new DecimalFormat("##.00");
		String formattedValue = f.format(i2);

		((TextView) convertView.findViewById(R.id.perc)).setText(formattedValue);

		((TextView) convertView.findViewById(R.id.txtNumero)).setText(Campi[7]);

		ImageView imgGiocatore = convertView.findViewById(R.id.img);
		Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.sconosciuto);
		imgGiocatore.setImageBitmap(bm);
		Utility.getInstance().PrendeImmagineGiocatore(Campi[0], imgGiocatore);

		convertView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String Mese = VariabiliStaticheStatAllenamenti.getInstance().getSpnMesi().getSelectedItem().toString();

				DBRemotoStatAllenamenti dbr = new DBRemotoStatAllenamenti();
				dbr.RitornaInfo(VariabiliStaticheGlobali.getInstance().getContext(),
						Integer.toString(VariabiliStaticheStatAllenamenti.getInstance().idCategoriaScelta),
						Campi[0],
						Mese,
						"");
			}
		});

		return convertView;
	}
}
