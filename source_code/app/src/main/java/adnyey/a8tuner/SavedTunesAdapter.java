package adnyey.a8tuner;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brouding.blockbutton.BlockButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mahajan-PC on 2017-11-03.
 */

public class SavedTunesAdapter extends RecyclerView.Adapter<SavedTunesAdapter.MyViewHolder> {

    List<UserTune> list = new ArrayList<>();
    Context mContext;
    CarDBAccess myDB;
    UserDatabase userDB;

    public SavedTunesAdapter(List<UserTune> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        myDB = CarDBAccess.getInstance(mContext);
        userDB = new UserDatabase(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_tunes, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final UserTune tune = list.get(position);
        final String name = myDB.getName(tune.getId());
        holder.name.setText(""+tune.getRank()+" - "+name);
        holder.rank.setText(""+Unitzer.convert(tune.getTk(), mContext));

        String stune = ""+tune.getAccel()+"-"+tune.getTop()+"-"+tune.getHand()+"-"+tune.getNitro()+" | "+tune.getTires()+"-"+tune.getSusp()+"-"+tune.getDrive()+"-"+tune.getExha();
        holder.tune.setText(stune);

        holder.speed.setText(""+Unitzer.convert(tune.getSpeed(), mContext));

        holder.bean.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("REMOVE", "Removing "+position+"/"+getItemCount());
                        list.remove(position);
                        notifyItemRemoved(position);
                        userDB.remove(tune);
                        notifyDataSetChanged();
                    }
                });
                builder.setNeutralButton("Cancel",
                        new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int id)
                            {
                                dialog.dismiss();
                            }
                        });
                builder.setNegativeButton("Modify", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(mContext, MainActivity.class);
                        i.putExtra("package",tune);
                        mContext.startActivity(i);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setMessage("Do you want to delete this "+name+" tune?");
                dialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, rank, tune, speed;
        BlockButton bean;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.car_name);
            rank = (TextView) view.findViewById(R.id.car_rank);
            tune = (TextView) view.findViewById(R.id.car_tune);
            speed = (TextView) view.findViewById(R.id.car_top);
            bean = (BlockButton) view.findViewById(R.id.itembean);
        }
    }
}
