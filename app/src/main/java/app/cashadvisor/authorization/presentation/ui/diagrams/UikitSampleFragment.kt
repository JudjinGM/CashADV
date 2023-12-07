package app.cashadvisor.authorization.presentation.ui.diagrams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.cashadvisor.BuildConfig.BUILD_TYPE
import app.cashadvisor.R
import app.cashadvisor.databinding.FragmentUikitSampleBinding
import com.google.firebase.BuildConfig

class UikitSampleFragment : Fragment() {

    private var _binding: FragmentUikitSampleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUikitSampleBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.firstDiagramButton.setOnClickListener {
            findNavController().navigate(R.id.action_uikitSampleFragment_to_firstDiagramFragment)
        }
        binding.secondDiagramButton.setOnClickListener {
            findNavController().navigate(R.id.action_uikitSampleFragment_to_secondDiagramFragment)
        }
        binding.thirdDiagramButton.setOnClickListener {
            findNavController().navigate(R.id.action_uikitSampleFragment_to_thirdDiagramFragment)
        }
        binding.fourthDiagramButton.setOnClickListener {
            findNavController().navigate(R.id.action_uikitSampleFragment_to_fourthDiagramFragment)
        }
        binding.fifthDiagramButton.setOnClickListener {
            findNavController().navigate(R.id.action_uikitSampleFragment_to_fifthDiagramFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}