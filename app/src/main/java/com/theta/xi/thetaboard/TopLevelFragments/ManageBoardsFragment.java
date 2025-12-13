package com.theta.xi.thetaboard.TopLevelFragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.theta.xi.thetaboard.HttpRequestProxy;
import com.theta.xi.thetaboard.ManageMembersActivity;
import com.theta.xi.thetaboard.R;
import com.theta.xi.thetaboard.datacontainers.BoardInformation;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ManageBoardsFragment extends Fragment implements View.OnClickListener {

    MaterialButton add_board_cancel = null;
    MaterialButton add_board_submit = null;
    FloatingActionButton add_board = null;
    MaterialCardView add_board_prompt = null;
    TextInputEditText add_board_code_entry = null;
    private LinearLayout containerLayout;
    private LayoutInflater inflater;
    ArrayList<ButtonInfo> manage_members_buttons = new ArrayList<>();
    ArrayList<ButtonInfo> delete_board_buttons = new ArrayList<>();
    //network requests
    private final Executor thread = Executors.newSingleThreadExecutor();
    private final Handler callback = new Handler(Looper.getMainLooper());

    private class ButtonInfo {
        MaterialButton button;
        BoardInformation board;

        ButtonInfo(MaterialButton button, BoardInformation board){
            this.button = button;
            this.board = board;
        }
    }


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

        containerLayout = view.findViewById(R.id.ManageBoardsListContainer);
        inflater = getLayoutInflater();
        add_board = view.findViewById(R.id.add_board_button);
        add_board_submit = view.findViewById(R.id.add_board_submit);
        add_board_cancel = view.findViewById(R.id.add_board_cancel);
        add_board_prompt = view.findViewById(R.id.add_board_prompt);
        add_board_code_entry = view.findViewById(R.id.add_board_code_entry);

        add_board.setOnClickListener(this);
        add_board_submit.setOnClickListener(this);
        add_board_cancel.setOnClickListener(this);
        add_board_prompt.setOnClickListener(this);

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
        loadBoards();
    }

    //technically not needed but could be useful if screen is pulled up unexpectly
    @Override
    public void onResume() {
        super.onResume();
        loadBoards();
    }

    private void loadBoards() {
        //no network requests in main thread
        thread.execute(() -> {
            ArrayList<BoardInformation> boards = HttpRequestProxy.getProxy().getAllBoardsForUser();
            callback.post(() -> {
                manage_members_buttons.clear();
                delete_board_buttons.clear();
                containerLayout.removeAllViews();
                for(BoardInformation board : boards){
                    View currentElement = inflater.inflate(R.layout.manage_boards_list_item, containerLayout, false);
                    TextView currentText = currentElement.findViewById(R.id.manage_boards_item_name);
                    currentText.setText(board.name);
                    MaterialButton manageMembersButton = currentElement.findViewById(R.id.manage_members_button);
                    MaterialButton deleteBoardButton = currentElement.findViewById(R.id.delete_board_button);
                    //this is where the privs become useful to hide and show stuff
                    if(board.userIsAdmin == true) {
                        manageMembersButton.setVisibility(View.VISIBLE);
                        manageMembersButton.setOnClickListener(this);
                        deleteBoardButton.setOnClickListener(this);
                        manage_members_buttons.add(new ButtonInfo(manageMembersButton, board));
                        delete_board_buttons.add(new ButtonInfo(deleteBoardButton, board));
                    }else{
                        manageMembersButton.setVisibility(View.INVISIBLE);
                    }
                    containerLayout.addView(currentElement);
                }
            });
        });
    }

    @Override
    public void onClick(View v) {
        if(v == add_board){
            add_board_prompt.setVisibility(View.VISIBLE);
        } else if(v == add_board_cancel){
            add_board_prompt.setVisibility(View.GONE);
        } else if(v == add_board_submit){
            String code = add_board_code_entry.getText().toString().trim();
            thread.execute(() -> {
                Boolean success = HttpRequestProxy.getProxy().joinBoard(code);
                callback.post(() -> {
                    assert getActivity() != null;
                    if(success) {
                        Toast.makeText(getActivity(), "Successfully added board.", Toast.LENGTH_SHORT).show();
                        add_board_prompt.setVisibility(View.GONE);
                        add_board_code_entry.setText("");
                        loadBoards();
                    }else {
                        Toast.makeText(getActivity(), "Failed to add board.", Toast.LENGTH_SHORT).show();
                    }
                });
            });
        } else {
            for(ButtonInfo buttonInfo : manage_members_buttons){
                if(v == buttonInfo.button){
                    assert getActivity() != null;
                    Intent intent = new Intent(getActivity(), ManageMembersActivity.class);
                    intent.putExtra("boardData", buttonInfo.board);
                    startActivity(intent);
                }
            }
            //this needs to be fixed
            for(ButtonInfo buttonInfo : delete_board_buttons){

            }
        }
    }
}