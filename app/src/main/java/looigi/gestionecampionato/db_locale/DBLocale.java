package looigi.gestionecampionato.db_locale;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;

import static android.content.Context.MODE_PRIVATE;

public class DBLocale {
	private static String nomeDb="DatiLocali";
	
	public void CreaDB(Context context) {
    	SQLiteDatabase myDB= null;

    	try {
			myDB = context.openOrCreateDatabase(nomeDb, MODE_PRIVATE, null);			 
			myDB.execSQL("CREATE TABLE IF NOT EXISTS Settings (Anno Text, DescrizioneAnno Text);");
			myDB.close();
    	} catch(Exception ignored) {

		} finally {
		   if (myDB != null){
			   myDB.close();
		   }
		}
	}

    public void SalvaOpzioni(Context context) {
		SQLiteDatabase myDB=context.openOrCreateDatabase(nomeDb, MODE_PRIVATE, null);
	   	String Sql="";
	   	String Suono="";
	   	
	   	Sql="Delete From Settings;";
	   	myDB.execSQL(Sql);

	   	Sql="Insert Into Settings Values ('"+ Integer.toString(VariabiliStaticheGlobali.getInstance().getAnnoInCorso())+"'," +
				"'"+VariabiliStaticheGlobali.getInstance().getDescAnnoInCorso()+"');";
	   	myDB.execSQL(Sql);
	   	
	   	myDB.close();
    }
    
	public void PrendeDatiUtente(Context context) {
		Boolean Ok=false;
    	SQLiteDatabase myDB=context.openOrCreateDatabase(nomeDb, MODE_PRIVATE, null);
	   	String Sql="SELECT * FROM Settings;";
		Cursor c = myDB.rawQuery(Sql , null);
		c.moveToFirst();

		String Anno="";
		String descAnno="";

		try {
			Anno=c.getString(0);
			descAnno=c.getString(1);
			Ok=true;
		} catch (Exception e) {
			Ok=false;
		}
		c.close();

		if (!Ok) {
			Sql="Insert Into Settings Values ('4','2017/2018');";
		   	myDB.execSQL(Sql);

		   	Anno="4";
		   	descAnno="2017/2018";
		}

		// VariabiliStaticheGlobali.getInstance().setAnnoInCorso(Integer.parseInt(Anno));
		// VariabiliStaticheGlobali.getInstance().setDescAnnoInCorso(descAnno);

		myDB.close();
	}
}
