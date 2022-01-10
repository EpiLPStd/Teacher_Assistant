package com.example.asystentnauczyciela.manage

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.example.asystentnauczyciela.R


class ManageFragment : Fragment() {
    lateinit var viewModel: ManageFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        return if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.fragment_manage, container, false)
        }
        else {
            inflater.inflate(R.layout.fragment_manage_horizontal, container, false)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.manage_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel = ViewModelProvider(requireActivity())[ManageFragmentViewModel::class.java]

        val builder = AlertDialog.Builder(context)
        builder.setMessage("Czy na pewno chcesz usunąć bezpowrotnie wszystkie dane aplikacji?")
        builder.setTitle("Uwaga")
        builder.setCancelable(false)
        builder.setNegativeButton("NIE") { dialog, _ -> dialog.cancel() }
        builder.setPositiveButton("TAK") { _, _ -> viewModel.terminate() }

        val alert = builder.create()
        alert.show()

        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.addSubjectButton).apply {
            setOnClickListener{
                it.findNavController().navigate(R.id.action_manageFragment_to_manageSubjectsFragment)
            }
        }

        view.findViewById<Button>(R.id.addGroupButton).apply {
            setOnClickListener{
                it.findNavController().navigate(R.id.action_manageFragment_to_manageGroupsFragment)
            }
        }

        view.findViewById<Button>(R.id.addStudentButton).apply {
            setOnClickListener{
                it.findNavController().navigate(R.id.action_manageFragment_to_manageStudentsFragment)
            }
        }

        view.findViewById<Button>(R.id.addStudentClassButton).apply {
            setOnClickListener{
                it.findNavController().navigate(R.id.action_manageFragment_to_studentClassFragment)
            }
        }
    }
}
