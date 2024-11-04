package com.bloodalcoholcaltulatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final Map<String,BigDecimal> LEGAL_BAC_FOR_DRIVING_PER_COUNTRY = Map.of(
            "Default", BigDecimal.valueOf(0.08),
            "China", BigDecimal.valueOf(0.02),
            "France", BigDecimal.valueOf(0.05),
            "Japan", BigDecimal.valueOf(0.03)
    );

    private List<String> drinksAdded;
    private BigDecimal totalBac = BigDecimal.valueOf(0.0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drinksAdded = new ArrayList<>();

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button addDrinkBtn = findViewById(R.id.add_drink_button);
        addDrinkBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.add_drink_button) {
            getDataAndDoCalculation();
        }
    }

    private void getDataAndDoCalculation() {

        RadioButton metricRadio = findViewById(R.id.metric_units);
        RadioButton imperialRadio = findViewById(R.id.imperial_units);
        String unitsString = metricRadio.isChecked() ? "M" : imperialRadio.isChecked() ? "I" : "";
        EditText countryValue = findViewById(R.id.country_value);
        String countryString = countryValue.getText().toString();
        EditText weightValue = findViewById(R.id.weight_value);
        String weightString = weightValue.getText().toString();
        RadioButton maleRadio = findViewById(R.id.male);
        RadioButton femaleRadio = findViewById(R.id.female);
        String genderString = maleRadio.isChecked() ? "M" : femaleRadio.isChecked() ? "F" : "";
        EditText drinksNameValue = findViewById(R.id.drinks_name_value);
        String drinksNameString = drinksNameValue.getText().toString();
        EditText drinksVolumeValue = findViewById(R.id.drinks_volume_value);
        String drinksVolumeString = drinksVolumeValue.getText().toString();
        EditText hoursSinceLastDrinkValue = findViewById(R.id.hours_since_last_drink_value);
        String hoursSinceLastDrinkString = hoursSinceLastDrinkValue.getText().toString();

        TextView outputTextView = findViewById(R.id.output_text_view);

        if (!unitsString.isEmpty() && !countryString.isEmpty() && !weightString.isEmpty() && !genderString.isEmpty()
                && !drinksNameString.isEmpty() && !drinksVolumeString.isEmpty() && !hoursSinceLastDrinkString.isEmpty()) {

            BigDecimal legalBacForDriving = LEGAL_BAC_FOR_DRIVING_PER_COUNTRY.containsKey(countryString) ?
                    LEGAL_BAC_FOR_DRIVING_PER_COUNTRY.get(countryString) : LEGAL_BAC_FOR_DRIVING_PER_COUNTRY.get("Default");

            BigDecimal totalAlcoholConsumed = new BigDecimal(drinksVolumeString);
            BigDecimal alcoholDistributionRatio = genderString.equals("M") ? BigDecimal.valueOf(0.73) : BigDecimal.valueOf(0.66);
            BigDecimal weight = new BigDecimal(weightString);
            BigDecimal hoursSinceLastDrink = new BigDecimal(hoursSinceLastDrinkString);

            if (unitsString.equalsIgnoreCase("M")) {
                weight = weight.divide(BigDecimal.valueOf(2.2046), RoundingMode.UP);

                totalAlcoholConsumed = totalAlcoholConsumed.multiply(BigDecimal.valueOf(0.0338140227));
            }

            BigDecimal finalTotalAlcoholConsumed = totalAlcoholConsumed;
            BigDecimal finalWeight = weight;


            calculateBloodAlcoholContent(finalTotalAlcoholConsumed, finalWeight, alcoholDistributionRatio, hoursSinceLastDrink, legalBacForDriving, outputTextView);
            drinksAdded.add(drinksNameString);

            outputTextView.setText(MessageFormat.format("{0}", outputTextView.getText().toString() + "\n\nDrinks consumed:" +
                    drinksAdded.toString()));
        } else {
            outputTextView.setText("");
        }
    }

    private void calculateBloodAlcoholContent(BigDecimal totalAlcoholConsumed, BigDecimal weight, BigDecimal alcoholDistributionRatio, BigDecimal hoursSinceLastDrink, BigDecimal legalBacForDriving, TextView outputTextView) {

        BigDecimal bac = (totalAlcoholConsumed.multiply(BigDecimal.valueOf(5.14))
                .divide(weight.multiply(alcoholDistributionRatio), RoundingMode.UP))
                .subtract((BigDecimal.valueOf(0.015).multiply(hoursSinceLastDrink)));

        totalBac = totalBac.add(bac);

        outputTextView.setText(MessageFormat.format("Yor BAC is {0}\nIt is {1}legal for you to drive.",
                totalBac, (totalBac.compareTo(legalBacForDriving) > 0 ? "not " : "")));
    }
}