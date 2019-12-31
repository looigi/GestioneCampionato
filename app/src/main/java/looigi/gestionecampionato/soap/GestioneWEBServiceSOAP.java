package looigi.gestionecampionato.soap;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.SocketTimeoutException;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.ritorni_ws.wsAllenamenti;
import looigi.gestionecampionato.ritorni_ws.wsAllenatori;
import looigi.gestionecampionato.ritorni_ws.wsArbitri;
import looigi.gestionecampionato.ritorni_ws.wsAvversari;
import looigi.gestionecampionato.ritorni_ws.wsCampionato;
import looigi.gestionecampionato.ritorni_ws.wsCategorie;
import looigi.gestionecampionato.ritorni_ws.wsDirigenti;
import looigi.gestionecampionato.ritorni_ws.wsEventi;
import looigi.gestionecampionato.ritorni_ws.wsGenerale;
import looigi.gestionecampionato.ritorni_ws.wsGiocatori;
import looigi.gestionecampionato.ritorni_ws.wsMeteo;
import looigi.gestionecampionato.ritorni_ws.wsMultimedia;
import looigi.gestionecampionato.ritorni_ws.wsPartite;
import looigi.gestionecampionato.ritorni_ws.wsStatAllenamenti;
import looigi.gestionecampionato.ritorni_ws.wsStatistiche;
import looigi.gestionecampionato.ritorni_ws.wsUtenti;

public class GestioneWEBServiceSOAP {
	private ProgressDialog progressDialog;
	private String tOperazione;
	private Boolean Errore;
  	private Handler handlerF;
  	private Runnable rTimerF;
  	private Context conx;
  	private String Urletto;
  	//private int SecondiPassati;
  	private String messErrore;
	private String Maschera;
    private String Ricerca;

    private String NAMESPACE; //  = "http://cvcalcio.org/";
    private String METHOD_NAME = "";
    private String SOAP_ACTION; //  = "http://cvcalcio.org/";
    private String sURL = "";
    private String Parametri[];
    private String result="";
 	
	//@Override
	//public void onCreate(Bundle savedInstanceState) {
	//    super.onCreate(savedInstanceState);
	//}
	
	public GestioneWEBServiceSOAP(Context context, String urletto, String TipoOperazione, String Ricerca,
                                  String Maschera, String NS, String SA) {
		tOperazione=TipoOperazione;
		conx=context;
		this.Maschera=Maschera;
		this.Ricerca=Ricerca;
		this.NAMESPACE=NS;
		this.SOAP_ACTION=SA;

		ApriDialog();

		VariabiliStaticheGlobali.getInstance().setOperazioneInCorso(tOperazione);
		
		Urletto=urletto;
		
		//SecondiPassati = 0;
		SplittaCampiUrletto(Urletto);
		
		Errore=false;

		Esegue(conx, Urletto);
	}

	private void SplittaCampiUrletto(String Cosa) {
		String Perc=Cosa;
		int pos;
		String Indirizzo="";
		String[] Variabili;
		String Funzione="";
		
		pos=Perc.indexOf("?");
		if (pos>-1) {
			Indirizzo=Perc.substring(0, pos);
			for (int i=Indirizzo.length()-1;i>0;i--) {
				if (Indirizzo.substring(i, i+1).equals("/")) {
					Funzione=Indirizzo.substring(i+1, Indirizzo.length());
					Indirizzo=Indirizzo.substring(0, i);
					break;
				}
			}
			sURL=Indirizzo;
			METHOD_NAME = Funzione;
			SOAP_ACTION = NAMESPACE + Funzione;
			Perc=Perc.substring(pos+1, Perc.length());
			pos=Perc.indexOf("&");
			if (pos>-1) {
				Variabili=Perc.split("&");
			} else {
				Variabili=new String[1];
				Variabili[0]=Perc;
			}
			Parametri=Variabili;
		} else {
			Indirizzo=Perc;
			for (int i=Indirizzo.length()-1;i>0;i--) {
				if (Indirizzo.substring(i, i+1).equals("/")) {
					Funzione=Indirizzo.substring(i+1, Indirizzo.length());
					Indirizzo=Indirizzo.substring(0, i);
					break;
				}
			}
			sURL=Indirizzo;
			METHOD_NAME = Funzione;
			SOAP_ACTION = NAMESPACE + Funzione;
		}
	}
	
	private void Esegue(final Context context, final String Urletto) {
    	new BackgroundAsyncTask(context).execute(Urletto);
	}
	
