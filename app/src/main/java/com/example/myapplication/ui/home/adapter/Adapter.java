package com.example.myapplication.ui.home.adapter;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.App;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemRvBinding;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.HomeViewHolder> {
    private Listen listen;
    private List<HomeModel> list = new ArrayList<>();

    public Adapter(Listen listen) {
        this.listen = listen;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(ItemRvBinding.inflate(LayoutInflater.from(parent.getContext()), parent,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.onBind(list.get(position), listen);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addList(List<HomeModel> homeModelList) {
        list = homeModelList;
        notifyDataSetChanged();
    }
    public void remove (HomeModel homeModel, int post){
        App.fillDataBase.fillDao().delete(homeModel);
        notifyItemChanged(post);
    }


    public HomeModel getModelToId(long id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                return list.get(i);
            }
        }
        return null;
    }


    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        private final ItemRvBinding binding;

        public HomeViewHolder(@NonNull ItemRvBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }


        public void onBind(HomeModel homeModel, Listen listen) {

            binding.dateAdd.setText(homeModel.getDate());

            binding.debt2.setText(homeModel.getName());
            binding.description.setText(homeModel.getDescription());
            binding.debt.setText(homeModel.getDebt());
            binding.dateEdit.setText(homeModel.getEditDate());

            binding.getRoot().setOnClickListener(v -> {

                listen.setDataForForm(homeModel, getAdapterPosition());
            });

            binding.getRoot().setOnLongClickListener(v -> {
                listen.del(homeModel, getAdapterPosition());
                return true;
            });
        }

    }

}
