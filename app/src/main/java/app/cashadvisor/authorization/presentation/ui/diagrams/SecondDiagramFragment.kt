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

    private var processProgressOne = 0
    private var processProgressTwo = 0


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
                processProgressOne,
                500,
                mainColor = resources.getColor(R.color.m6, context?.theme),
                backgroundColor = resources.getColor(R.color.sm2, context?.theme)
            ), Progress(
                id = 2,
                processProgressTwo,
                100,
                mainColor = resources.getColor(R.color.m7, context?.theme),
                backgroundColor = resources.getColor(R.color.sm2, context?.theme)
            ), Progress(
                id = 1,
                processProgressOne,
                50,
                mainColor = resources.getColor(R.color.m6, context?.theme),
                backgroundColor = resources.getColor(R.color.sm2, context?.theme)
            ), Progress(
                id = 2,
                processProgressTwo,
                10,
                mainColor = resources.getColor(R.color.m7, context?.theme),
                backgroundColor = resources.getColor(R.color.sm2, context?.theme)
            ), Progress(
                id = 1,
                processProgressOne,
                5,
                mainColor = resources.getColor(R.color.m6, context?.theme),
                backgroundColor = resources.getColor(R.color.sm2, context?.theme)
            )
        )

        binding.progressBar.initialiseProgressBar(progressList)

        binding.buttonPlusOne.setOnClickListener {
            processProgressOne += 1
            binding.progressBar.setProgressById(1, processProgressOne)
        }
        binding.buttonMinusOne.setOnClickListener {
            processProgressOne -= 1
            binding.progressBar.setProgressById(1, processProgressOne)
        }
        binding.buttonPlusTwo.setOnClickListener {
            processProgressTwo += 1
            binding.progressBar.setProgressById(2, processProgressTwo)
        }
        binding.buttonMinusTwo.setOnClickListener {
            processProgressTwo -= 1
            binding.progressBar.setProgressById(2, processProgressTwo)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}