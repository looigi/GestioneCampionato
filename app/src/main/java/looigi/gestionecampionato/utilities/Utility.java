package looigi.gestionecampionato.utilities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.dati.VariabiliStaticheMeteo;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.dati.VariabiliStaticheStatAllenamenti;
import looigi.gestionecampionato.dati.VariabiliStaticheUtenti;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.About;
import looigi.gestionecampionato.maschere.Album;
import looigi.gestionecampionato.maschere.Allenamenti;
import looigi.gestionecampionato.maschere.Allenatori;
import looigi.gestionecampionato.maschere.Arbitri;
import looigi.gestionecampionato.maschere.Avversari;
import looigi.gestionecampionato.maschere.Campionato;
import looigi.gestionecampionato.maschere.Categorie;
import looigi.gestionecampionato.maschere.Dirigenti;
import looigi.gestionecampionato.maschere.Eventi;
import looigi.gestionecampionato.maschere.Grafici;
import looigi.gestionecampionato.maschere.Home;
import looigi.gestionecampionato.maschere.Mappa;
import looigi.gestionecampionato.maschere.ModificaUtente;
import looigi.gestionecampionato.maschere.Multimedia;
import looigi.gestionecampionato.maschere.NuovaPartita;
import looigi.gestionecampionato.maschere.NuovoAnno;
import looigi.gestionecampionato.maschere.Rose;
import looigi.gestionecampionato.maschere.Statistiche;
import looigi.gestionecampionato.maschere.StatisticheAllenamenti;
import looigi.gestionecampionato.maschere.Utenti;
import looigi.gestionecampionato.maschere.VisualizzaImmagini;
import looigi.gestionecampionato.soap.GestioneWEBServiceSOAP;

//import looigi.totomioii_2.Messaggi;
//import looigi.totomioii_2.autostart.service;
//import looigi.totomioii_2.thread.MessageThread;

@SuppressLint("SimpleDateFormat")
public class Utility {
	//-------- Singleton ----------//
	private static Utility instance = null;

	private Utility() {
	}

	public static Utility getInstance() {
		if (instance == null) {
			instance = new Utility();
		}

		return instance;
	}

	private VariabiliStaticheMain vm = VariabiliStaticheMain.getInstance();
	private VariabiliStaticheGlobali vg = VariabiliStaticheGlobali.getInstance();

    public void PrendeImmagineGiocatore(String id, ImageView imgDestinazione) {
        String nFile=Integer.toString(vg.getAnnoInCorso())+"_"+id+".jpg";

        if (!Utility.getInstance().fileExistsInSD(nFile, vg.PercorsoDIR+"/Giocatori")) {
            ScaricaImmagini.getInstance().AggiungeImmagineDaScaricare(
                    "GIOCATORE",
                    vg.getAnnoInCorso(),
                    imgDestinazione,
                    id,
					"");
        } else {
			if (imgDestinazione!=null) {
				imgDestinazione.setImageBitmap(BitmapFactory.decodeFile(vg.PercorsoDIR + "/Giocatori/" + nFile));
				imgDestinazione.setVisibility(LinearLayout.VISIBLE);
			}
        }
    }

	public void PrendeImmagineCategoria(String idCategoria, ImageView imgDestinazione) {
		String nFile=Integer.toString(vg.getAnnoInCorso())+"_"+idCategoria+".jpg";

		if (!Utility.getInstance().fileExistsInSD(nFile, vg.PercorsoDIR+"/Categorie")) {
			ScaricaImmagini.getInstance().AggiungeImmagineDaScaricare(
					"CATEGORIE",
					vg.getAnnoInCorso(),
					imgDestinazione,
					idCategoria,
					"");
		} else {
			if (imgDestinazione!=null) {
				imgDestinazione.setImageBitmap(BitmapFactory.decodeFile(vg.PercorsoDIR + "/Categorie/" + nFile));
				imgDestinazione.setVisibility(LinearLayout.VISIBLE);
			}
		}
	}

	public void PrendeImmagineAvversario(String idAvversario, ImageView imgDestinazione) {
		String nFileAvv=idAvversario+".jpg";

		if (!Utility.getInstance().fileExistsInSD(nFileAvv, vg.PercorsoDIR+"/Avversari")) {
			ScaricaImmagini.getInstance().AggiungeImmagineDaScaricare(
					"AVVERSARI",
					vg.getAnnoInCorso(),
					imgDestinazione,
					idAvversario,
					"");
		} else {
			if (imgDestinazione!=null) {
				imgDestinazione.setImageBitmap(BitmapFactory.decodeFile(vg.PercorsoDIR + "/Avversari/" + nFileAvv));
				imgDestinazione.setVisibility(LinearLayout.VISIBLE);
			}
		}
	}

