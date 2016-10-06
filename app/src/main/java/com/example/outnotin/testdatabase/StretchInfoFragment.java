package com.example.outnotin.testdatabase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * Created by outnotin on 10/4/2016 AD.
 */

public class StretchInfoFragment extends Fragment{
    private ImageView stretchPhoto;
    private TextView stretchName;
    private TextView stretchInfo;
    private String stretchNameString;

    public static StretchInfoFragment newInstance(String sname) {
        
        Bundle args = new Bundle();
        
        StretchInfoFragment fragment = new StretchInfoFragment();
        args.putString("sname", sname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stretchNameString = getArguments().getString("sname");
        Log.d("TAG", "sname: " + stretchNameString);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stretch_info_fragment, container, false);

        stretchPhoto = (ImageView) view.findViewById(R.id.stretch_info_gif);
        stretchName = (TextView) view.findViewById(R.id.stretch_info_name);
        stretchName.setText(stretchNameString);
        stretchInfo = (TextView) view.findViewById(R.id.stretch_info);


        Glide.with(getActivity()).load(R.drawable.giphy).asGif().into(stretchPhoto);

        return view;
    }

}
