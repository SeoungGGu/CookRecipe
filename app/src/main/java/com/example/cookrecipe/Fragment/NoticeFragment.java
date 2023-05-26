package com.example.cookrecipe.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.cookrecipe.R;

public class NoticeFragment extends Fragment {

    private ImageButton mBtn1, mBtn2, mBtn3, mBtn4, mBtn5, mBtn6, mBtn7, mBtn8, mBtn9;
    private FragmentManager mFragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtn1 = view.findViewById(R.id.button1);
        mBtn2 = view.findViewById(R.id.button2);
        mBtn3 = view.findViewById(R.id.button3);
        mBtn4 = view.findViewById(R.id.button4);
        mBtn5 = view.findViewById(R.id.button5);
        mBtn6 = view.findViewById(R.id.button6);
        mBtn7 = view.findViewById(R.id.button7);
        mBtn8 = view.findViewById(R.id.button8);
        mBtn9 = view.findViewById(R.id.button9);

        mFragmentManager = getActivity().getSupportFragmentManager();

        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new BoardserveFragmant();
                mFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });
    }

}

