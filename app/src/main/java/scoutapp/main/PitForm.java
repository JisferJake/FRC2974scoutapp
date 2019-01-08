package scoutapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class PitForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_form);
    }

    public void toHomeScreen(View view) {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}
