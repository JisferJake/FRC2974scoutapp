package scoutapp.main;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.*;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class Export extends AppCompatActivity {

    private static final String URI = "uri";
    private static final String DESTINATION = "AC-FD-CE-30-1C-23";
    private static final String correctDevice = "DESKTOP-DDTDG6C";
    private ArrayAdapter<String> devicesNearby;
    private ListView list = new ListView(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
    }

    public void exportScoutingForm(View view) {

        devicesNearby = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        final String DIRECTORY_2974SCOUTINGAPP = "2974ScoutingApp";
        File folder = Environment.getExternalStoragePublicDirectory(DIRECTORY_2974SCOUTINGAPP);
        File[] files = folder.listFiles();

        setBlueTooth();
    }


    private void setBlueTooth() {

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter == null)
            System.out.println("Bluetooth not supported");

        if(!bluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 1);
        }

        bluetoothAdapter.startDiscovery();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        list.setAdapter(devicesNearby);

//        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
//
//        for(BluetoothDevice device : pairedDevices) {
//
//            }
//
//        if(foundDevice == null) {

//            IntentFilter filter1 = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//            BroadcastReceiver receiver = new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    String action = intent.getAction();
//                    if(BluetoothDevice.ACTION_FOUND.equals(action)) {
//                        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//                        String deviceName = device.getName();
//                        String deviceHardwareAddress = device.getAddress();
//                        System.out.println(deviceName + " " + deviceHardwareAddress);
//                    }
//                }
//            };
//
//            registerReceiver(receiver, filter);
//
//        }
//
//        while(foundDevice == null);
//
//        return foundDevice;
    }

    public void exportPitForm() {
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            System.out.println("hi");
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if(device.getName() != null)
                    devicesNearby.add(device.getName());
            }
        }
    };

    class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket
            // because mmSocket is final.
            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // Get a BluetoothSocket to connect with the given BluetoothDevice.
                // MY_UUID is the app's UUID string, also used in the server code.
                tmp = device.createRfcommSocketToServiceRecord(UUID.randomUUID());
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it otherwise slows down the connection.

            try {
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and return.
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                    closeException.printStackTrace();
                }
                return;
            }
        }

        // Closes the client socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
