package com.dev.imr.knp.germanclient;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AttrsRecyclerAdapter extends RecyclerView.Adapter<AttrsRecyclerAdapter.AttrHolder> {

    class Attr{
        public Attr(String name, boolean val) {
            this.name = name;
            this.val = val;
        }

        String name;
        boolean val;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isVal() {
            return val;
        }

        public void setVal(boolean val) {
            this.val = val;
        }
    }

    public interface OnAttrChanged{
        void onAttrChanged(String fn, String attr, boolean val);
    }

    class AttrHolder extends RecyclerView.ViewHolder{

        public AttrHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    OnAttrChanged listener;
    String fn;

    Context context;
    List<Attr> attrs;
    public AttrsRecyclerAdapter(String fn,Context context,String atrlist, OnAttrChanged listener) throws JSONException {
        this.context = context;
        this.fn = fn;
        this.listener = listener;
        attrs = new ArrayList<>();
        JSONObject jo = new JSONObject(atrlist);
        Iterator<String> iter = jo.keys();

        Log.i("RES", "AttrsRecyclerAdapter: "+jo.length());
        while (iter.hasNext()){
            String name = iter.next();
            boolean val = jo.getBoolean(name);
            Attr attr = new Attr(name,val);
            attrs.add(attr);
        }
    }

    @NonNull
    @Override
    public AttrHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CheckBox rb = new CheckBox(context);
        rb.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        return new AttrHolder(rb);
    }

    @Override
    public void onBindViewHolder(@NonNull AttrHolder attrHolder, final int i) {
        ((CheckBox)attrHolder.itemView).setText(attrs.get(i).name);
        ((CheckBox)attrHolder.itemView).setChecked(attrs.get(i).val);
        ((CheckBox)attrHolder.itemView).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                listener.onAttrChanged(fn,
                        attrs.get(i).name,
                        b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return attrs.size();
    }


}
