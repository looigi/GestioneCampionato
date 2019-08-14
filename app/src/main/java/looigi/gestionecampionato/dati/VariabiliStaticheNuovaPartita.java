package looigi.gestionecampionato.dati;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.adapter.AdapterDirigenti;
import looigi.gestionecampionato.adapter.AdapterDirigentiConvocati;
import looigi.gestionecampionato.adapter.AdapterEventi;
import looigi.gestionecampionato.adapter.AdapterEventiPartita;
import looigi.gestionecampionato.adapter.AdapterGiocatore;
import looigi.gestionecampionato.adapter.AdapterGiocatoreEventi;
import looigi.gestionecampionato.adapter.AdapterGiocatoreRigori;
import looigi.gestionecampionato.adapter.AdapterMinutiGoalAvversari;

public class VariabiliStaticheNuovaPartita {
    //-------- Singleton ----------//
    private static VariabiliStaticheNuovaPartita instance = null;

    private VariabiliStaticheNuovaPartita() {
    }

    public static VariabiliStaticheNuovaPartita getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheNuovaPartita();
        }

        return instance;
    }
    // Maschera Nuova Partita
    private Context context;
    private List<String> Allenatore;
    private List<Integer> idAllenatore;
    private List<String> Dirigente;
    private List<Integer> idDirigente;
    private List<String> Arbitro;
    private List<Integer> idArbitro;
    private List<String> DirigenteSelezionato;
    private List<Integer> idDirigenteSelezionato;
    private List<String> DescrizioneCategorie;
    private List<Integer> idCategorie;
    private List<String> DescrizioneTipologie;
    private List<Integer> idTipologie;
    private List<String> GiocatoreCognomeNome;
    private List<Integer> GiocatoreID;
    private List<String> GiocatoreRuolo;
    private List<Integer> GiocatoreIDRuolo;
    private List<Integer> GiocatoreNumeroMaglia;
    private List<String> Avversari;
    private List<Integer> AvversariID;
    private List<Integer> AvversariCampoID;
    private List<String> AvversariCampo;
    private List<String> AvversariCampoIndirizzo;
    private List<String> GiocatoreConvocato;
    private List<String> GiocatoreDaConvocare;
    private List<String> GiocatoreConvocatoRigori;
    private List<String> GiocatoreDaConvocareRigori;
    private ArrayList<String> listMarcPrimoTempo;
    private ArrayList<String> listMarcSecondoTempo;
    private ArrayList<String> listMarcTerzoTempo;
    private List<String> GiocatoriPerMarcature;

    private TextView txtTimer1Tempo;
    private TextView txtTimer2Tempo;
    private TextView txtTimer3Tempo;
    private ImageView cmdStartSopTimer1Tempo;
    private ImageView cmdStartSopTimer2Tempo;
    private ImageView cmdStartSopTimer3Tempo;
    private ImageView cmdReset1Tempo;
    private ImageView cmdReset2Tempo;
    private ImageView cmdReset3Tempo;

    private TextView txtTempo;
    private TextView txtGradi;
    private TextView txtUmidita;
    private TextView txtPressione;
    private TextView txtLat;
    private TextView txtLon;
    private CheckBox chkTempi;
    public Intent intentGPS;

    private int GiocatoreSelezionatoRigore;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public int getGiocatoreSelezionatoRigore() {
        return GiocatoreSelezionatoRigore;
    }

    public void setGiocatoreSelezionatoRigore(int giocatoreSelezionatoRigore) {
        GiocatoreSelezionatoRigore = giocatoreSelezionatoRigore;
    }

    public List<String> getGiocatoreConvocatoRigori() {
        return GiocatoreConvocatoRigori;
    }

    public void setGiocatoreConvocatoRigori(List<String> giocatoreConvocatoRigori) {
        GiocatoreConvocatoRigori = giocatoreConvocatoRigori;
    }

    public List<String> getGiocatoreDaConvocareRigori() {
        return GiocatoreDaConvocareRigori;
    }

    public void setGiocatoreDaConvocareRigori(List<String> giocatoreDaConvocareRigori) {
        GiocatoreDaConvocareRigori = giocatoreDaConvocareRigori;
    }

    public CheckBox getChkTempi() {
        return chkTempi;
    }

    public void setChkTempi(CheckBox chkTempi) {
        this.chkTempi = chkTempi;
    }

    public List<String> getArbitro() {
        return Arbitro;
    }

    public void setArbitro(List<String> arbitro) {
        Arbitro = arbitro;
    }

    public List<Integer> getIdArbitro() {
        return idArbitro;
    }

    public void setIdArbitro(List<Integer> idArbitro) {
        this.idArbitro = idArbitro;
    }

    public List<String> getDirigenteSelezionato() {
        return DirigenteSelezionato;
    }

    public void setDirigenteSelezionato(List<String> dirigenteSelezionato) {
        DirigenteSelezionato = dirigenteSelezionato;
    }

    public List<Integer> getIdDirigenteSelezionato() {
        return idDirigenteSelezionato;
    }

    public void setIdDirigenteSelezionato(List<Integer> idDirigenteSelezionato) {
        this.idDirigenteSelezionato = idDirigenteSelezionato;
    }

    public List<String> getDirigente() {
        return Dirigente;
    }

    public void setDirigente(List<String> dirigente) {
        Dirigente = dirigente;
    }

    public List<Integer> getIdDirigente() {
        return idDirigente;
    }

    public void setIdDirigente(List<Integer> idDirigente) {
        this.idDirigente = idDirigente;
    }

    public List<Integer> getGiocatoreNumeroMaglia() {
        return GiocatoreNumeroMaglia;
    }

    public void setGiocatoreNumeroMaglia(List<Integer> giocatoreNumeroMaglia) {
        GiocatoreNumeroMaglia = giocatoreNumeroMaglia;
    }

    public TextView getTxtLat() {
        return txtLat;
    }

    public void setTxtLat(TextView txtLat) {
        this.txtLat = txtLat;
    }

    public TextView getTxtLon() {
        return txtLon;
    }

    public void setTxtLon(TextView txtLon) {
        this.txtLon = txtLon;
    }

    public TextView getTxtTempo() {
        return txtTempo;
    }

    public void setTxtTempo(TextView txtTempo) {
        this.txtTempo = txtTempo;
    }

    public TextView getTxtGradi() {
        return txtGradi;
    }

    public void setTxtGradi(TextView txtGradi) {
        this.txtGradi = txtGradi;
    }

    public TextView getTxtUmidita() {
        return txtUmidita;
    }

    public void setTxtUmidita(TextView txtUmidita) {
        this.txtUmidita = txtUmidita;
    }

    public TextView getTxtPressione() {
        return txtPressione;
    }

    public void setTxtPressione(TextView txtPressione) {
        this.txtPressione = txtPressione;
    }

    public ImageView getCmdReset1Tempo() {
        return cmdReset1Tempo;
    }

    public void setCmdReset1Tempo(ImageView cmdReset1Tempo) {
        this.cmdReset1Tempo = cmdReset1Tempo;
    }

    public ImageView getCmdReset2Tempo() {
        return cmdReset2Tempo;
    }

    public void setCmdReset2Tempo(ImageView cmdReset2Tempo) {
        this.cmdReset2Tempo = cmdReset2Tempo;
    }

    public ImageView getCmdReset3Tempo() {
        return cmdReset3Tempo;
    }

    public void setCmdReset3Tempo(ImageView cmdReset3Tempo) {
        this.cmdReset3Tempo = cmdReset3Tempo;
    }

    public TextView getTxtTimer1Tempo() {
        return txtTimer1Tempo;
    }

    public void setTxtTimer1Tempo(TextView txtTimer1Tempo) {
        this.txtTimer1Tempo = txtTimer1Tempo;
    }

    public TextView getTxtTimer2Tempo() {
        return txtTimer2Tempo;
    }

    public void setTxtTimer2Tempo(TextView txtTimer2Tempo) {
        this.txtTimer2Tempo = txtTimer2Tempo;
    }

    public TextView getTxtTimer3Tempo() {
        return txtTimer3Tempo;
    }

    public void setTxtTimer3Tempo(TextView txtTimer3Tempo) {
        this.txtTimer3Tempo = txtTimer3Tempo;
    }

    public ImageView getCmdStartSopTimer1Tempo() {
        return cmdStartSopTimer1Tempo;
    }

    public void setCmdStartSopTimer1Tempo(ImageView cmdStartSopTimer1Tempo) {
        this.cmdStartSopTimer1Tempo = cmdStartSopTimer1Tempo;
    }

    public ImageView getCmdStartSopTimer2Tempo() {
        return cmdStartSopTimer2Tempo;
    }

    public void setCmdStartSopTimer2Tempo(ImageView cmdStartSopTimer2Tempo) {
        this.cmdStartSopTimer2Tempo = cmdStartSopTimer2Tempo;
    }

    public ImageView getCmdStartSopTimer3Tempo() {
        return cmdStartSopTimer3Tempo;
    }

    public void setCmdStartSopTimer3Tempo(ImageView cmdStartSopTimer3Tempo) {
        this.cmdStartSopTimer3Tempo = cmdStartSopTimer3Tempo;
    }

    public void PulisceTuttiGliArray() {
        Allenatore = new ArrayList<>();
        idAllenatore = new ArrayList<>();
        DescrizioneCategorie = new ArrayList<>();
        idCategorie = new ArrayList<>();
        DescrizioneTipologie = new ArrayList<>();
        idTipologie = new ArrayList<>();
        GiocatoreCognomeNome = new ArrayList<>();
        GiocatoreID = new ArrayList<>();
        GiocatoreRuolo = new ArrayList<>();
        GiocatoreIDRuolo = new ArrayList<>();
        Avversari = new ArrayList<>();
        AvversariID = new ArrayList<>();
        AvversariCampoID = new ArrayList<>();
        AvversariCampo = new ArrayList<>();
        AvversariCampoIndirizzo = new ArrayList<>();
        GiocatoreConvocato = new ArrayList<>();
        GiocatoreDaConvocare = new ArrayList<>();
        listMarcPrimoTempo = new ArrayList<>();
        listMarcSecondoTempo = new ArrayList<>();
        listMarcTerzoTempo = new ArrayList<>();
        GiocatoriPerMarcature = new ArrayList<>();
    }

    private RelativeLayout rlMaschera;
    private LinearLayout llContenuto;

    private Spinner spnCategorie;
    private Spinner spnTipologie;
    private Spinner spnAvversario;
    private Spinner spnAllenatore;
    private Spinner spnArbitro;
    private ListView spnDirigenti;
    private ListView spnDirigentiConvocati;
    private ListView spnDaConvocare;
    private ListView spnConvocati;
    private ListView spnDaConvocareRigori;
    private ListView spnConvocatiRigori;
    private ListView spnMarcatoriPrimoTempo;
    private ListView spnMarcatoriSecondoTempo;
    private ListView spnMarcatoriTerzoTempo;
    public int idCategoriaScelta;
    public int idTipologiaScelta;
    public int idAvversarioScelto;
    public int idAllenatoreScelto;
    public int idArbitroScelto;
    public int idCampoScelto;
    public int idPartita;
    private TextView txtConvocati;
    private TextView txtCategoria;
    private TextView txtTipologia;
    private TextView txtAvversario;
    private TextView txtCampo;
    private TextView txtCampoIndirizzo;
    private TextView txtArbitro;
    private AdapterGiocatore adapterGiocatoriDaConvocare;
    private AdapterGiocatore adapterGiocatoriDaConvocareRigori;
    private AdapterGiocatore adapterGiocatoriConvocati;
    private AdapterGiocatoreRigori adapterGiocatoriConvocatiRigori;
    private AdapterGiocatore adapterMarcatoriPrimoTempo;
    private AdapterGiocatore adapterMarcatoriSecondoTempo;
    private AdapterGiocatore adapterMarcatoriTerzoTempo;
    private AdapterDirigentiConvocati adapterDirigenti;
    private AdapterDirigenti adapterDirigentiDaConvocare;
    private ListView lvvMarcatoriPrimoTempo;
    private ListView lvvMarcatoriSecondoTempo;
    private ListView lvvMarcatoriTerzoTempo;
    private AdapterGiocatore adapterLvPrimoTempo;
    private AdapterGiocatore adapterLvSecondoTempo;
    private AdapterGiocatore adapterLvTerzoTempo;
    private TextView txtGoal;
    private TextView txtRigori;
    private TextView txtTempi;
    private TextView txtRisAvv1Tempo;
    private TextView txtRisAvv2Tempo;
    private TextView txtRisAvv3Tempo;
    private EditText edtGiochetti;
    public Boolean PartitaSalvata;
    private View ViewActivity;
    private TextView txtData;
    private TextView txtOra;
    private EditText edtNote;
    private ImageView imgCasa;
    private ImageView imgFuori;
    private TextView txtCasa;
    private TextView txtFuori;
    public String AllenatorePerRicarica;
    public String DirigentePerRicarica;
    public String ConvocatiPerRicarica;
    public String MarcatoriPerRicarica;
    private CheckBox chkInCasa;
    private CheckBox chkEsterno;
    private ImageView cmdSalva;
    private ImageView cmdUscita;
    private LinearLayout layCampoIndirizzo;
    private LinearLayout layCampoEsterno;
    private EditText edtCampoEsterno;
    private ListView lvTempiGAvvPrimoTempo;
    private ListView lvTempiGAvvSecondoTempo;
    private ListView lvTempiGAvvTerzoTempo;
    private List<Integer> TempiGAvvPrimoTempo;
    private List<Integer> TempiGAvvSecondoTempo;
    private List<Integer> TempiGAvvTerzoTempo;
    private AdapterMinutiGoalAvversari adapterTempiGAvvPrimoTempo;
    private AdapterMinutiGoalAvversari adapterTempiGAvvSecondoTempo;
    private AdapterMinutiGoalAvversari adapterTempiGAvvTerzoTempo;

    private ListView lvEventiPrimoTempo;
    private ListView lvEventiSecondoTempo;
    private ListView lvEventiTerzoTempo;
    private AdapterEventiPartita adapterEventiPrimoTempo;
    private AdapterEventiPartita adapterEventiSecondoTempo;
    private AdapterEventiPartita adapterEventiTerzoTempo;
    private List<String> eventiPrimoTempo;
    private List<String> eventiSecondoTempo;
    private List<String> eventiTerzoTempo;
    private int QualeTempoEvento;
    private ListView lvEventiGiocatori;
    private AdapterGiocatoreEventi adapterEventiGiocatori;
    private List<String> eventiGiocatori;
    private ListView lvEventiLista;
    private AdapterEventi adapterListaEventi;
    private List<String> eventiLista;
    private TextView txtEvento;
    private String descEvento="";
    private int idEvento=-1;
    private String MinutoEvento="";
    private String aFavoreDi="";
    private int idAFavore=-1;
    // Maschera Nuova Partita

    public void PulisceEvento() {
        descEvento="";
        idEvento= -1;
        MinutoEvento="";
        aFavoreDi="";
        idAFavore=-1;
        QualeTempoEvento=-1;
    }

    public void StampaEvento() {
        txtEvento.setText(MinutoEvento+"° - " + descEvento + " " + aFavoreDi);
    }

    public RelativeLayout getRlMaschera() {
        return rlMaschera;
    }

    public void setRlMaschera(RelativeLayout rlMaschera) {
        this.rlMaschera = rlMaschera;
    }

    public int getIdAFavore() {
        return idAFavore;
    }

    public void setIdAFavore(int idAFavore) {
        this.idAFavore = idAFavore;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public TextView getTxtEvento() {
        return txtEvento;
    }

    public String getDescEvento() {
        return descEvento;
    }

    public String getMinutoEvento() {
        return MinutoEvento;
    }

    public void setMinutoEvento(String minutoEvento) {
        MinutoEvento = minutoEvento;
    }

    public void setDescEvento(String descEvento) {
        this.descEvento = descEvento;
    }

    public String getaFavoreDi() {
        return aFavoreDi;
    }

    public void setaFavoreDi(String aFavoreDi) {
        this.aFavoreDi = aFavoreDi;
    }

    public void setTxtEvento(TextView txtEvento) {
        this.txtEvento = txtEvento;
    }

    public ListView getLvEventiLista() {
        return lvEventiLista;
    }

    public void setLvEventiLista(ListView lvEventiLista) {
        this.lvEventiLista = lvEventiLista;
    }

    public AdapterEventi getAdapterListaEventi() {
        return adapterListaEventi;
    }

    public void setAdapterListaEventi(AdapterEventi adapterListaEventi) {
        this.adapterListaEventi = adapterListaEventi;
    }

    public List<String> getEventiLista() {
        return eventiLista;
    }

    public void setEventiLista(List<String> eventiLista) {
        this.eventiLista = eventiLista;
    }

    public ListView getLvEventiGiocatori() {
        return lvEventiGiocatori;
    }

    public void setLvEventiGiocatori(ListView lvEventiGiocatori) {
        this.lvEventiGiocatori = lvEventiGiocatori;
    }

    public AdapterGiocatoreEventi getAdapterEventiGiocatori() {
        return adapterEventiGiocatori;
    }

    public void setAdapterEventiGiocatori(AdapterGiocatoreEventi adapterEventiGiocatori) {
        this.adapterEventiGiocatori = adapterEventiGiocatori;
    }

    public List<String> getEventiGiocatori() {
        return eventiGiocatori;
    }

    public void setEventiGiocatori(List<String> eventiGiocatori) {
        this.eventiGiocatori = eventiGiocatori;
    }

    public List<String> getEventiPrimoTempo() {
        return eventiPrimoTempo;
    }

    public void setEventiPrimoTempo(List<String> eventiPrimoTempo) {
        this.eventiPrimoTempo = eventiPrimoTempo;
    }

    public List<String> getEventiSecondoTempo() {
        return eventiSecondoTempo;
    }

    public void setEventiSecondoTempo(List<String> eventiSecondoTempo) {
        this.eventiSecondoTempo = eventiSecondoTempo;
    }

    public List<String> getEventiTerzoTempo() {
        return eventiTerzoTempo;
    }

    public void setEventiTerzoTempo(List<String> eventiTerzoTempo) {
        this.eventiTerzoTempo = eventiTerzoTempo;
    }

    public LinearLayout getLlContenuto() {
        return llContenuto;
    }

    public void setLlContenuto(LinearLayout llContenuto) {
        this.llContenuto = llContenuto;
    }

    public AdapterEventiPartita getAdapterEventiPrimoTempo() {
        return adapterEventiPrimoTempo;
    }

    public void setAdapterEventiPrimoTempo(AdapterEventiPartita adapterEventiPrimoTempo) {
        this.adapterEventiPrimoTempo = adapterEventiPrimoTempo;
    }

    public AdapterEventiPartita getAdapterEventiSecondoTempo() {
        return adapterEventiSecondoTempo;
    }

    public void setAdapterEventiSecondoTempo(AdapterEventiPartita adapterEventiSecondoTempo) {
        this.adapterEventiSecondoTempo = adapterEventiSecondoTempo;
    }

    public AdapterEventiPartita getAdapterEventiTerzoTempo() {
        return adapterEventiTerzoTempo;
    }

    public void setAdapterEventiTerzoTempo(AdapterEventiPartita adapterEventiTerzoTempo) {
        this.adapterEventiTerzoTempo = adapterEventiTerzoTempo;
    }

    public int getQualeTempoEvento() {
        return QualeTempoEvento;
    }

    public void setQualeTempoEvento(int qualeTempoEvento) {
        QualeTempoEvento = qualeTempoEvento;
    }

    public ListView getLvEventiPrimoTempo() {
        return lvEventiPrimoTempo;
    }

    public void setLvEventiPrimoTempo(ListView lvEventiPrimoTempo) {
        this.lvEventiPrimoTempo = lvEventiPrimoTempo;
    }

    public ListView getLvEventiSecondoTempo() {
        return lvEventiSecondoTempo;
    }

    public void setLvEventiSecondoTempo(ListView lvEventiSecondoTempo) {
        this.lvEventiSecondoTempo = lvEventiSecondoTempo;
    }

    public ListView getLvEventiTerzoTempo() {
        return lvEventiTerzoTempo;
    }

    public void setLvEventiTerzoTempo(ListView lvEventiTerzoTempo) {
        this.lvEventiTerzoTempo = lvEventiTerzoTempo;
    }

    public TextView getTxtRigori() {
        return txtRigori;
    }

    public void setTxtRigori(TextView txtRigori) {
        this.txtRigori = txtRigori;
    }

    public ListView getSpnDaConvocareRigori() {
        return spnDaConvocareRigori;
    }

    public void setSpnDaConvocareRigori(ListView spnDaConvocareRigori) {
        this.spnDaConvocareRigori = spnDaConvocareRigori;
    }

    public ListView getSpnConvocatiRigori() {
        return spnConvocatiRigori;
    }

    public void setSpnConvocatiRigori(ListView spnConvocatiRigori) {
        this.spnConvocatiRigori = spnConvocatiRigori;
    }

    public AdapterGiocatore getAdapterGiocatoriDaConvocareRigori() {
        return adapterGiocatoriDaConvocareRigori;
    }

    public void setAdapterGiocatoriDaConvocareRigori(AdapterGiocatore adapterGiocatoriDaConvocareRigori) {
        this.adapterGiocatoriDaConvocareRigori = adapterGiocatoriDaConvocareRigori;
    }

    public AdapterGiocatoreRigori getAdapterGiocatoriConvocatiRigori() {
        return adapterGiocatoriConvocatiRigori;
    }

    public void setAdapterGiocatoriConvocatiRigori(AdapterGiocatoreRigori adapterGiocatoriConvocatiRigori) {
        this.adapterGiocatoriConvocatiRigori = adapterGiocatoriConvocatiRigori;
    }

    public TextView getTxtArbitro() {
        return txtArbitro;
    }

    public void setTxtArbitro(TextView txtArbitro) {
        this.txtArbitro = txtArbitro;
    }

    public Spinner getSpnArbitro() {
        return spnArbitro;
    }

    public void setSpnArbitro(Spinner spnArbitro) {
        this.spnArbitro = spnArbitro;
    }

    public ListView getSpnDirigentiConvocati() {
        return spnDirigentiConvocati;
    }

    public void setSpnDirigentiConvocati(ListView spnDirigentiConvocati) {
        this.spnDirigentiConvocati = spnDirigentiConvocati;
    }

    public AdapterDirigenti getAdapterDirigentiDaConvocare() {
        return adapterDirigentiDaConvocare;
    }

    public void setAdapterDirigentiDaConvocare(AdapterDirigenti adapterDirigentiDaConvocare) {
        this.adapterDirigentiDaConvocare = adapterDirigentiDaConvocare;
    }

    public AdapterDirigentiConvocati getAdapterDirigenti() {
        return adapterDirigenti;
    }

    public void setAdapterDirigenti(AdapterDirigentiConvocati adapterDirigenti) {
        this.adapterDirigenti = adapterDirigenti;
    }

    public ListView getSpnDirigenti() {
        return spnDirigenti;
    }

    public void setSpnDirigenti(ListView spnDirigenti) {
        this.spnDirigenti = spnDirigenti;
    }

    public ImageView getCmdUscita() {
        return cmdUscita;
    }

    public void setCmdUscita(ImageView cmdUscita) {
        this.cmdUscita = cmdUscita;
    }

    public AdapterMinutiGoalAvversari getAdapterTempiGAvvSecondoTempo() {
        return adapterTempiGAvvSecondoTempo;
    }

    public void setAdapterTempiGAvvSecondoTempo(AdapterMinutiGoalAvversari adapterTempiGAvvSecondoTempo) {
        this.adapterTempiGAvvSecondoTempo = adapterTempiGAvvSecondoTempo;
    }

    public AdapterMinutiGoalAvversari getAdapterTempiGAvvTerzoTempo() {
        return adapterTempiGAvvTerzoTempo;
    }

    public void setAdapterTempiGAvvTerzoTempo(AdapterMinutiGoalAvversari adapterTempiGAvvTerzoTempo) {
        this.adapterTempiGAvvTerzoTempo = adapterTempiGAvvTerzoTempo;
    }

    public AdapterMinutiGoalAvversari getAdapterTempiGAvvPrimoTempo() {
        return adapterTempiGAvvPrimoTempo;
    }

    public void setAdapterTempiGAvvPrimoTempo(AdapterMinutiGoalAvversari adapterTempiGAvvPrimoTempo) {
        this.adapterTempiGAvvPrimoTempo = adapterTempiGAvvPrimoTempo;
    }

    public List<Integer> getTempiGAvvPrimoTempo() {
        return TempiGAvvPrimoTempo;
    }

    public void setTempiGAvvPrimoTempo(List<Integer> tempiGAvvPrimoTempo) {
        TempiGAvvPrimoTempo = tempiGAvvPrimoTempo;
    }

    public List<Integer> getTempiGAvvSecondoTempo() {
        return TempiGAvvSecondoTempo;
    }

    public void setTempiGAvvSecondoTempo(List<Integer> tempiGAvvSecondoTempo) {
        TempiGAvvSecondoTempo = tempiGAvvSecondoTempo;
    }

    public List<Integer> getTempiGAvvTerzoTempo() {
        return TempiGAvvTerzoTempo;
    }

    public void setTempiGAvvTerzoTempo(List<Integer> tempiGAvvTerzoTempo) {
        TempiGAvvTerzoTempo = tempiGAvvTerzoTempo;
    }

    public ListView getLvTempiGAvvPrimoTempo() {
        return lvTempiGAvvPrimoTempo;
    }

    public void setLvTempiGAvvPrimoTempo(ListView lvTempiGAvvPrimoTempo) {
        this.lvTempiGAvvPrimoTempo = lvTempiGAvvPrimoTempo;
    }

    public ListView getLvTempiGAvvSecondoTempo() {
        return lvTempiGAvvSecondoTempo;
    }

    public void setLvTempiGAvvSecondoTempo(ListView lvTempiGAvvSecondoTempo) {
        this.lvTempiGAvvSecondoTempo = lvTempiGAvvSecondoTempo;
    }

    public ListView getLvTempiGAvvTerzoTempo() {
        return lvTempiGAvvTerzoTempo;
    }

    public void setLvTempiGAvvTerzoTempo(ListView lvTempiGAvvTerzoTempo) {
        this.lvTempiGAvvTerzoTempo = lvTempiGAvvTerzoTempo;
    }

    public EditText getEdtCampoEsterno() {
        return edtCampoEsterno;
    }

    public void setEdtCampoEsterno(EditText edtCampoEsterno) {
        this.edtCampoEsterno = edtCampoEsterno;
    }

    public LinearLayout getLayCampoIndirizzo() {
        return layCampoIndirizzo;
    }

    public void setLayCampoIndirizzo(LinearLayout layCampoIndirizzo) {
        this.layCampoIndirizzo = layCampoIndirizzo;
    }

    public LinearLayout getLayCampoEsterno() {
        return layCampoEsterno;
    }

    public void setLayCampoEsterno(LinearLayout layCampoEsterno) {
        this.layCampoEsterno = layCampoEsterno;
    }

    public CheckBox getChkEsterno() {
        return chkEsterno;
    }

    public void setChkEsterno(CheckBox chkEsterno) {
        this.chkEsterno = chkEsterno;
    }

    public ImageView getCmdSalva() {
        return cmdSalva;
    }

    public void setCmdSalva(ImageView cmdSalva) {
        this.cmdSalva = cmdSalva;
    }

    public CheckBox getChkInCasa() {
        return chkInCasa;
    }

    public void setChkInCasa(CheckBox chkInCasa) {
        this.chkInCasa = chkInCasa;
    }

    public TextView getTxtCasa() {
        return txtCasa;
    }

    public void setTxtCasa(TextView txtCasa) {
        this.txtCasa = txtCasa;
    }

    public TextView getTxtFuori() {
        return txtFuori;
    }

    public void setTxtFuori(TextView txtFuori) {
        this.txtFuori = txtFuori;
    }

    public ImageView getImgCasa() {
        return imgCasa;
    }

    public void setImgCasa(ImageView imgCasa) {
        this.imgCasa = imgCasa;
    }

    public ImageView getImgFuori() {
        return imgFuori;
    }

    public void setImgFuori(ImageView imgFuori) {
        this.imgFuori = imgFuori;
    }

    public EditText getEdtNote() {
        return edtNote;
    }

    public void setEdtNote(EditText edtNote) {
        this.edtNote = edtNote;
    }

    public TextView getTxtData() {
        return txtData;
    }

    public void setTxtData(TextView txtData) {
        this.txtData = txtData;
    }

    public TextView getTxtOra() {
        return txtOra;
    }

    public void setTxtOra(TextView txtOra) {
        this.txtOra = txtOra;
    }

    public View getViewActivity() {
        return ViewActivity;
    }

    public void setViewActivity(View viewActivity) {
        ViewActivity = viewActivity;
    }

    public Spinner getSpnAllenatore() {
        return spnAllenatore;
    }

    public void setSpnAllenatore(Spinner spnAllenatore) {
        this.spnAllenatore = spnAllenatore;
    }

    public List<String> getAllenatore() {
        return Allenatore;
    }

    public void setAllenatore(List<String> allenatore) {
        Allenatore = allenatore;
    }

    public List<Integer> getIdAllenatore() {
        return idAllenatore;
    }

    public void setIdAllenatore(List<Integer> idAllenatore) {
        this.idAllenatore = idAllenatore;
    }

    public EditText getEdtGiochetti() {
        return edtGiochetti;
    }

    public void setEdtGiochetti(EditText edtGiochetti) {
        this.edtGiochetti = edtGiochetti;
    }

    public TextView getTxtRisAvv1Tempo() {
        return txtRisAvv1Tempo;
    }

    public void setTxtRisAvv1Tempo(TextView txtRisAvv1Tempo) {
        this.txtRisAvv1Tempo = txtRisAvv1Tempo;
    }

    public TextView getTxtRisAvv2Tempo() {
        return txtRisAvv2Tempo;
    }

    public void setTxtRisAvv2Tempo(TextView txtRisAvv2Tempo) {
        this.txtRisAvv2Tempo = txtRisAvv2Tempo;
    }

    public TextView getTxtRisAvv3Tempo() {
        return txtRisAvv3Tempo;
    }

    public void setTxtRisAvv3Tempo(TextView txtRisAvv3Tempo) {
        this.txtRisAvv3Tempo = txtRisAvv3Tempo;
    }

    public TextView getTxtGoal() {
        return txtGoal;
    }

    public void setTxtGoal(TextView txtGoal) {
        this.txtGoal = txtGoal;
    }

    public TextView getTxtTempi() {
        return txtTempi;
    }

    public void setTxtTempi(TextView txtTempi) {
        this.txtTempi = txtTempi;
    }

    public List<String> getGiocatoriPerMarcature() {
        return GiocatoriPerMarcature;
    }

    public void setGiocatoriPerMarcature(List<String> giocatoriPerMarcature) {
        GiocatoriPerMarcature = giocatoriPerMarcature;
    }

    public ArrayList<String> getListMarcPrimoTempo() {
        return listMarcPrimoTempo;
    }

    public void setListMarcPrimoTempo(ArrayList<String> listMarcPrimoTempo) {
        this.listMarcPrimoTempo = listMarcPrimoTempo;
    }

    public ArrayList<String> getListMarcSecondoTempo() {
        return listMarcSecondoTempo;
    }

    public void setListMarcSecondoTempo(ArrayList<String> listMarcSecondoTempo) {
        this.listMarcSecondoTempo = listMarcSecondoTempo;
    }

    public ArrayList<String> getListMarcTerzoTempo() {
        return listMarcTerzoTempo;
    }

    public void setListMarcTerzoTempo(ArrayList<String> listMarcTerzoTempo) {
        this.listMarcTerzoTempo = listMarcTerzoTempo;
    }

    public AdapterGiocatore getAdapterLvPrimoTempo() {
        return adapterLvPrimoTempo;
    }

    public void setAdapterLvPrimoTempo(AdapterGiocatore adapterLvPrimoTempo) {
        this.adapterLvPrimoTempo = adapterLvPrimoTempo;
    }

    public AdapterGiocatore getAdapterLvSecondoTempo() {
        return adapterLvSecondoTempo;
    }

    public void setAdapterLvSecondoTempo(AdapterGiocatore adapterLvSecondoTempo) {
        this.adapterLvSecondoTempo = adapterLvSecondoTempo;
    }

    public AdapterGiocatore getAdapterLvTerzoTempo() {
        return adapterLvTerzoTempo;
    }

    public void setAdapterLvTerzoTempo(AdapterGiocatore adapterLvTerzoTempo) {
        this.adapterLvTerzoTempo = adapterLvTerzoTempo;
    }

    public ArrayAdapter<String> getAdapterMarcatoriSecondoTempo() {
        return adapterMarcatoriSecondoTempo;
    }

    public void setAdapterMarcatoriSecondoTempo(AdapterGiocatore adapterMarcatoriSecondoTempo) {
        this.adapterMarcatoriSecondoTempo = adapterMarcatoriSecondoTempo;
    }

    public ArrayAdapter<String> getAdapterMarcatoriTerzoTempo() {
        return adapterMarcatoriTerzoTempo;
    }

    public void setAdapterMarcatoriTerzoTempo(AdapterGiocatore adapterMarcatoriTerzoTempo) {
        this.adapterMarcatoriTerzoTempo = adapterMarcatoriTerzoTempo;
    }

    public ListView getSpnMarcatoriSecondoTempo() {
        return spnMarcatoriSecondoTempo;
    }

    public void setSpnMarcatoriSecondoTempo(ListView spnMarcatoriSecondoTempo) {
        this.spnMarcatoriSecondoTempo = spnMarcatoriSecondoTempo;
    }

    public ListView getSpnMarcatoriTerzoTempo() {
        return spnMarcatoriTerzoTempo;
    }

    public void setSpnMarcatoriTerzoTempo(ListView spnMarcatoriTerzoTempo) {
        this.spnMarcatoriTerzoTempo = spnMarcatoriTerzoTempo;
    }

    public ListView getLvvMarcatoriSecondoTempo() {
        return lvvMarcatoriSecondoTempo;
    }

    public void setLvvMarcatoriSecondoTempo(ListView lvvMarcatoriSecondoTempo) {
        this.lvvMarcatoriSecondoTempo = lvvMarcatoriSecondoTempo;
    }

    public ListView getLvvMarcatoriTerzoTempo() {
        return lvvMarcatoriTerzoTempo;
    }

    public void setLvvMarcatoriTerzoTempo(ListView lvvMarcatoriTerzoTempo) {
        this.lvvMarcatoriTerzoTempo = lvvMarcatoriTerzoTempo;
    }

    public ListView getLvvMarcatoriPrimoTempo() {
        return lvvMarcatoriPrimoTempo;
    }

    public void setLvvMarcatoriPrimoTempo(ListView lvvMarcatoriPrimoTempo) {
        this.lvvMarcatoriPrimoTempo = lvvMarcatoriPrimoTempo;
    }

    public ListView getSpnMarcatoriPrimoTempo() {
        return spnMarcatoriPrimoTempo;
    }

    public void setSpnMarcatoriPrimoTempo(ListView spnMarcatoriPrimoTempo) {
        this.spnMarcatoriPrimoTempo = spnMarcatoriPrimoTempo;
    }

    public AdapterGiocatore getAdapterMarcatoriPrimoTempo() {
        return adapterMarcatoriPrimoTempo;
    }

    public void setAdapterMarcatoriPrimoTempo(AdapterGiocatore adapterMarcatoriPrimoTempo) {
        this.adapterMarcatoriPrimoTempo = adapterMarcatoriPrimoTempo;
    }

    public AdapterGiocatore getAdapterGiocatoriDaConvocare() {
        return adapterGiocatoriDaConvocare;
    }

    public void setAdapterGiocatoriDaConvocare(AdapterGiocatore adapterGiocatoriDaConvocare) {
        this.adapterGiocatoriDaConvocare = adapterGiocatoriDaConvocare;
    }

    public ArrayAdapter<String> getAdapterGiocatoriConvocati() {
        return adapterGiocatoriConvocati;
    }

    public void setAdapterGiocatoriConvocati(AdapterGiocatore adapterGiocatoriConvocati) {
        this.adapterGiocatoriConvocati = adapterGiocatoriConvocati;
    }

    public TextView getTxtCampoIndirizzo() {
        return txtCampoIndirizzo;
    }

    public void setTxtCampoIndirizzo(TextView txtCampoIndirizzo) {
        this.txtCampoIndirizzo = txtCampoIndirizzo;
    }

    public List<String> getAvversariCampoIndirizzo() {
        return AvversariCampoIndirizzo;
    }

    public void setAvversariCampoIndirizzo(List<String> avversariCampoIndirizzo) {
        AvversariCampoIndirizzo = avversariCampoIndirizzo;
    }

    public List<String> getGiocatoreConvocato() {
        return GiocatoreConvocato;
    }

    public void setGiocatoreConvocato(List<String> giocatoreConvocato) {
        GiocatoreConvocato = giocatoreConvocato;
    }

    public List<String> getGiocatoreDaConvocare() {
        return GiocatoreDaConvocare;
    }

    public void setGiocatoreDaConvocare(List<String> giocatoreDaConvocare) {
        GiocatoreDaConvocare = giocatoreDaConvocare;
    }

    public ListView getSpnConvocati() {
        return spnConvocati;
    }

    public void setSpnConvocati(ListView spnConvocati) {
        this.spnConvocati = spnConvocati;
    }

    public ListView getSpnDaConvocare() {
        return spnDaConvocare;
    }

    public void setSpnDaConvocare(ListView spnDaConvocare) {
        this.spnDaConvocare = spnDaConvocare;
    }

    public TextView getTxtCampo() {
        return txtCampo;
    }

    public void setTxtCampo(TextView txtCampo) {
        this.txtCampo = txtCampo;
    }

    public List<String> getAvversariCampo() {
        return AvversariCampo;
    }

    public void setAvversariCampo(List<String> avversariCampo) {
        AvversariCampo = avversariCampo;
    }

    public List<Integer> getAvversariCampoID() {
        return AvversariCampoID;
    }

    public void setAvversariCampoID(List<Integer> avversariCampoID) {
        AvversariCampoID = avversariCampoID;
    }

    public Spinner getSpnAvversario() {
        return spnAvversario;
    }

    public void setSpnAvversario(Spinner spnAvversario) {
        this.spnAvversario = spnAvversario;
    }

    public List<String> getAvversari() {
        return Avversari;
    }

    public void setAvversari(List<String> avversari) {
        Avversari = avversari;
    }

    public List<Integer> getAvversariID() {
        return AvversariID;
    }

    public void setAvversariID(List<Integer> avversariID) {
        AvversariID = avversariID;
    }

    public List<String> getGiocatoreCognomeNome() {
        return GiocatoreCognomeNome;
    }

    public void setGiocatoreCognomeNome(List<String> giocatoreCognomeNome) {
        GiocatoreCognomeNome = giocatoreCognomeNome;
    }

    public List<Integer> getGiocatoreID() {
        return GiocatoreID;
    }

    public void setGiocatoreID(List<Integer> giocatoreID) {
        GiocatoreID = giocatoreID;
    }

    public List<Integer> getGiocatoreIDRuolo() {
        return GiocatoreIDRuolo;
    }

    public void setGiocatoreIDRuolo(List<Integer> giocatoreIDRuolo) {
        GiocatoreIDRuolo = giocatoreIDRuolo;
    }

    public List<String> getGiocatoreRuolo() {
        return GiocatoreRuolo;
    }

    public void setGiocatoreRuolo(List<String> giocatoreRuolo) {
        GiocatoreRuolo = giocatoreRuolo;
    }

    public TextView getTxtConvocati() {
        return txtConvocati;
    }

    public void setTxtConvocati(TextView txtConvocati) {
        this.txtConvocati = txtConvocati;
    }

    public TextView getTxtCategoria() {
        return txtCategoria;
    }

    public void setTxtCategoria(TextView txtCategoria) {
        this.txtCategoria = txtCategoria;
    }

    public TextView getTxtTipologia() {
        return txtTipologia;
    }

    public void setTxtTipologia(TextView txtTipologia) {
        this.txtTipologia = txtTipologia;
    }

    public TextView getTxtAvversario() {
        return txtAvversario;
    }

    public void setTxtAvversario(TextView txtAvversario) {
        this.txtAvversario = txtAvversario;
    }

    public List<Integer> getIdTipologie() {
        return idTipologie;
    }

    public void setIdTipologie(List<Integer> idTipologie) {
        this.idTipologie = idTipologie;
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

    public void setSpnCategorie(Spinner spnCat) {
        this.spnCategorie = spnCat;
    }

    public Spinner getSpnTipologie() {
        return spnTipologie;
    }

    public void setSpnTipologie(Spinner spnTip) {
        this.spnTipologie = spnTip;
    }

    public void setDescrizioneCategorie(List<String> descrizioneCategorie) {
        DescrizioneCategorie = descrizioneCategorie;
    }

    public List<String> getDescrizioneCategorie() {
        return DescrizioneCategorie;
    }

    public void setDescrizioneTipologie(List<String> descrizioneTipologie) {
        DescrizioneTipologie = descrizioneTipologie;
    }

    public List<String> getDescrizioneTipologie() {
        return DescrizioneTipologie;
    }

    public void PulisceAmbientePartita() {
        PartitaSalvata = false;
        idCategoriaScelta = -1;
        idTipologiaScelta = -1;
        idAvversarioScelto = -1;
        idAllenatoreScelto = -1;
        setDescrizioneCategorie(null);
        setIdCategorie(null);
        setDescrizioneTipologie(null);
        setIdTipologie(null);
        setGiocatoreCognomeNome(null);
        setGiocatoreID(null);
        setGiocatoreRuolo(null);
        setGiocatoreIDRuolo(null);
        setAvversari(null);
        setAvversariID(null);
        setAvversariCampoID(null);
        setAvversariCampo(null);
    }
}
