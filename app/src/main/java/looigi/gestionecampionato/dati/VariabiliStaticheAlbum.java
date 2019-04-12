package looigi.gestionecampionato.dati;

import android.content.Context;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

import looigi.gestionecampionato.adapter.AdapterThumbs;

public class VariabiliStaticheAlbum {
    //-------- Singleton ----------//
    private static VariabiliStaticheAlbum instance = null;

    private VariabiliStaticheAlbum() {
    }

    public static VariabiliStaticheAlbum getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheAlbum();
        }

        return instance;
    }

    private Context context;
    private List<String> Categorie;
    private List<Integer> idCategorie;
    private Spinner spnCategorie;
    public int idCategoriaScelta;
    private List<String> Immagini;
    private TextView txtRiga0;
    private TextView txtRiga1;
    private TextView txtRiga2;
    private TextView txtRiga3;
    private TextView txtRiga4;
    private ImageView imgAlbum;
    public int QualeImmagine;
    public int QuanteImmagini;
    private ImageView imgIndietro;
    private ImageView imgAvanti;
    // private RelativeLayout layInfo;
    private ListView lstThumbs;
    private AdapterThumbs adapterLvThumbs;
    private VideoView vView;

    public VideoView getvView() {
        return vView;
    }

    public void setvView(VideoView vView) {
        this.vView = vView;
    }

    public AdapterThumbs getAdapterLvThumbs() {
        return adapterLvThumbs;
    }

    public void setAdapterLvThumbs(AdapterThumbs adapterLvThumbs) {
        this.adapterLvThumbs = adapterLvThumbs;
    }

    public ListView getLstThumbs() {
        return lstThumbs;
    }

    public void setLstThumbs(ListView lstThumbs) {
        this.lstThumbs = lstThumbs;
    }

    public ImageView getImgIndietro() {
        return imgIndietro;
    }

    public void setImgIndietro(ImageView imgIndietro) {
        this.imgIndietro = imgIndietro;
    }

    public ImageView getImgAvanti() {
        return imgAvanti;
    }

    public void setImgAvanti(ImageView imgAvanti) {
        this.imgAvanti = imgAvanti;
    }

    public ImageView getImgAlbum() {
        return imgAlbum;
    }

    public void setImgAlbum(ImageView imgAlbum) {
        this.imgAlbum = imgAlbum;
    }

    public TextView getTxtRiga1() {
        return txtRiga1;
    }

    public TextView getTxtRiga0() {
        return txtRiga0;
    }

    public void setTxtRiga0(TextView txtRiga0) {
        this.txtRiga0 = txtRiga0;
    }

    public void setTxtRiga1(TextView txtRiga1) {
        this.txtRiga1 = txtRiga1;
    }

    public TextView getTxtRiga2() {
        return txtRiga2;
    }

    public void setTxtRiga2(TextView txtRiga2) {
        this.txtRiga2 = txtRiga2;
    }

    public TextView getTxtRiga3() {
        return txtRiga3;
    }

    public void setTxtRiga3(TextView txtRiga3) {
        this.txtRiga3 = txtRiga3;
    }

    public TextView getTxtRiga4() {
        return txtRiga4;
    }

    public void setTxtRiga4(TextView txtRiga4) {
        this.txtRiga4 = txtRiga4;
    }

    public List<String> getImmagini() {
        return Immagini;
    }

    public void setImmagini(List<String> immagini) {
        Immagini = immagini;
    }

    public Spinner getSpnCategorie() {
        return spnCategorie;
    }

    public void setSpnCategorie(Spinner spnCategorie) {
        this.spnCategorie = spnCategorie;
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

    public List<Integer> getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(List<Integer> idCategorie) {
        this.idCategorie = idCategorie;
    }
}
