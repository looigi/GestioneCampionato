package looigi.gestionecampionato.dati;

public class StrutturaDatiUtente {
    private String idUtente;
    private String Utente;
    private String Cognome;
    private String Nome;
    private String Password;
    private String EMail;
    private String idCategoria1;
    private String descCategoria;
    // private String idCategoria2;
    // private String descCategoria2;
    private String idTipologia;
    // private String descCategoria3;

    // public String getDescCategoria3() {
    //     return descCategoria3;
    // }
//
    // public void setDescCategoria3(String descCategoria3) {
    //     this.descCategoria3 = descCategoria3;
    // }
//
    // public String getIdCategoria2() {
    //     return idCategoria2;
    // }
//
    // public void setIdCategoria2(String idCategoria2) {
    //     this.idCategoria2 = idCategoria2;
    // }
//
    // public String getDescCategoria2() {
    //     return descCategoria2;
    // }
//
    // public void setDescCategoria2(String descCategoria2) {
    //     this.descCategoria2 = descCategoria2;
    // }

    public String getDescCategoria1() {
        return descCategoria;
    }

    public void setDescCategoria(String descCategoria) {
        this.descCategoria = descCategoria;
    }

    public String getIdTipologia() {
        return idTipologia;
    }

    public void setIdTipologia(String idTipologia) {
        this.idTipologia = idTipologia;
    }

    public String getCognome() {
        return Cognome;
    }

    public void setCognome(String cognome) {
        Cognome = cognome;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(String idUtente) {
        this.idUtente = idUtente;
    }

    public String getUtente() {
        return Utente;
    }

    public void setUtente(String utente) {
        Utente = utente;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getIdCategoria1() {
        return idCategoria1;
    }

    public void setIdCategoria1(String idCategoria1) {
        this.idCategoria1 = idCategoria1;
    }
}
