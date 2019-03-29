package com.hc.calling.api.basemodel

import android.graphics.pdf.PdfDocument

open class PageModel<T> : BaseModel<T>() {
    var pageInfo: PdfDocument.PageInfo? = null

    data class PageInfo(
        var pageNum: Int,
        var pageSize: Int,
        var startRow: Int,
        var endRow: Int,
        var pages: Int
    )
}