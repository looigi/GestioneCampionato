package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.content.Intent;
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

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.adapter.AdapterCategorie;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheCategorie;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.upload_download.CropUtility;
import looigi.gestionecampionato.utilities.Utility;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class Categorie extends android.support.v4.app.Fragment {
    private static String TAG= NomiMaschere.getInstance().getCategorie();
    private static CropUtility cu;
    private android.support.v4.app.Fragment a;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        VariabiliStaticheCategorie.getInstance().setContext(this.getActivity());
        a=this;
        cu = new CropUtility();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.categorie, container, false));
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
        //                VariabiliStaticheCategorie.getInstance().getImgCategoria(),
        //                resultUri,
        //                NomiMaschere.getInstance().getCategoriePerTitolo(),
        //                VariabiliStaticheCategorie.getInstance().getTxtId().getText().toString());
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
                    VariabiliStaticheCategorie.getInstance().getImgCategoria(),
                    resultUri,
                    NomiMaschere.getInstance().getCategoriePerTitolo(),
                    VariabiliStaticheCategorie.getInstance().getTxtId().getText().toString());
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();
        }
    }

    private void initializeGraphic() {
        Context context = VariabiliStaticheGlobali.getInstance().getContext();
        View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            VariabiliStaticheCategorie.getInstance().setTxtId((TextView) view.findViewById(R.id.txtId));

            VariabiliStaticheCategorie.getInstance().setRlMaschera((RelativeLayout) view.findViewById(R.id.layMascheraModUtenti));
            VariabiliStaticheCategorie.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            VariabiliStaticheCategorie.getInstance().setEdtCategoria((EditText) view.findViewById(R.id.edtCategoria));
            VariabiliStaticheCategorie.getInstance().setImgCategoria((ImageView) view.findViewById(R.id.imgCategoriaMod));

            VariabiliStaticheCategorie.getInstance().setCmdOk((Button) view.findViewById(R.id.cmdOkCategoria));
            VariabiliStaticheCategorie.getInstance().setCmdAnnulla((Button) view.findViewById(R.id.cmdAnnullaCategoria));
            VariabiliStaticheCategorie.getInstance().setCmdElimina((Button) view.findViewById(R.id.cmdEliminaCategoria));

            VariabiliStaticheCategorie.getInstance().setLstCategorie((ListView) view.findViewById(R.id.lstvCategoria));

            // Carica nuova foto / Elimina foto
            VariabiliStaticheCategorie.getInstance().setImgScegliFoto((ImageView) view.findViewById(R.id.imgSalvaImmagine));
            VariabiliStaticheCategorie.getInstance().setImgEliminaFoto((ImageView) view.findViewById(R.id.imgEliminaImmagine));
            VariabiliStaticheCategorie.getInstance().setImgRefreshFoto((ImageView) view.findViewById(R.id.imgRefreshImmagine));

            VariabiliStaticheCategorie.getInstance().getImgScegliFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    cu.SceglieFotoDaGalleria(a);
                }
            });

            VariabiliStaticheCategorie.getInstance().getImgRefreshFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String id = VariabiliStaticheCategorie.getInstance().getTxtId().getText().toString();
                    String Percorso=VariabiliStaticheGlobali.getInstance().PercorsoDIR + "/Categorie/";

                    Percorso += VariabiliStaticheGlobali.getInstance().getAnnoInCorso()+"_"+id+".jpg";

                    File file = new File(Percorso);
                    if (file.exists()) {
                        file.delete();
                    }

                    Utility.getInstance().PrendeImmagineCategoria(id, VariabiliStaticheCategorie.getInstance().getImgCategoria());
                }
            });

            VariabiliStaticheCategorie.getInstance().getImgEliminaFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String id = VariabiliStaticheCategorie.getInstance().getTxtId().getText().toString();
                    String NomeFile = Integer.toString(VariabiliStaticheGlobali.getInstance().getAnnoInCorso()) +"_"+ id;

                    DialogElimina.getInstance().show(VariabiliStaticheCategorie.getInstance().getContext(),
                            "Si vuole eliminare l'immagine ?",
                            NomeFile,
                            NomiMaschere.getInstance().getCategorie()+"Immagine");
                }
            });
            // Carica nuova foto / Elimina foto

            if (VariabiliStaticheCategorie.getInstance().getCategorie()==null) {
                DBRemotoCategorie dbr = new DBRemotoCategorie();
                dbr.RitornaCategorie(context, TAG);
            } else {
                fillListViewCategorie();
            }
        }
    }

    public static void NuovaCategoria() {
        String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            VariabiliStaticheCategorie.getInstance().getEdtCategoria().setText("");
            VariabiliStaticheCategorie.getInstance().getImgCategoria().setImageBitmap(null);

            VariabiliStaticheCategorie.getInstance().getRlMaschera().setVisibility(RelativeLayout.VISIBLE);
            //VariabiliStaticheCategorie.getInstance().getCmdElimina().setVisibility(LinearLayout.GONE);

            VariabiliStaticheCategorie.getInstance().getCmdOk().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String Categoria=VariabiliStaticheCategorie.getInstance().getEdtCategoria().getText().toString();

                    if (Categoria.isEmpty()) {
                        DialogMessaggio.getInstance().show(VariabiliStaticheCategorie.getInstance().getContext(), "Inserire la descrizione della categoria", true, VariabiliStaticheGlobali.NomeApplicazione);
                    } else {
                        DBRemotoCategorie dbr = new DBRemotoCategorie();
                        dbr.SalvaCategoria(VariabiliStaticheCategorie.getInstance().getContext(),
                                "-1",
                                Categoria, NomiMaschere.getInstance().getCategorie());

                        VariabiliStaticheCategorie.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
                    }
                }
            });

            VariabiliStaticheCategorie.getInstance().getCmdAnnulla().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    VariabiliStaticheCategorie.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
                }
            });
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContextPrincipale(),
                    "Non si hanno i permessi per inserire categorie", true, VariabiliStaticheGlobali.NomeApplicazione);
        }
    }

    public static void fillListViewCategorie() {
        if (VariabiliStaticheCategorie.getInstance().getCategorie() != null) {
            VariabiliStaticheCategorie.getInstance().setAdapterCategorie(new AdapterCategorie(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, VariabiliStaticheCategorie.getInstance().getCategorie()));
            VariabiliStaticheCategorie.getInstance().getLstCategorie().setAdapter(VariabiliStaticheCategorie.getInstance().getAdapterCategorie());
        }
    }

}
