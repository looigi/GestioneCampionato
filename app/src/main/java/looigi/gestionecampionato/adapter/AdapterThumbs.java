package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.VariabiliStaticheAlbum;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.maschere.Album;

public class AdapterThumbs extends ArrayAdapter
{
	private Context context;
	private List<String> lista;

	public AdapterThumbs(Context context, int textViewResourceId, List<String> objects)
	{	
		super(context, textViewResourceId, objects);
		
		this.context = context;
		this.lista=objects;
	}
	
	@Override
	@Nullable
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		convertView = inflater.inflate(R.layout.listview_thumbs, null);

		String riga = lista.get(position);
		final String[] campi = riga.split(";");

		ImageView imgThumb = (ImageView) convertView.findViewById(R.id.imgThumb);

		File f = new File(campi[1]);
		if (f.exists()) {
			if (campi[1].toUpperCase().contains(".JPG")) {
				imgThumb.setImageBitmap(BitmapFactory.decodeFile(campi[1]));
			} else {
				int id = VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale().getResources()
						.getIdentifier("looigi.gestionecampionato:drawable/film", null, null);
				imgThumb.setImageResource(id);
			}
		} else {
			int id = VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale().getResources()
					.getIdentifier("looigi.gestionecampionato:drawable/about", null, null);
			imgThumb.setImageResource(id);
		}
		imgThumb.setTag(campi[0]);
		imgThumb.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int pos = Integer.parseInt(v.getTag().toString());
				// if (lista.get(pos).toUpperCase().contains(".JPG")) {
					VariabiliStaticheAlbum.getInstance().QualeImmagine = pos;

					Album.VisualizzaImmagine();
                    // } else {
					/* Fragment fragment = new VisualizzaVideo();
					String title = NomiMaschere.getInstance().getAlbum();

					Bundle args = new Bundle();
					args.putString("NomeVideo", campi[1]);
					fragment.setArguments(args);

					if (fragment != null) {
						FragmentTransaction ft = VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale()
								.getSupportFragmentManager().beginTransaction();
						ft.replace(R.id.content_frame, fragment);
						ft.commit();
					}

					if (VariabiliStaticheGlobali.getInstance().getContextPrincipale().getSupportActionBar() != null) {
						VariabiliStaticheGlobali.getInstance().getContextPrincipale().getSupportActionBar().setTitle(title);
					}

					DrawerLayout drawer = VariabiliStaticheGlobali.getInstance().getFragmentActivityPrincipale().findViewById(R.id.drawer_layout);
					drawer.closeDrawer(GravityCompat.START);

					HelperKeyboard.closeKeyboard(VariabiliStaticheGlobali.getInstance().getContext());

					VariabiliStaticheMain.getInstance().getActButtonNew().hide(); // .setVisibility(LinearLayout.GONE);
					VariabiliStaticheMain.getInstance().getActButtonBack().show(); // .setVisibility(LinearLayout.VISIBLE); */
				// }
			}
		});

		return convertView;
	}
}
