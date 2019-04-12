package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import looigi.gestionecampionato.R;

import java.util.List;

public class AdapterMinutiGoalAvversari extends ArrayAdapter
{
	private Context context;
	private List<Integer> lista;

	public AdapterMinutiGoalAvversari(Context context, int textViewResourceId, List<Integer> objects)
	{	
		super(context, textViewResourceId, objects);
		
		this.context = context;
		this.lista=objects;
	}
	
	@Override
	@Nullable
	public View getView(int position, View convertView, @NonNull ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.listview_minutiga, null);

		Integer riga = lista.get(position);

		TextView minuto = convertView.findViewById(R.id.txtMinuto);
		minuto.setText(Integer.toString(position+1) +": "+Integer.toString(riga)+"°");

		return convertView;
	}
}
