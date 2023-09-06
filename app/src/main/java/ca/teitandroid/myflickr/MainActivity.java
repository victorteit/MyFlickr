package ca.teitandroid.myflickr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ca.teitandroid.myflickr.adapter.PhotoAdapter;
import ca.teitandroid.myflickr.adapter.PhotoRecyclerAdapter;
import ca.teitandroid.myflickr.flickrService.FlickrAPI;
import ca.teitandroid.myflickr.model.Photo;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PhotoRecyclerAdapter.OnPhotoListener {
    ArrayList<Photo> photoList;
    RecyclerView recyclerView;
    PhotoAdapter photoAdapter;


    LinearLayout searchLinearLayout, favoriteLinearLayout;
    TextView homeTextView;
    ImageView homeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        initializeRecyclerView();
        setRecyclerViewLayoutManager();
        initializeLinearLayout();
    }

    private void initialize() {
        recyclerView = findViewById(R.id.recyclerImages);

        FlickrAPI flickrAPI = new FlickrAPI(new FlickrAPI.FetchInterestingPhotoListAsyncTask.Listener() {
            @Override
            public void onFetchComplete(ArrayList<Photo> photos) {
                photoList = photos;
                photoAdapter = new PhotoAdapter(MainActivity.this, photos);
                photoAdapter.setOnItemClickListener(new PhotoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Photo photo) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("PHOTO", photo);
                        startActivity(intent);
                    }
                });
                photoAdapter.setOnItemLongClickListener(new PhotoAdapter.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(Photo photo) {
                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.image_view_big);
                        ImageView imageView = dialog.findViewById(R.id.imageLarge);
                        Glide.with(MainActivity.this)
                                .load(photo.getUrlPhoto())
                                .into(imageView);
                        dialog.show();
                    }
                });

                recyclerView.setAdapter(photoAdapter);
               // savePhotoListToDatabase(photoList);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        flickrAPI.fetchInterestingPhotos();
    }

    @Override
    public void onPhotoClick(int position) {
        Photo photo = photoList.get(position);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("PHOTO", photo);
        startActivity(intent);
    }

    private void initializeRecyclerView() {
        recyclerView = findViewById(R.id.recyclerImages);
    }

    private void setRecyclerViewLayoutManager() {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.searchLinearLayoutId) {
            Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intentSearch);
        } else if (v.getId() == R.id.favoriteLinearLayoutId) {
            Intent intentFavorite = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(intentFavorite);
        }
    }

    public void initializeLinearLayout() {
        homeImageView = findViewById(R.id.homeImageViewId);
        homeTextView = findViewById(R.id.homeTextViewId);
        homeImageView.setColorFilter(Color.WHITE);
        homeTextView.setTextColor(Color.WHITE);

        favoriteLinearLayout = findViewById(R.id.favoriteLinearLayoutId);
        favoriteLinearLayout.setOnClickListener(this);
        searchLinearLayout = findViewById(R.id.searchLinearLayoutId);
        searchLinearLayout.setOnClickListener(this);
    }

//    private void savePhotoListToDatabase(ArrayList<Photo> photoList) {
//        DBManager dbManager = new DBManager(getApplicationContext());
//        DatabaseDemo databaseDemo = new DatabaseDemo(dbManager);
//        ArrayList<PhotoFavorite> favoriteArrayList = convertToPhotoFavorites(photoList);
//        databaseDemo.insertDummyDataToDB(favoriteArrayList);
//    }
//
//    private ArrayList<PhotoFavorite> convertToPhotoFavorites(ArrayList<Photo> photos) {
//        ArrayList<PhotoFavorite> photoFavorites = new ArrayList<>();
//        for (Photo photo : photos) {
//            PhotoFavorite photoFavorite = new PhotoFavorite(photo.getPhotoID(), photo.getAuthorPhoto(), photo.getTitlePhoto(), photo.getDescriptionPhoto(), photo.getViewsPhoto(), photo.getDateTakenPhoto(), photo.getUrlPhoto());
//            photoFavorites.add(photoFavorite);
//        }
//        return photoFavorites;
//    }
}

