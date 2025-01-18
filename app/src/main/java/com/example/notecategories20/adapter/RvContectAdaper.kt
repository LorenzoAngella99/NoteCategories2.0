package com.example.notecategories20.adapter

import android.view.ActionMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notecategories20.Note.Note
import com.example.notecategories20.databinding.NoteItemListBinding
import com.example.notecategories20.homeFragmentDirections
import java.util.Calendar

class RvContactsAdapter(private  val contactList : java.util.ArrayList<Note>) : RecyclerView.Adapter<RvContactsAdapter.ViewHolder>(){


    class ViewHolder (val binding : NoteItemListBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder((NoteItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)))
    }

    override fun getItemCount(): Int {
       return contactList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = contactList[position]
        holder.apply{
            binding.apply{
                textTitle.text = currentItem.title
                textDate.text = currentItem.date
                rvReload.setOnClickListener{
                   val action = homeFragmentDirections.actionHomeFragmentToUpdateFragment(
                       currentItem.title.toString(),
                       currentItem.content.toString(),
                       currentItem.id.toString(),
                       currentItem.date.toString()
                   )
                    findNavController(holder.itemView).navigate(action)
                }
                rvReload.setOnLongClickListener {
                    TODO("inserire la cancellazione singola dei elementi della lista")



                    true
                }

            }
        }
    }






}