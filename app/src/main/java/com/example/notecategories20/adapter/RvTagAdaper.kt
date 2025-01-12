package com.example.notecategories20.adapter

import android.nfc.Tag
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notecategories20.Note.Tag.TagCategory
import com.example.notecategories20.categories_home_fragment
import com.example.notecategories20.databinding.CategoryItemListBinding


class RvTagAdaper(private  val TagList : java.util.ArrayList<TagCategory>) : RecyclerView.Adapter<RvTagAdaper.ViewHolder>(){

    class ViewHolder (val binding : CategoryItemListBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder((CategoryItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)))
    }

    override fun getItemCount(): Int {
        return TagList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = TagList[position]
        holder.apply{
            binding.apply{
                textNameCategory.text = currentItem.name

                horiRecycleViewTag.setOnClickListener {


                }
            }
        }
    }
}