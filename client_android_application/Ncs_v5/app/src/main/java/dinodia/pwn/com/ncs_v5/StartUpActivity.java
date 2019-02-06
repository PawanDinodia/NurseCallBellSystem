package dinodia.pwn.com.ncs_v5;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import dinodia.pwn.com.ncs_v5.Fragments.StartupWardInfo;
import dinodia.pwn.com.ncs_v5.Models.Ward;

public class StartUpActivity extends AppCompatActivity{
    Ward ward;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_layout);
        ward=new Ward(getApplicationContext());
        if (ward.getUuid().equals(getResources().getString(R.string.nopatient))) {
            getSupportFragmentManager().beginTransaction().replace(R.id.startup_container,new StartupWardInfo()).commit();
        }else{
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
}
