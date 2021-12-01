package com.mefalvarez.caritas_login_inicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mefalvarez.caritas_login_inicio.databinding.FragmentMenuBinding

//La pantalla del menu en el que se inicia la actividad
class Menu : Fragment() {
    private var _binding: FragmentMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Los botones que te llevan a la pantalla del solicitante
    //o a la pantalla del login
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.botonConfig.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_login)
        }

        binding.botonEmpezar.setOnClickListener {
            findNavController().navigate(R.id.action_menu_to_solicitante)
        }
    }
}