package app.cashadvisor.authorization.presentation.ui

import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import app.cashadvisor.R
import app.cashadvisor.authorization.presentation.ui.models.StartScreenUiState
import app.cashadvisor.authorization.presentation.viewmodel.StartViewModel
import app.cashadvisor.common.ui.BaseFragment
import app.cashadvisor.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartFragment :
    BaseFragment<FragmentStartBinding, StartViewModel>(FragmentStartBinding::inflate) {
    override val viewModel: StartViewModel by viewModels()
    override fun onConfigureViews() {
        binding.containerLayout.setOnClickListener {
            viewModel.authenticateUser()
        }

        binding.tapToUiKitSampleTextView.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_uikitSampleFragment)
        }
    }

    override fun onSubscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateUi(uiState)
                }
            }
        }
    }

    private fun updateUi(uiState: StartScreenUiState) {
        with(uiState) {
            binding.tapToUiKitSampleTextView.isVisible = isDebug

            userMessage?.let {
                Toast.makeText(requireContext(), getText(userMessage), Toast.LENGTH_SHORT).show()
                viewModel.userMessageShown()
            }

            if (isUserAuthenticated) {
                if (isAuthenticationSuccessful) {
                    findNavController().navigate(R.id.action_startFragment_to_pinCodeFragment)
                } else {
                    findNavController().navigate(R.id.action_startFragment_to_entryFragment)
                }
                viewModel.userProceededNext()
            }
        }
    }

}