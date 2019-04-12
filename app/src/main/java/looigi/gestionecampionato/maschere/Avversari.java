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

import com.google.android.gms.maps.model.LatLng;
import looigi.gestionecampionato.R;
import looigi.gestionecampionato.adapter.AdapterAvversari;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheAvversari;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.db_remoto.DBRemotoAvversari;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.upload_download.CropUtility;
import looigi.gestionecampionato.utilities.Utility;
import com.theartofdev.edmodo.cropper.CropImage;

import static android.app.Activity.RESULT_OK;

public class Avversari extends android.support.v4.app.Fragment {
    private Context context;
    private static String TAG= NomiMaschere.getInstance().getAvversari();
    private static CropUtility cu;
    private android.support.v4.app.Fragment a;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        VariabiliStaticheAvversari.getInstance().setContext(this.getActivity());
        a=this;
        cu = new CropUtility();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.avversari, container, false));
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
        //                VariabiliStaticheAvversari.getInstance().getImgAvversario(),
        //                resultUri,
        //                NomiMaschere.getInstance().getAvversariPerTitolo(),
        //                VariabiliStaticheAvversari.getInstance().getTxtId().getText().toString());
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
                    VariabiliStaticheAvversari.getInstance().getImgAvversario(),
                    resultUri,
                    NomiMaschere.getInstance().getAvversariPerTitolo(),
                    VariabiliStaticheAvversari.getInstance().getTxtId().getText().toString());
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            Exception error = result.getError();
        }
    }

    private void initializeGraphic() {
        final Context context = VariabiliStaticheGlobali.getInstance().getContext();
        View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            VariabiliStaticheAvversari.getInstance().setTxtId((TextView) view.findViewById(R.id.txtId));

            VariabiliStaticheAvversari.getInstance().setRlMaschera((RelativeLayout) view.findViewById(R.id.layMascheraModUtenti));
            VariabiliStaticheAvversari.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);

            VariabiliStaticheAvversari.getInstance().setRlMascheraStat((RelativeLayout) view.findViewById(R.id.layMascheraStat));
            VariabiliStaticheAvversari.getInstance().getRlMascheraStat().setVisibility(RelativeLayout.GONE);

            VariabiliStaticheAvversari.getInstance().setEdtAvversario((EditText) view.findViewById(R.id.edtAvversario));
            VariabiliStaticheAvversari.getInstance().setEdtCampo((EditText) view.findViewById(R.id.edtCampo));
            VariabiliStaticheAvversari.getInstance().setEdtIndirizzo((EditText) view.findViewById(R.id.edtIndirizzo));
            VariabiliStaticheAvversari.getInstance().setImgAvversario((ImageView) view.findViewById(R.id.imgAvversarioMod));

            VariabiliStaticheAvversari.getInstance().setCmdOk((Button) view.findViewById(R.id.cmdOkAvversario));
            VariabiliStaticheAvversari.getInstance().setCmdAnnulla((Button) view.findViewById(R.id.cmdAnnullaAvversario));
            VariabiliStaticheAvversari.getInstance().setCmdElimina((Button) view.findViewById(R.id.cmdEliminaAvversari));
            VariabiliStaticheAvversari.getInstance().setCmdtrovaCoord((Button) view.findViewById(R.id.cmdTrovaCoord));
            VariabiliStaticheAvversari.getInstance().setTxtCoord((TextView) view.findViewById(R.id.txtCoords));

            VariabiliStaticheAvversari.getInstance().setLstAvversari((ListView) view.findViewById(R.id.lstvAvversari));

            VariabiliStaticheAvversari.getInstance().setEdtRicerca((EditText) view.findViewById(R.id.edtSquadra));
            VariabiliStaticheAvversari.getInstance().getEdtRicerca().setText("");

            VariabiliStaticheAvversari.getInstance().setImgAvversarioStat((ImageView) view.findViewById(R.id.imgAvversarioStat));
            VariabiliStaticheAvversari.getInstance().setCmdStatAvversario((Button) view.findViewById(R.id.cmdOkStatAvversario));
            VariabiliStaticheAvversari.getInstance().getCmdStatAvversario().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    VariabiliStaticheAvversari.getInstance().getRlMascheraStat().setVisibility(RelativeLayout.GONE);
                }
            });

            VariabiliStaticheAvversari.getInstance().setTxtStat1((TextView) view.findViewById(R.id.txtStat1));
            VariabiliStaticheAvversari.getInstance().setTxtStat2((TextView) view.findViewById(R.id.txtStat2));
            VariabiliStaticheAvversari.getInstance().setTxtStat3((TextView) view.findViewById(R.id.txtStat3));
            VariabiliStaticheAvversari.getInstance().setTxtStat4((TextView) view.findViewById(R.id.txtStat4));
            VariabiliStaticheAvversari.getInstance().setTxtStat5((TextView) view.findViewById(R.id.txtStat5));
            VariabiliStaticheAvversari.getInstance().setTxtStat6((TextView) view.findViewById(R.id.txtStat6));
            VariabiliStaticheAvversari.getInstance().setTxtStat7((TextView) view.findViewById(R.id.txtStat7));
            VariabiliStaticheAvversari.getInstance().setTxtStat8((TextView) view.findViewById(R.id.txtStat8));
            VariabiliStaticheAvversari.getInstance().setTxtStat9((TextView) view.findViewById(R.id.txtStat9));
            VariabiliStaticheAvversari.getInstance().setTxtStat10((TextView) view.findViewById(R.id.txtStat10));
            VariabiliStaticheAvversari.getInstance().setTxtStat11((TextView) view.findViewById(R.id.txtStat11));
            VariabiliStaticheAvversari.getInstance().setTxtStat12((TextView) view.findViewById(R.id.txtStat12));
            VariabiliStaticheAvversari.getInstance().setTxtStat13((TextView) view.findViewById(R.id.txtStat13));

            VariabiliStaticheAvversari.getInstance().setTxtStat1Anno((TextView) view.findViewById(R.id.txtStat1Anno));
            VariabiliStaticheAvversari.getInstance().setTxtStat2Anno((TextView) view.findViewById(R.id.txtStat2Anno));
            VariabiliStaticheAvversari.getInstance().setTxtStat3Anno((TextView) view.findViewById(R.id.txtStat3Anno));
            VariabiliStaticheAvversari.getInstance().setTxtStat4Anno((TextView) view.findViewById(R.id.txtStat4Anno));
            VariabiliStaticheAvversari.getInstance().setTxtStat5Anno((TextView) view.findViewById(R.id.txtStat5Anno));
            VariabiliStaticheAvversari.getInstance().setTxtStat6Anno((TextView) view.findViewById(R.id.txtStat6Anno));
            VariabiliStaticheAvversari.getInstance().setTxtStat7Anno((TextView) view.findViewById(R.id.txtStat7Anno));
            VariabiliStaticheAvversari.getInstance().setTxtStat8Anno((TextView) view.findViewById(R.id.txtStat8Anno));
            VariabiliStaticheAvversari.getInstance().setTxtStat9Anno((TextView) view.findViewById(R.id.txtStat9Anno));
            VariabiliStaticheAvversari.getInstance().setTxtStat10Anno((TextView) view.findViewById(R.id.txtStat10Anno));
            VariabiliStaticheAvversari.getInstance().setTxtStat11Anno((TextView) view.findViewById(R.id.txtStat11Anno));
            VariabiliStaticheAvversari.getInstance().setTxtStat12Anno((TextView) view.findViewById(R.id.txtStat12Anno));
            VariabiliStaticheAvversari.getInstance().setTxtStat13Anno((TextView) view.findViewById(R.id.txtStat13Anno));

            // Carica nuova foto / Elimina foto
            VariabiliStaticheAvversari.getInstance().setImgScegliFoto((ImageView) view.findViewById(R.id.imgSalvaImmagine));
            VariabiliStaticheAvversari.getInstance().setImgEliminaFoto((ImageView) view.findViewById(R.id.imgEliminaImmagine));

            VariabiliStaticheAvversari.getInstance().getImgScegliFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    cu.SceglieFotoDaGalleria(a);
                }
            });

            VariabiliStaticheAvversari.getInstance().getCmdtrovaCoord().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (VariabiliStaticheAvversari.getInstance().getEdtIndirizzo().getText().toString()==null ||
                            VariabiliStaticheAvversari.getInstance().getEdtIndirizzo().getText().toString().isEmpty() ) {
                        DialogMessaggio.getInstance().show(VariabiliStaticheAvversari.getInstance().getContext(), "Inserire l'indirizzo del campo dell'avversario", true, VariabiliStaticheGlobali.NomeApplicazione);
                    } else {
                        String Indirizzo = VariabiliStaticheAvversari.getInstance().getEdtIndirizzo().getText().toString();
                        LatLng l = Utility.getInstance().RitornaCoordinateDaIndirizzo(context, Indirizzo);
                        String coord="";
                        if (l==null) {
                            coord="Nessuna coord.";
                        } else {
                            coord=Double.toString(l.latitude) +";"+Double.toString(l.longitude)+";";
                        }
                        VariabiliStaticheAvversari.getInstance().getTxtCoord().setText(coord);
                    }
                }
            });

            VariabiliStaticheAvversari.getInstance().getImgEliminaFoto().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String id = VariabiliStaticheAvversari.getInstance().getTxtId().getText().toString();
                    String NomeFile = id;

                    DialogElimina.getInstance().show(VariabiliStaticheAvversari.getInstance().getContext(),
                            "Si vuole eliminare l'immagine ?",
                            NomeFile,
                            NomiMaschere.getInstance().getAvversari()+"Immagine");
                }
            });
            // Carica nuova foto / Elimina foto

            ImageView i = view.findViewById(R.id.imgRicerca);
            i.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String Ricerca = VariabiliStaticheAvversari.getInstance().getEdtRicerca().getText().toString();

                    DBRemotoAvversari dbr = new DBRemotoAvversari();
                    dbr.RitornaAvversari(VariabiliStaticheAvversari.getInstance().getContext(), Ricerca, TAG);
                }
            });
        }
    }

    public static void NuovoAvversario() {
        String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

        if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
            VariabiliStaticheAvversari.getInstance().getEdtAvversario().setText("");
            VariabiliStaticheAvversari.getInstance().getEdtCampo().setText("");
            VariabiliStaticheAvversari.getInstance().getEdtIndirizzo().setText("");

            VariabiliStaticheAvversari.getInstance().getRlMaschera().setVisibility(RelativeLayout.VISIBLE);
            //VariabiliStaticheAvversari.getInstance().getCmdElimina().setVisibility(LinearLayout.GONE);

            VariabiliStaticheAvversari.getInstance().getCmdOk().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String Avversario = VariabiliStaticheAvversari.getInstance().getEdtAvversario().getText().toString();
                    String Campo = VariabiliStaticheAvversari.getInstance().getEdtCampo().getText().toString();
                    String Indirizzo = VariabiliStaticheAvversari.getInstance().getEdtIndirizzo().getText().toString();

                    if (Avversario.isEmpty()) {
                        DialogMessaggio.getInstance().show(VariabiliStaticheAvversari.getInstance().getContext(), "Inserire il nome dell'avversario", true, VariabiliStaticheGlobali.NomeApplicazione);
                    } else {
                        if (Campo.isEmpty()) {
                            DialogMessaggio.getInstance().show(VariabiliStaticheAvversari.getInstance().getContext(), "Inserire il campo dell'avversario", true, VariabiliStaticheGlobali.NomeApplicazione);
                        } else {
                            if (Indirizzo.isEmpty()) {
                                DialogMessaggio.getInstance().show(VariabiliStaticheAvversari.getInstance().getContext(), "Inserire l'indirizzo del campo dell'avversario", true, VariabiliStaticheGlobali.NomeApplicazione);
                            } else {
                                String Ricerca = VariabiliStaticheAvversari.getInstance().getEdtRicerca().getText().toString();
                                String Coords = VariabiliStaticheAvversari.getInstance().getTxtCoord().getText().toString();

                                DBRemotoAvversari dbr = new DBRemotoAvversari();
                                dbr.SalvaAvversario(VariabiliStaticheAvversari.getInstance().getContext(), "-1",
                                        "-1", Avversario, Campo, Indirizzo,
                                        Ricerca, Coords, NomiMaschere.getInstance().getAvversari());
                                VariabiliStaticheAvversari.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
                            }
                        }
                    }
                }
            });

            VariabiliStaticheAvversari.getInstance().getCmdAnnulla().setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    VariabiliStaticheAvversari.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
                }
            });
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContextPrincipale(),
                    "Non si hanno i permessi per inserire avversari", true, VariabiliStaticheGlobali.NomeApplicazione);
        }
    }

    public static void fillListViewAvversari() {
        if (VariabiliStaticheAvversari.getInstance().getAvversari() != null) {
            VariabiliStaticheAvversari.getInstance().setAdapterAvversari(new AdapterAvversari(VariabiliStaticheGlobali.getInstance().getContext(),
                    android.R.layout.simple_list_item_1, VariabiliStaticheAvversari.getInstance().getAvversari()));

            VariabiliStaticheAvversari.getInstance().getLstAvversari().setAdapter(VariabiliStaticheAvversari.getInstance().getAdapterAvversari());
        }
    }
}
