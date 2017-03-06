package com.example.anthony.genielamp.callback;

import android.content.Context;
import android.support.v4.app.Fragment;

public class DataCallBack extends Fragment {

    private OnReceivedDatabase UserListener;

    public DataCallBack() { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            UserListener = (OnReceivedDatabase) context;
        }
        catch (RuntimeException e) {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    //callback function that contain 3 value for profile info
    public interface OnReceivedDatabase {
        void onDatabaseInteraction(String username, String image, String description);
    }
}

