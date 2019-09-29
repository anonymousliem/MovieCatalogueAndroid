package com.anonymousliem.retrofit2.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.anonymousliem.retrofit2.Activity.DetailActivity;
import com.anonymousliem.retrofit2.Model.Movie;
import com.anonymousliem.retrofit2.R;
import com.anonymousliem.retrofit2.Util.Constant;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie.Results> results;
    private Context context;
    private ArrayList<String> data;

    public MovieAdapter(Context context, List<Movie.Results> results) {
        this.results = results;
        this.context = context;
    }

    public MovieAdapter() {
        results = new ArrayList<>();
    }

    public void refill(ArrayList<Movie.Results> results) {
        this.results = new ArrayList<>();
        this.results.addAll(results);

        notifyDataSetChanged();
    }


    @NotNull
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        @SuppressLint("InflateParams") View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, null);
        return new ViewHolder(v);
    }

    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int i) {
        final Movie.Results result = results.get(i);

        viewHolder.txtTitle.setText(result.getTitle());
        viewHolder.txtTvTitle.setText(result.getName());
        viewHolder.txtDesc.setText(result.getOverview());
        Picasso.get()
                .load(Constant.BACKDROP_PATH + result.getBackdrop_path())
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .into(viewHolder.imgThumb);
        viewHolder.btnDetails.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title_movie",
                        result.getTitle());
                intent.putExtra(context.getString(R.string.intent_backdrop_path),
                        result.getBackdrop_path());
                intent.putExtra(context.getString(R.string.intent_overview),
                        result.getOverview());
                intent.putExtra("name",
                        result.getName());
                intent.putExtra("moviesKey", result.getRelease_date());
                intent.putExtra("EXTRA_POSITION", i);
                intent.putExtra("EXTRA_FAVORITE", result);
                intent.putExtra("id_movie", result.getId());

                context.startActivity(intent);
            }
        });
    }


    public int getItemCount() {
        return results.size();
    }

    public ArrayList<String> getData() {
        return data;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle, txtDesc, txtTvTitle;
        ImageView imgThumb;
        Button btnDetails;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDesc = itemView.findViewById(R.id.txtDesc);
            imgThumb = itemView.findViewById(R.id.thumb);
            txtTvTitle = itemView.findViewById(R.id.txtTvTitle);
            btnDetails = itemView.findViewById(R.id.btnDetails);

        }
    }
}