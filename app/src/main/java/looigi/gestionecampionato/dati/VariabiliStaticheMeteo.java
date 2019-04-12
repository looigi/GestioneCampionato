package looigi.gestionecampionato.dati;

public class VariabiliStaticheMeteo {
    //-------- Singleton ----------//
    private static VariabiliStaticheMeteo instance = null;

    private VariabiliStaticheMeteo() {
    }

    public static VariabiliStaticheMeteo getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheMeteo();
        }

        return instance;
    }

    private String Tempo;
    private String Umidita;
    private String Gradi;
    private String Pressione;
    private String lat;
    private String lon;

    public void PulisceTutto() {
        Tempo ="";
        Umidita="";
        Gradi="";
        Pressione="";
        lat="";
        lon="";
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getTempo() {
        return Tempo;
    }

    public void setTempo(String tempo) {
        Tempo = tempo;
    }

    public String getUmidita() {
        return Umidita;
    }

    public void setUmidita(String umidita) {
        Umidita = umidita;
    }

    public String getGradi() {
        return Gradi;
    }

    public void setGradi(String gradi) {
        Gradi = gradi;
    }

    public String getPressione() {
        return Pressione;
    }

    public void setPressione(String visibilita) {
        Pressione = visibilita;
    }
}
