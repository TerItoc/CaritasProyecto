package com.mefalvarez.caritas_login_inicio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mefalvarez.caritas_login_inicio.databinding.FragmentLoginBinding

//Este es el fragment en el que se desarolla el login
class Login : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Aqui se ejecutan la navegacion entre las pantallas en las que se conecta el login
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginToMenu.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_menu)
        }

        binding.loginToDashboard.setOnClickListener {
            if (binding.editTextTextEmailAddress.text.toString() == "karitas6942@gmail.com" && binding.editTextTextPassword.text.toString() == "admin") {
                findNavController().navigate(R.id.action_login_to_dashboard)
            } else if (binding.editTextTextEmailAddress.text.toString() != "karitas6942@gmail.com" && binding.editTextTextPassword.text.toString() != "") {
                Toast.makeText(context, "El correo ingresado es incorrecto", Toast.LENGTH_SHORT).show()
            } else if (binding.editTextTextPassword.text.toString() != "admin" && binding.editTextTextEmailAddress.text.toString() != "") {
                Toast.makeText(context, "La contraseña ingresada es incorrecta", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Te faltó ingresar alguno de los datos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}