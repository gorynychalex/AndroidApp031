package ru.dvfu.mrcpk.popovich.androidapp031;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final int REQUEST_CODE_NAME = 1;
    final int REQUEST_CODE_ADDNAME = 2;

    List names = new ArrayList<String>();
    ListView listView;
    Button buttonEdit;
    Button buttonAdd;
    Button buttonDel;
    ArrayAdapter<String> adapterNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        names.add("Иванов Иван");
        names.add("Петров Петр");
        names.add("Алексеев Алексей");

        //Определение списка из макета activity_main.xml
        listView = (ListView) findViewById(R.id.listView);

        // Установка режима выбора пунктов списка (по одному)
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //Создание адаптера из массива names для назначения их списку (напрямую нельзя!)
        adapterNames = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice, names);
        listView.setAdapter(adapterNames);

        //Объекты кнопок редактирования, добавления, удаления
        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        buttonEdit.setOnClickListener(this);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);
        buttonDel = (Button) findViewById(R.id.buttonDel);
        buttonDel.setOnClickListener(this);

        //Обработка выбора пункта списка (НЕОБЯЗАТЕЛЬНАЯ ЧАСТЬ)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,names.get(listView.getCheckedItemPosition()) + ", pos = " + position + ", id = " + id,Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,names.get(listView.getCheckedItemPosition()) + ", pos = " + position + ", id = " + id,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.buttonEdit:

                //Создание объекта "намерение" для порождения нового активити - EditActivity
                intent = new Intent(this, EditActivity.class);

                // Работа с элементом списка. Если не выбран ни один пунк - выдать сообщение (Toast)
                if(listView.getCheckedItemPosition() != -1) {
                    intent.putExtra("desc","Редактировать данные:");
                    intent.putExtra("name", String.valueOf(names.get(listView.getCheckedItemPosition())));
                    Toast.makeText(this, String.valueOf(names.get(listView.getCheckedItemPosition())), Toast.LENGTH_LONG).show();
//                startActivity(intent);
                    startActivityForResult(intent, REQUEST_CODE_NAME);
                } else {
                    Toast.makeText(this, "Выберите пунк списка", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonAdd:
                intent = new Intent(this, EditActivity.class);
                intent.putExtra("name",false);
                intent.putExtra("desc","Добавить персону:");
                startActivityForResult(intent, REQUEST_CODE_ADDNAME);
                break;
            case R.id.buttonDel:
                names.remove(listView.getCheckedItemPosition());
                listView.setAdapter(adapterNames);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(data == null) return;
        if(resultCode == RESULT_OK) {
            switch (requestCode){
                case REQUEST_CODE_NAME:
                    String nameResult = data.getExtras().getString("name");
                    names.set(listView.getCheckedItemPosition(),nameResult);
                    Toast.makeText(MainActivity.this, nameResult, Toast.LENGTH_LONG).show();
                    listView.setAdapter(adapterNames);
    //                textViewName.setText(nameResult);
                    break;
                case REQUEST_CODE_ADDNAME:
                    String nameAddResult = data.getExtras().getString("name");
                    names.add(nameAddResult);
                    Toast.makeText(MainActivity.this, nameAddResult, Toast.LENGTH_LONG).show();
                    listView.setAdapter(adapterNames);
                    break;
            }
        }

    }
}
