package app.cashadvisor.authorization.presentation.ui
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.cashadvisor.R
import app.cashadvisor.databinding.FragmentEntryBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail

class EntryFragment : Fragment() {
    private var _binding: FragmentEntryBinding? = null
    private val binding get() = _binding!!
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var startResultSignIn: ActivityResultLauncher<Intent>

    private val vkAuthCallback = object : VKID.AuthCallback {
        override fun onSuccess(accessToken: AccessToken) {
            val token = accessToken.token
            Toast.makeText(requireContext(), "token: ${token}", Toast.LENGTH_SHORT).show()
            Toast.makeText(
                requireContext(),
                "${accessToken.userData.firstName} ${accessToken.userData.lastName}",
                Toast.LENGTH_LONG
            ).show()
        }

        override fun onFail(fail: VKIDAuthFail) {
            Toast.makeText(requireContext(), "error: ${fail.description}", Toast.LENGTH_LONG).show()
            when (fail) {
                is VKIDAuthFail.Canceled -> {
                    //...
                }
                else -> {
                    //...
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
        setupGoogleRegistration()
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

        binding.signInButton.setOnClickListener{
            val signIntent = mGoogleSignInClient.signInIntent
            startResultSignIn.launch(signIntent)
        }
        binding.btnAuthVk.setOnClickListener {
            val vkid = VKID(requireContext())
            vkid.authorize(this, vkAuthCallback)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()

        //Проверка вошел ли пользователь с помощью google в приложение

        val account = GoogleSignIn.getLastSignedInAccount(requireActivity())
    }

    //Настроить регистрацию через google
    private fun setupGoogleRegistration() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.client_id))
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        setupResultSignIn()
    }

    private fun setupResultSignIn(){
        startResultSignIn = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val task =
                    GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            val googleFirstName = account?.givenName ?: ""
            Log.i("Google Account", "Google First Name: $googleFirstName")
            val googleLastName = account?.familyName ?: ""
            Log.i("Google Account", "Google Last Name: $googleLastName")
            val googleEmail = account?.email ?: ""
            Log.i("Google Account", "Google Email: $googleEmail")
            val googleIdToken = account?.idToken
            Log.i("Google Account", "Google idToken: $googleIdToken")
            Log.i("Google Account", "Token Info: https://oauth2.googleapis.com/tokeninfo?id_token=$googleIdToken")

        } catch (e: ApiException) {
            Log.e(
                "Google Account", "failed code= ${e.statusCode}"
            )
        }
    }
}