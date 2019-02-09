package scoutapp.main;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.*;
import java.util.Set;
import java.util.UUID;

public class Export extends AppCompatActivity {

    private String pairedDevice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);

        pairedDevice = "DESKTOP-DDTDG6G";
    }

    public void exportScoutingForm(View view) {
        final String DIRECTORY_2974SCOUTINGAPP = "2974ScoutingApp";
        File folder = Environment.getExternalStoragePublicDirectory(DIRECTORY_2974SCOUTINGAPP);
        File[] files = folder.listFiles();
        for(File file : files)
            if (file.getName().contains("ScoutingForm")) {
                BluetoothDevice device = setBlueTooth();
                try {
                    BluetoothSocket socket = device.createRfcommSocketToServiceRecord(UUID.randomUUID());
                    socket.connect();
                    FileInputStream input = new FileInputStream(file);
                    byte[] fileContent = new byte[(int) file.length()];
                    input.read(fileContent);
                    socket.getOutputStream().write(fileContent);
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    private BluetoothDevice setBlueTooth() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        while(!adapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 1);
            adapter = BluetoothAdapter.getDefaultAdapter();
        }

        for(BluetoothDevice device : adapter.getBondedDevices())
            if (device.getName().equals(pairedDevice))
                return device;

        return null;
    }

    public void exportPitForm() {

    }
}