	public void PrendeImmagineAllenatore(String idAllenatore, ImageView imgDestinazione) {
		String nFileAll=Integer.toString(vg.getAnnoInCorso())+"_"+idAllenatore+".jpg";

		if (!Utility.getInstance().fileExistsInSD(nFileAll, 
				vg.PercorsoDIR+"/Allenatori")) {
			ScaricaImmagini.getInstance().AggiungeImmagineDaScaricare(
					"ALLENATORI",
					vg.getAnnoInCorso(),
					imgDestinazione,
					idAllenatore,
					"");
		} else {
			if (imgDestinazione!=null) {
				try {
					imgDestinazione.setImageBitmap(BitmapFactory.decodeFile(
							vg.PercorsoDIR + "/Allenatori/" + nFileAll));
					imgDestinazione.setVisibility(LinearLayout.VISIBLE);
				} catch (Exception ignored) {

				}
			}
		}
	}

	public void PrendeImmagineDirigente(String idDirigente, ImageView imgDestinazione) {
		String nFileAll=Integer.toString(vg.getAnnoInCorso())+"_"+idDirigente+".jpg";

		if (!Utility.getInstance().fileExistsInSD(nFileAll,
				vg.PercorsoDIR+"/Dirigenti")) {
			ScaricaImmagini.getInstance().AggiungeImmagineDaScaricare(
					"DIRIGENTI",
					vg.getAnnoInCorso(),
					imgDestinazione,
					idDirigente,
					"");
		} else {
			if (imgDestinazione!=null) {
				try {
					imgDestinazione.setImageBitmap(BitmapFactory.decodeFile(
							vg.PercorsoDIR + "/Dirigenti/" + nFileAll));
					imgDestinazione.setVisibility(LinearLayout.VISIBLE);
				} catch (Exception ignored) {

				}
			}
		}
	}

	public void PrendeImmagineArbitro(String idArbitro, ImageView imgDestinazione) {
		String nFileAll=idArbitro+".jpg";

		if (!Utility.getInstance().fileExistsInSD(nFileAll,
				vg.PercorsoDIR+"/Arbitri")) {
			ScaricaImmagini.getInstance().AggiungeImmagineDaScaricare(
					"ARBITRI",
					-1,
					imgDestinazione,
					idArbitro,
					"");
		} else {
			if (imgDestinazione!=null) {
				try {
					imgDestinazione.setImageBitmap(BitmapFactory.decodeFile(
							vg.PercorsoDIR + "/Arbitri/" + nFileAll));
					imgDestinazione.setVisibility(LinearLayout.VISIBLE);
				} catch (Exception ignored) {

				}
			}
		}
	}

	private int UltimaMaschera=-1;

