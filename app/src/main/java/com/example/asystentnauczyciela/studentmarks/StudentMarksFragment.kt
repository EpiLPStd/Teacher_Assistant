package com.example.asystentnauczyciela.studentmarks

import android.annotation.SuppressLint
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.Communicator
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.entities.Mark
import com.example.asystentnauczyciela.select.SelectAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*

class StudentMarksFragment : Fragment() {
    lateinit var viewModel: StudentMarksViewModel
    private lateinit var studentMarksAdapter: StudentMarksAdapter
    private var communicator: Communicator ?= null
    private lateinit var marks: LiveData<List<Mark>>
    private var selectedSubject = ""
    private var selectedGroup = ""
    private var selectedStudent = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_student_marks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    private fun initialize() = runBlocking {
        val scope = CoroutineScope(Dispatchers.Main + Job())

        scope.launch { getSelected() }
        scope.launch { createMarksList() }
    }

    private fun getSelected() {
        communicator = ViewModelProvider(requireActivity())[Communicator::class.java]

        communicator!!.SubjectName.observe(requireActivity(), { o -> selectedSubject = o.toString() })
        communicator!!.GroupName.observe(requireActivity(), { o -> selectedGroup = o.toString() })
        communicator!!.StudentID.observe(requireActivity(), { o -> selectedStudent = o.toString() })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun createMarksList() {
        val studentMarksFactory = StudentMarksViewModelFactory(requireNotNull(this.activity).application)
        viewModel = ViewModelProvider(requireActivity(), studentMarksFactory)[StudentMarksViewModel::class.java]
        marks = viewModel.getStudentMarks(selectedSubject, selectedGroup, selectedStudent.toLong())

        studentMarksAdapter = StudentMarksAdapter(marks)

        marks.observe(viewLifecycleOwner) { studentMarksAdapter.notifyDataSetChanged() }

        val layoutManager = LinearLayoutManager(view?.context)

        view?.findViewById<RecyclerView>(R.id.studentMarksListView).let {
            it?.adapter = studentMarksAdapter
            it?.layoutManager = layoutManager
        }

        view?.findViewById<FloatingActionButton>(R.id.addNewMark)?.apply {
            setOnClickListener {
                it.findNavController().navigate(R.id.action_studentMarksFragment_to_addMarkFragment)
            }
        }
    }
}