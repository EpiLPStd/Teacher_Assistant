package com.example.asystentnauczyciela.subjectstudent

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.Communicator
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.entities.GroupsWithStudents
import com.example.asystentnauczyciela.entities.StudentsGroups
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StudentSubjectFragment : Fragment(), StudentSubjectAdapter.OnItemClickListener {
    lateinit var viewModel: StudentSubjectViewModel
    lateinit var studentsListAdapter: StudentSubjectAdapter
    private var communicator: Communicator ?= null
    lateinit var students: LiveData<List<GroupsWithStudents>>
    private var selectedSubject = ""
    private var selectedGroup = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_student_subject, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    private fun initialize() {
        val scope = CoroutineScope(Dispatchers.Main + Job())

        scope.launch { getNames() }
        scope.launch { createStudentsList() }
    }

    private fun getNames() {
        communicator = ViewModelProvider(requireActivity())[Communicator::class.java]

        communicator!!.SubjectName.observe(viewLifecycleOwner, { o -> selectedSubject = o.toString() })
        communicator!!.GroupName.observe(viewLifecycleOwner, { o -> selectedGroup = o.toString() })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun createStudentsList() {
        val studentSubjectFactory = StudentSubjectViewModelFactory(requireNotNull(this.activity).application)
        viewModel = ViewModelProvider(requireActivity(), studentSubjectFactory)[StudentSubjectViewModel::class.java]

        students = viewModel.getStudents(selectedSubject, selectedGroup)

        studentsListAdapter = StudentSubjectAdapter(students, this)

        students.observe(viewLifecycleOwner, { studentsListAdapter.notifyDataSetChanged() })

        val layoutManager = LinearLayoutManager(view?.context)

        view?.findViewById<RecyclerView>(R.id.studentsListView).let {
            it?.adapter = studentsListAdapter
            it?.layoutManager = layoutManager
        }
    }

    override fun onItemClick(position: Int) {
        communicator!!.setStudentName(students.value?.get(position)?.students?.get(position)?.studentName.toString())
        communicator!!.setStudentID(students.value?.get(position)?.students?.get(position)?.studentID.toString())

        findNavController().navigate(R.id.action_studentSubjectFragment_to_studentMarksFragment)
    }
}