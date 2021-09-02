package io.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import io.scanbot.sdk.ScanbotSDK
import io.scanbot.sdk.persistence.Page
import io.scanbot.sdk.persistence.PageFileStorage
import io.scanbot.sdk.ui.view.camera.DocumentScannerActivity
import io.scanbot.sdk.ui.view.camera.configuration.DocumentScannerConfiguration

class MainActivity : AppCompatActivity() {
    private lateinit var pageFileStorage: PageFileStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.start_scanner_button).setOnClickListener {
            startDocument()
        }

        pageFileStorage = ScanbotSDK(this).createPageFileStorage()
    }

    private fun startDocument() {
        val configuration = DocumentScannerConfiguration()
        configuration.setTopBarBackgroundColor(Color.RED)
        val docIntent = DocumentScannerActivity.newIntent(this, configuration)
        startActivityForResult(docIntent, DOCUMENT_SCANNER_ACTIVITY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DOCUMENT_SCANNER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            val snappedPagesExtra = data?.getParcelableArrayExtra(DocumentScannerActivity.SNAPPED_PAGE_EXTRA)
            snappedPagesExtra?.get(0)?.let { page ->
                processPagePreview(page as Page)
            }
        }
    }

    private fun processPagePreview(page: Page) {
        val imageType = PageFileStorage.PageFileType.DOCUMENT
        val filteredPreviewImage = pageFileStorage.getImage(page.pageId, imageType)
        val previewWidget = findViewById<ImageView>(R.id.page_preview)
        previewWidget.setImageBitmap(filteredPreviewImage)
    }

    companion object {
        const val DOCUMENT_SCANNER_ACTIVITY_REQUEST_CODE = 100
    }
}