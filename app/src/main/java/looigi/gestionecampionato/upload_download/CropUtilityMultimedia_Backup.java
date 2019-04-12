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

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.db_remoto.DBRemotoMultimedia;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.utilities.Utility;

public class CropUtilityMultimedia_Backup {
    public int PICK_IMAGE_REQUEST = 1;
    public int PIC_CROP = 2;
    public static String NomeFileGalleria;

    public void SceglieFotoDaGalleria(AppCompatActivity a) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        a.startActivityForResult(Intent.createChooser(intent, "Seleziona immagine"), PICK_IMAGE_REQUEST);
    }

    public void performCrop(Uri picUri){
        CropImage.activity(picUri)
                .start(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale());
    }

    public void SalvataggioImmagine(Uri uri, String Tipologia, String Id) {
        Bitmap thePic = null;

        try {
            thePic = MediaStore.Images.Media.getBitmap(VariabiliStaticheGlobali.getInstance().getContext().getContentResolver(), uri);
        } catch (IOException ignored) {

        }

        if (thePic==null) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    "Problemi nel salvataggio dell'immagine", true,
                    VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            // String NomeFilePerUpload;
            String NomeFileLocale;
            String Percorso;

            if (Tipologia.equals("Giocatori")) {
                Id = VariabiliStaticheGlobali.getInstance().getAnnoInCorso() + "_" + Id;
            }
            Percorso = VariabiliStaticheGlobali.getInstance().PercorsoDIR + "/" + Tipologia + "/" + Id;

            // NomeFilePerUpload = NomeFileGalleria;
            // NomeFilePerUpload+= ".jpg";
            String n = Long.toString(System.currentTimeMillis()).trim();
            NomeFileLocale = Percorso + "/" + n + ".jpg";

            File f = new File(NomeFileLocale);
            if (f.exists()) {
                f.delete();
            }

            Utility.getInstance().CreaCartelle(Percorso + "/");

            try {
                FileOutputStream out = new FileOutputStream(f);
                thePic.compress(Bitmap.CompressFormat.JPEG, 95, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            DBRemotoMultimedia dbr = new DBRemotoMultimedia();
            dbr.UploadFile(Percorso, Tipologia, NomeFileLocale, Id);
        }
    }
}
