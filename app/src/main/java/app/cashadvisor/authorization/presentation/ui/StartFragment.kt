package app.cashadvisor.authorization.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import app.cashadvisor.R
import app.cashadvisor.authorization.presentation.ui.model.StartScreenUiState
import app.cashadvisor.authorization.presentation.viewmodel.StartViewModel
import app.cashadvisor.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StartViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setDebugStatus(app.cashadvisor.BuildConfig.DEBUG)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateUi(uiState)
                }
            }
        }

        binding.tapToContinueTextView.setOnClickListener {
            viewModel.navigateToNextScreen()
        }

        binding.tapToUiKitSampleTextView.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_uikitSampleFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(uiState: StartScreenUiState) {
        with(uiState) {
            binding.tapToUiKitSampleTextView.isVisible = isDebug
            if (navigateToNextScreen) {
                if (isUserLoggedIn) {
                    findNavController().navigate(R.id.action_startFragment_to_pinCodeFragment)
                } else {
                    findNavController().navigate(R.id.action_startFragment_to_entryFragment)
                }
                viewModel.navigationProceeded()
            }
        }
    }
}