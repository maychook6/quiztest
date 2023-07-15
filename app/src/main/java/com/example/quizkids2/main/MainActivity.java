package com.example.quizkids2.main;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.quizkids2.R;
import com.example.quizkids2.main.account.AccountFragment;
import com.example.quizkids2.main.account.LoginFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainerView, new AccountFragment()).commit();
        }
    }

}