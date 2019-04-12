package looigi.gestionecampionato.dati;

public class StrutturaMarcatori {
    private Integer idGiocatoreMarc;
    private String CognomeMarc;
    private String NomeMarc;
    private Integer Minuto;

    public Integer getIdGiocatoreMarc() {
        return idGiocatoreMarc;
    }

    public void setIdGiocatoreMarc(Integer idGiocatoreMarc) {
        this.idGiocatoreMarc = idGiocatoreMarc;
    }

    public String getCognomeMarc() {
        return CognomeMarc;
    }

    public void setCognomeMarc(String cognomeMarc) {
        CognomeMarc = cognomeMarc;
    }

    public String getNomeMarc() {
        return NomeMarc;
    }

    public void setNomeMarc(String nomeMarc) {
        NomeMarc = nomeMarc;
    }

    public Integer getMinuto() {
        return Minuto;
    }

    public void setMinuto(Integer minuto) {
        Minuto = minuto;
    }
}
