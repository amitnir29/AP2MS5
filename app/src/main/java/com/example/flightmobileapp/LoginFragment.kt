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

        var databaseSize : Int? = null
        uiScope.launch {
            databaseSize = readDataBase(binding, database)
        }

        binding.Connect.setOnClickListener {view : View ->
            startSimulator(binding, database, view, databaseSize)
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
     * @param databaseSize the size of the database before connecting.
     */
    private fun startSimulator(binding: FragmentLoginBinding, database: SavedURLsDatabase,
    view: View, databaseSize: Int?) {
        // Get the uri inserted by the user.
        val uri :  String = binding.URL.text.toString()

        uiScope.launch {
            withContext(Dispatchers.IO) {
                if (database.urlDatabaseDao.getByURL(uri) == null) {
                    // Save the uri to the database for future usage.
                    val url = myURL(url = uri)
                    database.urlDatabaseDao.insert(url)

                    /* If database already contains more than the required elements,
                     * remove the oldest*/
                    if (databaseSize == 5)
                        database.urlDatabaseDao.removeOldest()
                }
                else {
                    database.urlDatabaseDao.setLastUsed(uri)
                }
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
     * @return the size of the database.
     */
    private suspend fun readDataBase(binding: FragmentLoginBinding, database: SavedURLsDatabase):
            Int? {

        // Read the most 5 relevant urls from the database.
        var savedURLs: List<myURL>?
        withContext(Dispatchers.IO) {
            savedURLs = database.urlDatabaseDao.getRelevant()
        }

        if (savedURLs == null)
            return null

        // Declare last element to iterate over.
        val last = savedURLs!!.size - 1

        // Iterate over all the relevant urls.
        for (i in 0..last) {
            // Get the corresponding button.
            val urlButton = (binding.URLButtons[i] as Button)

            // Set it as visible.
            urlButton.visibility = View.VISIBLE

            // Change the button text to the url.
            urlButton.text = savedURLs!![i].url

            // By clicking on the button the text box should show the corresponding url.
            urlButton.setOnClickListener {
                binding.URL.setText(savedURLs!![i].url)
            }
        }

        return savedURLs!!.size
    }
}