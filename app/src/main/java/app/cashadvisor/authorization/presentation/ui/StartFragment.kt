package app.cashadvisor.authorization.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import app.cashadvisor.R
import app.cashadvisor.authorization.presentation.ui.models.TestStartUiState
import app.cashadvisor.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartFragment : Fragment() {

    //отладочный фрагмент, перед МР все будет очищено, верстка будет приведена в первоначальное состояние

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureViews()
        subscribe()
        viewModel.init()
    }

    private fun configureViews() {
        binding.btnRegister.setOnClickListener {
            viewModel.register()
        }

        binding.emailCodeInput
            .doOnTextChanged { text, _, _, _ ->
                viewModel.setEmailConfirmCode(text.toString())
            }

        binding.sendEmailCodeButton.setOnClickListener {
            viewModel.sendEmailConfirmCode()
        }

        binding.btnLogin.setOnClickListener {
            viewModel.login()
        }

        binding.loginCodeInput
            .doOnTextChanged { text, _, _, _ ->
                viewModel.setLoginConfirmCode(text.toString())
            }

        binding.sendLoginCodeButton.setOnClickListener {
            viewModel.sendLoginConfirmCode()
        }


        if (app.cashadvisor.BuildConfig.DEBUG) {

            binding.uikitSampleButton.isVisible = true
            binding.uikitSampleButton.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_uikitSampleFragment)
            }
        }
    }

    private fun subscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateUi(uiState)
                }
            }
        }
    }

    private fun updateUi(uiState: TestStartUiState) {
        with(uiState) {
            binding.sendEmailCodeButton.isEnabled = emailCodeIsValid
            binding.sendLoginCodeButton.isEnabled = loginCodeIsValid
            if (message.isNotBlank()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                viewModel.messageWasShown()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}