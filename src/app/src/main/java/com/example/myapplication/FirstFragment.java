package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.EditText;

import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
                return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        View control = view.findViewById(R.id.button_first2);

        if(control != null) {

            //view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText mEdit =  (EditText)view.getRootView().findViewById(R.id.textName);

                //View mView =  view.findViewById(R.id.textName);

                if(mEdit != null)
                {
                    String text =  mEdit.getText().toString();
                    if(text != null && text != "") {
                        TTSHelper.getInstance().speechText(text);
                    }

                }
                else {
                    TTSHelper.getInstance().speechText("Christian");
                }
            }

        });

        }



    }

}
