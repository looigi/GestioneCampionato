package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.dati.VariabiliStaticheRose;
import looigi.gestionecampionato.db_remoto.DBRemotoMultimedia;
import looigi.gestionecampionato.maschere.NuovaPartita;
import looigi.gestionecampionato.maschere.VisualizzaImmagini;
import looigi.gestionecampionato.maschere.VisualizzaVideo;
import looigi.gestionecampionato.utilities.HelperKeyboard;
import looigi.gestionecampionato.utilities.ScaricaMultimedia;
import looigi.gestionecampionato.utilities.Utility;

public class AdapterMultimedia extends ArrayAdapter
{
    private Context context;
    private List<String> lista;

    public AdapterMultimedia(Context context, int textViewResourceId, List<String> objects)
    {
        super(context, textViewResourceId, objects);

        this.context = context;
        this.lista=objects;
    }

    @Override
    @Nullable
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.listview_multimedia, null);

        String riga = lista.get(position);

        TextView TxtId=convertView.findViewById(R.id.idMultimedia);
        TextView TxtNome=convertView.findViewById(R.id.NomeFile);
        TextView TxtDime=convertView.findViewById(R.id.Dimensione);
        TextView TxtData=convertView.findViewById(R.id.UltimaModifica);
        ImageView ImgMultimedia= convertView.findViewById(R.id.imgMultimedia);
        ImageView ImgEliminaMultimedia= convertView.findViewById(R.id.imgEliminaMultimedia);

        final String Campi[] = riga.split(";");

        TxtId.setText(Campi[0]);
        TxtNome.setText(Campi[1]);

        float d = Float.parseFloat(Campi[2]);
        String dd ="";
        if (d<1024) {
            dd="B.";
        } else {
            if (d<1024000) {
                d/=1024;
                dd="Kb.";
            } else {
                if (d<1024000000) {
                    d/=1024000;
                    dd="Mb.";
                } else {
                    d/=1024000000;
                    dd="Gb.";
                }
            }
        }
        String ddd = String.format("%.2f", d);
        TxtDime.setText(ddd+" "+dd);

        TxtData.setText(Campi[3]);
        String Codice=Campi[4];

        String Path="";
        String Nome="";

        if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getRose())) {
            Path = VariabiliStaticheGlobali.getInstance().PercorsoDIR + "/Giocatori/" + Codice;
            Nome=Campi[1];
        }
        if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getNuovaPartita())) {
            Path = VariabiliStaticheGlobali.getInstance().PercorsoDIR + "/Partite/" + Codice;
            Nome=Campi[1];
        }
        final String Imm = Path + "/" + Nome;

        if (Nome.toUpperCase().contains(".JPG")) {
            if (Utility.getInstance().fileExistsInSD(Nome, Path)) {
                try {
                    ImgMultimedia.setImageBitmap(BitmapFactory.decodeFile(Path + "/" + Nome));
                } catch (OutOfMemoryError ignored) {
                } catch (Exception ignored) {

                }
            } else {
                ScaricaMultimedia.getInstance().AggiungeFileMultimedialeDaScaricare(Campi[0], ImgMultimedia, Path + "/" + Nome);
            }
        } else {
            if (Utility.getInstance().fileExistsInSD(Nome, Path)) {
                ImgMultimedia.setBackgroundResource(R.drawable.film);
            } else {
                ScaricaMultimedia.getInstance().AggiungeFileMultimedialeDaScaricare(Campi[0], ImgMultimedia, Path + "/" + Nome);
            }
        }

        ImgEliminaMultimedia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String Percorso;
                String Tipologia="";
                String Id="";

                if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getRose())) {
                    if (VariabiliStaticheRose.getInstance().idCategoriaScelta1 > -1) {
                        Tipologia="Giocatori";
                        Id=Integer.toString(VariabiliStaticheRose.getInstance().idGiocatoreScelto);
                    }
                }
                if (VariabiliStaticheGlobali.MascheraAttuale.equals(NomiMaschere.getInstance().getNuovaPartita())) {
                    if (NuovaPartita.PartitaNuova > -1) {
                        Tipologia="Partite";
                        Id=Integer.toString(NuovaPartita.PartitaNuova);
                    }
                }

                if (Tipologia.equals("Giocatori")) {
                    Id=VariabiliStaticheGlobali.getInstance().getAnnoInCorso()+"_"+Id;
                }
                Percorso=Tipologia+"\\"+Id+"\\"+Campi[1];

                VariabiliStaticheGlobali.ImmagineDaEliminare=Imm;

                DBRemotoMultimedia dbm = new DBRemotoMultimedia();
                dbm.EliminaMultimedia(VariabiliStaticheGlobali.getInstance().getContext(), Percorso, NomiMaschere.getInstance().getMultimedia());
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (Imm.toUpperCase().contains(".JPG")) {
                    Fragment fragment = new VisualizzaImmagini();
                    String title = NomiMaschere.getInstance().getVisualizzaImmaginiPerTitolo();

                    Bundle args = new Bundle();
                    args.putString("NomeImmagine", Imm);
                    fragment.setArguments(args);

                    if (fragment != null) {
                        FragmentTransaction ft = VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale()
                                .getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }

                    if (VariabiliStaticheGlobali.getInstance().getContextPrincipale().getSupportActionBar() != null) {
                        VariabiliStaticheGlobali.getInstance().getContextPrincipale().getSupportActionBar().setTitle(title);
                    }

                    DrawerLayout drawer = VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale().findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);

                    HelperKeyboard.closeKeyboard(VariabiliStaticheGlobali.getInstance().getContext());

                    VariabiliStaticheMain.getInstance().getActButtonNew().hide(); // .setVisibility(LinearLayout.GONE);
                    VariabiliStaticheMain.getInstance().getActButtonBack().show(); // .setVisibility(LinearLayout.VISIBLE);
                } else {
                    Fragment fragment = new VisualizzaVideo();
                    String title = NomiMaschere.getInstance().getVisualizzaImmaginiPerTitolo();

                    Bundle args = new Bundle();
                    args.putString("NomeVideo", Imm);
                    fragment.setArguments(args);

                    if (fragment != null) {
                        FragmentTransaction ft = VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale()
                                .getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.content_frame, fragment);
                        ft.commit();
                    }

                    if (VariabiliStaticheGlobali.getInstance().getContextPrincipale().getSupportActionBar() != null) {
                        VariabiliStaticheGlobali.getInstance().getContextPrincipale().getSupportActionBar().setTitle(title);
                    }

                    DrawerLayout drawer = VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale().findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);

                    HelperKeyboard.closeKeyboard(VariabiliStaticheGlobali.getInstance().getContext());

                    VariabiliStaticheMain.getInstance().getActButtonNew().hide(); // .setVisibility(LinearLayout.GONE);
                    VariabiliStaticheMain.getInstance().getActButtonBack().show(); // .setVisibility(LinearLayout.VISIBLE);
                }
            }
        });

        return convertView;
    }
}
