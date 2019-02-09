package com.example.ashi.kalakaarindia.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ashi.kalakaarindia.R;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;


public class DialogFragment extends SupportBlurDialogFragment {


    public DialogFragment() {
        // Required empty public constructor

    }
    int image;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            image=getArguments().getInt("image");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View view= inflater.inflate(R.layout.fragment_dialog, container, false);
        ImageView imageView=view.findViewById(R.id.imageView);
        imageView.setImageResource(image);
        return view;
    }

}
