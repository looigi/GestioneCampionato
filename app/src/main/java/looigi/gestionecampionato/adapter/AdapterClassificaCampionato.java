package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.StrutturaSquadreClassifica;
import looigi.gestionecampionato.dati.VariabiliStaticheCampionato;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

import java.util.List;

public class AdapterClassificaCampionato extends ArrayAdapter
{
	private Context context;
	private List<StrutturaSquadreClassifica> lista;

	public AdapterClassificaCampionato(Context context, int textViewResourceId, List<StrutturaSquadreClassifica> objects)
	{	
		super(context, textViewResourceId, objects);
		
		this.context = context;
		this.lista=objects;
	}
	
	@Override
	@Nullable
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		VariabiliStaticheCampionato vv = VariabiliStaticheCampionato.getInstance();

		if (position<vv.getDatiCampionato().getSquadreClass().size()) {
			StrutturaSquadreClassifica s = vv.getDatiCampionato().getSquadreClass().get(position);

			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.listview_classifica_camp, null);

			String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

			// String riga = lista.get(position);

			Integer idAvversario = s.getIdSquadraClass();
			Integer Punti = s.getPunti();
			Integer Vinte = s.getVinte();
			Integer Pareggiate = s.getPareggiate();
			Integer Perse = s.getPerse();
			Integer gFatti = s.getGf();
			Integer gSubiti = s.getGs();
			Integer Giocate = s.getGiocate();
			String Avversario = s.getSquadreClass();

			if (idAvversario < 0) {
				convertView.setBackgroundColor(Color.argb(255, 230, 130, 130));
			} else {
				if (Utility.getInstance().ePari(position)) {
					convertView.setBackgroundColor(Color.WHITE);
				} else {
					convertView.setBackgroundColor(Color.argb(255, 230, 230, 230));
				}
			}

			TextView txtid = convertView.findViewById(R.id.idAvversario);
			TextView txtPosizione = convertView.findViewById(R.id.txtPosizione);
			TextView txtPunti = convertView.findViewById(R.id.txtPunti);
			TextView txtAvversario = convertView.findViewById(R.id.Avversario);
			ImageView imgAvversario = convertView.findViewById(R.id.imgAvversario);

			TextView txtVinte = convertView.findViewById(R.id.Vinte);
			TextView txtPareggiate = convertView.findViewById(R.id.Pareggiate);
			TextView txtPerse = convertView.findViewById(R.id.Perse);
			TextView txtgFatti = convertView.findViewById(R.id.Gf);
			TextView txtgSubiti = convertView.findViewById(R.id.Gs);
			TextView txtGiocate = convertView.findViewById(R.id.Giocate);

			txtid.setText(Integer.toString(idAvversario));
			txtPosizione.setText(Integer.toString(position + 1));
			txtPunti.setText(Integer.toString(Punti));
			txtAvversario.setText(Avversario);

			txtVinte.setText(Integer.toString(Vinte));
			txtPerse.setText(Integer.toString(Pareggiate));
			txtPareggiate.setText(Integer.toString(Perse));
			txtgFatti.setText(Integer.toString(gFatti));
			txtgSubiti.setText(Integer.toString(gSubiti));
			txtGiocate.setText(Integer.toString(Giocate));

			if (idAvversario > -1) {
				Utility.getInstance().PrendeImmagineAvversario(Integer.toString(idAvversario), imgAvversario);
			} else {
				Utility.getInstance().PrendeImmagineCategoria(Integer.toString(vv.getIdCategoriaScelta()), imgAvversario);
			}
		}

		return convertView;
	}
}
