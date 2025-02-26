package com.raival.fileexplorer.tab.file.misc

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.example.filemanager.R
import com.example.filemanager.util.FileMimeTypes
import java.io.File
import java.util.zip.ZipEntry

object IconHelper {
    /**
     * 根据文件类型为ImageView设置相应的图标。
     *
     * @param icon 用于显示图标的ImageView。
     * @param file 可以是File对象或ZipEntry对象，表示要显示图标的文件。
     */
    fun setFileIcon(icon: ImageView, file: Any) {
        // 检查文件是否为目录，如果是则加载文件夹图标
        if ((file is File && !file.isFile) || (file is ZipEntry && file.isDirectory)) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.ic_baseline_folder_24, icon.context))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }

        // 获取文件扩展名，如果是File对象则获取扩展名并转换为小写，否则为空字符串
        val ext: String = if (file is File) file.extension.lowercase()
        else ""

        // 如果是PDF文件，加载PDF图标
        if (ext == FileMimeTypes.pdfType) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.pdf_file_extension))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }
        // 如果是文本文件，加载文本图标
        if (FileMimeTypes.textType.contains(ext)) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.txt_file_extension))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }
        // 如果是Java文件，加载Java图标
        if (ext == FileMimeTypes.javaType) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.java_file_extension))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }
        // 如果是Kotlin文件，加载Kotlin图标
        if (ext == FileMimeTypes.kotlinType) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.kt_file_extension))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }
        // 如果是XML文件，加载XML图标
        if (ext == FileMimeTypes.xmlType) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.xml_file_extension))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }
        // 如果是代码文件，加载代码图标
        if (FileMimeTypes.codeType.contains(ext)) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.code_file_extension))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }
        // 如果是APK文件，加载APK图标
        if (ext == FileMimeTypes.apkType) {
            Glide.with(icon.context)
                .load(if (file is File) file.absolutePath else R.drawable.apk_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .error(R.drawable.apk_placeholder)
                .into(icon)
            return
        }
        // 如果是存档文件，加载存档图标
        if (FileMimeTypes.archiveType.contains(ext) || ext == FileMimeTypes.rarType) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.archive_file_extension))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }
        // 如果是视频文件，加载视频图标
        if (FileMimeTypes.videoType.contains(ext)) {
            if (file is File) {
                Glide.with(icon.context)
                    .load(file)
                    .signature(ObjectKey(file.lastModified()))
                    .error(R.drawable.video_file_extension)
                    .placeholder(R.drawable.video_file_extension)
                    .into(icon)
            } else {
                Glide.with(icon.context)
                    .load(R.drawable.video_file_extension)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(icon)
            }
            return
        }
        // 如果是音频文件，加载音频图标
        if (FileMimeTypes.audioType.contains(ext)) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.music_file_extension))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }

        // 如果是字体文件，加载字体图标
        if (FileMimeTypes.fontType.contains(ext)) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.font_file_extension))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }

        // 如果是SQL文件，加载SQL图标
        if (ext == FileMimeTypes.sqlType) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.sql_file_extension))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }

        // 如果是AI文件，加载矢量图标
        if (ext == FileMimeTypes.aiType) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.vector_file_extension))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }

        // 如果是SVG文件，加载SVG图标
        if (ext == FileMimeTypes.svgType) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.svg_file_extension))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }

        // 如果是图片文件，加载图片图标
        if (FileMimeTypes.imageType.contains(ext)) {
            if (file is File) {
                Glide.with(icon.context)
                    .applyDefaultRequestOptions(RequestOptions().override(100).encodeQuality(80))
                    .load(file)
                    .signature(ObjectKey(file.lastModified()))
                    .error(R.drawable.image_file_extension)
                    .into(icon)
            } else {
                Glide.with(icon.context)
                    .load(R.drawable.image_file_extension)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(icon)
            }
            return
        }

        // 如果是DOC或DOCX文件，加载DOC图标
        if (ext == FileMimeTypes.docType || ext == FileMimeTypes.docxType) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.doc_file_extension))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }

        // 如果是XLS或XLSX文件，加载XLS图标
        if (ext == FileMimeTypes.xlsType || ext == FileMimeTypes.xlsxType) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.xls_file_extension))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }

        // 如果是PPT或PPTX文件，加载PPT图标
        if (ext == FileMimeTypes.pptType || ext == FileMimeTypes.pptxType) {
            Glide.with(icon.context)
                .load(IconRes(R.drawable.powerpoint_file_extension))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(icon)
            return
        }

        // 如果是未知文件类型，加载未知图标
        Glide.with(icon.context)
            .load(IconRes(R.drawable.unknown_file_extension))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(icon)
    }

}

class IconRes(val resId: Int, val context: Context? = null)