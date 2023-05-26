package com.example.cookrecipe.Fragment;

import static android.app.Activity.RESULT_OK;

import androidx.fragment.app.Fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cookrecipe.Fragment.Add_ingredients;
import com.example.cookrecipe.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BottomMyiceFragment extends androidx.fragment.app.Fragment {

    // 플로팅버튼 상태
    private boolean fabMain_status = false;

    private FloatingActionButton fabMain;
    private FloatingActionButton fabCamera;
    private FloatingActionButton fabEdit;

    private static final int REQUEST_CODE_ADD_INGREDIENTS = 100;

    public BottomMyiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_myice, container, false);

        fabMain = view.findViewById(R.id.fabMain);
        fabCamera = view.findViewById(R.id.fabCamera);
        fabEdit = view.findViewById(R.id.fabEdit);

        // 메인플로팅 버튼 클릭
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        });

        // 수정 플로팅 버튼 클릭
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add_ingredients로 전환하는 Intent 생성
                Intent intent = new Intent(getActivity(), Add_ingredients.class);
                // Intent를 사용하여 Add_ingredients로 전환
                startActivityForResult(intent, REQUEST_CODE_ADD_INGREDIENTS);
            }
        });

        // 카메라 플로팅 버튼 클릭
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "카메라 버튼 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    // 플로팅 액션 버튼 클릭시 애니메이션 효과
    public void toggleFab() {
        if(fabMain_status) {
            // 플로팅 액션 버튼 닫기
            // 애니메이션 추가
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(fabCamera, "translationY", 0f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(fabEdit, "translationY", 0f);
            fe_animation.start();
            // 메인 플로팅 이미지 변경
            fabMain.setImageResource(R.drawable.ic_baseline_add_24);
        } else {
            // 플로팅 액션 버튼 열기
            ObjectAnimator fc_animation = ObjectAnimator.ofFloat(fabCamera, "translationY", -200f);
            fc_animation.start();
            ObjectAnimator fe_animation = ObjectAnimator.ofFloat(fabEdit, "translationY", -400f);
            fe_animation.start();
            // 메인 플로팅 이미지 변경
            fabMain.setImageResource(R.drawable.ic_baseline_clear_24);
        }
        // 플로팅 버튼 상태 변경
        fabMain_status = !fabMain_status;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_ADD_INGREDIENTS && resultCode == RESULT_OK) {
            String selectedItem = data.getStringExtra("selected_item");
            long expirationDateInMillis = data.getLongExtra("expiration_date", 0);
            int selectedPeriod = data.getIntExtra("selected_period", 0);

            // Do something with the returned data...
        }
    }
}