	public void CambiaMaschera(int viewId, int NumeroPartita, int idUnioneCalendario) {
		String idTipologia = vg.getDatiUtente().getIdTipologia();

		if (viewId!=UltimaMaschera) {
			UltimaMaschera=viewId;

			Fragment fragment = null;
			String title = ""; // vg.getFragmentActivityPrincipale().getString(R.string.app_name);

			switch (viewId) {
				case -1:
					// Nuovo anno
					fragment = new NuovoAnno();
					title = NomiMaschere.getInstance().getNuovoAnnoPerTitolo();
					VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getNuovoAnno();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case -2:
					// Gestione utenti
					fragment = new ModificaUtente();
					title = NomiMaschere.getInstance().getPermessiUtentiPerTitolo();
					VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getModificaUtenti();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case -3:
					// Gestione mappa
					fragment = new Mappa();
					title = "Mappa";
					VariabiliStaticheGlobali.MascheraAttuale = "Mappa";
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case -4:
					// Gestione grafico minuti fatti
					fragment = new Grafici();

					Bundle argsg = new Bundle();
					argsg.putInt("NumeroGrafico", NumeroPartita);
					fragment.setArguments(argsg);

					title = "Grafico";
					VariabiliStaticheGlobali.MascheraAttuale = "Grafico";
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
                case -5:
                    // Gestione grafico minuti subiti
                    fragment = new Grafici();

                    Bundle argsg1 = new Bundle();
                    argsg1.putInt("NumeroGrafico", NumeroPartita);
                    fragment.setArguments(argsg1);

                    title = "Grafico";
                    VariabiliStaticheGlobali.MascheraAttuale = "Grafico";
                    VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

                    break;
				case -6:
					// Gestione grafico meteo 1
					fragment = new Grafici();

					Bundle argsgs = new Bundle();
					argsgs.putInt("NumeroGrafico", NumeroPartita);
					fragment.setArguments(argsgs);

					title = "Grafico";
					VariabiliStaticheGlobali.MascheraAttuale = "Grafico";
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
                case -7:
                    // Gestione grafico meteo 1
                    fragment = new Grafici();

                    Bundle argsgs2 = new Bundle();
                    argsgs2.putInt("NumeroGrafico", NumeroPartita);
                    fragment.setArguments(argsgs2);

                    title = "Grafico";
                    VariabiliStaticheGlobali.MascheraAttuale = "Grafico";
                    VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

                    break;
				case -8:
					// Gestione grafico goal segnati
					fragment = new Grafici();

					Bundle argsgs21 = new Bundle();
					argsgs21.putInt("NumeroGrafico", NumeroPartita);
					fragment.setArguments(argsgs21);

					title = "Grafico";
					VariabiliStaticheGlobali.MascheraAttuale = "Grafico";
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case -9:
					// Gestione grafico goal subiti
					fragment = new Grafici();

					Bundle argsgs22 = new Bundle();
					argsgs22.putInt("NumeroGrafico", NumeroPartita);
					fragment.setArguments(argsgs22);

					title = "Grafico";
					VariabiliStaticheGlobali.MascheraAttuale = "Grafico";
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case -10:
					// Gestione grafico goal subiti
					fragment = new Grafici();

					Bundle argsgs221 = new Bundle();
					argsgs221.putInt("NumeroGrafico", NumeroPartita);
					fragment.setArguments(argsgs221);

					title = "Grafico";
					VariabiliStaticheGlobali.MascheraAttuale = "Grafico";
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case -11:
					// Gestione grafico tipologia partite
					fragment = new Grafici();

					Bundle argsgs2212 = new Bundle();
					argsgs2212.putInt("NumeroGrafico", NumeroPartita);
					fragment.setArguments(argsgs2212);

					title = "Grafico";
					VariabiliStaticheGlobali.MascheraAttuale = "Grafico";
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case -12:
					// Gestione casa / fuori
					fragment = new Grafici();

					Bundle argsgs22121 = new Bundle();
					argsgs22121.putInt("NumeroGrafico", NumeroPartita);
					fragment.setArguments(argsgs22121);

					title = "Grafico";
					VariabiliStaticheGlobali.MascheraAttuale = "Grafico";
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case -13:
					// Gestione allenatori
					fragment = new Grafici();

					Bundle argsgs221212 = new Bundle();
					argsgs221212.putInt("NumeroGrafico", NumeroPartita);
					fragment.setArguments(argsgs221212);

					title = "Grafico";
					VariabiliStaticheGlobali.MascheraAttuale = "Grafico";
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case -14:
					// Gestione statistiche allenamenti
					fragment = new StatisticheAllenamenti();

					Bundle argsgs2212121 = new Bundle();
					fragment.setArguments(argsgs2212121);

					title = "Statistiche allenamenti";
					VariabiliStaticheGlobali.MascheraAttuale = "Statistiche Allenamenti";
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case R.id.home:
					fragment = new Home();
					title = NomiMaschere.getInstance().getHomePerTitolo();
					VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getHome();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case R.id.nuova_partita:
					if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore) || (idTipologia.equals("2") && NumeroPartita>0)) {
						fragment = new NuovaPartita();
						title = NomiMaschere.getInstance().getNuovaPartitaPerTitolo();
						VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getNuovaPartita();

						Bundle args = new Bundle();
						args.putInt("NumeroPartita", NumeroPartita);
						args.putInt("idUnioneCalendario", idUnioneCalendario);
						fragment.setArguments(args);
						VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";
					} else {
						DialogMessaggio.getInstance().show(vg.getContextPrincipale(),
								"Non si hanno i permessi per eseguire la funzionalità", true, VariabiliStaticheGlobali.NomeApplicazione);
					}
					break;
				case R.id.avversari:
					fragment = new Avversari();
					title = NomiMaschere.getInstance().getAvversariPerTitolo();
					VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getAvversari();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case R.id.rose:
					fragment = new Rose();
					title = NomiMaschere.getInstance().getRosePerTitolo();
					VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getRose();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case R.id.allenatori:
					fragment = new Allenatori();
					title = NomiMaschere.getInstance().getAllenatoriPerTitolo();
					VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getAllenatori();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case R.id.dirigenti:
					fragment = new Dirigenti();
					title = NomiMaschere.getInstance().getDirigentiPerTitolo();
					VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getDirigenti();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case R.id.eventi:
					fragment = new Eventi();
					title = NomiMaschere.getInstance().getEventiPerTitolo();
					VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getEventi();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case R.id.arbitri:
					fragment = new Arbitri();
					title = NomiMaschere.getInstance().getArbitriPerTitolo();
					VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getArbitri();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case R.id.statistiche:
					fragment = new Statistiche();
					title = NomiMaschere.getInstance().getStatistichePerTitolo();
					VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getStatistiche();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case R.id.album:
					fragment = new Album();
					title = NomiMaschere.getInstance().getAlbumPerTitolo();
					VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getAlbum();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case R.id.categorie:
					fragment = new Categorie();
					title = NomiMaschere.getInstance().getCategoriePerTitolo();
					VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getCategorie();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case R.id.multimedia:
					fragment = new Multimedia();
					title = NomiMaschere.getInstance().getMultimediaPerTitolo();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = NomiMaschere.getInstance().getMultimedia()+"_"+VariabiliStaticheGlobali.MascheraAttuale;

					break;
				case R.id.utenti:
					fragment = new Utenti();
					title = NomiMaschere.getInstance().getUtentiPerTitolo();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case R.id.about:
					fragment = new About();
					title = NomiMaschere.getInstance().getAboutPerTitolo();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case R.id.campionato:
					fragment = new Campionato();
					title = NomiMaschere.getInstance().getCampionatoPerTitolo();
					VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getCampionato();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case R.id.allenamenti:
					fragment = new Allenamenti();
					title = NomiMaschere.getInstance().getAllenamentiPerTitolo();
					VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getCampionato();
					VariabiliStaticheGlobali.MascheraAttualePerMultimedia = "";

					break;
				case R.id.uscita:
					System.exit(0);
					break;
			}

