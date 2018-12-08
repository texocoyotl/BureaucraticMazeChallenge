package com.capone.myi627.bureaucraticmaze;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements RoomFragment.OnExitInteractionListener {

    private static final String ROOM1_NAME = "Human Resources";
    private static final String ROOM2_NAME = "Marketing";
    private static final String ROOM3_NAME = "Corporate Compliance";
    private static final String ROOM4_NAME = "Information Management";

    private TextView intro;
    private String visits = "";
    private String employeeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button begin  = (Button) findViewById(R.id.btBegin);
        begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edID = (EditText) findViewById(R.id.edID);
                employeeID = edID.getText().toString();
                if (employeeID != null && employeeID.matches("\\D+")){

                    findViewById(R.id.tvID).setVisibility(View.GONE);
                    findViewById(R.id.edID).setVisibility(View.GONE);
                    findViewById(R.id.tvIntro).setVisibility(View.GONE);
                    v.setVisibility(View.GONE);

                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    visits += "[" + ROOM1_NAME + "]";
                    ft.add(R.id.roomContainer, RoomFragment.newInstance(ROOM1_NAME, new String[]{ROOM2_NAME, ROOM3_NAME}));
                    ft.commit();
                }
            }
        });

    }

    @Override
    public void onExitClick(String currentRoom, String destination) {

        RoomFragment room = createRoom(currentRoom, destination);
        visits += "{" + destination + "}";

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.roomContainer, room, room.getRoomName());
        ft.commit();

    }

    @Override
    public void onSendClick() {
        Log.d("SOLUTION", "SOLUTION: " + employeeID + ":" + visits);
    }


    private RoomFragment createRoom(String currentRoom, String destination) {
        RoomFragment room = null;

        if (currentRoom.equals(ROOM3_NAME) && destination.equals(ROOM1_NAME))
            room = RoomFragment.newInstance(ROOM1_NAME, new String[]{ROOM4_NAME, ROOM2_NAME});
        else if (currentRoom.equals(ROOM4_NAME) && destination.equals(ROOM1_NAME))
            room = RoomFragment.newInstance(ROOM1_NAME, new String[]{});
        else if (currentRoom.equals(ROOM2_NAME) && destination.equals(ROOM1_NAME))
            room = RoomFragment.newInstance(ROOM1_NAME, new String[]{ROOM3_NAME, ROOM4_NAME});

        if (currentRoom.equals(ROOM1_NAME) && destination.equals(ROOM2_NAME))
            room = RoomFragment.newInstance(ROOM2_NAME, new String[]{ROOM4_NAME});
        else if (currentRoom.equals(ROOM3_NAME) && destination.equals(ROOM2_NAME))
            room = RoomFragment.newInstance(ROOM2_NAME, new String[]{ROOM1_NAME, ROOM3_NAME, ROOM4_NAME});
        else if (currentRoom.equals(ROOM4_NAME) && destination.equals(ROOM2_NAME))
            room = RoomFragment.newInstance(ROOM2_NAME, new String[]{ROOM1_NAME});


        else if (currentRoom.equals(ROOM1_NAME) && destination.equals(ROOM3_NAME))
            room = RoomFragment.newInstance(ROOM3_NAME, new String[]{ROOM2_NAME});
        else if (currentRoom.equals(ROOM2_NAME) && destination.equals(ROOM3_NAME))
            room = RoomFragment.newInstance(ROOM3_NAME, new String[]{ROOM4_NAME, ROOM1_NAME});
        else if (currentRoom.equals(ROOM4_NAME) && destination.equals(ROOM3_NAME))
            room = RoomFragment.newInstance(ROOM3_NAME, new String[]{ROOM2_NAME, ROOM1_NAME});

        else if (currentRoom.equals(ROOM3_NAME) && destination.equals(ROOM4_NAME))
            room = RoomFragment.newInstance(ROOM4_NAME, new String[]{ROOM2_NAME, ROOM1_NAME});
        else if (currentRoom.equals(ROOM1_NAME) && destination.equals(ROOM4_NAME))
            room = RoomFragment.newInstance(ROOM4_NAME, new String[]{ROOM2_NAME, ROOM3_NAME});
        else if (currentRoom.equals(ROOM2_NAME) && destination.equals(ROOM4_NAME))
            room = RoomFragment.newInstance(ROOM4_NAME, new String[]{ROOM2_NAME, ROOM3_NAME});

        return room;
    }
}
