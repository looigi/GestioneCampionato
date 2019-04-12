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

import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheArbitri;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.db_remoto.DBRemotoArbitri;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.utilities.Utility;

public class AdapterArbitri extends ArrayAdapter
{
	private Context context;
	private List<String> lista;

	public AdapterArbitri(Context context, int textViewResourceId, List<String> objects)
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
		convertView = inflater.inflate(R.layout.listview_arbitri, null);

		if (Utility.getInstance().ePari(position)) {
			convertView.setBackgroundColor(Color.WHITE);
		} else {
			convertView.setBackgroundColor(Color.argb(255,230,230,230));
		}

		String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

		final TextView txtCognome = convertView.findViewById(R.id.Cognome);
		TextView txtNome = convertView.findViewById(R.id.Nome);
		ImageView imgArbitro = convertView.findViewById(R.id.imgArbitro);
		TextView txtId = convertView.findViewById(R.id.idArbitro);
		TextView txtEMail = convertView.findViewById(R.id.EMail);
		TextView txtTelefono = convertView.findViewById(R.id.Telefono);

		String idDirig = "";
		String Cognome = "";
		String Nome = "";
		String EMail = "";
		String Telefono = "";

		if (lista.get(position).contains(";")) {
			String riga = lista.get(position);
			String Campi[] = riga.split(";", -1);

			idDirig = Campi[0];
			Cognome = Campi[1];
			Nome = Campi[2];
			EMail = Campi[3];
			Telefono = Campi[4];
		} else {
			idDirig=Integer.toString(VariabiliStaticheNuovaPartita.getInstance().getIdArbitro().get(position));

			String Campi[]=lista.get(position).split(" ");

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

		Utility.getInstance().PrendeImmagineArbitro(idDirig, imgArbitro);

		if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
			convertView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (lista.get(position).contains(";")) {
						int idArbitro = VariabiliStaticheArbitri.getInstance().getIdArbitri().get(position);
						String c[] = VariabiliStaticheArbitri.getInstance().getArbitri().get(position).split(";", -1);

						VariabiliStaticheArbitri.getInstance().getTxtId().setText(Integer.toString(idArbitro));
						VariabiliStaticheArbitri.getInstance().getEdtCognome().setText(c[1]);
						VariabiliStaticheArbitri.getInstance().getEdtNome().setText(c[2]);
						VariabiliStaticheArbitri.getInstance().getEdtMail().setText(c[3]);
						VariabiliStaticheArbitri.getInstance().getEdtTelefono().setText(c[4]);

						Utility.getInstance().PrendeImmagineArbitro(Integer.toString(idArbitro), VariabiliStaticheArbitri.getInstance().getImgArbitro());

						VariabiliStaticheArbitri.getInstance().getRlMaschera().setVisibility(RelativeLayout.VISIBLE);
						VariabiliStaticheArbitri.getInstance().getCmdElimina().setVisibility(LinearLayout.VISIBLE);

						VariabiliStaticheArbitri.getInstance().getCmdOk().setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								String Cognome = VariabiliStaticheArbitri.getInstance().getEdtCognome().getText().toString();
								String Nome = VariabiliStaticheArbitri.getInstance().getEdtNome().getText().toString();
								String EMail = VariabiliStaticheArbitri.getInstance().getEdtMail().getText().toString();
								String Telefono = VariabiliStaticheArbitri.getInstance().getEdtTelefono().getText().toString();

								if (Cognome.isEmpty()) {
									DialogMessaggio.getInstance().show(VariabiliStaticheArbitri.getInstance().getContext(), "Inserire il cognome",
											true, VariabiliStaticheGlobali.NomeApplicazione);
								} else {
									if (Nome.isEmpty() && !Cognome.replace(" ","").toUpperCase().equals("AUTOARBITRAGGIO")) {
										DialogMessaggio.getInstance().show(VariabiliStaticheArbitri.getInstance().getContext(), "Inserire il nome",
												true, VariabiliStaticheGlobali.NomeApplicazione);
									} else {
										String id = VariabiliStaticheArbitri.getInstance().getTxtId().getText().toString();

										DBRemotoArbitri dbr = new DBRemotoArbitri();
										dbr.SalvaArbitro(context, Integer.toString(VariabiliStaticheArbitri.getInstance().idArbitroScelto),
												id, Cognome, Nome, EMail, Telefono, NomiMaschere.getInstance().getArbitri());
										VariabiliStaticheArbitri.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
									}
								}
							}
						});

						VariabiliStaticheArbitri.getInstance().getCmdAnnulla().setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								VariabiliStaticheArbitri.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
							}
						});

						VariabiliStaticheArbitri.getInstance().getCmdElimina().setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								String cognome = txtCognome.getText().toString().toUpperCase().replace(" ", "");
								if (cognome.equals("AUTORABITRAGGIO")) {
									DialogMessaggio.getInstance().show(VariabiliStaticheArbitri.getInstance().getContext(),
											"Non si può eliminare l'arbitro selezionato",
											true, VariabiliStaticheGlobali.NomeApplicazione);
								} else {
									DialogElimina.getInstance().show(VariabiliStaticheArbitri.getInstance().getContext(),
											"Si vuole eliminare l'Arbitro selezionato ?",
											VariabiliStaticheArbitri.getInstance().getTxtId().getText().toString(),
											NomiMaschere.getInstance().getArbitri());
								}
							}
						});
					} else {
						// Selezione arbitro
					}
				}
			});
		}

		return convertView;
	}
}
