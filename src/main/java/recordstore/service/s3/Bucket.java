package recordstore.service.s3;

public enum Bucket {

    PROFILE_IMAGE("recordstore-file-storage");

    private final String bucketName;

    Bucket(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}