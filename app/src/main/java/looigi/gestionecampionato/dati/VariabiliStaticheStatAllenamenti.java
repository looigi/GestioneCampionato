package looigi.gestionecampionato.dati;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.adapter.AdapterAllenatori;
import looigi.gestionecampionato.adapter.AdapterInfo;
import looigi.gestionecampionato.adapter.AdapterStatAllenamenti;

public class VariabiliStaticheStatAllenamenti {
    //-------- Singleton ----------//
    private static VariabiliStaticheStatAllenamenti instance = null;

    private VariabiliStaticheStatAllenamenti() {
    }

    public static VariabiliStaticheStatAllenamenti getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheStatAllenamenti();
        }

        return instance;
    }

    private List<String> StatisticheAllenamenti;
    private List<String> Info;
    private List<String> Categorie;
    private List<Integer> idCategorie;
    public int idCategoriaScelta;

    private Spinner spnCategorie;
    private Spinner spnMesi;
    private Context context;
    private ListView lstStatAllenamenti;
    private AdapterStatAllenamenti adapterStatAllenamenti;
    private RelativeLayout layInfo;
    private ListView lstInfo;
    private AdapterInfo adapterInfo;

    public List<String> getInfo() {
        return Info;
    }

    public void setInfo(List<String> info) {
        Info = info;
    }

    public ListView getLstInfo() {
        return lstInfo;
    }

    public void setLstInfo(ListView lstInfo) {
        this.lstInfo = lstInfo;
    }

    public AdapterInfo getAdapterInfo() {
        return adapterInfo;
    }

    public void setAdapterInfo(AdapterInfo adapterInfo) {
        this.adapterInfo = adapterInfo;
    }

    public RelativeLayout getLayInfo() {
        return layInfo;
    }

    public void setLayInfo(RelativeLayout layInfo) {
        this.layInfo = layInfo;
    }

    public Spinner getSpnMesi() {
        return spnMesi;
    }

    public void setSpnMesi(Spinner spnMesi) {
        this.spnMesi = spnMesi;
    }

    public List<String> getStatisticheAllenamenti() {
        return StatisticheAllenamenti;
    }

    public void setStatisticheAllenamenti(List<String> statisticheAllenamenti) {
        StatisticheAllenamenti = statisticheAllenamenti;
    }

    public ListView getLstStatAllenamenti() {
        return lstStatAllenamenti;
    }

    public void setLstStatAllenamenti(ListView lstStatAllenamenti) {
        this.lstStatAllenamenti = lstStatAllenamenti;
    }

    public AdapterStatAllenamenti getAdapterStatAllenamenti() {
        return adapterStatAllenamenti;
    }

    public void setAdapterStatAllenamenti(AdapterStatAllenamenti adapterStatAllenamenti) {
        this.adapterStatAllenamenti = adapterStatAllenamenti;
    }

    public List<Integer> getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(List<Integer> idCategorie) {
        this.idCategorie = idCategorie;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<String> getCategorie() {
        return Categorie;
    }

    public void setCategorie(List<String> categorie) {
        Categorie = categorie;
    }

    public Spinner getSpnCategorie() {
        return spnCategorie;
    }

    public void setSpnCategorie(Spinner spnCategorie) {
        this.spnCategorie = spnCategorie;
    }
}
