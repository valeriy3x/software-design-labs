package by.bsuir.firebasegame.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.bsuir.firebasegame.R
import by.bsuir.firebasegame.data.viewdata.Role
import by.bsuir.firebasegame.databinding.FragmentCreateRoomBinding
import by.bsuir.firebasegame.databinding.FragmentJoinRoomBinding
import by.bsuir.firebasegame.utilities.GameNavigation
import by.bsuir.firebasegame.utilities.InjectorUtils
import by.bsuir.firebasegame.viewmodels.RoomViewModel

class JoinRoomFragment: Fragment(R.layout.fragment_join_room) {
    lateinit var viewModel: RoomViewModel
    lateinit var binding: FragmentJoinRoomBinding

    private val args: JoinRoomFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val factory = InjectorUtils.provideRoomViewModelFactory()

        viewModel = ViewModelProvider(this, factory)
            .get(RoomViewModel::class.java)

        binding = FragmentJoinRoomBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigation.observe(viewLifecycleOwner) {
            when(it) {
                GameNavigation.JoinToRoom -> navigateToRoom()
            }
        }
    }

    private fun navigateToRoom() {
        val action = JoinRoomFragmentDirections.actionJoinRoomFragmentToCreateRoomFragment(Role.Guest, args.profile, viewModel.roomId.value)
        findNavController().navigate(action)
    }
}