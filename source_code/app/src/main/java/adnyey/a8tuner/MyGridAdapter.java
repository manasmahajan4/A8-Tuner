package adnyey.a8tuner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.codewaves.stickyheadergrid.StickyHeaderGridAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahajan-PC on 2017-11-02.
 */

public class MyGridAdapter extends StickyHeaderGridAdapter {
    private List<List<GridItem>> labels;

    MyGridAdapter(List<List<GridItem>> labels) {
        this.labels = labels;
    }

    @Override
    public int getSectionCount() {
        return labels.size();
    }

    @Override
    public int getSectionItemCount(int section) {
        return labels.get(section).size();
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_header, parent, false);
        return new MyHeaderViewHolder(view);
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_item, parent, false);
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int section) {
        final MyHeaderViewHolder holder = (MyHeaderViewHolder)viewHolder;
        switch(section)
        {
            case 0:
                holder.labelView.setText("CLASS D");
                break;
            case 1:
                holder.labelView.setText("CLASS C");
                break;
            case 2:
                holder.labelView.setText("CLASS B");
                break;
            case 3:
                holder.labelView.setText("CLASS A");
                break;
            case 4:
                holder.labelView.setText("CLASS S");
                break;
        }


    }

    @Override
    public void onBindItemViewHolder(ItemViewHolder viewHolder, final int section, final int position) {
        final MyItemViewHolder holder = (MyItemViewHolder)viewHolder;
        final GridItem label = labels.get(section).get(position);
        holder.labelView.setText(label.getName());
        holder.labelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int section = getAdapterPositionSection(holder.getAdapterPosition());
                final int offset = getItemSectionOffset(section, holder.getAdapterPosition());
                DiagSelector.IdGetter(label);
            }
        });
    }

    public static class MyHeaderViewHolder extends HeaderViewHolder {
        TextView labelView;

        MyHeaderViewHolder(View itemView) {
            super(itemView);
            labelView = (TextView) itemView.findViewById(R.id.label);
        }
    }

    public static class MyItemViewHolder extends ItemViewHolder {
        TextView labelView;

        MyItemViewHolder(View itemView) {
            super(itemView);
            labelView = (TextView) itemView.findViewById(R.id.label);
        }
    }
}
