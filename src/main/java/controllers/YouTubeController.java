package controllers;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import services.YouTubeService;

import java.util.List;

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
        String commentBody = (String)jsonObject.get("comment_body");

        // Create a comment snippet with text.
        CommentSnippet commentSnippet = new CommentSnippet();
        commentSnippet.setTextOriginal(commentBody);


        // Create a top-level comment with snippet.
        Comment topLevelComment = new Comment();
        topLevelComment.setSnippet(commentSnippet);


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

    @RequestMapping(value = "/replyToComment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Comment replyToComment(@RequestBody JSONObject jsonObject) throws Exception{

        String videoId = (String)jsonObject.get("video_id");
        String text = (String)jsonObject.get("comment_body");

        // Call the YouTube Data API's commentThreads.list method to
        // retrieve video comment threads.
        CommentThreadListResponse videoCommentsListResponse = youTubeService.getService().commentThreads()
                .list("snippet").setVideoId(videoId).setTextFormat("plainText").execute();
        List<CommentThread> videoComments = videoCommentsListResponse.getItems();

        Comment commentInsertResponse = null;
        if (videoComments.isEmpty()) {
            System.out.println("Can't get video comments.");
        } else {
            // Print information from the API response.
            System.out
                    .println("\n================== Returned Video Comments ==================\n");
            for (CommentThread videoComment : videoComments) {
                CommentSnippet snippet = videoComment.getSnippet().getTopLevelComment()
                        .getSnippet();
                System.out.println("  - Author: " + snippet.getAuthorDisplayName());
                System.out.println("  - Comment: " + snippet.getTextDisplay());
                System.out
                        .println("\n-------------------------------------------------------------\n");
            }
            CommentThread firstComment = videoComments.get(0);

            // Will use this thread as parent to new reply.
            String parentId = firstComment.getId();

            // Create a comment snippet with text.
            CommentSnippet commentSnippet = new CommentSnippet();
            commentSnippet.setTextOriginal(text);
            commentSnippet.setParentId(parentId);

            // Create a comment with snippet.
            Comment comment = new Comment();
            comment.setSnippet(commentSnippet);

            // Call the YouTube Data API's comments.insert method to reply
            // to a comment.
            // (If the intention is to create a new top-level comment,
            // commentThreads.insert
            // method should be used instead.)
            commentInsertResponse = youTubeService.getService().comments().insert("snippet", comment)
                    .execute();

        }
        return commentInsertResponse;
    }
}
