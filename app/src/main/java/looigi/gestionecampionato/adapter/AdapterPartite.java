package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.Utility;

public class AdapterPartite extends ArrayAdapter
{
	private Context context;
	private List<String> lista;

	public AdapterPartite(Context context, int textViewResourceId, List<String> objects)
	{	
		super(context, textViewResourceId, objects);
		
		this.context = context;
		this.lista=objects;
	}
	
	@Override
	@Nullable
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.listview_partita, null);

		/* if (Utility.getInstance().ePari(position)) {
			convertView.setBackgroundColor(Color.WHITE);
		} else {
			convertView.setBackgroundColor(Color.argb(255,230,230,230));
		} */

		String riga = lista.get(position);
		String Campi[]=riga.split(";", -1);

		String DataOra = Campi[0];
		final String idPartita = Campi[1];
		String InCasa = Campi[2];
		String Categoria = Campi[3];
		String Avversario = Campi[4];
		String Risultato = Campi[5];
		String Allenatore = Campi[6];
		String Campo = Campi[7];
		String idCategoria = Campi[8];
		String idAvversario = Campi[9];
		String idAllenatore = Campi[10];
		String idArbitro = Campi[15];
		final String Coords = Campi[14];
		String RisultatoATempi = Campi[17];

		String puntiATempi[]={"0","0"};

		if (Risultato.contains("-")) {
			puntiATempi = Risultato.split("-");
		}

		TextView txtId = convertView.findViewById(R.id.idPartita);
        TextView txtDataOraCampo = convertView.findViewById(R.id.dataoracampo);
        TextView txtRisultatoTempi = convertView.findViewById(R.id.risultatoTempi);
		TextView txtRisultatoGoal = convertView.findViewById(R.id.risultatoGoal);
		TextView txtTipologia = convertView.findViewById(R.id.txtTipologia);
		Utility.getInstance().SettaColoreSfondoPerNomeSquadra(txtTipologia);

		// TextView txtCampo = convertView.findViewById(R.id.campo);
		ImageView imgMaps = convertView.findViewById(R.id.imgMaps);
		ImageView imgArbitro = convertView.findViewById(R.id.imgArbitro);

		if (Coords.isEmpty() || InCasa.equals("S")) {
			imgMaps.setVisibility(LinearLayout.GONE);
		} else {
			imgMaps.setVisibility(LinearLayout.VISIBLE);
			imgMaps.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					String c[] = Coords.split(",", -1);
					Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Coords);
					// Uri gmmIntentUri = Uri.parse("geo:"+c[0]+","+c[1]);
					Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
					mapIntent.setPackage("com.google.android.apps.maps");
					VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale().startActivity(mapIntent);
				}
			});
		}

		String Tipologia = "";
		try {
			Tipologia = Campi[11];
		} catch (Exception ignored) {
			Tipologia = "Sconosciuta";
		}
		txtTipologia.setText(Tipologia);

		Boolean CiSonoGoals=false;
		String goals[] = {"0", "0"};

		try {
			String RisGoal = Campi[12];

			if (RisGoal.contains("-")) {
				goals = RisGoal.split("-");
			}
			CiSonoGoals=true;
		} catch (Exception ignored) {

		}

		TextView txtSqCasa;
		TextView txtSqFuori;
		ImageView imgCasa;
		ImageView imgFuori;

		if (InCasa.equals("S")) {
			txtSqCasa = convertView.findViewById(R.id.sqCasa);
			txtSqFuori = convertView.findViewById(R.id.sqFuori);
			imgCasa = convertView.findViewById(R.id.imgCasa);
			imgFuori = convertView.findViewById(R.id.imgFuori);
		} else {
			txtSqCasa = convertView.findViewById(R.id.sqFuori);
			txtSqFuori = convertView.findViewById(R.id.sqCasa);
			imgCasa = convertView.findViewById(R.id.imgFuori);
			imgFuori = convertView.findViewById(R.id.imgCasa);
		}

		TextView txtGoals=convertView.findViewById(R.id.txtGoals);
		TextView txtTempi=convertView.findViewById(R.id.txtTempi);
		TextView txtRisTempi=convertView.findViewById(R.id.risultatoTempi);

		Utility.getInstance().SettaColoreTestoPerNomeSquadra(txtSqCasa);
		Utility.getInstance().SettaColoreTestoPerNomeSquadra(txtSqFuori);
		Utility.getInstance().SettaColoreTestoPerNomeSquadra(txtGoals);
		Utility.getInstance().SettaColoreTestoPerNomeSquadra(txtTempi);
		// TextView txtAllenatore = convertView.findViewById(R.id.allenatore);

		ImageView imgMultimedia = convertView.findViewById(R.id.imgMultimedia);
		TextView tMM = convertView.findViewById(R.id.txtQuantiMultimedia);

		int Multimedia = Integer.parseInt(Campi[13]);
		if (Multimedia>1) {
			imgMultimedia.setVisibility(LinearLayout.VISIBLE);
			tMM.setVisibility(LinearLayout.VISIBLE);
			tMM.setText(Integer.toString(Multimedia-1));
		} else {
			imgMultimedia.setVisibility(LinearLayout.GONE);
			tMM.setVisibility(LinearLayout.GONE);
		}

		ImageView imgAllenatore = convertView.findViewById(R.id.imgAllenatore);

		int coloreVerde= Color.rgb(0,200,0);
		int coloreNero=Color.BLACK;
		int coloreRosso=Color.RED;

		txtId.setText(idPartita);
		txtDataOraCampo.setText(DataOra+" "+Campo);
		// txtCampo.setText(Campo);
		txtSqCasa.setText(Categoria);
		txtSqFuori.setText(Avversario);

		if (InCasa.equals("S")) {
			txtRisultatoTempi.setText(puntiATempi[0]+'-'+puntiATempi[1]);
			if (Integer.parseInt(puntiATempi[0])>Integer.parseInt(puntiATempi[1])) {
				txtRisultatoTempi.setTextColor(coloreVerde);
			} else {
				if (Integer.parseInt(puntiATempi[0])<Integer.parseInt(puntiATempi[1])) {
					txtRisultatoTempi.setTextColor(coloreRosso);
				} else {
					txtRisultatoTempi.setTextColor(coloreNero);
				}
			}

			if (CiSonoGoals) {
				txtRisultatoGoal.setText(goals[0]+"-"+goals[1]);
				if (Integer.parseInt(goals[0])>Integer.parseInt(goals[1])) {
					txtRisultatoGoal.setTextColor(coloreVerde);
				} else {
					if (Integer.parseInt(goals[0])<Integer.parseInt(goals[1])) {
						txtRisultatoGoal.setTextColor(coloreRosso);
					} else {
						txtRisultatoGoal.setTextColor(coloreNero);
					}
				}
			} else {
				txtRisultatoGoal.setText("-");
			}
		} else {
			txtRisultatoTempi.setText(puntiATempi[1]+'-'+puntiATempi[0]);
			if (Integer.parseInt(puntiATempi[1])>Integer.parseInt(puntiATempi[0])) {
				txtRisultatoTempi.setTextColor(coloreRosso);
			} else {
				if (Integer.parseInt(puntiATempi[1])<Integer.parseInt(puntiATempi[0])) {
					txtRisultatoTempi.setTextColor(coloreVerde);
				} else {
					txtRisultatoTempi.setTextColor(coloreNero);
				}
			}

			if (CiSonoGoals) {
				txtRisultatoGoal.setText(goals[0]+"-"+goals[1]);
				if (Integer.parseInt(goals[0])>Integer.parseInt(goals[1])) {
					txtRisultatoGoal.setTextColor(coloreRosso);
				} else {
					if (Integer.parseInt(goals[0])<Integer.parseInt(goals[1])) {
						txtRisultatoGoal.setTextColor(coloreVerde);
					} else {
						txtRisultatoGoal.setTextColor(coloreNero);
					}
				}
			} else {
				txtRisultatoGoal.setText("-");
			}
		}

		// txtAllenatore.setText(Allenatore);

		Utility.getInstance().PrendeImmagineCategoria(idCategoria, imgCasa);
		Utility.getInstance().PrendeImmagineAvversario(idAvversario, imgFuori);
		Utility.getInstance().PrendeImmagineAllenatore(idAllenatore, imgAllenatore);
		if (!idArbitro.isEmpty()) {
			imgArbitro.setVisibility(LinearLayout.VISIBLE);
			Utility.getInstance().PrendeImmagineArbitro(idArbitro, imgArbitro);
		} else {
			imgArbitro.setVisibility(LinearLayout.INVISIBLE);
		}

		if (RisultatoATempi.equals("N")) {
			txtTempi.setVisibility(LinearLayout.GONE);
			txtRisTempi.setVisibility(LinearLayout.GONE);
		}

		convertView.setOnClickListener(new View.OnClickListener() {
		   public void onClick(View v) {
			   Utility.getInstance().CambiaMaschera(R.id.nuova_partita, Integer.parseInt(idPartita), -1);
		   }
		});

		return convertView;
	}
}
