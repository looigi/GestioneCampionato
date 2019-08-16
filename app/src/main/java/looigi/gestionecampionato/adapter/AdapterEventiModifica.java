package looigi.gestionecampionato.adapter;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheEventi;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.db_remoto.DBRemotoEventi;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.utilities.Utility;

public class AdapterEventiModifica extends ArrayAdapter
{
	private Context context;
	private List<String> lista;

	public AdapterEventiModifica(Context context, int textViewResourceId, List<String> objects)
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
		convertView = inflater.inflate(R.layout.listview_eventi, null);

		if (Utility.getInstance().ePari(position)) {
			convertView.setBackgroundColor(Color.WHITE);
		} else {
			convertView.setBackgroundColor(Color.argb(255,230,230,230));
		}

		String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

		TextView txtEvento = convertView.findViewById(R.id.txtEvento);
		TextView txtId = convertView.findViewById(R.id.txtIdEvento);

		String idEvento = "";
		String Evento = "";

		if (lista.get(position).contains(";")) {
			String riga = lista.get(position);
			String[] Campi = riga.split(";", -1);

			idEvento = Campi[0];
			Evento = Campi[1];
		}

		txtId.setText(idEvento);
		txtEvento.setText(Evento);

		final String idEvento2 = idEvento;

		if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
			convertView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (lista.get(position).contains(";")) {
						String[] c = lista.get(position).split(";", -1);

						VariabiliStaticheEventi.getInstance().getEdtEvento().setText(c[1]);

						VariabiliStaticheEventi.getInstance().getLayMascheraModEventi().setVisibility(RelativeLayout.VISIBLE);
						VariabiliStaticheEventi.getInstance().getCmdElimina().setVisibility(LinearLayout.VISIBLE);

						VariabiliStaticheEventi.getInstance().getCmdOk().setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								String Evento = VariabiliStaticheEventi.getInstance().getEdtEvento().getText().toString();

								if (Evento.isEmpty()) {
									DialogMessaggio.getInstance().show(VariabiliStaticheEventi.getInstance().getContext(), "Inserire il cognome",
											true, VariabiliStaticheGlobali.NomeApplicazione);
								} else {
									DBRemotoEventi dbr = new DBRemotoEventi();
									dbr.SalvaEvento(context, idEvento2, Evento, NomiMaschere.getInstance().getEventi());
									VariabiliStaticheEventi.getInstance().getLayMascheraModEventi().setVisibility(RelativeLayout.GONE);
								}
							}
						});

						VariabiliStaticheEventi.getInstance().getCmdAnnulla().setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								VariabiliStaticheEventi.getInstance().getLayMascheraModEventi().setVisibility(RelativeLayout.GONE);
							}
						});

						VariabiliStaticheEventi.getInstance().getCmdElimina().setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								DialogElimina.getInstance().show(VariabiliStaticheEventi.getInstance().getContext(),
										"Si vuole eliminare l'evento selezionato ?",
										idEvento2,
										NomiMaschere.getInstance().getEventi());
							}
						});
					}
				}
			});
		}

		return convertView;
	}
}
