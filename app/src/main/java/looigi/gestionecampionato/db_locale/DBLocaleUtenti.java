package looigi.gestionecampionato.db_locale;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import looigi.gestionecampionato.dati.StrutturaDatiUtente;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;

import static android.content.Context.MODE_PRIVATE;

public class DBLocaleUtenti {
	private static String nomeDb="UtentiNuova";
	
	public void CreaDB(Context context) {
    	SQLiteDatabase myDB= null;

    	try {
			myDB = context.openOrCreateDatabase(nomeDb, MODE_PRIVATE, null);			 
			myDB.execSQL("CREATE TABLE IF NOT EXISTS " + nomeDb + " (idUtente Text, Utente Text, " +
					"Cognome Text, Nome Text, Email Text, idCategoria Text, idTipologia Text);");
			myDB.close();
    	} catch(Exception ignored) {

		} finally {
		   if (myDB != null){
			   myDB.close();
		   }
		}
	}

    public void SalvaDatiUtente(Context context, StrutturaDatiUtente Dati) {
		SQLiteDatabase myDB=context.openOrCreateDatabase(nomeDb, MODE_PRIVATE, null);
	   	String Sql="";

	   	Sql="Delete From " + nomeDb + ";";
	   	myDB.execSQL(Sql);

	   	Sql="Insert Into " + nomeDb + " Values (" +
				"'"+ Dati.getIdUtente() +"', " +
				"'"+ Dati.getUtente() +"', " +
				"'"+ Dati.getCognome() +"', " +
				"'"+ Dati.getNome() +"', " +
				"'"+ Dati.getEMail() +"', " +
				"'"+ Dati.getIdCategoria1() +"', " +
				"'"+ Dati.getIdTipologia() +"' " +
				");";
	   	myDB.execSQL(Sql);
	   	
	   	myDB.close();
    }

	public boolean RitornaUtenteSalvato(Context context) {
		Boolean ritorno=true;
		Boolean Ok=false;
    	SQLiteDatabase myDB=context.openOrCreateDatabase(nomeDb, MODE_PRIVATE, null);
	   	String Sql="SELECT * FROM " + nomeDb + ";";
		Cursor c = myDB.rawQuery(Sql , null);
		c.moveToFirst();

		VariabiliStaticheGlobali.getInstance().setDatiUtente(new StrutturaDatiUtente());
		try {
			VariabiliStaticheGlobali.getInstance().getDatiUtente().setIdUtente(c.getString(0));
			VariabiliStaticheGlobali.getInstance().getDatiUtente().setUtente(c.getString(1));
			VariabiliStaticheGlobali.getInstance().getDatiUtente().setCognome(c.getString(2));
			VariabiliStaticheGlobali.getInstance().getDatiUtente().setNome(c.getString(3));
			VariabiliStaticheGlobali.getInstance().getDatiUtente().setEMail(c.getString(4));
			VariabiliStaticheGlobali.getInstance().getDatiUtente().setIdCategoria1(c.getString(5));
			VariabiliStaticheGlobali.getInstance().getDatiUtente().setIdTipologia(c.getString(6));

			ritorno=true;
		} catch (Exception ignored) {
			ritorno=false;
		}
		c.close();

		myDB.close();

		return ritorno;
	}
}
