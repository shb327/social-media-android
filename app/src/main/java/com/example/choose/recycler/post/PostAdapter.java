package com.example.choose.recycler.post;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.choose.R;
import com.example.choose.dto.PostDTO;
import com.example.choose.dto.PostType;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{
    public List<PostDTO> localDataSet = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView textType;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.textView5);
            textType = view.findViewById(R.id.textViewTitle);
        }
        public TextView getTextType() { return textType; }
        public TextView getTextView() {
            return textView;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        PostDTO post = localDataSet.get(position);
        PostType type = post.getType();
        viewHolder.getTextView().setText(post.getTitle());
        switch (type){
            case IMAGE:
                viewHolder.getTextType().setText("Image Post");
                break;
            case PETITION:
                viewHolder.getTextType().setText("Petition");
                break;
            case VOTE:
                viewHolder.getTextType().setText("Voting Post");
                break;
            case PLAYOFF:
                viewHolder.getTextType().setText("Play-Off Post");
                break;
            default:
            case TEXT:
                viewHolder.getTextType().setText("Text Post");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
