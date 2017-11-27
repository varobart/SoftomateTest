package ru.softomate.softomatetest;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import ru.softomate.softomatetest.data.Text;
import ru.softomate.softomatetest.loaders.DBLoader;

public class LanguageDetectionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        ,LoaderManager.LoaderCallbacks<List<Text>>{



    private NewTextFragment mNewTextFragment;
    private HistoryFragment mHistoryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_detection);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(fragmentManager.findFragmentByTag(NewTextFragment.TAG) == null) {
            mNewTextFragment = new NewTextFragment();
        } else{
            mNewTextFragment = (NewTextFragment)fragmentManager.findFragmentByTag(NewTextFragment.TAG);
        }
        if(fragmentManager.findFragmentByTag(NewTextFragment.TAG) == null &&
                fragmentManager.findFragmentByTag(HistoryFragment.TAG) == null) {
            startFragment(NewTextFragment.TAG);
        }
        if(fragmentManager.findFragmentByTag(HistoryFragment.TAG) == null) {
            mHistoryFragment = new HistoryFragment();
            getSupportLoaderManager().initLoader(DBLoader.DB_LOADER_ID, null, this).forceLoad();
        } else{
            mHistoryFragment = (HistoryFragment)fragmentManager.findFragmentByTag(HistoryFragment.TAG);
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.new_text) {
            startFragment(NewTextFragment.TAG);
        } else if (id == R.id.history) {
            startFragment(HistoryFragment.TAG);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     *Returns fragments instance by tag
     */
    public Fragment getFragment(String fragmentTag){
        switch (fragmentTag){
            case NewTextFragment.TAG:
                return mNewTextFragment;
            case HistoryFragment.TAG:
                return mHistoryFragment;
            default:
                return  null;
        }
    }

    /**
     *Starts one of the fragments depending on TAG
     */
    protected void startFragment(String fragmentTAG){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.findFragmentByTag(fragmentTAG) == null){
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, getFragment(fragmentTAG), fragmentTAG)
                    .commit();
        }
    }

    /**
     *Creates loader for loading history from DB
     */
    @Override
    public Loader<List<Text>> onCreateLoader(int id, Bundle args) {
        Loader<List<Text>> loader = null;
        if(id == DBLoader.DB_LOADER_ID){
            loader = new DBLoader(this, args);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<Text>> loader, List<Text> data) {
        mHistoryFragment.setTexts(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Text>> loader) {

    }



}
