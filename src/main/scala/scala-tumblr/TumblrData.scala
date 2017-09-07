package io.github.petesta.tumblr

import java.time.ZonedDateTime

final case class Meta(
  status: Int,
  msg: String
)

sealed trait TumblrResponse[T] {
  val meta: Meta
  val response: T
}

final case class Blog(
  ask: Boolean,
  askAnon: Boolean,
  askPageTitle: String,
  canSubscribe: Boolean,
  description: String,
  isAdult: Boolean,
  isNsfw: Boolean,
  name: String,
  posts: Long,
  replyConditions: String,
  shareLikes: Boolean,
  submissionPageTitle: String,
  subscribed: Boolean,
  title: String,
  totalPosts: Long,
  updated: Long,
  url: String,
  isOptOutAds: Boolean
)

final case class BlogInfo(
  blog: Blog
)

final case class InfoResponse(
  meta: Meta,
  response: BlogInfo
) extends TumblrResponse[BlogInfo]

final case class Reblog(
  comment: String,
  treeHtml: String
)

final case class Theme(
  headerFullWidth: Long,
  headerFullHeight: Long,
  headerFocusWidth: Long,
  headerFocusHeight: Long,
  avatarShape: String,
  backgroundColor: String,
  bodyFont: String,
  headerBounds: String,
  headerImage: String,
  headerImageFocused: String,
  headerImageScaled: String,
  headerStretch: Boolean,
  linkColor: String,
  showAvatar: Boolean,
  showDescription: Boolean,
  showHeaderImage: Boolean,
  showTitle: Boolean,
  titleColor: String,
  titleFont: String,
  titleFontWeight: String
)

final case class BlogObject(
  name: String,
  active: Boolean,
  theme: Theme,
  shareLikes: Boolean,
  shareFollowing: Boolean,
  canBeFollowed: Boolean
)

final case class TrailPostObject(
  id: String
)

final case class Trail(
  blog: List[BlogObject],
  post: TrailPostObject,
  contentRaw: String,
  content: String,
  isCurrentItem: Boolean,
  isRootItem: Boolean
)

final case class Size(
  url: String,
  width: Long,
  height: Long
)

final case class Photos(
  caption: String,
  originalSize: Size,
  altSizes: List[Size]
)

final case class Post(
  `type`: String,
  blogName: String,
  id: Long,
  postUrl: String,
  slug: String,
  date: ZonedDateTime,
  timestamp: Long,
  state: String,
  format: String,
  reblogKey: String,
  tags: List[String],
  shortUrl: String,
  summary: String,
  isBlocksPostFormat: Boolean,
  recommendedSource: Option[String],
  recommendedColor: Option[String],
  postAuthor: String,
  postAuthorIsAdult: Boolean,
  isSubmission: Boolean,
  noteCount: Long,
  title: String,
  body: String,
  reblog: Reblog,
  trail: List[Trail],
  photosetLayout: String,
  photos: Option[List[Photos]],
  canLike: Boolean,
  canReblog: Boolean,
  canSendInMessage: Boolean,
  canReply: Boolean,
  displayAvatar: Boolean
)

final case class LikesObject(
  likedPosts: List[Post],
  likedCount: Long
)

final case class LikesResponse(
  meta: Meta,
  response: LikesObject
) extends TumblrResponse[LikesObject]

final case class TumblrPosts(
  blog: Blog,
  posts: List[Post],
  totalPosts: Long,
  supplyLoggingPositions: List[Double]
)

final case class PostsResponse(
  meta: Meta,
  response: TumblrPosts
) extends TumblrResponse[TumblrPosts]

final case class TaggedResponse(
  meta: Meta,
  response: List[Post]
) extends TumblrResponse[List[Post]]
