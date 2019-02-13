package com.example.android.kfupmsocialspace;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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

    static final int PICK_IMAGE_REQUEST = 1;

    private ImageView myItemImage;

    private Uri ImageURi;

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
        myItemImage = findViewById(R.id.item_picture);

        myItemImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                chooseImage();
            }
        });

        Button addMarketItemButton = findViewById(R.id.add_item_to_market);
        addMarketItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    /*
    *
    * this a dialog to choose from gallery or take picure with camera
    *
    */

    private void chooseImage(){

        final CharSequence[] items = {"CAMERA", "GALLERY", "CANCEL"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SELECT FROM");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if(items[item].equals("CAMERA")){
                    FromCamera();
                }
                else if (items[item].equals("GALLERY")){
                    FromGallery();
                }
                else{

                    dialog.dismiss();

                }
            }
        });

        builder.show();



    }

    /*
     *
     * run the gallery method
     *
     */

    private void FromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /*
     *
     * run the camera method
     *
     */
    private void FromCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /*
     *
     * show the chooser image.
     *
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null){

            ImageURi = data.getData();

            myItemImage.setImageURI(ImageURi);


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

}
