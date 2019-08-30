package looigi.gestionecampionato.upload_download;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.utilities.ScaricaImmagini;
import looigi.gestionecampionato.utilities.Utility;

public class DownloadPic {
	private String Directory = "";
	private String NomeFiletto="";
	private String FileDaDownloadare="";
	private ImageView imgView;
	private int idSconosciuto;
	private String Categoria;
	private Context context;
	private ProgressDialog progressDialog;

	public void startDownload(Context context, String Categoria) {
    	this.Categoria = Categoria;
    	this.context = context;

		DownloadFile downloadFile = new DownloadFile();
		downloadFile.execute(FileDaDownloadare);
    }

	public void setImmagine(ImageView NomeImm) {
		imgView=NomeImm;
	}

	public void setIdSconosciuto(int id) { this.idSconosciuto=id; }

	public void setDirectory(String NomeDir) {
		Directory = NomeDir;
	}
	
	public void setFileDaDown(String NomeFileURL) {
		FileDaDownloadare = NomeFileURL;
	}
	
	public void setFiletto(String NomeFile) {
		NomeFiletto = NomeFile;
	}
	
	private class DownloadFile extends AsyncTask<String, Integer, String> {
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
		}

	    @Override
	    protected String doInBackground(String... sUrl) {
			try {
				URL url = new URL(sUrl[0]);
				URLConnection connection = url.openConnection();
				connection.connect();
				int fileLength = connection.getContentLength();

				InputStream input = new BufferedInputStream(url.openStream());

				String[] s=NomeFiletto.split("/");
				if (s.length>1) {
					File f = new File(Directory+"/"+s[0]);
					f.mkdirs();
				}

				OutputStream output = new FileOutputStream(Directory+"/"+NomeFiletto);

				byte[] data = new byte[1024];
				long total = 0;
				int count;
				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress((int) (total * 100 / fileLength));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
				e.printStackTrace();

				/* VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale().runOnUiThread(new Runnable() {
					public void run() {
						Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.sconosciuto);
						imgView.setImageBitmap(bm);
						imgView.setVisibility(LinearLayout.VISIBLE);

						String fDestinazione="";
						String NomeSquadra=VariabiliStaticheGlobali.getInstance().getNomeSquadra();

						switch(Categoria) {
							case "GIOCATORE":
								fDestinazione= VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/"+NomeSquadra+"/Giocatori/" + NomeFiletto;
								break;
							case "CATEGORIE":
								fDestinazione= VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/"+NomeSquadra+"/Categorie/" + NomeFiletto;
								break;
							case "AVVERSARI":
								fDestinazione= VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/Avversari/" + NomeFiletto;
								break;
							case "ALLENATORI":
								fDestinazione= VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/"+NomeSquadra+"/Allenatori/" + NomeFiletto;
								break;
							case "DIRIGENTI":
								fDestinazione= VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/"+NomeSquadra+"/Dirigenti/" + NomeFiletto;
								break;
							case "ARBITRI":
								fDestinazione= VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/Arbitri/" + NomeFiletto;
								break;
							case "PARTITE":
								fDestinazione= VariabiliStaticheGlobali.getInstance().PercorsoDIR+"/"+NomeSquadra+"/Partite/" + NomeFiletto;
								break;
						}

						Utility.getInstance().saveImageFile(bm, fDestinazione);
					}
				}); */

				ScaricaImmagini.getInstance().setRitornoDownload("ERROR: "+e.getMessage());
			}

	        return null;
	    }

		@Override
		protected void onPostExecute(String s) {
			super.onPostExecute(s);

			ChiudeDialog();

			if (imgView!=null) {
				if (Utility.getInstance().fileExistsInSD(NomeFiletto, Directory)) {
					imgView.setImageBitmap(BitmapFactory.decodeFile(Directory + "/" + NomeFiletto));
				// } else {
					// imgView.setImageResource(idSconosciuto);
				}
				imgView.setVisibility(LinearLayout.VISIBLE);
			}

			ScaricaImmagini.getInstance().setRitornoDownload("*");
		}

	    @Override
	    protected void onProgressUpdate(Integer... progress) {
	        super.onProgressUpdate(progress);
	    }
	}
}
