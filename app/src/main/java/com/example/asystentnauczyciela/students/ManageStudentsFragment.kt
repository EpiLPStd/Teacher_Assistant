package com.example.asystentnauczyciela.students

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.entities.Student

class ManageStudentsFragment : Fragment() {
    lateinit var viewModel: ManageStudentsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[ManageStudentsViewModel::class.java]

        return if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.fragment_manage_students, container, false)
        }
        else {
            inflater.inflate(R.layout.fragment_manage_students_horizontal, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.addStudentButton).apply {
            setOnClickListener {
                val nameTemp = view.findViewById<EditText>(R.id.studentName)
                val idTemp = view.findViewById<EditText>(R.id.studentID)

                if (nameTemp.text.toString() != "" && idTemp.text.toString() != "") {
                    viewModel.setError()
                    viewModel.checkStudent(nameTemp.text.toString(), idTemp.text.toString())

                    if (viewModel.error.value == false) {
                        val student = Student(0, viewModel.studentName.value.toString(),
                            viewModel.studentID.value.toString())

                        viewModel.addStudent(student)

                        nameTemp.text = null
                        idTemp.text = null

                        Toast.makeText(context, "Student dodany pomyślnie", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(context, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}