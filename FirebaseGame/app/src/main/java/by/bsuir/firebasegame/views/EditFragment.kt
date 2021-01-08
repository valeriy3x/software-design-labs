package by.bsuir.firebasegame.views

import android.app.Activity
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.bsuir.firebasegame.R
import by.bsuir.firebasegame.databinding.FragmentEditBinding
import by.bsuir.firebasegame.utilities.GameNavigation
import by.bsuir.firebasegame.utilities.InjectorUtils
import by.bsuir.firebasegame.viewmodels.EditViewModel

class EditFragment : Fragment(R.layout.fragment_edit) {
    lateinit var viewModel: EditViewModel
    lateinit var binding: FragmentEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val factory = InjectorUtils.provideEditViewModelFactory()

        viewModel = ViewModelProvider(this, factory)
            .get(EditViewModel::class.java)

        binding = FragmentEditBinding.inflate(inflater, container, false)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.navigation.observe(viewLifecycleOwner) {
            when (it) {
                GameNavigation.EditToAccount -> navigateToAccount()
                GameNavigation.EditToPhotoSelector -> invokePhotoSelector()
            }
        }

        viewModel.avatar.observe(viewLifecycleOwner) {
            binding.circleImageViewEdit.setImageBitmap(it)
        }

        viewModel.globalErrorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToAccount() {
        val action = EditFragmentDirections.actionEditFragmentToAccountFragment()
        findNavController().navigate(action)
    }

    private fun invokePhotoSelector() {
        val restLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val filepath: Uri? = result.data?.data
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver,
                        filepath
                    )
                    viewModel.avatarUrl.value = filepath
                    viewModel.avatar.value = bitmap
                }


            }
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        restLauncher.launch(intent)
    }
}