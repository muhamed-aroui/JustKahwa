package com.example.justkahwa;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 1;
    /**
     * This method is called when the order button is clicked.
     * @param price of the order
     * @param checkBox whether the costumer wants Whipped Cream or not
     * @param checkBox2 whether the costumer wants to add Chocolate or not
     */
    public void submitOrder(View view) {
       int price= quantity*5;
        CheckBox WhippedCreamCheckBox = findViewById(R.id.checkbox);
        boolean checkBox = WhippedCreamCheckBox.isChecked();
        CheckBox ChocolateCheckBox = findViewById(R.id.checkbox2);
        boolean checkBox2 = ChocolateCheckBox.isChecked();
        EditText yourName = findViewById(R.id.edit_text_name);
        String myNameIs = yourName.getText().toString();
        if (checkBox && checkBox2) {
            price = quantity * (5 + 1 + 2);
        } else if (checkBox) {
            price = quantity * (5 + 1);
        } else if (checkBox2) {
            price = quantity * (5 + 2);
        }
       display(quantity);
        String message = createOrderSummary(price, checkBox, checkBox2, myNameIs);
//        displayMessage(message);
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "future.coffeeshop@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, myNameIs));
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
//        startActivity(Intent.createChooser(emailIntent, "Send email..."));
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.no_email_app), Toast.LENGTH_SHORT).show();
        }
    }
    public void increase(View view){
        if (quantity < 100) {
            display(++quantity);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.do_not_exceed_100), Toast.LENGTH_SHORT);
            toast.show();
        }

    }
    public void decrease(View view){
        if (quantity > 1) {
            display(--quantity);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), getString(R.string.do_not_be_zero), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private String createOrderSummary(int price, boolean checkBox, boolean checkBox2, String Name) {
        String message = getString(R.string.order_summary_name, Name) + "\n" + getString(R.string.order_summary_whipped_cream, checkBox) + "\n" + getString(R.string.order_summary_chocolate, checkBox2) + "\n" + getString(R.string.order_summary_quantity, quantity) + "\n" + getString(R.string.order_summary_price, price) + "\n" + getString(R.string.thank_you);
        return message;
    }
}