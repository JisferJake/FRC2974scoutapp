package scoutapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
    }

    public void toScoutingForm(View view){
        Intent intent = new Intent(this, ScoutingForm.class);
        startActivity(intent);
    }

    public void toPitForm(View view) {
        Intent intent = new Intent(this, PitForm.class);
        startActivity(intent);
    }

    public void toExport(View view) {
        Intent intent = new Intent(this, Export.class);
        startActivity(intent);
    }
}
