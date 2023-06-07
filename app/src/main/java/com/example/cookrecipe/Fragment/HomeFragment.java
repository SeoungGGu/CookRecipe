package com.example.cookrecipe.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.cookrecipe.R;
import com.example.cookrecipe.code.Notice;
import com.example.cookrecipe.code.NoticeListAdapter;
import com.example.cookrecipe.code.SessionManager;
import com.example.cookrecipe.main.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class HomeFragment extends Fragment {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;

    private ImageButton mBtn1, mBtn2, mBtn3, mBtn4, mBtn5, mBtn6 ;
    private FragmentManager mFragmentManager;
    private SessionManager sessionManager;
    private String userId;
    private String recipeId;
    private RequestQueue requestQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        noticeListView = (ListView) view.findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        adapter = new NoticeListAdapter(getActivity(), noticeList);
        noticeListView.setAdapter(adapter);
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
        mFragmentManager = getActivity().getSupportFragmentManager();

        sessionManager = new SessionManager(getActivity().getApplicationContext());

        requestQueue = Volley.newRequestQueue(getActivity());

        userId = sessionManager.getUsername();

        mBtn1.setOnClickListener(onImageClickListener);
        mBtn2.setOnClickListener(onImageClickListener);
        mBtn3.setOnClickListener(onImageClickListener);
        mBtn4.setOnClickListener(onImageClickListener);
        mBtn5.setOnClickListener(onImageClickListener);
        mBtn6.setOnClickListener(onImageClickListener);

        new BackgroundTask().execute();
    }

    private final View.OnClickListener onImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String recipeId = v.getTag().toString();
            Log.d("HomeFragment", "selected recipeId: " + recipeId);

//            ApplicableFragment applicableFragment = ApplicableFragment.newInstance(recipeId);
//
//            getParentFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, applicableFragment) // 프래그먼트 컨테이너 아이디에 맞춰 변경
//                    .addToBackStack(null)
//                    .commit();

        }
    };

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute() {
            target = "http://192.168.219.103/AndroidDB/NoticeList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL (target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null){
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result){
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String noticeContent, noticeName, noticeDate;
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                    noticeList.add(notice);
                    adapter.notifyDataSetChanged();
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

