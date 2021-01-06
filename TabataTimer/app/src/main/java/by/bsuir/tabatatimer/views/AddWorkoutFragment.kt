package by.bsuir.tabatatimer.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.bsuir.tabatatimer.R
import by.bsuir.tabatatimer.databinding.FragmentAddworkoutBinding
import by.bsuir.tabatatimer.utilities.HomeNavigation
import by.bsuir.tabatatimer.utilities.InjectorUtils
import by.bsuir.tabatatimer.viewmodels.EditWorkoutViewModel
import yuku.ambilwarna.AmbilWarnaDialog

class AddWorkoutFragment: Fragment(R.layout.fragment_addworkout) {
    private lateinit var binding: FragmentAddworkoutBinding
    private lateinit var viewModel: EditWorkoutViewModel

    private val args: AddWorkoutFragmentArgs by navArgs()
    private var defaultColor = R.color.purple_500

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val factory = InjectorUtils.provideEditWorkoutViewModelFactory()

        viewModel = ViewModelProvider(this, factory)
            .get(EditWorkoutViewModel::class.java)

        binding = FragmentAddworkoutBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.color.value = defaultColor

        args.sequence?.let {
            viewModel.fillFields(it)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val colorButton = binding.buttonCurrentColor
        colorButton.setOnClickListener{
            openColorPicker()
        }

        viewModel.navigation.observe(viewLifecycleOwner) {
            when(it) {
                HomeNavigation.EditToHome -> navigateToHome()
            }
        }

        viewModel.color.observe(viewLifecycleOwner) {
            defaultColor = it
            colorButton.setBackgroundColor(it)
        }

        viewModel.warningMessage.observe(viewLifecycleOwner) {
            makeToast(it)
        }

    }

    private fun openColorPicker() {
        val colorPicker = AmbilWarnaDialog(context, defaultColor,
            object: AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {

            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                viewModel.color.value = color
            }

        })

        colorPicker.show()
    }

    private fun navigateToHome() {
        val action = AddWorkoutFragmentDirections.actionAddWorkoutFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun makeToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}