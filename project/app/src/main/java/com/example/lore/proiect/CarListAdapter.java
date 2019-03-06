package com.example.lore.proiect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarsViewHolder> {

    private Context mContext;
    private List<Car> mCars;

    public CarListAdapter(Context mContext, List<Car> mCars) {
        this.mContext = mContext;
        this.mCars = mCars;
    }

//    @Override
////    public CarsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
////        View view = LayoutInflater.from(mContext).inflate(R.layout.car_layout, viewGroup, false);
////        if(mCars.get(i).getChecked()==true)
////            view = LayoutInflater.from(mContext).inflate(R.layout.car_layout2, viewGroup, false);
////        return new CarsViewHolder(view);
////    }

    @Override
    public CarsViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        if(mCars.get(position).getChecked()==true)
            return new CarsViewHolder(layoutInflater.inflate(R.layout.car_layout2, viewGroup, false));
        return new CarsViewHolder(layoutInflater.inflate(R.layout.car_layout, viewGroup, false));
        // returns the view holder which is created from the view
    }

    @Override
    public void onBindViewHolder(final CarsViewHolder carsViewHolder, final int position) {
        //call bindViewHolder function where you format the view of each item
        carsViewHolder.bindViewHolder(mCars.get(position));

        carsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCars.get(position).getChecked())
                    mCars.get(position).setChecked(false);
                else {
                    mCars.get(position).setChecked(true);
                }
                notifyItemChanged(position);
            }

        });

    }

    @Override
    public int getItemCount() {
        return mCars.size();
    }

    @Override
    public int getItemViewType(int position) {
        // returns a code that will be used in method onCreateViewHolder to determine which layout to inflate for this item
        return position;
    }

    class CarsViewHolder extends RecyclerView.ViewHolder {
        TextView listElem;
        ImageView checked;

        CarsViewHolder(View itemView) {
            super(itemView);
            listElem = itemView.findViewById(R.id.car_element);
            checked = itemView.findViewById(R.id.checked);
        }


        public void bindViewHolder(Car c) {
            listElem.setText(c.getBrand()+" ");
        }

    }
}
