package com.stateful.sample_app.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.stateful.sample_app.databinding.MainFragmentBinding
import com.stateful.statefullayout.showContent
import com.stateful.statefullayout.showError
import com.stateful.statefullayout.showLoading

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding: MainFragmentBinding
        get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            errorButton.setOnClickListener {
                statefulLayout.showError {
                    Toast.makeText(context, "Retry", Toast.LENGTH_SHORT).show()
                }
            }
            contentButton.setOnClickListener {
                statefulLayout.showContent()
            }
            loadingButton.setOnClickListener {
                statefulLayout.showLoading()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
