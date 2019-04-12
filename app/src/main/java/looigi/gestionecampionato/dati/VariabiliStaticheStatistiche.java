package looigi.gestionecampionato.dati;

import android.content.Context;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class VariabiliStaticheStatistiche {
    //-------- Singleton ----------//
    private static VariabiliStaticheStatistiche instance = null;

    private VariabiliStaticheStatistiche() {
    }

    public static VariabiliStaticheStatistiche getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheStatistiche();
        }

        return instance;
    }

    private Context context;
    private List<String> Categorie;
    private List<Integer> idCategorie;
    private Spinner spnCategorie;
    public int idCategoriaScelta;
    private List<LatLng> Coords;
    private List<String> DescrMarkers;
    private LatLng CoordsCasa;
    private TextView tStat;
    private List<String> Avversari;
    public int NumPartite;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<String> getAvversari() {
        return Avversari;
    }

    public void setAvversari(List<String> avversari) {
        Avversari = avversari;
    }

    public TextView gettStat() {
        return tStat;
    }

    public void settStat(TextView tStat) {
        this.tStat = tStat;
    }

    public LatLng getCoordsCasa() {
        return CoordsCasa;
    }

    public void setCoordsCasa(LatLng coordsCasa) {
        CoordsCasa = coordsCasa;
    }

    public List<LatLng> getCoords() {
        return Coords;
    }

    public void setCoords(List<LatLng> coords) {
        Coords = coords;
    }

    public List<String> getDescrMarkers() {
        return DescrMarkers;
    }

    public void setDescrMarkers(List<String> descrMarkers) {
        DescrMarkers = descrMarkers;
    }

    public List<String> getCategorie() {
        return Categorie;
    }

    public void setCategorie(List<String> categorie) {
        Categorie = categorie;
    }

    public List<Integer> getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(List<Integer> idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Spinner getSpnCategorie() {
        return spnCategorie;
    }

    public void setSpnCategorie(Spinner spnCategorie) {
        this.spnCategorie = spnCategorie;
    }


    public void PulisceTuttiGliArray() {
        Categorie=new ArrayList<String>();
        idCategorie=new ArrayList<Integer>();
    }
}
