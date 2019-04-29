package looigi.gestionecampionato.db_remoto;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.ritorni_ws.wsMeteo;

public class DBRemotoMeteo {
	private String ws = "data/2.5/";
	private String NS="http://api.openweathermap.org/";
	private String SA="http://api.openweathermap.org/";
	private String Urletto="";
    private String returns = "";

    public void RitornaMeteoTramiteCoordinate(Double lat, Double lon) {
		Urletto="weather?";
		Urletto+="lon=" + Double.toString(lon);
		Urletto+="&lat=" + Double.toString(lat);
        Urletto+="&units=metric";
		Urletto+="&appid=1856b7a9244abb668591169ef0a34308";

        new RetrieveMeteo().execute(NS + ws + Urletto);
	}

    class RetrieveMeteo extends AsyncTask<String, Void, Void> {
        protected Void doInBackground(String... urls) {
            boolean errore=false;
            returns="";

            try {
                URL url = new URL(urls[0]);
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                while ((str = in.readLine()) != null) {
                    returns += str;
                }
                in.close();
            } catch (MalformedURLException ignored) {
                errore=true;
            } catch (NetworkOnMainThreadException ignored) {
                errore=true;
            } catch (IOException ignored) {
                errore=true;
            }

            if (!errore) {
                DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                        "Meteo impostato", false, VariabiliStaticheGlobali.NomeApplicazione);

                wsMeteo wsm = new wsMeteo();
                wsm.RitornaMeteo(returns);
            } else {
                DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                        "Problemi nel rilevare il meteo", true, VariabiliStaticheGlobali.NomeApplicazione);

            }

            return null;
        }

        protected void onPostExecute() {
        }
    }
}
