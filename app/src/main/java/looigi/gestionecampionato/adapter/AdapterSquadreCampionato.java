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
import android.widget.TextView;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.StrutturaSquadreCampionato;
import looigi.gestionecampionato.dati.VariabiliStaticheCampionato;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.db_remoto.DBRemotoCampionato;
import looigi.gestionecampionato.utilities.Utility;

import java.util.List;

public class AdapterSquadreCampionato extends ArrayAdapter
{
	private Context context;
	private List<StrutturaSquadreCampionato> lista;

	public AdapterSquadreCampionato(Context context, int textViewResourceId, List<StrutturaSquadreCampionato> objects)
	{	
		super(context, textViewResourceId, objects);
		
		this.context = context;
		this.lista=objects;
	}
	
	@Override
	@Nullable
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		final VariabiliStaticheCampionato vv = VariabiliStaticheCampionato.getInstance();
		if (position<vv.getDatiCampionato().getSquadre().size()) {
			StrutturaSquadreCampionato s = vv.getDatiCampionato().getSquadre().get(position);

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.listview_avversari, null);

			String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
			// String riga = lista.get(position);
			// String[] Campi=riga.split(";",-1);

			final String idAvversario = Integer.toString(s.getIdSquadre());
			final String Avversario = s.getSquadre();
			final String Campo = s.getCampo();
			final Integer idCampo = s.getIdCampo();
			final String Indirizzo = s.getIndirizzoCampo();
			String l1 = s.getLat();
			String l2 = s.getLon();
			if (l1.length() > 10) {
				l1 = l1.substring(0, 10);
			}
			if (l2.length() > 10) {
				l2 = l2.substring(0, 10);
			}
			final String Lat = l1;
			final String Lon = l2;

			if (Integer.parseInt(idAvversario) < 0) {
				convertView.setBackgroundColor(Color.argb(255, 230, 130, 130));
			} else {
				if (Utility.getInstance().ePari(position)) {
					convertView.setBackgroundColor(Color.WHITE);
				} else {
					convertView.setBackgroundColor(Color.argb(255, 230, 230, 230));
				}
			}

			TextView txtAvversario = convertView.findViewById(R.id.Avversario);
			TextView txtCampo = convertView.findViewById(R.id.Campo);
			TextView txtIndirizzo = convertView.findViewById(R.id.Indirizzo);
			ImageView imgAvversario = convertView.findViewById(R.id.imgAvversario);
			TextView txtCoords = convertView.findViewById(R.id.txtCoords);
			ImageView imgStat = convertView.findViewById(R.id.imgStat);
			imgStat.setVisibility(LinearLayout.GONE);

			txtAvversario.setText(Avversario);
			txtCampo.setText(Campo);
			txtIndirizzo.setText(Indirizzo);
			txtCoords.setText(Lat + ";" + Lon);

			ImageView imgElimina = convertView.findViewById(R.id.imgElimina);
			if (Integer.parseInt(idAvversario) < 0 || Integer.parseInt(idTipologia) != Integer.parseInt(VariabiliStaticheGlobali.ValoreAmministratore)) {
				imgElimina.setVisibility(LinearLayout.GONE);
			} else {
				imgElimina.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						vv.setAvversarioScelto(idAvversario + ";" +
								Avversario + ";" +
								idCampo + ";" +
								Campo + ";" +
								Indirizzo + ";" +
								Lat + ";" +
								Lon + ";");

						DBRemotoCampionato dbr = new DBRemotoCampionato();
						dbr.EliminaSquadraAvversaria(vv.getContext(), Integer.toString(vv.getIdCategoriaScelta()), idAvversario);
					}
				});
			}

			Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.sconosciuto);
			imgAvversario.setImageBitmap(bm);

			if (Integer.parseInt(idAvversario) < 0) {
				Utility.getInstance().PrendeImmagineCategoria(Integer.toString(-Integer.parseInt(idAvversario)), imgAvversario);
			} else {
				Utility.getInstance().PrendeImmagineAvversario(idAvversario, imgAvversario);
			}
		}

		return convertView;
	}
}
