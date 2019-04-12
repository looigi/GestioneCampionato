package looigi.gestionecampionato.dati;

public class StrutturaPartita {
    private Integer idPartitaGen;
    private Integer idSqCasa;
    private Integer idSqFuori;
    private String Datella;
    private String Casa;
    private String Fuori;
    private String RisGiochetti;
    private Integer GoalAvv;
    private String Risultato1;
    private String Risultato2;
    private String Notelle;
    private String InCasa;
    private String OraConv;
    private String Giocata;
    private int idPartitaGiorno;
    // private List<StrutturaConvocati> Convocati = new ArrayList<>();
    // private List<StrutturaMarcatori> Marcatori = new ArrayList<>();

    public int getIdPartitaGiorno() {
        return idPartitaGiorno;
    }

    public void setIdPartitaGiorno(int idPartitaGiorno) {
        this.idPartitaGiorno = idPartitaGiorno;
    }

    public String getGiocata() {
        return Giocata;
    }

    public void setGiocata(String giocata) {
        Giocata = giocata;
    }

    // public List<StrutturaConvocati> getConvocati() {
    //     return Convocati;
    // }
//
    // public void setConvocati(List<StrutturaConvocati> convocati) {
    //     Convocati = convocati;
    // }
//
    // public List<StrutturaMarcatori> getMarcatori() {
    //     return Marcatori;
    // }
//
    // public void setMarcatori(List<StrutturaMarcatori> marcatori) {
    //    Marcatori = marcatori;
    // }

    public Integer getIdPartitaGen() {
        return idPartitaGen;
    }

    public void setIdPartitaGen(Integer idPartitaGen) {
        this.idPartitaGen = idPartitaGen;
    }

    public Integer getIdSqCasa() {
        return idSqCasa;
    }

    public void setIdSqCasa(Integer idSqCasa) {
        this.idSqCasa = idSqCasa;
    }

    public Integer getIdSqFuori() {
        return idSqFuori;
    }

    public void setIdSqFuori(Integer idSqFuori) {
        this.idSqFuori = idSqFuori;
    }

    public String getDatella() {
        return Datella;
    }

    public void setDatella(String datella) {
        Datella = datella;
    }

    public String getCasa() {
        return Casa;
    }

    public void setCasa(String casa) {
        Casa = casa;
    }

    public String getFuori() {
        return Fuori;
    }

    public void setFuori(String fuori) {
        Fuori = fuori;
    }

    public String getRisGiochetti() {
        return RisGiochetti;
    }

    public void setRisGiochetti(String risGiochetti) {
        RisGiochetti = risGiochetti;
    }

    public Integer getGoalAvv() {
        return GoalAvv;
    }

    public void setGoalAvv(Integer goalAvv) {
        GoalAvv = goalAvv;
    }

    public String getRisultato1() {
        return Risultato1;
    }

    public void setRisultato1(String risultato1) {
        Risultato1 = risultato1;
    }

    public String getRisultato2() {
        return Risultato2;
    }

    public void setRisultato2(String risultato2) {
        Risultato2 = risultato2;
    }

    public String getNotelle() {
        return Notelle;
    }

    public void setNotelle(String notelle) {
        Notelle = notelle;
    }

    public String getInCasa() {
        return InCasa;
    }

    public void setInCasa(String inCasa) {
        InCasa = inCasa;
    }

    public String getOraConv() {
        return OraConv;
    }

    public void setOraConv(String oraConv) {
        OraConv = oraConv;
    }
}
