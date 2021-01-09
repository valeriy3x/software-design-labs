package by.bsuir.firebasegame.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.bsuir.firebasegame.R
import by.bsuir.firebasegame.data.viewdata.Role
import by.bsuir.firebasegame.databinding.FragmentAccountBinding
import by.bsuir.firebasegame.utilities.GameNavigation
import by.bsuir.firebasegame.utilities.InjectorUtils
import by.bsuir.firebasegame.viewmodels.AccountViewModel
import com.squareup.picasso.Picasso

class AccountFragment: Fragment(R.layout.fragment_account) {
    private lateinit var viewModel: AccountViewModel
    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val factory = InjectorUtils.provideAccountViewModelFactory()
        viewModel = ViewModelProvider(this, factory)
            .get(AccountViewModel::class.java)

        binding = FragmentAccountBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fillData()
        viewModel.avatarUrl.observe(viewLifecycleOwner) {
            Picasso.get().load(it).placeholder(R.drawable.unknown).into(binding.circleImageViewAccount)
        }

        viewModel.navigation.observe(viewLifecycleOwner) {
            when(it) {
                GameNavigation.AccountToEdit -> navigateToEdit()
                GameNavigation.AccountToLogin -> navigateToLogin()
                GameNavigation.AccountToCreate -> navigationToCreate()
                GameNavigation.AccountToJoin -> navigationToJoin()
            }
        }
    }

    private fun navigateToEdit() {
        val action = AccountFragmentDirections.actionAccountFragmentToEditFragment(viewModel.profile)
        findNavController().navigate(action)
    }

    private fun navigateToLogin() {
        val action = AccountFragmentDirections.actionAccountFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    private fun navigationToCreate() {
        val action = AccountFragmentDirections.actionAccountFragmentToCreateRoomFragment(Role.Host, viewModel.profile)
        findNavController().navigate(action)
    }

    private fun navigationToJoin() {
        val action = AccountFragmentDirections.actionAccountFragmentToJoinRoomFragment(viewModel.profile)
        findNavController().navigate(action)
    }
}