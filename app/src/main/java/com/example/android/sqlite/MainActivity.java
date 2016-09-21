package com.example.android.sqlite;
 import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
 import android.util.Log;
 import android.view.View;
        import android.widget.EditText;
        import android.widget.TextView;

        import com.example.android.sqlite.R;

// This program exists solely to generate a database and play with it.

public class MainActivity extends AppCompatActivity {
    EditText userEntryText;
    EditText userEntryText2;
    TextView resultsText;
    myDBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Grab references to UI elements
        userEntryText = (EditText) findViewById(R.id.userEntryText);
        userEntryText2 = (EditText) findViewById(R.id.userEntryText2);
        resultsText = (TextView) findViewById(R.id.resultsText);

        // dbManager reference
        dbManager = new myDBManager(this, null, null, 1);

        // Let's see what's in the database.
        printDatabase();
    }

    public void printDatabase() {
        // Query the database using the dbManager's dbToString() method.
        String dbString = dbManager.dbToString();
        // Drop the results onto the UI
        resultsText.setText(dbString);
        // Set the UI up for fresh input
        userEntryText.setText("");
    }

    public void addButtonClick(View view) {
        // Create a new product based on what's in the UI
        Products product = new Products(userEntryText.getText().toString());
        product.set_color(userEntryText2.getText().toString());
        // Send that product object to the dbManager so the item can be added.
        dbManager.addItem(product);
        // See what's in the database
        printDatabase();

    }

    public void delButtonClick(View view) {
        // Grab the UI contents and dump it into a string
        String inputText = userEntryText.getText().toString();
        // Send that string to the dbManager's delItem() method.
        dbManager.delItem(inputText);
        // See what's in the database
        printDatabase();
    }
}
