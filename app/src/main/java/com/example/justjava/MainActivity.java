package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity=2;
    int pricePerCup = 5;
    public void submitOrder(View view) {
       // int price = calculatePrice();
       // String summary = calculateOrderSummary(price);
       // displayMessage(summary);
        boolean hasWC = ((CheckBox) findViewById(R.id.whippedCream_checkbox)).isChecked();
        boolean hasChocolate = ((CheckBox) findViewById(R.id.chocolate_checkbox)).isChecked();
        String name = ((EditText) findViewById(R.id.name_field)).getText().toString();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava Order - " + name);
        intent.putExtra(Intent.EXTRA_TEXT, "" + calculateOrderSummary(calculatePrice()));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
     * Returns a summary of the order placed
     *
     */
    private String calculateOrderSummary(int orderTotal) {
        boolean hasWC = ((CheckBox) findViewById(R.id.whippedCream_checkbox)).isChecked();
        boolean hasChocolate = ((CheckBox) findViewById(R.id.chocolate_checkbox)).isChecked();
        String name = ((EditText) findViewById(R.id.name_field)).getText().toString();
        String orderSummary = "Name: " + name +
                            "\nAdd whipped Cream? " + hasWC +
                            "\nAdd chocolate? " + hasChocolate +
                            "\nQuantity: " + quantity +
                            "\nTotal: $" + orderTotal + "\nThank you!";
        return orderSummary;
    }

    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice() {
        boolean hasWC = ((CheckBox) findViewById(R.id.whippedCream_checkbox)).isChecked();
        boolean hasChocolate = ((CheckBox) findViewById(R.id.chocolate_checkbox)).isChecked();
        int total = quantity * pricePerCup;
        if(hasWC)
        {
            int priceWC = quantity*1;
            total += priceWC;
        }
        if(hasChocolate)
        {
            int priceChocolate = quantity*2;
            total += priceChocolate;
        }
        return total;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method increments the quantity variable by 1.
     */
    public void increment(View view) {
        if(quantity > 99) {
            quantity = 100;
            Toast.makeText(this, "Cannot order more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
        } else {
            quantity = quantity + 1;
            display(quantity);
            displayPrice(quantity * 5);
        }
    }

    /**
     * This method decrement the quantity variable by -1.
     */
    public void decrement(View view) {
        if(quantity != 0)
        {
            quantity = quantity - 1;
        }
        else
        {
            quantity = 0;
            Toast.makeText(this, "Cannot order less than zero cups of coffee", Toast.LENGTH_SHORT).show();
        }
        display(quantity);
        displayPrice(quantity*5);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}