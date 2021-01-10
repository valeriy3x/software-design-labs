package by.bsuir.firebasegame.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.bsuir.firebasegame.R
import by.bsuir.firebasegame.databinding.FragmentLoginBinding
import by.bsuir.firebasegame.databinding.FragmentRegisterBinding
import by.bsuir.firebasegame.utilities.AuthNavigation
import by.bsuir.firebasegame.utilities.InjectorUtils
import by.bsuir.firebasegame.viewmodels.AuthViewModel

class RegisterFragment: Fragment(R.layout.fragment_register) {
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val factory = InjectorUtils.provideAuthViewModelFactory()
        viewModel = ViewModelProvider(this, factory)
            .get(AuthViewModel::class.java)

        val binding = FragmentRegisterBinding.inflate(inflater, container, false)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigation.observe(viewLifecycleOwner) {
            when(it) {
                AuthNavigation.RegisterToLogin -> { redirectToLogin() }
                AuthNavigation.RegisterToEdit -> { redirectToEdit() }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun redirectToLogin() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    private fun redirectToEdit() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToEditFragment()
        findNavController().navigate(action)
    }
    
}