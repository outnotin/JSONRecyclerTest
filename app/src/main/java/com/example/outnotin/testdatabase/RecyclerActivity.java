package com.example.outnotin.testdatabase;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by outnotin on 9/27/2016 AD.
 */

public class RecyclerActivity extends AppCompatActivity{

    RecyclerView mRecyclerView;
//    TestDB stretchDatabase;
//    int stretchListSize;
//    StretchAdapter mStretchAdapter;

    private ArrayList<HashMap<String, String>> stretchList;

    private String readJson(){
        String json;

        //read json
        try{
            InputStream inputStream = this.getAssets().open("stretch.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            return json;
        }catch (Exception e){
            return null;
        }
    }

    private void stringJsonToList(String jsonInput){
        try{
            JSONObject obj = new JSONObject(jsonInput);
            JSONArray jsonArray = obj.getJSONArray("stretch");
            HashMap<String, String> hashMapList;

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject objInside = jsonArray.getJSONObject(i);
                String stretchName = objInside.getString("sname");
                String stretchPath = objInside.getString("spath");

                hashMapList = new HashMap<>();
                hashMapList.put("sname", stretchName);
                hashMapList.put("spath", stretchPath);

                stretchList.add(hashMapList);
            }

        }catch (Exception e){

        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stretch_recycler);

        stretchList = new ArrayList<>();

        stringJsonToList(readJson());

        mRecyclerView = (RecyclerView) findViewById(R.id.stretch_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerView.setAdapter(new StretchAdapter(stretchList));
    }

    public class StretchHolder extends RecyclerView.ViewHolder{

        private ImageView mStretchPhoto;
        private TextView mStretchName;

        public StretchHolder(View itemView) {
            super(itemView);
            mStretchPhoto = (ImageView) itemView.findViewById(R.id.stretch_photo);
            mStretchName = (TextView) itemView.findViewById(R.id.stretch_name);
        }

        public void bindBitmap(Bitmap bitmap){
            mStretchPhoto.setImageBitmap(bitmap);
        }

        public void bindDrawable(Drawable drawable){
            mStretchPhoto.setImageDrawable(drawable);
        }

        public void setStretchName(String stringName){
            mStretchName.setText(stringName);
        }
    }

    public class StretchAdapter extends RecyclerView.Adapter<StretchHolder>{

        ArrayList<HashMap<String, String>> _stretchList;

        public StretchAdapter(ArrayList<HashMap<String, String>> stretchList) {
            _stretchList = stretchList;
        }


        @Override
        public StretchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(RecyclerActivity.this).inflate(R.layout.item_stretch, parent, false);
            return new StretchHolder(view);
        }

        @Override
        public void onBindViewHolder(StretchHolder holder, int position) {

            try {
                InputStream inputStream = getAssets().open("test_photo" + File.separator + _stretchList.get(position).get("spath"));
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                holder.bindBitmap(bitmap);
            }catch(Exception e){
                Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.giphy, null);
                holder.bindDrawable(drawable);
                Log.e("errrrr", "onBindViewHolder: ", e);
            }

            holder.setStretchName(_stretchList.get(position).get("sname"));
        }

        @Override
        public int getItemCount() {
            return _stretchList.size();
        }
    }


}
