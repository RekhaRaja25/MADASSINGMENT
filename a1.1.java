package com.example.patientapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText name, age, date;
    RadioGroup genderGroup;
    Spinner illness;
    Button call, sms, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        date = findViewById(R.id.date);
        genderGroup = findViewById(R.id.genderGroup);
        illness = findViewById(R.id.illness);

        call = findViewById(R.id.call);
        sms = findViewById(R.id.sms);
        email = findViewById(R.id.email);

        // Spinner Data
        String[] illnessList = {"Fever", "Cold", "Headache", "Diabetes"};

        illness.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                illnessList));

        // Date Picker
        date.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dp = new DatePickerDialog(
                    MainActivity.this,
                    (view, y, m, d) -> date.setText(d + "/" + (m + 1) + "/" + y),
                    year, month, day);

            dp.show();
        });

        // Call Doctor with Confirmation Dialog
        call.setOnClickListener(v -> {

            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Confirm Call")
                    .setMessage("Do you want to call the doctor?")
                    .setPositiveButton("Call", (dialog, which) -> {

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:9876543210"));
                        startActivity(intent);

                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        // Send SMS
        sms.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:9876543210"));
            intent.putExtra("sms_body", "I need appointment.");
            startActivity(intent);
        });

        // Send Email
        email.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL,
                    new String[]{"doctor@mail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT,
                    "Appointment Request");
            intent.putExtra(Intent.EXTRA_TEXT,
                    "Patient details submitted.");

            startActivity(Intent.createChooser(intent, "Send Email"));
        });
    }
}
