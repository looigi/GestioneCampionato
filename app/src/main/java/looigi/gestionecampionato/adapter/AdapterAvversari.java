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

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheAvversari;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.db_remoto.DBRemotoAvversari;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.utilities.Utility;

import java.util.List;

public class AdapterAvversari extends ArrayAdapter
{
	private Context context;
	private List<String> lista;

	public AdapterAvversari(Context context, int textViewResourceId, List<String> objects)
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
		convertView = inflater.inflate(R.layout.listview_avversari, null);

		if (Utility.getInstance().ePari(position)) {
			convertView.setBackgroundColor(Color.WHITE);
		} else {
			convertView.setBackgroundColor(Color.argb(255,230,230,230));
		}

		String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

		String riga = lista.get(position);
		String[] Campi=riga.split(";",-1);

		final String idAvversario = Campi[0];
		String Avversario = Campi[2];
		String Campo = Campi[3];
		String Indirizzo = Campi[4];
        String Lat = Campi[5];
		String Lon = Campi[6];
		if (Lat.length()>10) { Lat = Lat.substring(0,10); }
		if (Lon.length()>10) { Lon = Lon.substring(0,10); }

        TextView txtAvversario = convertView.findViewById(R.id.Avversario);
        TextView txtCampo = convertView.findViewById(R.id.Campo);
		TextView txtIndirizzo = convertView.findViewById(R.id.Indirizzo);
		ImageView imgAvversario = convertView.findViewById(R.id.imgAvversario);
        TextView txtCoords = convertView.findViewById(R.id.txtCoords);
		ImageView imgElimina = convertView.findViewById(R.id.imgElimina);
		imgElimina.setVisibility(LinearLayout.GONE);
		ImageView imgStat = convertView.findViewById(R.id.imgStat);
		imgStat.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
				Utility.getInstance().PrendeImmagineAvversario(idAvversario, VariabiliStaticheAvversari.getInstance().getImgAvversarioStat());

				DBRemotoAvversari dbr = new DBRemotoAvversari();
				dbr.RitornaStatisticheAvversario(VariabiliStaticheAvversari.getInstance().getContext(), idAvversario);
				VariabiliStaticheAvversari.getInstance().getRlMascheraStat().setVisibility(RelativeLayout.VISIBLE);
		    }
	   	});

		txtAvversario.setText(Avversario);
		txtCampo.setText(Campo);
		txtIndirizzo.setText(Indirizzo);
		txtCoords.setText(Lat + ";" + Lon);

		Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.sconosciuto);
		imgAvversario.setImageBitmap(bm);

		Utility.getInstance().PrendeImmagineAvversario(Campi[0], imgAvversario);

		if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
			convertView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					String[] c = VariabiliStaticheAvversari.getInstance().getAvversari().get(position).split(";", -1);

					final String idAvversario = c[0];
					final String idCampo = c[1];
					VariabiliStaticheAvversari.getInstance().getEdtAvversario().setText(c[2]);
					VariabiliStaticheAvversari.getInstance().getEdtCampo().setText(c[3]);
					VariabiliStaticheAvversari.getInstance().getEdtIndirizzo().setText(c[4]);

					if (!c[5].isEmpty() && !c[6].isEmpty()) {
						VariabiliStaticheAvversari.getInstance().getTxtCoord().setText(c[5] + ";" + c[6] + ";");
					} else {
						VariabiliStaticheAvversari.getInstance().getTxtCoord().setText("");
					}

					VariabiliStaticheAvversari.getInstance().getTxtId().setText(idAvversario);

					Utility.getInstance().PrendeImmagineAvversario(idAvversario, VariabiliStaticheAvversari.getInstance().getImgAvversario());

					VariabiliStaticheAvversari.getInstance().getRlMaschera().setVisibility(RelativeLayout.VISIBLE);
					VariabiliStaticheAvversari.getInstance().getCmdElimina().setVisibility(LinearLayout.VISIBLE);

					VariabiliStaticheAvversari.getInstance().getCmdOk().setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							String Avversario = VariabiliStaticheAvversari.getInstance().getEdtAvversario().getText().toString();
							String Campo = VariabiliStaticheAvversari.getInstance().getEdtCampo().getText().toString();
							String Indirizzo = VariabiliStaticheAvversari.getInstance().getEdtIndirizzo().getText().toString();
							String Coords = VariabiliStaticheAvversari.getInstance().getTxtCoord().getText().toString();

							if (Avversario.isEmpty()) {
								DialogMessaggio.getInstance().show(VariabiliStaticheAvversari.getInstance().getContext(), "Inserire il nome dell'avversario", true, VariabiliStaticheGlobali.NomeApplicazione);
							} else {
								if (Campo.isEmpty()) {
									DialogMessaggio.getInstance().show(VariabiliStaticheAvversari.getInstance().getContext(), "Inserire il campo dell'avversario", true, VariabiliStaticheGlobali.NomeApplicazione);
								} else {
									if (Indirizzo.isEmpty()) {
										DialogMessaggio.getInstance().show(VariabiliStaticheAvversari.getInstance().getContext(), "Inserire l'indirizzo del campo dell'avversario", true, VariabiliStaticheGlobali.NomeApplicazione);
									} else {
										if (Coords.isEmpty()) {
											DialogMessaggio.getInstance().show(VariabiliStaticheAvversari.getInstance().getContext(), "Inserire le coordinate GPS del campo", true, VariabiliStaticheGlobali.NomeApplicazione);
										} else {
											String Ricerca = VariabiliStaticheAvversari.getInstance().getEdtRicerca().getText().toString();
											DBRemotoAvversari dbr = new DBRemotoAvversari();
											dbr.SalvaAvversario(context, idAvversario,
													idCampo, Avversario, Campo, Indirizzo, Ricerca, Coords,
													NomiMaschere.getInstance().getAllenatori());
											VariabiliStaticheAvversari.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
										}
									}
								}
							}
						}
					});

					VariabiliStaticheAvversari.getInstance().getCmdAnnulla().setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							VariabiliStaticheAvversari.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
						}
					});

					VariabiliStaticheAvversari.getInstance().getCmdElimina().setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							DialogElimina.getInstance().show(VariabiliStaticheAvversari.getInstance().getContext(),
									"Si vuole eliminare l'avversario selezionato ?",
									idAvversario,
									NomiMaschere.getInstance().getAvversari());
						}
					});

				}
			});
		}

		return convertView;
	}
}
