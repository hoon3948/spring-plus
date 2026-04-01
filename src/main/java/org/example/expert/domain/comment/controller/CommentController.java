package org.example.expert.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.request.CommentSaveRequest;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.comment.dto.response.CommentSaveResponse;
import org.example.expert.domain.comment.service.CommentService;
import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{todoId}/comments")
    public ResponseEntity<CommentSaveResponse> saveComment(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable long todoId,
            @Valid @RequestBody CommentSaveRequest commentSaveRequest
    ) {
        return ResponseEntity.ok(commentService.saveComment(authUser, todoId, commentSaveRequest));
    }

    @GetMapping("/{todoId}/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable long todoId) {
        return ResponseEntity.ok(commentService.getComments(todoId));
    }
}
