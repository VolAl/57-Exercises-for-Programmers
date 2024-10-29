package com.paintcalculatorapp;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.paintcalculatorapp.databinding.FragmentRoundRoomBinding;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;

public class FragmentRoundRoom extends Fragment {

    private FragmentRoundRoomBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentRoundRoomBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText roundRadiusValue = binding.roundRadiusValue;

        roundRadiusValue.setOnKeyListener(
                new View.OnKeyListener()
                {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        String roomRadiusString = roundRadiusValue.getText().toString();
                        if(!roomRadiusString.isEmpty()) {
                            BigDecimal roomRadius = new BigDecimal(roundRadiusValue.getText().toString());
                            BigDecimal roomArea = roomRadius.multiply(roomRadius).multiply(BigDecimal.valueOf(Math.PI).setScale(0, RoundingMode.HALF_EVEN));
                            BigDecimal areaCoveredByOneGallon = BigDecimal.valueOf(350);
                            BigDecimal gallonsNeeded = roomArea.divide(areaCoveredByOneGallon, RoundingMode.UP);

                            binding.outputTextView.setText(MessageFormat.format("You will need to purchase {0} gallon{1} of paint to cover {2} square feet.", gallonsNeeded, gallonsNeeded.compareTo(BigDecimal.valueOf(1)) > 0 ? "s" : "", roomArea));
                        } else {
                            binding.outputTextView.setText("");
                        }

                        return false;
                    }
                });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}