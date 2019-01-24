package com.dev.imr.knp.germanclient;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FileListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    List<String> fileList;

    public FileListFragment() {
        // Required empty public constructor
        fileList = new ArrayList<>();

    }

    public static FileListFragment newInstance(String fl) {
        FileListFragment fragment = new FileListFragment();
        Bundle args = new Bundle();
        args.putString("FILE_LIST", fl);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String fl = getArguments().getString("FILE_LIST");
            try {
                JSONArray jsonArray = new JSONArray(fl);
                for (int i = 0; i < jsonArray.length(); i++) {
                    fileList.add(jsonArray.getString(i));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView rv = new RecyclerView(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new FileAdapter());
        return rv;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFileClick(String fn);
    }

    class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileHolder>{
        @NonNull
        @Override
        public FileHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View item = LayoutInflater.from(getContext()).inflate(R.layout.file_layout,viewGroup,false);
            return new FileHolder(item);
        }

        @Override
        public void onBindViewHolder(@NonNull FileHolder fileHolder, final int i) {
            fileHolder.name.setText(fileList.get(i));
            fileHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onFileClick(fileList.get(i));
                }
            });
        }

        @Override
        public int getItemCount() {
            return fileList.size();
        }

        class FileHolder extends RecyclerView.ViewHolder{
            TextView name;
            public FileHolder(@NonNull View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.textView);
            }
        }
    }
}
