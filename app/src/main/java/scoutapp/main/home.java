package scoutapp.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class home extends AppCompatActivity {

    boolean testBool = false;
    int incrementNumber = 0;
    public static final String EXTRA_MESSAGE = "com.example.frcScoutApp.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, hi.class);
        EditText editText = findViewById(R.id.editText2);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void incrementValue(View view){
        incrementNumber++;
        display(incrementNumber, testBool);
    }

    public void decrementValue(View view){
        incrementNumber--;
        display(incrementNumber, testBool);
    }

    public void setTrue(View view){
        testBool = true;
        display(incrementNumber, testBool);
    }

    public void setFalse(View view){
        testBool = false;
        display(incrementNumber, testBool);
    }

    public void display(int number, boolean test){
        TextView numberIncrement = findViewById(R.id.numberIncrement);
        TextView trueFalse = findViewById(R.id.boolDisplay);
        numberIncrement.setText("" + number);
        trueFalse.setText("" + test);

    }
}
