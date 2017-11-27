package ru.softomate.softomatetest.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.softomate.softomatetest.R;
import ru.softomate.softomatetest.data.Text;

/**
 * Created by Вараздат on 25.11.2017.
 */



public class TextAdapter extends RecyclerView.Adapter<TextAdapter.ViewHolder>{


    private List<Text> mTexts;

    public TextAdapter(List<Text> cities){
        mTexts = cities;
    }




    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textTV;
        public TextView languageTV;

        public ViewHolder(View itemView){
            super(itemView);
            textTV = itemView.findViewById(R.id.item_text);
            languageTV = itemView.findViewById(R.id.item_language);
        }
    }


    @Override
    public TextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,
                parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(TextAdapter.ViewHolder holder, final int position) {
        holder.textTV.setText(mTexts.get(position).getText());
        holder.languageTV.setText(mTexts.get(position).getLanguage());
    }


    public void setTexts(List<Text> texts) {
        mTexts = texts;
    }

    public void addText(Text text){
        mTexts.add(text);
    }

    @Override
    public int getItemCount() {
        Log.d("MyTag","size = " + mTexts.size());
        return mTexts.size();
    }

}

