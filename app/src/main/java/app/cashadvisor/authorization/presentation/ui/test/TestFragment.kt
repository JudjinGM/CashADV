package app.cashadvisor.authorization.presentation.ui.test

import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import app.cashadvisor.common.ui.BaseFragment
import app.cashadvisor.databinding.FragmentTestBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TestFragment() :
    BaseFragment<FragmentTestBinding, TestViewModel>(FragmentTestBinding::inflate) {
    override val viewModel: TestViewModel by viewModels()

    //TODO: отладочный фрагмент, перед МР все будет очищено, верстка будет приведена в первоначальное состояние

    override fun onConfigureViews() {
        binding.emailInput.doOnTextChanged { text, _, _, _ ->
            viewModel.setEmail(text.toString())
        }

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
    }

    override fun onSubscribe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    updateUi(uiState)
                }
            }
        }
        viewModel.init()
    }

    private fun updateUi(uiState: TestStartUiState) {
        with(uiState) {
            binding.sendEmailCodeButton.isEnabled = registerCodeIsValid
            binding.sendLoginCodeButton.isEnabled = loginCodeIsValid
            binding.btnRegister.isEnabled = emailIsValid
            binding.btnLogin.isEnabled = emailIsValid

            if (message.isNotBlank()) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
                viewModel.messageWasShown()
            }
        }
    }

}
