package com.mefalvarez.caritas_login_inicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mefalvarez.caritas_login_inicio.databinding.FragmentPopUpBinding

//Este es el pop up que sale al momento de darle al boton de enviar
class PopUp : Fragment() {
    private var _binding: FragmentPopUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPopUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    //En este boton te manda de nuevo al menu despues de cerrar el pop up
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.popUpToMenu.setOnClickListener {
            findNavController().navigate(R.id.action_popUp_to_menu)
        }
    }
}