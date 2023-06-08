package com.example.cookrecipe.dialog;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.cookrecipe.R;
import com.example.cookrecipe.data.Ingredent;
import com.example.cookrecipe.data.MessageEvent;
import com.example.cookrecipe.databinding.BottomSheetAddIngredientBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ozcanalasalvar.library.view.datePicker.DatePicker;
import com.ozcanalasalvar.library.view.popup.DatePickerPopup;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;

public class IngredientSelectDialog extends BottomSheetDialogFragment {
    public Context context;

    private BottomSheetAddIngredientBinding binding;
    private Ingredent data;

    private DatePickerPopup datePickerPopup;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");

    private long startTime;
    private long endTime;

    private SelectDialogDelegatge delegate;

    private ViewMode viewMode = ViewMode.ADD;


    public enum ViewMode {
        ADD, // 추가모드
        UPDATE, // 삭제모드
    }

    public interface SelectDialogDelegatge {
        void onClick();
    }


    @Override
    public int getTheme() {
        return R.style.bottomSheetDialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void setData(Ingredent data) {
        this.data = data;
    }

    public void setViewMode(ViewMode mode) {
        this.viewMode = mode;
    }

    public void setDelegate(SelectDialogDelegatge delegate) {
        this.delegate = delegate;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_add_ingredient, container, false, DataBindingUtil.getDefaultComponent());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Window window = getDialog().getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            int defaultSidePadding = (int) convertDpToPixel(16);
            params.width -= defaultSidePadding * 2;
            window.setAttributes(params);
        }


        binding.ivBanner.setImageResource(data.getIcon());
        binding.tvName.setText(data.getName());
        binding.tvType.setText(data.getType());

        //현재 날짜 가져오기
        if (viewMode == ViewMode.ADD) {
            // 데이터 추가모드시
            startTime = System.currentTimeMillis();
            data.setStartTime(startTime);
            binding.btnSetting.setText("설정");
        } else {
            //일반 데이터 보기 모드시
            startTime = data.getStartTime();
            if (data.getEndTime() != 0) {
                binding.tvEndTimeValue.setText(dateFormat.format(data.getEndTime()));
            }
            binding.btnSetting.setText("삭제");
        }

        binding.tvStartTimeValue.setText(dateFormat.format(startTime));


        binding.tvEndTimeValue.setOnClickListener(view12 -> {
            if (datePickerPopup != null && datePickerPopup.isShowing()) {
                datePickerPopup.dismiss();
            }

            datePickerPopup = new DatePickerPopup.Builder()
                    .from(context)
                    .offset(3)
                    .pickerMode(DatePicker.MONTH_ON_FIRST)
                    .textSize(19)
                    .currentDate(startTime)
                    .listener((dp, date, day, month, year) -> {
                        endTime = date;
                        data.setEndTime(endTime);
                        binding.tvEndTimeValue.setText(dateFormat.format(endTime));
                    })
                    .build();


            datePickerPopup.show();
        });

        binding.btnSetting.setOnClickListener(view1 -> {

            if(viewMode == ViewMode.ADD){
                EventBus.getDefault().post(new MessageEvent(data));
                dismissAllowingStateLoss();
                if (delegate != null) {
                    delegate.onClick();
                }
            }else{
                dismissAllowingStateLoss();
            }

        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (binding != null) {
            binding.unbind();
            binding = null;
        }
    }


    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }
}
