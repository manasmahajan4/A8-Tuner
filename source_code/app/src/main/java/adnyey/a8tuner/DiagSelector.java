package adnyey.a8tuner;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.codewaves.stickyheadergrid.StickyHeaderGridLayoutManager;

import java.util.List;

/**
 * Created by Mahajan-PC on 12-03-2017.
 */

public class DiagSelector extends Dialog {
    List<List<GridItem>> data;
    CarDBAccess myDbHelper;
    private static OnMyDialogResult mDialogResult;

    private static final int SPAN_SIZE = 2;
    private static final int SECTIONS = 10;
    private static final int SECTION_ITEMS = 5;
    private RecyclerView mRecycler;
    int columns=2;
    private StickyHeaderGridLayoutManager mLayoutManager;

    public DiagSelector(Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // same you have
        setContentView(R.layout.diag_list);
        setTitle("CAR");
        myDbHelper = CarDBAccess.getInstance(getContext());

        data=myDbHelper.getLists();

        mRecycler = (RecyclerView)findViewById(R.id.recycler);
        try {
            float scalefactor = getContext().getResources().getDisplayMetrics().density * 100;
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindow().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int screenWidth = displaymetrics.widthPixels;
            columns = (int) ((float) screenWidth / (float) scalefactor);
        }catch(NullPointerException e){e.printStackTrace();}
        if (columns > 1 && columns < 7) {
            mLayoutManager = new StickyHeaderGridLayoutManager(columns - 1);
        } else if (columns >= 7) {
            mLayoutManager = new StickyHeaderGridLayoutManager(7);
        } else {
            mLayoutManager = new StickyHeaderGridLayoutManager(columns);
        }
        mLayoutManager.setHeaderBottomOverlapMargin(getContext().getResources().getDimensionPixelSize(R.dimen.header_shadow_size));
        mLayoutManager.setSpanSizeLookup(new StickyHeaderGridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int section, int position) {
                switch (section) {
                    default:
                        return 1;
                }
            }
        });

        // Workaround RecyclerView limitation when playing remove animations. RecyclerView always
        // puts the removed item on the top of other views and it will be drawn above sticky header.
        // The only way to fix this, abandon remove animations :(
        mRecycler.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean animateRemove(RecyclerView.ViewHolder holder) {
                dispatchRemoveFinished(holder);
                return false;
            }
        });

        mRecycler.setLayoutManager(mLayoutManager);
        mRecycler.setAdapter(new MyGridAdapter(data));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = getWindow();
        lp.copyFrom(window.getAttributes());

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#212121")));
        window.setAttributes(lp);


    }

    public static void IdGetter(GridItem e)
    {
        mDialogResult.finish(e.getID());
    }

    @Override
    public void setOnDismissListener(@Nullable OnDismissListener listener) {
        super.setOnDismissListener(listener);
        myDbHelper.close();
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(int result);
    }
}
