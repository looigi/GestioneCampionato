package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.adapter.AdapterThumbs;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheAlbum;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheUtenti;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.db_remoto.DBRemotoMultimedia;
import looigi.gestionecampionato.utilities.ScaricaImmagini;
import looigi.gestionecampionato.utilities.Utility;
import uk.co.senab.photoview.PhotoViewAttacher;

public class Album extends android.support.v4.app.Fragment {
    private Context context;
    private static String TAG= NomiMaschere.getInstance().getAlbum();
    private static boolean isSfumanding=false;
    private static boolean stoppati=false;
    private static CountDownTimer ct;
    private boolean ApertoChiuso;
    private static Boolean StaVedendo=false;
    private static String NomeVideo="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = this.getActivity();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.album, container, false));
        } catch (Exception ignored) {

        }

        if (view!=null) {
            VariabiliStaticheGlobali.getInstance().setViewActivity(view);

            initializeGraphic();
        }

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //isVisible=isVisibleToUser;

        //if (isVisible) {
        //    initializeGraphic();
        //}
    }

    @Override
    public void onResume()
    {
        super.onResume();

        //if (isVisible) {
        //    initializeGraphic();
        //}
    }

    private void initializeGraphic() {
        final Context context = VariabiliStaticheAlbum.getInstance().getContext();
        View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            DBRemotoCategorie dbr = new DBRemotoCategorie();
            dbr.RitornaCategorie(context, TAG);

            VariabiliStaticheAlbum.getInstance().setTxtRiga0((TextView) view.findViewById(R.id.txtRiga0));
            VariabiliStaticheAlbum.getInstance().setTxtRiga1((TextView) view.findViewById(R.id.txtRiga1));
            VariabiliStaticheAlbum.getInstance().setTxtRiga2((TextView) view.findViewById(R.id.txtRiga2));
            VariabiliStaticheAlbum.getInstance().setTxtRiga3((TextView) view.findViewById(R.id.txtRiga3));
            VariabiliStaticheAlbum.getInstance().setTxtRiga4((TextView) view.findViewById(R.id.txtRiga4));

            VariabiliStaticheAlbum.getInstance().getTxtRiga1().setVisibility(LinearLayout.GONE);
            VariabiliStaticheAlbum.getInstance().getTxtRiga2().setVisibility(LinearLayout.GONE);
            VariabiliStaticheAlbum.getInstance().getTxtRiga3().setVisibility(LinearLayout.GONE);
            VariabiliStaticheAlbum.getInstance().getTxtRiga4().setVisibility(LinearLayout.GONE);

            VariabiliStaticheAlbum.getInstance().setLstThumbs((ListView) view.findViewById(R.id.lstThumbs));

            VariabiliStaticheAlbum.getInstance().setSpnCategorie((Spinner) view.findViewById(R.id.spnCategorie));

            // Utility.getInstance().SettaColoreSceltaCategoria(view);

            VariabiliStaticheAlbum.getInstance().setImgAlbum((ImageView) view.findViewById(R.id.imgAlbum));
            VariabiliStaticheAlbum.getInstance().getImgAlbum().setVisibility(LinearLayout.GONE);

            VariabiliStaticheAlbum.getInstance().setvView((VideoView) view.findViewById(R.id.videoView1));
            VariabiliStaticheAlbum.getInstance().getvView().setVisibility(LinearLayout.GONE);
            VariabiliStaticheAlbum.getInstance().getvView().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!StaVedendo) {
                        PlayVideo();
                    } else {
                        StopVideo();
                    }
                }
            });

            //VariabiliStaticheAlbum.getInstance().setLayInfo((RelativeLayout) view.findViewById(R.id.layInfo));
            //VariabiliStaticheAlbum.getInstance().getLayInfo().setAlpha(.3F);
            //VariabiliStaticheAlbum.getInstance().getLayInfo().setVisibility(LinearLayout.GONE);
            //VariabiliStaticheAlbum.getInstance().getLayInfo().setOnClickListener(new View.OnClickListener() {
            //    public void onClick(View v) {
            //        AccendeSpegneIcone();
            //    }
            //});

            VariabiliStaticheAlbum.getInstance().setImgIndietro((ImageView) view.findViewById(R.id.imgIndietro));
            VariabiliStaticheAlbum.getInstance().getImgIndietro().setVisibility(LinearLayout.GONE);
            // VariabiliStaticheAlbum.getInstance().getImgIndietro().setAlpha(.3F);
            VariabiliStaticheAlbum.getInstance().getImgIndietro().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    VariabiliStaticheAlbum.getInstance().QualeImmagine--;
                    if (VariabiliStaticheAlbum.getInstance().QualeImmagine<0) {
                        VariabiliStaticheAlbum.getInstance().QualeImmagine=VariabiliStaticheAlbum.getInstance().QuanteImmagini;
                    }
                    VisualizzaImmagine();
                }
            });

            VariabiliStaticheAlbum.getInstance().setImgAvanti((ImageView) view.findViewById(R.id.imgAvanti));
            VariabiliStaticheAlbum.getInstance().getImgAvanti().setVisibility(LinearLayout.GONE);
            // VariabiliStaticheAlbum.getInstance().getImgAvanti().setAlpha(.3F);
            VariabiliStaticheAlbum.getInstance().getImgAvanti().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    VariabiliStaticheAlbum.getInstance().QualeImmagine++;
                    if (VariabiliStaticheAlbum.getInstance().QualeImmagine>VariabiliStaticheAlbum.getInstance().QuanteImmagini) {
                        VariabiliStaticheAlbum.getInstance().QualeImmagine=0;
                    }
                    VisualizzaImmagine();
                }
            });

            Button cmdScegliCat = view.findViewById(R.id.cmdScegliCat);
            cmdScegliCat.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (VariabiliStaticheAlbum.getInstance().idCategoriaScelta>-1) {
                        DBRemotoMultimedia dbr = new DBRemotoMultimedia();
                        dbr.RitornaAlbumPerCategoria(context, Integer.toString(VariabiliStaticheAlbum.getInstance().idCategoriaScelta), TAG);
                    }
                }
            });

            ApertoChiuso=true;
            /* ImageView imgMostraNasconde = (ImageView) view.findViewById(R.id.imgMostraNasconde);
            final RelativeLayout rlthumbs = (RelativeLayout) view.findViewById(R.id.layThumbs);
            rlthumbs.setVisibility(LinearLayout.VISIBLE);
            imgMostraNasconde.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (ApertoChiuso) {
                        rlthumbs.getLayoutParams().height = 0;
                        rlthumbs.invalidate();
                        ApertoChiuso=false;
                    } else {
                        rlthumbs.getLayoutParams().height = 85;
                        rlthumbs.invalidate();
                        ApertoChiuso=true;
                    }
                }
            }); */
        }
    }

    public static void RiempieListaCategorie() {
        if (VariabiliStaticheAlbum.getInstance().getCategorie() != null) {
            final ArrayAdapter<String> adapterCategorie = new ArrayAdapter<String>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_piccolo,
                    VariabiliStaticheAlbum.getInstance().getCategorie());
            adapterCategorie.setDropDownViewResource(R.layout.spinner_item_piccolo);
            VariabiliStaticheAlbum.getInstance().getSpnCategorie().setAdapter(adapterCategorie);

            Utility.getInstance().CercaESettaStringaInSpinner(VariabiliStaticheAlbum.getInstance().getSpnCategorie(),
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria1());

            int pos = Utility.getInstance().CercaESettaStringaInSpinner(VariabiliStaticheAlbum.getInstance().getSpnCategorie(),
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria1());
            if (pos>-1) {
                VariabiliStaticheAlbum.getInstance().getSpnCategorie().setSelection(pos);
            }
            VariabiliStaticheAlbum.getInstance().idCategoriaScelta=Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1());
            VariabiliStaticheAlbum.getInstance().idCategoriaScelta = Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1());

            VariabiliStaticheAlbum.getInstance().getSpnCategorie().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int idCategoria=VariabiliStaticheAlbum.getInstance().getIdCategorie().get(position);
                    VariabiliStaticheAlbum.getInstance().idCategoriaScelta=idCategoria;

                    VariabiliStaticheUtenti.getInstance().setIdCategoriaScelta(VariabiliStaticheAlbum.getInstance().idCategoriaScelta);
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().setDescCategoria(VariabiliStaticheAlbum.getInstance().getCategorie().get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    /* private static void AccendeSpegneIcone() {
        Runnable runRiga;
        Handler hSelezionaRiga;

        VariabiliStaticheAlbum.getInstance().getImgIndietro().setAlpha(1F);
        VariabiliStaticheAlbum.getInstance().getImgAvanti().setAlpha(1F);
        VariabiliStaticheAlbum.getInstance().getLayInfo().setAlpha(1F);

        ct = new CountDownTimer(15000, 100) {
            public void onTick(long millisUntilFinished) {
                if (!stoppati) {
                    float a = VariabiliStaticheAlbum.getInstance().getImgAvanti().getAlpha();
                    a -= .025;
                    if (a < .3) a = .3F;
                    VariabiliStaticheAlbum.getInstance().getImgAvanti().setAlpha(a);
                    VariabiliStaticheAlbum.getInstance().getImgIndietro().setAlpha(a);
                    VariabiliStaticheAlbum.getInstance().getLayInfo().setAlpha(a);
                } else {
                    VariabiliStaticheAlbum.getInstance().getImgIndietro().setAlpha(1F);
                    VariabiliStaticheAlbum.getInstance().getImgAvanti().setAlpha(1F);
                    VariabiliStaticheAlbum.getInstance().getLayInfo().setAlpha(1F);

                    ct.cancel();
                }
            }

            public void onFinish() {
                if (!stoppati) {
                    VariabiliStaticheAlbum.getInstance().getImgAvanti().setAlpha(.3F);
                    VariabiliStaticheAlbum.getInstance().getImgIndietro().setAlpha(.3F);
                    VariabiliStaticheAlbum.getInstance().getLayInfo().setAlpha(.3F);
                }

                isSfumanding=false;
                stoppati=false;
            }
        };

        if (!isSfumanding) {
            isSfumanding=true;

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga = new Runnable() {
                @Override
                public void run() {
                    stoppati=false;
                    ct.start();
                }
            }, 2000);
        } else {
            ct.cancel();
            stoppati=true;

            hSelezionaRiga = new Handler();
            hSelezionaRiga.postDelayed(runRiga = new Runnable() {
                @Override
                public void run() {
                    stoppati=false;
                    ct.start();
                }
            }, 2000);
        }
    } */

    public static void SettaThumbs() {
        List<String> NomeImmagine = new ArrayList<>();

        int i=0;
        for (String s : VariabiliStaticheAlbum.getInstance().getImmagini())  {
            String[] Campi = s.split(";");

            Campi[0]=VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/"+Campi[0].replace("\\","/");
            NomeImmagine.add(Integer.toString(i)+";"+Campi[0]+";");
            i++;
        }

        if (NomeImmagine.size()>0) {
            VariabiliStaticheAlbum.getInstance().setAdapterLvThumbs(new AdapterThumbs(
                    VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1,
                    NomeImmagine));
            VariabiliStaticheAlbum.getInstance().getLstThumbs().setAdapter(VariabiliStaticheAlbum.getInstance().getAdapterLvThumbs());
            VariabiliStaticheAlbum.getInstance().getLstThumbs().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                }
            });
        }
    }

    public static void VisualizzaImmagine() {
        if (VariabiliStaticheAlbum.getInstance().QualeImmagine>-1) {
            VariabiliStaticheAlbum.getInstance().getImgIndietro().setVisibility(LinearLayout.VISIBLE);
            VariabiliStaticheAlbum.getInstance().getImgAvanti().setVisibility(LinearLayout.VISIBLE);
            // VariabiliStaticheAlbum.getInstance().getLayInfo().setVisibility(LinearLayout.VISIBLE);

            // AccendeSpegneIcone();

            String Immagine = VariabiliStaticheAlbum.getInstance().getImmagini().get(VariabiliStaticheAlbum.getInstance().QualeImmagine);
            String Campi[] = Immagine.split(";");

            StopVideo();
            StaVedendo=false;
            NomeVideo="";

            String OriginalName = Campi[0];
            Campi[0] = VariabiliStaticheGlobali.getInstance().PercorsoDIR + "/" + Campi[0].replace("\\", "/");

            if (Immagine.toUpperCase().contains(".JPG")) {
                VariabiliStaticheAlbum.getInstance().getTxtRiga0().setText("Immagine " +
                        Integer.toString(VariabiliStaticheAlbum.getInstance().QualeImmagine + 1) + "/" +
                        Integer.toString(VariabiliStaticheAlbum.getInstance().QuanteImmagini + 1));

                File f = new File(Campi[0]);
                if (f.exists()) {
                    VariabiliStaticheAlbum.getInstance().getImgAlbum().setImageBitmap(BitmapFactory.decodeFile(Campi[0]));
                    VariabiliStaticheAlbum.getInstance().getImgAlbum().setVisibility(LinearLayout.VISIBLE);
                    VariabiliStaticheAlbum.getInstance().getvView().setVisibility(LinearLayout.GONE);
                } else {
                    String separator = "\\\\";
                    String c[] = OriginalName.split(separator);

                    if (c.length > 2) {
                        ScaricaImmagini.getInstance().AggiungeImmagineDaScaricare(Campi[1].toUpperCase().trim(),
                                VariabiliStaticheGlobali.getInstance().getAnnoInCorso(),
                                VariabiliStaticheAlbum.getInstance().getImgAlbum(),
                                c[1],
                                c[2]);
                    }
                }

                if (Campi.length > 1) {
                    switch (Campi[1]) {
                        case "Partite":
                            VariabiliStaticheAlbum.getInstance().getTxtRiga1().setVisibility(LinearLayout.VISIBLE);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga2().setVisibility(LinearLayout.VISIBLE);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga3().setVisibility(LinearLayout.VISIBLE);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga4().setVisibility(LinearLayout.VISIBLE);

                            VariabiliStaticheAlbum.getInstance().getTxtRiga1().setText("Partita: " + Campi[2]);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga2().setText(Campi[3]);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga3().setText("Avversario: " + Campi[4]);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga4().setText("Risultato: " + Campi[5]);
                            break;
                        case "Giocatori":
                            VariabiliStaticheAlbum.getInstance().getTxtRiga1().setVisibility(LinearLayout.VISIBLE);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga2().setVisibility(LinearLayout.VISIBLE);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga3().setVisibility(LinearLayout.VISIBLE);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga4().setVisibility(LinearLayout.VISIBLE);

                            VariabiliStaticheAlbum.getInstance().getTxtRiga1().setText(Campi[2]);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga2().setText("Soprannome: " + Campi[3]);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga3().setText("Ruolo: " + Campi[4]);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga4().setText("Nascita: " + Campi[5]);
                            break;
                        case "Allenatori":
                            VariabiliStaticheAlbum.getInstance().getTxtRiga1().setVisibility(LinearLayout.VISIBLE);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga2().setVisibility(LinearLayout.GONE);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga3().setVisibility(LinearLayout.GONE);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga4().setVisibility(LinearLayout.GONE);

                            VariabiliStaticheAlbum.getInstance().getTxtRiga1().setText("Allenatore: " + Campi[2]);
                            break;
                        case "Dirigenti":
                            VariabiliStaticheAlbum.getInstance().getTxtRiga1().setVisibility(LinearLayout.VISIBLE);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga2().setVisibility(LinearLayout.GONE);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga3().setVisibility(LinearLayout.GONE);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga4().setVisibility(LinearLayout.GONE);

                            VariabiliStaticheAlbum.getInstance().getTxtRiga1().setText("Dirigente: " + Campi[2]);
                            break;
                        case "Categorie":
                            VariabiliStaticheAlbum.getInstance().getTxtRiga1().setVisibility(LinearLayout.VISIBLE);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga2().setVisibility(LinearLayout.GONE);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga3().setVisibility(LinearLayout.GONE);
                            VariabiliStaticheAlbum.getInstance().getTxtRiga4().setVisibility(LinearLayout.GONE);

                            VariabiliStaticheAlbum.getInstance().getTxtRiga1().setText("Categoria: " + Campi[2]);
                            break;
                    }
                }
            } else {
                VariabiliStaticheAlbum.getInstance().getTxtRiga0().setText("Video " +
                        Integer.toString(VariabiliStaticheAlbum.getInstance().QualeImmagine + 1) + "/" +
                        Integer.toString(VariabiliStaticheAlbum.getInstance().QuanteImmagini + 1));

                NomeVideo=Campi[0];
                File f = new File(Campi[0]);
                if (f.exists()) {
                    VariabiliStaticheAlbum.getInstance().getImgAlbum().setVisibility(LinearLayout.GONE);
                    VariabiliStaticheAlbum.getInstance().getvView().setVisibility(LinearLayout.VISIBLE);

                    VariabiliStaticheAlbum.getInstance().getvView().setVideoURI(Uri.parse(NomeVideo));
                    VariabiliStaticheAlbum.getInstance().getvView().seekTo(100);
                } else {
                    // String separator = "\\\\";
                    // String c[] = OriginalName.split(separator);
                    // String Path="";
                    // String Nome="";
//
                    // if (c.length > 2) {
                    //     ScaricaMultimedia.getInstance().AggiungeFileMultimedialeDaScaricare(Campi[0],
                    //             VariabiliStaticheAlbum.getInstance().getImgAlbum(), Path + "/" + Nome);
                    // }

                    VariabiliStaticheAlbum.getInstance().getImgAlbum().setBackgroundResource(R.drawable.sconosciuto);;
                    VariabiliStaticheAlbum.getInstance().getImgAlbum().setVisibility(LinearLayout.VISIBLE);
                    VariabiliStaticheAlbum.getInstance().getvView().setVisibility(LinearLayout.GONE);
                }
            }

            PhotoViewAttacher photoAttacher;
            photoAttacher = new PhotoViewAttacher(VariabiliStaticheAlbum.getInstance().getImgAlbum());
            photoAttacher.update();
        }
    }

    private void PlayVideo() {
        try {
            VariabiliStaticheAlbum.getInstance().getvView().start();

            StaVedendo=true;
        } catch (Exception ignored) {

        }
    }

    private static void StopVideo() {
        if (StaVedendo) {
            VariabiliStaticheAlbum.getInstance().getvView().pause();
            StaVedendo = false;
        }
    }

}
