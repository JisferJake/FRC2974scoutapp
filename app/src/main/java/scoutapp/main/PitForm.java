package scoutapp.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PitForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pit_form);

        Spinner dropClimbs = findViewById(R.id.dropClimb);
        String[] climbLevels = {"Level 1", "Level 2", "Level 3"};
        ArrayAdapter<String> dropClimb = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, climbLevels);
        dropClimbs.setAdapter(dropClimb);

        Spinner dropPosition = findViewById(R.id.startPosition);
        String[] startPositions = {"Starting Position", "Right", "Middle", "Left"};
        ArrayAdapter<String> dropPositions = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, startPositions);
        dropPosition.setAdapter(dropPositions);

        Spinner dropIntake = findViewById(R.id.dropDriveTrain);
        String[] intakes = {"Intake", "Drop Center", "Mecanum", "Omni-H", "Butterfly", "Swerve"};
        ArrayAdapter<String> dropIntakes = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, intakes);
        dropIntake.setAdapter(dropIntakes);
    }
}
