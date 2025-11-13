package com.theta.xi.thetaboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;
import com.theta.xi.thetaboard.datacontainers.BoardInformation;

import java.util.ArrayList;

public class BulletinBoardsFragment extends Fragment implements View.OnClickListener {

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

        LinearLayout containerLayout = view.findViewById(R.id.BulletinBoardListContainer);
        LayoutInflater inflater = getLayoutInflater();

        // TODO: replace with a request (more likely a call to a requests class)
        String[] items = {"Apple", "Banana", "Cherry", "Dates", "Elderberry", "Fig", "Grape", "ahhh"};
        ArrayList<BoardInformation> boards = new ArrayList<>();
        int i = 0;
        for(String item : items){
            boards.add(new BoardInformation(item, i % 2 != 0, i));
            i++;
        }

        for(BoardInformation board : boards){

            View currentElement = inflater.inflate(R.layout.bullatin_boards_list_item, containerLayout, false);
            TextView currentText = currentElement.findViewById(R.id.board_list_item_name);
            ShapeableImageView currentImage = currentElement.findViewById(R.id.board_list_item_image);
            currentText.setText(board.name);
            currentElement.setOnClickListener(this);

            containerLayout.addView(currentElement);
        }
    }

    @Override
    public void onClick(View v) {
        assert getActivity() != null;
        Intent intent = new Intent(getActivity(), ViewBoardActivity.class);
        startActivity(intent);
    }
}
