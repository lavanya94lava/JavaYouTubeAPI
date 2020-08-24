package controllers;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import services.YouTubeService;

@RestController
public class YouTubeController {
    private static final String DEVELOPER_KEY = "AIzaSyBZW-9OYsCRSfMDZBQGErRnVh9tRaSNn1U";
    @Autowired
    YouTubeService youTubeService;

    @RequestMapping(value = "/getComments")
    public CommentThreadListResponse getComments() throws Exception {
        YouTube youtubeService = youTubeService.getService();
        // Define and execute the API request
        YouTube.CommentThreads.List request = youtubeService.commentThreads()
                .list("snippet, replies");
        CommentThreadListResponse response = request.setKey(DEVELOPER_KEY)
                .setVideoId("wszbwUdbLXs")
                .execute();
        return response;
    }

    @RequestMapping(value = "/postComment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommentThread postComment(@RequestBody JSONObject jsonObject) throws Exception {
        String videoId = (String)jsonObject.get("video_id");

        // Create a comment snippet with text.
        CommentSnippet commentSnippet = new CommentSnippet();
        commentSnippet.setTextOriginal("hi");


        // Create a top-level comment with snippet.
        Comment topLevelComment = new Comment();
        topLevelComment.setSnippet(commentSnippet);

        // Create a comment thread snippet with channelId and top-level
        // comment.
        CommentThreadSnippet commentThreadSnippet = new CommentThreadSnippet();
        commentThreadSnippet.setVideoId(videoId);
        commentThreadSnippet.setTopLevelComment(topLevelComment);

        // Create a comment thread with snippet.

        CommentThread commentThread = new CommentThread();
        commentThread.setSnippet(commentThreadSnippet);


        // Create a comment with snippet.
        CommentThread videoCommentInsert = youTubeService.getService().commentThreads().insert("snippet",commentThread).execute();
        return  videoCommentInsert;

    }

    @RequestMapping(value = "/replyToComment", method = RequestMethod.POST)
    public 
}
