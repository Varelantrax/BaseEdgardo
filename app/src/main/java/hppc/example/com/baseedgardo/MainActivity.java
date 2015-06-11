package hppc.example.com.baseedgardo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
// aqui estoy declarando los editText que tengo en el xml
    EditText e_idpro, e_nombrepro, e_descri, e_precio, e_empresa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// aqui es donde se enlasa el archivo xml con el archivo java
        e_idpro=(EditText) findViewById(R.id.edittext_idpro);
        e_nombrepro=(EditText) findViewById(R.id.edittext_nombrepro);
        e_descri=(EditText) findViewById(R.id.edittext_desc);
        e_precio=(EditText) findViewById(R.id.edittext_precio);
        e_empresa=(EditText) findViewById(R.id.edittext_empresa);
    }

// Este es el metodo para dar de alta en la tienda
    public void alta (View v) {
        //Aqui es donde se hace la conexion con la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tienda", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
// aqui se declaran variables para guardar el contenido de los EditText
        String idpro = e_idpro.getText().toString();
        String nombrepro = e_nombrepro.getText().toString();
        String descri = e_descri.getText().toString();
        String precio = e_precio.getText().toString();
        String empresa = e_empresa.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("id_producto", idpro);
        registro.put("nombre", nombrepro);
        registro.put("descripcion", descri);
        registro.put("precio", precio);
        registro.put("empresa", empresa);

        bd.insert("tienda", null, registro);
        bd.close();

        e_idpro.setText("");
        e_nombrepro.setText("");
        e_descri.setText("");
        e_precio.setText("");
        e_empresa.setText("");

        Toast.makeText(this, "Se ha agregado un nuevo producto", Toast.LENGTH_SHORT).show();

    }

    public void consulta(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tienda", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String idprod = e_idpro.getText().toString();
        Cursor fila = bd.rawQuery("select nombre, descripcion, precio, empresa from tienda where id_producto=" + idprod, null);
        if (fila.moveToFirst()) {
            e_nombrepro.setText(fila.getString(0));
            e_descri.setText(fila.getString(1));
            e_precio.setText(fila.getString(2));
            e_empresa.setText(fila.getString(3));
        } else {
            Toast.makeText(this,"No existe el producto",Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

    public void baja(View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tienda", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String idprod = e_idpro.getText().toString();
        int cant = bd.delete("tienda", "id_producto=" + idprod, null);
        bd.close();
        e_idpro.setText("");
        e_nombrepro.setText("");
        e_descri.setText("");
        e_precio.setText("");
        e_empresa.setText("");

        if (cant == 1) {
            Toast.makeText(this, "Se borró el producto indicado",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe el producto indicado",Toast.LENGTH_SHORT).show();
        }
    }

    public void modificacion (View v) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "tienda", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();

        String idpro = e_idpro.getText().toString();
        String nombrepro = e_nombrepro.getText().toString();
        String descri = e_descri.getText().toString();
        String precio = e_precio.getText().toString();
        String empresa = e_empresa.getText().toString();

        ContentValues registro = new ContentValues();
        registro.put("id_producto", idpro);
        registro.put("nombre", nombrepro);
        registro.put("descripcion", descri);
        registro.put("precio", precio);
        registro.put("empresa", empresa);

        int cant = bd.update("tienda", registro, "id_producto=" + idpro, null);
        bd.close();

        if (cant == 1) {
            Toast.makeText(this, "Se modificaron los datos del producto",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No existe el producto indicado",Toast.LENGTH_SHORT).show();
        }

    }

    public void limpia (View v){
        e_idpro.setText("");
        e_nombrepro.setText("");
        e_descri.setText("");
        e_precio.setText("");
        e_empresa.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
