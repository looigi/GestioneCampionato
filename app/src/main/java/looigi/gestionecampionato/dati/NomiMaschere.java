package looigi.gestionecampionato.dati;

public class NomiMaschere {
    //-------- Singleton ----------//
    private static NomiMaschere instance = null;

    private NomiMaschere() {
    }

    public static NomiMaschere getInstance() {
        if (instance == null) {
            instance = new NomiMaschere();
        }

        return instance;
    }

    private String HomePerTitolo="Home";
    private String AllenatoriPerTitolo="Allenatori";
    private String DirigentiPerTitolo="Dirigenti";
    private String EventiPerTitolo="Eventi";
    private String ArbitriPerTitolo="Arbitri";
    private String AvversariPerTitolo="Avversari";
    private String RosePerTitolo="Giocatori";
    private String NuovaPartitaPerTitolo="Nuova Partita";
    private String SettingsPerTitolo="Settings";
    private String StatistichePerTitolo="Statistiche";
    private String AlbumPerTitolo="Album";
    private String MultimediaPerTitolo="Multimedia";
    private String CategoriePerTitolo="Categorie";
    private String AllenamentiPerTitolo="Allenamenti";
    private String VisualizzaImmaginiPerTitolo="Visualizza";
    private String Utenti="Utenti";
    private String Splash="Splash";
    private String About="About";
    private String ModificaUtenti ="Modifica Utenti";
    private String NuovoAnno ="Nuovo Anno";
    private String Campionato ="Campionato";

    public String getAbout() {
        return About.toUpperCase().replace(" ","");
    }

    public String getAboutPerTitolo() {
        return About;
    }

    public String getSplash() {
        return Splash.toUpperCase().replace(" ","");
    }

    public String getSplashPerTitolo() {
        return Splash;
    }

    public String getUtenti() {
        return Utenti.toUpperCase().replace(" ","");
    }

    public String getUtentiPerTitolo() {
        return Utenti;
    }

    public String getVisualizzaImmagini() {
        return VisualizzaImmaginiPerTitolo.toUpperCase().replace(" ","");
    }

    public String getVisualizzaImmaginiPerTitolo() {
        return VisualizzaImmaginiPerTitolo;
    }

    public String getEventiPerTitolo() {
        return EventiPerTitolo;
    }

    public String getCategoriePerTitolo() {
        return CategoriePerTitolo;
    }

    public String getCategorie() {
        return CategoriePerTitolo.toUpperCase().replace(" ","");
    }

    public String getMultimediaPerTitolo() {
        return MultimediaPerTitolo;
    }

    public String getMultimedia() {
        return MultimediaPerTitolo.toUpperCase().replace(" ","");
    }

    public String getAlbumPerTitolo() {
        return AlbumPerTitolo;
    }

    public String getAlbum() {
        return AlbumPerTitolo.toUpperCase().replace(" ","");
    }

    public String getStatistichePerTitolo() {
        return StatistichePerTitolo;
    }

    public String getStatistiche() {
        return StatistichePerTitolo.toUpperCase().replace(" ","");
    }

    public String getSettingsPerTitolo() {
        return SettingsPerTitolo;
    }

    public String getHomePerTitolo() {
        return HomePerTitolo;
    }

    public String getAvversariPerTitolo() {
        return AvversariPerTitolo;
    }

    public String getRosePerTitolo() {
        return RosePerTitolo;
    }

    public String getNuovaPartitaPerTitolo() {
        return NuovaPartitaPerTitolo;
    }

    public String getSettings() {
        return SettingsPerTitolo.toUpperCase().replace(" ","");
    }

    public String getNuovaPartita() {
        return NuovaPartitaPerTitolo.toUpperCase().replace(" ","");
    }

    public String getHome() {
        return HomePerTitolo.toUpperCase().replace(" ","");
    }

    public String getAllenatori() {
        return AllenatoriPerTitolo.toUpperCase().replace(" ","");
    }

    public String getAllenatoriPerTitolo() {
        return AllenatoriPerTitolo;
    }

    public String getDirigenti() {
        return DirigentiPerTitolo.toUpperCase().replace(" ","");
    }

    public String getEventi() {
        return EventiPerTitolo.toUpperCase().replace(" ","");
    }

    public String getDirigentiPerTitolo() {
        return DirigentiPerTitolo;
    }

    public String getAvversari() {
        return AvversariPerTitolo.toUpperCase().replace(" ","");
    }

    public String getRose() {
        return RosePerTitolo.toUpperCase().replace(" ","");
    }

    public String getModificaUtenti() {
        return ModificaUtenti.toUpperCase().replace(" ","");
    }

    public String getPermessiUtentiPerTitolo() {
        return ModificaUtenti;
    }

    public String getNuovoAnno() {
        return NuovoAnno.toUpperCase().replace(" ","");
    }

    public String getNuovoAnnoPerTitolo() {
        return NuovoAnno;
    }

    public String getCampionato() {
        return Campionato.toUpperCase().replace(" ","");
    }

    public String getCampionatoPerTitolo() {
        return Campionato;
    }

    public String getArbitri() {
        return ArbitriPerTitolo.toUpperCase().replace(" ","");
    }

    public String getArbitriPerTitolo() {
        return ArbitriPerTitolo;
    }

    public String getAllenamenti() {
        return AllenamentiPerTitolo.toUpperCase().replace(" ","");
    }

    public String getAllenamentiPerTitolo() {
        return AllenamentiPerTitolo;
    }
}
