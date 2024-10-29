package com.paintcalculatorapp;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.paintcalculatorapp.databinding.FragmentRectangularRoomBinding;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;

public class FragmentRectangularRoom extends Fragment {

    private FragmentRectangularRoomBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentRectangularRoomBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText rectangleLengthValue = binding.rectangleLengthValue;
        EditText rectangleWidthValue = binding.rectangleWidthValue;

        rectangleLengthValue.setOnKeyListener(
                new View.OnKeyListener()
                {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        calculateArea(rectangleLengthValue.getText().toString(),
                                rectangleWidthValue.getText().toString());

                        return false;
                    }
                });

        rectangleWidthValue.setOnKeyListener(
                new View.OnKeyListener()
                {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        calculateArea(rectangleLengthValue.getText().toString(),
                                rectangleWidthValue.getText().toString());

                        return false;
                    }
                });
    }

    private void calculateArea(String roomLengthString, String roomWidthString) {
        if(!roomLengthString.isEmpty() && !roomWidthString.isEmpty()) {
            BigDecimal roomLength = new BigDecimal(roomLengthString);
            BigDecimal roomWidth = new BigDecimal(roomWidthString);
            BigDecimal roomArea = roomWidth.multiply(roomLength);
            BigDecimal areaCoveredByOneGallon = BigDecimal.valueOf(350);
            BigDecimal gallonsNeeded = roomArea.divide(areaCoveredByOneGallon, RoundingMode.UP);

            binding.outputTextView.setText(MessageFormat.format("You will need to purchase {0} gallon{1} of paint to cover {2} square feet.", gallonsNeeded, gallonsNeeded.compareTo(BigDecimal.valueOf(1)) > 0 ? "s" : "", roomArea));
        } else {
            binding.outputTextView.setText("");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}