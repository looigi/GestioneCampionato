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

import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.maschere.NuovaPartita;
import looigi.gestionecampionato.utilities.Utility;

public class AdapterDirigentiConvocati extends ArrayAdapter
{
	private Context context;
	private List<String> lista;

	public AdapterDirigentiConvocati(Context context, int textViewResourceId, List<String> objects)
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
		convertView = inflater.inflate(R.layout.listview_dirigenti, null);

		if (Utility.getInstance().ePari(position)) {
			convertView.setBackgroundColor(Color.WHITE);
		} else {
			convertView.setBackgroundColor(Color.argb(255,230,230,230));
		}

		String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

		TextView txtCognome = convertView.findViewById(R.id.Cognome);
		TextView txtNome = convertView.findViewById(R.id.Nome);
		ImageView imgDirigente = convertView.findViewById(R.id.imgDirigente);
		TextView txtId = convertView.findViewById(R.id.idDirigente);
		TextView txtEMail = convertView.findViewById(R.id.EMail);
		TextView txtTelefono = convertView.findViewById(R.id.Telefono);

		String idDirig = "";
		String Cognome = "";
		String Nome = "";
		String EMail = "";
		String Telefono = "";

		if (lista.get(position).contains(";")) {
			String riga = lista.get(position);
			String[] Campi = riga.split(";", -1);

			idDirig = Campi[0];
			Cognome = Campi[1];
			Nome = Campi[2];
			EMail = Campi[3];
			Telefono = Campi[4];
		} else {
			idDirig=Integer.toString(VariabiliStaticheNuovaPartita.getInstance().getIdDirigenteSelezionato().get(position));

			String[] Campi=lista.get(position).split(" ");

			Cognome = Campi[0];
			if (Campi.length>2) {
				Cognome=Campi[0]+" "+Campi[1];
				Nome=Campi[2];
			} else {
				Nome = Campi[1];
			}
		}

		txtId.setText(idDirig);
		txtCognome.setText(Cognome);
		txtNome.setText(Nome);
		txtEMail.setText(EMail);
		txtTelefono.setText(Telefono);

		Utility.getInstance().PrendeImmagineDirigente(idDirig, imgDirigente);

		if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
			convertView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					VariabiliStaticheNuovaPartita.getInstance().getDirigenteSelezionato().remove(position);
					VariabiliStaticheNuovaPartita.getInstance().getIdDirigenteSelezionato().remove(position);

					NuovaPartita.RiempieListaDirigentiSelezionati();
				}
			});
		}

		return convertView;
	}
}
