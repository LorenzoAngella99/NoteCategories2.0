package com.example.notecategories20.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notecategories20.Note.Note
import com.example.notecategories20.databinding.NoteItemListBinding

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
                //textDate.text = currentItem.date.toString()

                rvReload.setOnClickListener{


                }
                rvReload.setOnLongClickListener {


                    true
                }
            }

        }

    }





}