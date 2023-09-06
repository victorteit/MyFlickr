package ca.teitandroid.myflickr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ca.teitandroid.myflickr.db.DBManager;
import ca.teitandroid.myflickr.db.DatabaseDemo;
import ca.teitandroid.myflickr.model.Photo;
import ca.teitandroid.myflickr.model.PhotoFavorite;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    PhotoFavorite photoFavorite;
    Photo photoFromMainActivity;
    ImageView imageView, emptyHeartImageView;
    TextView textTitle, textAuthor, textDateTaken, textViewsCount, textDescription;
    DBManager dbManager;
    DatabaseDemo databaseDemo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        dbManager = (DBManager) getApplication();
        databaseDemo = new DatabaseDemo(dbManager);
        initialize();
        try {
            getIntentFromMainActivity();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void initialize() {
        imageView = findViewById(R.id.imageView);
        textTitle = findViewById(R.id.textTitle);
        textAuthor = findViewById(R.id.textOwner);
        textDescription = findViewById(R.id.textDescription);
        textViewsCount = findViewById(R.id.textCountView);
        textDateTaken = findViewById(R.id.textDateTaken);
        emptyHeartImageView = findViewById(R.id.heartImage);
        emptyHeartImageView.setOnClickListener(this);
        emptyHeartImageView.setColorFilter(Color.WHITE);
        textDescription.setMovementMethod(LinkMovementMethod.getInstance());

//        dbManager = (DBManager) getApplication();
//        databaseDemo = new DatabaseDemo(dbManager);
    }

    public void getIntentFromMainActivity() throws ExecutionException, InterruptedException {
        Intent intent = getIntent();
        photoFromMainActivity = (Photo) intent.getParcelableExtra("PHOTO");
        if (photoFromMainActivity != null) {
            String description = photoFromMainActivity.getDescription();
            textDescription.setText(description);
            String htmlDescription = photoFromMainActivity.getDescriptionPhoto();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textDescription.setText(Html.fromHtml(htmlDescription, Html.FROM_HTML_MODE_COMPACT));
            } else {
                textDescription.setText(Html.fromHtml(htmlDescription));
            }
            textTitle.setText(photoFromMainActivity.getTitlePhoto());
            textAuthor.setText("Owner: " + photoFromMainActivity.getAuthorPhoto());
            textDescription.setText(photoFromMainActivity.getDescriptionPhoto());
            textViewsCount.setText(String.valueOf(photoFromMainActivity.getViewsPhoto()));
            textDateTaken.setText(photoFromMainActivity.getDateTakenPhoto());

            Glide.with(this)
                    .load(photoFromMainActivity.getUrlPhoto())
                    .into(imageView);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.heartImage) {
            if (checkForPhotoInDB(photoFromMainActivity)) {
                createAlertDialogForDeleting(photoFromMainActivity);
            } else {
                createAlertDialogForAdding(photoFromMainActivity);
            }
        }
    }

    public void createAlertDialogForAdding(Photo photo) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Add!");
        alertDialogBuilder.setMessage(" Are You sure you want to add " + photo.getTitlePhoto() + " to your favorites?");
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                emptyHeartImageView.setColorFilter(Color.RED);
                PhotoFavorite photoFavorite = new PhotoFavorite(photo.getPhotoID(), photo.getAuthorPhoto(), photo.getTitlePhoto(), photo.getDescriptionPhoto(), photo.getViewsPhoto(), photo.getDateTakenPhoto(), photo.getUrlPhoto());
                databaseDemo.insertDataToDB(photoFavorite);
                Toast.makeText(dbManager, "Photo by" + photo.getAuthorPhoto() + " successfully got added from DB", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }

    public void createAlertDialogForDeleting(Photo photo) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Delete!");
        alertDialogBuilder.setMessage(" Are You sure you want to delete " + photo.getTitlePhoto());
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                emptyHeartImageView.setColorFilter(Color.WHITE);
                databaseDemo.deleteFromDB(photo.getPhotoID());
                Toast.makeText(dbManager, "Photo " + photo.getTitlePhoto() + " successfully got remove from DB", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialogBuilder.create();
        alertDialogBuilder.show();
    }

    public boolean checkForPhotoInDB(Photo photo) {
        boolean check = false;
        ArrayList<PhotoFavorite> favorites = (ArrayList<PhotoFavorite>) databaseDemo.readFromDB();
        //ArrayList<PhotoFavorite> favorites = dbManager.cursorToArrayList((Cursor) databaseDemo.readFromDB());
        if (favorites.size() > 0) {
            for (PhotoFavorite photoFavorite : favorites) {
                if (photoFavorite.getPhotoID() == photo.getPhotoID()) {
                    check = true;
                    break; // Exit the loop if a match is found
                }
            }
        }
        return check;
    }
}