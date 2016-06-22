package ru.dvfu.mrcpk.popovich.androidapp031;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView;
    EditText editText;
    Button buttonEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editText = (EditText) findViewById(R.id.editText);
        buttonEdit = (Button) findViewById(R.id.buttonEdit);

        textView = (TextView) findViewById(R.id.textView);
        Intent intent = getIntent();
        editText.setText(intent.getExtras().getString("name"));
        textView.setText(intent.getExtras().getString("desc"));
        buttonEdit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent1 = new Intent();
        intent1.putExtra("name", editText.getText().toString());
        setResult(RESULT_OK, intent1);
        finish();
    }
}