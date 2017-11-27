package ru.softomate.softomatetest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import ru.softomate.softomatetest.data.Text;
import ru.softomate.softomatetest.loaders.JsonLoader;
import ru.softomate.softomatetest.utils.InternetManager;
import ru.softomate.softomatetest.utils.WarningDialogBuilder;
import ru.softomate.softomatetest.views.CustomTextInputLayout;

/**
 * Created by Вараздат on 25.11.2017.
 */

public class NewTextFragment extends Fragment implements View.OnClickListener
        ,LoaderManager.LoaderCallbacks<Text>, TextWatcher{

    public static final String TAG = "NewTextFragmentTag";


    private EditText mEditText;
    private FloatingActionButton mFAB;
    private ActionBar mActionBar;
    private boolean isInternetConnected;
    private Text mText;
    //variable for preventing double click on add button
    private boolean isTextAdded;
    //custom layout with text helper
    private CustomTextInputLayout mTextInputLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_text, container, false);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialization(view);
        fabSettings();
        toolbarSettings();
        editTextSettings();
    }


    /**
     *Initilizes fields of fragment
     */
    protected void initialization(View view){
        mFAB = view.findViewById(R.id.fab);
        isInternetConnected = InternetManager.isInternetConn(getContext());
        mActionBar = ((LanguageDetectionActivity)getActivity()).getSupportActionBar();
        mTextInputLayout = view.findViewById(R.id.text_input_layout);
        mEditText = view.findViewById(R.id.edit_text);
        isTextAdded = false;
    }


    protected void toolbarSettings(){
        String title = getString(R.string.new_text_fragment_title);
        mActionBar.setTitle(title);
    }


    protected void editTextSettings(){
        mEditText.addTextChangedListener(this);
    }


    protected void fabSettings(){
        mFAB.setOnClickListener(this);
    }



    /**
     *Every time users change text it restarts loader to check weather this new text can be
     * identified or cannot. And we say it to user via text helper under edittext.
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isTextAdded = false;
        Bundle bundle = new Bundle();
        bundle.putString(JsonLoader.TEXT, s.toString());
        getLoaderManager().restartLoader(JsonLoader.JSON_LOADER_ID, bundle, this).forceLoad();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    @Override
    public void onDestroyView(){
        super.onDestroyView();
        mEditText.removeTextChangedListener(this);
    }

    @Override
    public void onClick(View v) {
        if(!isInternetConnected){
            //if internet is not connected it shows alert dialog
            AlertDialog alertDialog = new WarningDialogBuilder(getContext())
                    .message(R.string.no_internet_connection).build();
            alertDialog.show();
        } else if(isTextAdded){
            //if users press add button two times in a row it shows alert dialog
            AlertDialog alertDialog = new WarningDialogBuilder(getContext())
                    .message(R.string.already_added).build();
            alertDialog.show();
        } else if(mText != null) {
            startService(mText);
            //adds new Text instance to adapter and notify changes
            ((HistoryFragment)((LanguageDetectionActivity)getActivity())
                    .getFragment(HistoryFragment.TAG))
                    .addText(mText);
            isTextAdded = true;
            Toast.makeText(getContext(), R.string.text_was_added, Toast.LENGTH_LONG).show();
        } else{
            //if language has not been identified it shows alert dialog
            AlertDialog alertDialog = new WarningDialogBuilder(getContext())
                    .message(R.string.unidentified_language).build();
            alertDialog.show();
        }
    }

    /**
     * @param text Text instance that is supposed to be inserted
     *
     *Starts service to insert Text instance to DB
     */
    public void startService(Text text){
        QueryService queryService = new QueryService();
        Intent intent = new Intent(getContext(), queryService.getClass());
        intent.putExtra(Text.TEXT, text);
        getActivity().startService(intent);
    }

    @Override
    public Loader<Text> onCreateLoader(int id, Bundle args) {
        Loader<Text> loader = null;
        if(id == JsonLoader.JSON_LOADER_ID){
            loader = new JsonLoader(getContext(), args);
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Text> loader, Text data) {
        if(data == null){
            //it shows error text
            mText = null;
            mTextInputLayout.setHelperTextEnabled(false);
            mTextInputLayout.setErrorEnabled(true);
            String continueToEnter = getString(R.string.continue_to_enter);
            mTextInputLayout.setError(continueToEnter);
        } else {
            //it shows helper text
            mText = data;
            mTextInputLayout.setHelperTextEnabled(true);
            mTextInputLayout.setErrorEnabled(false);
            String enough = getString(R.string.this_is_enough);
            mTextInputLayout.setHelperText(enough);
        }
    }

    @Override
    public void onLoaderReset(Loader<Text> loader) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }




}
