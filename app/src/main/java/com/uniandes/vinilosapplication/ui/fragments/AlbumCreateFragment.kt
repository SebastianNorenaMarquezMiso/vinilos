package com.uniandes.vinilosapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uniandes.vinilosapplication.R
import com.uniandes.vinilosapplication.data.model.AlbumCreateModel
import com.uniandes.vinilosapplication.databinding.AlbumCreateFragmentBinding
import com.uniandes.vinilosapplication.ui.adapters.AlbumCreateAdapter
import com.uniandes.vinilosapplication.ui.viewmodel.AlbumCreateViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AlbumCreateFragment : Fragment() {
    private var _binding: AlbumCreateFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumCreateViewModel
    private var viewModelAdapter: AlbumCreateAdapter? = null

    private var albumCreateTitle: EditText? = null
    private var albumCreateCover: EditText? = null
    private var albumCreateDate: EditText? = null
    private var albumCreateDescription: EditText? = null
    private var albumCreateGenre: EditText? = null
    private var albumCreateRecordCompany: EditText? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AlbumCreateFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = AlbumCreateAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.fragmentsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter

        albumCreateTitle = requireView().findViewById<EditText>(R.id.albumCreateTitle)
        albumCreateCover = requireView().findViewById<EditText>(R.id.albumCreateCover)
        albumCreateDate = requireView().findViewById<EditText>(R.id.albumCreateDate)
        albumCreateDescription = requireView().findViewById<EditText>(R.id.albumCreateDescription)
        albumCreateGenre = requireView().findViewById<EditText>(R.id.albumCreateGenre)
        albumCreateRecordCompany =
            requireView().findViewById<EditText>(R.id.albumCreateRecordCompany)

        binding.albumCreateButton.setOnClickListener {
            viewModel.postDataToNetwork(
                AlbumCreateModel(
                    name = albumCreateTitle!!.text.toString(),
                    cover = albumCreateCover!!.text.toString(),
                    releaseDate = albumCreateDate!!.text.toString(),
                    description = albumCreateDescription!!.text.toString(),
                    genre = albumCreateGenre!!.text.toString(),
                    recordLabel = albumCreateRecordCompany!!.text.toString()
                )
            ) {
                onNetworkSuccess()
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_album_create)
        viewModel = ViewModelProvider(
            this,
            AlbumCreateViewModel.Factory(activity.application)
        ).get(AlbumCreateViewModel::class.java)
        viewModel.albumCreate.observe(viewLifecycleOwner, Observer<AlbumCreateModel> {
            it.apply {
                /*requireView().findViewById<ProgressBar>(R.id.progressBar).visibility=View.VISIBLE
                viewModelAdapter!!.album_create = this
                requireView().findViewById<ProgressBar>(R.id.progressBar).visibility=View.INVISIBLE*/
            }
        })
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<String> { isNetworkError ->
                if (isNetworkError != "") onNetworkError()
            })
    }

    fun onNetworkSuccess() {
        val action = AlbumCreateFragmentDirections.actionAlbumCreateFragmentToAlbumFragment()

        // Navigate using that action
        requireView().findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onNetworkError() {
        Log.d("333333", "enrtoooooooo")

        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, viewModel.eventNetworkError.value, Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}