package com.example.asystentnauczyciela.addmark

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.ViewModelProvider
import com.example.asystentnauczyciela.Communicator
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.entities.Mark
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AddMarkFragment : Fragment() {
    lateinit var viewModel: AddMarkViewModel
    private var communicator: Communicator ?= null
    private var selectedSubject = ""
    private var selectedStudent = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(requireActivity())[AddMarkViewModel::class.java]

        return if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.fragment_add_mark, container, false)
        }
        else {
            inflater.inflate(R.layout.fragment_add_mark_horizontal, container, false)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    private fun initialize() {
        val scope = CoroutineScope(Dispatchers.Main + Job())

        scope.launch { getSelected() }
        scope.launch { addMark() }
    }

    private fun getSelected() {
        communicator = ViewModelProvider(requireActivity())[Communicator::class.java]

        communicator!!.SubjectName.observe(viewLifecycleOwner, { o -> selectedSubject = o.toString() })
        communicator!!.StudentID.observe(viewLifecycleOwner, { o -> selectedStudent = o.toString() })
    }

    @SuppressLint("SetTextI18n")
    fun addMark() {
        val markInput = view?.findViewById<EditText>(R.id.studentMark)
        var markType = "ocena"

        view?.findViewById<SwitchCompat>(R.id.markTypeSwitch)?.apply {
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    this.text = "Dodaj punkty"
                    markInput?.hint = "Wprowadź ilość punktów"

                    markType = "punkty"
                }
                else {
                    this.text = "Dodaj ocenę"
                    markInput?.hint = "Wprowadź ocenę"

                    markType = "ocena"
                }
            }
        }

        view?.findViewById<Button>(R.id.addMark)?.apply {
            setOnClickListener {
                val markValue = markInput?.text
                val markDescription = view?.findViewById<EditText>(R.id.studentMarkDescription)

                if (markValue.toString() != "" && selectedSubject != "" && selectedStudent != "") {
                    viewModel.setError()
                    viewModel.checkValue(markValue.toString(), markType, markDescription?.text.toString())

                    if (viewModel.error.value == false) {
                        val mark = Mark(0, markValue.toString(), markType, selectedStudent.toLong(), selectedSubject, markDescription?.text.toString())
                        viewModel.addMark(mark)

                        markInput?.text = null
                        markDescription?.text = null

                        Toast.makeText(context, "Ocena dodana pomyślnie", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(context, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}