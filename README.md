# Simple Bluetooth Socket for communication with Arduino HC05, HC06

## Procedure:

1. Define necessary Bluetooth permissions in Manifest.
2. Declare physical Mac address of Bluetooth device to connect.
3. Get a handle to the default local `BluetoothAdapter`. Connect to remote device.
4. Initialize `BluetoothSocket` for communication.
5. Send "0" symbol to the socket.
6. Receive input stream from Bluetooth socket.
7. Don't forget to close `BluetoothSocket` after completion.
8. Display results with `Logcat` of Android.