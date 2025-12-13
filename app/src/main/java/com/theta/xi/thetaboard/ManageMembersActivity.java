package com.theta.xi.thetaboard;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.theta.xi.thetaboard.datacontainers.BoardInformation;
import com.theta.xi.thetaboard.datacontainers.InviteResponse;
import com.theta.xi.thetaboard.datacontainers.MemberInformation;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ManageMembersActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialButton add_member_cancel = null;
    MaterialButton add_member_submit = null;
    FloatingActionButton add_member = null;
    MaterialCardView add_member_prompt = null;
    BoardInformation current_board;
    private LinearLayout containerLayout;
    private LayoutInflater inflater;
    private final Executor thread = Executors.newSingleThreadExecutor();
    private final Handler callback = new Handler(Looper.getMainLooper());

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

        current_board = (BoardInformation) getIntent().getSerializableExtra("boardData");
        assert current_board != null;

        containerLayout = findViewById(R.id.ManageMemberListContainer);
        inflater = getLayoutInflater();

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
        getOnBackPressedDispatcher().addCallback(this, callback);

        loadMembers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMembers();
    }

    private void loadMembers() {
        thread.execute(() -> {
            ArrayList<MemberInformation> memberList = HttpRequestProxy.getProxy().getAllMembersForBoard(current_board.boardID);

            callback.post(() -> {
                kick_buttons.clear();
                containerLayout.removeAllViews();

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
            });
        });
    }

    private void showInviteCodeDialog(String joinCode, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invite");
        builder.setMessage("Give this code for others to join:\n\n" +
                joinCode + "\n\n" + message);
        builder.setPositiveButton("Copy Code", (dialog, which) -> {
            android.content.ClipboardManager clipboard =
                    (android.content.ClipboardManager) getSystemService(android.content.Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Invite Code", joinCode);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "Code copied to clipboard", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Close", null);
        builder.show();
    }

    @Override
    public void onClick(View v) {
        if(v == add_member){
            add_member_prompt.setVisibility(View.VISIBLE);
        } else if(v == add_member_cancel){
            add_member_prompt.setVisibility(View.GONE);
        } else if(v == add_member_submit){
            thread.execute(() -> {
                InviteResponse response = HttpRequestProxy.getProxy().inviteUser(current_board.boardID);
                callback.post(() -> {
                    if(response.success && response.joinCode != null) {
                        add_member_prompt.setVisibility(View.GONE);
                        showInviteCodeDialog(response.joinCode, response.message);
                    } else {
                        Toast.makeText(this, "Invite code generation failed", Toast.LENGTH_LONG).show();
                    }
                });
            });
        } else{
            for(KickMemberButtonInfo button : kick_buttons){
                if(button.button == v){
                    thread.execute(() -> {
                        Boolean success = HttpRequestProxy.getProxy().kickUser(current_board.boardID, button.memberInfo.email);
                        callback.post(() -> {
                            if(success) {
                                Toast.makeText(this, "Successfully kicked user.", Toast.LENGTH_SHORT).show();
                                loadMembers();
                            }else {
                                Toast.makeText(this, "Failed to kick user.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    });
                }
            }
        }
    }
}