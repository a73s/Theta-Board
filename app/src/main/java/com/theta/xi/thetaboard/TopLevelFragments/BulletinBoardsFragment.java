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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.theta.xi.thetaboard.HttpRequestProxy;
import com.theta.xi.thetaboard.R;
import com.theta.xi.thetaboard.ViewBoardActivity;
import com.theta.xi.thetaboard.datacontainers.BoardInformation;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BulletinBoardsFragment extends Fragment implements View.OnClickListener {

    private ArrayList<BulletinBoardsFragment.BoardButtonInfo> board_buttons = new ArrayList<>();
    private LinearLayout containerLayout;
    private LayoutInflater inflater;
    //network requests
    private final Executor thread = Executors.newSingleThreadExecutor();
    private final Handler callback = new Handler(Looper.getMainLooper());

    private class BoardButtonInfo {
        View clickable;
        BoardInformation board;
        BoardButtonInfo(View clickable, BoardInformation board){
            this.clickable = clickable;
            this.board = board;
        }
    }


    public BulletinBoardsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bulletin_boards_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        containerLayout = view.findViewById(R.id.BulletinBoardListContainer);
        inflater = getLayoutInflater();
        loadBoards();
    }

    //this fixes boards not showing up after login
    @Override
    public void onResume() {
        super.onResume();
        loadBoards();
    }

    private void loadBoards() {
        //all network calls follow this format in the app as you can't run network requests on the main thread
        thread.execute(() -> {
            ArrayList<BoardInformation> boards = HttpRequestProxy.getProxy().getAllBoardsForUser();
            callback.post(() -> {
                board_buttons.clear();
                containerLayout.removeAllViews();
                for(BoardInformation board : boards){
                    View currentElement = inflater.inflate(R.layout.bullatin_boards_list_item, containerLayout, false);
                    TextView currentText = currentElement.findViewById(R.id.board_list_item_name);
                    currentText.setText(board.name);
                    currentElement.setOnClickListener(this);
                    board_buttons.add(new BoardButtonInfo(currentElement, board));
                    containerLayout.addView(currentElement);
                }
            });
        });
    }

    @Override
    public void onClick(View v) {
        for(BoardButtonInfo boardButton : board_buttons) {
            if(boardButton.clickable == v) {
                assert getActivity() != null;
                Intent intent = new Intent(getActivity(), ViewBoardActivity.class);
                intent.putExtra("boardData", boardButton.board);
                startActivity(intent);
            }
        }
    }
}