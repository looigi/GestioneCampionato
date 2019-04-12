package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;

import uk.co.senab.photoview.PhotoViewAttacher;

public class VisualizzaImmagini extends android.support.v4.app.Fragment {
    private Context context;
    private static String TAG= NomiMaschere.getInstance().getVisualizzaImmagini();
    private String NomeImmagine;
    public static String MascheraApertaPer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = this.getActivity();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.visualizzatore, container, false));
        } catch (Exception ignored) {

        }

        if (view!=null) {
            VariabiliStaticheGlobali.getInstance().setViewActivity(view);

            MascheraApertaPer=VariabiliStaticheGlobali.MascheraAttuale;

            Bundle args = getArguments();
            NomeImmagine = args.getString("NomeImmagine", "");

            initializeGraphic();
        }

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        //isVisible=isVisibleToUser;

        //if (isVisible) {
        //    initializeGraphic();
        //}
    }

    @Override
    public void onResume()
    {
        super.onResume();

        //if (isVisible) {
        //    initializeGraphic();
        //}
    }

    private void initializeGraphic() {
        Context context = VariabiliStaticheGlobali.getInstance().getContext();
        View view = VariabiliStaticheGlobali.getInstance().getViewActivity();

        if (view != null) {
            ImageView mImageView = view.findViewById(R.id.imgImmagine);
            mImageView.setImageBitmap(BitmapFactory.decodeFile(NomeImmagine));

            PhotoViewAttacher photoAttacher;
            photoAttacher= new PhotoViewAttacher(mImageView);
            photoAttacher.update();
        }
    }
}

