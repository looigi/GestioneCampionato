package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheRose;
import looigi.gestionecampionato.db_remoto.DBRemotoGiocatori;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.utilities.Utility;
import uk.co.senab.photoview.PhotoViewAttacher;

public class AdapterGiocatori extends ArrayAdapter
{
	private Context context;
	private List<String> lista;

	public AdapterGiocatori(Context context, int textViewResourceId, List<String> objects)
	{	
		super(context, textViewResourceId, objects);
		
		this.context = context;
		this.lista=objects;
	}
	
	@Override
	@Nullable
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		final VariabiliStaticheRose vv = VariabiliStaticheRose.getInstance();
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.listview_giocatori, null);

		String riga = lista.get(position);

		TextView id = convertView.findViewById(R.id.id);
		TextView ruolo = convertView.findViewById(R.id.ruolo);
		TextView datanasc = convertView.findViewById(R.id.datanasc);
		TextView name = convertView.findViewById(R.id.name);
		TextView numero = convertView.findViewById(R.id.txtNumero);
		TextView categoria1 = convertView.findViewById(R.id.categoria1);
		TextView categoria2 = convertView.findViewById(R.id.categoria2);
		TextView categoria3 = convertView.findViewById(R.id.categoria3);
		ImageView imgGiocatore = convertView.findViewById(R.id.img);
		
		String[] Campi = riga.split(";");

		id.setText(Campi[0]);
		ruolo.setText(Campi[4]);
		name.setText(Campi[2]+" "+Campi[3]);
		numero.setText(Campi[14]);
		categoria1.setText(Campi[20]);
		categoria2.setText(Campi[17]);
		categoria3.setText(Campi[19]);
		datanasc.setText(Campi[8]);

		Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.sconosciuto);
		imgGiocatore.setImageBitmap(bm);

		Utility.getInstance().PrendeImmagineGiocatore(id.getText().toString(), imgGiocatore);

		final String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

		convertView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String c[] = vv.getGiocatori().get(position).split(";", -1);

				vv.idGiocatoreScelto = Integer.parseInt(c[0]);

				// if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
					vv.getTxtId().setText(c[0]);
					vv.getEdtCognome().setText(c[2]);
					vv.getEdtNome().setText(c[3]);
					vv.getEdtEMail().setText(c[5]);
					vv.getEdttelefono().setText(c[6]);
					vv.getEdtSoprannome().setText(c[7]);
					vv.getEdtDataNascita().setText(c[8]);
					vv.getEdtIndirizzo().setText(c[9]);
					vv.getEdtCodFisc().setText(c[10]);
					vv.getEdtCitta().setText(c[12]);
					vv.getEdtMatricola().setText(c[13]);
					vv.getEdtNumeroMaglia().setText(c[14]);
					if (c[11].equals("S")) {
						vv.getOptMaschio().setChecked(true);
						vv.getOptFemmina().setChecked(false);
					} else {
						vv.getOptMaschio().setChecked(false);
						vv.getOptFemmina().setChecked(true);
					}

					int pos = -1;
					for (String r : vv.getRuoli()) {
						pos++;
						if (r.contains(c[1] + ";")) {
							break;
						}
					}
					vv.getSpnRuoli().setSelection(pos);

					pos = Utility.getInstance().CercaESettaStringaInSpinner(vv.getSpnCategorie2(), c[17]);
					if (pos>-1) {
						vv.getSpnCategorie2().setSelection(pos);
						try {
							vv.idCategoriaScelta2 = Integer.parseInt(c[16]);
						} catch (Exception ignored) {
							vv.idCategoriaScelta2 = 0;
						}
					} else {
						vv.idCategoriaScelta2 = 0;
					}

					pos = Utility.getInstance().CercaESettaStringaInSpinner(vv.getSpnCategorie3(), c[19]);
					if (pos>-1) {
						vv.getSpnCategorie3().setSelection(pos);
						try {
							vv.idCategoriaScelta3 = Integer.parseInt(c[18]);
						} catch (Exception ignored) {
							vv.idCategoriaScelta3=0;
						}
					} else {
						vv.idCategoriaScelta3 = 0;
					}

					Utility.getInstance().PrendeImmagineGiocatore(c[0], vv.getImgGiocatore());

					PhotoViewAttacher photoAttacher;
					photoAttacher = new PhotoViewAttacher(vv.getImgGiocatore());
					photoAttacher.update();

					vv.getRlMaschera().setVisibility(RelativeLayout.VISIBLE);
					vv.getCmdElimina().setVisibility(LinearLayout.VISIBLE);

					vv.getCmdOk().setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
								String Cognome = vv.getEdtCognome().getText().toString();
								String Nome = vv.getEdtNome().getText().toString();
								String EMail = vv.getEdtEMail().getText().toString();
								String Telefono = vv.getEdttelefono().getText().toString();
								String Soprannome = vv.getEdtSoprannome().getText().toString();
								String DataNascita = vv.getEdtDataNascita().getText().toString();
								String Indirizzo = vv.getEdtIndirizzo().getText().toString();
								String CodFisc = vv.getEdtCodFisc().getText().toString();
								String Citta = vv.getEdtCitta().getText().toString();
								String Matricola = vv.getEdtMatricola().getText().toString();
								String NumeroMaglia = vv.getEdtNumeroMaglia().getText().toString();
								String idRuolo = "0"; //=Integer.toString(vv.idRuoloScelto);

								int pos = -1;
								for (String r : vv.getRuoli()) {
									pos++;
									if (r.contains(vv.getSpnRuoli().getSelectedItem().toString().trim())) {
										String rr[] = r.split(";", -1);
										idRuolo = rr[0];
										break;
									}
								}

								String Maschio = "N";
								if (vv.getOptMaschio().isChecked()) {
									Maschio = "S";
								}

								if (Cognome.isEmpty()) {
									DialogMessaggio.getInstance().show(vv.getContext(),
											"Inserire il cognome", true,
											VariabiliStaticheGlobali.NomeApplicazione);
								} else {
									if (Nome.isEmpty()) {
										DialogMessaggio.getInstance().show(vv.getContext(),
												"Inserire il nome", true,
												VariabiliStaticheGlobali.NomeApplicazione);
									} else {
										String id = vv.getTxtId().getText().toString();

										DBRemotoGiocatori dbr = new DBRemotoGiocatori();
										dbr.SalvaGiocatore(vv.getContext(),
												Integer.toString(vv.idCategoriaScelta1),
												id,
												idRuolo,
												Cognome,
												Nome,
												EMail,
												Telefono,
												Soprannome,
												DataNascita,
												Indirizzo,
												CodFisc,
												Maschio,
												Citta,
												Matricola,
												NumeroMaglia,
												"",
												NomiMaschere.getInstance().getRose(),
												Integer.toString(vv.idCategoriaScelta2),
												Integer.toString(vv.idCategoriaScelta3)
										);

										vv.getRlMaschera().setVisibility(RelativeLayout.GONE);
									}
								}
							} else {
								DialogMessaggio.getInstance().show(vv.getContext(),
										"Non si hanno i permessi per modificare il giocatore", true,
										VariabiliStaticheGlobali.NomeApplicazione);
							}
						}
					});

					vv.getCmdAnnulla().setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							vv.getRlMaschera().setVisibility(RelativeLayout.GONE);
						}
					});

					vv.getCmdElimina().setOnClickListener(new View.OnClickListener() {
						public void onClick(View v) {
							if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
								DialogElimina.getInstance().show(vv.getContext(),
										"Si vuole eliminare il giocatore selezionato ?",
										vv.getTxtId().getText().toString(),
										NomiMaschere.getInstance().getRose());
							} else {
								DialogMessaggio.getInstance().show(vv.getContext(),
										"Non si hanno i permessi per eliminare il giocatore", true,
										VariabiliStaticheGlobali.NomeApplicazione);
							}
						}
					});
				}
			// }
		});

		return convertView;
	}
}
