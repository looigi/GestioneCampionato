package looigi.gestionecampionato.dati;

import android.widget.Spinner;

import java.util.List;

public class VariabiliStaticheHome {
    //-------- Singleton ----------//
    private static VariabiliStaticheHome instance = null;

    private VariabiliStaticheHome() {
    }

    public static VariabiliStaticheHome getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheHome();
        }

        return instance;
    }

    private List<String> Categorie;
    private List<Integer> idCategorie;
    private Spinner spnCategorie;
    public int idCategoriaScelta;

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
}
