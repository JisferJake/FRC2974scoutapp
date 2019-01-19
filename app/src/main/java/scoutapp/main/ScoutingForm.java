package scoutapp.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoutingForm extends AppCompatActivity {

    private Map<Integer, AtomicInteger> buttonsToAtomicInt;
    private Map<Integer, Integer> buttonsToTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_form);

        int[] plusButtons = {R.id.plusButtonACSC, R.id.plusButtonACSH, R.id.plusButtonARH, R.id.plusButtonARC, R.id.plusButtonTCSC, R.id.plusButtonTCSH, R.id.plusButtonTRC, R.id.plusButtonTRH};
        int[] minusButtons = {R.id.minusButtonACSC, R.id.minusButtonACSH, R.id.minusButtonARH, R.id.minusButtonARC, R.id.minusButtonTCSC, R.id.minusButtonTCSH, R.id.minusButtonTRC, R.id.minusButtonTRH};
        int[] textview = {R.id.autoCSC, R.id.autoCSH, R.id.autoRH, R.id.autoRC, R.id.teleOPCSC, R.id.teleOPCSH, R.id.teleOPRC, R.id.teleOPRH};

        buttonsToAtomicInt = new HashMap<>();
        buttonsToTextView = new HashMap<>();

        for (int i = 0; i < plusButtons.length; i++) {
            AtomicInteger inty = new AtomicInteger(0);
            buttonsToAtomicInt.put(plusButtons[i], inty);
            buttonsToAtomicInt.put(minusButtons[i], inty);

            buttonsToTextView.put(plusButtons[i], textview[i]);
            buttonsToTextView.put(minusButtons[i], textview[i]);
        }

        Spinner dropColor = findViewById(R.id.dropColor);
        Spinner dropPos = findViewById(R.id.dropPosition);
        String[] alliances = {"Color", "Red", "Blue"};
        String[] positions = {"Position", "Close", "Middle", "Far"};


        ArrayAdapter<String> dropColors = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, alliances);
        ArrayAdapter<String> dropPositions = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, positions);
        dropColor.setAdapter(dropColors);
        dropPos.setAdapter(dropPositions);
    }

//    Example of Sending Information between intents
//    public void sendMessage(View view){
//        Intent intent = new Intent(this, HomeScreen.class);
//        EditText editText = findViewById(R.id.editText2);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }

    public void incHashy(View view) {
        buttonsToAtomicInt.get(view.getId()).getAndIncrement();
        TextView text = findViewById(buttonsToTextView.get(view.getId()));
        text.setText(buttonsToAtomicInt.get(view.getId()).toString());
    }

    public void decHashy(View view) {
        if(buttonsToAtomicInt.get(view.getId()).get() != 0) {
            buttonsToAtomicInt.get(view.getId()).getAndDecrement();
            TextView text = findViewById(buttonsToTextView.get(view.getId()));
            text.setText(buttonsToAtomicInt.get(view.getId()).toString());
        }
    }
}
