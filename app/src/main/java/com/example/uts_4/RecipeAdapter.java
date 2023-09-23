package com.example.uts_4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private ArrayList<RecipeModel> RecipeList;
    private Context context;
    private ItemClickInterface itemClickInterface;
    int lastpos = -1;

        public RecipeAdapter(ArrayList<RecipeModel> recipeList, Context context, ItemClickInterface itemClickInterface) {
        RecipeList = recipeList;
        this.context = context;
        this.itemClickInterface = itemClickInterface;
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        RecipeModel model = RecipeList.get(position);
        holder.title.setText(model.getRecipeTitle());
        holder.type.setText(model.getRecipeType());
        holder.desc.setText(model.getRecipeDesc());
        holder.ingredients.setText(model.getRecipeIngre());
        Anim(holder.itemView,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickInterface.onItemClick(position);
            }
        });
    }

    private void Anim(View view, int pos){
        if(pos>lastpos){
            Animation anim = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            view.setAnimation(anim);
            lastpos = pos;
        }
    }
    @Override
    public int getItemCount() {
        return RecipeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title,type,desc,ingredients;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.idRVtitle);
            type = itemView.findViewById(R.id.idRVtype);
            desc = itemView.findViewById(R.id.idRVdesc);
            ingredients = itemView.findViewById(R.id.idRVingre);
        }
    }

    public interface ItemClickInterface{
        void onItemClick(int pos);
    }
}
