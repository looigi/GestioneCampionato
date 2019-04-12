package looigi.gestionecampionato.dati;

public class StrutturaSquadreCampionato {
    private Integer idSquadre;
    private String Squadre;
    private Integer idCampo;
    private String Campo;
    private String IndirizzoCampo;
    private String Lat;
    private String Lon;

    public Integer getIdSquadre() {
        return idSquadre;
    }

    public void setIdSquadre(Integer idSquadre) {
        this.idSquadre = idSquadre;
    }

    public String getSquadre() {
        return Squadre;
    }

    public void setSquadre(String squadre) {
        Squadre = squadre;
    }

    public Integer getIdCampo() {
        return idCampo;
    }

    public void setIdCampo(Integer idCampo) {
        this.idCampo = idCampo;
    }

    public String getCampo() {
        return Campo;
    }

    public void setCampo(String campo) {
        Campo = campo;
    }

    public String getIndirizzoCampo() {
        return IndirizzoCampo;
    }

    public void setIndirizzoCampo(String indirizzoCampo) {
        IndirizzoCampo = indirizzoCampo;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLon() {
        return Lon;
    }

    public void setLon(String lon) {
        Lon = lon;
    }
}
