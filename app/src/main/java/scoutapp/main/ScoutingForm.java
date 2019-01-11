package scoutapp.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicInteger;

public class ScoutingForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_form);
    }

//    Example of Sending Information between intents
//    public void sendMessage(View view){
//        Intent intent = new Intent(this, HomeScreen.class);
//        EditText editText = findViewById(R.id.editText2);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }

    TextView autoCSC = findViewById(R.id.autoCSC);
    TextView autoCSH = findViewById(R.id.autoCSH);
    TextView autoRC = findViewById(R.id.autoRC);
    TextView autoRH = findViewById(R.id.autoRH);

    AtomicInteger csc = new AtomicInteger(0);
    AtomicInteger csh = new AtomicInteger(0);
    AtomicInteger rc = new AtomicInteger(0);
    AtomicInteger rh = new AtomicInteger(0);


    public void incrementValue(AtomicInteger integer){
        integer.getAndIncrement();
    }

    public void decrementValue(AtomicInteger integer){
        integer.getAndDecrement();
    }

    public void incCSC(View view) {
        incrementValue(csc);
        autoCSC.setText(csc.intValue());
    }
    public void incCSH(View view) {
        incrementValue(csh);
        autoCSH.setText(csh.intValue());
    }
    public void incRC(View view) {
        incrementValue(rc);
        autoRC.setText(rc.intValue());
    }
    public void incRH(View view) {
        incrementValue(rh);
        autoRH.setText(rh.intValue());
    }
    public void decCSC(View view) {
        incrementValue(csc);
        autoCSC.setText(csc.intValue());
    }
    public void decCSH(View view) {
        incrementValue(csh);
        autoCSH.setText(csh.intValue());
    }
    public void decRC(View view) {
        incrementValue(rc);
        autoRC.setText(rc.intValue());
    }
    public void decRH(View view) {
        incrementValue(rh);
        autoRH.setText(rh.intValue());
    }

}
