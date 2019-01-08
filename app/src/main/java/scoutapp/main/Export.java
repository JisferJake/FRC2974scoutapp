package scoutapp.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Export extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
    }

    public void toHomeScreen(View view) {
        Intent intent = new Intent(this, HomeScreen.class);
        startActivity(intent);
    }
}
