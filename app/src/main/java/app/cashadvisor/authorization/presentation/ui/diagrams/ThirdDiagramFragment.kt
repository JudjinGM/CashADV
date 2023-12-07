package app.cashadvisor.authorization.presentation.ui.diagrams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.cashadvisor.R
import app.cashadvisor.databinding.FragmentSecondDiagramBinding
import app.cashadvisor.databinding.FragmentThirdDiagramBinding

class ThirdDiagramFragment : Fragment() {

    private var _binding: FragmentThirdDiagramBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdDiagramBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}