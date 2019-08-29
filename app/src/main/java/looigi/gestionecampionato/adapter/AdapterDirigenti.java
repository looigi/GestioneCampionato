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

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheDirigenti;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.db_remoto.DBRemotoDirigenti;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.NuovaPartita;
import looigi.gestionecampionato.utilities.Utility;

public class AdapterDirigenti extends ArrayAdapter
{
	private Context context;
	private List<String> lista;

	public AdapterDirigenti(Context context, int textViewResourceId, List<String> objects)
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
			idDirig=Integer.toString(VariabiliStaticheNuovaPartita.getInstance().getIdDirigente().get(position));

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

		final Integer idDirigente = Integer.parseInt(idDirig);

		Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.sconosciuto);
		imgDirigente.setImageBitmap(bm);

		Utility.getInstance().PrendeImmagineDirigente(idDirig, imgDirigente);

		if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
			convertView.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					if (lista.get(position).contains(";")) {
						int idDirigente = VariabiliStaticheDirigenti.getInstance().getIdDirigenti().get(position);
						String c[] = VariabiliStaticheDirigenti.getInstance().getDirigenti().get(position).split(";", -1);

						VariabiliStaticheDirigenti.getInstance().getTxtId().setText(Integer.toString(idDirigente));
						VariabiliStaticheDirigenti.getInstance().getEdtCognome().setText(c[1]);
						VariabiliStaticheDirigenti.getInstance().getEdtNome().setText(c[2]);
						VariabiliStaticheDirigenti.getInstance().getEdtEMail().setText(c[3]);
						VariabiliStaticheDirigenti.getInstance().getEdttelefono().setText(c[4]);

						Utility.getInstance().PrendeImmagineDirigente(Integer.toString(idDirigente), VariabiliStaticheDirigenti.getInstance().getImgDirigente());

						VariabiliStaticheDirigenti.getInstance().getRlMaschera().setVisibility(RelativeLayout.VISIBLE);
						VariabiliStaticheDirigenti.getInstance().getCmdElimina().setVisibility(LinearLayout.VISIBLE);

						VariabiliStaticheDirigenti.getInstance().getCmdOk().setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								String Cognome = VariabiliStaticheDirigenti.getInstance().getEdtCognome().getText().toString();
								String Nome = VariabiliStaticheDirigenti.getInstance().getEdtNome().getText().toString();
								String EMail = VariabiliStaticheDirigenti.getInstance().getEdtEMail().getText().toString();
								String Telefono = VariabiliStaticheDirigenti.getInstance().getEdttelefono().getText().toString();

								if (Cognome.isEmpty()) {
									DialogMessaggio.getInstance().show(VariabiliStaticheDirigenti.getInstance().getContext(), "Inserire il cognome",
											true, VariabiliStaticheGlobali.NomeApplicazione);
								} else {
									if (Nome.isEmpty()) {
										DialogMessaggio.getInstance().show(VariabiliStaticheDirigenti.getInstance().getContext(), "Inserire il nome",
												true, VariabiliStaticheGlobali.NomeApplicazione);
									} else {
										String id = VariabiliStaticheDirigenti.getInstance().getTxtId().getText().toString();

										DBRemotoDirigenti dbr = new DBRemotoDirigenti();
										dbr.SalvaDirigente(context, Integer.toString(VariabiliStaticheDirigenti.getInstance().idCategoriaScelta),
												id, Cognome, Nome, EMail, Telefono, NomiMaschere.getInstance().getDirigenti());
										VariabiliStaticheDirigenti.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
									}
								}
							}
						});

						VariabiliStaticheDirigenti.getInstance().getCmdAnnulla().setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								VariabiliStaticheDirigenti.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
							}
						});

						VariabiliStaticheDirigenti.getInstance().getCmdElimina().setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								DialogElimina.getInstance().show(VariabiliStaticheDirigenti.getInstance().getContext(),
										"Si vuole eliminare il Dirigente selezionato ?",
										VariabiliStaticheDirigenti.getInstance().getTxtId().getText().toString(),
										NomiMaschere.getInstance().getDirigenti());
							}
						});
					} else {
                        boolean Ok = true;

                        if (VariabiliStaticheNuovaPartita.getInstance().getDirigenteSelezionato() != null) {
                            for (String d : VariabiliStaticheNuovaPartita.getInstance().getDirigenteSelezionato()) {
                                if (VariabiliStaticheNuovaPartita.getInstance().getDirigente().get(position).equals(d)) {
                                    Ok = false;
                                    break;
                                }
                            }
                        } else {
                            VariabiliStaticheNuovaPartita.getInstance().setDirigenteSelezionato(new ArrayList<String>());
                            VariabiliStaticheNuovaPartita.getInstance().setIdDirigenteSelezionato(new ArrayList<Integer>());
                        }

                        if (Ok) {
                            VariabiliStaticheNuovaPartita.getInstance().getDirigenteSelezionato().add(VariabiliStaticheNuovaPartita.getInstance().getDirigente().get(position));
                            int m = VariabiliStaticheNuovaPartita.getInstance().getIdDirigente().get(position);
                            VariabiliStaticheNuovaPartita.getInstance().getIdDirigenteSelezionato().add(m);

                            NuovaPartita.RiempieListaDirigentiSelezionati();
                        } else {
                            DialogMessaggio.getInstance().show(VariabiliStaticheNuovaPartita.getInstance().getContext(),
                                    "Dirigente già in lista", true, VariabiliStaticheGlobali.NomeApplicazione);
                        }
					}
				}
			});
		}

		return convertView;
	}
}
