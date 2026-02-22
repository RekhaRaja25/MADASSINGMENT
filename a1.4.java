package com.example.farmerhelper;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner crop, soil, season;
    TextView tips;
    Button call, sms, email1, email2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crop = findViewById(R.id.crop);
        soil = findViewById(R.id.soil);
        season = findViewById(R.id.season);
        tips = findViewById(R.id.tips);

        call = findViewById(R.id.call);
        sms = findViewById(R.id.sms);
        email1 = findViewById(R.id.email1);
        email2 = findViewById(R.id.email2);

        // Spinner Data
        String[] crops = {"Rice", "Wheat", "Maize"};
        String[] soils = {"Loamy", "Clay", "Sandy"};
        String[] seasons = {"Monsoon", "Winter", "Summer"};

        crop.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, crops));

        soil.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, soils));

        season.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, seasons));

        // Change Tips based on Crop
        crop.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent,
                                       android.view.View view,
                                       int position,
                                       long id) {

                String selected = crop.getSelectedItem().toString();

                if (selected.equals("Rice")) {
                    tips.setText(
                            "• Use high-yielding rice varieties.\n" +
                                    "• Maintain proper water level.\n" +
                                    "• Apply nitrogen fertilizer properly.");
                } else if (selected.equals("Wheat")) {
                    tips.setText(
                            "• Choose disease-resistant seeds.\n" +
                                    "• Irrigate during critical stages.\n" +
                                    "• Use balanced fertilizers.");
                } else {
                    tips.setText(
                            "• Ensure proper spacing.\n" +
                                    "• Use organic manure.\n" +
                                    "• Monitor pests regularly.");
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {}
        });

        // Call Officer Button with Confirmation Dialog
        call.setOnClickListener(v -> {

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Confirm Call")
                    .setMessage("Do you want to call the agricultural officer?")
                    .setPositiveButton("Call", (dialog, which) -> {

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:1234567890"));
                        startActivity(intent);

                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Send SMS
        sms.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:1234567890"));
            intent.putExtra("sms_body", "Need farming guidance.");
            startActivity(intent);
        });

        // Send Email Button 1
        email1.setOnClickListener(v -> sendEmail());

        // Send Email Button 2
        email2.setOnClickListener(v -> sendEmail());
    }

    private void sendEmail() {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL,
                new String[]{"agri_officer@mail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,
                "Farming Help");
        intent.putExtra(Intent.EXTRA_TEXT,
                "I need guidance regarding crops.");

        startActivity(Intent.createChooser(intent, "Send Email"));
    }
}
