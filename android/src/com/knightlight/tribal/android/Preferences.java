package com.knightlight.tribal.android;

import com.knightlight.tribal.Config;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Preferences extends PreferenceActivity implements OnPreferenceChangeListener {
	private CheckBoxPreference checkBoxTest;
	private ListPreference listTest;

	@Override
	protected void onPostCreate(Bundle bundle) {
		super.onPostCreate(bundle);

		getPreferenceManager().setSharedPreferencesName("preferences"); // Don't change name here
		getPreferenceManager().setSharedPreferencesMode(0);

		addPreferencesFromResource(R.xml.preferences);

		Config.load();

		checkBoxTest = (CheckBoxPreference) findPreference("checkBoxTest");
		checkBoxTest.setChecked(Config.checkBoxTest);
		checkBoxTest.setOnPreferenceChangeListener(this);

		listTest = (ListPreference) findPreference("listTest");
		listTest.setValueIndex(Integer.valueOf(Config.listTest));
		listTest.setSummary(listTest.getEntry());
		listTest.setOnPreferenceChangeListener(this);

		Preference more = (Preference) findPreference("more");
		more.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				openUrl(R.string.url_more);

				return true;
			}
		});
	}

	private void openUrl(int i) {
		try {
			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse((String) getText(i))));
		} catch (Exception e) {
			toast();
		}
	}

	private void toast() {
		Toast.makeText(this, R.string.settings_toast_unavailable, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		if (preference == checkBoxTest) {
			Config.checkBoxTest = (Boolean) newValue;
			checkBoxTest.setChecked(Config.checkBoxTest);
			Config.save();
			return true;
		} else if (preference == listTest) {
			Config.listTest = newValue.toString();
			listTest.setValueIndex(Integer.valueOf(Config.listTest));
			listTest.setSummary(listTest.getEntry());
			Config.save();
			return true;
		}

		return false;
	}
}
