package com.mefalvarez.caritas_login_inicio

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.mefalvarez.caritas_login_inicio.databinding.FragmentSolicitanteBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import javax.mail.*
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
//En este fragment es donde se desarrolla el envio de los correos
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

        //Se muestran algunos de los departamentos que salen en el los editText
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

        //La navegacion para ir al pop up que regresa al menu
        binding.solicitanteToMenu.setOnClickListener {
            findNavController().navigate(R.id.action_solicitante_to_menu)
        }

        //En este boton se ejecuta la funcion que envia los correos
        binding.buttonEnviar.setOnClickListener {
            val departamento = binding.ACTextViewDepartamento.text.toString()
            val necesidad = binding.editTextNecesidad.text.toString()
            val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    sendEmail()
                    altaRegistro(departamento,necesidad, currentDate)
                }
            }
            findNavController().navigate(R.id.action_solicitante_to_popUp)
        }
    }

    //Funcion que envia los datos la API
    fun altaRegistro(destino: String,comentarios: String, currentDate: String) {
        val queue = Volley.newRequestQueue(activity)

        val url = "http://192.168.1.124:10046/visit/add/"

        val jsonobj = JSONObject()
        jsonobj.put("department",destino)
        jsonobj.put("reason",comentarios)
        jsonobj.put("date", currentDate)

        val req = JsonObjectRequest(
            Request.Method.POST, url, jsonobj,
            {
                    response -> Log.d("API",response.toString())
            },

            {
                Log.d("API",it.toString())
            }
        )
        queue.add(req)
    }


    //Funcion que envia los correos
    private fun sendEmail() {
        try {
            val props = System.getProperties()
            val necesidad = view?.findViewById<EditText>(R.id.editText_Necesidad)

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
}