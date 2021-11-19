package com.mefalvarez.caritas_login_inicio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mefalvarez.caritas_login_inicio.databinding.FragmentLoginBinding
import com.mefalvarez.caritas_login_inicio.databinding.FragmentUserOptionsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserOptions.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserOptions : Fragment() {
    private var _binding: FragmentUserOptionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.userToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_userOptions_to_login)
        }

        binding.userToDashboard.setOnClickListener {
            findNavController().navigate(R.id.action_userOptions_to_dashboard)
        }
    }
}