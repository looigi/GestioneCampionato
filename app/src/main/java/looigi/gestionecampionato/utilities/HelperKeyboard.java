package looigi.gestionecampionato.utilities;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;

public class HelperKeyboard 
{
	public static void openKeyboard(Context context)
	{
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}
	
	public static void closeKeyboard(Context context)
	{
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        imm.hideSoftInputFromWindow(VariabiliStaticheGlobali.getInstance().getViewActivity().getWindowToken(), 0);
	}
}
