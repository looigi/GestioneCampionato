package looigi.gestionecampionato.maschere;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import looigi.gestionecampionato.R;
import looigi.gestionecampionato.dati.NomiMaschere;
import looigi.gestionecampionato.dati.VariabiliStaticheGlobali;

public class VisualizzaVideo extends android.support.v4.app.Fragment {
    private Context context;
    private static String TAG= NomiMaschere.getInstance().getVisualizzaImmagini();
    private String NomeVideo;
    public static String MascheraApertaPer;
    private VideoView vView;
    private Boolean StaVedendo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context context = this.getActivity();

        View view=null;

        try {
            view=(inflater.inflate(R.layout.visualizzatore_video, container, false));
        } catch (Exception ignored) {

        }

        if (view!=null) {
            VariabiliStaticheGlobali.getInstance().setViewActivity(view);

            MascheraApertaPer=VariabiliStaticheGlobali.MascheraAttuale;
            vView =(VideoView) view.findViewById(R.id.videoView1);
            vView.setKeepScreenOn(true);

            Bundle args = getArguments();
            NomeVideo = args.getString("NomeVideo", "");

            StaVedendo=false;
            vView.setVideoURI(Uri.parse(NomeVideo));
            vView.seekTo(100);

            vView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!StaVedendo) {
                        PlayVideo();
                    } else {
                        StopVideo();
                    }
                }
            });

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
        }
    }

    private void PlayVideo() {
        try {
            vView.start();

            StaVedendo=true;
        } catch (Exception ignored) {

        }
    }

    public void StopVideo() {
        if (StaVedendo) {
            vView.pause();
            StaVedendo = false;
        }
    }
}

