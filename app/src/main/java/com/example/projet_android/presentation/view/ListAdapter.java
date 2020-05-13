package com.example.projet_android.presentation.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_android.R;
import com.example.projet_android.presentation.Singletons;
import com.example.projet_android.presentation.model.Champion;
import com.example.projet_android.presentation.model.ClasseEtOrigine;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ClasseEtOrigine> values;
    private Context context;

    class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txtHeader;
        TextView txtFooter;
        ImageView imageView;
        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.firstLine);
            txtFooter = (TextView) v.findViewById(R.id.secondLine);
            imageView = (ImageView) v.findViewById(R.id.icon);
        }
    }

    public void add(int position, ClasseEtOrigine item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    ListAdapter(List<ClasseEtOrigine> myDataset, Context context) {
        values = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ClasseEtOrigine currentClass = values.get(position);
        holder.txtHeader.setText(currentClass.getName());
        onItemClick(holder, currentClass);
        String url = "https://raw.githubusercontent.com/ChristianStephenn/Projet_Android/master/img/ClassOrigin/" + currentClass.getIcon() + ".png";
        Picasso.get().load(url).into(holder.imageView);
        holder.txtFooter.setText(currentClass.getDescription());
    }

    private void onItemClick(ViewHolder holder, final ClasseEtOrigine currentClass){
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Champion> champList = currentClass.getChampions();
                String jsonList = Singletons.getGson().toJson(champList);
                Intent champ = new Intent(context, ChampionsActivity.class);
                champ.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                champ.putExtra("className", currentClass.getName());
                champ.putExtra("Desc",currentClass.getDescription());
                champ.putExtra("champList",jsonList);
                context.startActivity(champ);
            }
        });

    }

    @Override
    public int getItemCount() {
        return values.size();
    }
}