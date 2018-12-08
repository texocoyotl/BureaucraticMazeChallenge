package com.capone.myi627.bureaucraticmaze;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link OnExitInteractionListener} interface
 * to handle interaction events.
 */
public class RoomFragment extends Fragment {

    private static final String ROOM_NAME_KEY = "ROOM_NAME";
    private static final String EXITS_KEY = "EXITS";

    private OnExitInteractionListener mListener;

    private String roomName;
    private String[] exits;

    public String getRoomName() {
        return roomName;
    }

    public RoomFragment() {
        // Required empty public constructor
    }

    public static RoomFragment newInstance(String roomName, String[] exits){
        RoomFragment instance = new RoomFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ROOM_NAME_KEY, roomName);
        bundle.putStringArray(EXITS_KEY, exits);
        instance.setArguments(bundle);
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        roomName = getArguments().getString(ROOM_NAME_KEY);
        exits = getArguments().getStringArray(EXITS_KEY);

        TextView tvRoomName = (TextView) view.findViewById(R.id.tvRoomName);
        tvRoomName.setText(roomName);

        Button[] btnExits = new Button[exits.length];

        for(int i = 0; i < exits.length; i++){
            btnExits[i] = (Button) view.findViewById(getResources().getIdentifier("btExit" + (i + 1), "id", getContext().getPackageName()));
            if (btnExits[i] != null) {

                btnExits[i].setVisibility(View.VISIBLE);
                btnExits[i].setText(exits[i]);

                final String destination = exits[i];

                btnExits[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onExitClick(roomName, destination);
                    }
                });
            }
        }

        if (exits.length == 0){
            TextView instructions = (TextView) view.findViewById(R.id.tvInstrutions);
            instructions.setText(getContext().getString(R.string.finish));
            Button btSend = (Button) view.findViewById(R.id.btSend);
            btSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onSendClick();
                }
            });
            btSend.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnExitInteractionListener) {
            mListener = (OnExitInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                + " must implement OnExitInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    public interface OnExitInteractionListener {
        void onExitClick(String currentRoom, String destination);
        void onSendClick();
    }

}
