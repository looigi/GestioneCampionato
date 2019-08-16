package looigi.gestionecampionato;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Map;

import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheAllenatori;
import looigi.gestionecampionato.dati.VariabiliStaticheDirigenti;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.dati.VariabiliStaticheNuovaPartita;
import looigi.gestionecampionato.dati.VariabiliStaticheRose;
import looigi.gestionecampionato.db_locale.DBLocale;
import looigi.gestionecampionato.db_remoto.ControlloVersioneApplicazione;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.Allenatori;
import looigi.gestionecampionato.maschere.Arbitri;
import looigi.gestionecampionato.maschere.Avversari;
import looigi.gestionecampionato.maschere.Campionato;
import looigi.gestionecampionato.maschere.Categorie;
import looigi.gestionecampionato.maschere.Dirigenti;
import looigi.gestionecampionato.maschere.Eventi;
import looigi.gestionecampionato.maschere.NuovaPartita;
import looigi.gestionecampionato.maschere.Rose;
import looigi.gestionecampionato.maschere.Settings;
import looigi.gestionecampionato.maschere.Splash;
import looigi.gestionecampionato.maschere.VisualizzaImmagini;
import looigi.gestionecampionato.upload_download.CropUtility;
import looigi.gestionecampionato.upload_download.CropUtilityMultimedia;
import looigi.gestionecampionato.utilities.Permessi;
import looigi.gestionecampionato.utilities.Utility;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Context context;
    private AppCompatActivity a;
    private CropUtilityMultimedia cum;
    private CropUtility cu;
    public static String TipologiaMultimedia;
    public static String idMultimedia;
    private VariabiliStaticheMain vm = VariabiliStaticheMain.getInstance();
    private VariabiliStaticheGlobali vg = VariabiliStaticheGlobali.getInstance();
    private boolean CiSonoPermessi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Permessi p=new Permessi();
        CiSonoPermessi  = p.ControllaPermessi(this);
        if (CiSonoPermessi) {
            ControlloVersioneApplicazione c = new ControlloVersioneApplicazione();
            c.ControllaVersione(this);

            EsegueEntrata();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (!CiSonoPermessi) {
            int index = 0;
            Map<String, Integer> PermissionsMap = new HashMap<String, Integer>();
            for (String permission : permissions) {
                PermissionsMap.put(permission, grantResults[index]);
                index++;
            }

            EsegueEntrata();
        }
    }

    private void EsegueEntrata() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        vm.setToolBar(toolbar);

        a=this;
        cum = new CropUtilityMultimedia();
        cu = new CropUtility();

        DBLocale dbl = new DBLocale();
        dbl.CreaDB(this);
        dbl.PrendeDatiUtente(this);

        vm.setSfondo((CoordinatorLayout) findViewById(R.id.layPrincipale));

        vm.settAnno((TextView) findViewById(R.id.txtAnno));
        vm.settDescAnno((TextView) findViewById(R.id.txtDescAnno));

        vm.setDrawer((DrawerLayout) findViewById(R.id.drawer_layout));
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, vm.getDrawer(),
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        vm.getDrawer().addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Utility.getInstance().CreaCartelle(vg.PercorsoDIR);
        Utility.getInstance().CreaCartelle(vg.PercorsoDIR+"/Giocatori");
        Utility.getInstance().CreaCartelle(vg.PercorsoDIR+"/Categorie");
        Utility.getInstance().CreaCartelle(vg.PercorsoDIR+"/Avversari");
        Utility.getInstance().CreaCartelle(vg.PercorsoDIR+"/Allenatori");
        Utility.getInstance().CreaCartelle(vg.PercorsoDIR+"/Dirigenti");
        Utility.getInstance().CreaCartelle(vg.PercorsoDIR+"/Arbitri");

        //overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        vg.setContext(this);
        vg.setFragmentActivityPrincipale(this);
        vg.setContextPrincipale(this);

        VariabiliStaticheNuovaPartita.getInstance().PulisceAmbientePartita();

        vm.setAppBar((AppBarLayout) findViewById(R.id.appBarLayout));
        vm.getAppBar().setVisibility(LinearLayout.GONE);
        vm.setWindowBackground(getWindow());
        // getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        vm.setActButtonNew((FloatingActionButton) findViewById(R.id.fab));
        vm.getActButtonNew().hide(); // .setVisibility(LinearLayout.GONE);
        vm.getActButtonNew().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EsegueMenuActionButton();
            }
        });

        vm.setActButtonBack((FloatingActionButton) findViewById(R.id.fabBack));
        vm.getActButtonBack().hide(); // .setVisibility(LinearLayout.GONE);
        vm.getActButtonBack().setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TornaIndietroMultiMedia();
            }
        });

        Fragment fragment = new Splash();
        FragmentTransaction ft = vg.getFragmentActivityPrincipale().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();
    }

    private void TornaIndietroMultiMedia() {
        if (VisualizzaImmagini.MascheraApertaPer==null || VisualizzaImmagini.MascheraApertaPer.isEmpty()) {
            if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getRose())) {
                Utility.getInstance().CambiaMaschera(R.id.rose, -1, -1);
            }
            if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                Utility.getInstance().CambiaMaschera(R.id.nuova_partita, NuovaPartita.PartitaNuova, -1);
            }
            if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                Utility.getInstance().CambiaMaschera(R.id.nuova_partita, NuovaPartita.PartitaNuova, -1);
            }
            if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getAlbum())) {
                Utility.getInstance().CambiaMaschera(R.id.album, -1, -1);
            }
        } else {
            if (VisualizzaImmagini.MascheraApertaPer.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getNuovaPartita();
                Utility.getInstance().CambiaMaschera(R.id.multimedia, -1, -1);
            }
            if (VisualizzaImmagini.MascheraApertaPer.equals(NomiMaschere.getInstance().getRose())) {
                VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getRose();
                Utility.getInstance().CambiaMaschera(R.id.multimedia, -1, -1);
            }
            if (VisualizzaImmagini.MascheraApertaPer.equals(NomiMaschere.getInstance().getAlbum())) {
                VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getAlbum();
                Utility.getInstance().CambiaMaschera(R.id.album, -1, -1);
            }

            VisualizzaImmagini.MascheraApertaPer="";
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // super.onBackPressed();
            //if (!VariabiliStaticheGlobali.MascheraAttualePerMultimedia.isEmpty()) {
            //    if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getRose())) {
            //        // Utility.getInstance().CambiaMaschera();
            //    }
            //    if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getNuovaPartita())) {
            //        // Utility.getInstance().CambiaMaschera();
            //    }
            //} else {
                return;
            //}
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if ( keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        vm.setItemNuovo(menu.findItem(R.id.menu_nuovo));
        vm.setItemMultimedia(menu.findItem(R.id.multimedia));
        Utility.getInstance().ImpostaMenu();

        //itemNuovo.setVisible(false);

        //switch (VariabiliStaticheGlobali.MascheraAttuale) {
        //    case "ALLENATORI":
        //        itemNuovo.setVisible(true);
        //        break;
        //}

        return true;
    }

    public void displayView(int viewId, int NumeroPartita) {
        Utility.getInstance().CambiaMaschera(viewId, NumeroPartita, -1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (VariabiliStaticheMain.getInstance().getPartitaApplicazione()) {
            if (id == R.id.menu_settings) {
                Fragment fragment = new Settings();
                String title = NomiMaschere.getInstance().getSettingsPerTitolo();
                VariabiliStaticheGlobali.MascheraAttuale = NomiMaschere.getInstance().getSettings();

                if (fragment != null) {
                    FragmentTransaction ft = vg.getFragmentActivityPrincipale().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, fragment);
                    ft.commit();
                }

                if (vg.getContextPrincipale().getSupportActionBar() != null) {
                    vg.getContextPrincipale().getSupportActionBar().setTitle(title);
                }

                VariabiliStaticheMain.getInstance().setDrawer((DrawerLayout)
                        vg.getFragmentActivityPrincipale().findViewById(R.id.drawer_layout));
                VariabiliStaticheMain.getInstance().getDrawer().closeDrawer(GravityCompat.START);

                Utility.getInstance().ImpostaMenu();
            }

            if (id == R.id.menu_nuovo) {
                EsegueMenuActionButton();

                return true;
            }

            if (id == R.id.multimedia) {
                if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getRose())) {
                    if (VariabiliStaticheRose.getInstance().idGiocatoreScelto == -1) {
                        DialogMessaggio.getInstance().show(vg.getContext(), "Selezionare un giocatore", true, VariabiliStaticheGlobali.NomeApplicazione);
                    } else {
                        Utility.getInstance().CambiaMaschera(R.id.multimedia, -1, -1);
                    }
                }

                if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                    if (NuovaPartita.PartitaNuova == -1) {
                        DialogMessaggio.getInstance().show(vg.getContext(), "Non è possibile inserire multimedia\nsulla creazione della partita.\nEffettuare l'operazione successivamente", true, VariabiliStaticheGlobali.NomeApplicazione);
                    } else {
                        Utility.getInstance().CambiaMaschera(R.id.multimedia, -1, -1);
                    }
                }

                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void EsegueMenuActionButton() {
        if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getAllenatori())) {
            if (VariabiliStaticheAllenatori.getInstance().idCategoriaScelta>-1) {
                Allenatori.NuovoAllenatore();
            } else {
                DialogMessaggio.getInstance().show(vg.getContext(),"Selezionare una categoria",
                        true, VariabiliStaticheGlobali.NomeApplicazione);
            }
        }
        if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getDirigenti())) {
            if (VariabiliStaticheDirigenti.getInstance().idCategoriaScelta>-1) {
                Dirigenti.NuovoDirigente();
            } else {
                DialogMessaggio.getInstance().show(vg.getContext(),"Selezionare una categoria",
                        true, VariabiliStaticheGlobali.NomeApplicazione);
            }
        }
        if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getEventi())) {
            Eventi.NuovoEvento();
        }
        if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getArbitri())) {
            Arbitri.NuovoArbitro();
        }
        if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getAvversari())) {
            Avversari.NuovoAvversario();
        }
        if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getCategorie())) {
            Categorie.NuovaCategoria();
        }
        if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getCampionato())) {
            Campionato.NuovaSquadra();
        }
        if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getRose())) {
            if (VariabiliStaticheRose.getInstance().idCategoriaScelta1 >-1) {
                Rose.NuovoGiocatore();
            } else {
                DialogMessaggio.getInstance().show(vg.getContext(),"Selezionare una categoria", true, VariabiliStaticheGlobali.NomeApplicazione);
            }
        }

        if (!VariabiliStaticheGlobali.MascheraAttualePerMultimedia.isEmpty()) {
            if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getRose())) {
                if (VariabiliStaticheRose.getInstance().idCategoriaScelta1 > -1) {
                    TipologiaMultimedia="Giocatori";
                    idMultimedia=Integer.toString(VariabiliStaticheRose.getInstance().idGiocatoreScelto);
                    cum.SceglieFotoDaGalleria(a);
                }
            }
            if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                if (NuovaPartita.PartitaNuova > -1) {
                    TipologiaMultimedia="Partite";
                    idMultimedia=Integer.toString(NuovaPartita.PartitaNuova);
                    cum.SceglieFotoDaGalleria(a);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getAvversari())) {
        ////    Avversari.onActRes(requestCode, resultCode, data);
        //} else {
            if (requestCode == cum.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri uri = data.getData();
                cum.NomeFileGalleria = uri.getLastPathSegment();
                cum.performCrop(uri);
            } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getAvversari())) {
                    Avversari.SalvataggioImmagine(resultCode, data);
                } else {
                    if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getAllenatori())) {
                        Allenatori.SalvataggioImmagine(resultCode, data);
                    } else {
                        if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getDirigenti())) {
                            Dirigenti.SalvataggioImmagine(resultCode, data);
                        } else {
                            if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getArbitri())) {
                                Arbitri.SalvataggioImmagine(resultCode, data);
                            } else {
                                if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getCategorie())) {
                                    Categorie.SalvataggioImmagine(resultCode, data);
                                } else {
                                    if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getRose())) {
                                        Rose.SalvataggioImmagine(resultCode, data);
                                    } else {
                                        CropImage.ActivityResult result = CropImage.getActivityResult(data);
                                        if (resultCode == RESULT_OK) {
                                            Uri resultUri = result.getUri();

                                            cum.SalvataggioFileMultimediale(
                                                    resultUri,
                                                    TipologiaMultimedia,
                                                    idMultimedia);
                                        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                                            Exception error = result.getError();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        //}
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displayView(item.getItemId(), -1);
        //int id = item.getItemId();
//
        //if (id == R.id.nuova_partita) {
//
        //}
        //if (id == R.id.uscita) {
//
        //}
//
        //DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
