package looigi.gestionecampionato.ritorni_ws;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovoAnno;
import looigi.gestionecampionato.dati.VariabiliStaticheSettings;
import looigi.gestionecampionato.dati.VariabiliStaticheUtenti;
import looigi.gestionecampionato.db_locale.DBLocaleUtenti;
import looigi.gestionecampionato.db_remoto.ControlloVersioneApplicazione;
import looigi.gestionecampionato.db_remoto.DBRemotoAvversari;
import looigi.gestionecampionato.db_remoto.DBRemotoGenerale;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.ModificaUtente;
import looigi.gestionecampionato.maschere.NuovaPartita;
import looigi.gestionecampionato.maschere.Rose;
import looigi.gestionecampionato.maschere.Settings;
import looigi.gestionecampionato.maschere.Utenti;
import looigi.gestionecampionato.utilities.Utility;

public class wsGenerale {
	private Runnable runRiga;
	private Handler hSelezionaRiga;

	private String ToglieTag(String Cosa) {
		return Cosa;
	}

	public void RitornaTipologie(final Context context, String Ritorno, final String Maschera) {
		String Appoggio=ToglieTag(Ritorno);

		if (Appoggio.toUpperCase().contains("ERROR:")) {
			DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
					Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
		} else {
			List<String> descTipologie = new ArrayList<>();
			List<Integer> idTipologie = new ArrayList<>();

			String tt[]=Appoggio.split("§");
			for (String ttt : tt) {
				String t[]=ttt.split(";");
				idTipologie.add(Integer.parseInt(t[0]));
				descTipologie.add(t[1]);
			}

			VariabiliStaticheNuovaPartita.getInstance().setDescrizioneTipologie(descTipologie);
			VariabiliStaticheNuovaPartita.getInstance().setIdTipologie(idTipologie);

			hSelezionaRiga = new Handler();
			hSelezionaRiga.postDelayed(runRiga=new Runnable() {
				@Override
				public void run() {
					NuovaPartita.fillSpinnersTipologie();

					DBRemotoAvversari dbr = new DBRemotoAvversari();
					dbr.RitornaAvversari(context, "", Maschera);
				}
			}, 50);
		}
	}

	public void RitornaAnni(final Context context, String Ritorno, final String Maschera) {
		String Appoggio=ToglieTag(Ritorno);

		if (Appoggio.toUpperCase().contains("ERROR:")) {
			DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
					Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
		} else {
			List<String> descAnni = new ArrayList<>();

			String tt[]=Appoggio.split("§");
			for (String ttt : tt) {
				String t[]=ttt.split(";",-1);
				descAnni.add(t[0]+";"+t[1]+";");
			}

			VariabiliStaticheSettings.getInstance().setAnni(descAnni);

			Settings.fillSpinnersAnni();
		}
	}

	public void RitornaVersioneApplicazione(String Ritorno) {
		String Appoggio=ToglieTag(Ritorno);

		if (Appoggio.toUpperCase().contains("ERROR:")) {
			//DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
			// Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
		} else {
			ControlloVersioneApplicazione.ControlloVersione(Appoggio);
		}
	}

	public void RitornaRuoli(final Context context, String Ritorno, final String Maschera) {
		String Appoggio=ToglieTag(Ritorno);

		if (Appoggio.toUpperCase().contains("ERROR:")) {
			DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
					Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
		} else {
			List<String> descRuoli = new ArrayList<>();

			String rr[]=Appoggio.split("§");
			for (String rrr : rr) {
				String r[]=rrr.split(";",-1);
				descRuoli.add(r[0]+";"+r[1]+";");
			}

			Rose.fillSpinnersRuoli(descRuoli);
		}
	}

	public void RitornaMaxAnno(String Ritorno) {
		String Appoggio=ToglieTag(Ritorno);

		if (Appoggio.toUpperCase().contains("ERROR:")) {
			DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
					Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
		} else {
			String c[] = Ritorno.split(";",-1);

			VariabiliStaticheNuovoAnno.getInstance().getTxtAnno().setText(c[0]);
			VariabiliStaticheNuovoAnno.getInstance().getEdtAnno().setText(c[1]);
			VariabiliStaticheNuovoAnno.getInstance().getEdtNomeSquadra().setText("");
		}
	}

	public void RitornaValoriPerRegistrazione(String Ritorno, String Maschera) {
		String Appoggio=ToglieTag(Ritorno);

		if (Appoggio.toUpperCase().contains("ERROR:")) {
			DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
					Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
		} else {
			String r[] = Ritorno.split("§",-1);

			List<Integer> idAnno = new ArrayList<>();
			List<String> NomiSquadre = new ArrayList<>();

			for (String c : r) {
				if (!c.isEmpty()) {
					String cc[] = c.split(";", -1);
					if (cc.length > 2) {
						idAnno.add(Integer.parseInt(cc[0]));
						NomiSquadre.add(cc[2]);
					}
				}
			}

			VariabiliStaticheUtenti.getInstance().setIdAnno(idAnno);
			VariabiliStaticheUtenti.getInstance().setNomiSquadre(NomiSquadre);

			if (Maschera.equals(NomiMaschere.getInstance().getUtenti())) {
				Utenti.RiempieListaSquadre();
			} else {
				if (Maschera.equals(NomiMaschere.getInstance().getModificaUtenti())) {
					ModificaUtente.RiempieListaSquadre();
				}
			}
		}
	}

