package edu.rosehulman.lujasaa.comicviewer;

/**
 * Created by lujasaa on 1/13/2016.
 */

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A placeholder fragment containing a simple view.
 */
public class ComicFragment extends Fragment{
    private ComicWrapper mComicWrapper;
    private Bitmap mBitmap;
    private boolean mBmLoaded= false;
    private PhotoViewAttacher mAttacher;


    private static final String ARG_COMIC_WRAPPER = "comic_wrapper";
    private ImageView mImgView;
    private TextView mTitleTV;

    public ComicFragment() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mouse_over_text){
            showMouseOverDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Displayed when you press the info button
     * shows some info about the comic
     */
    private void showMouseOverDialog() {
        DialogFragment df = new DialogFragment(){
            @NonNull
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(getContext().getString(R.string.mouseoverTitle)+ mComicWrapper.getXkcdIssue());
                builder.setMessage(mComicWrapper.getComic().getAlt());
                return builder.create();
            }

            @Override
            public void onDestroyView() {
                if(getDialog() != null && getRetainInstance()){
                    getDialog().setDismissMessage(null);
                }
                super.onDestroyView();
            }
        };
        df.setRetainInstance(true);
        df.show(getFragmentManager(),"dialog");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(getArguments() != null){
            mComicWrapper = getArguments().getParcelable(ARG_COMIC_WRAPPER);
            //below is the link to publicly hosted xckd comics
            String urlString = String.format("https://xckd.com/%d/info.0.json", mComicWrapper.getXkcdIssue());
            new GetComicTask(this).execute(urlString);
        }
    }


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ComicFragment newInstance(ComicWrapper comicWrapper) {
        ComicFragment fragment = new ComicFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_COMIC_WRAPPER, comicWrapper);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        Log.d("Color", "ComicsPagerAdapter: " + mComicWrapper.getColor());
        rootView.setBackgroundColor(mComicWrapper.getColor());
        mTitleTV = (TextView) rootView.findViewById(R.id.title_textview);
        //the way i did it, the comit isnt loaded on the first time that onCreate is called
        //so check for null first time.
        //on PostExecute, the comic is set, and the textview is updated (see below)
//        mUrlTextView = (TextView)rootView.findViewById(R.id.url_textview);
        mImgView = (ImageView)rootView.findViewById(R.id.imgView);
        if(mComicWrapper.getComic() != null){
//            mUrlTextView.setText(mComicWrapper.getComic().getImg());

            mTitleTV.setText(mComicWrapper.getComic().getTitle());
        }
        mAttacher = new PhotoViewAttacher(mImgView);
        if(mBmLoaded){
            mImgView.setImageBitmap(mBitmap);
            mAttacher.update();
        }

        return rootView;
    }

    /**
     * Loads the comic image (off the ui thread)
     */
    public class GetImageTask extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            InputStream in = null;
            String urlString = strings[0];
            Bitmap bitmap = null;
            try {
                in = new URL(urlString).openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bitmap = BitmapFactory.decodeStream(in);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mBitmap = bitmap;
            mBmLoaded = true;
            mImgView.setImageBitmap(mBitmap);
            mAttacher.update();
        }
    }

    /**
     * Load comics (off ui thread)
     * This will call the getGetImage task, since the image url
     * isnt known until this finishes
     */
    public class GetComicTask extends AsyncTask<String ,Void, Comic> {
        private ComicFragment mFragment;
        public GetComicTask(ComicFragment fragment){
            this.mFragment = fragment;
        }

        @Override
        protected Comic doInBackground(String... urlStrings) {
            String urlString = urlStrings[0];
            Comic comic = null;
            try{
                // Get the json -> comic class
                comic = new ObjectMapper().readValue(new URL(urlString),Comic.class);
            } catch (IOException e) {
                Log.d("Tag", "ERROR:" + e.toString());
            }
            return comic;
        }

        @Override
        protected void onPostExecute(Comic comic) {
            super.onPostExecute(comic);
            Log.d("finished", "onPostExecute: " + comic.getImg());
            mComicWrapper.setComic(comic);
            mTitleTV.setText(mComicWrapper.getComic().getTitle());
//            mUrlTextView.setText(mComicWrapper.getComic().getImg());
            new GetImageTask().execute(comic.getImg());
        }
    }
}
