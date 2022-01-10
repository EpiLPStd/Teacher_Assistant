package com.example.asystentnauczyciela.groups

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.entities.Group


class ManageGroupsFragment : Fragment() {
    lateinit var viewModel: ManageGroupsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[ManageGroupsViewModel::class.java]

        return if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.fragment_manage_groups, container, false)
        }
        else {
            inflater.inflate(R.layout.fragment_manage_groups_horizontal, container, false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinnerSubject = view.findViewById<Spinner>(R.id.subjectsList)
        val namesList = viewModel.subjects

        var subjectName = ""
        var groupDay = ""
        val groupName = view.findViewById<EditText>(R.id.subjectGroupName)
        val spinnerDay = view.findViewById<Spinner>(R.id.subjectDayOfWeek)
        val groupStart = view.findViewById<EditText>(R.id.subjectStartHour)
        val groupEnd = view.findViewById<EditText>(R.id.subjectEndHour)

        if (namesList.isNotEmpty()) {
            val days = listOf("poniedziałek", "wtorek", "środa", "czwartek", "piątek", "sobota", "niedziela")

            val adapterSubject = activity?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item,
                    namesList
                )
            }

            val adapterDay = activity?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item,
                    days
                )
            }

            adapterSubject?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerSubject.adapter = adapterSubject

            spinnerSubject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                    subjectName = namesList[position]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) { }
            }

            adapterDay?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerDay.adapter = adapterDay

            spinnerDay.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                    groupDay = days[position]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>) { }
            }

            view.findViewById<Button>(R.id.addGroupButton).apply {
                setOnClickListener {
                    if (groupName.text.toString() != "" && groupDay != "" &&
                        groupStart.text.toString() != "" && groupEnd.text.toString() != "") {
                        viewModel.setError()
                        viewModel.checkGroupName(subjectName, groupName.text.toString(),
                            groupDay, groupStart.text.toString(), groupEnd.text.toString())

                        if (viewModel.error.value == false) {
                            val group = Group(0, viewModel.groupName.value.toString(), subjectName, viewModel.groupDayOfWeek.value.toString(),
                                viewModel.groupStartHour.value.toString(), viewModel.groupEndHour.value.toString())

                            viewModel.addGroup(group)

                            groupName.text = null
                            groupStart.text = null
                            groupEnd.text = null
                            spinnerDay.setSelection(0)
                            spinnerSubject.setSelection(0)

                            Toast.makeText(context, "Grupa dodana pomyślnie", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else {
                        Toast.makeText(context, "Uzupełnij wszystkie pola", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        else {
            Toast.makeText(context, "Brak przedmiotów na liście", Toast.LENGTH_SHORT).show()
        }
    }
}