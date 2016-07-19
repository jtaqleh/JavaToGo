package com.example.android.javatogo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    //Global variables for number of coffees
    int quantity = 0;
    //int price = 5;
    //boolean tickedBox;
    //boolean tickedBox2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        if(quantity == 0){
            //Toast message to tell the user his/her order is empty
            Toast.makeText(this, "Please enter a quantity", Toast.LENGTH_SHORT).show();

        }else{
            //User enter name
            EditText name = (EditText) findViewById(R.id.name_field);
            String orderName = name.getText().toString();

            //User check for whipped cream
            CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.checkbox_whipped_cream);
            boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

            //User check for chocolate
            CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.checkbox_chocolate);
            boolean hasChocolate = chocolateCheckBox.isChecked();

            //Store the value of price from the calculatePrice method
            int price = calculatePrice(hasWhippedCream, hasChocolate);

            String orderMessage = createOrderSummary(price, orderName, hasWhippedCream, hasChocolate);

            //Sending intent to an email app
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:"));
            //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
            intent.putExtra(Intent.EXTRA_SUBJECT, "JavaToGo Order for " + orderName);
            intent.putExtra(Intent.EXTRA_TEXT, orderMessage);
            if(intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

            displayMessage(orderMessage);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int whatever) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + whatever);
    }

    //This method increase the quantity
    public void increment(View view){
        if((quantity >= 0) && (quantity <= 100)) {
            if (quantity == 100) {
                //Show an error message as a toast
                Toast.makeText(this, "You cannot have more than 100 coffees.", Toast.LENGTH_SHORT).show();
                return;
            }

            quantity += 1;
            displayQuantity(quantity);
        }
    }

    //This method decrease the quantity
    public void decrement(View view){
        if(quantity > 0){
            if(quantity == 1) {
                //Show an error message as a toast
                Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
                return;
            }
            quantity -= 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method is called when the clear button is clicked.
     */
    public void clearOrder(View view) {
        quantity = 0;
        displayQuantity(quantity);

        String priceMessage = "Total: RM" + 0;
        priceMessage += "\nYour Order Has Been Cleared";
        displayMessage(priceMessage);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method calculate the price of your order.
     */
    private int calculatePrice(boolean whippedCream, boolean chocolate) {
        //Price of 1 cup of coffee
        int basePrice = 5;

        //Add RM 1 if the user wants whipped cream
        if(whippedCream)
            basePrice += 1;

        //Add RM 2 if the user wants chocolate
        if(chocolate)
            basePrice += 2;

        //Calculate the total order price
        return quantity * basePrice;
    }

    /**
     * This method will create the order summary.
     */
    private String createOrderSummary(int price, String nameOrder, boolean addWhippedCream, boolean addChocolate) {

        String orderSummary;
        String stringTickedBox1;
        String stringTickedBox2;

        if(addWhippedCream == true)
            stringTickedBox1 = "Yes";
        else
            stringTickedBox1 = "No";

        if(addChocolate == true)
            stringTickedBox2 = "Yes";
        else
            stringTickedBox2 = "No";

        orderSummary = "Name: " + nameOrder;
        orderSummary += "\nWhipped Cream? " + stringTickedBox1;
        orderSummary += "\nChocolate? " + stringTickedBox2;
        orderSummary += "\nQuantity: " + quantity;
        orderSummary += "\nTotal: RM" + calculatePrice(addWhippedCream, addChocolate);
        orderSummary += "\nThank You!";

        //displayMessage(orderSummary);
        return orderSummary;

    }
}
