package net.logiase.rita.service.pixivc

// region Pixivc数据类

/**
 * 基础回应
 */
data class Response<T>(
    val message: String,
    val data: List<T>?
)

/**
 * 画作
 */
data class Illust(
    val id: Long,
    val artistId: Long,
    val title: String,
    val type: String,
    val caption: String,
    val artistPreview: ArtistPreView,
    val tags: List<Tag>,
    val imageUrls: List<ImageUrl>,
    val pageCount: Int
)

/**
 * 画师概览
 */
data class ArtistPreView(
    val id: Long,
    val name: String,
    val account: String,
    val avatar: String
)

/**
 * Tag
 */
data class Tag(
    val id: Long,
    val name: String,
    val translatedName: String
)

/**
 * 图片URL
 */
data class ImageUrl(
    val squareMedium: String,
    val medium: String,
    val large: String,
    val original: String
)

// endregion