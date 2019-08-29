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

import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheAllenatori;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.db_remoto.DBRemotoAllenatori;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.utilities.Utility;

public class AdapterAllenatori extends ArrayAdapter
{
	private Context context;
	private List<String> lista;

	public AdapterAllenatori(Context context, int textViewResourceId, List<String> objects)
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
		convertView = inflater.inflate(R.layout.listview_allenatori, null);

		if (Utility.getInstance().ePari(position)) {
			convertView.setBackgroundColor(Color.WHITE);
		} else {
			convertView.setBackgroundColor(Color.argb(255,230,230,230));
		}

		String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

		String riga = lista.get(position);
		String[] Campi=riga.split(";",-1);

		final String idAllenatore = Campi[0];
		String Cognome = Campi[1];
		String Nome = Campi[2];
		String EMail = Campi[3];
		String Telefono = Campi[4];

		TextView txtId = convertView.findViewById(R.id.idAllenatore);
        TextView txtCognome = convertView.findViewById(R.id.Cognome);
        TextView txtNome = convertView.findViewById(R.id.Nome);
		TextView txtEMail = convertView.findViewById(R.id.EMail);
		TextView txtTelefono = convertView.findViewById(R.id.Telefono);
		ImageView imgAllenatore = convertView.findViewById(R.id.imgAllenatore);

		txtId.setText(idAllenatore);
		txtCognome.setText(Cognome);
		txtNome.setText(Nome);
		txtEMail.setText(EMail);
		txtTelefono.setText(Telefono);

		Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.sconosciuto);
		imgAllenatore.setImageBitmap(bm);

		Utility.getInstance().PrendeImmagineAllenatore(idAllenatore, imgAllenatore);

		if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
			convertView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					int idAllenatore = VariabiliStaticheAllenatori.getInstance().getIdAllenatori().get(position);
					String c[] = VariabiliStaticheAllenatori.getInstance().getAllenatori().get(position).split(";", -1);

					VariabiliStaticheAllenatori.getInstance().getTxtId().setText(Integer.toString(idAllenatore));
					VariabiliStaticheAllenatori.getInstance().getEdtCognome().setText(c[1]);
					VariabiliStaticheAllenatori.getInstance().getEdtNome().setText(c[2]);
					VariabiliStaticheAllenatori.getInstance().getEdtEMail().setText(c[3]);
					VariabiliStaticheAllenatori.getInstance().getEdttelefono().setText(c[4]);

					Utility.getInstance().PrendeImmagineAllenatore(Integer.toString(idAllenatore), VariabiliStaticheAllenatori.getInstance().getImgAllenatore());

					VariabiliStaticheAllenatori.getInstance().getRlMaschera().setVisibility(RelativeLayout.VISIBLE);
					VariabiliStaticheAllenatori.getInstance().getCmdElimina().setVisibility(LinearLayout.VISIBLE);

					VariabiliStaticheAllenatori.getInstance().getCmdOk().setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							String Cognome = VariabiliStaticheAllenatori.getInstance().getEdtCognome().getText().toString();
							String Nome = VariabiliStaticheAllenatori.getInstance().getEdtNome().getText().toString();
							String EMail = VariabiliStaticheAllenatori.getInstance().getEdtEMail().getText().toString();
							String Telefono = VariabiliStaticheAllenatori.getInstance().getEdttelefono().getText().toString();

							if (Cognome.isEmpty()) {
								DialogMessaggio.getInstance().show(VariabiliStaticheAllenatori.getInstance().getContext(), "Inserire il cognome", true, VariabiliStaticheGlobali.NomeApplicazione);
							} else {
								if (Nome.isEmpty()) {
									DialogMessaggio.getInstance().show(VariabiliStaticheAllenatori.getInstance().getContext(), "Inserire il nome", true, VariabiliStaticheGlobali.NomeApplicazione);
								} else {
									String id = VariabiliStaticheAllenatori.getInstance().getTxtId().getText().toString();

									DBRemotoAllenatori dbr = new DBRemotoAllenatori();
									dbr.SalvaAllenatore(context, Integer.toString(VariabiliStaticheAllenatori.getInstance().idCategoriaScelta),
											id, Cognome, Nome, EMail, Telefono, NomiMaschere.getInstance().getAllenatori());
									VariabiliStaticheAllenatori.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
								}
							}
						}
					});

					VariabiliStaticheAllenatori.getInstance().getCmdAnnulla().setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							VariabiliStaticheAllenatori.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
						}
					});

					VariabiliStaticheAllenatori.getInstance().getCmdElimina().setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							DialogElimina.getInstance().show(VariabiliStaticheAllenatori.getInstance().getContext(),
									"Si vuole eliminare l'allenatore selezionato ?",
									VariabiliStaticheAllenatori.getInstance().getTxtId().getText().toString(),
									NomiMaschere.getInstance().getAllenatori());
						}
					});
				}
			});
		}

		return convertView;
	}
}
