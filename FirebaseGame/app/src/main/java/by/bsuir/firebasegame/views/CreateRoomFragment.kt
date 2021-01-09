package by.bsuir.firebasegame.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import by.bsuir.firebasegame.R
import by.bsuir.firebasegame.data.viewdata.Role
import by.bsuir.firebasegame.databinding.FragmentCreateRoomBinding
import by.bsuir.firebasegame.utilities.InjectorUtils
import by.bsuir.firebasegame.viewmodels.RoomViewModel
import com.squareup.picasso.Picasso

class CreateRoomFragment: Fragment(R.layout.fragment_create_room) {
    lateinit var viewModel: RoomViewModel
    lateinit var binding: FragmentCreateRoomBinding

    private val args: CreateRoomFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val factory = InjectorUtils.provideRoomViewModelFactory()

        viewModel = ViewModelProvider(this, factory)
            .get(RoomViewModel::class.java)

        binding = FragmentCreateRoomBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        viewModel.profile = args.profile!!
        if (args.role == Role.Host)
            viewModel.createGameRoom()
        else {
            binding.buttonStartGame.visibility = View.GONE
            viewModel.joinGameRoom(args.roomId ?: "")
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.host.observe(viewLifecycleOwner) {
            if (it.avatar.isNotEmpty()) {
                Picasso.get()
                    .load(it.avatar)
                    .into(binding.circleImageViewHostAvatar)
            }
        }

        viewModel.guest.observe(viewLifecycleOwner) {
            if (it.avatar.isNotEmpty()) {
                Picasso.get()
                    .load(it.avatar)
                    .into(binding.circleImageViewGuestAvatar)
            }
        }
    }
}