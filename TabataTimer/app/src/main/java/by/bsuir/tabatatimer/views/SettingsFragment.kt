package by.bsuir.tabatatimer.views

import android.content.Context.WINDOW_SERVICE
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.*
import by.bsuir.tabatatimer.MainActivity
import by.bsuir.tabatatimer.R
import by.bsuir.tabatatimer.TabataTimerApplication
import by.bsuir.tabatatimer.utilities.InjectorUtils
import by.bsuir.tabatatimer.utilities.LocaleHelper
import by.bsuir.tabatatimer.viewmodels.SettingsViewModel
import java.util.*



class SettingsFragment : PreferenceFragmentCompat() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel: SettingsViewModel

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_preferences, rootKey)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = InjectorUtils.provideSettingsViewModelFactory()
        viewModel = ViewModelProvider(this, factory)
            .get(SettingsViewModel::class.java)

        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

    }




    override fun onResume() {
        val themePreference: SwitchPreferenceCompat? = context?.let { findPreference(it.getString(R.string.theme_key)) }

        themePreference?.let {
            it.setOnPreferenceChangeListener { _, newValue ->
                if (newValue as Boolean) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                true
            }
        }

        val langPreference: ListPreference? = context?.let { findPreference(it.getString(R.string.lang_key)) }

        langPreference?.let { lang ->
            lang.setOnPreferenceChangeListener { _, newValue ->
//                activity?.let {
//                    LocaleHelper.setLocale(it, newValue.toString())
//                    activity?.finish()
//                    startActivity(Intent(it, MainActivity::class.java))
//                }
                val configuration: Configuration? = TabataTimerApplication.applicationContext?.resources?.configuration
                configuration?.setLocale(Locale(newValue as String))
                resources.updateConfiguration(configuration, resources.displayMetrics)
                activity?.recreate()
                true

            }
        }

        val fontSizePreference: ListPreference? = context?.let { findPreference(it.getString(R.string.font_key)) }

        fontSizePreference?.let { font ->
            font.setOnPreferenceChangeListener { _, newValue ->
                var coefficient = 0F

                when (font.findIndexOfValue(newValue.toString())) {
                    0 -> coefficient = 1F
                    1 -> coefficient = 1.15F
                    2 -> coefficient = 1.3F
                }

                sharedPreferences.edit {
                    putFloat(getString(R.string.size_key), coefficient)
                }

                val configuration = resources.configuration
                configuration.fontScale = coefficient


                val metrics = DisplayMetrics()
                activity?.windowManager?.defaultDisplay?.getMetrics(metrics)
                metrics.scaledDensity = configuration.fontScale * metrics.density
                activity?.resources
                    ?.updateConfiguration(configuration, metrics)

                activity?.recreate()

                true
            }
        }

        val resetPreference: Preference? = context?.let { findPreference(it.getString(R.string.delete_key)) }

        resetPreference?.setOnPreferenceClickListener {
            context?.let { context_dialog ->
                AlertDialog.Builder(context_dialog)
                    .setTitle(context_dialog.getString(R.string.delete_title))
                    .setMessage(getString(R.string.delete_warning))
                    .setPositiveButton(
                        getString(R.string.dialog_positive)
                    ) { _, _ -> viewModel.clearData() }
                    .show()
            }
            true
        }

        super.onResume()
    }
}