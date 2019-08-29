package looigi.gestionecampionato.upload_download;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import looigi.gestionecampionato.MainActivity;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.db_remoto.DBRemotoMultimedia;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.utilities.Utility;

public class CropUtilityMultimedia {
    public int PICK_IMAGE_REQUEST = 1;
    public int PIC_CROP = 2;
    public static String NomeFileGalleria;

    public void SceglieFotoDaGalleria(AppCompatActivity a) {
        Intent intent = new Intent();
        intent.setType("image/* video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        a.startActivityForResult(Intent.createChooser(intent, "Seleziona multimedia"), PICK_IMAGE_REQUEST);
    }

    public void performCrop(Uri picUri){
        String nf = Utility.getInstance().getPath(picUri);

        if (nf!=null && nf.toUpperCase().contains(".JPG")) {
            CropImage.activity(picUri)
                    .start(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale());
        } else {
            if (nf!=null) {
                SalvataggioFileMultimediale(
                        picUri,
                        MainActivity.TipologiaMultimedia,
                        MainActivity.idMultimedia);
            } else {
                DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                        "Problemi nella selezione del file multimediale", true,
                        VariabiliStaticheGlobali.NomeApplicazione);
            }
        }
    }

    public void SalvataggioFileMultimediale(Uri uri, String Tipologia, String Id) {
        String nf = Utility.getInstance().getPath(uri);
        if (nf==null) nf = uri.toString();
        File nFile = null;
        boolean isImage=false;
        if (nf.toUpperCase().contains("CROPPED")) {
            isImage=true;
        } else {
            nFile = new File(nf);
        }

        if (isImage || nFile.exists()) {
            Bitmap thePic = null;
            boolean isJpg = false;

            if (nf.toUpperCase().contains(".JPG")) {
                try {
                    thePic = MediaStore.Images.Media.getBitmap(VariabiliStaticheGlobali.getInstance().getContext().getContentResolver(), uri);
                    isJpg = true;
                } catch (IOException ignored) {

                }
            }

            if (thePic == null && isJpg) {
                DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                        "Problemi nel salvataggio del file multimediale", true,
                        VariabiliStaticheGlobali.NomeApplicazione);
            } else {
                // String NomeFilePerUpload;
                String NomeFileLocale;
                String Percorso;

                if (Tipologia.equals("Giocatori")) {
                    Id = VariabiliStaticheGlobali.getInstance().getAnnoInCorso() + "_" + Id;
                }
                Percorso = VariabiliStaticheGlobali.getInstance().PercorsoDIR + "/" + Tipologia + "/" + Id;

                String n = Long.toString(System.currentTimeMillis()).trim();
                if (isJpg) {
                    NomeFileLocale = Percorso + "/" + n + ".jpg";
                } else {
                    NomeFileLocale = Percorso + "/" + n + ".mp4";
                }

                File fDest = new File(NomeFileLocale);
                if (fDest.exists()) {
                    fDest.delete();
                }

                Utility.getInstance().CreaCartelle(Percorso + "/");

                boolean ok = true;

                if (isJpg) {
                    try {
                        FileOutputStream out = new FileOutputStream(fDest);
                        thePic.compress(Bitmap.CompressFormat.JPEG, 95, out);
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        ok = false;
                    }
                } else {
                    try {
                        Utility.getInstance().copy(nFile, fDest);
                    } catch (IOException ignored) {
                        ok = false;
                    }
                }

                if (ok) {
                    DBRemotoMultimedia dbr = new DBRemotoMultimedia();
                    dbr.UploadFile(Percorso, Tipologia, NomeFileLocale, Id, NomeFileLocale);
                } else {
                    DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                            "Problemi nella copia del file multimediale", true,
                            VariabiliStaticheGlobali.NomeApplicazione);
                }
            }
        } else {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    "File multimediale non esistente", true,
                    VariabiliStaticheGlobali.NomeApplicazione);
        }
    }
}
