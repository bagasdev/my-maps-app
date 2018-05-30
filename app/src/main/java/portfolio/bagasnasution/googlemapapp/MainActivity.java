package portfolio.bagasnasution.googlemapapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by bagas on 5/28/2018.
 */

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private String[] titles = new String[]{
            "Basic Map",
            "Get Current Location Address"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list_content);
        listView.setAdapter(
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles)
        );

        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = null;

        switch (i) {
            case 0:
                intent = new Intent(this, MapsActivity.class);
                break;
            case 1:
                intent = new Intent(this, LocationActivity.class);
                break;
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}
