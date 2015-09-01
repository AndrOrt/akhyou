package dulleh.akhyou.Settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import dulleh.akhyou.MainActivity;
import dulleh.akhyou.MainModel;
import dulleh.akhyou.R;

public class SettingsFragment extends Fragment {
    private SharedPreferences sharedPreferences;
    public static final String THEME_PREFERENCE = "theme_preference";
    private CharSequence[] themeTitles;
    private static final String lastToolbarTitleBundleKey = "LTT";
    private String lastToolbarTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        themeTitles = getResources().getStringArray(R.array.theme_entries);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, container, false);

        if (savedInstanceState != null) {
            lastToolbarTitle = savedInstanceState.getString(lastToolbarTitleBundleKey);
        } else {
            CharSequence title = ((MainActivity) getActivity()).getSupportActionBar().getTitle();
            if (title != null) {
                lastToolbarTitle = title.toString();
            }
        }

        RelativeLayout themeItem = (RelativeLayout) view.findViewById(R.id.theme_preference_item);
        TextView themeSummary = (TextView) themeItem.findViewById(R.id.preference_summary_text);
        themeSummary.setText(getSummary(THEME_PREFERENCE));
        themeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .title(R.string.theme_dialog_title)
                        .items(themeTitles)
                        .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog materialDialog, View view, int i, CharSequence charSequence) {

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putInt(THEME_PREFERENCE, i + 1);
                                editor.apply();

                                getActivity().recreate();
                                return false;
                            }
                        })
                        .show();
            }

        });

        boolean shouldAutoUpdateVal = sharedPreferences.getBoolean(MainModel.AUTO_UPDATE_PREF, true);
        RelativeLayout autoUpdateItem = (RelativeLayout) view.findViewById(R.id.auto_update_preference_item);
        CheckBox autoUpdateCheckBox = (CheckBox) autoUpdateItem.findViewById(R.id.preference_check_box);
        TextView autoUpdateSummary = (TextView) autoUpdateItem.findViewById(R.id.preference_summary_text);
        autoUpdateSummary.setText(autoUpdateSummaryUpdate(shouldAutoUpdateVal));
        autoUpdateCheckBox.setChecked(shouldAutoUpdateVal);
        autoUpdateCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(MainModel.AUTO_UPDATE_PREF,  b);
                editor.apply();

                autoUpdateSummary.setText(autoUpdateSummaryUpdate(b));
            }
        });

        RelativeLayout licencesItem = (RelativeLayout) view.findViewById(R.id.licences_preference_item);
        licencesItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title(getString(R.string.licences_preference_summary))
                        .customView(R.layout.licences_text_view, true)
                        .show();
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(lastToolbarTitleBundleKey, lastToolbarTitle);
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbarTitle(getString(R.string.settings_item));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setToolbarTitle(lastToolbarTitle);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem searchItem = menu.findItem(R.id.search_item);

        if (searchItem != null) {
            menu.setGroupVisible(searchItem.getGroupId(), false);
        }

    }

    public void setToolbarTitle (String title) {
        //((MainActivity) getActivity()).setToolbarTitle(title);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    private String getSummary (String key) {
        if (key.equals(THEME_PREFERENCE)) {
            int themePref = sharedPreferences.getInt(THEME_PREFERENCE, 0);
            switch (themePref) {
                case 1:
                    return getActivity().getApplicationContext().getString(R.string.pink_theme);

                case 2:
                    return getActivity().getApplicationContext().getString(R.string.purple_theme);

                case 3:
                    return getActivity().getApplicationContext().getString(R.string.deep_purple_theme);

                case 4:
                    return getActivity().getApplicationContext().getString(R.string.indigo_theme);

                case 5:
                    return getActivity().getApplicationContext().getString(R.string.light_blue_theme);

                case 6:
                    return getActivity().getApplicationContext().getString(R.string.cyan_theme);

                case 7:
                    return getActivity().getApplicationContext().getString(R.string.teal_theme);

                case 8:
                    return getActivity().getApplicationContext().getString(R.string.green_theme);

                case 9:
                    return getActivity().getApplicationContext().getString(R.string.light_green_theme);

                case 10:
                    return getActivity().getApplicationContext().getString(R.string.lime_theme);

                case 11:
                    return getActivity().getApplicationContext().getString(R.string.yellow_theme);

                case 12:
                    return getActivity().getApplicationContext().getString(R.string.orange_theme);

                case 13:
                    return getActivity().getApplicationContext().getString(R.string.deep_orange_theme);

                case 14:
                    return getActivity().getApplicationContext().getString(R.string.brown_theme);

                case 15:
                    return getActivity().getApplicationContext().getString(R.string.grey_theme);

                case 16:
                    return getActivity().getApplicationContext().getString(R.string.blue_grey_theme);

                default:
                    return getActivity().getApplicationContext().getString(R.string.grey_theme);

            }
        }
        return null;
    }

    private String autoUpdateSummaryUpdate (boolean bool) {
        if (bool) {
            return getString(R.string.auto_update_prefernce_true_summary);
        } else {
            return getString(R.string.auto_update_preference_false_summary);
        }
    }

}
