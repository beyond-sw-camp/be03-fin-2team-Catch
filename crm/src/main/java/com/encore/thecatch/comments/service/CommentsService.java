package com.encore.thecatch.comments.service;

import com.encore.thecatch.admin.domain.Admin;
import com.encore.thecatch.admin.repository.AdminRepository;
import com.encore.thecatch.comments.dto.request.CreateCommentsReq;
import com.encore.thecatch.comments.dto.request.UpdateCommentsReq;
import com.encore.thecatch.comments.dto.response.CreateCommentsRes;
import com.encore.thecatch.comments.dto.response.DetailCommentRes;
import com.encore.thecatch.comments.dto.response.UpdateCommentsRes;
import com.encore.thecatch.comments.entity.Comments;
import com.encore.thecatch.comments.repository.CommentsRepository;
import com.encore.thecatch.common.CatchException;
import com.encore.thecatch.common.ResponseCode;
import com.encore.thecatch.common.util.AesUtil;
import com.encore.thecatch.complaint.entity.Complaint;
import com.encore.thecatch.complaint.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentsService {

    private final ComplaintRepository complaintRepository;
    private final CommentsRepository commentsRepository;
    private final AdminRepository adminRepository;
    private final AesUtil aesUtil;

    @PreAuthorize("hasAuthority('CS')")
    public CreateCommentsRes createComment(Long id, CreateCommentsReq createCommentsReq) throws Exception {

        if (commentsRepository.findByComplaintIdAndActive(id, true).isPresent()) {
            throw new CatchException(ResponseCode.ALREADY_BEEN_COMMENTS);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminRepository.findByEmployeeNumber(authentication.getName()).orElseThrow(() -> new CatchException(ResponseCode.ADMIN_NOT_FOUND));
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new CatchException(ResponseCode.POST_NOT_FOUND));
        Comments comments = createCommentsReq.toEntity(complaint, admin);
        complaint.isReply();
        commentsRepository.save(comments);

        return CreateCommentsRes.from(comments);
    }

    public DetailCommentRes detailComment(Long id) {
        Comments comments = commentsRepository.findByComplaintIdAndActive(id, true).orElseThrow(() -> new CatchException(ResponseCode.COMMENT_NOT_FOUND));
        return DetailCommentRes.from(comments);
    }

    @PreAuthorize("hasAuthority('CS')")
    public UpdateCommentsRes updateComment(Long id, UpdateCommentsReq updateCommentsReq) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Admin admin = adminRepository.findByEmployeeNumber(authentication.getName()).orElseThrow(() -> new CatchException(ResponseCode.ADMIN_NOT_FOUND));
        Comments comments = commentsRepository.findByComplaintIdAndActive(id, true).orElseThrow(() -> new CatchException(ResponseCode.COMMENT_NOT_FOUND));
        if (admin != comments.getAdmin()){
            throw new CatchException(ResponseCode.COMMENT_CAN_NOT_UPDATE);
        }
        comments.updateComment(updateCommentsReq.getComment());
        return UpdateCommentsRes.from(comments);
    }

    @PreAuthorize("hasAuthority('CS')")
    public String deleteComment(Long id) {
        Complaint complaint = complaintRepository.findById(id).orElseThrow(() -> new CatchException(ResponseCode.POST_NOT_FOUND));
        Comments comments = commentsRepository.findByComplaintIdAndActive(id, true).orElseThrow(() -> new CatchException(ResponseCode.COMMENT_NOT_FOUND));
        comments.deleteComment();
        complaint.isBefore();
        return "Complete delete";
    }
}
