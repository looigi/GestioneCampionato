package looigi.gestionecampionato.upload_download;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.utilities.ScaricaMultimedia;
import looigi.gestionecampionato.utilities.Utility;

public class DownloadMultimediaImage {
    private ImageView iv;
    private Context context;
    private String Path;
    private Bitmap bitmap;

    public void setIv(ImageView iv) {
        this.iv = iv;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setPath(String path) {
        Path = path;
    }

    public void startDownload(String Url) {
        DownloadMultimediaImage.DownloadMultimediaFile downloadFile = new DownloadMultimediaImage.DownloadMultimediaFile();
        downloadFile.execute(Url);
    }

    private class DownloadMultimediaFile extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... sUrl) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream) new URL(sUrl[0]).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (bitmap != null) {
                iv.setImageBitmap(bitmap);
            } else {
                Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.sconosciuto);
                iv.setImageBitmap(bm);
                bitmap = bm;
            }

            String Cartelle[] = Path.split("/",-1);
            String Path2="";
            for (int i=0; i<Cartelle.length-1;i++) {
                Path2+=Cartelle[i]+"/";
            }
            Utility.getInstance().CreaCartelle(Path2);
            Utility.getInstance().saveImageFile(bitmap, Path);

            ScaricaMultimedia.getInstance().setRitornoDownload("*");
        }
    }
}