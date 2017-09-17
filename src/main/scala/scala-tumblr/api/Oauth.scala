package io.github.petesta.tumblr

final case class Following(
  blogName: String,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val path = s"${versionBlog(blogName)}/following"
}

final case class Followers(
  blogName: String,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val path = s"${versionBlog(blogName)}/followers"
}

final case class PostsQueue(
  blogName: String,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val path = s"${versionBlog(blogName)}/posts/queue"
}

final case class PostsDraft(
  blogName: String,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val path = s"${versionBlog(blogName)}/posts/draft"
}

final case class PostsSubmission(
  blogName: String,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val path = s"${versionBlog(blogName)}/posts/submission"
}

final case class UserInfo(
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val path = "/v2/user/info"
}

final case class UserDashboard(
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val path = "/v2/user/dashboard"
}

final case class UserLikes(
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val path = "/v2/user/likes"
}

final case class UserFollowing(
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val path = "/v2/user/following"
}

final case class DeletePost(
  blogName: String, id: Long
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val params: Option[Map[String, String]] = None
  val path = s"${versionBlog(blogName)}/post/delete"

  override protected val url = s"$urlBuilder&id=$id"
}

final case class FollowUser(
  blogUrl: String
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val params: Option[Map[String, String]] = None
  val path = s"/v2/user/follow"

  override protected val url = s"$urlBuilder&url=$blogUrl"
}

final case class UnollowUser(
  blogUrl: String
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val params: Option[Map[String, String]] = None
  val path = s"/v2/user/unfollow"

  override protected val url = s"$urlBuilder&url=$blogUrl"
}

final case class LikePost(
  id: Long,
  reblogKey: Long
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val params: Option[Map[String, String]] = None
  val path = s"/v2/user/like"

  override protected val url = s"$urlBuilder&id=$id&reblog_key=$reblogKey"
}

final case class UnlikePost(
  id: Long,
  reblogKey: Long
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val params: Option[Map[String, String]] = None
  val path = s"/v2/user/unlike"

  override protected val url = s"$urlBuilder&id=$id&reblog_key=$reblogKey"
}

final case class CreatePost(
  blogName: String,
  posts: PostType,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val path = s"${versionBlog(blogName)}/post"

  override protected val url = s"$urlBuilder${posts.`type`}"
}

final case class EditPost(
  blogName: String,
  posts: PostType,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val path = s"${versionBlog(blogName)}/post/edit"

  override protected val url = s"$urlBuilder${posts.`type`}"
}

final case class ReblogPost(
  blogName: String,
  id: Long,
  reblogKey: Long,
  posts: PostType,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends OAuth {
  val path = s"${versionBlog(blogName)}/post/reblog"

  override protected val url = s"$urlBuilder${posts.`type`}&id=$id&reblog_key=$reblogKey"
}
