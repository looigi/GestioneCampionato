package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.StrutturaPartita;
import looigi.gestionecampionato.dati.StrutturaSquadreCampionato;
import looigi.gestionecampionato.dati.VariabiliStaticheCampionato;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.db_remoto.DBRemotoCampionato;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.utilities.Utility;

public class AdapterPartiteCampionato extends ArrayAdapter
{
	private Context context;
	private List<StrutturaPartita> lista;

	public AdapterPartiteCampionato(Context context, int textViewResourceId, List<StrutturaPartita> objects)
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
		convertView = inflater.inflate(R.layout.listview_partita_campionato, null);

		String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
		final VariabiliStaticheCampionato vv = VariabiliStaticheCampionato.getInstance();

		TextView txtId = convertView.findViewById(R.id.idPartita);
		TextView txtDataOraCampo = convertView.findViewById(R.id.dataora);
		// TextView txtRisultatoTempi = convertView.findViewById(R.id.risultatoTempi);
		TextView txtRisultatoGoal = convertView.findViewById(R.id.risultatoGoal);
		TextView txtTipologia = convertView.findViewById(R.id.txtTipologia);
		TextView txtCampo = convertView.findViewById(R.id.campo);
		TextView txtCasa = convertView.findViewById(R.id.sqCasa);
		TextView txtFuori = convertView.findViewById(R.id.sqFuori);
		TextView txtAllenatore = convertView.findViewById(R.id.allenatore);
		ImageView imgAllenatore = convertView.findViewById(R.id.imgAllenatore);
        ImageView imgElimina = convertView.findViewById(R.id.imgElimina);

		final StrutturaPartita lsp = vv.getDatiCampionato().getPartitaGiornata(vv.getDatiCampionato().getGiornataAttuale(), position);

		if (lsp.getIdSqCasa()<0 || lsp.getIdSqFuori()<0) {
			convertView.setBackgroundColor(Color.argb(255, 230, 130, 130));
		} else {
			if (Utility.getInstance().ePari(position)) {
				convertView.setBackgroundColor(Color.WHITE);
			} else {
				convertView.setBackgroundColor(Color.argb(255, 230, 230, 230));
			}
		}

		txtId.setText(Integer.toString(lsp.getIdPartitaGen()));
		String Datella = lsp.getDatella()+" "+lsp.getOraConv();
		txtDataOraCampo.setText(Datella);

		String r[];
		if (!lsp.getRisultato1().isEmpty()) {
            r = lsp.getRisultato1().split("-",-1);
        } else {
            r = lsp.getRisultato2().split("-",-1);
        }
        String rr="";
		if (r.length>1) {
            rr = r[0] + "-" + r[1];
        }
        if (lsp.getIdSqCasa()<0) {
            txtCasa.setText(VariabiliStaticheGlobali.getInstance().getNomeSquadraAnno());
        } else {
            txtCasa.setText(lsp.getCasa());
        }

        if (lsp.getIdSqFuori()<0) {
            txtFuori.setText(VariabiliStaticheGlobali.getInstance().getNomeSquadraAnno());
            if (r.length>1) {
                rr = r[1] + "-" + r[0];
            }
        } else {
            txtFuori.setText(lsp.getFuori());
        }
        txtRisultatoGoal.setText(rr);

		txtTipologia.setText("Campionato");

		txtAllenatore.setVisibility(LinearLayout.GONE);
		imgAllenatore.setVisibility(LinearLayout.GONE);

		String Campo = "";
		List<StrutturaSquadreCampionato> s = vv.getDatiCampionato().getSquadre();
		for (StrutturaSquadreCampionato ss : s) {
			if (ss.getIdSquadre() == lsp.getIdSqCasa()) {
				Campo = ss.getCampo() + " " +ss.getIndirizzoCampo();
				break;
			}
		}
		txtCampo.setText(Campo);

