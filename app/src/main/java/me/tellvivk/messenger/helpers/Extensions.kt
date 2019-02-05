import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import me.tellvivk.messenger.BuildConfig
import me.tellvivk.messenger.R
import me.tellvivk.messenger.app.base.BaseActivity
import java.io.File
import java.io.IOException

fun String.shareViaIntent(activity: AppCompatActivity) {
    val share = Intent(Intent.ACTION_SEND)
    share.type = "image/jpeg"
    val outputFile = File("${Environment.getExternalStorageDirectory()}${File.separator}temporary_file.jpg")
    if (!outputFile.exists()) {
        outputFile.createNewFile()
    }
    try {
        File(this).copyTo(outputFile, overwrite = true, bufferSize = 1024)
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(outputFile))
        activity.startActivity(Intent.createChooser(share, "Share Image"))
    } catch (e: IOException) {
        e.printStackTrace()
    }
}


fun BaseActivity.checkAllPermissions() {
    Dexter.withActivity(this)
        .withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                if (!report?.areAllPermissionsGranted()!!) {
                    showToast(resources.getString(R.string.str_grant_storage_permissions))
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {
                showToast(resources.getString(R.string.str_grant_storage_permissions))
            }
        })
        .check()
}

fun BaseActivity.dispatchTakePictureIntent(reqId: Int, photoFile: File?) {
    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
        // Ensure that there's a camera activity to handle the intent
        takePictureIntent.resolveActivity(packageManager)?.also {
            // Continue only if the File was successfully created
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    this,
                    "${BuildConfig.APPLICATION_ID}.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, reqId)
            }
        }
    }
}