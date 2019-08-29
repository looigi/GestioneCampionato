package looigi.gestionecampionato.upload_download;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.db_remoto.DBRemotoMultimedia;
import looigi.gestionecampionato.dialog.DialogMessaggio;

public class CropUtility {
    public int PICK_IMAGE_REQUEST = 1;
    public int PIC_CROP = 2;

    public void SceglieFotoDaGalleria(android.support.v4.app.Fragment a) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        a.startActivityForResult(Intent.createChooser(intent, "Seleziona immagine"), PICK_IMAGE_REQUEST);
    }

    //public void ActivityResultPerAcquisizioneImmagine(android.support.v4.app.Fragment a, Intent data) {
    //    Uri uri = data.getData();
//
    //    try {
    //        Bitmap bitmap = MediaStore.Images.Media.getBitmap(VariabiliStaticheGlobali.getInstance().getContext().getContentResolver(), uri);
    //        int w = bitmap.getWidth();
    //        int h = bitmap.getHeight();
    //        if (w>800 || h>600) {
    //            float d1 = w/800;
    //            float d2 = h/600;
    //            float d3;
    //            if (d1>d2) d3=d1; else d3=d2;
    //            w/=d3;
    //            h/=d3;
    //        }
    //        Bitmap resized = Bitmap.createScaledBitmap(bitmap, w, h, true);
    //        Uri uri2 = getImageUri(VariabiliStaticheGlobali.getInstance().getContext(), resized);
    //        performCrop(a, uri2, Integer.toString(w), Integer.toString(h));
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }
    //}
//
    //private Uri getImageUri(Context inContext, Bitmap inImage) {
    //    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    //    inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
    //    String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//
    //    return Uri.parse(path);
    //}

    public void performCrop(Uri picUri){
        //try {
        //    Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //    cropIntent.setDataAndType(picUri, "image/*");
        //    cropIntent.putExtra("crop", "true");
        //    cropIntent.putExtra("aspectX", 1);
        //    cropIntent.putExtra("aspectY", 1);
        //    cropIntent.putExtra("outputX", w);
        //    cropIntent.putExtra("outputY", h);
        //    cropIntent.putExtra("return-data", true);
        //    a.startActivityForResult(cropIntent, PIC_CROP);
        //} catch (ActivityNotFoundException ignored) {
        //}
        CropImage.activity(picUri).start(VariabiliStaticheGlobali.getInstance().getContextPrincipale());
    }

    public void SalvataggioImmagine(ImageView img, Uri uri, String Tipologia, String Id) {
        //Bundle extras = data.getExtras();
        //Bitmap thePic = extras.getParcelable("data");
        //img.setImageBitmap(thePic);
        Bitmap thePic = null;

        try {
            thePic = MediaStore.Images.Media.getBitmap(VariabiliStaticheGlobali.getInstance().getContext().getContentResolver(), uri);
        } catch (IOException ignored) {

        }

        if (thePic==null) {
            DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    "Problemi nel salvataggio dell'immagine", true, VariabiliStaticheGlobali.NomeApplicazione);
        } else {
            int w = thePic.getWidth();
            int h = thePic.getHeight();
            if (w>800 || h>600) {
                float d1 = w/800;
                float d2 = h/600;
                float d3;
                if (d1>d2) d3=d1; else d3=d2;
                w/=d3;
                h/=d3;
                Bitmap resized = Bitmap.createScaledBitmap(thePic, w, h, true);
                thePic=resized;
            }

            String NomeFilePerUpload;
            String NomeFileLocale;
            String Percorso;

            Percorso = VariabiliStaticheGlobali.getInstance().PercorsoDIR + "/" + Tipologia;

            if (!Tipologia.equals(NomiMaschere.getInstance().getAvversariPerTitolo())) {
                NomeFilePerUpload = Integer.toString(VariabiliStaticheGlobali.getInstance().getAnnoInCorso()) + "_" +
                        Id + ".jpg";
                NomeFileLocale = Percorso + "/" + Integer.toString(VariabiliStaticheGlobali.getInstance().getAnnoInCorso()) + "_" +
                        Id + ".jpg";
            } else {
                NomeFilePerUpload = Id + ".jpg";
                NomeFileLocale = Percorso + "/" + Id + ".jpg";
            }

            File f = new File(NomeFileLocale);
            f.mkdirs();
            if (f.exists()) {
                f.delete();
            }

            boolean Errore=false;

            try {
                FileOutputStream out = new FileOutputStream(f);
                thePic.compress(Bitmap.CompressFormat.JPEG, 95, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                Errore=true;
            }

            if (!Errore) {
                DBRemotoMultimedia dbr = new DBRemotoMultimedia();
                dbr.UploadFile(Percorso, Tipologia, NomeFileLocale, "", NomeFileLocale);
            } else {
                DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                        "Problemi nel salvataggio dell'immagine", true, VariabiliStaticheGlobali.NomeApplicazione);
            }
        }
    }
}
