package com.paintcalculatorapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.paintcalculatorapp.databinding.FragmentRoomShapeBinding;

public class FragmentRoomShape extends Fragment {

    private FragmentRoomShapeBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentRoomShapeBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rectangleButton.setOnClickListener(v ->
                NavHostFragment.findNavController(FragmentRoomShape.this)
                        .navigate(R.id.action_FragmentRoomShape_to_FragmentRectangularRoom)
        );

        binding.roundButton.setOnClickListener(v ->
                NavHostFragment.findNavController(FragmentRoomShape.this)
                        .navigate(R.id.action_FragmentRoomShape_to_FragmentRoundRoom)
        );

        binding.lShapeButton.setOnClickListener(v ->
                NavHostFragment.findNavController(FragmentRoomShape.this)
                        .navigate(R.id.action_FragmentRoomShape_to_FragmentLShapeRoom)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}