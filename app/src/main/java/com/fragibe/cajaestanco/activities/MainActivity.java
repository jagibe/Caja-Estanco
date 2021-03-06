package com.fragibe.cajaestanco.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.fragibe.cajaestanco.R;
import com.fragibe.cajaestanco.fragments.CajaFragment;
import com.fragibe.cajaestanco.fragments.EditarArticulosFragment;
import com.fragibe.cajaestanco.fragments.ImportTarifarioFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabSearch);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Cargar Fragment Caja sin apilar en la pila atras
        replaceFragment(new CajaFragment(), false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().findFragmentByTag("EditarArticulosFragment") != null) {
            // I'm viewing EditarArticulosFragment
            getSupportFragmentManager().popBackStack("EditarArticulos_TAG",
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_caja) {
            replaceFragment(new CajaFragment(), true);
        } else if (id == R.id.nav_importar_tarifario) {
            replaceFragment(new ImportTarifarioFragment(), true);
        } else if (id == R.id.nav_editar_articulos) {
            replaceFragment(new EditarArticulosFragment(), true);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment newFragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.frame_container, newFragment, "stack");

        if (addToBackStack)
            ft.addToBackStack("stack_TAG");

        ft.commit();

        selectNavigationDrawer(newFragment);
    }

    public void selectNavigationDrawer(Fragment newFragment) {
        // Marcar elemento de navigationDrawer correspondiente
        if (newFragment instanceof CajaFragment) {
            deselectNavigationDrawer();
            navigationView.getMenu().getItem(0).setChecked(true);

        } else if (newFragment instanceof ImportTarifarioFragment) {
            deselectNavigationDrawer();
            navigationView.getMenu().getItem(1).getSubMenu().getItem(0).setChecked(true);

        } else if (newFragment instanceof EditarArticulosFragment) {
            deselectNavigationDrawer();
            navigationView.getMenu().getItem(1).getSubMenu().getItem(1).setChecked(true);
        }
    }

    public void deselectNavigationDrawer() {
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
            Menu submenu = menu.getItem(i).getSubMenu();
            if (submenu != null)
                for (int j = 0; j < submenu.size(); j++) {
                    submenu.getItem(j).setChecked(false);
                }
        }
    }

    public void closeKeyboard() {
        // Check if no view has focus:
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
