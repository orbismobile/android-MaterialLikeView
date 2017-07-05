package com.orbis.samplemateriallikeview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.orbis.materiallikeview.MaterialLikeView;
import com.orbis.materiallikeview.OnMaterialLikeAnimationListener;

public class MainActivity extends AppCompatActivity implements OnMaterialLikeAnimationListener {

    private MaterialLikeView materialLikeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        materialLikeView = (MaterialLikeView) findViewById(R.id.mlvCarlitos);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "ORBISMOBILE & CARLITOSDROID", Toast.LENGTH_SHORT).show();
            }
        });
        
        materialLikeView.setOnMaterialLikeClickListener(this);
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

    @Override
    public void onMaterialLikeAnimationFinished() {
        Toast.makeText(this, "LikeAnimationFinished " + materialLikeView.isFavoriteState(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMaterialNotLikeAnimationFinished() {
        Toast.makeText(this, "NotLikeAnimationFinished " + materialLikeView.isFavoriteState(), Toast.LENGTH_SHORT).show();
    }
}
