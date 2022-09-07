package com.fyl.reader.file.explorer

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.webkit.MimeTypeMap
import androidx.core.content.FileProvider
import com.fyl.reader.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileExplorerRepository @Inject constructor(
    @ApplicationContext val context: Context
) {

    private val authority = "${BuildConfig.APPLICATION_ID}.provider"

    fun openFile(selectedItem: File) {
        // Get URI and MIME type of file
        val uri = FileProvider.getUriForFile(context.applicationContext, authority, selectedItem)
        val mime: String = getMimeType(uri.toString())

        // Open file with user selected app
        val intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.setDataAndType(uri, mime)
        return context.startActivity(intent)
    }

    private fun getMimeType(url: String): String {
        val ext = MimeTypeMap.getFileExtensionFromUrl(url)
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext) ?: "text/plain"
    }

    suspend fun getFilesList(selectedItem: File): List<File> = withContext(Dispatchers.IO) {
        val rawFilesList = selectedItem.listFiles()?.filter { !it.isHidden }

        if (selectedItem == Environment.getExternalStorageDirectory()) {
            rawFilesList?.toList() ?: listOf()
        } else {
            listOf(selectedItem.parentFile) + (rawFilesList?.toList() ?: listOf())
        }
    }
}