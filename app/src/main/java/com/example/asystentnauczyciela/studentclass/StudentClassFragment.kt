package com.example.asystentnauczyciela.studentclass

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.entities.StudentsGroups

class StudentClassFragment : Fragment() {
    lateinit var viewModel: StudentClassViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[StudentClassViewModel::class.java]

        return if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.fragment_student_class, container, false)
        }
        else {
            inflater.inflate(R.layout.fragment_student_class_horizontal, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerSubjects = view.findViewById<Spinner>(R.id.subjects)
        val spinnerGroups = view.findViewById<Spinner>(R.id.groups)
        val spinnerStudents = view.findViewById<Spinner>(R.id.students)

        val subjectsList = viewModel.subjects
        var groupsList = mutableListOf<String>()
        val studentsList: MutableList<String>

        var subjectName: String
        var groupName: String
        var studentName: String

        if (subjectsList.isNotEmpty()) {
            subjectName = subjectsList[0]

            viewModel.studentsInit()
            studentsList = viewModel.students

            studentName = if (studentsList.isEmpty()) {
                ""
            } else {
                studentsList[0]
            }

            val adapterSubject = activity?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item,
                    subjectsList
                )
            }

            val adapterGroup = activity?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item,
                    groupsList
                )
            }

            val adapterStudent = activity?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item,
                    studentsList
                )
            }

            adapterSubject?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSubjects.adapter = adapterSubject

            spinnerSubjects.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                    val temp = subjectsList[position]
                    subjectName = temp

                    groupsList = viewModel.groupsInit(subjectName).toMutableList()

                    if (groupsList.isEmpty()) {
                        groupName = ""
                        Toast.makeText(context, "Brak grup dla wybranego przedmiotu", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        groupName = groupsList[0]
                    }

                    adapterGroup?.clear()
                    adapterGroup?.addAll(groupsList)
                    adapterGroup?.notifyDataSetChanged()
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) { }
            }

            groupsList = viewModel.groupsInit(subjectName).toMutableList()

            if (groupsList.isEmpty()) {
                groupName = ""
                Toast.makeText(context, "Brak grup dla wybranego przedmiotu", Toast.LENGTH_SHORT).show()
            }
            else {
                groupName = groupsList[0]
            }

            adapterGroup?.clear()
            adapterGroup?.addAll(groupsList)
            adapterGroup?.notifyDataSetChanged()

            adapterGroup?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerGroups.adapter = adapterGroup

            spinnerGroups.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                    groupName = groupsList[position]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) { }
            }

            if (studentsList.isNotEmpty()) {
                adapterStudent?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerStudents.adapter = adapterStudent

                spinnerStudents.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                        studentName = studentsList[position]
                    }

                    override fun onNothingSelected(adapterView: AdapterView<*>) { }
                }

                view.findViewById<Button>(R.id.addClassButton).apply {
                    setOnClickListener {
                        viewModel.setError()
                        viewModel.checkStudentClass(subjectName, groupName, studentName)

                        if (viewModel.error.value == false) {
                            val studentID = viewModel.getStudentID(studentName.takeLast(6))
                            val groupID = viewModel.getGroupID(subjectName, groupName)

                            val subjectGroupStudents = StudentsGroups(groupID, studentID)
                            viewModel.addStudentClass(subjectGroupStudents)

                            spinnerSubjects.setSelection(0)
                            spinnerGroups.setSelection(0)
                            spinnerStudents.setSelection(0)

                            Toast.makeText(context, "Uczeń dodany pomyślnie do grupy", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            else {
                Toast.makeText(context, "Brak studentów na liście", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(context, "Brak przedmiotów na liście", Toast.LENGTH_SHORT).show()
        }
    }
}