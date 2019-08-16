package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.adapter.AdapterArbitri;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheArbitri;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.db_remoto.DBRemotoArbitri;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.upload_download.CropUtility;
import looigi.gestionecampionato.utilities.Utility;

import static android.app.Activity.RESULT_OK;

public class Arbitri extends android.support.v4.app.Fragment {
    private static String TAG = NomiMaschere.getInstance().getArbitri();
    private static CropUtility cu;
    private android.support.v4.app.Fragment a;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        VariabiliStaticheArbitri.getInstance().setContext(this.getActivity());
        a=this;
        cu = new CropUtility();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.arbitri, container, false));
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
        //                VariabiliStaticheArbitri.getInstance().getImgAllenatore(),
        //                resultUri,
        //                NomiMaschere.getInstance().getAllenatoriPerTitolo(),
        //                VariabiliStaticheArbitri.getInstance().getTxtId().getText().toString());
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
                    VariabiliStaticheArbitri.getInstance().getImgArbitro(),
                    resultUri,
                    NomiMaschere.getInstance().getArbitriPerTitolo(),
                    VariabiliStaticheArbitri.getInstance().getTxtId().getText().toString());
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();
        }
    }

    private void initializeGraphic() {
        Context context = VariabiliStaticheGlobali.getInstance().getContext();
        View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            VariabiliStaticheArbitri.getInstance().setRlMaschera((RelativeLayout) view.findViewById(R.id.layMascheraModUtenti));
            VariabiliStaticheArbitri.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            VariabiliStaticheArbitri.getInstance().setTxtId((TextView) view.findViewById(R.id.txtId));
            VariabiliStaticheArbitri.getInstance().setEdtCognome((EditText) view.findViewById(R.id.edtCognome));
            VariabiliStaticheArbitri.getInstance().setEdtNome((EditText) view.findViewById(R.id.edtNome));
            VariabiliStaticheArbitri.getInstance().setEdtMail((EditText) view.findViewById(R.id.edtEMail));
            VariabiliStaticheArbitri.getInstance().setEdtTelefono((EditText) view.findViewById(R.id.edtTelefono));
            VariabiliStaticheArbitri.getInstance().setImgArbitro((ImageView) view.findViewById(R.id.imgArbitroMod));

            VariabiliStaticheArbitri.getInstance().setCmdOk((Button) view.findViewById(R.id.cmdOkArbitri));
            VariabiliStaticheArbitri.getInstance().setCmdAnnulla((Button) view.findViewById(R.id.cmdAnnullaArbitri));
            VariabiliStaticheArbitri.getInstance().setCmdElimina((Button) view.findViewById(R.id.cmdEliminaArbitri));

            VariabiliStaticheArbitri.getInstance().setLstvArbitri((ListView) view.findViewById(R.id.lstvArbitri));

            /* TextView txtAllTit = (TextView) view.findViewById(R.id.txtArbitriTit);
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
            VariabiliStaticheArbitri.getInstance().setImgScegliFoto((ImageView) view.findViewById(R.id.imgSalvaImmagine));
            VariabiliStaticheArbitri.getInstance().setImgEliminaFoto((ImageView) view.findViewById(R.id.imgEliminaImmagine));
            VariabiliStaticheArbitri.getInstance().setImgRefreshFoto((ImageView) view.findViewById(R.id.imgRefreshImmagine));

            VariabiliStaticheArbitri.getInstance().getImgScegliFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    cu.SceglieFotoDaGalleria(a);
                }
            });

            VariabiliStaticheArbitri.getInstance().getImgRefreshFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String id = VariabiliStaticheArbitri.getInstance().getTxtId().getText().toString();
                    String Percorso=VariabiliStaticheGlobali.getInstance().PercorsoDIR + "/Arbitri/";

                    Percorso += VariabiliStaticheGlobali.getInstance().getAnnoInCorso()+"_"+id+".jpg";

                    File file = new File(Percorso);
                    if (file.exists()) {
                        file.delete();
                    }

                    Utility.getInstance().PrendeImmagineArbitro(id, VariabiliStaticheArbitri.getInstance().getImgArbitro());
                }
            });

            VariabiliStaticheArbitri.getInstance().getImgEliminaFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String id = VariabiliStaticheArbitri.getInstance().getTxtId().getText().toString();
                    String NomeFile = Integer.toString(VariabiliStaticheGlobali.getInstance().getAnnoInCorso()) +"_"+ id;

                    DialogElimina.getInstance().show(VariabiliStaticheArbitri.getInstance().getContext(),
                            "Si vuole eliminare l'immagine ?",
                            NomeFile,
                            NomiMaschere.getInstance().getAllenatori()+"Immagine");
                }
            });
            // Carica nuova foto / Elimina foto

            VariabiliStaticheArbitri.getInstance().idArbitroScelto=-1;

            if (VariabiliStaticheArbitri.getInstance().getArbitri()==null) {
                DBRemotoArbitri dbr = new DBRemotoArbitri();
                dbr.RitornaArbitri(context, TAG);
            } else {
                fillListViewArbitri();
            }
        }
    }

    public static void NuovoArbitro() {
        String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            VariabiliStaticheArbitri.getInstance().getTxtId().setText("-1");
            VariabiliStaticheArbitri.getInstance().getEdtCognome().setText("");
            VariabiliStaticheArbitri.getInstance().getEdtNome().setText("");
            VariabiliStaticheArbitri.getInstance().getEdtMail().setText("");
            VariabiliStaticheArbitri.getInstance().getEdtTelefono().setText("");
            VariabiliStaticheArbitri.getInstance().getImgArbitro().setImageBitmap(null);

            VariabiliStaticheArbitri.getInstance().getRlMaschera().setVisibility(RelativeLayout.VISIBLE);

            VariabiliStaticheArbitri.getInstance().getCmdOk().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String Cognome=VariabiliStaticheArbitri.getInstance().getEdtCognome().getText().toString();
                    String Nome=VariabiliStaticheArbitri.getInstance().getEdtNome().getText().toString();
                    String EMail=VariabiliStaticheArbitri.getInstance().getEdtMail().getText().toString();
                    String Telefono=VariabiliStaticheArbitri.getInstance().getEdtTelefono().getText().toString();

                    if (Cognome.isEmpty()) {
                        DialogMessaggio.getInstance().show(VariabiliStaticheArbitri.getInstance().getContext(), "Inserire il cognome",
                                true, VariabiliStaticheGlobali.NomeApplicazione);
                    } else {
                        if (Nome.isEmpty() && !Cognome.replace(" ","").toUpperCase().equals("AUTOARBITRAGGIO")) {
                            DialogMessaggio.getInstance().show(VariabiliStaticheArbitri.getInstance().getContext(), "Inserire il nome",
                                    true, VariabiliStaticheGlobali.NomeApplicazione);
                        } else {
                            //String id=VariabiliStaticheArbitri.getInstance().getTxtId().getText().toString();

                            DBRemotoArbitri dbr = new DBRemotoArbitri();
                            dbr.SalvaArbitro(VariabiliStaticheArbitri.getInstance().getContext(),
                                    Integer.toString(VariabiliStaticheArbitri.getInstance().idArbitroScelto),
                                    "-1", Cognome, Nome, EMail, Telefono, NomiMaschere.getInstance().getAllenatori());

                            VariabiliStaticheArbitri.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
                        }
                    }
                }
            });

            VariabiliStaticheArbitri.getInstance().getCmdAnnulla().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    VariabiliStaticheArbitri.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
                }
            });
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContextPrincipale(),
                    "Non si hanno i permessi per inserire Arbitri", true, VariabiliStaticheGlobali.NomeApplicazione);
        }
    }

    public static void fillListViewArbitri() {
        if (VariabiliStaticheArbitri.getInstance().getArbitri() != null) {
            VariabiliStaticheArbitri.getInstance().setAdapterArbitri(new AdapterArbitri(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, VariabiliStaticheArbitri.getInstance().getArbitri()));
            VariabiliStaticheArbitri.getInstance().getLstvArbitri().setAdapter(VariabiliStaticheArbitri.getInstance().getAdapterArbitri());
        }
    }
}
