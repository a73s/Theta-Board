package com.theta.xi.thetaboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ManageMembersActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialButton add_member_cancel = null;
    MaterialButton add_member_submit = null;
    FloatingActionButton add_member = null;
    MaterialCardView add_member_prompt = null;

    public ManageMembersActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_members_activity);

        LinearLayout containerLayout = findViewById(R.id.ManageMemberListContainer);
        LayoutInflater inflater = getLayoutInflater();

        String[] items = {"Apple", "Banana", "Cherry", "Dates", "Elderberry", "Fig", "Grape", "Hackberry", "Indian Plum", "Juniper", "Kiwi", "Lemon", "Mango", "Nectarine"};
        for(String itemText : items){

            View currentElement = inflater.inflate(R.layout.manage_members_list_item, containerLayout, false);
            TextView currentText = currentElement.findViewById(R.id.manage_members_item_name);
            currentText.setText(itemText);

            containerLayout.addView(currentElement);
        }

        add_member = findViewById(R.id.add_member_button);
        add_member_submit = findViewById(R.id.add_member_submit);
        add_member_cancel = findViewById(R.id.add_member_cancel);
        add_member_prompt = findViewById(R.id.add_member_prompt);

        add_member.setOnClickListener(this);
        add_member_submit.setOnClickListener(this);
        add_member_cancel.setOnClickListener(this);
        add_member_prompt.setOnClickListener(this);

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                if (add_member_prompt.getVisibility() == View.VISIBLE) {
                    add_member_prompt.setVisibility(View.GONE);
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        };
        // Add the callback to the dispatcher
        getOnBackPressedDispatcher().addCallback(this, callback);
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        LinearLayout containerLayout = view.findViewById(R.id.ManageMemberListContainer);
//        LayoutInflater inflater = getLayoutInflater();
//
//        String[] items = {"Apple", "Banana", "Cherry", "Dates", "Elderberry", "Fig", "Grape"};
//        for(String itemText : items){
//
//            View currentElement = inflater.inflate(R.layout.manage_members_list_item, containerLayout, false);
//            TextView currentText = currentElement.findViewById(R.id.manage_members_item_name);
//            currentText.setText(itemText);
//
//            containerLayout.addView(currentElement);
//        }
//
//        add_member = view.findViewById(R.id.add_member_button);
//        add_member_submit = view.findViewById(R.id.add_member_submit);
//        add_member_cancel = view.findViewById(R.id.add_member_cancel);
//        add_member_prompt = view.findViewById(R.id.add_member_prompt);
//
//        add_member.setOnClickListener(this);
//        add_member_submit.setOnClickListener(this);
//        add_member_cancel.setOnClickListener(this);
//        add_member_prompt.setOnClickListener(this);
//
//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//                if (add_member_prompt.getVisibility() == View.VISIBLE) {
//                    add_member_prompt.setVisibility(View.GONE);
//                } else {
//                    setEnabled(false);
//                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
//                }
//            }
//        };
//        // Add the callback to the dispatcher
//        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
//    }

    @Override
    public void onClick(View v) {
        if(v == add_member){
            add_member_prompt.setVisibility(View.VISIBLE);
        } else if(v == add_member_cancel){
            add_member_prompt.setVisibility(View.GONE);
        } else if(v == add_member_submit){
            // TODO: add logic for submitting
        }
    }
}
