package com.example.notecategories20.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notecategories20.Note.CategoryClass
import com.example.notecategories20.databinding.CategoryItemListBinding

class RvCategoryAdaper(private val CategoryList: ArrayList<CategoryClass>) : RecyclerView.Adapter<RvCategoryAdaper.ViewHolder>(){

    class ViewHolder (val binding : CategoryItemListBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            (CategoryItemListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
        )
    }

    override fun getItemCount(): Int {
        return CategoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = CategoryList[position]
        holder.apply{
            binding.apply{
                textNameCategory.text = currentItem.name
                blockCategoryListItem.setOnLongClickListener {
                    true
                }
            }
        }
    }
}