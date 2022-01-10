package com.example.asystentnauczyciela.subjects

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.entities.Subject

class ManageSubjectsFragment : Fragment() {
    lateinit var viewModel: ManageSubjectsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[ManageSubjectsViewModel::class.java]

        return if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.fragment_manage_subjects, container, false)
        }
        else {
            inflater.inflate(R.layout.fragment_manage_subjects_horizontal, container, false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.addSubjectButton).apply {
            setOnClickListener{
                val nameTemp = view.findViewById<EditText>(R.id.subjectName)

                if (nameTemp.text.toString() != "") {
                    viewModel.setError()
                    viewModel.checkSubject(nameTemp.text.toString())

                    if (viewModel.error.value == false) {
                        val subject = Subject(0, viewModel.subjectName.value.toString())
                        viewModel.addSubject(subject)

                        nameTemp.text = null

                        Toast.makeText(context, "Przedmiot dodany pomyślnie", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(context, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}