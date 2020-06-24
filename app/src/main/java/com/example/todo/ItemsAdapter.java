package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{
    /**
     * Class to 'adapt' items (data) into a view (Recycle View) and render the items.
     * Extends the base generic Adapter class into an ItemsAdapter
     * (of the ViewHolder type returned by onCreateViewHolder)
     */

    List<String> elements;
    OnLongClickListener longClickListener;

    public interface OnLongClickListener{
        void onItemLongClick(int position);
    }

    public ItemsAdapter(List<String> listElements, OnLongClickListener longClickListener) {
        elements = listElements;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /**
        * @return ViewHolder holding the instance of one rendered layout containing 'Views'
        */

        //Inflate ('render') the single view element's layout.
        View inflatedElement = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        ViewHolder elementHolder = new ViewHolder(inflatedElement);

        return elementHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /**
         * Method that binds data to the corresponding ViewHolder.
         */

        //Grabs element from the instance variable elements
        String textElement = elements.get(position);

        //Binds data to the corresponding ViewHolder
        holder.bind(textElement);
    }

    @Override
    public int getItemCount() {
        return elements.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        /**
         * Class composition.
         * The ViewHolder class defines the data that will be inserted into each ViewHolder instance.
         */

        TextView elementView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            elementView = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String textViewElement){
            elementView.setText(textViewElement);

            //Binds listener for long press to each ViewHolder
            elementView.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View view) {
                    //Notifies listener about the position of long-pressed item.
                    longClickListener.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });
        }
    }

}
