package com.example.cookrecipe.Fragment;

import android.content.ContentValues;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cookrecipe.R;
import com.example.cookrecipe.code.Notice;
import com.example.cookrecipe.code.Recipe;
import com.example.cookrecipe.code.RequestHttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ApplicableFragment extends Fragment {

    private int recipeId;
    TextView Title, Category, Servings, Difficultly, Contents, ingredients;
    ImageView imageView_server;
    Bitmap bitmap;
    Button scrap;

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_applicable, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Title = view.findViewById(R.id.Title);
        Category = view.findViewById(R.id.category);
        Servings = view.findViewById(R.id.servings);
        Difficultly = view.findViewById(R.id.difficultly);
        ingredients = view.findViewById(R.id.ingredients);
        Contents = view.findViewById(R.id.contents);
        imageView_server = view.findViewById(R.id.imageView);
        scrap = view.findViewById(R.id.scrap);

        getData(recipeId);
    }

    private void getData(int recipeId) {
        String url = "http://192.168.0.14/AndroidDB/Applicable.php";
        ContentValues contentValues = new ContentValues();
        contentValues.put("recipeId", String.valueOf(recipeId));

        selectDatabase selectDatabase = new selectDatabase(url, contentValues);
        try {
            String result = selectDatabase.execute().get();
            JSONObject jsonObject = new JSONObject(result);

            Title.setText(jsonObject.getString("title"));
            Category.setText(jsonObject.getString("category"));
            Servings.setText(jsonObject.getString("servings"));
            Difficultly.setText(jsonObject.getString("difficulty"));
            ingredients.setText(jsonObject.getString("ingredients"));
            Contents.setText(jsonObject.getString("contents"));

            // Get the image URL
            String imageUrl = jsonObject.getString("image_url");
            // Download the image
            loadImageFromUrl(imageUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class selectDatabase extends AsyncTask<Void, Void, String> {

        private String url1;
        private ContentValues values1;
        String result1; // 요청 결과를 저장할 변수.

        public selectDatabase(String url, ContentValues contentValues) {
            this.url1 = url;
            this.values1 = contentValues;
        }

        @Override
        protected String doInBackground(Void... params) {
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result1 = requestHttpURLConnection.request(url1, values1); // 해당 URL로 부터 결과물을 얻어온다.
            return result1;
        }
    }

    private void loadImageFromUrl(String imageUrl) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(imageUrl);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();

        try {
            thread.join();
            imageView_server.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}