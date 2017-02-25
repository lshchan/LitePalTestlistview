package com.example.litepaltest;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private String TAG="MainActivity";
    private boolean clear;
    private EditText name,author,pages,price,press;
    private List<Bookview> BookList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button createDatabase=(Button)findViewById(R.id.create_database);

        final RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final BookAdapter adapter=new BookAdapter(BookList);
        recyclerView.setAdapter(adapter);

        createDatabase.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LitePal.getDatabase();
            }
        });

        name=(EditText)findViewById(R.id.name);
         author=(EditText)findViewById(R.id.author);
         pages=(EditText)findViewById(R.id.pages);
         price=(EditText)findViewById(R.id.price);
         press=(EditText)findViewById(R.id.press);



        Button addData=(Button)findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book=new Book();
                book.setName(name.getText().toString());
                book.setAuthor(author.getText().toString());
                String str=pages.getText().toString();//获得输入的内容
                int result=1;//定义result存放转换后的值
                try{
                    result=Integer.parseInt(str);//调用类Integer的parseiInt()方法将字符串str转换为相应的整形数据
                    book.setPages(result);
                }catch(Exception ex){//捕获异常，并在textField中显示指定的信息
                    pages.setText("页码含有非数字字符...");
                }
                String str1=price.getText().toString();//获得输入的内容
                double result1=1;//定义result存放转换后的值
                try{
                    result1=Double.parseDouble(str1);//调用类Integer的parseiInt()方法将字符串str转换为相应的整形数据
                    book.setPrice(result1);
                }catch(Exception ex){//捕获异常，并在textField中显示指定的信息
                    price.setText("价格含有非数字字符...");
                }
                book.setPress(press.getText().toString());
                book.save();

            }
        });

        Button updateData=(Button)findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Book book=new Book();
                book.setName("The Lost Symbol");
                book.setAuthor("Dan Brown");
                book.setPages(510);
                book.setPrice(19.95);
                book.setPress("Unknow");
                book.save();
                book.setPrice(10.99);
                book.save();
            }
        });

        Button queryButton=(Button)findViewById(R.id.query_data);
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear=false;
                List<Book> books= DataSupport.findAll(Book.class);
                int i=1;
                recyclerView.removeAllViews();
                BookList.clear();   //clear recyclerview items display
                adapter.notifyDataSetChanged();

                for (Book book:books){
                    Log.d(TAG,"书的序号"+i);
                    Log.d(TAG,"book id is "+book.getId());
                    Log.d(TAG,"book name is "+book.getName());
                    Log.d(TAG,"book author is "+book.getAuthor());
                    Log.d(TAG,"book pages is "+book.getPages());
                    Log.d(TAG,"book price is "+book.getPrice());
                    Log.d(TAG,"book press is "+book.getPress());

                    Bookview bookview=new Bookview(book.getId(),book.getName(),book.getAuthor());
                    BookList.add(bookview);
                    adapter.notifyDataSetChanged();
                    i=i+1;
                    recyclerView.setAdapter(adapter);
                }



            }
        });

        Button deleteButton=(Button)findViewById(R.id.delete_data);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DataSupport.deleteAll(Book.class,"price<?","15");
                DataSupport.delete(Book.class,1);
                Log.d("dele","1");

            }
        });

        Button deleteall=(Button)findViewById(R.id.delete_alldata);
        deleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(Book.class);
                Book book=new Book();
                book.setId(0);
            }
        });

        Button clearview=(Button)findViewById(R.id.clearView);
        clearview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.removeAllViews();
                BookList.clear();   //clear recyclerview items display
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setmOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                //Toast.makeText(MainActivity.this,position+"click",Toast.LENGTH_SHORT).show();
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("确定删除此纪录吗？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(MainActivity.this,"删除",Toast.LENGTH_SHORT).show();
                       Bookview bookview=BookList.get(position);
                        //DataSupport.deleteAll(Book.class,"name=?",bookview.getName());
                        DataSupport.delete(Book.class,bookview.getId());

                        adapter.removeData(position);
                        //DataSupport.delete(Book.class,position);
                        Log.d("DelTest","position  "+position);
                        //DataSupport.deleteAll(Book.class);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });



    }



/*
  private void updataBooklist(){
        Bookview a=new Bookview("a","a");
        BookList.add(a);
        Bookview b=new Bookview("aa","aa");
        BookList.add(b);
    }
*/

}
