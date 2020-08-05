package ru.mrlargha.englishwords.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.mrlargha.englishwords.data.Course
import ru.mrlargha.englishwords.databinding.CourseCardBinding
import ru.mrlargha.englishwords.ui.learn.LearnFragmentDirections

class CourseAdapter : ListAdapter<Course, CourseAdapter.CourseViewHolder>(CourseDiffCallback()) {

    var courses: List<Course> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        return CourseViewHolder(
            CourseCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = courses.size

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    class CourseViewHolder(private val binding: CourseCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Course) {
            binding.root.setOnClickListener {
                val action =
                    LearnFragmentDirections.actionLearnFragmentToLearnProcessFragment(course.courseId)
                binding.root.findNavController().navigate(action)
            }
            binding.apply {
                courseName.text = course.courseName
                description.text = course.courseDescription
                progress.progress = course.courseProgress
            }
        }
    }
}

private class CourseDiffCallback : DiffUtil.ItemCallback<Course>() {
    override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem.courseId == newItem.courseId
    }

    override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
        return oldItem == newItem
    }

}
