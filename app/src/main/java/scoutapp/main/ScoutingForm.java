package scoutapp.main;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoutingForm extends AppCompatActivity {

    private Map<Integer, AtomicInteger> buttonsToAtomicInt;
    private Map<Integer, Integer> buttonsToTextView;
    private Map<String, AtomicInteger> numbersToIdentifiers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_form);

        int[] plusButtons = {R.id.plusButtonACSC, R.id.plusButtonACSH, R.id.plusButtonARH, R.id.plusButtonARC, R.id.plusButtonTCSC, R.id.plusButtonTCSH, R.id.plusButtonTRC, R.id.plusButtonTRH};
        int[] minusButtons = {R.id.minusButtonACSC, R.id.minusButtonACSH, R.id.minusButtonARH, R.id.minusButtonARC, R.id.minusButtonTCSC, R.id.minusButtonTCSH, R.id.minusButtonTRC, R.id.minusButtonTRH};
        int[] textview = {R.id.autoCSC, R.id.autoCSH, R.id.autoRH, R.id.autoRC, R.id.teleOPCSC, R.id.teleOPCSH, R.id.teleOPRC, R.id.teleOPRH};
        String[] names = { "Auto Cargo Ship Cargo", "Auto Cargo Ship Hatches", "Auto Rocket Hatches", "Auto Rocket Cargo", "Teleop Cargo Ship Cargo", "Teleop Cargo Ship Hatches", "Teleop Rocket Cargo", "Teleop Rocket Hatches"};
        buttonsToAtomicInt = new HashMap<>();
        buttonsToTextView = new HashMap<>();
        numbersToIdentifiers = new HashMap<>();

        //Mapping Buttons to HashMap buttonsToAtomicInt and buttonsToTextView
        for (int i = 0; i < plusButtons.length; i++) {
            AtomicInteger inty = new AtomicInteger(0);
            buttonsToAtomicInt.put(plusButtons[i], inty);
            buttonsToAtomicInt.put(minusButtons[i], inty);

            buttonsToTextView.put(plusButtons[i], textview[i]);
            buttonsToTextView.put(minusButtons[i], textview[i]);

            numbersToIdentifiers.put(names[i], inty);
        }

        //Drop Menu of Alliance Colors
        Spinner dropColor = findViewById(R.id.dropColor);
        String[] alliances = {"Color", "Red", "Blue"};
        ArrayAdapter<String> dropColors = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, alliances);
        dropColor.setAdapter(dropColors);

        //Drop Menu of of Alliance Positions
        Spinner dropPos = findViewById(R.id.dropPosition);
        String[] positions = {"Position", "Close", "Middle", "Far"};
        ArrayAdapter<String> dropPositions = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, positions);
        dropPos.setAdapter(dropPositions);

        //Drop Menu of Connections During Match
        Spinner dropConnection = findViewById(R.id.dropConnection);
        String[] connections = {"None", "Once", "More than once"};
        ArrayAdapter<String> dropConnections = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, connections);
        dropConnection.setAdapter(dropConnections);

        //Drop Menus of levels climbed to
        Spinner dropClimb = findViewById(R.id.dropClimb);
        String[] climbs = {"Level Climbed to", "Garbage", "Level 1", "Level 2", "Level 3"};
        ArrayAdapter<String> dropClimbs = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, climbs);
        dropClimb.setAdapter(dropClimbs);
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

    public void export(View view) {
        final String DIRECTORY_2974SCOUTINGAPP = "2974ScoutingApp";         //Direction the Scouting App Folder is on the Android.
        System.out.println(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_2974SCOUTINGAPP), "ScoutingForm" + System.currentTimeMillis() + ".csv");
        try {
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);
            for(String key : numbersToIdentifiers.keySet())
                writer.write(key + " " + numbersToIdentifiers.get(key) + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Hello");
    }
}
