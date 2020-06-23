package com.example.flightmobileapp

import android.content.SharedPreferences
import android.os.Bundle
import android.text.style.TtsSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.flightmobileapp.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.view.*
import org.w3c.dom.Text

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater,
        R.layout.fragment_login, container, false)

        binding.Connect.setOnClickListener {view : View ->
            val uri :  String = binding.URL.text.toString()
            SimulatorFragment.newInstance(uri)
            view.findNavController().navigate(R.id.action_loginFragment_to_simulatorFragment)
        }

        return binding.root
    }
}