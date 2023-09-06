package ca.teitandroid.myflickr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.teitandroid.myflickr.adapter.PhotoAdapter;
import ca.teitandroid.myflickr.db.DBManager;
import ca.teitandroid.myflickr.db.DatabaseDemo;
import ca.teitandroid.myflickr.model.Photo;
import ca.teitandroid.myflickr.model.PhotoFavorite;

public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener, PhotoAdapter.OnItemClickListener{
    LinearLayout searchLinearLayout, homeLinearLayout;
    TextView favoriteTextView;
    ImageView favoriteImageView;
    Photo photo;
    RecyclerView favoritePhotoRecyclerView;
    PhotoAdapter photoAdapter;
    DBManager dbManager;
    ArrayList<Photo> photoList;
    ArrayList<PhotoFavorite> favoriteArrayList, favoriteArrayListFromDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initialize();
        databaseDemo();
        searchPhoto();
    }

    private void initialize(){
        favoritePhotoRecyclerView = findViewById(R.id.recyclerImagesFavorite);
        homeLinearLayout = findViewById(R.id.homeLinearLayoutId);
        homeLinearLayout.setOnClickListener(this);;
        searchLinearLayout = findViewById(R.id.searchLinearLayoutId);
        searchLinearLayout.setOnClickListener(this);
        favoriteImageView = findViewById(R.id.favortieImageViewId);
        favoriteTextView = findViewById(R.id.favoriteTextViewId);
        favoriteImageView.setColorFilter(Color.WHITE);
        favoriteTextView.setTextColor(Color.WHITE);
    }
    public void onClick(View v) {
        if (v.getId() == R.id.homeLinearLayoutId) {
            Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.searchLinearLayoutId){
                Intent intentFavorite = new Intent(FavoriteActivity.this, SearchActivity.class);
                startActivity(intentFavorite);
        }
    }
    public void databaseDemo() {
        dbManager = new DBManager(getApplicationContext());
        favoriteArrayList = new ArrayList<>();
        DatabaseDemo databaseDemo = new DatabaseDemo(dbManager);
        favoriteArrayListFromDB = databaseDemo.readFromDB();
        if (favoriteArrayListFromDB == null) {
            favoriteArrayListFromDB = new ArrayList<>();
        }
    }

    @Override
    public void onItemClick(Photo photo) {
        Intent intent = new Intent(FavoriteActivity.this, DetailActivity.class);
        intent.putExtra("PHOTO", photo);
        startActivity(intent);

    }
    private void searchPhoto(){
        photoList = new ArrayList<>();
        for (PhotoFavorite favorite : favoriteArrayListFromDB){
            photo = new Photo(favorite.getPhotoID(), favorite.getAuthorPhoto(), favorite.getTitlePhoto(), favorite.getDescriptionPhoto(),
                    favorite.getViewsPhoto(), favorite.getDateTakenPhoto(), favorite.getUrlPhoto());
            photoList.add(photo);
        }
        photoAdapter = new PhotoAdapter(this, photoList);
        favoritePhotoRecyclerView.setAdapter(photoAdapter);
        favoritePhotoRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        photoAdapter.setOnItemClickListener(this);
    }
}