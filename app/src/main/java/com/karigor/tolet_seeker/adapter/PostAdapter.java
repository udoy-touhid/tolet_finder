package com.karigor.tolet_seeker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.karigor.tolet_seeker.R;
import com.karigor.tolet_seeker.data.model.HouseModel;

/**
 * RecyclerView adapter for a list of Restaurants.
 */
public class PostAdapter extends FirestoreAdapter<PostAdapter.ViewHolder> {

    public interface OnPostSelectedListener {

        void onRestaurantSelected(DocumentSnapshot data);

    }

    private OnPostSelectedListener mListener;
    public String authenticated_user_id;

    public PostAdapter(Query query, OnPostSelectedListener listener,String authenticated_user_id) {
        super(query);
        mListener = listener;
        this.authenticated_user_id = authenticated_user_id;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewHolder viewHolder = new ViewHolder(inflater.inflate(R.layout.item_restaurant, parent, false));
        viewHolder.user_id = authenticated_user_id;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(getSnapshot(position), mListener);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        String user_id;
        ImageView imageView;
        TextView nameView;
        TextView priceView;
        TextView categoryView;
        TextView cityView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.restaurantItemImage);
            nameView = itemView.findViewById(R.id.restaurantItemName);
            priceView = itemView.findViewById(R.id.restaurantItemPrice);
            categoryView = itemView.findViewById(R.id.restaurantItemCategory);
            cityView = itemView.findViewById(R.id.restaurantItemCity);
        }

        public void bind(final DocumentSnapshot snapshot,
                         final OnPostSelectedListener listener) {

            HouseModel houseModel = snapshot.toObject(HouseModel.class);

            // Load image
//            Glide.with(imageView.getContext())
//                    .load(houseModel)
//                    .into(imageView);

            nameView.setText(houseModel.getArea_no());

//            if(houseModel.ge().equals(user_id))
//                nameView.append("(YOU)");

            cityView.setText(houseModel.getDistrict());
            categoryView.setText(houseModel.getDistrict());


            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onRestaurantSelected(snapshot);
                    }
                }
            });
        }

    }
}
