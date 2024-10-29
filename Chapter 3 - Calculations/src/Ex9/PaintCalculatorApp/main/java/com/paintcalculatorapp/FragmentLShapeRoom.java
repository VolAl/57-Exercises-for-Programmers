package com.paintcalculatorapp;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.paintcalculatorapp.databinding.FragmentLShapeRoomBinding;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;

public class FragmentLShapeRoom extends Fragment {

    private FragmentLShapeRoomBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLShapeRoomBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText m1 = binding.lShapeM1Value;
        EditText m2 = binding.lShapeM2Value;
        EditText m3 = binding.lShapeM3Value;
        EditText m4 = binding.lShapeM4Value;

        m1.setOnKeyListener(
                new View.OnKeyListener()
                {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        calculateArea(m1.getText().toString(),
                                m2.getText().toString(),
                                m3.getText().toString(),
                                m4.getText().toString());

                        return false;
                    }
                });

        m2.setOnKeyListener(
                new View.OnKeyListener()
                {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        calculateArea(m1.getText().toString(),
                                m2.getText().toString(),
                                m3.getText().toString(),
                                m4.getText().toString());

                        return false;
                    }
                });

        m3.setOnKeyListener(
                new View.OnKeyListener()
                {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        calculateArea(m1.getText().toString(),
                                m2.getText().toString(),
                                m3.getText().toString(),
                                m4.getText().toString());

                        return false;
                    }
                });

        m4.setOnKeyListener(
                new View.OnKeyListener()
                {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        calculateArea(m1.getText().toString(),
                                m2.getText().toString(),
                                m3.getText().toString(),
                                m4.getText().toString());

                        return false;
                    }
                });
    }

    private void calculateArea(String m1String, String m2String, String m3String, String m4String) {
        if(!m1String.isEmpty() && !m2String.isEmpty() && !m3String.isEmpty() && !m4String.isEmpty()) {
            BigDecimal m1 = new BigDecimal(m1String);
            BigDecimal m2 = new BigDecimal(m2String);
            BigDecimal m3 = new BigDecimal(m3String);
            BigDecimal m4 = new BigDecimal(m4String);
            BigDecimal firstArea = m2.multiply(m3);
            BigDecimal secondArea = (m1.subtract(m3)).multiply(m2.subtract(m4));
            BigDecimal roomArea = firstArea.add(secondArea);
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