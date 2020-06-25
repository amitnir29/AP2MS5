package com.example.flightmobileapp

import android.content.SharedPreferences
import android.os.Bundle
import android.text.style.TtsSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.flightmobileapp.databinding.FragmentLoginBinding
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.coroutines.*
import org.w3c.dom.Text

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private var databaseJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + databaseJob)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater,
        R.layout.fragment_login, container, false)

        binding.Connect.setOnClickListener {view : View ->
            val uri :  String = binding.URL.text.toString()

            view.findNavController().navigate(R.id.action_loginFragment_to_simulatorFragment)

            (host as MainActivity).uriChanged(uri)
        }

        val application = requireNotNull(this.activity).application
        val database = savedURLsDatabase.getInstance(application)

        uiScope.launch {
            readDataBase(binding, database)
        }

        return binding.root
        }

    override fun onDestroy() {
        super.onDestroy()
        databaseJob.cancel()
    }


    private suspend fun readDataBase(binding: FragmentLoginBinding, database: savedURLsDatabase) {
        withContext(Dispatchers.IO) {
            val savedURls: List<myURL> = database.urlDatabaseDao.getRelevant()

            val last = savedURls.size - 1

            for (i in 0..last) {
                val urlButton = (binding.URLButtons[i] as Button)

                urlButton.visibility = View.VISIBLE

                urlButton.text = savedURls[i].url
            }
        }
    }
}