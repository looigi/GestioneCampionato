package looigi.gestionecampionato.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.VariabiliStaticheAllenamenti;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.maschere.Allenamenti;
import looigi.gestionecampionato.utilities.Utility;

public class AdapterGiocatoriAllAssenti extends ArrayAdapter {
    private Context context;
    private List<String> lista;

    public AdapterGiocatoriAllAssenti(Context context, int textViewResourceId, List<String> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.lista = objects;
    }

    @Nullable
    public View getView(int position, View convertView, ViewGroup parent) {
        final int i = position;
        final VariabiliStaticheAllenamenti vv = VariabiliStaticheAllenamenti.getInstance();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView2 = inflater.inflate(R.layout.listview_giocatori_allen, null);
        TextView id = (TextView) convertView2.findViewById(R.id.id);
        TextView ruolo = (TextView) convertView2.findViewById(R.id.ruolo);
        TextView name = (TextView) convertView2.findViewById(R.id.name);
        TextView numero = (TextView) convertView2.findViewById(R.id.txtNumero);
        ImageView imgGiocatore = (ImageView) convertView2.findViewById(R.id.img);
        if (i<this.lista.size()) {
            String[] Campi = ((String) this.lista.get(i)).split(";");
            id.setText(Campi[0]);
            ruolo.setText(Campi[4]);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Campi[2]);
            stringBuilder.append(" ");
            stringBuilder.append(Campi[3]);
            name.setText(stringBuilder.toString());
            numero.setText(Campi[14]);

            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.sconosciuto);
            imgGiocatore.setImageBitmap(bm);

            Utility.getInstance().PrendeImmagineGiocatore(id.getText().toString(), imgGiocatore);
            final String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();
            convertView2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                        VariabiliStaticheAllenamenti.getInstance().getGiocatoriPresenti().add(vv.getGiocatoriAssenti().get(i));
                        VariabiliStaticheAllenamenti.getInstance().getGiocatoriAssenti().remove(i);
                        Allenamenti.fillListViewGiocatoriPresenti();
                        Allenamenti.fillListViewGiocatoriAssenti();
                    }
                }
            });
        }
        return convertView2;
    }
}