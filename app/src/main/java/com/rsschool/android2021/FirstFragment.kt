package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import java.lang.NumberFormatException

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var generateButtonAction: GenerateButton? = null
    private var previousResult: TextView? = null
    private var minNumberInput: EditText? = null
    private var maxNumberInput: EditText? = null




    override fun onAttach(context: Context) {
        super.onAttach(context)
        generateButtonAction = context as GenerateButton
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)
        minNumberInput=view.findViewById(R.id.min_value)
        maxNumberInput=view.findViewById(R.id.max_value)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"


        minNumberInput?.addTextChangedListener {
            generateButton?.isEnabled = isMinAndMaxCorrect()
        }
        maxNumberInput?.addTextChangedListener {
            generateButton?.isEnabled = isMinAndMaxCorrect()
        }
        generateButton?.setOnClickListener {
            generateButtonAction?.generateNumber(minNumberInput?.text.toString().toInt(), maxNumberInput?.text.toString().toInt())

        }

    }
    private fun isMinAndMaxCorrect(): Boolean {
        val minValue = minNumberInput?.text ?: ""
        val maxValue = maxNumberInput?.text ?: ""

        try {
            if (minValue.isEmpty()
                || maxValue.isEmpty()
                || minValue.toString().toInt() > maxValue.toString().toInt())
                return false
        } catch (Exception: NumberFormatException) {
            return false
        }

        return true
    }
    interface GenerateButton {
        fun generateNumber(min: Int, max: Int)
    }
    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}