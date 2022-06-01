package com.demo.freshworkstudiopractical.model.response

data class GifResponseModel(
    var `data`: List<GitDataModel>? = null,
    var pagination: Pagination? = null,
    var meta: Meta
) {
    data class GitDataModel(
        var bitly_gif_url: String? = null,
        var bitly_url: String? = null,
        var content_url: String? = null,
        var embed_url: String? = null,
        var id: String? = null,
        var images: Images? = null,
        var import_datetime: String? = null,
        var is_sticker: Int? = null,
        var rating: String? = null,
        var slug: String? = null,
        var source: String? = null,
        var source_post_url: String? = null,
        var source_tld: String? = null,
        var title: String? = null,
        var trending_datetime: String? = null,
        var type: String? = null,
        var url: String? = null,
        var username: String? = null
    ) {

        data class Images(
            var `480w_still`: WStill? = null,
            var downsized: Downsized? = null,
            var downsized_large: DownsizedLarge? = null,
            var downsized_medium: DownsizedMedium? = null,
            var downsized_small: DownsizedSmall? = null,
            var downsized_still: DownsizedStill? = null,
            var fixed_height: FixedHeight? = null,
            var fixed_height_downsampled: FixedHeightDownsampled? = null,
            var fixed_height_small: FixedHeightSmall? = null,
            var fixed_height_small_still: FixedHeightSmallStill? = null,
            var fixed_height_still: FixedHeightStill? = null,
            var fixed_width: FixedWidth? = null,
            var fixed_width_downsampled: FixedWidthDownsampled? = null,
            var fixed_width_small: FixedWidthSmall? = null,
            var fixed_width_small_still: FixedWidthSmallStill? = null,
            var fixed_width_still: FixedWidthStill? = null,
            var hd: Hd? = null,
            var looping: Looping? = null,
            var original: Original? = null,
            var original_mp4: OriginalMp4? = null,
            var original_still: OriginalStill? = null,
            var preview: Preview? = null,
            var preview_gif: PreviewGif? = null,
            var preview_webp: PreviewWebp? = null
        ) {
            data class WStill(
                var height: String? = null,
                var size: String? = null,
                var url: String? = null,
                var width: String? = null
            )

            data class Downsized(
                var height: String? = null,
                var size: String? = null,
                var url: String? = null,
                var width: String? = null
            )

            data class DownsizedLarge(
                var height: String? = null,
                var size: String? = null,
                var url: String? = null,
                var width: String? = null
            )

            data class DownsizedMedium(
                var height: String? = null,
                var size: String? = null,
                var url: String? = null,
                var width: String? = null
            )

            data class DownsizedSmall(
                var height: String? = null,
                var mp4: String? = null,
                var mp4_size: String? = null,
                var width: String? = null
            )

            data class DownsizedStill(
                var height: String? = null,
                var size: String? = null,
                var url: String? = null,
                var width: String? = null
            )

            data class FixedHeight(
                var height: String? = null,
                var mp4: String? = null,
                var mp4_size: String? = null,
                var size: String? = null,
                var url: String? = null,
                var webp: String? = null,
                var webp_size: String? = null,
                var width: String? = null
            )

            data class FixedHeightDownsampled(
                var height: String? = null,
                var size: String? = null,
                var url: String? = null,
                var webp: String? = null,
                var webp_size: String? = null,
                var width: String? = null
            )

            data class FixedHeightSmall(
                var height: String? = null,
                var mp4: String? = null,
                var mp4_size: String? = null,
                var size: String? = null,
                var url: String? = null,
                var webp: String? = null,
                var webp_size: String? = null,
                var width: String? = null
            )

            data class FixedHeightSmallStill(
                var height: String? = null,
                var size: String? = null,
                var url: String? = null,
                var width: String? = null
            )

            data class FixedHeightStill(
                var height: String? = null,
                var size: String? = null,
                var url: String? = null,
                var width: String? = null
            )

            data class FixedWidth(
                var height: String? = null,
                var mp4: String? = null,
                var mp4_size: String? = null,
                var size: String? = null,
                var url: String? = null,
                var webp: String? = null,
                var webp_size: String? = null,
                var width: String? = null
            )

            data class FixedWidthDownsampled(
                var height: String? = null,
                var size: String? = null,
                var url: String? = null,
                var webp: String? = null,
                var webp_size: String? = null,
                var width: String? = null
            )

            data class FixedWidthSmall(
                var height: String? = null,
                var mp4: String? = null,
                var mp4_size: String? = null,
                var size: String? = null,
                var url: String? = null,
                var webp: String? = null,
                var webp_size: String? = null,
                var width: String? = null
            )

            data class FixedWidthSmallStill(
                var height: String? = null,
                var size: String? = null,
                var url: String? = null,
                var width: String? = null
            )

            data class FixedWidthStill(
                var height: String? = null,
                var size: String? = null,
                var url: String? = null,
                var width: String? = null
            )

            data class Hd(
                var height: String? = null,
                var mp4: String? = null,
                var mp4_size: String? = null,
                var width: String? = null
            )

            data class Looping(
                var mp4: String? = null,
                var mp4_size: String? = null
            )

            data class Original(
                var frames: String? = null,
                var hash: String? = null,
                var height: String? = null,
                var mp4: String? = null,
                var mp4_size: String? = null,
                var size: String? = null,
                var url: String? = null,
                var webp: String? = null,
                var webp_size: String? = null,
                var width: String? = null
            )

            data class OriginalMp4(
                var height: String? = null,
                var mp4: String? = null,
                var mp4_size: String? = null,
                var width: String? = null
            )

            data class OriginalStill(
                var height: String? = null,
                var size: String? = null,
                var url: String? = null,
                var width: String? = null
            )

            data class Preview(
                var height: String? = null,
                var mp4: String? = null,
                var mp4_size: String? = null,
                var width: String? = null
            )

            data class PreviewGif(
                var height: String? = null,
                var size: String? = null,
                var url: String? = null,
                var width: String? = null
            )

            data class PreviewWebp(
                var height: String? = null,
                var size: String? = null,
                var url: String? = null,
                var width: String? = null
            )
        }
    }


    data class Pagination(
        var count: Int? = null,
        var offset: Int? = null,
        var total_count: Int? = null
    )

    data class Meta(
        var status: String? = null,
        var msg: String? = null,
    )
}