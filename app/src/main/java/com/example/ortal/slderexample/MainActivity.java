/**
 * AndTinder v0.1 for Android
 *
 * @Author: Enrique López Mañas <eenriquelopez@gmail.com>
 * http://www.lopez-manas.com
 *
 * TAndTinder is a native library for Android that provide a
 * Tinder card like effect. A card can be constructed using an
 * image and displayed with animation effects, dismiss-to-like
 * and dismiss-to-unlike, and use different sorting mechanisms.
 *
 * AndTinder is compatible with API Level 13 and upwards
 *
 * @copyright: Enrique López Mañas
 * @license: Apache License 2.0
 */

package com.example.ortal.slderexample;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.example.ortal.slderexample.model.CardModel;
import com.example.ortal.slderexample.view.CardContainer;
import com.example.ortal.slderexample.view.SimpleCardStackAdapter;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends Activity {

    private static String MEDIA_PATH = null;

    /**
     * This variable is the container that will host our cards
     */
    private CardContainer mCardContainer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mainlayout);

        mCardContainer = (CardContainer) findViewById(R.id.layoutview);

        Resources r = getResources();

        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this);

        MEDIA_PATH = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera";
        File home = new File(MEDIA_PATH);
        File[] listFiles = home.listFiles();
        int i = 1;
        Bitmap bit = null;
        for (File file : listFiles) {
            if (!file.isFile()) {
                continue;
            }
            try {

//                bit = BitmapFactory.decodeFile(file.getPath());
                bit =Bitmap.createScaledBitmap(BitmapFactory.decodeFile(file.getPath()),1200,1600,false);
//                bit =Bitmap.createBitmap(BitmapFactory.decodeFile(file.getPath()));
//                Drawable drawable = Drawable.createFromPath(file.getPath());
                adapter.add(new CardModel(file.getName(), "Description goes here", bit));
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), (CharSequence) e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        }

        CardModel cardModel = new CardModel("Title1", "Description goes here",
                bit);
        cardModel.setOnClickListener(new CardModel.OnClickListener() {
            @Override
            public void OnClickListener() {
                Toast.makeText(getApplicationContext(), "I am pressing the card", Toast.LENGTH_LONG)
                        .show();
            }
        });

        cardModel.setOnCardDimissedListener(new CardModel.OnCardDimissedListener() {
            @Override
            public void onLike() {
                Toast.makeText(getApplicationContext(), "I like the card", Toast.LENGTH_SHORT)
                        .show();
                //  Log.i("Swipeable Cards","I like the card");
            }

            @Override
            public void onDislike() {
                Toast.makeText(getApplicationContext(), "I dislike the card", Toast.LENGTH_SHORT)
                        .show();
//                Log.i("Swipeable Cards","I dislike the card");
            }
        });

        adapter.add(cardModel);

        mCardContainer.setAdapter(adapter);
    }
}
