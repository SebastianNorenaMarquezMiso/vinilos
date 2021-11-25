package com.uniandes.vinilosapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.uniandes.vinilosapplication.R
import com.uniandes.vinilosapplication.data.model.AlbumCreateModel
import com.uniandes.vinilosapplication.databinding.AlbumCreateFragmentBinding
import com.uniandes.vinilosapplication.ui.viewmodel.AlbumCreateViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AlbumCreateFragment : Fragment() {
    private var _binding: AlbumCreateFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AlbumCreateViewModel

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        albumCreateTitle = requireView().findViewById<EditText>(R.id.albumCreateTitle)
        albumCreateCover = requireView().findViewById<EditText>(R.id.albumCreateCover)
        albumCreateDate = requireView().findViewById<EditText>(R.id.albumCreateDate)
        albumCreateDescription = requireView().findViewById<EditText>(R.id.albumCreateDescription)
        albumCreateGenre = requireView().findViewById<EditText>(R.id.albumCreateGenre)
        albumCreateRecordCompany =
            requireView().findViewById<EditText>(R.id.albumCreateRecordCompany)

        binding.albumCreateButton.setOnClickListener {
            if (validFields()) {
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

        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<String> { isNetworkError ->
                if (isNetworkError != "") onNetworkError()
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun validFields(): Boolean {
        if (albumCreateTitle!!.text.toString().trim() != "" &&
            albumCreateCover!!.text.toString().trim() != "" &&
            albumCreateDate!!.text.toString().trim() != "" &&
            albumCreateDescription!!.text.toString().trim() != "" &&
            albumCreateGenre!!.text.toString().trim() != "" &&
            albumCreateRecordCompany!!.text.toString().trim() != ""
        ) {
            return true;
        }

        Toast.makeText(activity, "No pueden haber campos vacios", Toast.LENGTH_LONG).show()
        return false;
    }

    private fun onNetworkSuccess() {
        val action = AlbumCreateFragmentDirections.actionAlbumCreateFragmentToAlbumFragment()

        // Navigate using that action
        requireView().findNavController().navigate(action)
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, viewModel.eventNetworkError.value, Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}