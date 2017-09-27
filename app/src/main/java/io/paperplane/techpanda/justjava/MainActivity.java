package io.paperplane.techpanda.justjava;

/**
 * This is a project from the Udacity Android Basics: User Input course.
 * Justin Michael September 8th, 2017
 * I'm taking this course to expand my knowledge in Android Development
 */

import android.content.Intent;
import android.icu.text.NumberFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form for coffee.
 * This apps is growing my skills in XML as well as Java in Android development.
 */

public class MainActivity extends AppCompatActivity {

    /**
     * Global variables that all of the methods in the class can access
     */
    int quantity = 1;
    int costOfWhippedCream = 1;
    int costOfChocolate = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked on
     */
    public void submitOrder(View view) {
        // This checks for the whipped cream checkbox
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        // checks for the chocolate checkbox
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        // Checks the EditText field for the entered name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        // calls the method that calculates the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = (createOrderSummary(name, price, hasWhippedCream, hasChocolate));

        /**
         *
         */
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // Only Email apps should handle this
        intent.putExtra(intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    /**
     * @param addWhippedCream did the user add whipped cream?
     * @param addChocolate    did the user add chocolate?
     * @return total price to submitOrder method
     */

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        if (addWhippedCream) {
            basePrice = basePrice + costOfWhippedCream;
        }
        if (addChocolate) {
            basePrice = basePrice + costOfChocolate;
        }
        return quantity * basePrice;
    }

    /**
     * This method creates an order summary that is called by the submitOrder method
     *
     * @param name            The name of the customer from the EditText view
     * @param price           is the price of the order
     * @param addWhippedCream checks to see if the user selected whipped cream in the checkbox
     * @param addChocolate    checks to see if the user selected chocolate in the checkbox
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage =  getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, price);
        priceMessage += "\n" + getString(R.string.thank_you) ;
        return priceMessage;
    }

    /**
     * This method is called when the increment button is clicked
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Shows a toast error message
            Toast.makeText(this, getString(R.string.too_many_coffees), Toast.LENGTH_SHORT).show();
            // Exits the method early since you can't go over 100 cups
            return;
        } else {
            quantity = quantity + 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method is called when the decrement button is clicked
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show a toast error message
            Toast.makeText(this, getString(R.string.too_few_coffees), Toast.LENGTH_SHORT).show();
            // Exits the method early since you can't have less than 1 cup of coffee
            return;
        } else {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
    }

    /**
     * This method displays the given quantity on the screen
     */
    public void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}
