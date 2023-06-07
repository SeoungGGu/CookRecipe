package com.example.cookrecipe.Fragment;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import androidx.annotation.RequiresApi;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cookrecipe.R;
import com.example.cookrecipe.code.Notice;
import com.example.cookrecipe.code.NoticeListAdapter;
import com.example.cookrecipe.code.Recipe;
import com.example.cookrecipe.code.RecipeListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BoardserveFragmant extends Fragment {

    public ListView recipeListView;
    public RecipeListAdapter adapter;
    public List<Recipe> recipeList;
    String UserSearchQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_boardserve, container, false);

        recipeListView = (ListView) view.findViewById(R.id.recipeListView);
        recipeList = new ArrayList<Recipe>();
        adapter = new RecipeListAdapter(getActivity(), recipeList);
        recipeListView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("searchQuery")) {
            String searchQuery = bundle.getString("searchQuery");
            Log.d("SearchQuery", searchQuery);
            UserSearchQuery = searchQuery;
        }

        new RecipeBackgroundTask().execute();

        recipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Recipe selectedRecipe = (Recipe) parent.getAdapter().getItem(position);
                int recipeId = selectedRecipe.getId();
                Log.d("Recipe ID", "Selected Recipe ID: " + recipeId);

                ApplicableFragment applicableFragment = new ApplicableFragment();

                // Set the recipeId
                applicableFragment.setRecipeId(recipeId);

                // Transition to the ApplicableFragment
                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager == null) return;
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, applicableFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }

    class RecipeBackgroundTask extends AsyncTask<Void, Void, String> {

        String target;

        @Override
        protected void onPreExecute() {
            target = "http://192.168.219.103/AndroidDB/RecipeList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String post_data = URLEncoder.encode("UserSearchQuery", "UTF-8") + "=" + URLEncoder.encode(UserSearchQuery, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
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
                int recipeId;
                Drawable recipeImage;
                String recipeTitle, recipeName, recipeDate, recipeImageStr;
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    recipeId = object.getInt("recipeId");
                    recipeTitle = object.getString("recipeTitle");
                    recipeName = object.getString("recipeName");
                    recipeDate = object.getString("recipeDate");

                    // 이미지를 데이터베이스에서 받아옵니다.
                    recipeImageStr = object.getString("recipeImage");
                    // 이미지를 Base64 형식에서 비트맵으로 변환합니다.
                    byte[] decodedBytes = Base64.decode(recipeImageStr, Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                    // 비트맵을 드로워블로 변환합니다.
                    recipeImage = new BitmapDrawable(Resources.getSystem(), bitmap);

                    Recipe recipe = new Recipe(recipeId, recipeImage, recipeTitle, recipeName, recipeDate);
                    recipeList.add(recipe);
                    adapter.notifyDataSetChanged();
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}