package looigi.gestionecampionato.gps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GPSStatusBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        PrendeCoordinateGPS.getInstance().AttivaGPS(arg0);
    }
}