package com.mefalvarez.caritas_login_inicio

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.mefalvarez.caritas_login_inicio.databinding.FragmentSolicitanteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

//En este fragment es donde se desarrolla el envio de los correos
class Solicitante : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding: FragmentSolicitanteBinding? = null
    private val binding get() = _binding!!

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

        //Se muestran algunos de los departamentos que salen en el los editText
        val Departamentos = arrayOf(
            Departamento("Recepción", "recepcion@gmail.com"),
            Departamento("Solicitudes", "solicitudes@gmail.com"),
            Departamento("Donaciones", "donaciones@gmail.com")
        )

        //val adapter = getActivity()?.let { ArrayAdapter(it, android.R.layout.simple_expandable_list_item_1, Departamentos) }
        val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_expandable_list_item_1, Departamentos)

        binding.ACTextViewDepartamento?.setAdapter(adapter)
        binding.ACTextViewDepartamento?.threshold = 1 // Esto es para definir cuantas letras se deben de escribir antes de comenzar la búsqueda

        val motivos = resources.getStringArray(R.array.lista_motivos)
        val arrayAdapter = ArrayAdapter(requireActivity(), R.layout.selected_item, motivos)
        arrayAdapter.setDropDownViewResource(R.layout.dropdown_item)
        binding.spinner.adapter = arrayAdapter
        //binding.spinner.setOnItemSelectedListener(this);
        //binding.spinner.setSelection(motivos.size-1)
        //binding.spinner.setSelection(0,false)

        //La navegacion para ir al pop up que regresa al menu
        binding.solicitanteToMenu.setOnClickListener {
            findNavController().navigate(R.id.action_solicitante_to_menu)
        }

        //En este boton se ejecuta la funcion que envia los correos
        binding.buttonEnviar.setOnClickListener {
            if (binding.ACTextViewDepartamento.text.toString() != "" && binding.spinner.selectedItem.toString() != binding.spinner.getItemAtPosition(0)) {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        sendEmail()
                    }
                }
                findNavController().navigate(R.id.action_solicitante_to_popUp)
            } else if (binding.ACTextViewDepartamento.text.toString() == "" && binding.spinner.selectedItem.toString() != binding.spinner.getItemAtPosition(0)) {
                if (binding.spinner.selectedItem.toString() == "Visita" || binding.spinner.selectedItem.toString() == "Otro") {
                    binding.ACTextViewDepartamento.setText("Recepción")
                } else if (binding.spinner.selectedItem.toString() == "Donar") {
                    binding.ACTextViewDepartamento.setText("Donaciones")
                } else if (binding.spinner.selectedItem.toString() == "Solicitar") {
                    binding.ACTextViewDepartamento.setText("Solicitudes")
                }
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        sendEmail()
                    }
                }
                findNavController().navigate(R.id.action_solicitante_to_popUp)
            } else if (binding.spinner.selectedItem.toString() == binding.spinner.getItemAtPosition(0) && binding.ACTextViewDepartamento.text.toString() != "") {
                Toast.makeText(context, "Te faltó ingresar el motivo de tu visita", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Te faltó llenar alguno de los datos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    //Funcion que envia los correos
    private fun sendEmail() {
        try {
            val props = System.getProperties()
            val necesidad = view?.findViewById<AutoCompleteTextView>(R.id.spinner)

            props["mail.smtp.host"] = "smtp.gmail.com"
            props["mail.smtp.socketFactory.port"] = "465"
            props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory"
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.port"] = "465"

            val session = Session.getInstance(props,
                object : Authenticator() {
                    //Authenticating the password
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(
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
                Message.RecipientType.TO,
                InternetAddress(emailTo)
            )
            //Adding subject
            mm.subject = "Notificacion del solicitante"
            //Aqui se toma el texto del motivo para ser añadido como el texto del correo
            if (necesidad != null) {
                mm.setText(necesidad.getText().toString())
            }

            //Sending email

            Transport.send(mm)
        }
        catch (e: Exception) {
            Log.d("EMAIL",e.toString())
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}