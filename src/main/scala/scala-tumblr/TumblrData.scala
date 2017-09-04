package io.github.petesta.tumblr

final case class Meta(
  status: Int,
  msg: String
)

sealed trait Base

final case class TaggedResponse(
  blog: BlogInfo
)

final case class BlogInfo(
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

final case class LikeResponse(
  blog: BlogInfo
) extends Base

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

final case class PostObject(
  id: String
)

final case class Trail(
  blog: List[BlogObject],
  post: PostObject,
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
  blogName: String,
  id: Long,
  postUrl: String,
  slug: String,
  date: String, // ZonedDateTime
  timestamp: Long,
  state: String,
  format: String,
  reblogKey: String,
  tags: List[String],
  shortUrl: String,
  summary: String,
  isBlocksPostFormat: Boolean,
  // recommendedSource: Option[],
  // recommendedColor: Option[],
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

final case class PostResponse(
  blog: BlogObject,
  posts: List[Post],
  totalPosts: Long// ,
  // supplyLoggingPositions: List[]
)

final case class TumblrResponse(
  meta: Meta,
  response: Base
)
