package ru.softomate.softomatetest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.softomate.softomatetest.adapters.TextAdapter;
import ru.softomate.softomatetest.data.Text;
import ru.softomate.softomatetest.loaders.DBLoader;

/**
 * Created by Вараздат on 25.11.2017.
 */

public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Text>> {

    public static final String TAG = "HistoryFragmentTag";


    private ActionBar mActionBar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DividerItemDecoration mDividerItemDecoration;
    private List<Text> mTexts = new ArrayList<>();
    private TextAdapter mTextAdapter = new TextAdapter(mTexts);


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_history, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization(view);
        toolbarSettings();
        recyclerViewSettings();
    }


    //adds new text to adapter and notify changes
    public void addText(Text text){
        mTextAdapter.addText(text);
        mTextAdapter.notifyDataSetChanged();
    }


    //adds new list of texts to adapter and notify changes
    public void setTexts(List<Text> texts){
        mTextAdapter.setTexts(texts);
        mTextAdapter.notifyDataSetChanged();
    }


    /**
     *Initilizes fields of fragment
     */
    protected void initialization(View view){
        mActionBar = ((LanguageDetectionActivity)getActivity()).getSupportActionBar();
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView = view.findViewById(R.id.rv);
    }


    protected void toolbarSettings(){
        String title = getString(R.string.history_fragment_title);
        mActionBar.setTitle(title);
    }


    protected void recyclerViewSettings(){
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mTextAdapter);
        mDividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                ((LinearLayoutManager)mLayoutManager).getOrientation());
        mRecyclerView.addItemDecoration(mDividerItemDecoration);
    }


    //creates new loader for loading history from DB
    @Override
    public Loader<List<Text>> onCreateLoader(int id, Bundle args) {
        Loader<List<Text>> loader = null;
        if(id == DBLoader.DB_LOADER_ID){
            loader = new DBLoader(getContext(), args);
        }
        return loader;
    }


    //adds new list of texts to adapter after loading from DB and notify changes
    @Override
    public void onLoadFinished(Loader<List<Text>> loader, List<Text> data) {
        mTextAdapter.setTexts(data);
        mTextAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<Text>> loader) {

    }


    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }


}
