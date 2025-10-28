package com.theta.xi.thetaboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ManageBoardsFragment extends Fragment implements View.OnClickListener {

    MaterialButton add_board_cancel = null;
    MaterialButton add_board_submit = null;
    FloatingActionButton add_board = null;
    MaterialCardView add_board_prompt = null;

    //TODO: we need a list of these, they should each be associated with an entry
    MaterialButton manage_members_button = null;

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

        add_board = view.findViewById(R.id.add_board_button);
        add_board_submit = view.findViewById(R.id.add_board_submit);
        add_board_cancel = view.findViewById(R.id.add_board_cancel);
        add_board_prompt = view.findViewById(R.id.add_board_prompt);
        manage_members_button = view.findViewById(R.id.manage_members_button);

        add_board.setOnClickListener(this);
        add_board_submit.setOnClickListener(this);
        add_board_cancel.setOnClickListener(this);
        add_board_prompt.setOnClickListener(this);
        manage_members_button.setOnClickListener(this);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if (add_board_prompt.getVisibility() == View.VISIBLE) {
                    add_board_prompt.setVisibility(View.GONE);
                } else {
                    setEnabled(false);
                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
                }
            }
        };
        // Add the callback to the dispatcher
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }

    @Override
    public void onClick(View v) {
        if(v == add_board){
            add_board_prompt.setVisibility(View.VISIBLE);
        } else if(v == add_board_cancel){
            add_board_prompt.setVisibility(View.GONE);
        } else if(v == add_board_submit){
            // TODO: add logic for submitting
        } else if(v == manage_members_button){
            assert getActivity() != null;
            Intent intent = new Intent(getActivity(), ManageMembersActivity.class);
            startActivity(intent);
        }
    }
}
