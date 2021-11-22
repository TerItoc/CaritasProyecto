package com.mefalvarez.caritas_login_inicio

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mefalvarez.caritas_login_inicio.databinding.FragmentSolicitanteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Solicitante.newInstance] factory method to
 * create an instance of this fragment.
 */
class Solicitante : Fragment() {
    private var _binding: FragmentSolicitanteBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSolicitanteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val Departamentos = arrayOf(
            Departamento("Departamento 1", "departamento1@email.com"),
            Departamento("Solicitudes", "departamento2@email.com"),
            Departamento("Donaciones", "departamento3@email.com"),
            Departamento("Banco de Alimentos", "bancodealimentos@email.com"),
            Departamento("Juan Lopez", "juanlopez@email.com"),
            Departamento("Manuel Gonzalez", "manuelgonzalez@email.com"),
            Departamento("Javier Alvarez", "javieralvarez@email.com"),
            Departamento("Mario Gutierrez", "mariogutierrez@email.com")
        )

        //val adapter = getActivity()?.let { ArrayAdapter(it, android.R.layout.simple_expandable_list_item_1, Departamentos) }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_expandable_list_item_1, Departamentos)

        binding.ACTextViewDepartamento?.setAdapter(adapter)
        binding.ACTextViewDepartamento?.threshold = 1 // Esto es para definir cuantas letras se deben de escribir antes de comenzar la búsqueda

        binding.solicitanteToMenu.setOnClickListener {
            findNavController().navigate(R.id.action_solicitante_to_menu)
        }

        binding.buttonEnviar.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    sendEmail()
                }
            }
            findNavController().navigate(R.id.action_solicitante_to_popUp)
        }
    }

    private fun sendEmail() {
        try {

            val props = System.getProperties()

            props["mail.smtp.host"] = "smtp.gmail.com"
            props["mail.smtp.socketFactory.port"] = "465"
            props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.port"] = "465"

            val session = Session.getInstance(props,
                object : javax.mail.Authenticator() {
                    //Authenticating the password
                    override fun getPasswordAuthentication(): javax.mail.PasswordAuthentication {
                        return javax.mail.PasswordAuthentication(
                            Credentials1.EMAIL,
                            Credentials1.PASSWORD
                        )
                    }
                })

            //Creating MimeMessage object
            val mm = MimeMessage(session)
            // Destination Email
            val emailTo = "karitas6942@gmail.com"

            //Adding receiver
            mm.addRecipient(
                javax.mail.Message.RecipientType.TO,
                InternetAddress(emailTo)
            )
            //Adding subject
            mm.subject = "Prueba de envio de correo"
            //Adding message
            mm.setText("Esto es una prueba.")

            //Sending email

            Transport.send(mm)
        }
        catch (e: Exception) {
            Log.d("EMAIL",e.toString())
        }
    }
}