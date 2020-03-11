package looigi.gestionecampionato.gps;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMeteo;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.db_remoto.DBRemotoMeteo;
import looigi.gestionecampionato.maschere.NuovaPartita;

public class PrendeCoordinateGPS implements LocationListener {
	private Context cnt;
	private LocationManager mLoc;
	private Boolean Fermati;

	private static PrendeCoordinateGPS instance = null;

	private PrendeCoordinateGPS() {
	}

	public static PrendeCoordinateGPS getInstance() {
		if(instance == null) {
			instance = new PrendeCoordinateGPS();
		}

		return instance;
	}

	public void AttivaGPS(final Context context) {
		cnt=context;

		if (mLoc==null) {
			mLoc = (LocationManager) cnt.getSystemService(Context.LOCATION_SERVICE);
		}

		Fermati=false;
		ImpostaLM();
	}
	
	private void ImpostaLM() {
		try {
			if (mLoc!=null) {
				if (mLoc.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
					GestisceIconaGPS(true);

					mLoc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
				} else {
					if (mLoc.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
						GestisceIconaGPS(false);

						mLoc.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, this);
					} else {
						if (mLoc.isProviderEnabled(LocationManager.PASSIVE_PROVIDER )) {
							GestisceIconaGPS(false);

							mLoc.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 500, 1, this);
						}
					}
				}
			}
		} catch (SecurityException ignored) {
			int a=0;
		}
	}

	private void GestisceIconaGPS(Boolean Acceso) {
		//if (Acceso) {
		//	GPSAcceso=true;
		//} else {
		//	GPSAcceso=false;
		//}
	}
	
	private void DisattivaGPS() {
		Fermati=true;
		mLoc.removeUpdates(this);
		mLoc=null;
	}
	
	@Override
	public void onLocationChanged(Location arg0) {
		if (!Fermati) {
			double lat = arg0.getLatitude();
			double lon = arg0.getLongitude();
			// float precisione=arg0.getAccuracy();

			// if (precisione<35) {
			VariabiliStaticheMeteo.getInstance().setLat(Double.toString(lat));
			VariabiliStaticheMeteo.getInstance().setLon(Double.toString(lon));
			NuovaPartita.ScriveCoords();

			if (VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale() != null) {
				VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale().stopService(VariabiliStaticheNuovaPartita.getInstance().intentGPS);
			}
			VariabiliStaticheNuovaPartita.getInstance().intentGPS = null;

			DisattivaGPS();

			DBRemotoMeteo dbr = new DBRemotoMeteo();
			dbr.RitornaMeteoTramiteCoordinate(lat, lon);
		}
		// }
	}

	@Override
	public void onProviderDisabled(String arg0) {
		try {
			GestisceIconaGPS(false);

			if (mLoc.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
				mLoc.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, this);
			} else {
				if (mLoc.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
					mLoc.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 500, 1, this);
				}
			}
		} catch (SecurityException ignored) {
		}
	}

	@Override
	public void onProviderEnabled(String arg0) {
		try {
			GestisceIconaGPS(true);

			if (mLoc.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				mLoc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);
			}
		} catch (SecurityException ignored) {
		}
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		ImpostaLM();
	}
}
