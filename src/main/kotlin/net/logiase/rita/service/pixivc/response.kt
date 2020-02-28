package net.logiase.rita.service.pixivc

data class Response<T>(
    val message: String,
    val data: List<T>?
)

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

data class ArtistPreView(
    val id: Long,
    val name: String,
    val account: String,
    val avatar: String
)

data class Tag(
    val id: Long,
    val name: String,
    val translatedName: String
)

data class ImageUrl(
    val squareMedium: String,
    val medium: String,
    val large: String,
    val original: String
)