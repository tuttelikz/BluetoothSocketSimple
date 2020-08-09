package com.example.bluetoothsocket;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    // for boards with bluetooth, use this UUID. For another mobile phone, use different
    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    // define mac address of device here
    private static final String arduinoMacAddress = "98:D3:32:70:A9:94";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get default ada[ter
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

        // print paired devices
        System.out.println(btAdapter.getBondedDevices());

        // connect to the bluetooth with known mac address
        BluetoothDevice hc06 = btAdapter.getRemoteDevice(arduinoMacAddress);
        System.out.println(hc06.getName());

        // initialize Bluetooth socket for communication
        BluetoothSocket btSocket = null;
        int counter = 0;
        do {
            try {
                // create Bluetooth socket
                btSocket = hc06.createRfcommSocketToServiceRecord(mUUID);
                System.out.println(btSocket);
                // start connection
                btSocket.connect();
                System.out.println(btSocket.isConnected());
            } catch (IOException e) {
                e.printStackTrace();
            }
            counter++;
        } while (!btSocket.isConnected() && counter < 3); // give 3 attempts

        try {
            // send char "0" on ASCII
            OutputStream outputStream = btSocket.getOutputStream();
            outputStream.write(48);
        } catch (IOException e) {
            e.printStackTrace();
        }

        InputStream inputStream = null;
        try {
            // initialize and clear the input stream from HC06
            inputStream = btSocket.getInputStream();
            inputStream.skip(inputStream.available());

            // convert received input bytes to alphabet
            for (int k = 0; k < 26; k++) {
                byte b = (byte) inputStream.read();
                System.out.println((char) b); //System.out.println((char) b);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // close Bluetooth socket
            btSocket.close();
            System.out.println(btSocket.isConnected());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
