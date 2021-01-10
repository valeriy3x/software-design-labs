package by.bsuir.firebasegame.views

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.bsuir.firebasegame.R
import by.bsuir.firebasegame.databinding.FragmentPlaygroundBinding
import by.bsuir.firebasegame.utilities.InjectorUtils
import by.bsuir.firebasegame.viewmodels.PlaygroundViewModel

class PlaygroundFragment: Fragment(R.layout.fragment_playground) {
    lateinit var viewModel: PlaygroundViewModel
    lateinit var binding: FragmentPlaygroundBinding

    private val args: PlaygroundFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val factory = InjectorUtils.providePlaygroundViewModelFactory()

        viewModel = ViewModelProvider(this, factory)
            .get(PlaygroundViewModel::class.java)

        binding = FragmentPlaygroundBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        viewModel.startGame(args.gameid, args.role)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.message.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.endGame.observe(viewLifecycleOwner) {
            if (it) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Congratulations!")
                    .setMessage("You won the game!")
                    .setPositiveButton("Okay") {_, _ ->
                        navigateToAccount()
                    }.show()
            }
            else {
                AlertDialog.Builder(requireContext())
                    .setTitle("Sorry:(")
                    .setMessage("You lost the game!")
                    .setPositiveButton("Okay") {_, _ ->
                        navigateToAccount()
                    }.show()
            }
        }

    }

    private fun navigateToAccount() {
        val action = PlaygroundFragmentDirections.actionPlaygroundFragmentToAccountFragment()
        findNavController().navigate(action)
    }
}