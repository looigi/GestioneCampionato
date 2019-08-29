package looigi.gestionecampionato.db_remoto;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.upload_download.HttpFileUpload;
import looigi.gestionecampionato.utilities.Utility;

public class DBRemotoMultimedia {
	private String ws = "wsMultimedia.asmx/";
	private String NS="http://cvcalcio_mult.org/";
	private String SA="http://cvcalcio_mult.org/";

	public void EliminaImmagine(Context context, String Tipologia, String NomeFile, String Maschera) {
		String Urletto="EliminaImmagine?";
		Urletto+="Tipologia=" + Tipologia;
		Urletto+="&NomeFile=" + NomeFile;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "EliminaImmagine", "", Maschera, NS, SA);
	}

	public void UploadFile(String Percorso, String Tipologia, String NomeFile, String Cartella, String FileFisico){
		try {
			File f = new File(NomeFile);
			if (f.exists()) {
				FileInputStream fstrm = new FileInputStream(f);
				String n = f.getName();
				String c = f.getPath();
				String c2=c.replace(n,"");
				String[] cc = c2.split("/");
				String c3=cc[cc.length-1];

				String NomeSquadra = "";

				switch (Tipologia) {
					case "Allenatori":
					case "Categorie":
					case "Giocatori":
					case "Partite":
					case "Dirigenti":
						NomeSquadra = VariabiliStaticheGlobali.getInstance().getNomeSquadra();
						break;
				}

				HttpFileUpload hfu = new HttpFileUpload(VariabiliStaticheGlobali.RadiceUpload, Tipologia, n, c3, FileFisico, NomeSquadra);
				hfu.Send_Now(VariabiliStaticheGlobali.getInstance().getContext(), fstrm);
			} else  {
				DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                        "ERRORE: File non esistente", true,
                        VariabiliStaticheGlobali.NomeApplicazione);
			}
		} catch (FileNotFoundException e) {
			String error = e.getMessage();
			DialogMessaggio.getInstance().show(VariabiliStaticheGlobali.getInstance().getContext(),
                    "ERRORE: "+error, true,
                    VariabiliStaticheGlobali.NomeApplicazione);
		}
	}

	public void RitornaMultimedia(Context context, String id, String Tipologia, String Maschera) {
		String Urletto="RitornaMultimedia?";
		Urletto+="Squadra=" + VariabiliStaticheGlobali.getInstance().getNomeSquadra();
		Urletto+="&idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&id=" + id;
		Urletto+="&Tipologia=" + Tipologia;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaMultimedia", "", Maschera, NS, SA);
	}

	public void EliminaMultimedia(Context context, String Nome, String Maschera) {
		String Urletto="EliminaMultimedia?";
		Urletto+="Immagine=" + Nome;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "EliminaMultimedia", "", Maschera, NS, SA);
	}

	public void RitornaAlbumPerCategoria(Context context, String idCategoria, String Maschera) {
		String Urletto="RitornaAlbumPerCategoria?";
		Urletto+="idAnno=" + VariabiliStaticheGlobali.getInstance().getAnnoInCorso();
		Urletto+="&idCategoria=" + idCategoria;

		Utility.getInstance().EsegueChiamata(context, ws, Urletto, "RitornaAlbumPerCategoria", "", Maschera, NS, SA);
	}
}
