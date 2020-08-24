package controllers;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThreadListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import services.YouTubeService;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
public class YouTubeController {
    private static final String DEVELOPER_KEY = "AIzaSyBZW-9OYsCRSfMDZBQGErRnVh9tRaSNn1U";
    @Autowired
    YouTubeService youTubeService;
    @RequestMapping(value = "/getComments")
    public CommentThreadListResponse getComments() throws GeneralSecurityException, IOException {
        YouTube youtubeService = youTubeService.getService();
        // Define and execute the API request
        YouTube.CommentThreads.List request = youtubeService.commentThreads()
                .list("snippet, replies");
        CommentThreadListResponse response = request.setKey(DEVELOPER_KEY)
                .setVideoId("wszbwUdbLXs")
                .execute();
        return response;
    }
}