	public void RitornaAnnoAttuale(final Context context, String Ritorno, final String TAG) {
		String Appoggio=ToglieTag(Ritorno);

		if (Appoggio.toUpperCase().contains("ERROR:")) {
			DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
					Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
		} else {
			String c[] = Ritorno.split(";",-1);
			final String nomeSquadra = c[2].replace(" ", "_").toLowerCase().trim();

			VariabiliStaticheGlobali.getInstance().setAnnoInCorso(Integer.parseInt(c[0]));
			VariabiliStaticheGlobali.getInstance().setDescAnnoInCorso(c[1]);
			VariabiliStaticheGlobali.getInstance().setNomeSquadraAnno(c[2]);
			VariabiliStaticheGlobali.getInstance().setLatAnno(c[3]);
			VariabiliStaticheGlobali.getInstance().setLonAnno(c[4]);

			Utility.getInstance().ScriveAnno();

			hSelezionaRiga = new Handler();
			hSelezionaRiga.postDelayed(runRiga=new Runnable() {
				@Override
				public void run() {
                    DBLocaleUtenti dbl = new DBLocaleUtenti();
                    final boolean CeUtente=dbl.RitornaUtenteSalvato(VariabiliStaticheGlobali.getInstance().getContext());
                    if (CeUtente) {
                        String nomeLogo = "logo_" + nomeSquadra;
                        int myColor;
                        boolean niente = false;
                        switch (nomeSquadra) {
                            case "castelverde_calcio":
                                myColor = Color.parseColor("#44a751");
                                VariabiliStaticheMain.getInstance().getToolBar().setBackgroundColor(Color.argb(255,0,200,0));
                                VariabiliStaticheMain.getInstance().setSquadra(VariabiliStaticheGlobali.NomeSquadraCastelVerde);
								VariabiliStaticheMain.getInstance().getSfondo().setBackgroundResource(R.drawable.sfondo_verde_chiaro);
                                break;
                            case "ponte_di_nona":
                                myColor = Color.parseColor("#ffffff");
                                VariabiliStaticheMain.getInstance().getToolBar().setBackgroundColor(Color.argb(255,200,0,0));
								VariabiliStaticheMain.getInstance().setSquadra(VariabiliStaticheGlobali.NomeSquadraPonteDiNona);
								VariabiliStaticheMain.getInstance().getSfondo().setBackgroundResource(R.drawable.sfondo_rosso_chiaro);
                                break;
                            default:
                                myColor = Color.parseColor("#ffffff");
								VariabiliStaticheMain.getInstance().setSquadra("");
                                niente = true;
                                break;
                        }
                        VariabiliStaticheMain.getInstance().getWindowBackground().getDecorView()
                                .setBackgroundColor(myColor);


						/* VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale().getWindow()
								.setNavigationBarColor(VariabiliStaticheGlobali.getInstance().getContextPrincipale().getResources()
										.getColor(myColor));
						VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale().getWindow().setStatusBarColor(
								VariabiliStaticheGlobali.getInstance().getContextPrincipale().getResources().getColor(myColor)); */

						if (!niente) {
                            int id = VariabiliStaticheGlobali.getInstance().getViewActivity().
                                    getResources().getIdentifier("looigi.gestionecampionato:drawable/" + nomeLogo, null, null);
                            VariabiliStaticheMain.getInstance().getImgSplash().setImageResource(id);
                        }
						VariabiliStaticheMain.getInstance().setPartitaApplicazione(true);
						VariabiliStaticheMain.getInstance().getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    } else {
						VariabiliStaticheMain.getInstance().setPartitaApplicazione(false);
                        int myColor = Color.parseColor("#ffffff");;
                        VariabiliStaticheMain.getInstance().getWindowBackground().getDecorView()
                                .setBackgroundColor(myColor);

						VariabiliStaticheMain.getInstance().getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    }

                    if (TAG.equals("UTENTI")) {
						Utility.getInstance().CambiaMaschera(R.id.home, -1, -1);
					}
				}
			}, 50);
		}
	}

	public void ImpostaAnnoAttualeUtente(final Context context, String Ritorno, final String TAG) {
		String Appoggio=ToglieTag(Ritorno);

		if (Appoggio.toUpperCase().contains("ERROR:")) {
			DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
					Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
		} else {
			hSelezionaRiga = new Handler();
			hSelezionaRiga.postDelayed(runRiga=new Runnable() {
				@Override
				public void run() {
					VariabiliStaticheMain.getInstance().setPartite(null);

					DBRemotoGenerale dbr = new DBRemotoGenerale();
					dbr.RitornaAnnoAttualeUtenti(context, TAG);
				}
			}, 50);

			DialogMessaggio.getInstance().show(VariabiliStaticheSettings.getInstance().getContext(), "Anno modificato", false, VariabiliStaticheGlobali.NomeApplicazione);
		}
	}

	public void CreaNuovoAnno(String Ritorno) {
		String Appoggio=ToglieTag(Ritorno);

		if (Appoggio.toUpperCase().contains("ERROR:")) {
			DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
					Appoggio, true, VariabiliStaticheGlobali.NomeApplicazione);
		} else {
			DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
					"Nuovo anno creato.\nChiudere l'applicazione e riavviarla",
					true, VariabiliStaticheGlobali.NomeApplicazione);
		}
	}
}