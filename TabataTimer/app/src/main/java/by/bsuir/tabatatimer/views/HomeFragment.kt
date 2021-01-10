package by.bsuir.tabatatimer.views

import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.tabatatimer.R
import by.bsuir.tabatatimer.data.viewdata.Sequence
import by.bsuir.tabatatimer.databinding.FragmentHomeBinding
import by.bsuir.tabatatimer.utilities.HomeNavigation
import by.bsuir.tabatatimer.utilities.InjectorUtils
import by.bsuir.tabatatimer.viewmodels.HomeViewModel
import by.bsuir.tabatatimer.views.adapters.ImageButtonManager
import by.bsuir.tabatatimer.views.adapters.PopUpMenuManager
import by.bsuir.tabatatimer.views.adapters.SequencesAdapter

class HomeFragment: Fragment(R.layout.fragment_home) {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val factory = InjectorUtils.provideHomeViewModelFactory()
        viewModel = ViewModelProvider(this, factory)
            .get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_home)
        val adapter = SequencesAdapter()
        adapter.listener = object : PopUpMenuManager<Sequence> {
            override fun showPopUp(view: View, model: Sequence) {
                val popup = PopupMenu(view.context, view)
                popup.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.delete_sequence -> {
                            viewModel.deleteSequence(model)
                            true
                        }
                        R.id.edit_sequence -> {
                            val action = HomeFragmentDirections.actionHomeFragmentToAddWorkoutFragment(model)
                            findNavController().navigate(action)
                            true
                        }
                        else -> false
                    }
                }
                val inflater: MenuInflater = popup.menuInflater
                inflater.inflate(R.menu.menu_sequence, popup.menu)
                popup.show()
            }
        }

        adapter.playListener = object : ImageButtonManager<Sequence> {
            override fun play(model: Sequence) {
                val action = HomeFragmentDirections.actionHomeFragmentToWorkoutFragment(model)
                findNavController().navigate(action)
            }
        }

        viewModel.dataSet.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        recyclerView.adapter = adapter

        viewModel.navigation.observe(viewLifecycleOwner) {
            when(it) {
                HomeNavigation.HomeToEdit -> navigateToEdit()
            }
        }
    }

    private fun navigateToEdit() {
        val action = HomeFragmentDirections.actionHomeFragmentToAddWorkoutFragment()
        findNavController().navigate(action)
    }
}