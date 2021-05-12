package com.example.productly.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*

import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import com.example.productly.R
import com.example.productly.data.viewmodel.ToDoViewModel
import com.example.productly.databinding.FragmentListBinding
import com.example.productly.fragments.SharedViewModel
import com.example.productly.fragments.list.adapter.ListAdapter
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator


class ListFragment : Fragment(), SearchView.OnQueryTextListener{
        private val  mToDoViewModel: ToDoViewModel by viewModels()
        private val adapter: ListAdapter by  lazy { ListAdapter() }
        private var _binding: FragmentListBinding? = null
        private val  binding get() = _binding!!
        private  val mShareViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Data Binding

      _binding = FragmentListBinding.inflate(inflater,container,false)
        binding.lifecycleOwner = this
        binding.mSharedViewModel = mShareViewModel
        //Setup Recycler View
        setupRecyclerView()

            // Observer Live Data
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer {   data ->
            mShareViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })



        // Set Menu
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 100
        }

        // Swipe to Delete set up
        swipeToDelete(recyclerView)
    }

    private  fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallBack = object : SwiperToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.dataList[viewHolder.adapterPosition]
                mToDoViewModel.deleteItem(itemToDelete)
                Toast.makeText(requireContext(),"'${itemToDelete.title}' Was Removed ",Toast.LENGTH_SHORT).show()
            }
        }
         val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
                inflater.inflate(R.menu.nav_drawer_menu_todo,menu)

        val search =  menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete_all){
            confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
       if(query !=null){
           searchThroughDatabase(query)
       }
        return true
    }


    override fun onQueryTextChange(query: String?): Boolean {
       if (query !=null){
           searchThroughDatabase(query)
       }
        return true
    }


    private fun searchThroughDatabase(query: String) {
        val searchQuery = "%$query%"

        mToDoViewModel.searchDatabase(searchQuery).observe(viewLifecycleOwner, { list ->
            list?.let {
                Log.d("ListFragment", "searchThroughDatabase")
                adapter.setData(it)
            }
        })
    }

    // Show Alert Remove all database
    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes"){_, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(requireContext(), "SuccessFully Removed", Toast.LENGTH_SHORT).show()

        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete?")
        builder.setMessage("Are you sure want to delete everything ?")
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
