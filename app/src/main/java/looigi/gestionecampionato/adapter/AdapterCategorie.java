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
import looigi.gestionecampionato.dati.VariabiliStaticheCategorie;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;
import looigi.gestionecampionato.db_remoto.DBRemotoCategorie;
import looigi.gestionecampionato.dialog.DialogElimina;
import looigi.gestionecampionato.dialog.DialogMessaggio;
import looigi.gestionecampionato.utilities.Utility;
import uk.co.senab.photoview.PhotoViewAttacher;

public class AdapterCategorie extends ArrayAdapter {
        private Context context;
        private List<String> lista;

        public AdapterCategorie(Context context, int textViewResourceId, List<String> objects)
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
            convertView = inflater.inflate(R.layout.listview_categoria, null);

            if (Utility.getInstance().ePari(position)) {
                convertView.setBackgroundColor(Color.WHITE);
            } else {
                convertView.setBackgroundColor(Color.argb(255,230,230,230));
            }

            //String riga = lista.get(position);

            final String idCategoria = Integer.toString(VariabiliStaticheCategorie.getInstance().getIdCategorie().get(position));
            String Categoria = VariabiliStaticheCategorie.getInstance().getCategorie().get(position);

            final TextView txtId = convertView.findViewById(R.id.idCategoria1);
            final TextView txtCategoria = convertView.findViewById(R.id.Categoria);
            ImageView imgCategoria = convertView.findViewById(R.id.imgCategoria);

            VariabiliStaticheCategorie.getInstance().getTxtId().setText(idCategoria);

            txtId.setText(idCategoria);
            txtCategoria.setText(Categoria);

            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.drawable.sconosciuto);
            imgCategoria.setImageBitmap(bm);

            Utility.getInstance().PrendeImmagineCategoria(idCategoria, imgCategoria);

            PhotoViewAttacher photoAttacher;
            photoAttacher= new PhotoViewAttacher(VariabiliStaticheCategorie.getInstance().getImgCategoria());
            photoAttacher.update();

            String idTipologia = VariabiliStaticheGlobali.getInstance().getDatiUtente().getIdTipologia();

            if (idTipologia.equals(VariabiliStaticheGlobali.ValoreAmministratore)) {
                convertView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        VariabiliStaticheCategorie.getInstance().getTxtId().setText(idCategoria);
                        VariabiliStaticheCategorie.getInstance().getEdtCategoria().setText(txtCategoria.getText());

                        Utility.getInstance().PrendeImmagineCategoria(idCategoria, VariabiliStaticheCategorie.getInstance().getImgCategoria());

                        VariabiliStaticheCategorie.getInstance().getRlMaschera().setVisibility(RelativeLayout.VISIBLE);
                        VariabiliStaticheCategorie.getInstance().getCmdElimina().setVisibility(LinearLayout.VISIBLE);

                        VariabiliStaticheCategorie.getInstance().getCmdOk().setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                String Categoria = VariabiliStaticheCategorie.getInstance().getEdtCategoria().getText().toString();

                                if (Categoria.isEmpty()) {
                                    DialogMessaggio.getInstance().show(VariabiliStaticheCategorie.getInstance().getContext(),
                                            "Inserire la descrizione della categoria", true, VariabiliStaticheGlobali.NomeApplicazione);
                                } else {
                                    DBRemotoCategorie dbr = new DBRemotoCategorie();
                                    dbr.SalvaCategoria(context,
                                            idCategoria, Categoria, NomiMaschere.getInstance().getAllenatori());
                                    VariabiliStaticheCategorie.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
                                }
                            }
                        });

                        VariabiliStaticheCategorie.getInstance().getCmdAnnulla().setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                VariabiliStaticheCategorie.getInstance().getRlMaschera().setVisibility(RelativeLayout.GONE);
                            }
                        });

                        VariabiliStaticheCategorie.getInstance().getCmdElimina().setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                DialogElimina.getInstance().show(VariabiliStaticheCategorie.getInstance().getContext(),
                                        "Si vuole eliminare la categoria selezionata ?",
                                        txtId.getText().toString(),
                                        NomiMaschere.getInstance().getCategorie());
                            }
                        });
                    }
                });
            }

            return convertView;
        }
    }
