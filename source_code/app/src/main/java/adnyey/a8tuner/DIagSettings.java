package adnyey.a8tuner;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.brouding.blockbutton.BlockButton;
import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;

import java.util.List;

/**
 * Created by Mahajan-PC on 2017-11-03.
 */

public class DIagSettings extends Dialog {
    private static DiagSelector.OnMyDialogResult mDialogResult;
    private BlockButton unit_switcher;
    private TextView texxt;

    public DIagSettings(Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // same you have
        setContentView(R.layout.diag_settings);
        setTitle("Settings");

        unit_switcher = (BlockButton)findViewById(R.id.units);
        texxt = (TextView)findViewById(R.id.unit_text);

        if (!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("UNITS", false)) {
            texxt.setText("KM/H");
        } else if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("UNITS", false)) {
            texxt.setText("MPH");
        }

        unit_switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("UNITS", false)) {
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean("UNITS", true).apply();
                    texxt.setText("MPH");
                } else if (PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("UNITS", false)) {
                    PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putBoolean("UNITS", false).apply();
                    texxt.setText("KM/H");
                }
                mDialogResult.finish(1);
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getWindow();
        lp.copyFrom(window.getAttributes());

        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }

    public void setDialogResult(DiagSelector.OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(int result);
    }
}
