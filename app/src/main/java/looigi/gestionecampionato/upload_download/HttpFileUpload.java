package looigi.gestionecampionato.upload_download;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.ritorni_ws.wsMultimedia;

public class HttpFileUpload implements Runnable {
        private URL connectURL;
        private String responseString;
        private byte[ ] dataToServer;
        private FileInputStream fileInputStream = null;
        private String NomeFile;
        private String Tipologia;
        private String Cartella;
    	private ProgressDialog progressDialog;

        public HttpFileUpload(String urlString, String vTipologia, String vNomeFile, String vCartella){
            try{
                connectURL = new URL(urlString);
                NomeFile= vNomeFile;
                Tipologia =vTipologia;
                Cartella=vCartella;
            }catch(Exception ex){
                Log.i("HttpFileUpload","URL Malformatted");
            }
        }
	
        public void Send_Now(Context context, FileInputStream fStream){
        	fileInputStream = fStream;

    		new UploadImageSyncTask(context).execute();
        }

    	
    	public class UploadImageSyncTask extends AsyncTask<String, Integer, String> {
    		private Context context;
    		
    	    public UploadImageSyncTask(Context cxt) {
    	        context = cxt;
    	    }
   		
    		@Override
    		protected void onPreExecute() {
    			super.onPreExecute();

    			ApriDialog();
    		}
    		
    		private void ApriDialog() {
				try {
				   progressDialog = new ProgressDialog(context);
				   progressDialog.setMessage("Attendere Prego");
				   progressDialog.setCancelable(false);
				   progressDialog.setCanceledOnTouchOutside(false);
				   progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				   progressDialog.show();
				} catch (Exception ignored) {
				   
				}
    		}
    		
    		private void ChiudeDialog() {
		        try {
		        	progressDialog.dismiss();
		        } catch (Exception ignored) {
		        }
		        
		        wsMultimedia r=new wsMultimedia();
		        r.RitornoUploadImmagine();
    		}
    		
    		@Override
    		protected void onPostExecute(String p) {
    			super.onPostExecute(p);
    	        
    	        ChiudeDialog();

                //DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(), "Upload immagine effettuato", false, VariabiliStaticheGlobali.NomeApplicazione);
    		}
    		 
    	    @Override
    	    protected String doInBackground(String... sUrl) {
                String iFileName = NomeFile;
                String iTipologia = Tipologia;
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                String Tag="fSnd";

                try
                {
                    HttpURLConnection conn = (HttpURLConnection) connectURL.openConnection();

                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Cache-Control", "no-cache");
                    conn.setRequestProperty("User-Agent", "Android Multipart HTTP Client 1.0");
                    conn.setChunkedStreamingMode(4096);

                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"tipologia\""+ iTipologia +"\""+lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(iTipologia);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                        
                    dos.writeBytes("Content-Disposition: form-data; name=\"nomefile\""+ lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(iFileName);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    dos.writeBytes("Content-Disposition: form-data; name=\"cartella\""+ lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(Cartella);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    String arrotonda = "NO";
                    if (iTipologia.equals(NomiMaschere.getInstance().getCategoriePerTitolo()) ||
                            iTipologia.equals(NomiMaschere.getInstance().getAllenatoriPerTitolo()) ||
                            iTipologia.equals(NomiMaschere.getInstance().getArbitriPerTitolo()) ||
                            iTipologia.equals(NomiMaschere.getInstance().getAvversariPerTitolo()) ||
                            iTipologia.equals(NomiMaschere.getInstance().getDirigentiPerTitolo()) ||
                            iTipologia.equals(NomiMaschere.getInstance().getRosePerTitolo())) {
                            arrotonda = "SI";
                    }

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"arrotonda\""+ arrotonda +"\""+lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(arrotonda);
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + lineEnd);

                    String tipo="";

                    if (iFileName.toUpperCase().contains(".MP4")) {
                        tipo="video/mp4";
                    } else {
                        tipo="image/jpg";
                    }

                    dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\"; filename=\"" + iFileName +"\"" + lineEnd);
                    dos.writeBytes("Content-Type: " + tipo + lineEnd);
                    dos.writeBytes("Content-Transfer-Encoding: binary" + lineEnd);
                    dos.writeBytes(lineEnd);

                    // create a buffer of maximum size
                    int bytesAvailable = fileInputStream.available();
                        
                    int maxBufferSize = 1 * 1024 * 1024;
                    int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    byte[] buffer = new byte[bufferSize];

                    // read file and write it into form...
                    int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {
                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math.min(bytesAvailable,maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0,bufferSize);
                    }
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                    // close streams
                    fileInputStream.close();
                        
                    dos.flush();
                    dos.close();
                        
                    // Log.e(Tag,"File Sent, Response: "+String.valueOf(conn.getResponseCode()));

                    int status = conn.getResponseCode();
                    BufferedInputStream in;
                    Boolean Errore=false;
                    if (status >= 400 ) {
                        in = new BufferedInputStream( conn.getErrorStream() );
                        Errore=true;
                    } else {
                        in = new BufferedInputStream( conn.getInputStream() );
                    }
                    // retrieve the response from server
                    int ch;

                    StringBuffer b =new StringBuffer();
                    while( ( ch = in.read() ) != -1 ){ b.append( (char)ch ); }
                    String s=b.toString();

                    if (Errore) {
                        DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                                "ERRORE: "+s, true,
                                VariabiliStaticheGlobali.NomeApplicazione);
                    }

                    conn.disconnect();
                } catch (MalformedURLException ex) {
                    DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                            "ERRORE: "+ex.getMessage(), true,
                            VariabiliStaticheGlobali.NomeApplicazione);
                } catch (IOException ioe) {
                    DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                            "ERRORE: "+ioe.getMessage(), true,
                            VariabiliStaticheGlobali.NomeApplicazione);
                } catch (Exception ignored) {
                    DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                            "ERRORE: "+ignored.getMessage(), true,
                            VariabiliStaticheGlobali.NomeApplicazione);
                }

				return null;
    	    }
    	}
        
        @Override
        public void run() {
                // TODO Auto-generated method stub
        }
}