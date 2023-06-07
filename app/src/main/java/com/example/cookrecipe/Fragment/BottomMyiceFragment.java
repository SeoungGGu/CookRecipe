package com.example.cookrecipe.fragment;

import static android.app.Activity.RESULT_OK;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookrecipe.L;
import com.example.cookrecipe.R;
import com.example.cookrecipe.adapter.IngredentSection;
import com.example.cookrecipe.adapter.IngredinetDelegate;
import com.example.cookrecipe.code.SessionManager;
import com.example.cookrecipe.data.Ingredent;
import com.example.cookrecipe.data.MessageEvent;
import com.example.cookrecipe.dialog.IngredientSelectDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class BottomMyiceFragment extends androidx.fragment.app.Fragment {

    private SessionManager sessionManager;
    private String loggedInUsername;


    // 플로팅버튼 상태
    private boolean fabMain_status = false;

    private FloatingActionButton fabMain;
    private FloatingActionButton fabCamera;
    private FloatingActionButton fabEdit;

    private RecyclerView rvSaveIngredients;

    private SectionedRecyclerViewAdapter sectionAdapter;

    private static final int REQUEST_CODE_ADD_INGREDIENTS = 100;

    private final LinkedHashMap<String, List<Ingredent>> selectIngredientMap = new LinkedHashMap<>();

    private IngredientSelectDialog ingredientSelectDialog;

    private IngredinetDelegate delegate = new IngredinetDelegate() {
        @Override
        public void onClickItem(Ingredent ingredent) {
            L.i("::::::::::::클릭... " + ingredent);
            showBottomDialog(ingredent);

        }
    };

    public BottomMyiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //이벤트버스 등록
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN_ORDERED)
    public void handleEvent(MessageEvent event) {
        L.d(" # handleEvent=> " + event.getData().toString());

        Ingredent ingredent = event.getData();


        if (selectIngredientMap.containsKey(ingredent.getType())) {
            //현재 재료타입의 항목이 나의 식재료에 이미 있다면?
            List<Ingredent> saveList = selectIngredientMap.get(ingredent.getType());
            //null이 발생할수없음
            saveList.add(ingredent);
            //중복제거 (이전화면에서 같은 타입의 예를들어 계란,계란 2번클릭할경우 스킵해줘야함)
            List<Ingredent> newList = saveList.stream().distinct().collect(Collectors.toList());
            selectIngredientMap.put(ingredent.getType(), newList);

        } else {
            List<Ingredent> saveList = new ArrayList<>();
            saveList.add(ingredent);
            selectIngredientMap.put(ingredent.getType(), saveList);
        }

//        selectIngredientMap.put(event.getData().getName(), event.getData());


        sectionAdapter.removeAllSections();
        selectIngredientMap.forEach((key, list) -> {
            L.d(":::::::key " + key + " list " + list.toString());
            sectionAdapter.addSection(new IngredentSection(key, list, delegate));
        });
        sectionAdapter.notifyDataSetChanged();


        L.d(" # selectIngredientMap => " + selectIngredientMap.size());

        // prevent event from re-delivering, like when leaving and coming back to app
        EventBus.getDefault().removeStickyEvent(event);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom_myice, container, false);

        fabMain = view.findViewById(R.id.fabMain);
        fabCamera = view.findViewById(R.id.fabCamera);
        fabEdit = view.findViewById(R.id.fabEdit);

        rvSaveIngredients = view.findViewById(R.id.rv_save);
        // 메인플로팅 버튼 클릭
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        sessionManager = new SessionManager(getActivity().getApplicationContext());

        if (sessionManager.isLoggedIn()) {
            loggedInUsername = sessionManager.getUsername();
        } else {
            // Handle the case when the user is not logged in
            // Redirect to the login activity or show an error message
        }

        setSectionAdapter();

        // 수정 플로팅 버튼 클릭
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add_ingredients로 전환하는 Intent 생성
                Intent intent = new Intent(getActivity(), AddIngredientActivity.class);
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
    }

    private void setSectionAdapter() {
        sectionAdapter = new SectionedRecyclerViewAdapter();


        GridLayoutManager glm = new GridLayoutManager(requireActivity(), 3);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (sectionAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 3;
                    default:
                        return 1;
                }
            }
        });

        rvSaveIngredients.setLayoutManager(glm);
        rvSaveIngredients.setItemAnimator(new DefaultItemAnimator());
        rvSaveIngredients.setAdapter(sectionAdapter);

    }

    // 플로팅 액션 버튼 클릭시 애니메이션 효과
    public void toggleFab() {
        if (fabMain_status) {
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

    private void showBottomDialog(Ingredent ingredent) {
        if (ingredientSelectDialog != null) {
            ingredientSelectDialog.dismiss();
        }
        if(!isAdded()) return;;
        ingredientSelectDialog = new IngredientSelectDialog();
        ingredientSelectDialog.setData(ingredent);
        ingredientSelectDialog.setViewMode(IngredientSelectDialog.ViewMode.UPDATE);
        ingredientSelectDialog.show(getChildFragmentManager(), "bottom_sheet");
    }
}