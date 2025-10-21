package com.theta.xi.thetaboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ManageBoardsFragment extends Fragment {

    public ManageBoardsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.manage_boards_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout containerLayout = view.findViewById(R.id.ManageBoardsListContainer);
        LayoutInflater inflater = getLayoutInflater();

        String[] items = {"Apple", "Banana", "Cherry", "Dates", "Elderberry", "Fig", "Grape"};
        for(String itemText : items){

            View currentElement = inflater.inflate(R.layout.manage_boards_list_item, containerLayout, false);
            TextView currentText = currentElement.findViewById(R.id.manage_boards_item_name);
            currentText.setText(itemText);

            containerLayout.addView(currentElement);
        }
    }
}
