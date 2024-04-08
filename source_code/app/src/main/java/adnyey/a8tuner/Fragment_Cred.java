package adnyey.a8tuner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Admin on 11-03-2016.
 */
public class Fragment_Cred extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.credits, container, false);
        Button wiki,flatpic,hec,elebut,hambut,asphbut,complus,wikibut;

        wiki=(Button)view.findViewById(R.id.wikibut);
        wikibut=(Button)view.findViewById(R.id.wikiabut);
        wiki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("https://plus.google.com/+MartinKneip");
            }
        });
        wikibut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("http://asphalt.wikia.com/wiki/Asphalt_Wiki");
            }
        });
        hec=(Button)view.findViewById(R.id.hectorbut);
        hec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("https://plus.google.com/+HopelessDeer11");
            }
        });

        complus=(Button)view.findViewById(R.id.pluscom);
        complus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("https://plus.google.com/communities/112010654504584136859");
            }
        });
        asphbut=(Button)view.findViewById(R.id.asphbut);
        asphbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("http://www.gameloft.com/asphalt8/");
            }
        });
        return view;
    }
    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }


}