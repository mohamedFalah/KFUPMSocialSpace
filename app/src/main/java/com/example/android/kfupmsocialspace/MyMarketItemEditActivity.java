package com.example.android.kfupmsocialspace;

import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.contract.MarketitemContract;
import com.example.android.kfupmsocialspace.model.MarketItem;
import com.example.android.kfupmsocialspace.presenter.MarketItemPresenter;
import com.squareup.picasso.Picasso;

public class MyMarketItemEditActivity extends AppCompatActivity implements MarketitemContract.IView, TextWatcher {


    static final int PICK_IMAGE_REQUEST = 1;

    private Uri ImageUri;

    private EditText itemName, itemPrice, itemDescription;
    private Spinner itemCategory;
    private ImageView itemImg;
    private Button confirm, cancel;
    private ProgressBar progressBar;


    private boolean textChangeHappened = false;
    private boolean ImageChangedHappened = false;
    private boolean SpinnerChanged = false;


    ArrayAdapter<String> adapter;

    private MarketItem marketItem;

    MarketItemPresenter marketItemPresenter;


    //needed for nothing
    int spinnerValueChange = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_market_item_edit);


        Bundle data = getIntent().getExtras();
        marketItem =  data.getParcelable("clickedItem");



        marketItemPresenter = new MarketItemPresenter(this);

        itemName = findViewById(R.id.title_string);
        itemPrice =  findViewById(R.id.price_value);
        itemDescription = findViewById(R.id.itemDescription);
        itemImg = findViewById(R.id.item_picture);
        itemCategory = findViewById(R.id.itemCategory);
        progressBar = findViewById(R.id.progress_bar);
        confirm = findViewById(R.id.confirm_item_edit);
        cancel = findViewById(R.id.cancel_item_edit);

        //spinner content
        //Add item to the category spinner
        //https://stackoverflow.com/questions/5241660/how-can-i-add-items-to-a-spinner-in-android
        String[] arraySpinner = new String[]{
                "-Category-", "Books", "Gaming", "Tools"
        };


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arraySpinner);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemCategory.setAdapter(adapter);





        //set the values
        itemName.setText(marketItem.getItemName());
        itemPrice.setText(marketItem.getItemPrice());
        itemDescription.setText(marketItem.getItemDescription());
        Picasso.with(this).load(Uri.parse(marketItem.getItemPicture())).fit().centerCrop().into(itemImg);
        //for spinner value
        final int spinnerValue = adapter.getPosition(marketItem.getItemCategory());
        itemCategory.setSelection(spinnerValue);



        //listener to spinner
        itemCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinnerValueChange != 1)
                    SpinnerChanged = true;

                spinnerValueChange++;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        //listener to detect change
        itemName.addTextChangedListener(this);
        itemPrice.addTextChangedListener(this);
        itemDescription.addTextChangedListener(this);



        //choose new Image

        itemImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });


        //cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyMarketItemViewActivity.class);
                intent.putExtra("clickedItem", marketItem);
                startActivity(intent);
                finish();
            }
        });

        //confirm Button

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemN = itemName.getText().toString().trim();
                String itemP = itemPrice.getText().toString().trim();
                String itemCat = itemCategory.getSelectedItem().toString();
                String itemDes = itemDescription.getText().toString().trim();



                if(ImageChangedHappened){

                    marketItemPresenter.uploadItemImage(System.currentTimeMillis() + "." + getFileExtension(ImageUri),
                            ImageUri, itemN, itemP, itemCat, itemDes, marketItem.getItemID());



                    textChangeHappened = false;
                    ImageChangedHappened =false;

                } else if (textChangeHappened ) {

                    marketItem.setItemName(itemN);
                    marketItem.setItemPrice(itemP);
                    marketItem.setItemDescription(itemDes);
                    marketItem.setItemCategory(itemCat);

                    marketItemPresenter.uploadMarketItem(marketItem);

                    Toast.makeText(MyMarketItemEditActivity.this, "your Item Updated", Toast.LENGTH_SHORT).show();
                    textChangeHappened =false;


                }else if(SpinnerChanged){

                    marketItem.setItemCategory(itemCat);
                    marketItemPresenter.uploadMarketItem(marketItem);

                    Toast.makeText(MyMarketItemEditActivity.this, "your Item Updated", Toast.LENGTH_SHORT).show();
                    SpinnerChanged = false;

                }else {

                    Toast.makeText(MyMarketItemEditActivity.this, "No thing modified", Toast.LENGTH_SHORT).show();

                }
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
            itemImg.setImageURI(ImageUri);
            ImageChangedHappened = true;


        }
    }


    private String getFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton().getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));

    }


    //check text edit changed
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {

        textChangeHappened = true;
    }




    @Override
    public void progressBarValue(int progress) {


        ValueAnimator animator = ValueAnimator.ofInt(progressBar.getProgress(), progress);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                progressBar.setProgress(value);
            }
        });

        animator.start();

        //end it if progress is finsihed
        if(progressBar.getProgress() == 100){
            Toast.makeText(this, "Item uploaded", Toast.LENGTH_LONG).show();
            ImageChangedHappened = false;

        }

    }

    //not used functions
    @Override
    public void reservationStatus(boolean status) { }
}
