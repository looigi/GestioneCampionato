package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.utilities.Utility;

public class AdapterGiocatore extends ArrayAdapter
{
	private Context context;
	private List<String> lista;

	public AdapterGiocatore(Context context, int textViewResourceId, List<String> objects)
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
		convertView = inflater.inflate(R.layout.listview_giocatore, null);

		String riga = lista.get(position);

		TextView id = convertView.findViewById(R.id.id);
        TextView ruolo = convertView.findViewById(R.id.ruolo);
        TextView name = convertView.findViewById(R.id.name);
        TextView minuto = convertView.findViewById(R.id.minuto);
		TextView numero = convertView.findViewById(R.id.txtNumero);
		ImageView imgGiocatore = convertView.findViewById(R.id.img);

		if (!riga.contains("Autorete")) {
			String[] Campi = riga.split(";");

			// if (Campi.length > 4) {
				id.setText(Campi[0]);
				ruolo.setText(Campi[1]);
				name.setText(Campi[2]);
				if (Campi.length>4) {
					if (Campi[4].isEmpty()) {
						minuto.setText("");
					} else {
						minuto.setText(Campi[4] + "°");
					}
					numero.setText(Campi[5]);
				} else {
					minuto.setText("");
					if (Campi.length>5) {
						numero.setText(Campi[5]);
					} else {
						numero.setText("0");
					}
				}
			// }

            Utility.getInstance().PrendeImmagineGiocatore(id.getText().toString(), imgGiocatore);
		} else {
		    if (riga.contains(";")) {
				String[] Campi = riga.split(";");

				if (riga.contains("Autorete")) {
					id.setText("");
					ruolo.setText("");
					name.setText(Campi[2]);
					numero.setText("");
					if (Campi.length>4) {
						try {
							minuto.setText(Campi[4] + "°");
						} catch (Exception ignored) {
							minuto.setText("");
						}
					} else {
						minuto.setText("");
					}
				} else {
					id.setText("");
					ruolo.setText("");
					name.setText(Campi[0]);
					numero.setText(Campi[4]);
					try {
						minuto.setText(Campi[1] + "°");
					} catch (Exception ignored) {
						minuto.setText("");
					}
				}
            } else {
                id.setText("");
                ruolo.setText("");
                minuto.setText("");
                name.setText(riga);
				numero.setText("");
            }

            imgGiocatore.setBackgroundResource(R.drawable.marcatore);
		}

		return convertView;
	}
}
