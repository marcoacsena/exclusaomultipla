package com.example.exclusaomultipla.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.exclusaomultipla.R;
import com.example.exclusaomultipla.model.Tarefa;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder> {

    private Context context;
    private List<Tarefa> listaDeTarefas;
    private OnClickListener onClickListener = null;

    private SparseBooleanArray selected_items;
    private int CURRENT_SELECTED_IDX = -1;


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public class TarefaViewHolder extends RecyclerView.ViewHolder{

        private TextView tvListaTarefaAdapter, image_letter;
        public CircularImageView image;
        public RelativeLayout lyt_checked, lyt_image;
        public View lyt_parent;

        public TarefaViewHolder(View view) {
            super(view);
            tvListaTarefaAdapter = itemView.findViewById(R.id.tvListaTarefaAdapter);
            image_letter = (TextView) view.findViewById(R.id.image_letter);
            image =  (CircularImageView) view.findViewById(R.id.image);
            lyt_checked = (RelativeLayout) view.findViewById(R.id.lyt_checked);
            lyt_image = (RelativeLayout) view.findViewById(R.id.lyt_image);
            lyt_parent = (View) view.findViewById(R.id.lyt_parent);
        }
    }

    public TarefaAdapter(Context contex, List<Tarefa> listaDeTarefas) {
        this.context = contex;
        this.listaDeTarefas = listaDeTarefas;
        selected_items = new SparseBooleanArray();
    }


    @NonNull
    @Override
    public TarefaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_tarefa_adapter,
                parent, false);

        return new TarefaViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(final TarefaViewHolder holder, final int position) {
        final Tarefa tarefa = listaDeTarefas.get(position);

        // displaying text view data
        holder.tvListaTarefaAdapter.setText(tarefa.getNomeTarefa());

        holder.image_letter.setText(tarefa.getNomeTarefa().substring(0, 1));

        holder.lyt_parent.setActivated(selected_items.get(position, false));

        holder.lyt_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener == null) return;
                onClickListener.onItemClick(v, tarefa, position);
            }
        });

        holder.lyt_parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onClickListener == null) return false;
                onClickListener.onItemLongClick(v, tarefa, position);
                return true;
            }
        });

        toggleCheckedIcon(holder, position);
        displayImage(holder, tarefa);

    }

    private void displayImage(TarefaViewHolder holder, Tarefa tarefa) {
        if (tarefa.image != null) {
            holder.image.setImageResource(tarefa.image);
            holder.image.setColorFilter(null);
            holder.image_letter.setVisibility(View.GONE);
        } else {
            holder.image.setImageResource(R.drawable.shape_circle);
            holder.image.setColorFilter(tarefa.color);
            holder.image_letter.setVisibility(View.VISIBLE);
        }
    }

    private void toggleCheckedIcon(TarefaViewHolder holder, int position) {
        if (selected_items.get(position, false)) {
            holder.lyt_image.setVisibility(View.GONE);
            holder.lyt_checked.setVisibility(View.VISIBLE);
            if (CURRENT_SELECTED_IDX == position) resetCurrentIndex();
        } else {
            holder.lyt_checked.setVisibility(View.GONE);
            holder.lyt_image.setVisibility(View.VISIBLE);
            if (CURRENT_SELECTED_IDX == position) resetCurrentIndex();
        }
    }

    public Tarefa getItem(int position) {
        return listaDeTarefas.get(position);
    }

    @Override
    public int getItemCount() {
        return listaDeTarefas.size();
    }

    public void toggleSelection(int pos) {
        CURRENT_SELECTED_IDX = pos;
        if (selected_items.get(pos, false)) {
            selected_items.delete(pos);
        } else {
            selected_items.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void clearSelections() {
        selected_items.clear();
        notifyDataSetChanged();
    }

    public int getSelectedItemCount() {
        return selected_items.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items = new ArrayList<>(selected_items.size());
        for (int i = 0; i < selected_items.size(); i++) {
            items.add(selected_items.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {
        listaDeTarefas.remove(position);
        resetCurrentIndex();
    }

    private void resetCurrentIndex() {
        CURRENT_SELECTED_IDX = -1;
    }

    public interface OnClickListener {
        void onItemClick(View view, Tarefa obj, int pos);

        void onItemLongClick(View view, Tarefa obj, int pos);
    }

}
