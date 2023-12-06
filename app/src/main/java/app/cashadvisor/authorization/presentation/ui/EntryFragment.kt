package app.cashadvisor.authorization.presentation.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.cashadvisor.R
import app.cashadvisor.databinding.FragmentEntryBinding
import app.cashadvisor.main.presentation.ui.MainActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.dto.common.id.UserId
import com.vk.sdk.api.users.UsersService
import com.vk.sdk.api.users.dto.UsersUserFullDto

class EntryFragment : Fragment() {

    private var _binding: FragmentEntryBinding? = null
    private val binding get() = _binding!!

    private val activityVKAuthLauncher = registerForActivityResult(VK.getVKAuthActivityResultContract()) { result ->
        when (result) {
            is VKAuthenticationResult.Success -> {
                Log.d("TEST", "Success: ${result.token.userId}")
                Toast.makeText(
                    activity as? MainActivity,
                    "Success: userId ${result.token.userId.value}",
                    Toast.LENGTH_SHORT
                ).show()

                getUserInfoFromVK(result.token.userId)
            }

            is VKAuthenticationResult.Failed -> {
                Log.d("TEST", "Failed: ${result.exception.authError}")
                Toast.makeText(
                    activity as? MainActivity,
                    "Failed: ${result.exception.authError}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_entryFragment_to_loginFragment)
        }

        binding.btnSignup.setOnClickListener {
            findNavController().navigate(R.id.action_entryFragment_to_signupFragment)
        }

        binding.btnAuthVk.setOnClickListener {
            activityVKAuthLauncher.launch(arrayListOf())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getUserInfoFromVK(userId: UserId) {
        VK.execute(UsersService().usersGet(userIds = listOf(userId)), object:
            VKApiCallback<List<UsersUserFullDto>> {
            override fun success(result: List<UsersUserFullDto>) {
                Log.d("TEST", "result: ${result.first().firstName}")
                Toast.makeText(
                    activity as? MainActivity,
                    "Success: ${result.first().firstName} ${result.first().lastName}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            override fun fail(error: Exception) {
                Log.e("TEST", error.toString())
            }
        })
    }
}