        ImageView imgCasa = convertView.findViewById(R.id.imgCasa);
        ImageView imgFuori = convertView.findViewById(R.id.imgFuori);

        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.sconosciuto);
        imgCasa.setImageBitmap(bm);
        imgFuori.setImageBitmap(bm);

        if (lsp.getIdSqCasa()<0) {
            Utility.getInstance().PrendeImmagineCategoria(Integer.toString(vv.getIdCategoriaScelta()), imgCasa);
        } else {
            Utility.getInstance().PrendeImmagineAvversario(Integer.toString(lsp.getIdSqCasa()), imgCasa);
        }

        if (lsp.getIdSqFuori()<0) {
            Utility.getInstance().PrendeImmagineCategoria(Integer.toString(vv.getIdCategoriaScelta()), imgFuori);
        } else {
		    Utility.getInstance().PrendeImmagineAvversario(Integer.toString(lsp.getIdSqFuori()), imgFuori);
        }

        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            imgElimina.setVisibility(LinearLayout.VISIBLE);
            imgElimina.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DialogElimina.getInstance().show(context,
                            "Si vuole eliminare la partita?",
                            Integer.toString(position),
                            NomiMaschere.getInstance().getCampionato());
                    // DBRemotoCampionato dbr = new DBRemotoCampionato();
                    // dbr.EliminaPartita(vv.getContext(),
                    //         Integer.toString(vv.getDatiCampionato().getGiornataAttuale()),
                    //         Integer.toString(vv.getIdCategoriaScelta()),
                    //         Integer.toString(lsp.getIdPartitaGen()));
                }
            });
        }

		if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
			convertView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (lsp.getIdSqCasa()<0 || lsp.getIdSqFuori()<0) {
                        DBRemotoCampionato dbr = new DBRemotoCampionato();
                        vv.setIdUnioneCalendario(lsp.getIdPartitaGen());
                        dbr.RitornaIdPartitaDaUnione(vv.getContext(),
                                Integer.toString(lsp.getIdPartitaGen()));
                    } else {
                        vv.getRlMascheraNuovaPartita().setVisibility(RelativeLayout.VISIBLE);

                        List<StrutturaSquadreCampionato> Squadre = new ArrayList<>(vv.getDatiCampionato().getSquadre());

                        List<String> Inserite = vv.getDatiCampionato().RitornaSquadreGiornata(vv.getDatiCampionato().getGiornataAttuale());
                        if (Inserite != null) {
                            for (String s : Inserite) {
                                String[] s1 = s.split(";", -1);
                                for (StrutturaSquadreCampionato c : Squadre) {
                                    if (c.getIdSquadre() == Integer.parseInt(s1[0]) &&
                                            !c.getSquadre().equals(lsp.getCasa()) &&
                                            !c.getSquadre().equals(lsp.getFuori())) {
                                        Squadre.remove(c);
                                        break;
                                    }
                                }
                            }
                        }

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm");
                        Date myDate = null;
                        try {
                            myDate = dateFormat.parse(lsp.getDatella());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat timeFormatD = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat timeFormatO = new SimpleDateFormat("kk:mm:ss");
                        String Data = timeFormatD.format(myDate);
                        String Ora = timeFormatO.format(myDate);

                        vv.setModalitaInserimento(lsp.getIdPartitaGen());
                        vv.setIdPartitaDaModificare(lsp.getIdPartitaGiorno());
                        vv.getTxtData().setText(Data);
                        vv.getTxtOra().setText(Ora);
                        vv.getEdtRisultato().setText(lsp.getRisultato1());
                        vv.getEdtNote().setText(lsp.getNotelle());

                        vv.getEdtRisultato().setEnabled(true);
                        vv.getEdtNote().setEnabled(true);

                        fillSpinnersSquadra(true, Squadre, lsp.getCasa());
                        fillSpinnersSquadra(false, Squadre, lsp.getFuori());
                    }
                }
            });
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheCampionato.getInstance().getContext(),
                    "Non si hanno i permessi per inserire partite", true,
                    VariabiliStaticheGlobali.NomeApplicazione);
		}

		return convertView;
	}

    private void fillSpinnersSquadra(final Boolean Casa, final List<StrutturaSquadreCampionato> Squadra,
                                     String NomeSquadra) {
        final VariabiliStaticheCampionato vnp = VariabiliStaticheCampionato.getInstance();

        if (Squadra != null && Squadra.size()>0) {
            // Carica squadre nella lista
            List<String> lSquadra = new ArrayList<>();
            // lSquadra.add("Scegliere una squadra");
            for (StrutturaSquadreCampionato sc : Squadra) {
                lSquadra.add(sc.getSquadre());
            }
            final ArrayAdapter<String> adapterSquadra = new ArrayAdapter<>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_per_categorie, lSquadra);
            adapterSquadra.setDropDownViewResource(R.layout.spinner_item_per_categorie);

            if (Casa) {
                vnp.getSpnCasa().setAdapter(adapterSquadra);
                int pos = Utility.getInstance().CercaESettaStringaInSpinner(vnp.getSpnCasa(), NomeSquadra);
                if (pos>-1) {
                    vnp.getSpnCasa().setSelection(pos);
                }
            } else {
                vnp.getSpnFuori().setAdapter(adapterSquadra);
                int pos = Utility.getInstance().CercaESettaStringaInSpinner(vnp.getSpnFuori(), NomeSquadra);
                if (pos>-1) {
                    vnp.getSpnFuori().setSelection(pos);
                }
            }

            // Carica squadre nella lista

            vnp.getSpnCasa().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    StrutturaSquadreCampionato sc = Squadra.get(position);
                    vnp.setSquadraSceltaCasa(sc.getIdSquadre()+";"+sc.getSquadre()+";");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            vnp.getSpnFuori().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    StrutturaSquadreCampionato sc = Squadra.get(position);
                    vnp.setSquadraSceltaFuori(sc.getIdSquadre()+";"+sc.getSquadre()+";");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

}
