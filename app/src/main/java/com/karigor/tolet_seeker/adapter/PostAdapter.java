package com.karigor.tolet_seeker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.karigor.tolet_seeker.R;
import com.karigor.tolet_seeker.data.model.BloodRequestModel;

/**
 * RecyclerView adapter for a list of Restaurants.
 */
public class PostAdapter extends FirestoreAdapter<PostAdapter.ViewHolder> {

    public interface OnPostSelectedListener {

        void onRestaurantSelected(DocumentSnapshot restaurant);

    }

    private OnPostSelectedListener mListener;
    public String authenticated_user_id;

    public PostAdapter(Query query, OnPostSelectedListener listener,String authenticated_user_id) {
        super(query);
        mListener = listener;
        this.authenticated_user_id = authenticated_user_id;

    }

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

            BloodRequestModel bloodRequestModel = snapshot.toObject(BloodRequestModel.class);

            // Load image
            Glide.with(imageView.getContext())
                    .load(bloodRequestModel.getProfilePictureUrl())
                    .into(imageView);

            nameView.setText(bloodRequestModel.getUser_name());

            if(bloodRequestModel.getUser_id().equals(user_id))
                nameView.append("(YOU)");

            cityView.setText(bloodRequestModel.getBlood_group());
            categoryView.setText(bloodRequestModel.getDistrict());


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
