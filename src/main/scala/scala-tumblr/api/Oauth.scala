package io.github.petesta.tumblr

final case class Following(
  blogName: String,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends GET {
  val path = s"${versionBlog(blogName)}/following"
}

final case class Followers(
  blogName: String,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends GET {
  val path = s"${versionBlog(blogName)}/followers"
}

final case class PostsQueue(
  blogName: String,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends GET {
  val path = s"${versionBlog(blogName)}/posts/queue"
}

final case class PostsDraft(
  blogName: String,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends GET {
  val path = s"${versionBlog(blogName)}/posts/draft"
}

final case class PostsSubmission(
  blogName: String,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends GET {
  val path = s"${versionBlog(blogName)}/posts/submission"
}

final case class UserInfo(
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends GET {
  val path = "/v2/user/info"
}

final case class UserDashboard(
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends GET {
  val path = "/v2/user/dashboard"
}

final case class UserLikes(
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends GET {
  val path = "/v2/user/likes"
}

final case class UserFollowing(
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends GET {
  val path = "/v2/user/following"
}

final case class DeletePost(
  blogName: String,
  id: Long
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends DELETE {
  val path = s"${versionBlog(blogName)}/post/delete"
  val bodyParams = Some(Map("id" -> id))
}

final case class FollowUser(
  blogUrl: String
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends POST {
  val params = None
  val path = s"/v2/user/follow"
  val bodyParams = Some(Map("url" -> blogUrl))
}

final case class UnfollowUser(
  blogUrl: String
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends POST {
  val params = None
  val path = s"/v2/user/unfollow"
  val bodyParams = Some(Map("url" -> blogUrl))
}

final case class LikePost(
  id: Long,
  reblogKey: Long
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends POST {
  val params = None
  val path = s"/v2/user/like"
  val bodyParams = Some(
    Map(
      "id" -> id.toString,
      "reblog_key" -> reblogKey.toString
    )
  )
}

final case class UnlikePost(
  id: Long,
  reblogKey: Long
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends POST {
  val params = None
  val path = s"/v2/user/unlike"
  val bodyParams = Some(
    Map(
      "id" -> id.toString,
      "reblog_key" -> reblogKey.toString
    )
  )
}

final case class CreatePost(
  blogName: String,
  posts: PostType,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends POST {
  val path = s"${versionBlog(blogName)}/post"
  val bodyParams = for {
    mappedParams <- params
    postsParams <- posts.params
  } yield mappedParams ++ postsParams
}

final case class EditPost(
  blogName: String,
  posts: PostType,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends POST {
  val path = s"${versionBlog(blogName)}/post/edit"
  val bodyParams = for {
    mappedParams <- params
    postsParams <- posts.params
  } yield mappedParams ++ postsParams
}

final case class ReblogPost(
  blogName: String,
  id: Long,
  reblogKey: Long,
  posts: PostType,
  params: Option[Map[String, String]] = None
)(implicit val oauthConfig: OauthConfig, val apiConfig: ApiConfig) extends POST {
  val path = s"${versionBlog(blogName)}/post/reblog"
  val bodyParams = for {
    mappedParams <- params
    instanceParams <- Some(Map("id" -> id.toString, "reblog_key" -> reblogKey.toString))
    postsParams <- posts.params
  } yield mappedParams ++ postsParams
}
