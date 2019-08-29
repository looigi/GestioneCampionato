package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.StrutturaDatiUtente;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheUtenti;
import looigi.gestionecampionato.db_remoto.DBRemotoUtenti;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.utilities.Utility;

public class AdapterModificaUtenti extends ArrayAdapter
{
	private Context context;
	private List<String> lista;

	public AdapterModificaUtenti(Context context, int textViewResourceId, List<String> objects)
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
		convertView = inflater.inflate(R.layout.listview_utenti, null);

		if (Utility.getInstance().ePari(position)) {
			convertView.setBackgroundColor(Color.WHITE);
		} else {
			convertView.setBackgroundColor(Color.argb(255,230,230,230));
		}

		final VariabiliStaticheUtenti vv = VariabiliStaticheUtenti.getInstance();
		
		String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

		String riga = lista.get(position);
		String[] Campi=riga.split(";", -1);

		if (Campi.length>0 && !Campi[0].isEmpty()) {
			VariabiliStaticheUtenti.idAnnoScelto=Integer.parseInt(Campi[0]);
			final String idUtente = Campi[1];
			String Utente = Campi[2];
			String Cognome = Campi[3];
			String Nome = Campi[4];
			String EMail = Campi[5];
			String Squadra = Campi[6];
			String Tipologia = Campi[7];
			if (Tipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
				vv.getOptAdmin().setChecked(true);
			} else {
				vv.getOptUser().setChecked(true);
			}
			final String idCategoria = Campi[9];
			final String Categoria = Campi[10];

			TextView txtId = convertView.findViewById(R.id.idUtente);
			TextView txtUtente = convertView.findViewById(R.id.utente);
			TextView txtCognome = convertView.findViewById(R.id.Cognome);
			TextView txtNome = convertView.findViewById(R.id.Nome);
			TextView txtEMail = convertView.findViewById(R.id.EMail);
			TextView txtNomeSquadra = convertView.findViewById(R.id.NomeSquadra);
			TextView txtCategoria = convertView.findViewById(R.id.Categoria);
			TextView txtTipologia = convertView.findViewById(R.id.Tipologia);

			txtId.setText("ID:" + idUtente);
			txtUtente.setText("Utente: "+Utente);
			txtCognome.setText("Cognome: "+ Cognome);
			txtNome.setText("Nome: "+Nome);
			txtEMail.setText("Mail: "+EMail);
			txtNomeSquadra.setText("Squadra: "+Squadra);
			if (Tipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
				Tipologia="Amministratore";
			} else {
				Tipologia="Utente";
			}
			txtTipologia.setText("Tipologia: "+Tipologia);
			txtCategoria.setText("Categoria: "+Categoria);

			if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
				convertView.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
					String r = vv.getUtenti().get(position);
					String c[] = r.split(";", -1);

					vv.getRlMaschera().setVisibility(RelativeLayout.VISIBLE);

					final String idUtente = c[1];
					vv.getEdtUtente().setText(c[2]);
					vv.getEdtCognome().setText(c[3]);
					vv.getEdtNome().setText(c[4]);
					vv.getEdtMail().setText(c[5]);
					int pos = Utility.getInstance().CercaESettaStringaInSpinner(vv.getSpnNomeSquadra(), c[6]);
					if (pos>-1) {
						vv.getSpnNomeSquadra().setSelection(pos);
						VariabiliStaticheUtenti.idAnnoScelto = vv.getIdAnno().get(pos);
					}
					if (c[7].equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
						vv.getOptAdmin().setChecked(true);
					} else {
						vv.getOptUser().setChecked(true);
					}
					vv.getEdtPassword().setText(c[8]);

					vv.getCmdSalva().setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							String Utente = vv.getEdtUtente().getText().toString();
							String Cognome = vv.getEdtCognome().getText().toString();
							String Password = vv.getEdtPassword().getText().toString();
							String Nome = vv.getEdtNome().getText().toString();
							String EMail = vv.getEdtMail().getText().toString();
							String Categoria = Integer.toString(vv.getIdCategoriaScelta());
							Boolean Admin = vv.getOptAdmin().isChecked();
							String Tipologia = "2";
							if (Admin) {
								Tipologia="1";
							}

							if (Utente.isEmpty()) {
								DialogMessaggio.getInstance().show(vv.getContext(),
										"Inserire il nome utente", true, VariabiliStaticheGlobali.NomeApplicazione);
							} else {
								if (Cognome.isEmpty()) {
									DialogMessaggio.getInstance().show(vv.getContext(),
											"Inserire il cognome", true, VariabiliStaticheGlobali.NomeApplicazione);
								} else {
									if (Nome.isEmpty()) {
										DialogMessaggio.getInstance().show(vv.getContext(),
												"Inserire il nome", true, VariabiliStaticheGlobali.NomeApplicazione);
									} else {
										if (EMail.isEmpty()) {
											DialogMessaggio.getInstance().show(vv.getContext(),
													"Inserire la mail", true, VariabiliStaticheGlobali.NomeApplicazione);
										} else {
											if (Password.isEmpty()) {
												DialogMessaggio.getInstance().show(vv.getContext(),
														"Inserire la password", true, VariabiliStaticheGlobali.NomeApplicazione);
											} else {
												VariabiliStaticheGlobali.getInstance().getDatiUtente().setIdCategoria1(idCategoria);
												VariabiliStaticheGlobali.getInstance().getDatiUtente().setDescCategoria(Categoria);

												StrutturaDatiUtente s = new StrutturaDatiUtente();
												s.setIdUtente(idUtente);
												s.setUtente(Utente);
												s.setCognome(Cognome);
												s.setEMail(EMail);
												s.setNome(Nome);
												s.setPassword(Password);
												s.setIdCategoria1(Categoria);
												s.setIdTipologia(Tipologia);

												DBRemotoUtenti dbr = new DBRemotoUtenti();
												dbr.ModificaUtente(context,
														Integer.toString(VariabiliStaticheUtenti.idAnnoScelto),
														s,
														NomiMaschere.getInstance().getUtenti());

												vv.getRlMaschera().setVisibility(RelativeLayout.GONE);
											}
										}
									}
								}
							}
						}
					});

					vv.getCmdAnnulla().setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							vv.getRlMaschera().setVisibility(RelativeLayout.GONE);
						}
					});
					}
				});
			}
		}

		return convertView;
	}
}
