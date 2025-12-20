package com.posting.post.dto.response;

public record PermissionsResponseDTO(
        boolean canEdit,
        boolean canDelete,
        boolean isOwner
) {
}
