package looigi.gestionecampionato.upload_download;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.ScaricaMultimedia;

public class DownloadMultimediaVideo {
    private ImageView iv;
    private Context context;
    private String Path;
    private Boolean downloaded;
    private ProgressDialog mProgressDialog;
    private Runnable runRiga;
    private Handler hSelezionaRiga;

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
        downloaded=false;

        DownloadMultimediaVideo.DownloadMultimediaFile downloadFile = new DownloadMultimediaVideo.DownloadMultimediaFile();
        downloadFile.execute(Url);
    }

    private class DownloadMultimediaFile extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            VariabiliStaticheGlobali.getInstance().getContextPrincipale().runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        mProgressDialog = new ProgressDialog(VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale());
                        mProgressDialog.setMessage("Attendere prego...");
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        mProgressDialog.setCancelable(false);
                        mProgressDialog.show();
                    } catch (Exception ignored) {
//
                    }
                }
            });
        }

        private void ChiudeDialog() {
            try {
                mProgressDialog.dismiss();
            } catch (Exception ignored) {
            }
        }

        @Override
        protected String doInBackground(String... sUrl) {
            // Mettere qui il download del video

            String fileToDownload = sUrl[0];
            String PathToSave = Path;

            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(fileToDownload);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(PathToSave);

                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                downloaded=false;

                return e.toString();
            } finally {
                downloaded=true;

                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Bitmap bm = null;
            if (downloaded) {
                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.film);
            } else {
                bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.sconosciuto);
            }
            iv.setImageBitmap(bm);

            /* String Cartelle[] = Path.split("/",-1);
            String Path2="";
            for (int i=0; i<Cartelle.length-1;i++) {
                Path2+=Cartelle[i]+"/";
            }
            Utility.getInstance().CreaCartelle(Path2);
            Utility.getInstance().saveVideoFile(Path); */

            ScaricaMultimedia.getInstance().setRitornoDownload("*");

            ChiudeDialog();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }    }
}