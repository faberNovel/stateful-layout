package com.fabernovel.statefullayout.sample.ui.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import com.fabernovel.statefullayout.requireStateView
import com.fabernovel.statefullayout.sample.R
import com.fabernovel.statefullayout.sample.databinding.MainFragmentBinding
import com.fabernovel.statefullayout.sample.databinding.StateCustomBinding
import com.fabernovel.statefullayout.showContent
import com.fabernovel.statefullayout.showError
import com.fabernovel.statefullayout.showLoading
import com.fabernovel.statefullayout.transitions.StateTransition
import com.fabernovel.statefullayout.transitions.StateTransitions

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        private const val TARGET_TRANSLATION = -1000f
    }

    private var _binding: MainFragmentBinding? = null

    private val binding: MainFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            stateContent.enterTransition = buildEnterTransition()
            stateContent.exitTransition = buildExitTransition()
            errorButton.setOnClickListener {
                statefulLayout.showError("There was an error") {
                    Toast.makeText(context, "Retry", Toast.LENGTH_SHORT).show()
                }
            }
            contentButton.setOnClickListener {
                statefulLayout.showContent()
            }
            loadingButton.setOnClickListener {
                statefulLayout.showLoading()
            }
            customButton.setOnClickListener {
                statefulLayout.showState(R.id.stateCustom)
            }

            // Binding a custom state view
            val customStateView = statefulLayout.requireStateView(R.id.stateCustom)
            val customBinding = StateCustomBinding.bind(customStateView)
            customBinding.customActionButton.setOnClickListener {
                Toast.makeText(requireContext(), "Doing stuff", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun MainFragmentBinding.buildExitTransition(): StateTransition {
        val iconAnimator = ObjectAnimator
            .ofFloat(icon, "translationY", 0f, TARGET_TRANSLATION)
        val textAnimator = ObjectAnimator
            .ofFloat(text, "translationX", 0f, TARGET_TRANSLATION)
        val backgroundAnimator = ObjectAnimator
            .ofFloat(stateContent, "alpha", 1f, 0f)

        val animator = AnimatorSet().apply {
            play(iconAnimator).with(textAnimator)
                .before(backgroundAnimator)
        }
        return StateTransitions.fromCallback(
            onStart = { transition, listener ->
                icon.translationY = 0f
                text.translationX = 0f
                stateContent.alpha = 1f

                animator.addListener(
                    onStart = { listener.onTransitionStart(transition) },
                    onEnd = { listener.onTransitionEnd(transition) }
                )
                animator.start()
            },
            onCancel = animator::cancel
        )
    }

    private fun MainFragmentBinding.buildEnterTransition(): StateTransition {
        val iconAnimator = ObjectAnimator
            .ofFloat(icon, "translationY", TARGET_TRANSLATION, 0f)
        val textAnimator = ObjectAnimator
            .ofFloat(text, "translationX", TARGET_TRANSLATION, 0f)
        val backgroundAnimator = ObjectAnimator
            .ofFloat(stateContent, "alpha", 0f, 1f)

        val animator = AnimatorSet().apply {
            play(iconAnimator)
                .with(textAnimator)
                .after(backgroundAnimator)
        }
        return StateTransitions.fromCallback(
            onStart = { transition, listener ->
                icon.translationY = TARGET_TRANSLATION
                text.translationX = TARGET_TRANSLATION
                stateContent.alpha = 0f

                animator.addListener(
                    onStart = { listener.onTransitionStart(transition) },
                    onEnd = { listener.onTransitionEnd(transition) }
                )
                animator.start()
            },
            onCancel = animator::cancel
        )
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
