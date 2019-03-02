package scoutapp.main;

import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoutingForm extends AppCompatActivity {

    private Map<Integer, AtomicInteger> buttonsToAtomicInt;
    private Map<Integer, Integer> buttonsToTextView;
    private Map<String, AtomicInteger> numbersToIdentifiers;

    private ArrayList<Double> laps;
    private Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_form);

        chronometer = findViewById(R.id.chronometer);
        laps = new ArrayList<Double>();

        int[] plusButtons = {R.id.plusButtonTCSC, R.id.plusButtonTCSH, R.id.plusButtonTRC, R.id.plusButtonTRH};
        int[] minusButtons = {R.id.minusButtonTCSC, R.id.minusButtonTCSH, R.id.minusButtonTRC, R.id.minusButtonTRH};
        int[] textview = {R.id.teleOPCSC, R.id.teleOPCSH, R.id.teleOPRC, R.id.teleOPRH};
        String[] names = {"Teleop Cargo Ship Cargo", "Teleop Cargo Ship Hatches", "Teleop Rocket Cargo", "Teleop Rocket Hatches"};
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
        String[] positions = {"Position", "Right", "Side", "Middle"};
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

    public void timerStart(View view) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }

    public void timerReset(View view) {
        chronometer.setBase(SystemClock.elapsedRealtime());
    }

    public void timerLap(View view) {
        long timeMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
        System.out.println(timeMillis);
        double sec = (timeMillis % 1000) / 1000.0;
        double t = timeMillis / 1000;
        laps.add(t + sec);

        chronometer.setBase(SystemClock.elapsedRealtime());
    }




    /**
     *
     * @return a boolean array of all the check marks in this.
     */
    private String[] getBooleans() {
        int[] ids = {R.id.auton, R.id.habLine, R.id.defense, R.id.unstable, R.id.checkAssist,
                        R.id.leftBotACSC, R.id.leftBotACSH, R.id.leftBotARH, R.id.leftTopACSC,
                        R.id.leftTopACSH, R.id.leftTopARH, R.id.leftMidACSC, R.id.leftMidACSH,
                        R.id.leftMidARH, R.id.leftFrontACSC, R.id.leftFrontACSH, R.id.rightBotACSC,
                        R.id.rightBotACSH, R.id.rightBotARH, R.id.rightTopACSC, R.id.rightTopACSH,
                        R.id.rightTopARH, R.id.rightMidACSC, R.id.rightMidACSH, R.id.rightMidARH,
                        R.id.rightFrontACSC, R.id.rightFrontACSH, R.id.midARC, R.id.botARC, R.id.topARC};

        int[] total = new int[2];
        String[] stings = { "Auto Cargo", "Auto Hatches"};
        String[] rtn = new String[ids.length + total.length];

        for(int i = 0; i < ids.length; i++) {
            CheckBox check = findViewById(ids[i]);
            String name = getResources().getResourceEntryName(ids[i]);
            rtn[i] =  name + "," + check.isChecked();

            if(check.isChecked()) {
                if(name.indexOf("C") == name.length() - 1) {
                    total[0]++;
                }
                else
                    total[1]++;
            }
        }

        for (int i = 0; i < total.length; i++) {
            rtn[i + ids.length] = stings[i] + "," + total[i];
        }

        return rtn;
    }

    private String[] getTextFields() {
        int[] ids = {R.id.teamNumber, R.id.comments};
        String[] rtn = new String[ids.length];

        for (int i = 0; i < rtn.length; i++) {
            EditText edit = findViewById(ids[i]);
            rtn[i] = getResources().getResourceEntryName(ids[i]) + "," + edit.getText();
        }
        return rtn;
    }

    private String[] getDropDowns() {
        int[] ids = {R.id.dropColor, R.id.dropPosition, R.id.dropClimb, R.id.dropConnection};
        String[] rtn = new String[ids.length];

        for (int i = 0; i < rtn.length; i++) {
            Spinner spin = findViewById(ids[i]);
            rtn[i] = getResources().getResourceEntryName(ids[i]) + "," + spin.getSelectedItem().toString();
        }
        return rtn;
    }

    public void export(View view) {
        final String DIRECTORY_2974SCOUTINGAPP = "2974ScoutingApp";         //Direction the Scouting App Folder is on the Android.
        System.out.println(Environment.DIRECTORY_DOWNLOADS);
        EditText matchNumber = findViewById(R.id.gameNumber);
        File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_2974SCOUTINGAPP), "ScoutingForm" + matchNumber.getText() + ".csv");
        try {
            file.createNewFile();
            CSV csv = new CSV("Name, data");
            for(String key : numbersToIdentifiers.keySet())
                csv.addRow(key + "," + numbersToIdentifiers.get(key));

            for(String str : getBooleans()) {
                csv.addRow(str);
            }

            for(String str : getTextFields()) {
                csv.addRow(str);
            }

            for(String str : getDropDowns()) {
                csv.addRow(str);
            }

            double average = 0;
            for(Double lap : laps) {
                average += lap;
            }
            average /= laps.size() ;
            csv.addRow("averageLap," + average);

            PrintWriter writer = new PrintWriter(file);
            writer.write(csv.compile());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Hello");
    }
}
