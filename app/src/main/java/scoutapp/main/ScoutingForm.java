package scoutapp.main;

import android.os.SystemClock;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Chronometer;
import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ScoutingForm extends AppCompatActivity {

    private Map<Integer, AtomicInteger> buttonsToAtomicInt;
    private Map<Integer, Integer> buttonsToTextView;
    private Map<String, AtomicInteger> numbersToIdentifiers;

    // timer
    private long lapDuration = 0;
    private long time = SystemClock.elapsedRealtime();
    private boolean stopClicked = false;
    private Chronometer chronometer = null;
    ArrayList<Double> laps = new ArrayList<Double>();
    private int numLap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scouting_form);

        chronometer = findViewById(R.id.chronometer);
        //chronometer.setFormat("00:%s");

        int[] plusButtons = {R.id.plusButtonACSC, R.id.plusButtonACSH, R.id.plusButtonARH, R.id.plusButtonARC, R.id.plusButtonTCSC, R.id.plusButtonTCSH, R.id.plusButtonTRC, R.id.plusButtonTRH};
        int[] minusButtons = {R.id.minusButtonACSC, R.id.minusButtonACSH, R.id.minusButtonARH, R.id.minusButtonARC, R.id.minusButtonTCSC, R.id.minusButtonTCSH, R.id.minusButtonTRC, R.id.minusButtonTRH};
        int[] textview = {R.id.autoCSC, R.id.autoCSH, R.id.autoRH, R.id.autoRC, R.id.teleOPCSC, R.id.teleOPCSH, R.id.teleOPRC, R.id.teleOPRH};
        String[] names = { "A Ship C", "A Ship H", "A Rocket H", "A Rocket C", "T Ship C", "T Ship H", "T Rocket C", "T Rocket H"};
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
        String[] positions = {"Position", "Right", "Middle", "Left"};
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

    // the method for when we press the 'start' button
    public void startButtonClick(View v) {

        //TextView timerText = findViewById(R.id.teleOP);
        //timerText.setText(Long.toString(SystemClock.elapsedRealtime()));

        if(!stopClicked) {
            chronometer.setBase(SystemClock.elapsedRealtime());
            //chronometer.setFormat("00:%s");
            chronometer.start();
            stopClicked = true;

            Button button = findViewById(R.id.tmStart);
            button.setText("Stop");
        }
        else {
            Button button = findViewById(R.id.tmStart);
            button.setText("Start");
            chronometer.stop();
            stopClicked = false;
        }
    }

    // the method for when we press the 'reset' button
    public void resetButtonClick(View v) {
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        stopClicked = true;
        lapDuration = 0;
    }

    public void lapButtonClick(View v) {
        System.out.println(SystemClock.elapsedRealtime() - chronometer.getBase());
        time = chronometer.getBase();
        chronometer.setBase(SystemClock.elapsedRealtime());
        //chronometer.setFormat("00:%s");
        chronometer.start();
        Button button = findViewById(R.id.tmStart);
        button.setText("Stop");
        stopClicked = true;
    }

    /**
     *
     * @return a boolean array of all the check marks on
     */
    private Boolean[] getBooleans() {
        Boolean[] rtn = new Boolean[IDs.Bools.IDS.length];

        for(int i = 0; i < rtn.length; i++) {
            CheckBox check = findViewById(IDs.Bools.IDS[i]);
            rtn[i] = check.isChecked();
        }
        return rtn;
    }

    private String[] getTextFields() {
        String[] rtn = new String[IDs.TextFields.IDS.length];

        for (int i = 0; i < rtn.length; i++) {
            EditText edit = findViewById(IDs.TextFields.IDS[i]);
            rtn[i] = String.valueOf(edit.getText());
        }
        return rtn;
    }

    private String[] getDropDowns() {
        String[] rtn = new String[IDs.DropDowns.IDS.length];

        for (int i = 0; i < rtn.length; i++) {
            Spinner spin = findViewById(IDs.DropDowns.IDS[i]);
            rtn[i] = spin.getSelectedItem().toString();
        }
        return rtn;
    }

    private boolean isExportReady() {
        boolean ok = true;
        EditText teamNumber = findViewById(IDs.TextFields.TEAM_NUMBER);
        if(String.valueOf(teamNumber.getText()).isEmpty()) {
            teamNumber.setBackgroundColor(Color.RED);
            ok = false;
        }
        else {
            teamNumber.setBackgroundColor(Color.TRANSPARENT);
        }

        String[] defaultLocations = { "Color", "Position", "Level Climbed to"};
        for (int i = 0; i < defaultLocations.length; i++) {
            Spinner spin = findViewById(IDs.DropDowns.IDS[i]);
            if(spin.getSelectedItem().toString().equals(defaultLocations[i])) {
                spin.setBackgroundColor(Color.RED);
                ok = false;
            }
            else {
                spin.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        return ok;
    }

    public void export(View view) {

        if(!isExportReady())
            return;

        final String DIRECTORY_2974SCOUTINGAPP = "2974ScoutingApp";         //Direction the Scouting App Folder is on the Android.
        File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_2974SCOUTINGAPP), "ScoutingForm" + System.currentTimeMillis() + ".json");
        try {
            final int indentSpace = 4;
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file);

            JSONObject dropDowns = new JSONObject();
            String[]  dropDown = getDropDowns();
            for (int i = 0; i < getDropDowns().length; i++)
                dropDowns.put(IDs.DropDowns.ID_NAMES[i], dropDown[i]);

            writer.write(dropDowns.toString(indentSpace));

            JSONObject bools = new JSONObject();
            Boolean[] booleans = getBooleans();
            for (int i = 0; i < IDs.Bools.ID_NAMES.length; i++)
                bools.put(IDs.Bools.ID_NAMES[i], booleans[i]);

            writer.write(bools.toString(indentSpace));

            JSONObject nums = new JSONObject();
            for (String key : numbersToIdentifiers.keySet())
                nums.put(key, numbersToIdentifiers.get(key));
            writer.write(nums.toString(indentSpace));

            JSONObject fields = new JSONObject();
            String[] textFields = getTextFields();
            for (int i = 0; i < textFields.length; i++)
                fields.put(IDs.TextFields.ID_NAMES[i], textFields[i]);


            writer.write(fields.toString(indentSpace));

            writer.close();
            System.out.println("Hello");
            Intent intent = new Intent(this, HomeScreen.class);
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class IDs {
        static class AutoPoints {
            public static final int[] IDS = { R.id.autoCSC, R.id.autoCSH, R.id.autoRH, R.id.autoRC };
            public static final String[] ID_NAMES = {"A Ship Cargo", "A Ship Hatches", "A Rocket Hatches", "A Rocket Cargo"};
            public static final int SHIP_CARGO = IDS[0];
            public static final int SHIP_HATCHES = IDS[1];
            public static final int ROCKET_HATCHES = IDS[2];
            public static final int ROCKET_CARGO = IDS[3];
        }

        static class TextFields {
            public static final int[] IDS = {R.id.teamNumber, R.id.comments};
            public static final String[] ID_NAMES = {"Team Number", "Comments"};
            public static final int TEAM_NUMBER = IDS[0];
            public static final int COMMENTS = IDS[1];
        }

        static class DropDowns {
            public static final int[] IDS = {R.id.dropColor, R.id.dropPosition, R.id.dropClimb, R.id.dropConnection};
            public static final String[] ID_NAMES = {"Drop Color", "Drop Position", "Drop Climb", "Drop Connection"};
            public static final int DROP_COLOR = IDS[0];
            public static final int DROP_POSITION = IDS[1];
            public static final int DROP_CONNECTION = IDS[2];
            public static final int DROP_CLIMB = IDS[3];
        }

        static class TeleopPoints {
            public static final int[] IDS = { R.id.teleOPCSC, R.id.teleOPCSH, R.id.teleOPRC, R.id.teleOPRH };
            public static final String[] ID_NAMES = {"T Ship C", "T Ship H", "T Rocket C", "T Rocket H"};
            public static final int SHIP_CARGO = IDS[0];
            public static final int SHIP_HATCH = IDS[1];
            public static final int ROCKET_CARGO = IDS[2];
            public static final int ROCKET_HATCHES = IDS[3];
        }

        static class Bools {
            public static final int[] IDS = {R.id.auton, R.id.habLine, R.id.defense, R.id.unstable, R.id.checkAssist};
            public static final String[] ID_NAMES = {"Auton", "Hab Line", "Defense", "Unstable", "Helped Another Climb"};
            public static final int AUTON = IDS[0];
            public static final int HABLINE = IDS[1];
            public static final int DEFENSE = IDS[2];
            public static final int UNSTABLE = IDS[3];
            public static final int CHECK_ASSIST = IDS[4];
        }
    }
}
