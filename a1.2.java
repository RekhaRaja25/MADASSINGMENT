package com.example.dailyexpenseapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText amount, desc;
    Spinner category;
    RadioGroup paymentGroup;
    Button add, call, sms, email;
    ListView listView;

    ArrayList<String> list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.amount);
        desc = findViewById(R.id.desc);
        category = findViewById(R.id.category);
        paymentGroup = findViewById(R.id.paymentGroup);
        add = findViewById(R.id.add);
        call = findViewById(R.id.call);
        sms = findViewById(R.id.sms);
        email = findViewById(R.id.email);
        listView = findViewById(R.id.listView);

        // Spinner Data
        String[] categories = {"Office", "Food", "Travel", "Shopping"};
        ArrayAdapter<String> spinnerAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        categories);

        category.setAdapter(spinnerAdapter);

        // ListView
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, list);

        listView.setAdapter(adapter);

        // Add Expense
        add.setOnClickListener(v -> {

            String amt = amount.getText().toString();
            String description = desc.getText().toString();

            if (amt.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Enter details", Toast.LENGTH_SHORT).show();
                return;
            }

            String item = description + " - $" + amt;
            list.add(item);
            adapter.notifyDataSetChanged();

            amount.setText("");
            desc.setText("");
        });

        // Call Button
        call.setOnClickListener(v -> showCallDialog());

        // SMS
        sms.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("sms:1234567890"));
            intent.putExtra("sms_body", "Expense details...");
            startActivity(intent);
        });

        // Email
        email.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL,
                    new String[]{"accountant@mail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT,
                    "Expense Report");
            intent.putExtra(Intent.EXTRA_TEXT,
                    "Here is my expense list.");

            startActivity(Intent.createChooser(intent, "Send Email"));
        });
    }

    private void showCallDialog() {

        new AlertDialog.Builder(this)
                .setTitle("Confirm Call")
                .setMessage("Do you want to call the accountant?")
                .setPositiveButton("Call",
                        (dialog, which) -> {

                            Intent intent =
                                    new Intent(Intent.ACTION_DIAL);
                            intent.setData(
                                    Uri.parse("tel:1234567890"));
                            startActivity(intent);
                        })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
