package com.binbean.register

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.binbean.register.databinding.FragmentRegisterHoursBinding
import dagger.hilt.android.AndroidEntryPoint


data class DayTime(val start: String?, val end: String?)

@AndroidEntryPoint
class RegisterHoursFragment : Fragment() {
    private lateinit var binding: FragmentRegisterHoursBinding
    private val viewModel: CafeRegisterViewModel by activityViewModels()
    private lateinit var weekTimeList: List<DayTime>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHours()

        binding.registerButton.setOnClickListener {
            makeWeekTimeList()
            Log.d(TAG, weekTimeList.joinToString(","))
            viewModel.setWeekTimes(weekTimeList)
            // viewModel.registerCafe(requireContext())
            val action = RegisterHoursFragmentDirections.actionRegistrationToDrawing()
            findNavController().navigate(action)
        }
    }

    /**
     * 요일들을 리스트로 정리하는 함수
     */
    private fun makeWeekTimeList() {
        val startFields = listOf(
            binding.monStart, binding.tueStart, binding.wedStart,
            binding.thuStart, binding.friStart, binding.satStart, binding.sunStart
        )

        val endFields = listOf(
            binding.monEnd, binding.tueEnd, binding.wedEnd,
            binding.thuEnd, binding.friEnd, binding.satEnd, binding.sunEnd
        )

        weekTimeList = startFields.zip(endFields).map { (startView, endView) ->
            val start = startView.text.toString().ifBlank { null }
            val end = endView.text.toString().ifBlank { null }
            DayTime(start, end)
        }
    }

    /**
     * 시간을 설정하는 함수
     */
    private fun setHours() {
        val timeTextViews = listOf(
            binding.monStart,
            binding.monEnd,
            binding.tueStart,
            binding.tueEnd,
            binding.wedStart,
            binding.wedEnd,
            binding.thuStart,
            binding.thuEnd,
            binding.friStart,
            binding.friEnd,
            binding.satStart,
            binding.satEnd,
            binding.sunStart,
            binding.sunEnd
        )

        timeTextViews.forEach {
            it.setOnClickListener {
                if (it is TextView)
                    showTimePicker(it)
            }
        }
    }

    /**
     * 타임피커를 보여주고 텍스트뷰 시간을 변경하는 함수
     */
    private fun showTimePicker(timeTextView: TextView) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val timeString = "%02d:%02d".format(hourOfDay, minute)
            timeTextView.text = timeString
        }

        TimePickerDialog(requireContext(), listener, hour, minute, true).show()
    }

    companion object {
        private const val TAG = "RegisterHoursFragment"
    }

}