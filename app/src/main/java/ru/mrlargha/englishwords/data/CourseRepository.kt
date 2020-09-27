package ru.mrlargha.englishwords.data

class CourseRepository(private val courseDao: CourseDao) {

    companion object {
        @Volatile
        private var instance: CourseRepository? = null

        @JvmStatic
        fun getInstance(courseDao: CourseDao) =
            instance ?: synchronized(this) {
                instance ?: CourseRepository(courseDao).also { instance = it }
            }
    }

    fun getCourses() = courseDao.getCourses()
}