package com.example.flightmobileapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.flightmobileapp.databinding.FragmentLoginBinding
import kotlinx.coroutines.*

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

        val application = requireNotNull(this.activity).application
        val database = SavedURLsDatabase.getInstance(application)

        uiScope.launch {
            readDataBase(binding, database)
        }

        binding.Connect.setOnClickListener {view : View ->
            startSimulator(binding, database, view)
        }

        return binding.root
        }

    override fun onDestroy() {
        super.onDestroy()
        databaseJob.cancel()
    }


    /**
     * Move to the simulator frame.
     * @param binding the binding object of the frame.
     * @param database the database of the urls.
     * @param view the view of the simulator, to move to.
     */
    private fun startSimulator(binding: FragmentLoginBinding, database: SavedURLsDatabase,
    view: View) {
        // Get the uri inserted by the user.
        val uri :  String = binding.URL.text.toString()

        uiScope.launch {
            withContext(Dispatchers.IO) {
                database.uriConnected(uri)
            }
        }

        // Clean the text box.
        binding.URL.setText("")

        // Move to the simulator frame.
        val bundle = bundleOf("url" to uri)
        view.findNavController().navigate(R.id.action_loginFragment_to_simulatorFragment, bundle)
    }


    /**
     * Read elements from the database to the appropriate buttons.
     * @param binding the binding object.
     * @param database the database of the urls.
     */
    private suspend fun readDataBase(binding: FragmentLoginBinding, database: SavedURLsDatabase) {

        // Read the most 5 relevant urls from the database.
        var savedURLs: List<String>
        withContext(Dispatchers.IO) {
            savedURLs = database.getRelevants()
        }

        // Declare last element to iterate over.
        val last = savedURLs.size - 1

        // Iterate over all the relevant urls.
        for (i in 0..last) {
            // Get the corresponding button.
            val urlButton = (binding.URLButtons[i] as Button)

            // Set it as visible.
            urlButton.visibility = View.VISIBLE

            // Change the button text to the url.
            urlButton.text = savedURLs[i]

            // By clicking on the button the text box should show the corresponding url.
            urlButton.setOnClickListener {
                binding.URL.setText(savedURLs[i])
            }
        }
    }
}