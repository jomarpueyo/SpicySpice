package suospice.suo.spice.com.spicyspice;

        import android.bluetooth.BluetoothAdapter;
        import android.bluetooth.BluetoothDevice;
        import android.bluetooth.BluetoothSocket;
        import android.content.Intent;
        import android.os.Bundle;
        import android.app.Fragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.Spinner;
        import android.widget.Toast;
        import android.widget.NumberPicker;

        import java.io.IOException;
        import java.io.OutputStream;
        import java.util.UUID;


/**
 * A simple {@link Fragment} subclass.
 */
public class settingsFragment extends Fragment {

    BluetoothAdapter myBluetooth;
    Button bluetoothDiscover, btnDispense;
    CheckBox checkBox;
    BluetoothSocket btSocket = null;
    UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");    //UUID to connect to bluetooth dongle
    NumberPicker np1,np2,np3,np4;
    Spinner spin;

    OutputStream outputStream;

    public settingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        checkBox = (CheckBox) v.findViewById(R.id.checkBox);
        bluetoothDiscover = (Button) v.findViewById(R.id.button2);
        btnDispense = (Button) v.findViewById(R.id.dispense);
        myBluetooth = BluetoothAdapter.getDefaultAdapter();
        spin = (Spinner) v.findViewById(R.id.spinner);

        np1 = (NumberPicker) v.findViewById(R.id.numberPicker);
        np2 = (NumberPicker) v.findViewById(R.id.numberPicker2);
        np3 = (NumberPicker) v.findViewById(R.id.numberPicker3);
        np4 = (NumberPicker) v.findViewById(R.id.numberPicker4);

        //Set NumberPicker Defaults
        np1.setMaxValue(10); np2.setMaxValue(10); np3.setMaxValue(10); np4.setMaxValue(10);
        np1.setMinValue(0); np2.setMinValue(0); np3.setMinValue(0); np4.setMinValue(0);

        np1.setWrapSelectorWheel(false); np2.setWrapSelectorWheel(false);
        np3.setWrapSelectorWheel(false); np4.setWrapSelectorWheel(false);

        //CheckBox Method: Request Bluetooth Enable, Enable with completed
        // Disable if on
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        if (checkBox.isChecked()) {
                                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                startActivityForResult(enableBtIntent, 1);
                        }
                        else {
                            myBluetooth.disable();
                            Toast.makeText(getActivity(),"Bluetooth Disabled",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        //Connectivity Button: Dispense button connects
        bluetoothDiscover.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(!myBluetooth.isEnabled()){
                    Toast.makeText(getActivity(),"Bluetooth Disabled, Please Enable Bluetooth",Toast.LENGTH_SHORT).show();
                }
                else{
                //Known MAC address of specific bluetooth dongle
                BluetoothDevice picController = myBluetooth.getRemoteDevice("98:D3:31:FB:67:B2");
                try {
                    btSocket = picController.createRfcommSocketToServiceRecord(BTMODULEUUID);
                    Toast.makeText(getActivity(), "Creating connection...", Toast.LENGTH_LONG).show();
                }
                catch (IOException e) {
                    //Error catch message
                    Toast.makeText(getActivity(), "Socket creation failed", Toast.LENGTH_LONG).show();
                }
                try {
                    //Create connection
                    btSocket.connect();
                    Toast.makeText(getActivity(), "Connected!", Toast.LENGTH_LONG).show();
                }
                catch (IOException connectException) {
                    Toast.makeText(getActivity(), "Socket creation failed", Toast.LENGTH_LONG).show();
                    try {
                        btSocket.close();
                    } catch (IOException closeException) {
                        //Can't close btSocket
                        //Error Log here to console
                    }
                }
                    return;
                }
            }
        });

        //Dispense Method: Sends messages to picController UART
        btnDispense.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                    //Convert Numbers and Units to Proper msgs
                if(!btSocket.isConnected()){
                    Toast.makeText(getActivity(),"Connection to Sous-Spice not made, Please connect", Toast.LENGTH_SHORT).show();
                }
                else {
                    int send1 = convert(np1.getValue(), spin.getSelectedItem().toString())+31;
                    int send2 = convert(np2.getValue(), spin.getSelectedItem().toString())+31;
                    int send3 = convert(np3.getValue(), spin.getSelectedItem().toString())+31;
                    int send4 = convert(np4.getValue(), spin.getSelectedItem().toString())+31;


                    if (send1 > 128 || send2 > 128 || send3 > 128 || send4 > 128) {
                        Toast.makeText(getActivity(), "Max dispense is one cup", Toast.LENGTH_SHORT).show();
                    } else {
                        char msg = ((char) send1);
                        try {
                            outputStream = btSocket.getOutputStream();
                            outputStream.write(msg);
                        } catch (IOException e) {
                            //Error
                        }

                        msg = ((char) send2);
                        try {
                            outputStream.write(msg);
                        } catch (IOException e) {
                            //Error
                        }

                        msg = ((char) send3);
                        try {
                            outputStream.write(msg);
                        } catch (IOException e) {
                            //Error
                        }

                        msg = ((char) send4);
                        try {
                            outputStream.write(msg);
                        } catch (IOException e) {
                            //Error
                        }
                        Toast.makeText(getActivity(), "Dispensing!", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getActivity(), "Message Sent:" + String.valueOf(send1) + " " + String.valueOf(send2) + " " + String.valueOf(send3) + " " + String.valueOf(send4) + " (31 Adjust)", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        // Inflate the layout for this fragment
        return v;

    }

    int convert(int num, String unit){
        switch (unit){
            case "Half-Teaspoon": //Unit is fine
                    break;
            case "Teaspoon": num = num*2;
                    break;
            case "Tablespoon": num = num*6;
                    break;
            case "Half-Cup": num = num*48;
                    break;
            case "Cup": num = num*96;
                    break;
        }
        return num;
    };

}
