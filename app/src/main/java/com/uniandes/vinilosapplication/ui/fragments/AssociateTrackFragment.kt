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
import androidx.navigation.fragment.navArgs
import com.uniandes.vinilosapplication.R
import com.uniandes.vinilosapplication.data.model.TrackModel
import com.uniandes.vinilosapplication.databinding.AssociateTrackFragmentBinding
import com.uniandes.vinilosapplication.ui.viewmodel.AssociateTrackViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AssociateTrackFragment : Fragment() {
    private val args: AssociateTrackFragmentArgs by navArgs()
    private var _binding: AssociateTrackFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: AssociateTrackViewModel

    private var associateTrackName: EditText? = null
    private var associateTrackDuration: EditText? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AssociateTrackFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        associateTrackName = requireView().findViewById<EditText>(R.id.associateTrackName)
        associateTrackDuration = requireView().findViewById<EditText>(R.id.associateTrackDuration)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_associate_track)
        binding.associateTrackButton.setOnClickListener {
            if (validFields()) {
                viewModel.postDataToNetwork(
                    args.albumId,
                    TrackModel(
                        name = associateTrackName!!.text.toString(),
                        duration = associateTrackDuration!!.text.toString()
                    )
                ) {
                    onNetworkSuccess()
                }
            }
        }
        viewModel = ViewModelProvider(
            this,
            AssociateTrackViewModel.Factory(activity.application)
        ).get(AssociateTrackViewModel::class.java)

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
        if (associateTrackName!!.text.toString().trim() != "" &&
            associateTrackDuration!!.text.toString().trim() != ""
        ) {
            return true
        }

        Toast.makeText(activity, "No pueden haber campos vacios", Toast.LENGTH_LONG).show()
        return false
    }

    private fun onNetworkSuccess() {
        val action =
            AssociateTrackFragmentDirections.actionAssociateTrackFragmentToAlbumDetailFragment(args.albumId)

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