package com.binbean.register

import android.os.Bundle
import android.view.DragEvent
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.binbean.domain.cafe.ObjectItem
import com.binbean.register.databinding.FragmentRegisterDrawingBinding
import com.binbean.register.drawing.CanvasView
import com.binbean.register.drawing.ObjectListAdapter
import com.binbean.register.drawing.RecyclerViewDecoration

class RegisterDrawingFragment : Fragment() {
    private lateinit var binding: FragmentRegisterDrawingBinding

    private val floorViews = mutableMapOf<Int, CanvasView>()
    private var currentFloor = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterDrawingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObjectRecyclerView()
        showFloor(currentFloor)
        setupObjectDragAndDrop()
        setupFloorControl()
    }

    private fun setupObjectRecyclerView() {
        binding.objectRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.objectRecyclerView.adapter = ObjectListAdapter().apply {
            val objectList = listOf(
                ObjectItem("chair", "의자", R.drawable.obj_seat),
                ObjectItem("toilet", "화장실", R.drawable.obj_toilet),
                ObjectItem("door", "문", R.drawable.obj_door),
                ObjectItem("window", "창문", R.drawable.obj_window),
                ObjectItem("counter", "카운터", R.drawable.obj_casher),
                ObjectItem("table", "테이블", R.drawable.obj_table)
            )
            submitList(objectList)
        }
        binding.objectRecyclerView.addItemDecoration(RecyclerViewDecoration(24))
    }

    private fun setupObjectDragAndDrop() {
        binding.drawingContainer.setOnDragListener { _, event ->
            if (event.action == DragEvent.ACTION_DROP) {
                val type = event.clipData.getItemAt(0).text.toString()
                addObjectView(type, event.x, event.y)
            }
            true
        }
    }

    private fun addObjectView(type: String, x: Float, y: Float) {
        val currentView = floorViews[currentFloor] ?: return

        val adjustedX = x - binding.canvasView.getOffsetX()

        val imageView = ImageView(requireContext()).apply {
            setImageResource(getIconByType(type))
            layoutParams = FrameLayout.LayoutParams(130, 130).apply {
                leftMargin = adjustedX.toInt() - 50
                topMargin = y.toInt() - 50
            }

            // 삭제 기능
            val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    (parent as? ViewGroup)?.removeView(this@apply)
                    return true
                }
            })

            // 드래그 가능
            setOnTouchListener(object : View.OnTouchListener {
                var dX = 0f
                var dY = 0f

                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    // 먼저 gestureDetector 처리
                    if (gestureDetector.onTouchEvent(event)) return true // 제스처가 처리되면 드래그 무시


                    when (event.action) {
                        MotionEvent.ACTION_DOWN -> {
                            dX = event.rawX - v.x
                            dY = event.rawY - v.y
                            return true
                        }

                        MotionEvent.ACTION_MOVE -> {
                            val newX = event.rawX - dX
                            val newY = event.rawY - dY

                            val layoutParams = v.layoutParams as FrameLayout.LayoutParams
                            layoutParams.leftMargin = newX.toInt()
                            layoutParams.topMargin = newY.toInt()
                            v.layoutParams = layoutParams
                            return true
                        }
                    }
                    return false
                }
            })
        }
        // imageView.translationX = binding.canvasView.getOffsetX()
        currentView.addView(imageView)
    }

    private fun getIconByType(type: String): Int {
        return when (type) {
            "counter" -> R.drawable.obj_casher
            "chair" -> R.drawable.obj_seat
            "toilet" -> R.drawable.obj_toilet
            "door" -> R.drawable.obj_door
            "table" -> R.drawable.obj_table
            "window" -> R.drawable.obj_window
            else -> android.R.drawable.ic_menu_help
        }
    }

    private fun showFloor(floor: Int) {
        binding.drawingContainer.removeAllViews()

        val editorView = floorViews.getOrPut(floor) { CanvasView(requireContext()) }

        binding.drawingContainer.addView(editorView, FrameLayout.LayoutParams(1000, FrameLayout.LayoutParams.MATCH_PARENT))
        binding.tvCurrentFloor.text = "${floor}층"
    }

    private fun setupFloorControl() {
        binding.btnNextFloor.setOnClickListener {
            val nextFloor = currentFloor + 1

            if (floorViews.containsKey(nextFloor)) {
                // 이미 존재하는 층 → 바로 이동
                currentFloor = nextFloor
                showFloor(currentFloor)
                Toast.makeText(requireContext(), "${currentFloor}층으로 이동했습니다.", Toast.LENGTH_SHORT).show()
            } else {
                // 새로운 층 → 알림 띄우기
                AlertDialog.Builder(requireContext())
                    .setTitle("도면 추가")
                    .setMessage("${nextFloor}층 도면을 추가하시겠습니까?")
                    .setPositiveButton("추가") { _, _ ->
                        currentFloor = nextFloor
                        showFloor(currentFloor)
                        Toast.makeText(requireContext(), "${currentFloor}층 도면이 추가되었습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                    .setNegativeButton("취소", null)
                    .show()
            }
        }

        binding.btnPrevFloor.setOnClickListener {
            if (currentFloor > 1) {
                currentFloor--
                showFloor(currentFloor)
                Toast.makeText(requireContext(), "${currentFloor}층으로 이동했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}