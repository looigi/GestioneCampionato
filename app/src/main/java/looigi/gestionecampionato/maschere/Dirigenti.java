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
import looigi.gestionecampionato.adapter.AdapterDirigenti;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheDirigenti;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dati.VariabiliStaticheMain;
import looigi.gestionecampionato.dati.VariabiliStaticheUtenti;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.db_remoto.DBRemotoDirigenti;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.upload_download.CropUtility;
import looigi.gestionecampionato.utilities.Utility;

import static android.app.Activity.RESULT_OK;

public class Dirigenti extends android.support.v4.app.Fragment {
    private static String TAG = NomiMaschere.getInstance().getDirigenti();
    private static CropUtility cu;
    private android.support.v4.app.Fragment a;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        VariabiliStaticheDirigenti.getInstance().setContext(this.getActivity());
        a=this;
        cu = new CropUtility();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.dirigenti, container, false));
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
        //                VariabiliStaticheDirigenti.getInstance().getImgAllenatore(),
        //                resultUri,
        //                NomiMaschere.getInstance().getAllenatoriPerTitolo(),
        //                VariabiliStaticheDirigenti.getInstance().getTxtId().getText().toString());
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
                    VariabiliStaticheDirigenti.getInstance().getImgDirigente(),
                    resultUri,
                    NomiMaschere.getInstance().getDirigentiPerTitolo(),
                    VariabiliStaticheDirigenti.getInstance().getTxtId().getText().toString());
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();
        }
    }

    private void initializeGraphic() {
        Context context = VariabiliStaticheGlobali.getInstance().getContext();
        View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            VariabiliStaticheDirigenti.getInstance().setRlMaschera((RelativeLayout) view.findViewById(R.id.layMascheraModUtenti));
            VariabiliStaticheDirigenti.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            VariabiliStaticheDirigenti.getInstance().setTxtId((TextView) view.findViewById(R.id.txtId));
            VariabiliStaticheDirigenti.getInstance().setEdtCognome((EditText) view.findViewById(R.id.edtCognome));
            VariabiliStaticheDirigenti.getInstance().setEdtNome((EditText) view.findViewById(R.id.edtNome));
            VariabiliStaticheDirigenti.getInstance().setEdtEMail((EditText) view.findViewById(R.id.edtEMail));
            VariabiliStaticheDirigenti.getInstance().setEdttelefono((EditText) view.findViewById(R.id.edtTelefono));
            VariabiliStaticheDirigenti.getInstance().setImgDirigente((ImageView) view.findViewById(R.id.imgDirigenteMod));

            VariabiliStaticheDirigenti.getInstance().setCmdOk((Button) view.findViewById(R.id.cmdOkDirigenti));
            VariabiliStaticheDirigenti.getInstance().setCmdAnnulla((Button) view.findViewById(R.id.cmdAnnullaDirigenti));
            VariabiliStaticheDirigenti.getInstance().setCmdElimina((Button) view.findViewById(R.id.cmdEliminaDirigenti));

            VariabiliStaticheDirigenti.getInstance().setSpnCategorie((Spinner) view.findViewById(R.id.spnCategorie));
            VariabiliStaticheDirigenti.getInstance().setLstDirigenti((ListView) view.findViewById(R.id.lstvDirigenti));

            /* Utility.getInstance().SettaColoreSceltaCategoria(view);

            TextView txtAllTit = (TextView) view.findViewById(R.id.txtDirigentiTit);
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
            VariabiliStaticheDirigenti.getInstance().setImgScegliFoto((ImageView) view.findViewById(R.id.imgSalvaImmagine));
            VariabiliStaticheDirigenti.getInstance().setImgEliminaFoto((ImageView) view.findViewById(R.id.imgEliminaImmagine));
            VariabiliStaticheDirigenti.getInstance().setImgRefreshFoto((ImageView) view.findViewById(R.id.imgRefreshImmagine));

            VariabiliStaticheDirigenti.getInstance().getImgScegliFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    cu.SceglieFotoDaGalleria(a);
                }
            });

            VariabiliStaticheDirigenti.getInstance().getImgRefreshFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String NomeSquadra = VariabiliStaticheGlobali.getInstance().getNomeSquadra();
                    String id = VariabiliStaticheDirigenti.getInstance().getTxtId().getText().toString();
                    String Percorso=VariabiliStaticheGlobali.getInstance().PercorsoDIR + "/"+NomeSquadra+"/Dirigenti/";

                    Percorso += VariabiliStaticheGlobali.getInstance().getAnnoInCorso()+"_"+id+".jpg";

                    File file = new File(Percorso);
                    if (file.exists()) {
                        file.delete();
                    }

                    Utility.getInstance().PrendeImmagineDirigente(id, VariabiliStaticheDirigenti.getInstance().getImgDirigente());
                }
            });

            VariabiliStaticheDirigenti.getInstance().getImgEliminaFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String id = VariabiliStaticheDirigenti.getInstance().getTxtId().getText().toString();
                    String NomeFile = Integer.toString(VariabiliStaticheGlobali.getInstance().getAnnoInCorso()) +"_"+ id;

                    DialogElimina.getInstance().show(VariabiliStaticheDirigenti.getInstance().getContext(),
                            "Si vuole eliminare l'immagine ?",
                            NomeFile,
                            NomiMaschere.getInstance().getAllenatori()+"Immagine");
                }
            });
            // Carica nuova foto / Elimina foto

            VariabiliStaticheDirigenti.getInstance().idCategoriaScelta=-1;

            if (VariabiliStaticheDirigenti.getInstance().getCategorie()==null) {
                DBRemotoCategorie dbr = new DBRemotoCategorie();
                dbr.RitornaCategorie(context, TAG);
            } else {
                RiempieListaCategorie();
            }

            Button cmdScegliCat = view.findViewById(R.id.cmdScegliCat);
            cmdScegliCat.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (VariabiliStaticheDirigenti.getInstance().idCategoriaScelta>-1) {
                        DBRemotoDirigenti dbr = new DBRemotoDirigenti();
                        dbr.RitornaDirigentiCategoria(VariabiliStaticheDirigenti.getInstance().getContext(),
                                Integer.toString(VariabiliStaticheDirigenti.getInstance().idCategoriaScelta),
                                TAG);
                    }
                }
            });
        }
    }

    public static void RiempieListaCategorie() {
        if (VariabiliStaticheDirigenti.getInstance().getCategorie() != null) {
            final ArrayAdapter<String> adapterCategorie = new ArrayAdapter<String>(
                    VariabiliStaticheGlobali.getInstance().getContext(), R.layout.spinner_item_piccolo,
                    VariabiliStaticheDirigenti.getInstance().getCategorie());
            adapterCategorie.setDropDownViewResource(R.layout.spinner_item_piccolo);
            VariabiliStaticheDirigenti.getInstance().getSpnCategorie().setAdapter(adapterCategorie);

            Utility.getInstance().CercaESettaStringaInSpinner(VariabiliStaticheDirigenti.getInstance().getSpnCategorie(),
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria1());
            VariabiliStaticheDirigenti.getInstance().idCategoriaScelta = Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1());

            int pos = Utility.getInstance().CercaESettaStringaInSpinner(VariabiliStaticheDirigenti.getInstance().getSpnCategorie(),
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().getDescCategoria1());
            if (pos>-1) {
                VariabiliStaticheDirigenti.getInstance().getSpnCategorie().setSelection(pos);
            }
            VariabiliStaticheDirigenti.getInstance().idCategoriaScelta=Integer.parseInt(VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdCategoria1());

            VariabiliStaticheDirigenti.getInstance().getSpnCategorie().setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int idCategoria=VariabiliStaticheDirigenti.getInstance().getIdCategorie().get(position);
                    VariabiliStaticheDirigenti.getInstance().idCategoriaScelta=idCategoria;

                    VariabiliStaticheUtenti.getInstance().setIdCategoriaScelta(VariabiliStaticheDirigenti.getInstance().idCategoriaScelta);
                    VariabiliStaticheGlobali.getInstance().getDatiUtente().setDescCategoria(VariabiliStaticheDirigenti.getInstance().getCategorie().get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }
    }

    public static void NuovoDirigente() {
        String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            VariabiliStaticheDirigenti.getInstance().getTxtId().setText("-1");
            VariabiliStaticheDirigenti.getInstance().getEdtCognome().setText("");
            VariabiliStaticheDirigenti.getInstance().getEdtNome().setText("");
            VariabiliStaticheDirigenti.getInstance().getEdtEMail().setText("");
            VariabiliStaticheDirigenti.getInstance().getEdttelefono().setText("");
            VariabiliStaticheDirigenti.getInstance().getImgDirigente().setImageBitmap(null);

            VariabiliStaticheDirigenti.getInstance().getRlMaschera().setVisibility(RelativeLayout.VISIBLE);
            //VariabiliStaticheDirigenti.getInstance().getCmdElimina().setVisibility(LinearLayout.GONE);

            VariabiliStaticheDirigenti.getInstance().getCmdOk().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String Cognome=VariabiliStaticheDirigenti.getInstance().getEdtCognome().getText().toString();
                    String Nome=VariabiliStaticheDirigenti.getInstance().getEdtNome().getText().toString();
                    String EMail=VariabiliStaticheDirigenti.getInstance().getEdtEMail().getText().toString();
                    String Telefono=VariabiliStaticheDirigenti.getInstance().getEdttelefono().getText().toString();

                    if (Cognome.isEmpty()) {
                        DialogMessaggio.getInstance().show(VariabiliStaticheDirigenti.getInstance().getContext(), "Inserire il cognome",
                                true, VariabiliStaticheGlobali.NomeApplicazione);
                    } else {
                        if (Nome.isEmpty()) {
                            DialogMessaggio.getInstance().show(VariabiliStaticheDirigenti.getInstance().getContext(), "Inserire il nome",
                                    true, VariabiliStaticheGlobali.NomeApplicazione);
                        } else {
                            //String id=VariabiliStaticheDirigenti.getInstance().getTxtId().getText().toString();

                            DBRemotoDirigenti dbr = new DBRemotoDirigenti();
                            dbr.SalvaDirigente(VariabiliStaticheDirigenti.getInstance().getContext(),
                                    Integer.toString(VariabiliStaticheDirigenti.getInstance().idCategoriaScelta),
                                    "-1", Cognome, Nome, EMail, Telefono, NomiMaschere.getInstance().getEventi());

                            VariabiliStaticheDirigenti.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
                        }
                    }
                }
            });

            VariabiliStaticheDirigenti.getInstance().getCmdAnnulla().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    VariabiliStaticheDirigenti.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
                }
            });
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContextPrincipale(),
                    "Non si hanno i permessi per inserire dirigenti", true, VariabiliStaticheGlobali.NomeApplicazione);
        }
    }

    public static void fillListViewDirigenti() {
        if (VariabiliStaticheDirigenti.getInstance().getDirigenti() != null) {
            VariabiliStaticheDirigenti.getInstance().setAdapterDirigenti(new AdapterDirigenti(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, VariabiliStaticheDirigenti.getInstance().getDirigenti()));
            VariabiliStaticheDirigenti.getInstance().getLstDirigenti().setAdapter(VariabiliStaticheDirigenti.getInstance().getAdapterDirigenti());
        }
    }
}
