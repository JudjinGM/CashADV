package app.cashadvisor.authorization.presentation.ui.diagrams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.cashadvisor.BuildConfig.BUILD_TYPE
import app.cashadvisor.R
import app.cashadvisor.common.ui.BaseFragment
import app.cashadvisor.common.ui.BaseViewModel
import app.cashadvisor.databinding.FragmentUikitSampleBinding
import com.google.firebase.BuildConfig

class UikitSampleViewModel : BaseViewModel() {
    // example viewModel
}

class UikitSampleFragment :
    BaseFragment<FragmentUikitSampleBinding, UikitSampleViewModel>(FragmentUikitSampleBinding::inflate) {
    override val viewModel: UikitSampleViewModel by lazy { UikitSampleViewModel() }


    override fun configureViews() {

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

    override fun subscribe() {
        // example subscribe
    }

}