# JavaTwitterAPI

This API is made using Spring Boot 2.1.5, Maven. In this API, I fetch replies to a youtube video , reply to a particular comment, comment on a particular video using videoId.

# Prerequisites

Use spring initiliser to create a project and add dependencies.<br>
<br>
Get your google credentials from [https://console.developers.google.com](https://console.developers.google.com/) Download that credentials and put that json file in your resources folder in Spring project.<br>
<br>



# Software Requirements

Spring Boot 2.1.5 or above<br>
JDK 1.8<br>
PostMan<br>
Intellij/Eclipse<br>


# Dependencies Used

spring-boot-starter-web<br>
google-api-client<br>
google-api-services-youtube<br>
google-oauth-client-jetty<br>
json-simple<br>



# Features of API

Fetches the comments on a particular youtube video, first it authorises the client using his/her credentials, and then gets you the comments.

Makes a comment on a video using videoId and authorising the user's credentials. It then sends a callback after authorising and after hitting that callback URL, we are able to post a comment.

Replies to a comment by taking the list of comments from a particular video and then taking a particular comment out of it and then using the parentId of a comment, makes the reply.


