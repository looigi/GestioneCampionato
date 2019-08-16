package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.adapter.AdapterAllenatori;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheAllenatori;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.dati.VariabiliStaticheUtenti;
import looigi.gestionecampionato.db_remoto.DBRemotoAllenatori;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.upload_download.CropUtility;
import looigi.gestionecampionato.utilities.Utility;

import static android.app.Activity.RESULT_OK;

public class Allenatori extends android.support.v4.app.Fragment {
    private static String TAG = NomiMaschere.getInstance().getAllenatori();
    private static CropUtility cu;
    private android.support.v4.app.Fragment a;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        VariabiliStaticheAllenatori.getInstance().setContext(this.getActivity());
        a=this;
        cu = new CropUtility();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.allenatori, container, false));
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
        //                VariabiliStaticheAllenatori.getInstance().getImgAllenatore(),
        //                resultUri,
        //                NomiMaschere.getInstance().getAllenatoriPerTitolo(),
        //                VariabiliStaticheAllenatori.getInstance().getTxtId().getText().toString());
        //    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
        //        Exception error = result.getError();
        //    }
        }
    }

    public static void SalvataggioImmagine(int resultCode, Intent data) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
            Uri resultUri = result.getUri();
            cu.SalvataggioImmagine(
                    VariabiliStaticheAllenatori.getInstance().getImgAllenatore(),
                    resultUri,
                    NomiMaschere.getInstance().getAllenatoriPerTitolo(),
                    VariabiliStaticheAllenatori.getInstance().getTxtId().getText().toString());
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();
        }
    }

    private void initializeGraphic() {
        Context context = VariabiliStaticheGlobali.getInstance().getContext();
        View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            VariabiliStaticheAllenatori.getInstance().setRlMaschera((RelativeLayout) view.findViewById(R.id.layMascheraModUtenti));
            VariabiliStaticheAllenatori.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            VariabiliStaticheAllenatori.getInstance().setTxtId((TextView) view.findViewById(R.id.txtId));
            VariabiliStaticheAllenatori.getInstance().setEdtCognome((EditText) view.findViewById(R.id.edtCognome));
            VariabiliStaticheAllenatori.getInstance().setEdtNome((EditText) view.findViewById(R.id.edtNome));
            VariabiliStaticheAllenatori.getInstance().setEdtEMail((EditText) view.findViewById(R.id.edtEMail));
            VariabiliStaticheAllenatori.getInstance().setEdttelefono((EditText) view.findViewById(R.id.edtTelefono));
            VariabiliStaticheAllenatori.getInstance().setImgAllenatore((ImageView) view.findViewById(R.id.imgAllenatoreMod));

            VariabiliStaticheAllenatori.getInstance().setCmdOk((Button) view.findViewById(R.id.cmdOkAllenatori));
            VariabiliStaticheAllenatori.getInstance().setCmdAnnulla((Button) view.findViewById(R.id.cmdAnnullaAllenatori));
            VariabiliStaticheAllenatori.getInstance().setCmdElimina((Button) view.findViewById(R.id.cmdEliminaAllenatori));

            VariabiliStaticheAllenatori.getInstance().setSpnCategorie((Spinner) view.findViewById(R.id.spnCategorie));
            VariabiliStaticheAllenatori.getInstance().setLstAllenatori((ListView) view.findViewById(R.id.lstvAllenatori));

            // Utility.getInstance().SettaColoreSceltaCategoria(view);

            /* TextView txtAllTit = (TextView) view.findViewById(R.id.txtAllenatoriTit);
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

            // Carica nuova foto / Elimina foto
            VariabiliStaticheAllenatori.getInstance().setImgScegliFoto((ImageView) view.findViewById(R.id.imgSalvaImmagine));
            VariabiliStaticheAllenatori.getInstance().setImgEliminaFoto((ImageView) view.findViewById(R.id.imgEliminaImmagine));
            VariabiliStaticheAllenatori.getInstance().setImgRefreshFoto((ImageView) view.findViewById(R.id.imgRefreshImmagine));

            VariabiliStaticheAllenatori.getInstance().getImgScegliFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    cu.SceglieFotoDaGalleria(a);
                }
            });

            VariabiliStaticheAllenatori.getInstance().getImgRefreshFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String id = VariabiliStaticheAllenatori.getInstance().getTxtId().getText().toString();
                    String Percorso=VariabiliStaticheGlobali.getInstance().PercorsoDIR + "/Allenatori/";

                    Percorso += VariabiliStaticheGlobali.getInstance().getAnnoInCorso()+"_"+id+".jpg";

                    File file = new File(Percorso);
                    if (file.exists()) {
                        file.delete();
                    }

                    Utility.getInstance().PrendeImmagineAllenatore(id, VariabiliStaticheAllenatori.getInstance().getImgAllenatore());
                }
            });

            VariabiliStaticheAllenatori.getInstance().getImgEliminaFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String id = VariabiliStaticheAllenatori.getInstance().getTxtId().getText().toString();
                    String NomeFile = Integer.toString(VariabiliStaticheGlobali.getInstance().getAnnoInCorso()) +"_"+ id;

                    DialogElimina.getInstance().show(VariabiliStaticheAllenatori.getInstance().getContext(),
                            "Si vuole eliminare l'immagine ?",
                            NomeFile,
                            NomiMaschere.getInstance().getAllenatori()+"Immagine");
                }
            });
            // Carica nuova foto / Elimina foto

            VariabiliStaticheAllenatori.getInstance().idCategoriaScelta=-1;

            if (VariabiliStaticheAllenatori.getInstance().getCategorie()==null) {
                DBRemotoCategorie dbr = new DBRemotoCategorie();
                dbr.RitornaCategorie(context, TAG);
            } else {
                RiempieListaCategorie();
            }

            Button cmdScegliCat = view.findViewById(R.id.cmdScegliCat);
            cmdScegliCat.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (VariabiliStaticheAllenatori.getInstance().idCategoriaScelta>-1) {
                        DBRemotoAllenatori dbr = new DBRemotoAllenatori();
                        dbr.RitornaAllenatoriCategoria(VariabiliStaticheAllenatori.getInstance().getContext(),
                                Integer.toString(VariabiliStaticheAllenatori.getInstance().idCategoriaScelta),
                                TAG);
                    }
                }
            });
        }
    }

    public static void RiempieListaCategorie() {
        if (VariabiliStaticheAllenatori.getInstance().getCategorie() != null) {
            final ArrayAdapter<String> adapterCategorie = new ArrayAdapter<String>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_piccolo,
                    VariabiliStaticheAllenatori.getInstance().getCategorie());
            adapterCategorie.setDropDownViewResource(R.layout.spinner_item_piccolo);
            VariabiliStaticheAllenatori.getInstance().getSpnCategorie().setAdapter(adapterCategorie);

            Utility.getInstance().CercaESettaStringaInSpinner(VariabiliStaticheAllenatori.getInstance().getSpnCategorie(),
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria1());
            VariabiliStaticheAllenatori.getInstance().idCategoriaScelta = Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1());

            int pos = Utility.getInstance().CercaESettaStringaInSpinner(VariabiliStaticheAllenatori.getInstance().getSpnCategorie(),
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria1());
            if (pos>-1) {
                VariabiliStaticheAllenatori.getInstance().getSpnCategorie().setSelection(pos);
            }
            VariabiliStaticheAllenatori.getInstance().idCategoriaScelta=Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1());

            VariabiliStaticheAllenatori.getInstance().getSpnCategorie().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int idCategoria=VariabiliStaticheAllenatori.getInstance().getIdCategorie().get(position);
                    VariabiliStaticheAllenatori.getInstance().idCategoriaScelta=idCategoria;

                    VariabiliStaticheUtenti.getInstance().setIdCategoriaScelta(VariabiliStaticheAllenatori.getInstance().idCategoriaScelta);
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().setDescCategoria(VariabiliStaticheAllenatori.getInstance().getCategorie().get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public static void NuovoAllenatore() {
        String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            VariabiliStaticheAllenatori.getInstance().getTxtId().setText("-1");
            VariabiliStaticheAllenatori.getInstance().getEdtCognome().setText("");
            VariabiliStaticheAllenatori.getInstance().getEdtNome().setText("");
            VariabiliStaticheAllenatori.getInstance().getEdtEMail().setText("");
            VariabiliStaticheAllenatori.getInstance().getEdttelefono().setText("");
            VariabiliStaticheAllenatori.getInstance().getImgAllenatore().setImageBitmap(null);

            VariabiliStaticheAllenatori.getInstance().getRlMaschera().setVisibility(RelativeLayout.VISIBLE);
            //VariabiliStaticheAllenatori.getInstance().getCmdElimina().setVisibility(LinearLayout.GONE);

            VariabiliStaticheAllenatori.getInstance().getCmdOk().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                String Cognome=VariabiliStaticheAllenatori.getInstance().getEdtCognome().getText().toString();
                String Nome=VariabiliStaticheAllenatori.getInstance().getEdtNome().getText().toString();
                String EMail=VariabiliStaticheAllenatori.getInstance().getEdtEMail().getText().toString();
                String Telefono=VariabiliStaticheAllenatori.getInstance().getEdttelefono().getText().toString();

                if (Cognome.isEmpty()) {
                    DialogMessaggio.getInstance().show(VariabiliStaticheAllenatori.getInstance().getContext(), "Inserire il cognome", true, VariabiliStaticheGlobali.NomeApplicazione);
                } else {
                    if (Nome.isEmpty()) {
                        DialogMessaggio.getInstance().show(VariabiliStaticheAllenatori.getInstance().getContext(), "Inserire il nome", true, VariabiliStaticheGlobali.NomeApplicazione);
                    } else {
                        //String id=VariabiliStaticheAllenatori.getInstance().getTxtId().getText().toString();

                        DBRemotoAllenatori dbr = new DBRemotoAllenatori();
                        dbr.SalvaAllenatore(VariabiliStaticheAllenatori.getInstance().getContext(),
                                Integer.toString(VariabiliStaticheAllenatori.getInstance().idCategoriaScelta),
                                "-1", Cognome, Nome, EMail, Telefono, NomiMaschere.getInstance().getAllenatori());

                        VariabiliStaticheAllenatori.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
                    }
                }
                }
            });

            VariabiliStaticheAllenatori.getInstance().getCmdAnnulla().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    VariabiliStaticheAllenatori.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
                }
            });
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContextPrincipale(),
                    "Non si hanno i permessi per inserire allenatori", true, VariabiliStaticheGlobali.NomeApplicazione);
        }
    }

    public static void fillListViewAllenatori() {
        if (VariabiliStaticheAllenatori.getInstance().getAllenatori() != null) {
            VariabiliStaticheAllenatori.getInstance().setAdapterAllenatori(new AdapterAllenatori(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, VariabiliStaticheAllenatori.getInstance().getAllenatori()));
            VariabiliStaticheAllenatori.getInstance().getLstAllenatori().setAdapter(VariabiliStaticheAllenatori.getInstance().getAdapterAllenatori());
        }
    }
}
