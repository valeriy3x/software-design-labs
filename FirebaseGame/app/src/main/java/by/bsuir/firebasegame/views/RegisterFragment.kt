package by.bsuir.firebasegame.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.bsuir.firebasegame.R
import by.bsuir.firebasegame.databinding.FragmentLoginBinding
import by.bsuir.firebasegame.databinding.FragmentRegisterBinding
import by.bsuir.firebasegame.utilities.AuthNavigation
import by.bsuir.firebasegame.viewmodels.AuthViewModel

class RegisterFragment: Fragment(R.layout.fragment_register) {
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            }
        }
    }

    private fun redirectToLogin() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }
}