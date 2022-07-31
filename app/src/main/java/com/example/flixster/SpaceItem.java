package com.example.flixster;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItem extends RecyclerView.ItemDecoration {
    final int MARGIN = 32;
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        final int positionItem = parent.getChildAdapterPosition(view);
        if (positionItem == RecyclerView.NO_POSITION) {
            return;
        }
        outRect.top = MARGIN;
        outRect.bottom = MARGIN;
        outRect.left = MARGIN + 5;
        outRect.right = MARGIN + 5;
    }
}
