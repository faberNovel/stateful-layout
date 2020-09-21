package com.fabernovel.statefullayout.test.ui.error

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fabernovel.statefullayout.showContent
import com.fabernovel.statefullayout.showError
import com.fabernovel.statefullayout.test.databinding.ErrorFragmentBinding

class ErrorFragment : Fragment() {
    private var _binding: ErrorFragmentBinding? = null

    private val binding: ErrorFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ErrorFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            errorWithCallbackButton.setOnClickListener {
                statefulLayout.showError("There was an error") {
                    Toast.makeText(context, "Retry", Toast.LENGTH_SHORT).show()
                }
            }
            errorWithoutCallbackButton.setOnClickListener {
                statefulLayout.showError("There was an error")
            }
            contentButton.setOnClickListener {
                statefulLayout.showContent()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
