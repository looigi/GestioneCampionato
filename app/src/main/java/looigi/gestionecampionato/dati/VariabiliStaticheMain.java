package looigi.gestionecampionato.dati;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.adapter.AdapterPartite;

public class VariabiliStaticheMain {
    //-------- Singleton ----------//
    private static VariabiliStaticheMain instance = null;

    private VariabiliStaticheMain() {
    }

    public static VariabiliStaticheMain getInstance() {
        if (instance == null) {
            instance = new VariabiliStaticheMain();
        }

        return instance;
    }

    // Main activity
    private List<String> Partite;

    public void PulisceTuttiGliArray() {
        Partite=new ArrayList<String>();
    }

    private ListView lstPartite;
    private AdapterPartite adapterPartite;
    private MenuItem itemNuovo;
    private MenuItem itemMultimedia;
    private TextView tAnno;
    private TextView tDescAnno;
    private AppBarLayout appBar;
    private FloatingActionButton actButtonNew;
    private FloatingActionButton actButtonBack;
    private ImageView imgSplash;
    private Window windowBackground;
    private DrawerLayout drawer;
    private Boolean PartitaApplicazione;
    private Toolbar toolBar;
    private String Squadra;
    private CoordinatorLayout sfondo;

    public CoordinatorLayout getSfondo() {
        return sfondo;
    }

    public void setSfondo(CoordinatorLayout sfondo) {
        this.sfondo = sfondo;
    }

    public String getSquadra() {
        return Squadra;
    }

    public void setSquadra(String squadra) {
        Squadra = squadra;
    }

    public Toolbar getToolBar() {
        return toolBar;
    }

    public void setToolBar(Toolbar toolBar) {
        this.toolBar = toolBar;
    }

    public Boolean getPartitaApplicazione() {
        return PartitaApplicazione;
    }

    public void setPartitaApplicazione(Boolean partitaApplicazione) {
        PartitaApplicazione = partitaApplicazione;
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }

    public TextView gettDescAnno() {
        return tDescAnno;
    }

    public void settDescAnno(TextView tDescAnno) {
        this.tDescAnno = tDescAnno;
    }

    public Window getWindowBackground() {
        return windowBackground;
    }

    public void setWindowBackground(Window windowBackground) {
        this.windowBackground = windowBackground;
    }

    public ImageView getImgSplash() {
        return imgSplash;
    }

    public void setImgSplash(ImageView imgSplash) {
        this.imgSplash = imgSplash;
    }

    public FloatingActionButton getActButtonBack() {
        return actButtonBack;
    }

    public void setActButtonBack(FloatingActionButton actButtonBack) {
        this.actButtonBack = actButtonBack;
    }

    public FloatingActionButton getActButtonNew() {
        return actButtonNew;
    }

    public void setActButtonNew(FloatingActionButton actButtonNew) {
        this.actButtonNew = actButtonNew;
    }

    public MenuItem getItemMultimedia() {
        return itemMultimedia;
    }

    public void setItemMultimedia(MenuItem itemMultimedia) {
        this.itemMultimedia = itemMultimedia;
    }

    public AppBarLayout getAppBar() {
        return appBar;
    }

    public void setAppBar(AppBarLayout appBar) {
        this.appBar = appBar;
    }

    public TextView gettAnno() {
        return tAnno;
    }

    public void settAnno(TextView tAnno) {
        this.tAnno = tAnno;
    }

    public MenuItem getItemNuovo() {
        return itemNuovo;
    }

    public void setItemNuovo(MenuItem itemNuovo) {
        this.itemNuovo = itemNuovo;
    }

    public ListView getLstPartite() {
        return lstPartite;
    }

    public void setLstPartite(ListView lstPartite) {
        this.lstPartite = lstPartite;
    }

    public AdapterPartite getAdapterPartite() {
        return adapterPartite;
    }

    public void setAdapterPartite(AdapterPartite adapterPartite) {
        this.adapterPartite = adapterPartite;
    }

    public List<String> getPartite() {
        return Partite;
    }

    public void setPartite(List<String> partite) {
        Partite = partite;
    }
    // Main activity
}
