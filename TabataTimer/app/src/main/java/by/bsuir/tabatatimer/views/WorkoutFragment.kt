package by.bsuir.tabatatimer.views

import android.app.AlertDialog
import android.content.*
import android.graphics.Color
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.bsuir.tabatatimer.WorkoutService
import by.bsuir.tabatatimer.R
import by.bsuir.tabatatimer.databinding.FragmentWorkoutBinding
import by.bsuir.tabatatimer.utilities.InjectorUtils
import by.bsuir.tabatatimer.data.viewdata.helpers.StepManager
import by.bsuir.tabatatimer.utilities.HomeNavigation
import by.bsuir.tabatatimer.viewmodels.WorkoutViewModel
import java.util.*


class WorkoutFragment : Fragment(R.layout.fragment_workout) {
    private val args: AddWorkoutFragmentArgs by navArgs()

    private lateinit var viewModel: WorkoutViewModel
    private lateinit var binding: FragmentWorkoutBinding
    private var workoutService: WorkoutService? = null


    private val broadcastReceiverData = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val remainingTime =
                intent?.extras?.getLong(WorkoutService.broadcastCountdownExtra, 0)
            val step = intent?.extras?.getString(WorkoutService.broadcastCurrentStepExtra)
            viewModel.currentTime.value = remainingTime.toString()
            viewModel.currentStep.value = step
        }
    }

    private val broadcastReceiverStepsManager = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.stepChanged()
        }
    }

    private val broadcastReceiverPauseManager = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            viewModel.running.value = false
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val factory = InjectorUtils.provideWorkoutViewModelFactory()
        viewModel = ViewModelProvider(this, factory)
            .get(WorkoutViewModel::class.java)

        binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        viewModel.sequence = args.sequence

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter: ArrayAdapter<String>? = context?.let {
            ArrayAdapter(
                it,
                R.layout.item_workout,
                viewModel.combineLists()
            )
        }
        binding.listView.choiceMode = ListView.CHOICE_MODE_SINGLE;
        binding.listView.adapter = adapter


        val intent = Intent(context, WorkoutService::class.java)
        intent.putExtra(WorkoutService.broadcastDurationsExtra, viewModel.durations as ArrayList<*>)
        intent.putExtra(WorkoutService.broadcastStepsExtra, viewModel.steps as ArrayList<*>)

        val serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                workoutService = (service as WorkoutService.LocalBinder).getService()
            }

            override fun onServiceDisconnected(name: ComponentName?) {
            }

        }

        activity?.registerReceiver(broadcastReceiverData, IntentFilter(WorkoutService.broadcastAction))
        activity?.registerReceiver(broadcastReceiverStepsManager, IntentFilter(WorkoutService.broadcastStepChanged))
        activity?.registerReceiver(broadcastReceiverPauseManager, IntentFilter(WorkoutService.broadcastEnd))

        viewModel.stepManager.observe(viewLifecycleOwner) {
            when (it) {
                StepManager.NextStep -> workoutService?.nextStep()
                StepManager.PreviousStep -> workoutService?.previousStep()
                StepManager.Pause -> workoutService?.stopTimer()
                StepManager.Start -> {
                    if (viewModel.active.value == false) {
                        activity?.bindService(intent, serviceConnection, 0)
                        activity?.startService(intent)
                        viewModel.active.value = true
                    } else {
                        workoutService?.resumeTimer()
                    }
                }
                StepManager.Stop -> {
                    context?.stopService(Intent(requireContext(), WorkoutService::class.java))
                    viewModel.navigateToHome()
                }
                else -> { viewModel.displayError() }
            }
        }

        viewModel.navigation.observe(viewLifecycleOwner) {
            when(it) {
                HomeNavigation.WorkoutToHome -> navigateToHome()
                else -> { viewModel.displayError() }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.currentStep.observe(viewLifecycleOwner) {
            when(it) {
                    getString(R.string.work) -> binding.layoutWorkout.setBackgroundColor(Color.RED)
                else -> binding.layoutWorkout.setBackgroundColor(Color.BLUE)
            }
        }

        viewModel.currentItemIndex.observe(viewLifecycleOwner) {
            binding.listView.setSelection(it)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            context?.let { context_dialog ->
                AlertDialog.Builder(context_dialog)
                    .setTitle(getString(R.string.warning))
                    .setMessage(getString(R.string.warning_message))
                    .setPositiveButton(
                        getString(R.string.dialog_positive)
                    ) { _, _ -> viewModel.stopWorkout() }
                    .setNegativeButton(getString(R.string.cancel)) { _, _ ->

                    }
                    .show()
            }
        }

    }



    private fun navigateToHome() {
        val action = WorkoutFragmentDirections.actionWorkoutFragmentToHomeFragment()
        findNavController().navigate(action)
    }
}
