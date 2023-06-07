package com.example.cookrecipe.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.cookrecipe.R;
import com.example.cookrecipe.adapter.IngredentSection;
import com.example.cookrecipe.adapter.IngredinetDelegate;
import com.example.cookrecipe.base.BaseActivity;
import com.example.cookrecipe.code.SessionManager;
import com.example.cookrecipe.data.Ingredent;
import com.example.cookrecipe.databinding.ActivityAddIngredientBinding;
import com.example.cookrecipe.dialog.IngredientSelectDialog;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class AddIngredientActivity extends BaseActivity<ActivityAddIngredientBinding> {

    private IngredientSelectDialog ingredientSelectDialog;
    private SessionManager sessionManager;
    private String loggedInUsername;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_ingredient;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setupAdapter();
        setSectionAdapter();

        sessionManager = new SessionManager(getApplicationContext());

        if (sessionManager.isLoggedIn()) {
            loggedInUsername = sessionManager.getUsername();
        } else {
            // Handle the case when the user is not logged in
            // Redirect to the login activity or show an error message
        }
    }

    private void setSectionAdapter() {
        SectionedRecyclerViewAdapter sectionAdapter = new SectionedRecyclerViewAdapter();


        IngredinetDelegate delegate = new IngredinetDelegate() {
            @Override
            public void onClickItem(Ingredent ingredent) {
                showBottomDialog(ingredent);
            }
        };

        sectionAdapter.addSection(new IngredentSection("가공/유제품", getSavingItem("가공/유제품"), delegate));
        sectionAdapter.addSection(new IngredentSection("고기류", getSavingItem("고기류"), delegate));
        sectionAdapter.addSection(new IngredentSection("곡물", getSavingItem("곡물"), delegate));
        sectionAdapter.addSection(new IngredentSection("과일", getSavingItem("과일"), delegate));
        sectionAdapter.addSection(new IngredentSection("면", getSavingItem("면"), delegate));
        sectionAdapter.addSection(new IngredentSection("빵/떡", getSavingItem("빵/떡"), delegate));
        sectionAdapter.addSection(new IngredentSection("채소", getSavingItem("채소"), delegate));
        sectionAdapter.addSection(new IngredentSection("콩/견과류", getSavingItem("콩/견과류"), delegate));
        sectionAdapter.addSection(new IngredentSection("해산물", getSavingItem("해산물"), delegate));
        sectionAdapter.addSection(new IngredentSection("햄/소시지", getSavingItem("햄/소시지"), delegate));
        sectionAdapter.addSection(new IngredentSection("조미료/양념", getSavingItem("조미료/양념"), delegate));



        GridLayoutManager glm = new GridLayoutManager(this, 3);
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

        binding.rvTotal.setLayoutManager(glm);
        binding.rvTotal.setItemAnimator(new DefaultItemAnimator());
        binding.rvTotal.setAdapter(sectionAdapter);
    }


    private List<Ingredent> getSavingItem(String type) {

        ArrayList<Ingredent> savingItemRow = new ArrayList<>();

        switch (type) {
            case "가공/유제품":
                savingItemRow.add(new Ingredent("참치캔", R.drawable.canned_tuna, "가공/유제품"));
                savingItemRow.add(new Ingredent("체다치즈", R.drawable.cheddar_cheese, "가공/유제품"));
                savingItemRow.add(new Ingredent("만두", R.drawable.dumpling, "가공/유제품"));
                savingItemRow.add(new Ingredent("계란", R.drawable.egg, "가공/유제품"));
                savingItemRow.add(new Ingredent("모짜렐라 치즈", R.drawable.mozzarella_cheese, "가공/유제품"));
                savingItemRow.add(new Ingredent("돈까스", R.drawable.pork_cutlet, "가공/유제품"));
                savingItemRow.add(new Ingredent("메추리알", R.drawable.quail_eggs, "가공/유제품"));
                savingItemRow.add(new Ingredent("스위트콘", R.drawable.sweet_corn, "가공/유제품"));
                savingItemRow.add(new Ingredent("물만두", R.drawable.water_dumplings, "가공/유제품"));
                savingItemRow.add(new Ingredent("요거트", R.drawable.yogurt, "가공/유제품"));
                break;
            case "고기류":
                savingItemRow.add(new Ingredent("소고기", R.drawable.beef, "고기류"));
                savingItemRow.add(new Ingredent("닭고기", R.drawable.chicken, "고기류"));
                savingItemRow.add(new Ingredent("오리고기", R.drawable.duck_meat, "고기류"));
                savingItemRow.add(new Ingredent("양고기", R.drawable.lamb, "고기류"));
                savingItemRow.add(new Ingredent("돼지고기", R.drawable.pork, "고기류"));
                break;
            case "곡물":
                savingItemRow.add(new Ingredent("빵가루", R.drawable.bread_crumbs, "곡물"));
                savingItemRow.add(new Ingredent("부침가루", R.drawable.buchimgalu, "곡물"));
                savingItemRow.add(new Ingredent("찹쌀가루", R.drawable.chabssalgalu, "곡물"));
                savingItemRow.add(new Ingredent("누룽지", R.drawable.nurungji, "곡물"));
                savingItemRow.add(new Ingredent("귀리", R.drawable.oats, "곡물"));
                savingItemRow.add(new Ingredent("옥수수", R.drawable.ogsusu, "곡물"));
                savingItemRow.add(new Ingredent("감자", R.drawable.potato, "곡물"));
                savingItemRow.add(new Ingredent("고구마", R.drawable.sweet_potato, "곡물"));
                savingItemRow.add(new Ingredent("통밀", R.drawable.tongmil, "곡물"));
                break;
            case "과일":
                savingItemRow.add(new Ingredent("감", R.drawable.gam, "과일"));
                savingItemRow.add(new Ingredent("건포도", R.drawable.geonpodo, "과일"));
                savingItemRow.add(new Ingredent("귤", R.drawable.gyul, "과일"));
                savingItemRow.add(new Ingredent("딸기", R.drawable.strawberry, "과일"));
                savingItemRow.add(new Ingredent("라임", R.drawable.lime, "과일"));
                savingItemRow.add(new Ingredent("레몬", R.drawable.lemon, "과일"));
                savingItemRow.add(new Ingredent("망고", R.drawable.mango, "과일"));
                savingItemRow.add(new Ingredent("메론", R.drawable.melon, "과일"));
                savingItemRow.add(new Ingredent("바나나", R.drawable.banana, "과일"));
                savingItemRow.add(new Ingredent("배", R.drawable.pear, "과일"));
                savingItemRow.add(new Ingredent("복숭아", R.drawable.peach, "과일"));
                savingItemRow.add(new Ingredent("블루베리", R.drawable.blueberries, "과일"));
                savingItemRow.add(new Ingredent("사과", R.drawable.apple, "과일"));
                savingItemRow.add(new Ingredent("수박", R.drawable.watermelon, "과일"));
                savingItemRow.add(new Ingredent("아보카도", R.drawable.avocado, "과일"));
                savingItemRow.add(new Ingredent("오렌지", R.drawable.orange, "과일"));
                savingItemRow.add(new Ingredent("자두", R.drawable.jadu, "과일"));
                savingItemRow.add(new Ingredent("체리", R.drawable.cherry, "과일"));
                savingItemRow.add(new Ingredent("키위", R.drawable.kiwi, "과일"));
                savingItemRow.add(new Ingredent("파인애플", R.drawable.pineapple, "과일"));
                savingItemRow.add(new Ingredent("포도", R.drawable.grape, "과일"));
                break;
            case "면":
                savingItemRow.add(new Ingredent("당면", R.drawable.dangmyeon, "면"));
                savingItemRow.add(new Ingredent("라면", R.drawable.ramen, "면"));
                savingItemRow.add(new Ingredent("소면", R.drawable.somyeon, "면"));
                savingItemRow.add(new Ingredent("수제비", R.drawable.sujebi, "면"));
                savingItemRow.add(new Ingredent("스파게티면", R.drawable.spaghetti_noodles,"면"));
                savingItemRow.add(new Ingredent("우동면", R.drawable.udon_noodles, "면"));
                savingItemRow.add(new Ingredent("칼국수면", R.drawable.kalguksu_noodles, "면"));
                break;
            case "빵/떡":
                savingItemRow.add(new Ingredent("가래떡", R.drawable.galaetteog, "빵"));
                savingItemRow.add(new Ingredent("떡국떡", R.drawable.tteokguktteok, "빵"));
                savingItemRow.add(new Ingredent("떡볶이떡", R.drawable.tteokbokki, "빵"));
                savingItemRow.add(new Ingredent("바게트", R.drawable.baguette, "빵"));
                savingItemRow.add(new Ingredent("베이글", R.drawable.bagel, "빵"));
                savingItemRow.add(new Ingredent("식빵", R.drawable.bread, "빵"));
                break;
            case "채소":
                savingItemRow.add(new Ingredent("가지", R.drawable.gaji, "채소"));
                savingItemRow.add(new Ingredent("고추", R.drawable.gochu, "채소"));
                savingItemRow.add(new Ingredent("김치", R.drawable.kimchi, "채소"));
                savingItemRow.add(new Ingredent("깻잎", R.drawable.kkaes_ip, "채소"));
                savingItemRow.add(new Ingredent("당근", R.drawable.carrot, "채소"));
                savingItemRow.add(new Ingredent("대파", R.drawable.daepa, "채소"));
                savingItemRow.add(new Ingredent("마늘", R.drawable.garlic, "채소"));
                savingItemRow.add(new Ingredent("무", R.drawable.mu, "채소"));
                savingItemRow.add(new Ingredent("배추", R.drawable.baechu, "채소"));
                savingItemRow.add(new Ingredent("브로콜리", R.drawable.broccoli, "채소"));
                savingItemRow.add(new Ingredent("비트", R.drawable.beat, "채소"));
                savingItemRow.add(new Ingredent("상추", R.drawable.sangchu, "채소"));
                savingItemRow.add(new Ingredent("셀러리", R.drawable.salad, "채소"));
                savingItemRow.add(new Ingredent("시금치", R.drawable.spinach, "채소"));
                savingItemRow.add(new Ingredent("아스파라거스", R.drawable.asparagus, "채소"));
                savingItemRow.add(new Ingredent("애호박", R.drawable.squash, "채소"));
                savingItemRow.add(new Ingredent("양배추", R.drawable.yangbaechu, "채소"));
                savingItemRow.add(new Ingredent("양송이 버섯", R.drawable.white_mushroom, "채소"));
                savingItemRow.add(new Ingredent("양파", R.drawable.onion, "채소"));
                savingItemRow.add(new Ingredent("열무", R.drawable.yeolmu, "채소"));
                savingItemRow.add(new Ingredent("오이", R.drawable.cucumber, "채소"));
                savingItemRow.add(new Ingredent("콩나물", R.drawable.bean_sprouts, "채소"));
                savingItemRow.add(new Ingredent("토마토", R.drawable.tomato, "채소"));
                savingItemRow.add(new Ingredent("파프리카", R.drawable.paprika, "채소"));
                savingItemRow.add(new Ingredent("팽이버섯", R.drawable.paeng_ibeoseos, "채소"));
                savingItemRow.add(new Ingredent("표고버섯", R.drawable.hobag, "채소"));
                savingItemRow.add(new Ingredent("호박", R.drawable.tongmil, "채소"));
                break;
            case "콩/견과류":
                savingItemRow.add(new Ingredent("검은콩", R.drawable.black_bean, "콩/견과류"));
                savingItemRow.add(new Ingredent("깨", R.drawable.kkae, "콩/견과류"));
                savingItemRow.add(new Ingredent("두부", R.drawable.dubu, "콩/견과류"));
                savingItemRow.add(new Ingredent("땅콩", R.drawable.peanut, "콩/견과류"));
                savingItemRow.add(new Ingredent("아몬드", R.drawable.almond, "콩/견과류"));
                savingItemRow.add(new Ingredent("완두콩", R.drawable.wandukong, "콩/견과류"));
                savingItemRow.add(new Ingredent("콩", R.drawable.bean, "콩/견과류"));
                savingItemRow.add(new Ingredent("호두", R.drawable.walnut, "콩/견과류"));
                savingItemRow.add(new Ingredent("연두부", R.drawable.soft_tofu, "콩/견과류"));
                break;
            case "해산물":
                savingItemRow.add(new Ingredent("갈치", R.drawable.galchi, "해산물"));
                savingItemRow.add(new Ingredent("건새우", R.drawable.geonsaeu, "해산물"));
                savingItemRow.add(new Ingredent("게맛살", R.drawable.gemas_sal, "해산물"));
                savingItemRow.add(new Ingredent("고등어", R.drawable.godeung_eo, "해산물"));
                savingItemRow.add(new Ingredent("골뱅이", R.drawable.golbaeng_i, "해산물"));
                savingItemRow.add(new Ingredent("굴", R.drawable.gul, "해산물"));
                savingItemRow.add(new Ingredent("꼬막", R.drawable.kkomag, "해산물"));
                savingItemRow.add(new Ingredent("꽁치", R.drawable.kkongchi, "해산물"));
                savingItemRow.add(new Ingredent("꽃게", R.drawable.crab, "해산물"));
                savingItemRow.add(new Ingredent("낙지", R.drawable.nagji, "해산물"));
                savingItemRow.add(new Ingredent("다시마", R.drawable.dasima, "해산물"));
                savingItemRow.add(new Ingredent("대합", R.drawable.daehab, "해산물"));
                savingItemRow.add(new Ingredent("도다리", R.drawable.dodali, "해산물"));
                savingItemRow.add(new Ingredent("동태", R.drawable.dongtae, "해산물"));
                savingItemRow.add(new Ingredent("멸치", R.drawable.myeolchi, "해산물"));
                savingItemRow.add(new Ingredent("명태", R.drawable.myeongtae, "해산물"));
                savingItemRow.add(new Ingredent("문어", R.drawable.octopus, "해산물"));
                savingItemRow.add(new Ingredent("미역", R.drawable.miyeog, "해산물"));
                savingItemRow.add(new Ingredent("바지락", R.drawable.bajilag, "해산물"));
                savingItemRow.add(new Ingredent("새우", R.drawable.shrimp, "해산물"));
                savingItemRow.add(new Ingredent("소라", R.drawable.sola, "해산물"));
                savingItemRow.add(new Ingredent("아귀", R.drawable.agwi, "해산물"));
                savingItemRow.add(new Ingredent("어묵", R.drawable.eomug, "해산물"));
                savingItemRow.add(new Ingredent("연어", R.drawable.salmon, "해산물"));
                savingItemRow.add(new Ingredent("오징어", R.drawable.ojing_eo, "해산물"));
                savingItemRow.add(new Ingredent("전복", R.drawable.jeonbog, "해산물"));
                savingItemRow.add(new Ingredent("전어", R.drawable.jeon_eo, "해산물"));
                savingItemRow.add(new Ingredent("조개", R.drawable.jogae, "해산물"));
                savingItemRow.add(new Ingredent("조기", R.drawable.jogi, "해산물"));
                savingItemRow.add(new Ingredent("진미채", R.drawable.jinmi_ojing_eo, "해산물"));
                savingItemRow.add(new Ingredent("쭈꾸미", R.drawable.jjukkumi, "해산물"));
                savingItemRow.add(new Ingredent("홍합", R.drawable.honghab, "해산물"));
                break;
            case "햄/소시지":
                savingItemRow.add(new Ingredent("미트볼", R.drawable.meatball, "햄/소시지"));
                savingItemRow.add(new Ingredent("베이컨", R.drawable.bacon, "햄/소시지"));
                savingItemRow.add(new Ingredent("비엔나 소시지", R.drawable.vienna_sausage, "햄/소시지"));
                savingItemRow.add(new Ingredent("소시지", R.drawable.sausage, "햄/소시지"));
                savingItemRow.add(new Ingredent("햄", R.drawable.ham, "햄/소시지"));
                savingItemRow.add(new Ingredent("스팸", R.drawable.spam, "햄/소시지"));
                savingItemRow.add(new Ingredent("순대", R.drawable.sundae, "햄/소시지"));
                break;
            case "조미료/양념":
                savingItemRow.add(new Ingredent("간장", R.drawable.ganjang, "조미료/양념"));
                savingItemRow.add(new Ingredent("초고추장", R.drawable.chogochujang, "조미료/양념"));
                savingItemRow.add(new Ingredent("고추장", R.drawable.gochujang, "조미료/양념"));
                savingItemRow.add(new Ingredent("고춧가루", R.drawable.gochusgalu, "조미료/양념"));
                savingItemRow.add(new Ingredent("굴소스", R.drawable.gulsoseu, "조미료/양념"));
                savingItemRow.add(new Ingredent("굵은소금", R.drawable.gulg_eunsogeum, "조미료/양념"));
                savingItemRow.add(new Ingredent("까나리 액젓", R.drawable.kkanaliaegjeos, "조미료/양념"));
                savingItemRow.add(new Ingredent("깨소금", R.drawable.kkaesogeum, "조미료/양념"));
                savingItemRow.add(new Ingredent("꿀", R.drawable.honey, "조미료/양념"));
                savingItemRow.add(new Ingredent("다진마늘", R.drawable.dajin_garlic, "조미료/양념"));
                savingItemRow.add(new Ingredent("데리야끼 소스", R.drawable.deliyakki_soseu, "조미료/양념"));
                savingItemRow.add(new Ingredent("돈까스 소스", R.drawable.tonkatsu_sauce, "조미료/양념"));
                savingItemRow.add(new Ingredent("된장", R.drawable.doenjang, "조미료/양념"));
                savingItemRow.add(new Ingredent("마요네즈", R.drawable.mayonnaise, "조미료/양념"));
                savingItemRow.add(new Ingredent("머스타드소스", R.drawable.mustard_sauce, "조미료/양념"));
                savingItemRow.add(new Ingredent("멸치액젓", R.drawable.myeolchiaegjeos, "조미료/양념"));
                savingItemRow.add(new Ingredent("명란젓갈", R.drawable.myeonglanjeosgal, "조미료/양념"));
                savingItemRow.add(new Ingredent("물엿", R.drawable.mulyeos, "조미료/양념"));
                savingItemRow.add(new Ingredent("미원", R.drawable.miwon, "조미료/양념"));
                savingItemRow.add(new Ingredent("버터", R.drawable.butter, "조미료/양념"));
                savingItemRow.add(new Ingredent("설탕", R.drawable.sugar, "조미료/양념"));
                savingItemRow.add(new Ingredent("소금", R.drawable.salt, "조미료/양념"));
                savingItemRow.add(new Ingredent("쇠고기 다시다", R.drawable.soegogi_dasida, "조미료/양념"));
                savingItemRow.add(new Ingredent("식초", R.drawable.sigcho, "조미료/양념"));
                savingItemRow.add(new Ingredent("쌈장", R.drawable.ssamjang, "조미료/양념"));
                savingItemRow.add(new Ingredent("오징어 젓갈", R.drawable.ojing_eojeosgal, "조미료/양념"));
                savingItemRow.add(new Ingredent("올리고당", R.drawable.olligodang, "조미료/양념"));
                savingItemRow.add(new Ingredent("올리브유", R.drawable.olive_oil, "조미료/양념"));
                savingItemRow.add(new Ingredent("참기름", R.drawable.chamgileum, "조미료/양념"));
                savingItemRow.add(new Ingredent("짜장가루", R.drawable.jjajang_powder, "조미료/양념"));
                savingItemRow.add(new Ingredent("춘장", R.drawable.chunjang, "조미료/양념"));
                savingItemRow.add(new Ingredent("청국장", R.drawable.cheong_gugjan, "조미료/양념"));
                savingItemRow.add(new Ingredent("칠리소스", R.drawable.chilli, "조미료/양념"));
                savingItemRow.add(new Ingredent("카레가루", R.drawable.kalegalu, "조미료/양념"));
                savingItemRow.add(new Ingredent("케첩", R.drawable.ketchup, "조미료/양념"));
                savingItemRow.add(new Ingredent("핫소스", R.drawable.hot_sauce, "조미료/양념"));
                savingItemRow.add(new Ingredent("후추", R.drawable.pepper, "조미료/양념"));
                break;
        }


        return savingItemRow;
    }

    private void showBottomDialog(Ingredent ingredent) {
        if (ingredientSelectDialog != null) {
            ingredientSelectDialog.dismiss();
        }
        if (this.isFinishing())
            return;
        ingredientSelectDialog = new IngredientSelectDialog();
        ingredientSelectDialog.setData(ingredent);
        ingredientSelectDialog.setDelegate(this::finish);
        ingredientSelectDialog.show(getSupportFragmentManager(), "bottom_sheet");
    }

}
