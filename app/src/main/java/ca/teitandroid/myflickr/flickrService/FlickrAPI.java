package ca.teitandroid.myflickr.flickrService;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import ca.teitandroid.myflickr.MainActivity;
import ca.teitandroid.myflickr.model.Photo;

public class FlickrAPI {

    static final String BASE_URL = "https://api.flickr.com/services/rest";
    static final String API_KEY = "21c5c0e4f5ba3817707fa3f62c1c3b21";
    static final String TAG = "WebServicesFunTag";

    ArrayList<Photo> photoList;
    private FlickrAPI.FetchInterestingPhotoListAsyncTask.Listener listener;

    public FlickrAPI(FlickrAPI.FetchInterestingPhotoListAsyncTask.Listener listener) {
        this.listener = listener;
    }
    public void fetchInterestingPhotos() {
        String url = constructInterestingPhotoListURL();
        Log.d(TAG, "fetchIntertestingPhotos:" + url);
        FetchInterestingPhotoListAsyncTask asyncTask = new FetchInterestingPhotoListAsyncTask(listener);
        asyncTask.execute(url);
    }

    public String constructInterestingPhotoListURL() {
        String url = BASE_URL;
        url += "?method=flickr.interestingness.getList";
        url += "&api_key=" + API_KEY;
        url += "&format=json";
        url += "&jsoncallback=1";
        url += "&extras=description,owner_name,views,date_taken,title,url_h";
        return url;
    }

    public static class FetchInterestingPhotoListAsyncTask extends AsyncTask<String, Void, ArrayList<Photo>> {
        public interface Listener {
            void onFetchComplete(ArrayList<Photo> photos);
            void onError(Exception e);
        }

        private Listener listener;

        public FetchInterestingPhotoListAsyncTask(Listener listener) {
            this.listener = listener;
        }
        @Override
        protected void onPostExecute(ArrayList<Photo> photos) {
            super.onPostExecute(photos);
            if (listener != null) {
                listener.onFetchComplete(photos);
            }
        }


        @Override
        protected ArrayList<Photo> doInBackground(String... strings) {
            String url = strings[0];
            ArrayList<Photo> photoList = new ArrayList<>();
            try {
                URL urlObject = new URL(url);
                HttpsURLConnection urlConnection = (HttpsURLConnection) urlObject.openConnection();
                StringBuilder result = new StringBuilder();
                char[] buffer = new char[1024];
                int read;
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                while ((read = reader.read(buffer)) != -1) {
                    result.append(buffer, 0, read);
                }
                String jsonResult = result.toString();
                String json = jsonResult.substring(2, jsonResult.length() -1);
                JSONObject jsonObject = new JSONObject(json);
                JSONObject photosObjet = jsonObject.getJSONObject("photos");
                JSONArray photoArray = photosObjet.getJSONArray("photo");
                for (int i = 0; i < photoArray.length(); i++) {
                    JSONObject singlePhotoObject = photoArray.getJSONObject(i);
                    Photo photoSingle = parseInterestingPhoto(singlePhotoObject);
                    if (photoSingle != null) {
                        photoList.add(photoSingle);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return photoList;
        }

        private Photo parseInterestingPhoto(JSONObject singlePhotoObject) {
            Photo photoSingle = null;
            try {
                int id = singlePhotoObject.getInt("id");
                String description = singlePhotoObject.getString("description");
                String views = singlePhotoObject.getString("views");
                String ownerName = singlePhotoObject.getString("ownername");
                String dateTaken = singlePhotoObject.getString("datetaken");
                String title = singlePhotoObject.getString("title");
                String urlPhoto = singlePhotoObject.optString("url_h");
                photoSingle = new Photo(id, ownerName, title, description, views, dateTaken, urlPhoto);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return photoSingle;
        }
    }
}

