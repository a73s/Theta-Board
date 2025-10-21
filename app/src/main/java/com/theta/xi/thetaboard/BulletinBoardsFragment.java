package com.theta.xi.thetaboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;

public class BulletinBoardsFragment extends Fragment {

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

        // Loop through your data
        String[] items = {"Apple", "Banana", "Cherry", "Dates", "Elderberry", "Fig", "Grape"};
        for(String itemText : items){

            View currentElement = inflater.inflate(R.layout.bullatin_boards_list_item, containerLayout, false);
            TextView currentText = currentElement.findViewById(R.id.board_list_item_name);
            ShapeableImageView currentImage = currentElement.findViewById(R.id.board_list_item_image);
            currentText.setText(itemText);

            containerLayout.addView(currentElement);
        }
    }
}
