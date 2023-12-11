package app.cashadvisor.authorization.presentation.ui.diagrams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.cashadvisor.customView.multipleCircleProgreeBar.model.Progress
import app.cashadvisor.databinding.FragmentSecondDiagramBinding
import app.cashadvisor.uikit.R


class SecondDiagramFragment : Fragment() {
    private var _binding: FragmentSecondDiagramBinding? = null
    private val binding get() = _binding!!

    private var processOne = 0
    private var processTwo = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSecondDiagramBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val progressList = listOf(
            Progress(
                id = 1,
                processOne,
                mainColor = resources.getColor(R.color.m6, context?.theme),
                backgroundColor = resources.getColor(R.color.sm2, context?.theme)
            ), Progress(
                id = 2,
                processTwo,
                mainColor = resources.getColor(R.color.m7, context?.theme),
                backgroundColor = resources.getColor(R.color.sm2, context?.theme)
            ), Progress(
                id = 1,
                processOne,
                mainColor = resources.getColor(R.color.m6, context?.theme),
                backgroundColor = resources.getColor(R.color.sm2, context?.theme)
            ), Progress(
                id = 2,
                processTwo,
                mainColor = resources.getColor(R.color.m7, context?.theme),
                backgroundColor = resources.getColor(R.color.sm2, context?.theme)
            ), Progress(
                id = 1,
                processOne,
                mainColor = resources.getColor(R.color.m6, context?.theme),
                backgroundColor = resources.getColor(R.color.sm2, context?.theme)
            )
        )

        binding.progressBar.initialiseProgressBar(progressList)

        binding.buttonPlusOne.setOnClickListener {
            processOne += 1
            binding.progressBar.setProgressById(1, processOne)
        }
        binding.buttonMinusOne.setOnClickListener {
            processOne -= 1
            binding.progressBar.setProgressById(1, processOne)
        }
        binding.buttonPlusTwo.setOnClickListener {
            processTwo += 1
            binding.progressBar.setProgressById(2, processTwo)
        }
        binding.buttonMinusTwo.setOnClickListener {
            processTwo -= 1
            binding.progressBar.setProgressById(2, processTwo)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}