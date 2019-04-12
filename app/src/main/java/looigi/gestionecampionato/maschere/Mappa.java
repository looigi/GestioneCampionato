package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheStatistiche;
import looigi.gestionecampionato.db_remoto.DBRemotoStatistiche;
import looigi.gestionecampionato.utilities.Utility;

public class Mappa extends android.support.v4.app.Fragment implements OnMapReadyCallback {
    private static GoogleMap mMap;
    private static String TAG = "MAPPA";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = this.getActivity();
        VariabiliStaticheStatistiche.getInstance().setContext(context);

        View view=null;

        try {
            view=(inflater.inflate(R.layout.mappa, container, false));
        } catch (Exception ignored) {

        }

        if (view!=null) {
            VariabiliStaticheGlobali.getInstance().setViewActivity(view);

            initializeGraphic();
        }

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //isVisible=isVisibleToUser;

        //if (isVisible) {
        //    initializeGraphic();
        //}
    }

    @Override
    public void onResume()
    {
        super.onResume();

        //if (isVisible) {
        //    initializeGraphic();
        //}
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public static void DisegnaMappa() {
        int Partite=0;

        Double km = 0D;
        LatLng lCasa = VariabiliStaticheStatistiche.getInstance().getCoordsCasa();

        mMap.addMarker(new MarkerOptions().position(lCasa).title(VariabiliStaticheStatistiche.getInstance().getDescrMarkers().get(0)));

        for (LatLng ll : VariabiliStaticheStatistiche.getInstance().getCoords()) {
            km += distance(lCasa.latitude, lCasa.longitude, ll.latitude, ll.longitude);
            mMap.addMarker(new MarkerOptions().position(ll).title(
                    VariabiliStaticheStatistiche.getInstance().getDescrMarkers().get(Partite+1).replace(">","")));
            mMap.setInfoWindowAdapter(new InfoWindowCustom(VariabiliStaticheStatistiche.getInstance().getContext()));
            Partite++;
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        int q = 0;
        for (LatLng point : VariabiliStaticheStatistiche.getInstance().getCoords()) {
            builder.include(point);
            q++;
        }
        if (q>0) {
            LatLngBounds bounds = builder.build();
            int padding = 100;
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.moveCamera(cu);
            mMap.animateCamera(cu, 2000, null);
        }
        int i = Integer.valueOf(km.intValue()*100);
        float kmF = i/100;
        VariabiliStaticheStatistiche.getInstance().gettStat().setText("Partite: "+VariabiliStaticheStatistiche.getInstance().NumPartite+" - Km. Effettuati (linea d'aria): "+Float.toString(kmF));
    }

    private static double distance(Double latitude, Double longitude, double e, double f) {
        double d2r = Math.PI / 180;

        double dlong = (longitude - f) * d2r;
        double dlat = (latitude - e) * d2r;
        double a = Math.pow(Math.sin(dlat / 2.0), 2) + Math.cos(e * d2r)
                * Math.cos(latitude * d2r) * Math.pow(Math.sin(dlong / 2.0), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = 6367 * c;

        return d*2;
    }

    private void initializeGraphic() {
        final Context context = VariabiliStaticheGlobali.getInstance().getContext();
        View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.Mappa);
            mapFragment.getMapAsync(this);

            VariabiliStaticheStatistiche.getInstance().settStat((TextView) view.findViewById(R.id.txtStat));
            VariabiliStaticheStatistiche.getInstance().gettStat().setText("");

            DBRemotoStatistiche dbr = new DBRemotoStatistiche();
            dbr.RitornaStatisticheMappa(context, TAG, "S", Integer.toString(VariabiliStaticheStatistiche.getInstance().idCategoriaScelta));

            android.support.design.widget.FloatingActionButton btnBack=view.findViewById(R.id.fabBackMappa);
            btnBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Utility.getInstance().CambiaMaschera(R.id.statistiche , -1, -1);
                }
            });

        }
    }
}

class InfoWindowCustom implements GoogleMap.InfoWindowAdapter {
    Context context;
    LayoutInflater inflater;
    public InfoWindowCustom(Context context) {
        this.context = context;
    }
    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.marker_windows, null);

        TextView txtData = (TextView) v.findViewById(R.id.txtData);
        TextView txtAvversario = (TextView) v.findViewById(R.id.txtAvversario);
        TextView txtCampo = (TextView) v.findViewById(R.id.txtCampo);
        TextView txtIndirizzo = (TextView) v.findViewById(R.id.txtIndirizzo);

        TextView txtTitData = (TextView) v.findViewById(R.id.txtTitData);
        TextView txtTitAvversario = (TextView) v.findViewById(R.id.txtTitAvversario);
        TextView txtTitCampo = (TextView) v.findViewById(R.id.txtTitCampo);
        TextView txtTitIndirizzo = (TextView) v.findViewById(R.id.txtTitIndirizzo);

        String c[] = marker.getTitle().split(";");

        if (c.length>3) {
            txtData.setText(c[0]);
            txtAvversario.setText(c[1]);
            txtCampo.setText(c[2]);
            txtIndirizzo.setText(c[3]);

            txtAvversario.setVisibility(LinearLayout.VISIBLE);
            txtCampo.setVisibility(LinearLayout.VISIBLE);
            txtIndirizzo.setVisibility(LinearLayout.VISIBLE);

            txtTitAvversario.setVisibility(LinearLayout.VISIBLE);
            txtTitCampo.setVisibility(LinearLayout.VISIBLE);
            txtTitIndirizzo.setVisibility(LinearLayout.VISIBLE);
        } else {
            if (c.length>2) {
                txtData.setText(c[0]);
                txtAvversario.setText(c[1]);
                txtCampo.setVisibility(LinearLayout.GONE);
                txtTitCampo.setVisibility(LinearLayout.GONE);
                txtIndirizzo.setText(c[2]);
            } else {
                txtCampo.setText(c[0]);

                txtCampo.setVisibility(LinearLayout.VISIBLE);
                txtTitCampo.setVisibility(LinearLayout.VISIBLE);

                txtAvversario.setVisibility(LinearLayout.GONE);
                txtData.setVisibility(LinearLayout.GONE);
                txtIndirizzo.setVisibility(LinearLayout.GONE);

                txtTitAvversario.setVisibility(LinearLayout.GONE);
                txtTitData.setVisibility(LinearLayout.GONE);
                txtTitIndirizzo.setVisibility(LinearLayout.GONE);
            }
        }

        return v;
    }
}