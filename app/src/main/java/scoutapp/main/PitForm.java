package scoutapp.main;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class PitForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_form);

        Spinner dropClimbs = findViewById(R.id.dropClimb);
        String[] climbLevels = {"Level 1", "Level 2", "Level 3"};
        ArrayAdapter<String> dropClimb = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, climbLevels);
        dropClimbs.setAdapter(dropClimb);

        Spinner dropPosition = findViewById(R.id.dropStartPosition);
        String[] startPositions = {"Starting Position", "Right", "Middle", "Left"};
        ArrayAdapter<String> dropPositions = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, startPositions);
        dropPosition.setAdapter(dropPositions);

        Spinner dropIntake = findViewById(R.id.dropIntake);
        String[] intakes = {"Intake", "Drop Center", "Mecanum", "Omni-H", "Butterfly", "Swerve"};
        ArrayAdapter<String> dropIntakes = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, intakes);
        dropIntake.setAdapter(dropIntakes);
    }

    private String[] getBooleans() {
        int[] ids = {R.id.checkCargoShip, R.id.checkCargoRocket, R.id.checkHatchShip, R.id.checkHatchRocket, R.id.checkAssisst};
        String[] names = {"Ship Cargo", "Rocket Cargo", "Ship Hatch", "Rocket Hatch", "Can Assist"};
        String[] rtn = new String[ids.length];

        for(int i = 0; i < rtn.length; i++) {
            CheckBox check = findViewById(ids[i]);
            rtn[i] = names[i] +"," + check.isChecked();
        }
        return rtn;
    }

    private String[] getTextFields() {
        int[] ids = {R.id.textTeamName, R.id.textTeamNumber, R.id.textWeight, R.id.textStrategies, R.id.textPlannedChanges, R.id.textOtherInfo};
        String[] names = {"team Name:", "Team Number:", "Weight:", "Strategies:", "Planned Changes:", "Other Info:"};
        String[] rtn = new String[ids.length];

        for (int i = 0; i < rtn.length; i++) {
            EditText edit = findViewById(ids[i]);
            rtn[i] = names[i] + "," + edit.getText();
        }
        return rtn;
    }

    private String[] getDropDowns() {
        int[] ids = {R.id.dropStartPosition, R.id.dropIntake, R.id.dropClimb};
        String[] names = {"Drop Start Position:", "Drop Intake:", "Drop Climb:"};
        String[] rtn = new String[ids.length];

        for (int i = 0; i < rtn.length; i++) {
            Spinner spin = findViewById(ids[i]);
            rtn[i] = names[i] + "," + spin.getSelectedItem().toString();
        }
        return rtn;
    }

    public void export(View view) {
        final String DIRECTORY_2974SCOUTINGAPP = "2974ScoutingApp";         //Direction the Scouting App Folder is on the Android.
        System.out.println(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(Environment.getExternalStoragePublicDirectory(DIRECTORY_2974SCOUTINGAPP), "PitForm" + System.currentTimeMillis() + ".csv");
        try {
            file.createNewFile();
            CSV csv = new CSV("Name, data");
            for(String str : getBooleans()) {
                csv.addRow(str);
            }

            for(String str : getTextFields()) {
                csv.addRow(str);
            }

            for(String str : getDropDowns()) {
                csv.addRow(str);
            }

            PrintWriter writer = new PrintWriter(file);
            writer.write(csv.compile());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Hello");
    }
}
