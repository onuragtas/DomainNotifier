package org.resoft.domainnotifier;

/**
 * Created by onuragtas on 16.07.2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by onuragtas on 7.02.2017.
 */
public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private SessionManager session;
    private ArrayList<String> mList;
    private LayoutInflater mLayoutInflater = null;
    private Dialog dialog;

    public ListAdapter(Context context, ArrayList<String> list) {
        mContext = context;
        mList = list;
        session = new SessionManager(context);
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mList.size();
    }
    @Override
    public Object getItem(int pos) {
        return mList.get(pos);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    public void addAll(ArrayList<String> list){
        this.mList = list;
        //notifyDataSetChanged();
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final HistoryViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.list_item, null);
            viewHolder = new HistoryViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (HistoryViewHolder) v.getTag();
        }
        viewHolder.domain.setText(mList.get(position));
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("index:"+position);
                try {
                    addAll(session.deleteDomain(position));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }


}
class HistoryViewHolder {
    public TextView domain;
    public Button delete;
    public HistoryViewHolder(View base) {
        domain = (TextView) base.findViewById(R.id.domain);
        delete = (Button) base.findViewById(R.id.delete);
    }
}