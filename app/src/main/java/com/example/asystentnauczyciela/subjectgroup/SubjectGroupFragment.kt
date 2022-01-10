package com.example.asystentnauczyciela.subjectgroup

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.Communicator
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.entities.Group
import com.example.asystentnauczyciela.entities.SubjectGroups
import com.example.asystentnauczyciela.select.SelectAdapter
import kotlinx.coroutines.*

class SubjectGroupFragment : Fragment(), SelectAdapter.OnItemClickListener {
    lateinit var viewModel: SubjectGroupViewModel
    private lateinit var subjectGroupAdapter: SubjectGroupAdapter
    private var communicator: Communicator ?= null
    lateinit var groups: LiveData<List<SubjectGroups>>
    private var selectedSubject = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_subject_group, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
    }

    private fun initialize() = runBlocking {
        val scope = CoroutineScope(Dispatchers.Main + Job())

        scope.launch { getSubjectName() }
        scope.launch { createGroupsList() }
    }

    private fun getSubjectName() {
        communicator = ViewModelProvider(requireActivity())[Communicator::class.java]

        communicator!!.SubjectName.observe(viewLifecycleOwner, { o -> selectedSubject = o.toString() })
    }

    @SuppressLint("NotifyDataSetChanged")
    fun createGroupsList() {
        val subjectGroupFactory = SubjectGroupViewModelFactory(requireNotNull(this.activity).application)
        viewModel = ViewModelProvider(requireActivity(), subjectGroupFactory)[SubjectGroupViewModel::class.java]
        groups = viewModel.getSubjectGroups(selectedSubject)

        subjectGroupAdapter = SubjectGroupAdapter(groups, this)

        groups.observe(viewLifecycleOwner, { subjectGroupAdapter.notifyDataSetChanged() })

        val layoutManager = LinearLayoutManager(view?.context)

        view?.findViewById<RecyclerView>(R.id.groupsListView).let {
            it?.adapter = subjectGroupAdapter
            it?.layoutManager = layoutManager
        }
    }

    override fun onItemClick(position: Int) {
        communicator!!.setGroupName(groups.value?.get(position)?.groups?.get(position)?.groupName.toString())

        findNavController().navigate(R.id.action_subjectGroupFragment_to_studentSubjectFragment)
    }
}