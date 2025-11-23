package com.gg.aireader.data.repo

import android.graphics.Bitmap
import com.gg.aireader.ktor.GroqApi
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class PdfRepo @Inject constructor(private val groqApi: GroqApi){

    suspend fun extractTextFromBitmap(bitmap: Bitmap): String =
        suspendCoroutine { continuation ->
            val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
            val image = InputImage.fromBitmap(bitmap, 0)

            recognizer.process(image)
                .addOnSuccessListener { visionText -> continuation.resume(visionText.text) }
                .addOnFailureListener { exception ->  continuation.resumeWithException(exception) }
        }



    suspend fun analyzeMood(pageText: String): String{
        return groqApi.classifyMood(pageText)
    }

}