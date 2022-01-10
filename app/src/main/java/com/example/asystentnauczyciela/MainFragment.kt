package com.example.asystentnauczyciela

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import androidx.navigation.findNavController

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if(resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            inflater.inflate(R.layout.fragment_main, container, false)
        } else {
            inflater.inflate(R.layout.fragment_main_horizontal, container, false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.manageButton).apply {
            setOnClickListener{
                it.findNavController().navigate(R.id.action_mainFragment_to_manageFragment)
            }
        }

        view.findViewById<Button>(R.id.selectButton).apply {
            setOnClickListener{
                it.findNavController().navigate(R.id.action_mainFragment_to_selectFragment)
            }
        }
    }
}