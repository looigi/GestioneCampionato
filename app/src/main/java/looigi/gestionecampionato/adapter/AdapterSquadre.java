package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheAllenatori;
import looigi.gestionecampionato.dati.VariabiliStaticheAvversari;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.db_remoto.DBRemotoAllenatori;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.maschere.SceltaSquadra;
import looigi.gestionecampionato.utilities.Utility;

public class AdapterSquadre extends ArrayAdapter {
    private Context context;
    private List<String> lista;

    public AdapterSquadre(Context context, int textViewResourceId, List<String> objects)
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
        convertView = inflater.inflate(R.layout.listview_nomi_squadre, null);

        if (Utility.getInstance().ePari(position)) {
            convertView.setBackgroundColor(Color.WHITE);
        } else {
            convertView.setBackgroundColor(Color.argb(255,230,230,230));
        }

        String riga = lista.get(position);
        final String[] Campi=riga.split(";",-1);

        ((TextView) convertView.findViewById(R.id.id)).setText(Campi[0]);
        ((TextView) convertView.findViewById(R.id.Squadra)).setText(Campi[2]);
        ((TextView) convertView.findViewById(R.id.Coords)).setText(Campi[5] + "-" + Campi[6]);

        ImageView img = convertView.findViewById(R.id.imgSquadra);
        Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.sconosciuto);
        img.setImageBitmap(bm);
        Utility.getInstance().PrendeImmagineAvversario(Campi[0], img);

        convertView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SceltaSquadra.ScriveSquadra(Campi[2]);
            }
        });

        return convertView;
    }
}
