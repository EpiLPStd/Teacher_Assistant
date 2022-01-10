package com.example.asystentnauczyciela.select


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.Communicator
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.subjectgroup.SubjectGroupFragment

class SelectFragment : Fragment(), SelectAdapter.OnItemClickListener {
    lateinit var viewModel: SelectViewModel
    lateinit var subjectsListAdapter: SelectAdapter
    private var communicator: Communicator?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        communicator = ViewModelProvider(requireActivity())[Communicator::class.java]

        val selectFactory = SelectViewModelFactory(requireNotNull(this.activity).application)
        viewModel = ViewModelProvider(requireActivity(), selectFactory)[SelectViewModel::class.java]
        subjectsListAdapter = SelectAdapter(viewModel.subjects, this)

        viewModel.subjects.observe(viewLifecycleOwner, { subjectsListAdapter.notifyDataSetChanged() })

        val layoutManager = LinearLayoutManager(view.context)
        view.findViewById<RecyclerView>(R.id.subjectsListView).let {
            it.adapter = subjectsListAdapter
            it.layoutManager = layoutManager
        }
    }

    override fun onItemClick(position: Int) {
        communicator!!.setSubjectName(viewModel.subjects.value?.get(position)?.subjectName.toString())

        findNavController().navigate(R.id.action_selectFragment_to_subjectGroupFragment)
    }
}
