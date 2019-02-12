package com.example.android.kfupmsocialspace;

import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMarketItemActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    String mCurrentPhotoPath;

    //https://stackoverflow.com/questions/5263068/how-to-get-android-device-features-using-package-manager
    //https://developer.android.com/reference/android/content/pm/PackageManager#hasSystemFeature(java.lang.String)
    //For the Camera
    public final static boolean isFeatureAvailable(Context context, String feature) {
        final PackageManager packageManager = context.getPackageManager();
        final FeatureInfo[] featuresList = packageManager.getSystemAvailableFeatures();
        for (FeatureInfo f : featuresList) {
            if (f.name != null && f.name.equals(feature)) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_market_item);

        //Add item to the category spinner
        //https://stackoverflow.com/questions/5241660/how-can-i-add-items-to-a-spinner-in-android
        String[] arraySpinner = new String[]{
                "-Category-", "Books", "Gaming", "Tools"
        };

        Spinner s = (Spinner) findViewById(R.id.category_spinner_string);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);

        //For adding an image to item from camera or gallery.
        //https://developer.android.com/training/camera/photobasics#TaskPath
        ImageView myItemImage = findViewById(R.id.item_picture);
        myItemImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isFeatureAvailable(getBaseContext(), PackageManager.FEATURE_CAMERA)) {
                    dispatchTakePictureIntent();
                }
            }
        });

        Button addMarketItemButton = findViewById(R.id.add_item_to_market);
        addMarketItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }


//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            ImageView myItemImage = findViewById(R.id.item_picture);
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            myItemImage.setImageBitmap(imageBitmap);
//        }
//    }

    //The above code is similar and might be better.

    //https://stackoverflow.com/questions/10165302/dialog-to-pick-image-from-gallery-or-from-camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        ImageView imageview = findViewById(R.id.item_picture);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageview.setImageURI(selectedImage);
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageview.setImageURI(selectedImage);
                }
                break;
        }
    }
}
