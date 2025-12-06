package com.theta.xi.thetaboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.theta.xi.thetaboard.datacontainers.BoardInformation;
import com.theta.xi.thetaboard.datacontainers.MemberInformation;

import java.util.ArrayList;

public class ManageMembersActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialButton add_member_cancel = null;
    MaterialButton add_member_submit = null;
    FloatingActionButton add_member = null;
    MaterialCardView add_member_prompt = null;
    TextInputEditText add_member_email = null;
    BoardInformation current_board;

    private class KickMemberButtonInfo {
        MaterialButton button;
        MemberInformation memberInfo;

        public KickMemberButtonInfo(MaterialButton button, MemberInformation memberInfo){
            this.button = button;
            this.memberInfo = memberInfo;
        }
    }
    private ArrayList<KickMemberButtonInfo> kick_buttons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_members_activity);

        current_board = (BoardInformation) getIntent().getSerializableExtra("BOARD_INFO");
        assert current_board != null;

        LinearLayout containerLayout = findViewById(R.id.ManageMemberListContainer);
        LayoutInflater inflater = getLayoutInflater();

        // String[] names = {"Apple", "Banana", "Cherry", "Dates", "Elderberry", "Fig", "Grape", "Hackberry", "Indian Plum", "Juniper", "Kiwi", "Lemon", "Mango", "Nectarine"};
        // ArrayList<MemberInformation> memberList = new ArrayList<>();
        // for(String name : names){
        //     memberList.add(new MemberInformation(name + "@example.com", name));
        // }

        ArrayList<MemberInformation> memberList = HttpRequestProxy.getProxy().getAllMembersForBoard(current_board.boardID);

        for(MemberInformation member : memberList){

            View currentElement = inflater.inflate(R.layout.manage_members_list_item, containerLayout, false);
            TextView memberNameText = currentElement.findViewById(R.id.manage_members_item_name);
            TextView memberEmailText = currentElement.findViewById(R.id.manage_members_item_email);
            memberNameText.setText(member.nickname);
            memberEmailText.setText(member.email);
            MaterialButton kickButton = currentElement.findViewById(R.id.manage_members_kick_button);
            kickButton.setOnClickListener(this);
            kick_buttons.add(new KickMemberButtonInfo(kickButton, member));

            containerLayout.addView(currentElement);
        }

        add_member = findViewById(R.id.add_member_button);
        add_member_submit = findViewById(R.id.add_member_submit);
        add_member_cancel = findViewById(R.id.add_member_cancel);
        add_member_prompt = findViewById(R.id.add_member_prompt);
        add_member_email = findViewById(R.id.add_member_email_entry);

        add_member.setOnClickListener(this);
        add_member_submit.setOnClickListener(this);
        add_member_cancel.setOnClickListener(this);
        add_member_prompt.setOnClickListener(this);
        add_member_email.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        if(v == add_member){
            add_member_prompt.setVisibility(View.VISIBLE);
        } else if(v == add_member_cancel){
            add_member_prompt.setVisibility(View.GONE);
        } else if(v == add_member_submit){
            Boolean success = HttpRequestProxy.getProxy().inviteUser(current_board.boardID, add_member_email.getText().toString());
            if(success) {
                add_member_prompt.setVisibility(View.GONE);
            }else {
                Toast.makeText(this, "Failed to invited user.", Toast.LENGTH_SHORT).show();
            }
        } else{
            for(KickMemberButtonInfo button : kick_buttons){
                if(button.button == v){
                    Boolean success = HttpRequestProxy.getProxy().kickUser(current_board.boardID, button.memberInfo.email);
                    if(success) {
                        Toast.makeText(this, "Successfully kicked user.", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this, "Failed to kick user.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