			if (!VariabiliStaticheGlobali.StaAggiornandoLaVersione) {
				if (fragment != null) {
					FragmentTransaction ft = vg.getFragmentActivityPrincipale().getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.content_frame, fragment);
					try {
						ft.commit();
					} catch (Exception ignored) {

					}
				}

				if (vg.getContextPrincipale().getSupportActionBar() != null) {
					vg.getContextPrincipale().getSupportActionBar().setTitle(title);
				}

				DrawerLayout drawer = vg.getFragmentActivityPrincipale().findViewById(R.id.drawer_layout);
				drawer.closeDrawer(GravityCompat.START);

				HelperKeyboard.closeKeyboard(vg.getContext());

				ImpostaMenu();
			}
		}
	}

	public void ImpostaMenu() {
	    try {
			vm.getItemNuovo().setVisible(false);
			vm.getItemMultimedia().setVisible(false);
			vm.getActButtonNew().hide(); // .setVisibility(LinearLayout.GONE);
			vm.getActButtonBack().hide(); // .setVisibility(LinearLayout.GONE);

			if (VariabiliStaticheGlobali.MascheraAttualePerMultimedia==null) {
                VariabiliStaticheGlobali.MascheraAttualePerMultimedia="";
            }
            if (VisualizzaImmagini.MascheraApertaPer==null) {
                VisualizzaImmagini.MascheraApertaPer="";
            }

			if (VariabiliStaticheGlobali.MascheraAttualePerMultimedia.isEmpty() && VisualizzaImmagini.MascheraApertaPer.isEmpty()) {
				if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getAllenatori()) ||
						VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getAvversari()) ||
						VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getCategorie()) ||
						VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getDirigenti()) ||
						VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getArbitri()) ||
						VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getEventi()) ||
						VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getRose())) {
					vm.getItemNuovo().setVisible(true);
					vm.getActButtonNew().show(); // .setVisibility(LinearLayout.VISIBLE);
				}
				if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getNuovaPartita())) {
					vm.getItemMultimedia().setVisible(true);
				}
				if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getCategorie())) {
					vm.getItemMultimedia().setVisible(true);
				}
				if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getRose())) {
					vm.getItemMultimedia().setVisible(true);
				}
				if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getMultimedia())) {
					vm.getItemNuovo().setVisible(true);
					vm.getActButtonNew().show(); // .setVisibility(LinearLayout.VISIBLE);
				}
				if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getCampionato())) {
					vm.getItemNuovo().setVisible(true);
					vm.getActButtonNew().show(); // .setVisibility(LinearLayout.VISIBLE);
				}
			} else {
				vm.getActButtonBack().show(); // .setVisibility(LinearLayout.VISIBLE);
				vm.getActButtonNew().show(); // .setVisibility(LinearLayout.VISIBLE);
            }
        } catch (Exception ignored) {

        }
	}

	public boolean ePari(int numero) {
		if ((numero % 2) == 0) {
			return true;
		} else {
			return false;
		}
	}

	public void CreaCartella(String PercorsoDIR) {
		File dDirectory = new File(PercorsoDIR);
		try {
			dDirectory.mkdir();
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}
	}

	public boolean fileExistsInSD(String sFileName, String Percorso){
		String sFile=Percorso+"/"+sFileName;
		File file = new File(sFile);

		return file.exists();
	}

	public Integer CercaESettaStringaInSpinner(Spinner spn, String Ricerca) {
		int ritorno=-1;

		for (int i=0; i<spn.getCount();i++) {
			if (spn.getItemAtPosition(i)!=null && Ricerca!=null) {
				if (spn.getItemAtPosition(i).toString().trim().toUpperCase().equals(Ricerca.trim().toUpperCase())) {
					ritorno = i;
					break;
				}
			}
		}

		return ritorno;
	}

	public void generateNoteOnSD(String Percorso, String sFileName, String sBody) {
		try {
			File gpxfile = new File(Percorso, sFileName);
			FileWriter writer = new FileWriter(gpxfile);
			writer.append(sBody);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ScriveAnno() {
		vm.gettAnno().setText("Anno: " + vg.getDescAnnoInCorso());
		vm.gettDescAnno().setText(VariabiliStaticheUtenti.NomeSquadraScelta);
	}

	public void saveImageFile(Bitmap bitmap, String filename) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filename);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void saveVideoFile(String filename) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void CreaCartelle(String Percorso) {
		String Campi[]=(Percorso+"/").split("/");
		String ss="";

		for (String s : Campi) {
			if (!s.isEmpty()) {
				ss += "/" + s;
				Utility.getInstance().CreaCartella(ss);
				if (!Utility.getInstance().fileExistsInSD(".noMedia",ss )) {
					// Crea file per nascondere alla galleria i files immagine della cartella
					Utility.getInstance().generateNoteOnSD(ss, ".noMedia","");
				}
			}
		}
	}

	public void EsegueChiamata(Context context, String ws, String Urletto, String TipoOperazione,
                               String Ricerca, String Maschera, String NS, String SA) {
		new GestioneWEBServiceSOAP(context,
				VariabiliStaticheGlobali.RadiceWS + ws + Urletto,
				TipoOperazione,
				Ricerca,
				Maschera,
				NS,
				SA);
	}

	public String getVersion(Context context) {
		String version = "";

		try {
			PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			version = pInfo.versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return version;
	}

	public LatLng RitornaCoordinateDaIndirizzo(Context context, String Indirizzo) {
		LatLng r=null;
		Geocoder geocoder = new Geocoder(context);
		List<Address> addresses;
		try {
			addresses = geocoder.getFromLocationName(Indirizzo, 1);
			if (addresses.size() > 0) {
				double latitude = addresses.get(0).getLatitude();
				double longitude = addresses.get(0).getLongitude();
				r= new LatLng(latitude, longitude);
			}
		} catch (IOException ignored) {

		}
		return r;
	}

	public String getPath(Uri uri)
	{
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale()
				.getContentResolver().query(uri, projection, null, null, null);
		if (cursor == null) return null;
		int column_index =  cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		String s=cursor.getString(column_index);
		cursor.close();

		return s;
	}

	public boolean isNumeric(String str)
	{
		try
		{
			double d = Double.parseDouble(str);
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
		return true;
	}

	public void copy(File src, File dst) throws IOException {
		FileInputStream inStream = new FileInputStream(src);
		FileOutputStream outStream = new FileOutputStream(dst);
		FileChannel inChannel = inStream.getChannel();
		FileChannel outChannel = outStream.getChannel();
		inChannel.transferTo(0, inChannel.size(), outChannel);
		inStream.close();
		outStream.close();
	}

	/* public void SettaColoreSfondoPerNomeSquadra(TextView t){
		if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraPonteDiNona)) {
			t.setBackgroundColor(Color.argb(255, 200, 0, 0));
		} else {
			if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraCastelVerde)) {
				t.setBackgroundColor(Color.argb(255, 0, 200, 0));
			}
		}
	} */

	/* public void SettaColoreTestoPerNomeSquadra(TextView t){
		if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraPonteDiNona)) {
			t.setTextColor(Color.argb(255, 136, 0, 0));
		} else {
			if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraCastelVerde)) {
				t.setTextColor(Color.argb(255, 0, 136, 136));
			}
		}
	} */

	/* public void SettaColoreSceltaCategoria(View view) {
		// TextView txtCatTit = (TextView) view.findViewById(R.id.txtCategoriaTit);
		LinearLayout layCat = view.findViewById(R.id.layCat);
		if (VariabiliStaticheMain.getInstance().getSquadra()!=null) {
			if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraCastelVerde)) {
				// txtCatTit.setTextColor(Color.BLACK);
				layCat.setBackgroundResource(R.drawable.bordo_arrotondato_verde);
			} else {
				if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraPonteDiNona)) {
					// txtCatTit.setTextColor(Color.WHITE);
					layCat.setBackgroundResource(R.drawable.bordo_arrotondato_rosso);
				}
			}
		}
	} */

	public void deleteFolder(File fileOrDirectory) {
		if (fileOrDirectory.isDirectory())
			for (File child : fileOrDirectory.listFiles())
				deleteFolder(child);

		fileOrDirectory.delete();
	}

	public String SistemaData(String Data) {
		String d[]=Data.split("/");
		String dd = d[0].trim();
		if (dd.length()==1) dd="0"+dd;
		String mm = d[1].trim();
		if (mm.length()==1) mm="0"+mm;
		String aa = d[2].trim();
		String date = dd+"/"+mm+"/"+aa;

		return date;
	}

	public String SistemaOra(String Ora) {
		String m[]=Ora.split(":");
		String oo = m[0].trim();
		if (oo.length()==1) oo="0"+oo;
		String mmm = m[1].trim();
		int m1 = Integer.parseInt(mmm);
		if (m1>30) {
			mmm="30";
		} else {
			mmm="00";
		}
		String ss = "00";
		if (ss.length()==1) ss="0"+ss;
		String ora = oo+":"+mmm+":"+ss;

		return ora;
	}

	public void PrendeCoordinateDaIndirizzo(View view) {
		CheckBox chkInCasa = (CheckBox) view.findViewById(R.id.chkInCasa);
		CheckBox chkEsterno = (CheckBox) view.findViewById(R.id.chkEsterno);
		String Indirizzo;
		String Lat="";
		String Lon="";
		if (chkInCasa.isChecked()) {
			Lat=VariabiliStaticheGlobali.getInstance().getLatAnno();
			Lon=VariabiliStaticheGlobali.getInstance().getLonAnno();

			Indirizzo="";
		} else {
			if (chkEsterno.isChecked()) {
				Indirizzo = VariabiliStaticheNuovaPartita.getInstance().getEdtCampoEsterno().getText().toString();
			} else {
				Indirizzo = VariabiliStaticheNuovaPartita.getInstance().getTxtCampoIndirizzo().getText().toString();
			}
		}

		if (!Indirizzo.isEmpty()) {
			LatLng l = Utility.getInstance().RitornaCoordinateDaIndirizzo(VariabiliStaticheNuovaPartita.getInstance().getContext(),
					Indirizzo);
			if (l != null) {
				VariabiliStaticheMeteo.getInstance().setLat(Double.toString(l.latitude));
				VariabiliStaticheMeteo.getInstance().setLon(Double.toString(l.longitude));

				VariabiliStaticheNuovaPartita.getInstance().getTxtLat().setText(Double.toString(l.latitude));
				VariabiliStaticheNuovaPartita.getInstance().getTxtLon().setText(Double.toString(l.longitude));
			}
		} else {
			if (!Lat.isEmpty() && !Lon.isEmpty()) {
				VariabiliStaticheMeteo.getInstance().setLat(Lat);
				VariabiliStaticheMeteo.getInstance().setLon(Lon);

				VariabiliStaticheNuovaPartita.getInstance().getTxtLat().setText(Lat);
				VariabiliStaticheNuovaPartita.getInstance().getTxtLon().setText(Lon);
			} else {
				VariabiliStaticheMeteo.getInstance().setLat("");
				VariabiliStaticheMeteo.getInstance().setLon("");

				VariabiliStaticheNuovaPartita.getInstance().getTxtLat().setText("");
				VariabiliStaticheNuovaPartita.getInstance().getTxtLon().setText("");
			}
		}
	}
}