	public void ChiudeDialog() {
        try {
        	progressDialog.dismiss();
        } catch (Exception ignored) {
        }

        if (!messErrore.isEmpty()) {
			DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), "ERRORE: "+messErrore, true, VariabiliStaticheGlobali.NomeApplicazione);
		}

        VariabiliStaticheGlobali.getInstance().setOperazioneInCorso("");
	}
	
	private void ApriDialog() {
		if (VariabiliStaticheGlobali.getInstance().ApreDialogWS) {
			try {
				progressDialog = new ProgressDialog(conx);
				progressDialog.setMessage("Attendere Prego");
				progressDialog.setCancelable(false);
				progressDialog.setCanceledOnTouchOutside(false);
				progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				progressDialog.show();
			} catch (Exception ignored) {

			}
		}
	}
	
	private class BackgroundAsyncTask extends AsyncTask<String, Integer, String> {
		private Context context;
		
	    private BackgroundAsyncTask(Context cxt) {
	        context = cxt;
	    }
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
		
		@Override
		protected void onPostExecute(String p) {
			super.onPostExecute(p);
			
			ControllaFineCiclo();
		}

	    @Override
	    protected String doInBackground(String... sUrl) {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            String Parametro="";
            String Valore="";
            
            if (Parametri!=null) {
	            for (int i=0;i<Parametri.length;i++) {
	            	int pos=Parametri[i].indexOf("=");
	            	if (pos>-1) {
	            		Parametro=Parametri[i].substring(0, pos);
	            		Valore=Parametri[i].substring(pos+1,Parametri[i].length());
	            	}
	            	Request.addProperty(Parametro, Valore);
	            }
            }

            SoapSerializationEnvelope soapEnvelope = null;
            HttpTransportSE aht = null;
            messErrore="";
            try {
                soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    			soapEnvelope.dotNet = true;
                soapEnvelope.setOutputSoapObject(Request);
                aht = new HttpTransportSE(sURL, 20000);
                aht.reset();
                aht.call(SOAP_ACTION, soapEnvelope);
            } catch (SocketTimeoutException e) {
            	Errore=true;
            	messErrore=e.getMessage();
            	if (messErrore!=null) {
            		messErrore=messErrore.toUpperCase().replace("WWW.LOOIGI.IT","Web Service");
            	} else {
            		messErrore="Unknown";
            	}
            	result="ERROR: "+messErrore;
				//Utility.getInstance().VisualizzaPOPUP(context, "Errore di socket sul DB:\n" + messErrore, false, 0, false);
			} catch (IOException e) {
            	Errore=true;
            	messErrore=e.getMessage();
            	if (messErrore!=null)
            		messErrore=messErrore.toUpperCase().replace("WWW.LOOIGI.IT","Web Service");
            	result="ERROR: "+messErrore;
				//Utility.getInstance().VisualizzaPOPUP(context, "Errore di I/O dal DB:\n" + messErrore, false, 0, false);
            } catch (XmlPullParserException e) {
            	Errore=true;
            	messErrore=e.getMessage();
            	if (messErrore!=null) {
            		messErrore=messErrore.toUpperCase().replace("WWW.LOOIGI.IT","Web Service");
            	} else {
            		messErrore="Unknown";
            	}
            	result="ERRORE: "+messErrore;
				//Utility.getInstance().VisualizzaPOPUP(context, "Errore di parsing XML:\n" + messErrore, false, 0, false);
            } catch (Exception e) {
            	Errore=true;
            	messErrore=e.getMessage();
            	if (messErrore!=null)
            		messErrore=messErrore.toUpperCase().replace("WWW.LOOIGI.IT","Web Service");
            	result="ERROR: "+messErrore;
				//Utility.getInstance().VisualizzaPOPUP(context, "Errore generico di lettura sul DB:\n" + messErrore, false, 0, false);
            }
            if (!Errore) {
	            try {
	                result = ""+soapEnvelope.getResponse();
	            } catch (SoapFault e) {
	            	Errore=true;
	            	messErrore=e.getMessage();
	            	if (messErrore!=null) {
	            		messErrore=messErrore.toUpperCase().replace("WWW.LOOIGI.IT","Web Service");
	            	} else {
	            		messErrore="Unknown";
	            	}
	            	result="ERRORE: "+messErrore;
	            }
            }
            if (aht!=null) {
            	aht=null;
            }
            if (soapEnvelope!=null) {
            	soapEnvelope=null;
            }

			return null;
	    }
	 	
	    private void ControllaFineCiclo() {
			String Ritorno=result;

			if (!Errore) {
				wsAllenatori rAll = new wsAllenatori();
				wsAllenamenti rAllti = new wsAllenamenti();
				wsDirigenti rDir = new wsDirigenti();
				wsEventi rEve = new wsEventi();
				wsArbitri rArb = new wsArbitri();
				wsAvversari rAvv = new wsAvversari();
				wsCategorie rCat = new wsCategorie();
				wsGenerale rGen = new wsGenerale();
				wsGiocatori rGio = new wsGiocatori();
				wsMultimedia rMul = new wsMultimedia();
				wsPartite rPar = new wsPartite();
				wsStatistiche rSta = new wsStatistiche();
				wsUtenti rUte = new wsUtenti();
				wsMeteo rMet = new wsMeteo();
				wsCampionato rCam = new wsCampionato();
				wsStatAllenamenti rSA= new wsStatAllenamenti();

				boolean Ancora=true;
				while (Ancora) {
					switch (tOperazione) {
						case "EliminaPartitaGEN":
							rPar.EliminaPartita(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "ControllaEsistenzaDB":
							rGen.ControllaEsistenzaDB(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaSquadrePerSceltaIniziale":
							rGen.RitornaSquadrePerSceltaIniziale(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaCategorie":
							rCat.RitornaCategorie(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaCategoriePerAnno":
							rCat.RitornaCategoriePerAnno(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaTipologie":
							rGen.RitornaTipologie(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaAvversari":
							rAvv.RitornaAvversari(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaGiocatoriCategoria":
							rGio.RitornaGiocatoriCategoria(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaAllenatoriCategoria":
							rAll.RitornaAllenatoriCategoria(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaStatAllenamentiCategoria":
							rSA.RitornaStatAllenamentiCategoria(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaInfo":
							rSA.RitornaInfo(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaDirigentiCategoria":
							rDir.RitornaDirigentiCategoria(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaEventi":
							rEve.RitornaEventi(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "SalvaEvento":
							rEve.SalvaEvento(Ritorno, Maschera);
							Ancora=false;
							break;
						case "EliminaEvento":
							rEve.EliminaEvento(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaArbitri":
							rArb.RitornaArbitri(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaIdPartita":
							rPar.RitornaIdPartita(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "CreaFoglioConvocazioni":
							rPar.CreaFoglioConvocazioni(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaPartite":
							rPar.RitornaPartite(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaPartitaDaID":
							rPar.RitornaPartitaDaID(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaRuoli":
							rGen.RitornaRuoli(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaAnni":
							rGen.RitornaAnni(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "SalvaPartita":
							rPar.SalvaPartita(Ritorno, Maschera);
							Ancora=false;
							break;
						case "SalvaCategoria":
							rCat.SalvaCategoria(Ritorno, Maschera);
							Ancora=false;
							break;
						case "SalvaAllenatore":
							rAll.SalvaAllenatore(Ritorno, Maschera);
							Ancora=false;
							break;
						case "EliminaAllenatore":
							rAll.EliminaAllenatore(Ritorno, Maschera);
							Ancora=false;
							break;
						case "SalvaDirigente":
							rDir.SalvaDirigente(Ritorno, Maschera);
							Ancora=false;
							break;
						case "SalvaArbitro":
							rArb.SalvaArbitro(Ritorno, Maschera);
							Ancora=false;
							break;
						case "EliminaDirigente":
							rDir.EliminaDirigente(Ritorno, Maschera);
							Ancora=false;
							break;
						case "EliminaArbitro":
							rArb.EliminaArbitro(Ritorno, Maschera);
							Ancora=false;
							break;
						case "SalvaAvversario":
							rAvv.SalvaAvversario(Ritorno, Ricerca, Maschera);
							Ancora=false;
							break;
						case "SalvaGiocatore":
							rGio.SalvaGiocatore(Ritorno, Ricerca, Maschera);
							Ancora=false;
							break;
						case "EliminaGiocatore":
							rGio.EliminaGiocatore(Ritorno, Maschera);
							Ancora=false;
							break;
						case "EliminaImmagine":
							rMul.EliminaImmagine(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaAlbumPerCategoria":
							rMul.RitornaAlbumPerCategoria(Ritorno, Maschera);
							Ancora=false;
							break;
						case "EliminaAvversario":
							rAvv.EliminaAvversario(Ritorno, Ricerca, Maschera);
							Ancora=false;
							break;
						case "EliminaCategoria":
							rCat.EliminaCategoria(Ritorno, Maschera);
							Ancora=false;
							break;
						case "EliminaMultimedia":
							rMul.EliminaMultimedia(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaMultimedia":
							rMul.RitornaMultimedia(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaStatisticheAvversari":
							rSta.RitornaStatisticheAvversari(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaTipologiaPartite":
							rSta.RitornaTipologiaPartite(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaStatisticheStagione":
							rSta.RitornaStatisticheStagione(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaPartiteCasaFuori":
							rSta.RitornaPartiteCasaFuori(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaPartiteAllenatore":
							rSta.RitornaPartiteAllenatore(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaStatisticheConvocati":
							rSta.RitornaStatisticheConvocati(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaStatisticheMarcatori":
							rSta.RitornaStatisticheMarcatori(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaStatisticheRisultati":
							rSta.RitornaStatisticheRisultati(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaStatisticheMeteo":
							rSta.RitornaStatisticheMeteo(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaStatisticheMinutiGoal":
							rSta.RitornaStatisticheMinutiGoal(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaStatisticheGoalSegnatiSubiti":
							rSta.RitornaStatisticheGoalSegnatiSubiti(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaAndamento":
							rSta.RitornaAndamento(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaUtenteDaID":
							rUte.RitornaUtenteDaID(Ritorno, Maschera);
							Ancora=false;
							break;
						case "SalvaUtente":
							rUte.SalvaUtente(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaUtentePerLogin":
							rUte.RitornaUtentePerLogin(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaVersioneApplicazione":
							rGen.RitornaVersioneApplicazione(Ritorno);
							Ancora=false;
							break;
						case "RitornaStatisticheMappa":
							rSta.RitornaStatisticheMappa(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaMeteo":
							rMet.RitornaMeteo(Ritorno);
							Ancora=false;
							break;
						case "RitornaListaUtenti":
							rUte.RitornaListaUtenti(Ritorno);
							Ancora=false;
							break;
						case "ModificaUtente":
							rUte.RitornaModificaUtente(Ritorno);
							Ancora=false;
							break;
						case "RitornaStatisticheAvversario":
							rAvv.RitornaStatisticheAvversario(Ritorno);
							Ancora=false;
							break;
						case "RitornaMaxAnno":
							rGen.RitornaMaxAnno(Ritorno);
							Ancora=false;
							break;
						case "RitornaValoriPerRegistrazione":
							rGen.RitornaValoriPerRegistrazione(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaAnnoAttualeUtenti":
							rGen.RitornaAnnoAttuale(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "ImpostaAnnoAttualeUtente":
							rGen.ImpostaAnnoAttualeUtente(context, Ritorno, Maschera);
							Ancora=false;
							break;
						case "CreaNuovoAnno":
							rGen.CreaNuovoAnno(Ritorno);
							Ancora=false;
							break;
						case "RitornaCampionatoCategoria":
							rCam.RitornaCampionatoCategoria(context, Ritorno);
							Ancora=false;
							break;
						case "AggiungeSquadraAvversaria":
							rCam.AggiungeSquadraAvversaria(Ritorno);
							Ancora=false;
							break;
						case "EliminaSquadraAvversaria":
							rCam.EliminaSquadraAvversaria(Ritorno);
							Ancora=false;
							break;
						case "InserisceNuovaPartita":
							rCam.InserisceNuovaPartita(Ritorno);
							Ancora=false;
							break;
						case "ModificaPartitaAltre":
							rCam.ModificaPartitaAltre(context, Ritorno);
							Ancora=false;
							break;
						case "EliminaPartita":
							rCam.EliminaPartita(Ritorno);
							Ancora=false;
							break;
						case "RitornaClassifica":
							rCam.RitornaClassifica(Ritorno);
							Ancora=false;
							break;
						case "RitornaIdPartitaDaUnione":
							rCam.RitornaIdPartitaDaUnione(context, Ritorno);
							Ancora=false;
							break;
						case "SalvaAllenamenti":
							rAllti.SalvaAllenamenti(Ritorno, Maschera);
							Ancora=false;
							break;
						case "RitornaAllenamentiCategoria":
							rAllti.RitornaAllenamentiCategoria(context, Ritorno, Maschera);
							Ancora=false;
							break;
					}
				}
			}
			
	        ChiudeDialog();
	    }
	    
	    @Override
	    protected void onProgressUpdate(Integer... progress) {
	        super.onProgressUpdate(progress);
	        
	    }

		@Override
		protected void onCancelled(){
		}
   
	}

	public void cancel(boolean b) {
		if (b) {
		}
	}
}
