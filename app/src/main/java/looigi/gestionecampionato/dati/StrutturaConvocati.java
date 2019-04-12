package looigi.gestionecampionato.dati;

public class StrutturaConvocati {
    private Integer idGiocatoreConv;
    private String CognomeConv;
    private String NomeConv;
    private Integer idRuolo;
    private String Ruolo;

    public Integer getIdGiocatoreConv() {
        return idGiocatoreConv;
    }

    public void setIdGiocatoreConv(Integer idGiocatoreConv) {
        this.idGiocatoreConv = idGiocatoreConv;
    }

    public String getCognomeConv() {
        return CognomeConv;
    }

    public void setCognomeConv(String cognomeConv) {
        CognomeConv = cognomeConv;
    }

    public String getNomeConv() {
        return NomeConv;
    }

    public void setNomeConv(String nomeConv) {
        NomeConv = nomeConv;
    }

    public Integer getIdRuolo() {
        return idRuolo;
    }

    public void setIdRuolo(Integer idRuolo) {
        this.idRuolo = idRuolo;
    }

    public String getRuolo() {
        return Ruolo;
    }

    public void setRuolo(String ruolo) {
        Ruolo = ruolo;
    }
}
