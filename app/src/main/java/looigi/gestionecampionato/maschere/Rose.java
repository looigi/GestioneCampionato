package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.adapter.AdapterGiocatori;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.dati.VariabiliStaticheRose;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.db_remoto.DBRemotoGiocatori;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.upload_download.CropUtility;
import looigi.gestionecampionato.utilities.Utility;

import static android.app.Activity.RESULT_OK;

public class Rose extends android.support.v4.app.Fragment {
    // private Context context;
    private static String TAG= NomiMaschere.getInstance().getRose();
    private static CropUtility cu;
    private android.support.v4.app.Fragment a;
    // private static vv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        VariabiliStaticheRose vv = VariabiliStaticheRose.getInstance();
        vv.setContext(this.getActivity());
        a=this;
        cu = new CropUtility();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.rose, container, false));
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == cu.PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            cu.performCrop(uri);
        //} else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
        //    CropImage.ActivityResult result = CropImage.getActivityResult(data);
        //    if (resultCode == RESULT_OK) {
        //        Uri resultUri = result.getUri();
        //        cu.SalvataggioFileMultimediale(
        //                vv.getImgGiocatore(),
        //                resultUri,
        //                NomiMaschere.getInstance().getRosePerTitolo(),
        //                vv.getTxtId().getText().toString());
        //    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
        //        Exception error = result.getError();
        //    }
        }
    }

    public static void SalvataggioImmagine(int resultCode, Intent data) {
        VariabiliStaticheRose vv = VariabiliStaticheRose.getInstance();
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            Uri resultUri = result.getUri();
            cu.SalvataggioImmagine(
                    vv.getImgGiocatore(),
                    resultUri,
                    NomiMaschere.getInstance().getRosePerTitolo(),
                    vv.getTxtId().getText().toString());
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();
        }
    }

    private void initializeGraphic() {
        final VariabiliStaticheRose vv = VariabiliStaticheRose.getInstance();
        Context context = VariabiliStaticheGlobali.getInstance().getContext();
        View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            vv.setRlMaschera((RelativeLayout) view.findViewById(R.id.layMascheraModUtenti));
            vv.getRlMaschera().setVisibility(RelativeLayout.GONE);

            vv.setTxtId((TextView) view.findViewById(R.id.txtId));
            vv.setEdtCognome((EditText) view.findViewById(R.id.edtCognome));
            vv.setEdtNome((EditText) view.findViewById(R.id.edtNome));
            vv.setEdtEMail((EditText) view.findViewById(R.id.edtEMail));
            vv.setEdttelefono((EditText) view.findViewById(R.id.edtTelefono));
            vv.setEdtSoprannome((EditText) view.findViewById(R.id.edtSoprannome));
            vv.setEdtDataNascita((EditText) view.findViewById(R.id.edtDataDiNascita));
            vv.setEdtIndirizzo((EditText) view.findViewById(R.id.edtIndirizzo));
            vv.setEdtCodFisc((EditText) view.findViewById(R.id.edtCF));
            vv.setEdtCitta((EditText) view.findViewById(R.id.edtCitta));
            vv.setEdtMatricola((EditText) view.findViewById(R.id.edtMatricola));
            vv.setEdtNumeroMaglia((EditText) view.findViewById(R.id.edtNumeroMaglia));
            vv.setOptMaschio((RadioButton) view.findViewById(R.id.optMaschio));
            vv.setOptFemmina((RadioButton) view.findViewById(R.id.optFemmina));

            vv.setImgGiocatore((ImageView) view.findViewById(R.id.imgGiocatoreMod));

            vv.setCmdOk((Button) view.findViewById(R.id.cmdOkGiocatore));
            vv.setCmdAnnulla((Button) view.findViewById(R.id.cmdAnnullaGiocatore));
            vv.setCmdElimina((Button) view.findViewById(R.id.cmdEliminaGiocatore));

            vv.setSpnCategorie((Spinner) view.findViewById(R.id.spnCategorie));
            vv.setSpnCategorie1((Spinner) view.findViewById(R.id.spnCategorie1));
            vv.setSpnCategorie2((Spinner) view.findViewById(R.id.spnCategorie2));
            vv.setSpnCategorie3((Spinner) view.findViewById(R.id.spnCategorie3));
            vv.setLstGiocatori((ListView) view.findViewById(R.id.lstvGiocatori));
            vv.setSpnRuoli((Spinner) view.findViewById(R.id.spnRuolo));

            // Carica nuova foto / Elimina foto
            vv.setImgScegliFoto((ImageView) view.findViewById(R.id.imgSalvaImmagine));
            vv.setImgEliminaFoto((ImageView) view.findViewById(R.id.imgEliminaImmagine));
            vv.setImgRefreshFoto((ImageView) view.findViewById(R.id.imgRefreshImmagine));

            vv.getImgScegliFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    cu.SceglieFotoDaGalleria(a);
                }
            });

            vv.getImgRefreshFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String NomeSquadra = VariabiliStaticheGlobali.getInstance().getNomeSquadra();
                    String id = vv.getTxtId().getText().toString();
                    String Percorso=VariabiliStaticheGlobali.getInstance().PercorsoDIR + "/"+NomeSquadra+"/Giocatori/";

                    Percorso += VariabiliStaticheGlobali.getInstance().getAnnoInCorso()+"_"+id+".jpg";

                    File file = new File(Percorso);
                    if (file.exists()) {
                        file.delete();
                    }

                    Utility.getInstance().PrendeImmagineGiocatore(id, vv.getImgGiocatore());
                }
            });

            vv.getImgEliminaFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String id = vv.getTxtId().getText().toString();
                    String NomeFile = Integer.toString(VariabiliStaticheGlobali.getInstance().getAnnoInCorso()) +"_"+ id;

                    DialogElimina.getInstance().show(vv.getContext(),
                            "Si vuole eliminare l'immagine ?",
                            NomeFile,
                            NomiMaschere.getInstance().getRose()+"Immagine");
                }
            });
            // Carica nuova foto / Elimina foto

            vv.idCategoriaScelta1 =-1;
            vv.idGiocatoreScelto=-1;

            // if (vv.getCategorie1()==null) {
                DBRemotoCategorie dbr = new DBRemotoCategorie();
                dbr.RitornaCategorie(context, TAG);
            // } else {
            //     RiempieListaCategorie();
            // }

            Button cmdScegliCat = view.findViewById(R.id.cmdScegliCat);
            cmdScegliCat.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (vv.idCategoriaScelta >-1) {
                        DBRemotoGiocatori dbr = new DBRemotoGiocatori();
                        dbr.RitornaGiocatoriCategoria(vv.getContext(),
                                Integer.toString(vv.idCategoriaScelta),
                                TAG);
                    }
                }
            });

            /* Utility.getInstance().SettaColoreSceltaCategoria(view);

            TextView txtAllTit = (TextView) view.findViewById(R.id.txtGiocatoriTit);
            Utility.getInstance().SettaColoreSfondoPerNomeSquadra(txtAllTit);
            if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraCastelVerde)) {
                txtAllTit.setTextColor(Color.BLACK);
            } else {
                if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraPonteDiNona)) {
                    txtAllTit.setTextColor(Color.WHITE);
                }
            }

            RelativeLayout layMascheraModUtenti=view.findViewById(R.id.layMascheraModUtenti);
            if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraCastelVerde)) {
                layMascheraModUtenti.setBackgroundResource(R.drawable.bordo_arrotondato_verde_chiaro);
            } else {
                if (VariabiliStaticheMain.getInstance().getSquadra().equals(VariabiliStaticheGlobali.NomeSquadraPonteDiNona)) {
                    layMascheraModUtenti.setBackgroundResource(R.drawable.bordo_arrotondato_rosso_chiaro);
                }
            } */
        }
    }

    public static void RiempieListaCategorie() {
        final VariabiliStaticheRose vv = VariabiliStaticheRose.getInstance();

        if (vv.getCategorie() != null) {
            ArrayAdapter<String> adapterCategorie = new ArrayAdapter<String>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_piccolo, vv.getCategorie());
            adapterCategorie.setDropDownViewResource(R.layout.spinner_item_piccolo);

            vv.getSpnCategorie().setAdapter(adapterCategorie);
            vv.getSpnCategorie1().setAdapter(adapterCategorie);
            vv.getSpnCategorie2().setAdapter(adapterCategorie);
            vv.getSpnCategorie3().setAdapter(adapterCategorie);

            Utility.getInstance().CercaESettaStringaInSpinner(vv.getSpnCategorie(),
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria1());
            // if (pos>-1) {
                vv.idCategoriaScelta = Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1());
            // }

            // int pos = Utility.getInstance().CercaESettaStringaInSpinner(vv.getSpnCategorie2(),
            //         VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria2());
            // if (pos>-1) {
            //     vv.idCategoriaScelta2 = vv.getIdCategorie2().get(pos);
            // }

            // pos = Utility.getInstance().CercaESettaStringaInSpinner(vv.getSpnCategorie3(),
            //         VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria3());
            // if (pos>-1) {
            //     vv.idCategoriaScelta3 = vv.getIdCategorie3().get(pos);
            // }

            vv.getSpnCategorie().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        int idCategoria=vv.getIdCategorie1().get(position);
                        vv.idCategoriaScelta1=idCategoria;
                    } catch (Exception ignored) {
                        vv.idCategoriaScelta1 = 0;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            vv.getSpnCategorie1().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        int idCategoria=vv.getIdCategorie1().get(position);
                        vv.idCategoriaScelta1=idCategoria;
                    } catch (Exception ignored) {
                        vv.idCategoriaScelta1 = 0;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            vv.getSpnCategorie2().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        int idCategoria=vv.getIdCategorie1().get(position);
                        vv.idCategoriaScelta2=idCategoria;
                    } catch (Exception ignored) {
                        vv.idCategoriaScelta2 = 0;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });

            vv.getSpnCategorie3().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        int idCategoria = vv.getIdCategorie1().get(position);
                        vv.idCategoriaScelta3 = idCategoria;
                    } catch (Exception ignored) {
                        vv.idCategoriaScelta3 = 0;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public static void NuovoGiocatore() {
        final VariabiliStaticheRose vv = VariabiliStaticheRose.getInstance();
        String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            vv.getTxtId().setText("-1");
            vv.getEdtCognome().setText("");
            vv.getEdtNome().setText("");
            vv.getEdtEMail().setText("");
            vv.getEdttelefono().setText("");
            vv.getImgGiocatore().setImageBitmap(null);
            vv.getEdtSoprannome().setText("");
            vv.getEdtDataNascita().setText("");
            vv.getEdtIndirizzo().setText("");
            vv.getEdtCodFisc().setText("");
            vv.getEdtCitta().setText("");
            vv.getEdtMatricola().setText("");
            vv.getEdtNumeroMaglia().setText("");

            vv.getRlMaschera().setVisibility(RelativeLayout.VISIBLE);

            vv.getCmdOk().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String Cognome=vv.getEdtCognome().getText().toString();
                    String Nome=vv.getEdtNome().getText().toString();
                    String EMail=vv.getEdtEMail().getText().toString();
                    String Telefono=vv.getEdttelefono().getText().toString();
                    String Soprannome=vv.getEdtSoprannome().getText().toString();
                    String DataNascita=vv.getEdtDataNascita().getText().toString();
                    String Indirizzo=vv.getEdtIndirizzo().getText().toString();
                    String CodFisc=vv.getEdtCodFisc().getText().toString();
                    String Citta=vv.getEdtCitta().getText().toString();
                    String Matricola=vv.getEdtMatricola().getText().toString();
                    String NumeroMaglia=vv.getEdtNumeroMaglia().getText().toString();
                    String idRuolo="0"; //=Integer.toString(vv.idRuoloScelto);

                    int pos=-1;
                    for (String r : vv.getRuoli()) {
                        pos++;
                        if (r.contains(vv.getSpnRuoli().getSelectedItem().toString().trim())) {
                            String[] rr = r.split(";",-1);
                            idRuolo=rr[0];
                            break;
                        }
                    }
                    String Maschio="N";
                    if (vv.getOptMaschio().isChecked()) {
                        Maschio="S";
                    }

                    if (Cognome.isEmpty()) {
                        DialogMessaggio.getInstance().show(vv.getContext(), "Inserire il cognome", true, VariabiliStaticheGlobali.NomeApplicazione);
                    } else {
                        if (Nome.isEmpty()) {
                            DialogMessaggio.getInstance().show(vv.getContext(), "Inserire il nome", true, VariabiliStaticheGlobali.NomeApplicazione);
                        } else {
                            DBRemotoGiocatori dbr = new DBRemotoGiocatori();
                            dbr.SalvaGiocatore(vv.getContext(),
                                    Integer.toString(vv.idCategoriaScelta1),
                                    "-1",
                                    idRuolo,
                                    Cognome,
                                    Nome,
                                    EMail,
                                    Telefono,
                                    Soprannome,
                                    DataNascita,
                                    Indirizzo,
                                    CodFisc,
                                    Maschio,
                                    Citta,
                                    Matricola,
                                    NumeroMaglia,
                                    "",
                                    NomiMaschere.getInstance().getRose(),
                                    Integer.toString(vv.idCategoriaScelta2),
                                    Integer.toString(vv.idCategoriaScelta3)
                                    );

                            vv.getRlMaschera().setVisibility(RelativeLayout.GONE);
                        }
                    }
                }
            });

            vv.getCmdAnnulla().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    vv.getRlMaschera().setVisibility(RelativeLayout.GONE);
                }
            });
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContextPrincipale(),
                    "Non si hanno i permessi per inserire giocatori", true, VariabiliStaticheGlobali.NomeApplicazione);
        }
    }

    public static void fillListViewGiocatori() {
        VariabiliStaticheRose vv = VariabiliStaticheRose.getInstance();

        if (vv.getGiocatori() != null) {
            vv.setAdapterGiocatori(new AdapterGiocatori(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, vv.getGiocatori()));
            vv.getLstGiocatori().setAdapter(vv.getAdapterGiocatori());
        }
    }

    public static void fillSpinnersRuoli(final List<String> Ruoli) {
        VariabiliStaticheRose vv = VariabiliStaticheRose.getInstance();

        if (Ruoli != null) {
            vv.setRuoli(Ruoli);

            List<String> Ruoli2 = new ArrayList<>();
            for (String r : Ruoli)  {
                String rr[] = r.split(";",-1);
                Ruoli2.add(rr[1]);
            }
            final ArrayAdapter<String> adapterRuoli = new ArrayAdapter<>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_piccolo, Ruoli2);
            adapterRuoli.setDropDownViewResource(R.layout.spinner_item_piccolo);
            vv.getSpnRuoli().setAdapter(adapterRuoli);

            vv.getSpnRuoli().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //String r[] = Ruoli.get(position).split(";",-1);
                   // vv.idRuoloScelto=Integer.parseInt(r[0]);
                    //vv.RuoloPerModifica=r[1];
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }
}
