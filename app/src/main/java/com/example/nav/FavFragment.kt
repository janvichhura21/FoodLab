package com.example.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.nav.adapter.FavListAdapter
import com.example.nav.databinding.FragmentFavBinding
import com.example.nav.db.MealDatabase
import com.example.nav.viewmodel.MainViewModel
import com.example.nav.viewmodel.MainViewModelFactory
import com.google.android.material.snackbar.Snackbar

class FavFragment : Fragment() {
    lateinit var binding: FragmentFavBinding
    lateinit var viewModel: MainViewModel
    lateinit var favListAdapter: FavListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    //    viewModel=(activity as MainActivity).viewModel
        val mealdb= MealDatabase.getbdinstance(requireContext())
        val mainviemodelfactory= MainViewModelFactory(mealdb)
        viewModel=ViewModelProvider(this,mainviemodelfactory)[MainViewModel::class.java]

        favListAdapter= FavListAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentFavBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observefavorites()
        setuprv()
        val itemTouchHelper=object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN
        , ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position=viewHolder.adapterPosition
                viewModel.delete(favListAdapter.differ.currentList[position])
                Snackbar.make(requireView(),"Meals are deleted",Snackbar.LENGTH_LONG)
                    .setAction("Undo"
                        ,View.OnClickListener {
                            Toast.makeText(requireContext(), "insert items", Toast.LENGTH_SHORT).show()
                        }
                    ).show()
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favrecyclerview)
    }

    private fun setuprv() {
        binding.favrecyclerview.apply {
            layoutManager=GridLayoutManager(activity,2,GridLayoutManager.VERTICAL,false)
            adapter=favListAdapter
        }
    }

    private fun observefavorites() {
        viewModel.observefavmealdblivedata().observe(requireActivity(), Observer { meals->
            favListAdapter.differ.submitList(meals)
        })
    }

}