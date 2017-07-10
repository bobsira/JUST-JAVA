/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 0) {
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Get user's name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        Editable nameEditable = nameField.getText();
        String name = nameEditable.toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants whipped cream topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // Display the order summary on the screen
        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not we should include whipped cream topping in the price
     * @param addChocolate    is whether or not we should include whipped cream topping in the price
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // First calculate the price of one cup of coffee
        int basePrice = 5;

        // If the user wants whipped cream, add $1 per cup
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        // If the user wants chocolate, add $2 per cup
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        // Calculate the total order price by multiplying by the quantity
        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param name            on the order
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream,
                                      boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}


//package com.example.android.justjava;
//
///**
// * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
// * This is the package name our example uses:
// * <p>
// * package com.example.android.justjava;
// */
//
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.Editable;
//import android.view.View;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.text.NumberFormat;
//
///**
// * This app displays an order form to order coffee.
// */
//public class MainActivity extends AppCompatActivity {
//
//    int quantity = 1;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//
//    }
//
//
//    /**
//     * This method is called when the order button is clicked.
//     */
//    public void submitOrder(View view) {
//        // Get user's name
//        EditText nameField = (EditText) findViewById(R.id.name_field);
//        Editable nameEditable = nameField.getText();
//        String name = nameEditable.toString();
//
//        // Figure out if the user wants whipped cream topping
//        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
//        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
//
//        // Figure out if the user wants chocolate topping
//        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.whipped_Cream_CheckBox);
//        boolean hasChocolate = chocolateCheckBox.isChecked();
//
//        // Calculate the price
//        int price = calculatePrice(hasWhippedCream, hasChocolate);
//
//        // Display the order summary on the screen
//        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate);
//
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java App for " + name);
//        intent.putExtra(Intent.EXTRA_TEXT, message);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
//
//        //displayMessage(message);
//
//
//    }
//
//    /**
//     * Create summary of the order.
//     *
//     * @param name            on the order
//     * @param price           of the order
//     * @param addWhippedCream is whether or not to add whipped cream to the coffee
//     * @param addChocolate    is whether or not to add chocolate to the coffee
//     * @return text summary
//     */
//    private String createOrderSummary(String name, int price, boolean addWhippedCream,
//                                      boolean addChocolate) {
//        String priceMessage = "Name:" + name;
//        priceMessage += "\nAdd Whipped Cream? " + addWhippedCream;
//        priceMessage += "\nAdd Chocolate? " + addChocolate;
//        priceMessage += "\nQuantity: " + quantity;
//        priceMessage += "\nTotal: $" + price;
//        priceMessage += "\nThank you!";
//        return priceMessage;
//    }
//    /**
//     * Calculates the price of the order.
//     *
//     * @param addWhippedCream is whether or not we should include whipped cream topping in the price
//     * @param addChocolate    is whether or not we should include chocolate topping in the price
//     * @return total price
//     */
//    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
//        // First calculate the price of one cup of coffee
//        int basePrice = 5;
//
//        // If the user wants whipped cream, add $1 per cup
//        if (addWhippedCream) {
//            basePrice += 1;
//        }
//
//        // If the user wants chocolate, add $2 per cup
//        if (addChocolate) {
//            basePrice += 2;
//        }
//
//        // Calculate the total price by multiplying by the quantity
//        return quantity * basePrice;
//    }
//
//
//    /**
//     * This method displays the given quantity value on the screen.
//     */
//    private void displayQuantity(int numberOfCoffees) {
//        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
//        quantityTextView.setText("" + numberOfCoffees);
//    }
//
//
//    /**
//     * This method displays the given price on the screen.
//     */
////    private void displayPrice(int number) {
////        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
////        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
////    }
//
//    /**
//     * This method is called when the plus button is clicked.
//     */
//    public void increment(View view) {
//        if (quantity == 100) {
//            // Show an error message as a toast
//            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
//            // Exit this method early because there's nothing left to do
//            return;
//        }
//        quantity=quantity+1;
//        displayQuantity(quantity);
//    }
//
//    /**
//     * This method is called when the minus button is clicked.
//     */
//    public void decrement(View view) {
//        if (quantity == 0) {
//            // Show an error message as a toast
//            Context context = getApplicationContext();
//            CharSequence text = "You cannot have less than 1 coffee";
//            int duration = Toast.LENGTH_SHORT;
//            Toast toast = Toast.makeText(context, text, duration);
//            toast.show();
//            // Exit this method early because there's nothing left to do
//            return;
//        }
//        quantity=quantity-1;
//        displayQuantity(quantity);
//    }
//    /**
//     * This method displays the given text on the screen.
//     */
////    private void displayMessage(String message) {
////        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
////        orderSummaryTextView.setText(message);
////    }
//}
