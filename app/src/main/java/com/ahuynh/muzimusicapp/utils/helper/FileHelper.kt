package com.ahuynh.muzimusicapp.utils.helper

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

object FileHelper {

    private val EOF = -1
    private val DEFAULT_BUFFER_SIZE = 1024 * 4

    //Create file by uri
    fun from(context: Context, uri: Uri): File? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val fileName = getFileName(context, uri)
            val splitName = splitFileName(fileName)
            var tempFile: File = File.createTempFile(splitName[0], splitName[1])
            tempFile = rename(tempFile, fileName)
            tempFile.deleteOnExit()
            var out: FileOutputStream? = null
            try {
                out = FileOutputStream(tempFile)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            if (inputStream != null) {
                if (out != null) {
                    copy(inputStream, out)
                }
                inputStream.close()
            }
            out?.close()
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun splitFileName(fileName: String): Array<String> {
        var name = fileName
        var extension = ""
        val i = fileName.lastIndexOf(".")
        if (i != -1) {
            name = fileName.substring(0, i)
            extension = fileName.substring(i)
        }
        return arrayOf(name, extension)
    }

    @SuppressLint("Range")
    private fun getFileName(context: Context, uri: Uri): String {
        var result: String? = null
        if (uri.scheme.equals("content")) {
            val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
            try {
                cursor?.let {
                    it.moveToFirst()
                    result = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                cursor?.close()
            }
        }
        if (result == null) {
            result = uri.path
            val cut: Int = result!!.lastIndexOf(File.separator)
            if (cut != -1) {
                result = result!!.substring(cut + 1)
            }
        }
        return result!!
    }

    private fun rename(file: File, newName: String): File {
        val newFile = File(file.parent, newName)
        if (newFile != file) {
            if (newFile.exists() && newFile.delete()) {
                Log.d("FileUtil", "Delete old $newName file")
            }
            if (file.renameTo(newFile)) {
                Log.d("FileUtil", "Rename file to $newName")
            }
        }
        return newFile
    }

    @Throws(IOException::class)
    private fun copy(input: InputStream, output: OutputStream): Long {
        var count: Long = 0
        var n: Int
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        while (EOF != input.read(buffer).also { n = it }) {
            output.write(buffer, 0, n)
            count += n.toLong()
        }
        return count
    }

    fun getDefaultImageFile(context: Context, drawableResId: Int): File? {
        val inputStream = context.resources.openRawResource(drawableResId)
        val file = File(context.cacheDir, "default_image")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()
        return file
    }
}