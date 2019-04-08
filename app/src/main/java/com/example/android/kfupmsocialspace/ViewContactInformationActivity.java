package com.example.android.kfupmsocialspace;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.kfupmsocialspace.model.MarketItem;
import com.example.android.kfupmsocialspace.model.User;
import com.example.android.kfupmsocialspace.presenter.MarketItemPresenter;
import com.example.android.kfupmsocialspace.presenter.userPresenter;

public class ViewContactInformationActivity extends AppCompatActivity {


    boolean resever = false;
    private TextView name, email, Phone;
    private MarketItem marketItem;
    private User user;
    private userPresenter userPresenter;
    private MarketItemPresenter marketItemPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact_information);

        //presenter
        userPresenter = new userPresenter();
        marketItemPresenter = new MarketItemPresenter();


        //intilization

        name = findViewById(R.id.contact_information_student_name);
        email = findViewById(R.id.contact_information_student_email);
        Phone = findViewById(R.id.contact_information_student_phone);

        //recive data
        Bundle data = getIntent().getExtras();
        marketItem = data.getParcelable("owner");

        //to check from where the item recieved
        boolean resever = data.getBoolean("showReseverinfo");

        if (resever) {
            userPresenter.getUserObject(marketItem.getOwnerID());


        } else {
            marketItemPresenter.getReserverID(marketItem);

            user = userPresenter.getUserModel();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                user = userPresenter.getUserModel();

                name.setText(user.getUserFullName());
                email.setText(user.getEmail());

                if (user.getPhone() != null)
                    Phone.setText(user.getPhone());
                else
                    Phone.setText("user has no phone number");

            }
        }, 400);
    }


    @Override
    protected void onStart() {
        super.onStart();


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
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
