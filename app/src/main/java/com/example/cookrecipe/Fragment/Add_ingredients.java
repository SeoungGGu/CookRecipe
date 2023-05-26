package com.example.cookrecipe.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.GridView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookrecipe.R;

import java.util.Calendar;

public class Add_ingredients extends AppCompatActivity {

    private GridView mGridView;
    private String mSelectedItem;
    private int mSelectedPeriod = 0;

    private final String[] mItems = {"계란", "돈까스", "만두", "메추리알", "모짜렐라치즈", "물만두", "옥수수콘", "요거트", "참치캔", "체다치즈"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredients);

        mGridView = findViewById(R.id.gridview);
        mGridView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mItems));

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedItem = mItems[position];
                showPeriodDialog();
            }
        });
    }

    private void showPeriodDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_datepicker, null);
        DatePicker datePicker = dialogView.findViewById(R.id.datePicker);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("유통기한 설정");
        builder.setView(dialogView);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 선택한 아이템과 유통기간 정보를 BottomMyiceFragment로 전달
                Intent intent = new Intent();
                intent.putExtra("selected_item", mSelectedItem);
                intent.putExtra("selected_period", mSelectedPeriod);
                Calendar calendar = Calendar.getInstance();
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                long expirationDateInMillis = calendar.getTimeInMillis();
                intent.putExtra("expiration_date", expirationDateInMillis);

                setResult(RESULT_OK, intent);
                finish();
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}