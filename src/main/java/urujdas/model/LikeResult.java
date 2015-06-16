package urujdas.model;

public class LikeResult {
    private final LikeType likeType;
    private final Integer likesCount;

    public LikeResult(LikeType likeType, Integer likesCount) {
        this.likeType = likeType;
        this.likesCount = likesCount;
    }

    public LikeType getLikeType() {
        return likeType;
    }

    public Integer getLikesCount() {
        return likesCount;
    }
}
