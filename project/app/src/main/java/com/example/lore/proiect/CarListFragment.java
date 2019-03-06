package com.example.lore.proiect;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CarListFragment extends Fragment {

    private CarViewModel carsViewModel;
    private RecyclerView mCarsList;

    public static CarListFragment newInstance() {
        return new CarListFragment();
    }    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.car_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mCarsList = view.findViewById(R.id.note_list);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mCarsList.setLayoutManager(new LinearLayoutManager(getActivity()));
        carsViewModel = ViewModelProviders.of(this).get(CarViewModel.class);
        carsViewModel.getCars().observe(this, new Observer<List<Car>>() {
            @Override
            public void onChanged(@Nullable List<Car> cars) {
                System.out.println(cars);

                List<Car> cs = new ArrayList<>();
                //cs.add(new Car(1, "gucci", "100", 150D, 1996, true));

                CarListAdapter carListAdapter = new CarListAdapter(getActivity(), cars);
                mCarsList.setLayoutManager(new LinearLayoutManager(getContext()));
                mCarsList.setAdapter(carListAdapter);


                SharedPreferences sharedpreferences = getActivity().getSharedPreferences("myprefs",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.remove("items");
                editor.commit();

                editor.putString("items", cars.toString());
                editor.commit();

                System.out.println(cars.toString());
            }
        });

    }
}
