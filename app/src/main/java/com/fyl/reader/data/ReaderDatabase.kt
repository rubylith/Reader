package com.fyl.reader.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.*
import com.fyl.reader.utils.LogUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val KEY_USERS = "users"
const val USERS = "Users.json"

/**
 * The room database for this app
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class ReaderDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private const val TAG = "ReaderDatabase"
        private const val DATABASE_NAME = "ReaderDatabase"
        @Volatile private var instance: ReaderDatabase? = null

        fun getInstance(context: Context): ReaderDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): ReaderDatabase {
            LogUtils.d(TAG, "buildDatabase()")
            return Room.databaseBuilder(context, ReaderDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<ReaderDatabaseWorker>()
                                    .setInputData(workDataOf(KEY_USERS to USERS))
                                    .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                )
                .build()
        }
    }
}

class ReaderDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val result = runCatching {
            inputData.getString(KEY_USERS)?.takeIf { it.isNotEmpty() }?.let { filename ->
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val userType = object : TypeToken<List<User>>() {}.type
                        val userList: List<User> = Gson().fromJson(jsonReader, userType)

                        val database = ReaderDatabase.getInstance(applicationContext)
                        database.userDao().insertAll(userList)
                    }
                }
            }
        }
        if (result.isSuccess) Result.success() else Result.failure()
    }
}
