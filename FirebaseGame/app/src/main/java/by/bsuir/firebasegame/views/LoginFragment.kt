package by.bsuir.firebasegame.views

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
import by.bsuir.firebasegame.utilities.AuthNavigation
import by.bsuir.firebasegame.utilities.InjectorUtils
import by.bsuir.firebasegame.viewmodels.AuthViewModel

class LoginFragment: Fragment(R.layout.fragment_login) {
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var factory = InjectorUtils.provideAuthViewModelFactory()
        viewModel = ViewModelProvider(this, factory)
            .get(AuthViewModel::class.java)

        val binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigation.observe(viewLifecycleOwner) {
            when(it) {
                AuthNavigation.LoginToRegister -> { redirectToRegister() }
                AuthNavigation.LoginToAccount -> { redirectToAccount() }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun redirectToRegister() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    private fun redirectToAccount() {
        val action = LoginFragmentDirections.actionLoginFragmentToAccountFragment()
        findNavController().navigate(action)
    }
}