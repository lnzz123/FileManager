package com.example.filemanager.util


/**
 * 该对象类用于定义文件的MIME类型相关常量和映射关系。
 */
object FileMimeTypes {
    // 定义不同文件类型的常量
    const val apkType = "apk"
    const val rarType = "rar"
    const val pdfType = "pdf"
    const val javaType = "java"
    const val kotlinType = "kt"
    const val xmlType = "xml"

    const val aiType = "ai"
    const val docType = "doc"
    const val docxType = "docx"
    const val xlsType = "xls"
    const val xlsxType = "xlsx"

    const val pptType = "ppt"
    const val pptxType = "pptx"
    const val sqlType = "sql"
    const val svgType = "svg"

    // 默认的MIME类型
    const val default = "*/*"

    /**
     * 字体文件类型数组
     */
    @JvmField
    val fontType = arrayOf("ttf", "otf")

    /**
     * 音频文件类型数组
     */
    @JvmField
    val audioType = arrayOf("mp3", "4mp", "aup", "ogg", "3ga", "m4b", "wav", "acc", "m4a")

    /**
     * 视频文件类型数组
     */
    @JvmField
    val videoType = arrayOf("mp4", "mov", "avi", "mkv", "wmv", "m4v", "3gp", "webm")

    /**
     * 归档文件类型数组
     */
    @JvmField
    val archiveType = arrayOf("zip", "7z", "tar", "jar", "gz", "xz", "xapk", "obb", apkType)

    /**
     * 文本文件类型数组
     */
    @JvmField
    val textType = arrayOf("txt", "text", "log", "dsc", "apt", "rtf", "rtx")

    /**
     * 代码文件类型数组
     */
    @JvmField
    val codeType = arrayOf(javaType, xmlType, "py", "css", kotlinType, "cs", "xml", "json")

    /**
     * 图片文件类型数组
     */
    @JvmField
    val imageType = arrayOf("png", "jpeg", "jpg", "heic", "tiff", "gif", "webp", svgType, "bmp")

    /**
     * 存储文件扩展名和对应MIME类型的映射关系
     */
    val mimeTypes = HashMap<String, String>().apply {
        // 文本类文件
        put("asm", "text/x-asm")
        put("def", "text/plain")
        put("in", "text/plain")
        put("rc", "text/plain")
        put("list", "text/plain")
        put("log", "text/plain")
        put("pl", "text/plain")
        put("prop", "text/plain")
        put("properties", "text/plain")
        put("rc", "text/plain")

        // 电子书籍类文件
        put("epub", "application/epub+zip")
        put("ibooks", "application/x-ibooks+zip")

        // 日历和邮件类文件
        put("ifb", "text/calendar")
        put("eml", "message/rfc822")
        put("msg", "application/vnd.ms-outlook")

        // 压缩文件
        put("ace", "application/x-ace-compressed")
        put("bz", "application/x-bzip")
        put("bz2", "application/x-bzip2")
        put("cab", "application/vnd.ms-cab-compressed")
        put("gz", "application/x-gzip")
        put("lrf", "application/octet-stream")
        put("jar", "application/java-archive")
        put("xz", "application/x-xz")
        put("Z", "application/x-compress")

        // 脚本和可执行文件
        put("bat", "application/x-msdownload")
        put("ksh", "text/plain")
        put("sh", "application/x-sh")

        // 数据库文件
        put("db", "application/octet-stream")
        put("db3", "application/octet-stream")

        // 字体文件
        put("otf", "application/x-font-otf")
        put("ttf", "application/x-font-ttf")
        put("psf", "application/x-font-linux-psf")

        // 图片文件
        put("cgm", "image/cgm")
        put("btif", "image/prs.btif")
        put("dwg", "image/vnd.dwg")
        put("dxf", "image/vnd.dxf")
        put("fbs", "image/vnd.fastbidsheet")
        put("fpx", "image/vnd.fpx")
        put("fst", "image/vnd.fst")
        put("mdi", "image/vnd.ms-mdi")
        put("npx", "image/vnd.net-fpx")
        put("xif", "image/vnd.xiff")
        put("pct", "image/x-pict")
        put("pic", "image/x-pict")

        // 音频文件
        put("adp", "audio/adpcm")
        put("au", "audio/basic")
        put("snd", "audio/basic")
        put("m2a", "audio/mpeg")
        put("m3a", "audio/mpeg")
        put("oga", "audio/ogg")
        put("spx", "audio/ogg")
        put("aac", "audio/x-aac")
        put("mka", "audio/x-matroska")

        // 视频文件
        put("jpgv", "video/jpeg")
        put("jpgm", "video/jpm")
        put("jpm", "video/jpm")
        put("mj2", "video/mj2")
        put("mjp2", "video/mj2")
        put("mpa", "video/mpeg")
        put("ogv", "video/ogg")
        put("flv", "video/x-flv")
        put("mkv", "video/x-matroska")
    }
}
