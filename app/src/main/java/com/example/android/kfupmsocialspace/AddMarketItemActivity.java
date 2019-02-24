package com.example.android.kfupmsocialspace;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.contract.MarketitemContract;
import com.example.android.kfupmsocialspace.presenter.MarketItemPresenter;

public class AddMarketItemActivity extends AppCompatActivity implements MarketitemContract.IView {

    static final int PICK_IMAGE_REQUEST = 1;
    String mCurrentPhotoPath;
    private ImageView myItemImage;
    private EditText imageName, imagePrice, itemDescription;
    private Button addMarketItemButton;
    private Spinner spinner;
    private ProgressBar progressBar;



    //uploading progress
    private int progress = 0;


    //presenter
    private Uri ImageUri;
    private MarketItemPresenter marketItemPresenter;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_market_item);

        ///intialze presenter
        marketItemPresenter = new MarketItemPresenter(this);
        addMarketItemButton = findViewById(R.id.add_item_to_market);
        spinner = (Spinner) findViewById(R.id.itemCategory);
        progressBar = findViewById(R.id.progress_bar);
        myItemImage = findViewById(R.id.item_picture);
        imageName = findViewById(R.id.title_string);
        imagePrice = findViewById(R.id.price_value);
        itemDescription = findViewById(R.id.itemDescription);

        //Add item to the category spinner
        //https://stackoverflow.com/questions/5241660/how-can-i-add-items-to-a-spinner-in-android
        String[] arraySpinner = new String[]{
                "-Category-", "Books", "Gaming", "Tools"
        };


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //For adding an image to item from camera or gallery.
        //https://developer.android.com/training/camera/photobasics#TaskPath


        myItemImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                chooseImage();
            }
        });

        addMarketItemButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        uploadMarketItem();

                    }
                });
    }

    
    /*
     * this a dialog to choose from gallery or take picure with camera
     */

    private void chooseImage() {

        final CharSequence[] items = {"CAMERA", "GALLERY", "CANCEL"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("SELECT FROM");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("CAMERA")) {
                    FromCamera();
                } else if (items[item].equals("GALLERY")) {
                    FromGallery();
                } else {

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

    private void FromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    /*
     *
     * run the camera method
     *
     */
    private void FromCamera() {
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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null) {

            ImageUri = data.getData();

            myItemImage.setImageURI(ImageUri);

        }
    }


    private String getFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton().getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    private void uploadMarketItem() {

        if (ImageUri != null && imageName != null && imagePrice != null && itemDescription != null && spinner != null) {

            //addMarketItemButton.setClickable(false);

            String itemName = imageName.getText().toString().trim();
            String itemPrice = imagePrice.getText().toString().trim();
            String itemCategory = spinner.getSelectedItem().toString();
            String itemDescription = this.itemDescription.getText().toString().trim();


            marketItemPresenter.uploadItemImage(System.currentTimeMillis() + "." + getFileExtension(ImageUri),
                    ImageUri, itemName, itemPrice, itemCategory, itemDescription);

        } else {

            Toast.makeText(this, "Fill All Fields ", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void progressBarValue(int progress) {

        progressBar.setProgress(progress);

        //end it if progress is finsihed
        if(progress == 100){
            Toast.makeText(this, "Item uploaded", Toast.LENGTH_LONG).show();
            finish();
        }


    }

    /*
            Not used but implemented by interface
     */
    @Override
    public void reservationStatus(boolean status) {

    }

}