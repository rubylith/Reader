package com.fyl.reader.file.explorer

import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class FileExplorerViewModel @Inject constructor(
    private val fileExplorerRepository: FileExplorerRepository
) : ViewModel() {

    var filesList: MutableLiveData<List<File>> = MutableLiveData<List<File>>()
    var currentDirectory: File? = null
    private var getFilesListJob: Job? = null
    val topDirectory: File? = Environment.getExternalStorageDirectory()

    fun open(selectedItem: File) {
        if (selectedItem.isFile) {
            return fileExplorerRepository.openFile(selectedItem)
        }
        currentDirectory = selectedItem

        getFilesListJob?.cancel()
        getFilesListJob = viewModelScope.launch {
            filesList.value = fileExplorerRepository.getFilesList(currentDirectory!!)
        }
    }

    fun isInTopDirectory(): Boolean {
        return currentDirectory == topDirectory
    }
}