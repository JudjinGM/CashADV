package app.cashadvisor.authorization.presentation.ui.diagrams

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.cashadvisor.R
import app.cashadvisor.databinding.FragmentFifthDiagramBinding
import app.cashadvisor.databinding.FragmentFourthDiagramBinding

class FifthDiagramFragment : Fragment() {

    private var _binding: FragmentFifthDiagramBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFifthDiagramBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}