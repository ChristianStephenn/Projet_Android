package com.example.projet_android.presentation.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projet_android.R;
import com.example.projet_android.presentation.model.Champion;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapterMyTeam extends RecyclerView.Adapter<ListAdapterMyTeam.ViewHolder> {
    private List<Champion> values;
    private OnItemClickListenerTeamChamp listenerchamp;

    public interface OnItemClickListenerTeamChamp {
        void onItemClick(Champion item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView txtHeader;
        TextView txtFooter;
        ImageView imageView;
        View layout;

        ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = v.findViewById(R.id.firstLine);
            txtFooter = v.findViewById(R.id.secondLine);
            imageView = v.findViewById(R.id.icon);
        }
    }

    public void add(int position, Champion item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    private void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    ListAdapterMyTeam(List<Champion> myDataset, OnItemClickListenerTeamChamp listenerchamp) {
        this.values = myDataset;
        this.listenerchamp = listenerchamp;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ListAdapterMyTeam.ViewHolder onCreateViewHolder(ViewGroup parent,
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

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final Champion currentChamp = values.get(position);
        final String url = "https://raw.githubusercontent.com/ChristianStephenn/Projet_Android/master/img/Champions/" + currentChamp.getIcon() + ".png";

        holder.txtHeader.setText(currentChamp.getName());
        Picasso.get().load(url).into(holder.imageView);
        String coast = "Classes or origins: " + currentChamp.getTraitsToString();
        holder.txtFooter.setText(coast);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listenerchamp.onItemClick(currentChamp);
                remove(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

}