package com.example.outnotin.testdatabase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by outnotin on 10/3/2016 AD.
 */

public class StretchListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<HashMap<String, String>> stretchList;


    private int gridSize = 2;
    private String stretchPhotoFolder = "test_photo";
    private String jsonFileName = "stretch.json";

    public static StretchListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        StretchListFragment fragment = new StretchListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void jsonStringToList(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("stretch");
            HashMap<String, String> hashMapList;

            for (int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject jsonObjectInside = jsonArray.getJSONObject(i);
                String stretchName = jsonObjectInside.getString("sname");
                String stetchPath = jsonObjectInside.getString("spath");

                hashMapList = new HashMap<>();
                hashMapList.put("sname", stretchName);
                hashMapList.put("spath", stetchPath);

                stretchList.add(hashMapList);
            }

        }catch (Exception e){

        }
    }

    private String jsonFileToString(String jsonFileName){
        String jsonString;
        try {
            InputStream inputStream = getActivity().getAssets().open(jsonFileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, "UTF-8");
            return jsonString;
        }catch (Exception e){
            return null;
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stretchList = new ArrayList<>();

        jsonStringToList(jsonFileToString(jsonFileName));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stretch_recycler, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.stretch_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), gridSize));
        mRecyclerView.setAdapter(new StretchAdapter(stretchList));
        return view;
    }

    private class StretchHolder extends RecyclerView.ViewHolder{

        private ImageView mStretchPhoto;
        private TextView mStretchName;
        private String sname;

        protected StretchHolder(View itemView) {
            super(itemView);
            mStretchPhoto = (ImageView) itemView.findViewById(R.id.stretch_photo);
            mStretchName = (TextView) itemView.findViewById(R.id.stretch_name);
            mStretchPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StretchInfoFragment f = StretchInfoFragment.newInstance(sname);
                    FragmentManager fm = getFragmentManager();
                    fm.beginTransaction().replace(R.id.fragment_container,f).addToBackStack(null).commit();
                }
            });
        }

        protected void bindBitmap(Bitmap bitmap){
            mStretchPhoto.setImageBitmap(bitmap);
        }

        protected void bindDrawable(Drawable drawable){
            mStretchPhoto.setImageDrawable(drawable);
        }

        protected void setStretchName(String stringName){
            mStretchName.setText(stringName);
        }

        protected void setSName(String sname){
            this.sname = sname;
        }

    }

    private class StretchAdapter extends RecyclerView.Adapter<StretchHolder>{
        ArrayList<HashMap<String, String>> _stretchList;

        protected StretchAdapter(ArrayList<HashMap<String, String>> stretchList){
            _stretchList = stretchList;
        }

        @Override
        public StretchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_stretch, parent, false);
            return new StretchHolder(view);
        }

        @Override
        public void onBindViewHolder(StretchHolder holder, int position) {
            try {
                InputStream inputStream = getActivity().getAssets().open(stretchPhotoFolder + File.separator + _stretchList.get(position).get("spath"));
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                holder.bindBitmap(bitmap);
            }catch (Exception e){
                Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.giphy, null);
                holder.bindDrawable(drawable);
            }
            holder.setStretchName(_stretchList.get(position).get("sname"));
            holder.setSName(_stretchList.get(position).get("sname"));
        }


        @Override
        public int getItemCount() {
            return _stretchList.size();
        }
    }
}
