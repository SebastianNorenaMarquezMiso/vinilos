package com.uniandes.vinilosapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.uniandes.vinilosapplication.R
import com.uniandes.vinilosapplication.data.model.CollectorModel
import com.uniandes.vinilosapplication.databinding.CollectorDetailFragmentBinding
import com.uniandes.vinilosapplication.ui.adapters.CollectorDetailAdapter
import com.uniandes.vinilosapplication.ui.viewmodel.CollectorDetailViewModel


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CollectorDetailFragment : Fragment() {
    private var _binding: CollectorDetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: CollectorDetailViewModel
    private var viewModelAdapter: CollectorDetailAdapter? = null
    private val picasso = Picasso.get()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CollectorDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModelAdapter = CollectorDetailAdapter()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.fragmentsRv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = viewModelAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        activity.actionBar?.title = getString(R.string.title_collectors)
        val args: CollectorDetailFragmentArgs by navArgs()
        viewModel = ViewModelProvider(
            this,
            CollectorDetailViewModel.Factory(activity.application, args.collectorId)
        ).get(CollectorDetailViewModel::class.java)
        viewModel.collectorDetail.observe(viewLifecycleOwner, Observer<CollectorModel> {
            it.apply {
                binding.collector = this
                requireView().findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
                viewModelAdapter!!.albums = this.collectorAlbums!!
                requireView().findViewById<ProgressBar>(R.id.progressBar).visibility =
                    View.INVISIBLE
            }
        })
        viewModel.eventNetworkError.observe(
            viewLifecycleOwner,
            Observer<Boolean> { isNetworkError ->
                if (isNetworkError) onNetworkError()
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*@BindingAdapter("imageUrl")
    fun setImageUrl(view: ImageView, path: String) {
        picasso.load(path).into(view)
    }*/

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}
