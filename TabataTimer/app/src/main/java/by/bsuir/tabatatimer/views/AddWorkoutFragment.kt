package by.bsuir.tabatatimer.views

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import by.bsuir.tabatatimer.R
import by.bsuir.tabatatimer.databinding.FragmentAddworkoutBinding
import by.bsuir.tabatatimer.utilities.InjectorUtils
import by.bsuir.tabatatimer.viewmodels.EditWorkoutViewModel
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.jaredrummler.android.colorpicker.ColorShape

class AddWorkoutFragment: Fragment(R.layout.fragment_addworkout) {
    private lateinit var viewModel: EditWorkoutViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val factory = InjectorUtils.provideEditWorkoutViewModelFactory()

        viewModel = ViewModelProvider(this, factory)
            .get(EditWorkoutViewModel::class.java)

        val binding = FragmentAddworkoutBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val button = view.findViewById<Button>(R.id.button_current_color)
    }


}