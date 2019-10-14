package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anychart.scales.Linear;

import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dialog.DialogDomanda;
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
		String[] Campi=riga.split(";", -1);

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
		String RigoriPropri = Campi[18];
		String RigoriAvv = Campi[19];

		int RigoriSegnati = 0;
		int RigoriSegnatiAvv = 0;
		int RigoriSbagliatiAvv = 0;
		boolean CiSonoRigori = false;

		if (!RigoriPropri.isEmpty()) {
			String[] c = RigoriPropri.split("%",-1);
			for (String cc : c) {
				String[] ccc = cc.split("!", -1);
				if (!cc.isEmpty()) {
					String termine = ccc[6];
					if (termine.equals("1")) {
						RigoriSegnati++;
						CiSonoRigori = true;
					}
				}
			}
		}
		if (!RigoriAvv.isEmpty()) {
			String[] r = RigoriAvv.split("!", -1);
			RigoriSegnatiAvv = Integer.parseInt(r[0]);
			RigoriSbagliatiAvv = Integer.parseInt(r[1]);
			if (RigoriSegnatiAvv>0 || RigoriSbagliatiAvv>0) {
				CiSonoRigori = true;
			}
		}

		String[] puntiATempi={"0","0"};

		if (Risultato.contains("-")) {
			puntiATempi = Risultato.split("-");
		}

		TextView txtId = convertView.findViewById(R.id.idPartita);
        TextView txtDataOraCampo = convertView.findViewById(R.id.dataoracampo);
        TextView txtRisultatoTempi = convertView.findViewById(R.id.risultatoTempi);
		TextView txtRisultatoGoal = convertView.findViewById(R.id.risultatoGoal);
		TextView txtTipologia = convertView.findViewById(R.id.txtTipologia);
		LinearLayout layRigori = convertView.findViewById(R.id.layRigori);
		// Utility.getInstance().SettaColoreSfondoPerNomeSquadra(txtTipologia);

		// TextView txtCampo = convertView.findViewById(R.id.campo);
		ImageView imgMaps = convertView.findViewById(R.id.imgMaps);
		ImageView imgArbitro = convertView.findViewById(R.id.imgArbitro);

		if (Coords.isEmpty() || InCasa.equals("S")) {
			imgMaps.setVisibility(LinearLayout.GONE);
		} else {
			imgMaps.setVisibility(LinearLayout.VISIBLE);
			imgMaps.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					String[] c = Coords.split(",", -1);
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

		boolean CiSonoGoals=false;
		String[] goals = {"0", "0"};

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

		// Utility.getInstance().SettaColoreTestoPerNomeSquadra(txtSqCasa);
		// Utility.getInstance().SettaColoreTestoPerNomeSquadra(txtSqFuori);
		// Utility.getInstance().SettaColoreTestoPerNomeSquadra(txtGoals);
		// Utility.getInstance().SettaColoreTestoPerNomeSquadra(txtTempi);
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

		String cate = Categoria;
		if (cate.contains("-")) {
			cate = cate.substring(cate.indexOf("-")+1, cate.length());
		}
		cate = cate.trim();
		txtSqCasa.setText(cate);
		txtSqFuori.setText(Avversario);

		String[] puntiATempiOriginari = puntiATempi.clone();
		String[] goalsOriginari = goals.clone();

		layRigori.setVisibility(LinearLayout.GONE);
		if (CiSonoRigori && CiSonoGoals) {
			int g1 = Integer.parseInt(goals[0]);
			int g2 = Integer.parseInt(goals[1]);

			g1+=RigoriSegnati;
			g2+=RigoriSegnatiAvv;

			if (InCasa.equals("S")) {
				goals[0] = Integer.toString(g1);
				goals[1] = Integer.toString(g2);
			} else {
				goals[0] = Integer.toString(g2);
				goals[1] = Integer.toString(g1);
			}

			int pt1 = Integer.parseInt(puntiATempi[0]);
			int pt2 = Integer.parseInt(puntiATempi[1]);

			if (RigoriSegnati>RigoriSegnatiAvv) {
				pt1++;
			} else {
				if (RigoriSegnati<RigoriSegnatiAvv) {
					pt2++;
				}
			}

			if (InCasa.equals("S")) {
				puntiATempi[0] = Integer.toString(pt1);
				puntiATempi[1] = Integer.toString(pt2);
			} else {
				puntiATempi[0] = Integer.toString(pt2);
				puntiATempi[1] = Integer.toString(pt1);
			}

			layRigori.setVisibility(LinearLayout.VISIBLE);
			TextView tR = convertView.findViewById(R.id.txtRigori);
			if (InCasa.equals("S")) {
				if (RisultatoATempi.equals("N")) {
					txtGoals.setText("Rigori");
					tR.setText("Risultato dtr " + goalsOriginari[0] + "-" + goalsOriginari[1]);
				} else {
					txtTempi.setText("Rigori");
					tR.setText("Risultato dtr " + puntiATempiOriginari[0] + "-" + puntiATempiOriginari[1]);
				}
			} else {
				if (RisultatoATempi.equals("N")) {
					txtGoals.setText("Rigori");
					tR.setText("Risultato dtr " + goalsOriginari[1] + "-" + goalsOriginari[0]);
				} else {
					txtTempi.setText("Rigori");
					tR.setText("Risultato dtr " + puntiATempiOriginari[1] + "-" + puntiATempiOriginari[0]);
				}
			}
		}

		if (InCasa.equals("S")) {
			if (RisultatoATempi.equals("S")) {
				txtRisultatoTempi.setText(puntiATempi[0] + '-' + puntiATempi[1]);
				if (Integer.parseInt(puntiATempi[0]) > Integer.parseInt(puntiATempi[1])) {
					txtRisultatoTempi.setTextColor(coloreVerde);
				} else {
					if (Integer.parseInt(puntiATempi[0]) < Integer.parseInt(puntiATempi[1])) {
						txtRisultatoTempi.setTextColor(coloreRosso);
					} else {
						txtRisultatoTempi.setTextColor(coloreNero);
					}
				}
			} else {
				if (CiSonoGoals) {
					txtRisultatoGoal.setText(goals[0] + "-" + goals[1]);
					if (Integer.parseInt(goals[0]) > Integer.parseInt(goals[1])) {
						txtRisultatoGoal.setTextColor(coloreVerde);
					} else {
						if (Integer.parseInt(goals[0]) < Integer.parseInt(goals[1])) {
							txtRisultatoGoal.setTextColor(coloreRosso);
						} else {
							txtRisultatoGoal.setTextColor(coloreNero);
						}
					}
				} else {
					txtRisultatoGoal.setText("-");
				}
			}
		} else {
			if (RisultatoATempi.equals("S")) {
				txtRisultatoTempi.setText(puntiATempi[1] + '-' + puntiATempi[0]);
				if (Integer.parseInt(puntiATempi[1]) > Integer.parseInt(puntiATempi[0])) {
					txtRisultatoTempi.setTextColor(coloreRosso);
				} else {
					if (Integer.parseInt(puntiATempi[1]) < Integer.parseInt(puntiATempi[0])) {
						txtRisultatoTempi.setTextColor(coloreVerde);
					} else {
						txtRisultatoTempi.setTextColor(coloreNero);
					}
				}
			} else {
				if (CiSonoGoals) {
					txtRisultatoGoal.setText(goals[0] + "-" + goals[1]);
					if (Integer.parseInt(goals[0]) > Integer.parseInt(goals[1])) {
						txtRisultatoGoal.setTextColor(coloreRosso);
					} else {
						if (Integer.parseInt(goals[0]) < Integer.parseInt(goals[1])) {
							txtRisultatoGoal.setTextColor(coloreVerde);
						} else {
							txtRisultatoGoal.setTextColor(coloreNero);
						}
					}
				} else {
					txtRisultatoGoal.setText("-");
				}
			}
		}

		// txtAllenatore.setText(Allenatore);

		Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.sconosciuto);
		imgCasa.setImageBitmap(bm);
		imgFuori.setImageBitmap(bm);
		imgAllenatore.setImageBitmap(bm);

		Utility.getInstance().PrendeImmagineCategoria(idCategoria, imgCasa);
		Utility.getInstance().PrendeImmagineAvversario(idAvversario, imgFuori);
		Utility.getInstance().PrendeImmagineAllenatore(idAllenatore, imgAllenatore);

		if (!idArbitro.isEmpty()) {
			imgArbitro.setVisibility(LinearLayout.VISIBLE);
			imgArbitro.setImageBitmap(bm);
			Utility.getInstance().PrendeImmagineArbitro(idArbitro, imgArbitro);
		} else {
			imgArbitro.setVisibility(LinearLayout.INVISIBLE);
		}

		if (RisultatoATempi.equals("N")) {
			txtTempi.setVisibility(LinearLayout.GONE);
			txtRisTempi.setVisibility(LinearLayout.GONE);
		} else {
			txtGoals.setVisibility(LinearLayout.GONE);
			txtRisultatoGoal.setVisibility(LinearLayout.GONE);
		}

		convertView.setOnClickListener(new View.OnClickListener() {
		   public void onClick(View v) {
			   Utility.getInstance().CambiaMaschera(R.id.nuova_partita, Integer.parseInt(idPartita), -1);
		   }
		});

		Button btnElimina = convertView.findViewById(R.id.btnEliminaPartita);
		btnElimina.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {

				DialogDomanda.getInstance().show(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale(),
						"Si vuole eliminare la partita selezionata N° " + idPartita + " ?",
						VariabiliStaticheGlobali.NomeApplicazione,
						"ELIMINA PARTITA",
						idPartita);
			}
		});


		return convertView;
	}
}
