package by.bsuir.tabatatimer.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import by.bsuir.tabatatimer.R
import by.bsuir.tabatatimer.databinding.FragmentHomeBinding
import by.bsuir.tabatatimer.utilities.InjectorUtils
import by.bsuir.tabatatimer.viewmodels.HomeViewModel

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

    }
}