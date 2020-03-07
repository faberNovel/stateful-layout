package com.stateful.sample_app.ui.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import com.stateful.sample_app.databinding.MainFragmentBinding
import com.stateful.statefullayout.showContent
import com.stateful.statefullayout.showError
import com.stateful.statefullayout.showLoading
import com.stateful.statefullayout.transitions.StateTransition
import com.stateful.statefullayout.transitions.StateTransitions

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        private const val TARGET_TRANSLATION = -1000f
    }

    private var _binding: MainFragmentBinding? = null

    private val binding: MainFragmentBinding
        get() = _binding!!

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
            stateContent.enterAnimation = buildEnterAnimation()
            stateContent.exitAnimation = buildExitAnimation()
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

    private fun MainFragmentBinding.buildExitAnimation(): StateTransition {
        return StateTransitions.fromCallback { transition, listener ->
            icon.translationY = 0f
            text.translationX = 0f
            stateContent.alpha = 1f

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
            animator.addListener(
                onStart = { listener.onTransitionStart(transition) },
                onEnd = { listener.onTransitionEnd(transition) }
            )
            animator.start()
        }
    }

    private fun MainFragmentBinding.buildEnterAnimation(): StateTransition {
        return StateTransitions.fromCallback { transition, listener ->
            icon.translationY = TARGET_TRANSLATION
            text.translationX = TARGET_TRANSLATION
            stateContent.alpha = 0f

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
            animator.addListener(
                onStart = { listener.onTransitionStart(transition) },
                onEnd = { listener.onTransitionEnd(transition) }
            )
            animator.start()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
