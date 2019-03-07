package com.example.android.kfupmsocialspace;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.kfupmsocialspace.Adapter.MessageAdapter;
import com.example.android.kfupmsocialspace.contract.ChatContract;
import com.example.android.kfupmsocialspace.model.Message;
import com.example.android.kfupmsocialspace.contract.UserContract;
import com.example.android.kfupmsocialspace.presenter.ChatPresenter;
import com.example.android.kfupmsocialspace.presenter.userPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements ChatContract.IView {

    ImageButton chatAttachFileBtn;
    ImageButton chatSendBtn;
    EditText chatMsgField;
    static final int PICK_IMAGE_REQUEST = 1;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference dbRef = database.getReference("Message");
    //private FirebaseAuth mAuth = FirebaseAuth.getInstance();
   // private String currentUserId = mAuth.getCurrentUser().getUid();
    private LinearLayoutManager linearLayoutManager;


    private final List<Message> messageList = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private RecyclerView userMessagesList;

    //chat presenter
    private ChatPresenter chatPresenter;
    private String SenderName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        messageAdapter = new MessageAdapter(messageList,this);
        userMessagesList = findViewById(R.id.messages_list);
        linearLayoutManager = new LinearLayoutManager(this);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(messageAdapter);

        //initilaize user presenter
        chatPresenter = new ChatPresenter(this);


        chatAttachFileBtn = findViewById(R.id.attach_file);
        chatSendBtn = findViewById(R.id.send_message);
        chatMsgField = findViewById(R.id.message_string);

        chatAttachFileBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(ChatActivity.this, "File btn clicked", Toast.LENGTH_SHORT).show();
                chooseImage();
            }
        });

        chatSendBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String messageText = chatMsgField.getText().toString().trim();
                chatPresenter.sendMsg(messageText);

                chatMsgField.setText("");
            }
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {


                int itemPosition = viewHolder.getAdapterPosition();
                messageAdapter.notifyItemChanged(itemPosition);

            }
        }).attachToRecyclerView(userMessagesList);

    }

    //getting the messages
    protected void onStart() {

        super.onStart();


        userMessagesList.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft,
                                       int oldTop, int oldRight, int oldBottom) {
                if(bottom < oldBottom)
                    linearLayoutManager.smoothScrollToPosition(userMessagesList,null, messageAdapter.getItemCount());

            }
        });

        dbRef.child("section2").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Message message = dataSnapshot.getValue(Message.class);

                messageList.add(message);
                linearLayoutManager.scrollToPosition(messageList.size() - 1);
                messageAdapter.notifyDataSetChanged();

            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }



    //This part adds the three dots on the top right
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.chat_info:
                intent = new Intent(this, ChatInfoActivity.class);
                break;
            case R.id.chat_files:
                intent = new Intent(this, ChatFilesActivity.class);
                break;
            case R.id.chat_group:
                intent = new Intent(this, ChatGroupActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
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

            Uri ImageUri = data.getData();


            chatPresenter.sendImageMessage(System.currentTimeMillis() + "." + getFileExtension(ImageUri), ImageUri);



        }
    }



    private String getFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton().getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));

    }



}
